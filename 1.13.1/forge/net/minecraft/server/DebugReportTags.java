package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DebugReportTags<T> implements DebugReportProvider {
    private static final Logger e = LogManager.getLogger();
    private static final Gson f = (new GsonBuilder()).setPrettyPrinting().create();
    protected final DebugReportGenerator b;
    protected final IRegistry<T> c;
    protected final Map<Tag<T>, Tag.a<T>> d = Maps.newLinkedHashMap();

    protected DebugReportTags(DebugReportGenerator debugreportgenerator, IRegistry<T> iregistry) {
        this.b = debugreportgenerator;
        this.c = iregistry;
    }

    protected abstract void b();

    public void a(HashCache hashcache) throws IOException {
        this.d.clear();
        this.b();
        Tags tags = new Tags((var0) -> {
            return false;
        }, (var0) -> {
            return null;
        }, "", false, "generated");

        for(Entry entry : this.d.entrySet()) {
            MinecraftKey minecraftkey = ((Tag)entry.getKey()).c();
            if (!((Tag.a)entry.getValue()).a(tags::a)) {
                throw new UnsupportedOperationException("Unsupported referencing of tags!");
            }

            Tag tag = ((Tag.a)entry.getValue()).b(minecraftkey);
            JsonObject jsonobject = tag.a(this.c::getKey);
            java.nio.file.Path path = this.a(minecraftkey);
            tags.a(tag);
            this.a(tags);

            try {
                String s = f.toJson(jsonobject);
                String s1 = a.hashUnencodedChars(s).toString();
                if (!Objects.equals(hashcache.a(path), s1) || !Files.exists(path, new LinkOption[0])) {
                    Files.createDirectories(path.getParent());
                    BufferedWriter bufferedwriter = Files.newBufferedWriter(path);
                    Throwable throwable = null;

                    try {
                        bufferedwriter.write(s);
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (bufferedwriter != null) {
                            if (throwable != null) {
                                try {
                                    bufferedwriter.close();
                                } catch (Throwable throwable1) {
                                    throwable.addSuppressed(throwable1);
                                }
                            } else {
                                bufferedwriter.close();
                            }
                        }

                    }
                }

                hashcache.a(path, s1);
            } catch (IOException ioexception) {
                e.error("Couldn't save tags to {}", path, ioexception);
            }
        }

    }

    protected abstract void a(Tags<T> var1);

    protected abstract java.nio.file.Path a(MinecraftKey var1);

    protected Tag.a<T> a(Tag<T> tag) {
        return (Tag.a)this.d.computeIfAbsent(tag, (var0) -> {
            return Tag.a.a();
        });
    }
}
