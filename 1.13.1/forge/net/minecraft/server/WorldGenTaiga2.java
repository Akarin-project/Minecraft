package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenTaiga2 extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.SPRUCE_LOG.getBlockData();
    private static final IBlockData b = Blocks.SPRUCE_LEAVES.getBlockData();

    public WorldGenTaiga2(boolean flag) {
        super(flag);
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = random.nextInt(4) + 6;
        int j = 1 + random.nextInt(2);
        int k = i - j;
        int l = 2 + random.nextInt(2);
        boolean flag = true;
        if (blockposition.getY() >= 1 && blockposition.getY() + i + 1 <= 256) {
            for(int i1 = blockposition.getY(); i1 <= blockposition.getY() + 1 + i && flag; ++i1) {
                int j1;
                if (i1 - blockposition.getY() < j) {
                    j1 = 0;
                } else {
                    j1 = l;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for(int k1 = blockposition.getX() - j1; k1 <= blockposition.getX() + j1 && flag; ++k1) {
                    for(int l1 = blockposition.getZ() - j1; l1 <= blockposition.getZ() + j1 && flag; ++l1) {
                        if (i1 >= 0 && i1 < 256) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition.c(k1, i1, l1));
                            if (!iblockdata.isAir() && !iblockdata.a(TagsBlock.LEAVES)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                Block block = generatoraccess.getType(blockposition.down()).getBlock();
                if ((block == Blocks.GRASS_BLOCK || Block.d(block) || block == Blocks.FARMLAND) && blockposition.getY() < 256 - i - 1) {
                    this.a(generatoraccess, blockposition.down());
                    int i3 = random.nextInt(2);
                    int j3 = 1;
                    byte b0 = 0;

                    for(int k3 = 0; k3 <= k; ++k3) {
                        int i4 = blockposition.getY() + i - k3;

                        for(int i2 = blockposition.getX() - i3; i2 <= blockposition.getX() + i3; ++i2) {
                            int j2 = i2 - blockposition.getX();

                            for(int k2 = blockposition.getZ() - i3; k2 <= blockposition.getZ() + i3; ++k2) {
                                int l2 = k2 - blockposition.getZ();
                                if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                                    BlockPosition blockposition1 = new BlockPosition(i2, i4, k2);
                                    if (!generatoraccess.getType(blockposition1).f(generatoraccess, blockposition1)) {
                                        this.a(generatoraccess, blockposition1, b);
                                    }
                                }
                            }
                        }

                        if (i3 >= j3) {
                            i3 = b0;
                            b0 = 1;
                            ++j3;
                            if (j3 > l) {
                                j3 = l;
                            }
                        } else {
                            ++i3;
                        }
                    }

                    int l3 = random.nextInt(3);

                    for(int j4 = 0; j4 < i - l3; ++j4) {
                        IBlockData iblockdata1 = generatoraccess.getType(blockposition.up(j4));
                        if (iblockdata1.isAir() || iblockdata1.a(TagsBlock.LEAVES)) {
                            this.a(set, generatoraccess, blockposition.up(j4), a);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
