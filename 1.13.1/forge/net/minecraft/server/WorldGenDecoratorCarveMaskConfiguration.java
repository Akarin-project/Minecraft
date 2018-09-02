package net.minecraft.server;

public class WorldGenDecoratorCarveMaskConfiguration implements WorldGenFeatureDecoratorConfiguration {
    final WorldGenStage.Features a;
    final float b;

    public WorldGenDecoratorCarveMaskConfiguration(WorldGenStage.Features worldgenstage$features, float f) {
        this.a = worldgenstage$features;
        this.b = f;
    }
}
