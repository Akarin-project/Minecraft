package net.minecraft.server;

import java.util.Map;
import java.util.function.Supplier;

public class ResourcePackSourceVanilla implements ResourcePackSource {

    private final ResourcePackVanilla a = new ResourcePackVanilla(new String[] { "minecraft"});

    public ResourcePackSourceVanilla() {}

    public <T extends ResourcePackLoader> void a(Map<String, T> map, ResourcePackLoader.b<T> resourcepackloader_b) {
        ResourcePackLoader resourcepackloader = ResourcePackLoader.a("vanilla", false, () -> {
            return this.a;
        }, resourcepackloader_b, ResourcePackLoader.Position.BOTTOM);

        if (resourcepackloader != null) {
            map.put("vanilla", resourcepackloader);
        }

    }
}
