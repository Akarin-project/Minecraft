package net.minecraft.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public interface IResourcePack extends Closeable {
    InputStream a(EnumResourcePackType var1, MinecraftKey var2) throws IOException;

    Collection<MinecraftKey> a(EnumResourcePackType var1, String var2, int var3, Predicate<String> var4);

    boolean b(EnumResourcePackType var1, MinecraftKey var2);

    Set<String> a(EnumResourcePackType var1);

    @Nullable
    <T> T a(ResourcePackMetaParser<T> var1) throws IOException;

    String a();
}
