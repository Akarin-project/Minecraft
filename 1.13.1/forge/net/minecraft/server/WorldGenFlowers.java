package net.minecraft.server;

import java.util.Random;

public abstract class WorldGenFlowers extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenFlowers() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        IBlockData iblockdata = this.a(random, blockposition);
        int i = 0;

        for(int j = 0; j < 64; ++j) {
            BlockPosition blockposition1 = blockposition.a(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (generatoraccess.isEmpty(blockposition1) && blockposition1.getY() < 255 && iblockdata.canPlace(generatoraccess, blockposition1)) {
                generatoraccess.setTypeAndData(blockposition1, iblockdata, 2);
                ++i;
            }
        }

        return i > 0;
    }

    public abstract IBlockData a(Random var1, BlockPosition var2);
}
