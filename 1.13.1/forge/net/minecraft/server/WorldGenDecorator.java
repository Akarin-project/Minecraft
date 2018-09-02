package net.minecraft.server;

import java.util.Random;

public abstract class WorldGenDecorator<T extends WorldGenFeatureDecoratorConfiguration> {
    public WorldGenDecorator() {
    }

    public abstract <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess var1, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition var4, T var5, WorldGenerator<C> var6, C var7);

    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
    }
}
