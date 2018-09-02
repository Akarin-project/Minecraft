package net.minecraft.server;

import java.util.Random;

public class WorldGenTaigaStructure extends WorldGenerator<WorldGenFeatureBlockOffsetConfiguration> {
    public WorldGenTaigaStructure() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureBlockOffsetConfiguration worldgenfeatureblockoffsetconfiguration) {
        while(true) {
            label50: {
                if (blockposition.getY() > 3) {
                    if (generatoraccess.isEmpty(blockposition.down())) {
                        break label50;
                    }

                    Block block = generatoraccess.getType(blockposition.down()).getBlock();
                    if (block != Blocks.GRASS_BLOCK && !Block.d(block) && !Block.c(block)) {
                        break label50;
                    }
                }

                if (blockposition.getY() <= 3) {
                    return false;
                }

                int i1 = worldgenfeatureblockoffsetconfiguration.b;

                for(int i = 0; i1 >= 0 && i < 3; ++i) {
                    int j = i1 + random.nextInt(2);
                    int k = i1 + random.nextInt(2);
                    int l = i1 + random.nextInt(2);
                    float f = (float)(j + k + l) * 0.333F + 0.5F;

                    for(BlockPosition blockposition1 : BlockPosition.a(blockposition.a(-j, -k, -l), blockposition.a(j, k, l))) {
                        if (blockposition1.n(blockposition) <= (double)(f * f)) {
                            generatoraccess.setTypeAndData(blockposition1, worldgenfeatureblockoffsetconfiguration.a.getBlockData(), 4);
                        }
                    }

                    blockposition = blockposition.a(-(i1 + 1) + random.nextInt(2 + i1 * 2), 0 - random.nextInt(2), -(i1 + 1) + random.nextInt(2 + i1 * 2));
                }

                return true;
            }

            blockposition = blockposition.down();
        }
    }
}
