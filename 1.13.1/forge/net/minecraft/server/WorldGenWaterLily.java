package net.minecraft.server;

import java.util.Random;

public class WorldGenWaterLily extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenWaterLily() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        BlockPosition blockposition2;
        for(BlockPosition blockposition1 = blockposition; blockposition1.getY() > 0; blockposition1 = blockposition2) {
            blockposition2 = blockposition1.down();
            if (!generatoraccess.isEmpty(blockposition2)) {
                break;
            }
        }

        for(int i = 0; i < 10; ++i) {
            BlockPosition blockposition3 = blockposition.a(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            IBlockData iblockdata = Blocks.LILY_PAD.getBlockData();
            if (generatoraccess.isEmpty(blockposition3) && iblockdata.canPlace(generatoraccess, blockposition3)) {
                generatoraccess.setTypeAndData(blockposition3, iblockdata, 2);
            }
        }

        return true;
    }
}
