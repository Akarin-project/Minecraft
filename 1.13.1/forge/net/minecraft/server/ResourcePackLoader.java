package net.minecraft.server;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackLoader {
    private static final Logger a = LogManager.getLogger();
    private static final ResourcePackInfo b = new ResourcePackInfo((new ChatMessage("resourcePack.broken_assets", new Object[0])).a(new EnumChatFormat[]{EnumChatFormat.RED, EnumChatFormat.ITALIC}), 4);
    private final String c;
    private final Supplier<IResourcePack> d;
    private final IChatBaseComponent e;
    private final IChatBaseComponent f;
    private final EnumResourcePackVersion g;
    private final ResourcePackLoader.Position h;
    private final boolean i;
    private final boolean j;

    @Nullable
    public static <T extends ResourcePackLoader> T a(String s, boolean flag, Supplier<IResourcePack> supplier, ResourcePackLoader.b<T> resourcepackloader$b, ResourcePackLoader.Position resourcepackloader$position) {
        try {
            IResourcePack iresourcepack = (IResourcePack)supplier.get();
            Throwable throwable = null;

            ResourcePackLoader resourcepackloader;
            try {
                ResourcePackInfo resourcepackinfo = (ResourcePackInfo)iresourcepack.a(ResourcePackInfo.a);
                if (flag && resourcepackinfo == null) {
                    a.error("Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!");
                    resourcepackinfo = b;
                }

                if (resourcepackinfo == null) {
                    a.warn("Couldn't find pack meta for pack {}", s);
                    return (T)null;
                }

                resourcepackloader = resourcepackloader$b.create(s, flag, supplier, iresourcepack, resourcepackinfo, resourcepackloader$position);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (iresourcepack != null) {
                    if (throwable != null) {
                        try {
                            iresourcepack.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        iresourcepack.close();
                    }
                }

            }

            return (T)resourcepackloader;
        } catch (IOException ioexception) {
            a.warn("Couldn't get pack info for: {}", ioexception.toString());
            return (T)null;
        }
    }

    public ResourcePackLoader(String s, boolean flag, Supplier<IResourcePack> supplier, IChatBaseComponent ichatbasecomponent, IChatBaseComponent ichatbasecomponent1, EnumResourcePackVersion enumresourcepackversion, ResourcePackLoader.Position resourcepackloader$position, boolean flag1) {
        this.c = s;
        this.d = supplier;
        this.e = ichatbasecomponent;
        this.f = ichatbasecomponent1;
        this.g = enumresourcepackversion;
        this.i = flag;
        this.h = resourcepackloader$position;
        this.j = flag1;
    }

    public ResourcePackLoader(String s, boolean flag, Supplier<IResourcePack> supplier, IResourcePack iresourcepack, ResourcePackInfo resourcepackinfo, ResourcePackLoader.Position resourcepackloader$position) {
        this(s, flag, supplier, new ChatComponentText(iresourcepack.a()), resourcepackinfo.a(), EnumResourcePackVersion.a(resourcepackinfo.b()), resourcepackloader$position, false);
    }

    public IChatBaseComponent a(boolean flag) {
        return ChatComponentUtils.a((IChatBaseComponent)(new ChatComponentText(this.c))).a((chatmodifier) -> {
            chatmodifier.setColor(flag ? EnumChatFormat.GREEN : EnumChatFormat.RED).setInsertion(StringArgumentType.escapeIfRequired(this.c)).setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_TEXT, (new ChatComponentText("")).addSibling(this.e).a("\n").addSibling(this.f)));
        });
    }

    public EnumResourcePackVersion c() {
        return this.g;
    }

    public IResourcePack d() {
        return (IResourcePack)this.d.get();
    }

    public String e() {
        return this.c;
    }

    public boolean f() {
        return this.i;
    }

    public boolean g() {
        return this.j;
    }

    public ResourcePackLoader.Position h() {
        return this.h;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ResourcePackLoader)) {
            return false;
        } else {
            ResourcePackLoader resourcepackloader1 = (ResourcePackLoader)object;
            return this.c.equals(resourcepackloader1.c);
        }
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public static enum Position {
        TOP,
        BOTTOM;

        private Position() {
        }

        public <T, P extends ResourcePackLoader> int a(List<T> list, T object, Function<T, P> function, boolean flag) {
            ResourcePackLoader.Position resourcepackloader$position1 = flag ? this.a() : this;
            if (resourcepackloader$position1 == BOTTOM) {
                int j;
                for(j = 0; j < list.size(); ++j) {
                    ResourcePackLoader resourcepackloader1 = (ResourcePackLoader)function.apply(list.get(j));
                    if (!resourcepackloader1.g() || resourcepackloader1.h() != this) {
                        break;
                    }
                }

                list.add(j, object);
                return j;
            } else {
                int i;
                for(i = list.size() - 1; i >= 0; --i) {
                    ResourcePackLoader resourcepackloader = (ResourcePackLoader)function.apply(list.get(i));
                    if (!resourcepackloader.g() || resourcepackloader.h() != this) {
                        break;
                    }
                }

                list.add(i + 1, object);
                return i + 1;
            }
        }

        public ResourcePackLoader.Position a() {
            return this == TOP ? BOTTOM : TOP;
        }
    }

    @FunctionalInterface
    public interface b<T extends ResourcePackLoader> {
        @Nullable
        T create(String var1, boolean var2, Supplier<IResourcePack> var3, IResourcePack var4, ResourcePackInfo var5, ResourcePackLoader.Position var6);
    }
}
