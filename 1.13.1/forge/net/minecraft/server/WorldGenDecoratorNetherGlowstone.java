package net.minecraft.server;

import java.util.Random;

public class WorldGenDecoratorNetherGlowstone extends WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> {
    public WorldGenDecoratorNetherGlowstone() {
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, WorldGenerator<C> worldgenerator, C worldgenfeatureconfiguration) {
        for(int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a) + 1); ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(120) + 4;
            int l = random.nextInt(16);
            worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), worldgenfeatureconfiguration);
        }

        return true;
    }
}
