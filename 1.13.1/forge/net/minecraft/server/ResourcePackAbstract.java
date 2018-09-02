package net.minecraft.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ResourcePackAbstract implements IResourcePack {
    private static final Logger b = LogManager.getLogger();
    protected final File a;

    public ResourcePackAbstract(File file1) {
        this.a = file1;
    }

    private static String c(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) {
        return String.format("%s/%s/%s", enumresourcepacktype.a(), minecraftkey.b(), minecraftkey.getKey());
    }

    protected static String a(File file1, File file2) {
        return file1.toURI().relativize(file2.toURI()).getPath();
    }

    public InputStream a(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) throws IOException {
        return this.a(c(enumresourcepacktype, minecraftkey));
    }

    public boolean b(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) {
        return this.c(c(enumresourcepacktype, minecraftkey));
    }

    protected abstract InputStream a(String var1) throws IOException;

    protected abstract boolean c(String var1);

    protected void d(String s) {
        b.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", s, this.a);
    }

    @Nullable
    public <T> T a(ResourcePackMetaParser<T> resourcepackmetaparser) throws IOException {
        return (T)a(resourcepackmetaparser, this.a("pack.mcmeta"));
    }

    @Nullable
    public static <T> T a(ResourcePackMetaParser<T> resourcepackmetaparser, InputStream inputstream) {
        JsonObject jsonobject;
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
            Throwable throwable = null;

            try {
                jsonobject = ChatDeserializer.a(bufferedreader);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (bufferedreader != null) {
                    if (throwable != null) {
                        try {
                            bufferedreader.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        bufferedreader.close();
                    }
                }

            }
        } catch (JsonParseException | IOException ioexception) {
            b.error("Couldn't load {} metadata", resourcepackmetaparser.a(), ioexception);
            return (T)null;
        }

        if (!jsonobject.has(resourcepackmetaparser.a())) {
            return (T)null;
        } else {
            try {
                return (T)resourcepackmetaparser.a(ChatDeserializer.t(jsonobject, resourcepackmetaparser.a()));
            } catch (JsonParseException jsonparseexception) {
                b.error("Couldn't load {} metadata", resourcepackmetaparser.a(), jsonparseexception);
                return (T)null;
            }
        }
    }

    public String a() {
        return this.a.getName();
    }
}
