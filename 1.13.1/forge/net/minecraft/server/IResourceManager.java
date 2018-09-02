package net.minecraft.server;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface IResourceManager {
    IResource a(MinecraftKey var1) throws IOException;

    List<IResource> b(MinecraftKey var1) throws IOException;

    Collection<MinecraftKey> a(String var1, Predicate<String> var2);
}
