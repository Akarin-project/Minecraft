package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenTaiga1 extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.SPRUCE_LOG.getBlockData();
    private static final IBlockData b = Blocks.SPRUCE_LEAVES.getBlockData();

    public WorldGenTaiga1() {
        super(false);
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = random.nextInt(5) + 7;
        int j = i - random.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + random.nextInt(k + 1);
        if (blockposition.getY() >= 1 && blockposition.getY() + i + 1 <= 256) {
            boolean flag = true;

            for(int i1 = blockposition.getY(); i1 <= blockposition.getY() + 1 + i && flag; ++i1) {
                int j1 = 1;
                if (i1 - blockposition.getY() < j) {
                    j1 = 0;
                } else {
                    j1 = l;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for(int k1 = blockposition.getX() - j1; k1 <= blockposition.getX() + j1 && flag; ++k1) {
                    for(int l1 = blockposition.getZ() - j1; l1 <= blockposition.getZ() + j1 && flag; ++l1) {
                        if (i1 >= 0 && i1 < 256) {
                            if (!this.a(generatoraccess.getType(blockposition$mutableblockposition.c(k1, i1, l1)).getBlock())) {
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
                if ((block == Blocks.GRASS_BLOCK || Block.d(block)) && blockposition.getY() < 256 - i - 1) {
                    this.a(generatoraccess, blockposition.down());
                    int k2 = 0;

                    for(int l2 = blockposition.getY() + i; l2 >= blockposition.getY() + j; --l2) {
                        for(int j3 = blockposition.getX() - k2; j3 <= blockposition.getX() + k2; ++j3) {
                            int k3 = j3 - blockposition.getX();

                            for(int i2 = blockposition.getZ() - k2; i2 <= blockposition.getZ() + k2; ++i2) {
                                int j2 = i2 - blockposition.getZ();
                                if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0) {
                                    BlockPosition blockposition1 = new BlockPosition(j3, l2, i2);
                                    if (!generatoraccess.getType(blockposition1).f(generatoraccess, blockposition1)) {
                                        this.a(generatoraccess, blockposition1, b);
                                    }
                                }
                            }
                        }

                        if (k2 >= 1 && l2 == blockposition.getY() + j + 1) {
                            --k2;
                        } else if (k2 < l) {
                            ++k2;
                        }
                    }

                    for(int i3 = 0; i3 < i - 1; ++i3) {
                        IBlockData iblockdata = generatoraccess.getType(blockposition.up(i3));
                        if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES)) {
                            this.a(set, generatoraccess, blockposition.up(i3), a);
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
