package net.minecraft.server;

import java.util.Random;

public class WorldGenPackedIce2 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenPackedIce2() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        while(generatoraccess.isEmpty(blockposition) && blockposition.getY() > 2) {
            blockposition = blockposition.down();
        }

        if (generatoraccess.getType(blockposition).getBlock() != Blocks.SNOW_BLOCK) {
            return false;
        } else {
            blockposition = blockposition.up(random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                blockposition = blockposition.up(10 + random.nextInt(30));
            }

            for(int k = 0; k < i; ++k) {
                float f = (1.0F - (float)k / (float)i) * (float)j;
                int l = MathHelper.f(f);

                for(int i1 = -l; i1 <= l; ++i1) {
                    float f1 = (float)MathHelper.a(i1) - 0.25F;

                    for(int j1 = -l; j1 <= l; ++j1) {
                        float f2 = (float)MathHelper.a(j1) - 0.25F;
                        if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(random.nextFloat() > 0.75F))) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition.a(i1, k, j1));
                            Block block = iblockdata.getBlock();
                            if (iblockdata.isAir() || Block.d(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                                this.a(generatoraccess, blockposition.a(i1, k, j1), Blocks.PACKED_ICE.getBlockData());
                            }

                            if (k != 0 && l > 1) {
                                iblockdata = generatoraccess.getType(blockposition.a(i1, -k, j1));
                                block = iblockdata.getBlock();
                                if (iblockdata.isAir() || Block.d(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                                    this.a(generatoraccess, blockposition.a(i1, -k, j1), Blocks.PACKED_ICE.getBlockData());
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;
            if (k1 < 0) {
                k1 = 0;
            } else if (k1 > 1) {
                k1 = 1;
            }

            for(int l1 = -k1; l1 <= k1; ++l1) {
                for(int i2 = -k1; i2 <= k1; ++i2) {
                    BlockPosition blockposition1 = blockposition.a(l1, -1, i2);
                    int j2 = 50;
                    if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                        j2 = random.nextInt(5);
                    }

                    while(blockposition1.getY() > 50) {
                        IBlockData iblockdata1 = generatoraccess.getType(blockposition1);
                        Block block1 = iblockdata1.getBlock();
                        if (!iblockdata1.isAir() && !Block.d(block1) && block1 != Blocks.SNOW_BLOCK && block1 != Blocks.ICE && block1 != Blocks.PACKED_ICE) {
                            break;
                        }

                        this.a(generatoraccess, blockposition1, Blocks.PACKED_ICE.getBlockData());
                        blockposition1 = blockposition1.down();
                        --j2;
                        if (j2 <= 0) {
                            blockposition1 = blockposition1.down(random.nextInt(5) + 1);
                            j2 = random.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}
