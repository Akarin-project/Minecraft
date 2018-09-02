package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenAcaciaTree extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.ACACIA_LOG.getBlockData();
    private static final IBlockData b = Blocks.ACACIA_LEAVES.getBlockData();

    public WorldGenAcaciaTree(boolean flag) {
        super(flag);
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = random.nextInt(3) + random.nextInt(3) + 5;
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
                if ((block == Blocks.GRASS_BLOCK || Block.d(block)) && blockposition.getY() < 256 - i - 1) {
                    this.a(generatoraccess, blockposition.down());
                    EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
                    int j2 = i - random.nextInt(4) - 1;
                    int k2 = 3 - random.nextInt(3);
                    int l2 = blockposition.getX();
                    int i1 = blockposition.getZ();
                    int j1 = 0;

                    for(int k1 = 0; k1 < i; ++k1) {
                        int l1 = blockposition.getY() + k1;
                        if (k1 >= j2 && k2 > 0) {
                            l2 += enumdirection.getAdjacentX();
                            i1 += enumdirection.getAdjacentZ();
                            --k2;
                        }

                        BlockPosition blockposition1 = new BlockPosition(l2, l1, i1);
                        IBlockData iblockdata = generatoraccess.getType(blockposition1);
                        if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES)) {
                            this.a(set, generatoraccess, blockposition1);
                            j1 = l1;
                        }
                    }

                    BlockPosition blockposition3 = new BlockPosition(l2, j1, i1);

                    for(int i3 = -3; i3 <= 3; ++i3) {
                        for(int l3 = -3; l3 <= 3; ++l3) {
                            if (Math.abs(i3) != 3 || Math.abs(l3) != 3) {
                                this.b(generatoraccess, blockposition3.a(i3, 0, l3));
                            }
                        }
                    }

                    blockposition3 = blockposition3.up();

                    for(int j3 = -1; j3 <= 1; ++j3) {
                        for(int i4 = -1; i4 <= 1; ++i4) {
                            this.b(generatoraccess, blockposition3.a(j3, 0, i4));
                        }
                    }

                    this.b(generatoraccess, blockposition3.east(2));
                    this.b(generatoraccess, blockposition3.west(2));
                    this.b(generatoraccess, blockposition3.south(2));
                    this.b(generatoraccess, blockposition3.north(2));
                    l2 = blockposition.getX();
                    i1 = blockposition.getZ();
                    EnumDirection enumdirection1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
                    if (enumdirection1 != enumdirection) {
                        int k3 = j2 - random.nextInt(2) - 1;
                        int j4 = 1 + random.nextInt(3);
                        j1 = 0;

                        for(int k4 = k3; k4 < i && j4 > 0; --j4) {
                            if (k4 >= 1) {
                                int i2 = blockposition.getY() + k4;
                                l2 += enumdirection1.getAdjacentX();
                                i1 += enumdirection1.getAdjacentZ();
                                BlockPosition blockposition2 = new BlockPosition(l2, i2, i1);
                                IBlockData iblockdata1 = generatoraccess.getType(blockposition2);
                                if (iblockdata1.isAir() || iblockdata1.a(TagsBlock.LEAVES)) {
                                    this.a(set, generatoraccess, blockposition2);
                                    j1 = i2;
                                }
                            }

                            ++k4;
                        }

                        if (j1 > 0) {
                            BlockPosition blockposition4 = new BlockPosition(l2, j1, i1);

                            for(int l4 = -2; l4 <= 2; ++l4) {
                                for(int j5 = -2; j5 <= 2; ++j5) {
                                    if (Math.abs(l4) != 2 || Math.abs(j5) != 2) {
                                        this.b(generatoraccess, blockposition4.a(l4, 0, j5));
                                    }
                                }
                            }

                            blockposition4 = blockposition4.up();

                            for(int i5 = -1; i5 <= 1; ++i5) {
                                for(int k5 = -1; k5 <= 1; ++k5) {
                                    this.b(generatoraccess, blockposition4.a(i5, 0, k5));
                                }
                            }
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

    private void a(Set<BlockPosition> set, GeneratorAccess generatoraccess, BlockPosition blockposition) {
        this.a(set, generatoraccess, blockposition, a);
    }

    private void b(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        IBlockData iblockdata = generatoraccess.getType(blockposition);
        if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES)) {
            this.a(generatoraccess, blockposition, b);
        }

    }
}
