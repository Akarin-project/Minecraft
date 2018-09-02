package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureChorusPlant extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenFeatureChorusPlant() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        if (generatoraccess.isEmpty(blockposition.up()) && generatoraccess.getType(blockposition).getBlock() == Blocks.END_STONE) {
            BlockChorusFlower.a(generatoraccess, blockposition.up(), random, 8);
            return true;
        } else {
            return false;
        }
    }
}
