package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureIceSnow extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenFeatureIceSnow() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
                int k = blockposition.getX() + i;
                int l = blockposition.getZ() + j;
                int i1 = generatoraccess.a(HeightMap.Type.MOTION_BLOCKING, k, l);
                blockposition$mutableblockposition.c(k, i1, l);
                blockposition$mutableblockposition1.g(blockposition$mutableblockposition).c(EnumDirection.DOWN, 1);
                BiomeBase biomebase = generatoraccess.getBiome(blockposition$mutableblockposition);
                if (biomebase.a(generatoraccess, blockposition$mutableblockposition1, false)) {
                    generatoraccess.setTypeAndData(blockposition$mutableblockposition1, Blocks.ICE.getBlockData(), 2);
                }

                if (biomebase.b(generatoraccess, blockposition$mutableblockposition)) {
                    generatoraccess.setTypeAndData(blockposition$mutableblockposition, Blocks.SNOW.getBlockData(), 2);
                    IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition1);
                    if (iblockdata.b(BlockDirtSnow.a)) {
                        generatoraccess.setTypeAndData(blockposition$mutableblockposition1, (IBlockData)iblockdata.set(BlockDirtSnow.a, Boolean.valueOf(true)), 2);
                    }
                }
            }
        }

        return true;
    }
}
