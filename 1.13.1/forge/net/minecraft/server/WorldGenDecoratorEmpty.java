package net.minecraft.server;

import java.util.Random;

public class WorldGenDecoratorEmpty extends WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> {
    public WorldGenDecoratorEmpty() {
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureDecoratorEmptyConfiguration var5, WorldGenerator<C> worldgenerator, C worldgenfeatureconfiguration) {
        return worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition, worldgenfeatureconfiguration);
    }
}
