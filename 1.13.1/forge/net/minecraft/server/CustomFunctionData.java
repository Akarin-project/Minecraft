package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomFunctionData implements ITickable, IResourcePackListener {
    private static final Logger c = LogManager.getLogger();
    private static final MinecraftKey d = new MinecraftKey("tick");
    private static final MinecraftKey e = new MinecraftKey("load");
    public static final int a = "functions/".length();
    public static final int b = ".mcfunction".length();
    private final MinecraftServer f;
    private final Map<MinecraftKey, CustomFunction> g = Maps.newHashMap();
    private final ArrayDeque<CustomFunctionData.a> h = new ArrayDeque();
    private boolean i;
    private final Tags<CustomFunction> j = new Tags<CustomFunction>((minecraftkey) -> {
        return this.a(minecraftkey) != null;
    }, this::a, "tags/functions", true, "function");
    private final List<CustomFunction> k = Lists.newArrayList();
    private boolean l;

    public CustomFunctionData(MinecraftServer minecraftserver) {
        this.f = minecraftserver;
    }

    @Nullable
    public CustomFunction a(MinecraftKey minecraftkey) {
        return (CustomFunction)this.g.get(minecraftkey);
    }

    public MinecraftServer a() {
        return this.f;
    }

    public int b() {
        return this.f.getGameRules().c("maxCommandChainLength");
    }

    public Map<MinecraftKey, CustomFunction> c() {
        return this.g;
    }

    public com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> d() {
        return this.f.getCommandDispatcher().a();
    }

    public void Y_() {
        this.f.methodProfiler.a(d::toString);

        for(CustomFunction customfunction : this.k) {
            this.a(customfunction, this.f());
        }

        this.f.methodProfiler.e();
        if (this.l) {
            this.l = false;
            Collection collection = this.g().b(e).a();
            this.f.methodProfiler.a(e::toString);

            for(CustomFunction customfunction1 : collection) {
                this.a(customfunction1, this.f());
            }

            this.f.methodProfiler.e();
        }

    }

    public int a(CustomFunction customfunction, CommandListenerWrapper commandlistenerwrapper) {
        int ix = this.b();
        if (this.i) {
            if (this.h.size() < ix) {
                this.h.addFirst(new CustomFunctionData.a(this, commandlistenerwrapper, new CustomFunction.d(customfunction)));
            }

            return 0;
        } else {
            try {
                this.i = true;
                int jx = 0;
                CustomFunction.c[] acustomfunction$c = customfunction.b();

                for(int kx = acustomfunction$c.length - 1; kx >= 0; --kx) {
                    this.h.push(new CustomFunctionData.a(this, commandlistenerwrapper, acustomfunction$c[kx]));
                }

                while(!this.h.isEmpty()) {
                    try {
                        CustomFunctionData.a customfunctiondata$a = (CustomFunctionData.a)this.h.removeFirst();
                        this.f.methodProfiler.a(customfunctiondata$a::toString);
                        customfunctiondata$a.a(this.h, ix);
                    } finally {
                        this.f.methodProfiler.e();
                    }

                    ++jx;
                    if (jx >= ix) {
                        int lx = jx;
                        return lx;
                    }
                }

                int i1 = jx;
                return i1;
            } finally {
                this.h.clear();
                this.i = false;
            }
        }
    }

    public void a(IResourceManager iresourcemanager) {
        this.g.clear();
        this.k.clear();
        this.j.b();
        Collection collection = iresourcemanager.a("functions", (s1) -> {
            return s1.endsWith(".mcfunction");
        });
        ArrayList arraylist = Lists.newArrayList();

        for(MinecraftKey minecraftkey : collection) {
            String s = minecraftkey.getKey();
            MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(a, s.length() - b));
            arraylist.add(CompletableFuture.supplyAsync(() -> {
                return a(iresourcemanager, minecraftkey);
            }, Resource.a).thenApplyAsync((list) -> {
                return CustomFunction.a(minecraftkey1, this, list);
            }).handle((customfunction, throwable) -> {
                return this.a(customfunction, throwable, minecraftkey);
            }));
        }

        CompletableFuture.allOf((CompletableFuture[])arraylist.toArray(new CompletableFuture[0])).join();
        if (!this.g.isEmpty()) {
            c.info("Loaded {} custom command functions", this.g.size());
        }

        this.j.a(iresourcemanager);
        this.k.addAll(this.j.b(d).a());
        this.l = true;
    }

    @Nullable
    private CustomFunction a(CustomFunction customfunction, @Nullable Throwable throwable, MinecraftKey minecraftkey) {
        if (throwable != null) {
            c.error("Couldn't load function at {}", minecraftkey, throwable);
            return null;
        } else {
            synchronized(this.g) {
                this.g.put(customfunction.a(), customfunction);
                return customfunction;
            }
        }
    }

    private static List<String> a(IResourceManager iresourcemanager, MinecraftKey minecraftkey) {
        try {
            IResource iresource = iresourcemanager.a(minecraftkey);
            Throwable throwable = null;

            List list;
            try {
                list = IOUtils.readLines(iresource.b(), StandardCharsets.UTF_8);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (iresource != null) {
                    if (throwable != null) {
                        try {
                            iresource.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        iresource.close();
                    }
                }

            }

            return list;
        } catch (IOException ioexception) {
            throw new CompletionException(ioexception);
        }
    }

    public CommandListenerWrapper f() {
        return this.f.getServerCommandListener().a(2).a();
    }

    public Tags<CustomFunction> g() {
        return this.j;
    }

    public static class a {
        private final CustomFunctionData a;
        private final CommandListenerWrapper b;
        private final CustomFunction.c c;

        public a(CustomFunctionData customfunctiondata, CommandListenerWrapper commandlistenerwrapper, CustomFunction.c customfunction$c) {
            this.a = customfunctiondata;
            this.b = commandlistenerwrapper;
            this.c = customfunction$c;
        }

        public void a(ArrayDeque<CustomFunctionData.a> arraydeque, int i) {
            try {
                this.c.a(this.a, this.b, arraydeque, i);
            } catch (Throwable var4) {
                ;
            }

        }

        public String toString() {
            return this.c.toString();
        }
    }
}
