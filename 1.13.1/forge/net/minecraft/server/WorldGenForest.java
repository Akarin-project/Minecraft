package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenForest extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.BIRCH_LOG.getBlockData();
    private static final IBlockData b = Blocks.BIRCH_LEAVES.getBlockData();
    private final boolean c;

    public WorldGenForest(boolean flag, boolean flag1) {
        super(flag);
        this.c = flag1;
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = random.nextInt(3) + 5;
        if (this.c) {
            i += random.nextInt(7);
        }

        boolean flag = true;
        if (blockposition.getY() >= 1 && blockposition.getY() + i + 1 <= 256) {
            for(int j = blockposition.getY(); j <= blockposition.getY() + 1 + i; ++j) {
                byte b0 = 1;
                if (j == blockposition.getY()) {
                    b0 = 0;
                }

                if (j >= blockposition.getY() + 1 + i - 2) {
                    b0 = 2;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for(int k = blockposition.getX() - b0; k <= blockposition.getX() + b0 && flag; ++k) {
                    for(int l = blockposition.getZ() - b0; l <= blockposition.getZ() + b0 && flag; ++l) {
                        if (j >= 0 && j < 256) {
                            if (!this.a(generatoraccess.getType(blockposition$mutableblockposition.c(k, j, l)).getBlock())) {
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

                    for(int l1 = blockposition.getY() - 3 + i; l1 <= blockposition.getY() + i; ++l1) {
                        int j2 = l1 - (blockposition.getY() + i);
                        int k2 = 1 - j2 / 2;

                        for(int l2 = blockposition.getX() - k2; l2 <= blockposition.getX() + k2; ++l2) {
                            int i1 = l2 - blockposition.getX();

                            for(int j1 = blockposition.getZ() - k2; j1 <= blockposition.getZ() + k2; ++j1) {
                                int k1 = j1 - blockposition.getZ();
                                if (Math.abs(i1) != k2 || Math.abs(k1) != k2 || random.nextInt(2) != 0 && j2 != 0) {
                                    BlockPosition blockposition1 = new BlockPosition(l2, l1, j1);
                                    IBlockData iblockdata = generatoraccess.getType(blockposition1);
                                    if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES)) {
                                        this.a(generatoraccess, blockposition1, b);
                                    }
                                }
                            }
                        }
                    }

                    for(int i2 = 0; i2 < i; ++i2) {
                        IBlockData iblockdata1 = generatoraccess.getType(blockposition.up(i2));
                        if (iblockdata1.isAir() || iblockdata1.a(TagsBlock.LEAVES)) {
                            this.a(set, generatoraccess, blockposition.up(i2), a);
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
