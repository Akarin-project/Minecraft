package net.minecraft.server;

import java.util.Map;

public interface ResourcePackSource {
    <T extends ResourcePackLoader> void a(Map<String, T> var1, ResourcePackLoader.b<T> var2);
}
