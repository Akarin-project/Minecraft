package net.minecraft.server;

import java.util.Map;

public class ResourcePackSourceVanilla implements ResourcePackSource {
    private final ResourcePackVanilla a = new ResourcePackVanilla(new String[]{"minecraft"});

    public ResourcePackSourceVanilla() {
    }

    public <T extends ResourcePackLoader> void a(Map<String, T> map, ResourcePackLoader.b<T> resourcepackloader$b) {
        ResourcePackLoader resourcepackloader = ResourcePackLoader.a("vanilla", false, () -> {
            return this.a;
        }, resourcepackloader$b, ResourcePackLoader.Position.BOTTOM);
        if (resourcepackloader != null) {
            map.put("vanilla", resourcepackloader);
        }

    }
}
