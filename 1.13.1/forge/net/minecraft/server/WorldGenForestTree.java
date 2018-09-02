package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenForestTree extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.DARK_OAK_LOG.getBlockData();
    private static final IBlockData b = Blocks.DARK_OAK_LEAVES.getBlockData();

    public WorldGenForestTree(boolean flag) {
        super(flag);
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = random.nextInt(3) + random.nextInt(2) + 6;
        int j = blockposition.getX();
        int k = blockposition.getY();
        int l = blockposition.getZ();
        if (k >= 1 && k + i + 1 < 256) {
            BlockPosition blockposition1 = blockposition.down();
            Block block = generatoraccess.getType(blockposition1).getBlock();
            if (block != Blocks.GRASS_BLOCK && !Block.d(block)) {
                return false;
            } else if (!this.a(generatoraccess, blockposition, i)) {
                return false;
            } else {
                this.a(generatoraccess, blockposition1);
                this.a(generatoraccess, blockposition1.east());
                this.a(generatoraccess, blockposition1.south());
                this.a(generatoraccess, blockposition1.south().east());
                EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
                int i1 = i - random.nextInt(4);
                int j1 = 2 - random.nextInt(3);
                int k1 = j;
                int l1 = l;
                int i2 = k + i - 1;

                for(int j2 = 0; j2 < i; ++j2) {
                    if (j2 >= i1 && j1 > 0) {
                        k1 += enumdirection.getAdjacentX();
                        l1 += enumdirection.getAdjacentZ();
                        --j1;
                    }

                    int k2 = k + j2;
                    BlockPosition blockposition2 = new BlockPosition(k1, k2, l1);
                    IBlockData iblockdata = generatoraccess.getType(blockposition2);
                    if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES)) {
                        this.a(set, generatoraccess, blockposition2);
                        this.a(set, generatoraccess, blockposition2.east());
                        this.a(set, generatoraccess, blockposition2.south());
                        this.a(set, generatoraccess, blockposition2.east().south());
                    }
                }

                for(int i3 = -2; i3 <= 0; ++i3) {
                    for(int l3 = -2; l3 <= 0; ++l3) {
                        byte b0 = -1;
                        this.a(generatoraccess, k1 + i3, i2 + b0, l1 + l3);
                        this.a(generatoraccess, 1 + k1 - i3, i2 + b0, l1 + l3);
                        this.a(generatoraccess, k1 + i3, i2 + b0, 1 + l1 - l3);
                        this.a(generatoraccess, 1 + k1 - i3, i2 + b0, 1 + l1 - l3);
                        if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2)) {
                            b0 = 1;
                            this.a(generatoraccess, k1 + i3, i2 + b0, l1 + l3);
                            this.a(generatoraccess, 1 + k1 - i3, i2 + b0, l1 + l3);
                            this.a(generatoraccess, k1 + i3, i2 + b0, 1 + l1 - l3);
                            this.a(generatoraccess, 1 + k1 - i3, i2 + b0, 1 + l1 - l3);
                        }
                    }
                }

                if (random.nextBoolean()) {
                    this.a(generatoraccess, k1, i2 + 2, l1);
                    this.a(generatoraccess, k1 + 1, i2 + 2, l1);
                    this.a(generatoraccess, k1 + 1, i2 + 2, l1 + 1);
                    this.a(generatoraccess, k1, i2 + 2, l1 + 1);
                }

                for(int j3 = -3; j3 <= 4; ++j3) {
                    for(int i4 = -3; i4 <= 4; ++i4) {
                        if ((j3 != -3 || i4 != -3) && (j3 != -3 || i4 != 4) && (j3 != 4 || i4 != -3) && (j3 != 4 || i4 != 4) && (Math.abs(j3) < 3 || Math.abs(i4) < 3)) {
                            this.a(generatoraccess, k1 + j3, i2, l1 + i4);
                        }
                    }
                }

                for(int k3 = -1; k3 <= 2; ++k3) {
                    for(int j4 = -1; j4 <= 2; ++j4) {
                        if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && random.nextInt(3) <= 0) {
                            int k4 = random.nextInt(3) + 2;

                            for(int l4 = 0; l4 < k4; ++l4) {
                                this.a(set, generatoraccess, new BlockPosition(j + k3, i2 - l4 - 1, l + j4));
                            }

                            for(int i5 = -1; i5 <= 1; ++i5) {
                                for(int l2 = -1; l2 <= 1; ++l2) {
                                    this.a(generatoraccess, k1 + k3 + i5, i2, l1 + j4 + l2);
                                }
                            }

                            for(int j5 = -2; j5 <= 2; ++j5) {
                                for(int k5 = -2; k5 <= 2; ++k5) {
                                    if (Math.abs(j5) != 2 || Math.abs(k5) != 2) {
                                        this.a(generatoraccess, k1 + k3 + j5, i2 - 1, l1 + j4 + k5);
                                    }
                                }
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, int i) {
        int j = blockposition.getX();
        int k = blockposition.getY();
        int l = blockposition.getZ();
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int i1 = 0; i1 <= i + 1; ++i1) {
            byte b0 = 1;
            if (i1 == 0) {
                b0 = 0;
            }

            if (i1 >= i - 1) {
                b0 = 2;
            }

            for(int j1 = -b0; j1 <= b0; ++j1) {
                for(int k1 = -b0; k1 <= b0; ++k1) {
                    if (!this.a(iblockaccess.getType(blockposition$mutableblockposition.c(j + j1, k + i1, l + k1)).getBlock())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void a(Set<BlockPosition> set, GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (this.a(generatoraccess.getType(blockposition).getBlock())) {
            this.a(set, generatoraccess, blockposition, a);
        }

    }

    private void a(GeneratorAccess generatoraccess, int i, int j, int k) {
        BlockPosition blockposition = new BlockPosition(i, j, k);
        if (generatoraccess.getType(blockposition).isAir()) {
            this.a(generatoraccess, blockposition, b);
        }

    }
}
