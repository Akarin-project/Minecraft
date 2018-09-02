package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenSwampTree extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData a = Blocks.OAK_LOG.getBlockData();
    private static final IBlockData b = Blocks.OAK_LEAVES.getBlockData();

    public WorldGenSwampTree() {
        super(false);
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i;
        for(i = random.nextInt(4) + 5; generatoraccess.b(blockposition.down()).a(TagsFluid.WATER); blockposition = blockposition.down()) {
            ;
        }

        boolean flag = true;
        if (blockposition.getY() >= 1 && blockposition.getY() + i + 1 <= 256) {
            for(int j = blockposition.getY(); j <= blockposition.getY() + 1 + i; ++j) {
                byte b0 = 1;
                if (j == blockposition.getY()) {
                    b0 = 0;
                }

                if (j >= blockposition.getY() + 1 + i - 2) {
                    b0 = 3;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for(int k = blockposition.getX() - b0; k <= blockposition.getX() + b0 && flag; ++k) {
                    for(int l = blockposition.getZ() - b0; l <= blockposition.getZ() + b0 && flag; ++l) {
                        if (j >= 0 && j < 256) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition.c(k, j, l));
                            Block block = iblockdata.getBlock();
                            if (!iblockdata.isAir() && !iblockdata.a(TagsBlock.LEAVES)) {
                                if (block == Blocks.WATER) {
                                    if (j > blockposition.getY()) {
                                        flag = false;
                                    }
                                } else {
                                    flag = false;
                                }
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
                Block block1 = generatoraccess.getType(blockposition.down()).getBlock();
                if ((block1 == Blocks.GRASS_BLOCK || Block.d(block1)) && blockposition.getY() < 256 - i - 1) {
                    this.a(generatoraccess, blockposition.down());

                    for(int j1 = blockposition.getY() - 3 + i; j1 <= blockposition.getY() + i; ++j1) {
                        int i2 = j1 - (blockposition.getY() + i);
                        int k2 = 2 - i2 / 2;

                        for(int i3 = blockposition.getX() - k2; i3 <= blockposition.getX() + k2; ++i3) {
                            int j3 = i3 - blockposition.getX();

                            for(int l3 = blockposition.getZ() - k2; l3 <= blockposition.getZ() + k2; ++l3) {
                                int i1 = l3 - blockposition.getZ();
                                if (Math.abs(j3) != k2 || Math.abs(i1) != k2 || random.nextInt(2) != 0 && i2 != 0) {
                                    BlockPosition blockposition1 = new BlockPosition(i3, j1, l3);
                                    if (!generatoraccess.getType(blockposition1).f(generatoraccess, blockposition1)) {
                                        this.a(generatoraccess, blockposition1, b);
                                    }
                                }
                            }
                        }
                    }

                    for(int k1 = 0; k1 < i; ++k1) {
                        IBlockData iblockdata1 = generatoraccess.getType(blockposition.up(k1));
                        Block block2 = iblockdata1.getBlock();
                        if (iblockdata1.isAir() || iblockdata1.a(TagsBlock.LEAVES) || block2 == Blocks.WATER) {
                            this.a(set, generatoraccess, blockposition.up(k1), a);
                        }
                    }

                    for(int l1 = blockposition.getY() - 3 + i; l1 <= blockposition.getY() + i; ++l1) {
                        int j2 = l1 - (blockposition.getY() + i);
                        int l2 = 2 - j2 / 2;
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

                        for(int k3 = blockposition.getX() - l2; k3 <= blockposition.getX() + l2; ++k3) {
                            for(int i4 = blockposition.getZ() - l2; i4 <= blockposition.getZ() + l2; ++i4) {
                                blockposition$mutableblockposition1.c(k3, l1, i4);
                                if (generatoraccess.getType(blockposition$mutableblockposition1).a(TagsBlock.LEAVES)) {
                                    BlockPosition blockposition4 = blockposition$mutableblockposition1.west();
                                    BlockPosition blockposition5 = blockposition$mutableblockposition1.east();
                                    BlockPosition blockposition2 = blockposition$mutableblockposition1.north();
                                    BlockPosition blockposition3 = blockposition$mutableblockposition1.south();
                                    if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition4).isAir()) {
                                        this.a(generatoraccess, blockposition4, BlockVine.EAST);
                                    }

                                    if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition5).isAir()) {
                                        this.a(generatoraccess, blockposition5, BlockVine.WEST);
                                    }

                                    if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition2).isAir()) {
                                        this.a(generatoraccess, blockposition2, BlockVine.SOUTH);
                                    }

                                    if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition3).isAir()) {
                                        this.a(generatoraccess, blockposition3, BlockVine.NORTH);
                                    }
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

    private void a(GeneratorAccess generatoraccess, BlockPosition blockposition, BlockStateBoolean blockstateboolean) {
        IBlockData iblockdata = (IBlockData)Blocks.VINE.getBlockData().set(blockstateboolean, Boolean.valueOf(true));
        this.a(generatoraccess, blockposition, iblockdata);
        int i = 4;

        for(BlockPosition blockposition1 = blockposition.down(); generatoraccess.getType(blockposition1).isAir() && i > 0; --i) {
            this.a(generatoraccess, blockposition1, iblockdata);
            blockposition1 = blockposition1.down();
        }

    }
}
