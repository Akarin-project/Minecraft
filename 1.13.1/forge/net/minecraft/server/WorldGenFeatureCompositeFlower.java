package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureCompositeFlower<D extends WorldGenFeatureDecoratorConfiguration> extends WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, D> {
    public WorldGenFeatureCompositeFlower(WorldGenFlowers worldgenflowers, WorldGenDecorator<D> worldgendecorator, D worldgenfeaturedecoratorconfiguration) {
        super(worldgenflowers, WorldGenFeatureConfiguration.e, worldgendecorator, worldgenfeaturedecoratorconfiguration);
    }

    public IBlockData a(Random random, BlockPosition blockposition) {
        return ((WorldGenFlowers)this.a).a(random, blockposition);
    }
}
