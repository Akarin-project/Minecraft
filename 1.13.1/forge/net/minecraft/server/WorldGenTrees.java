package net.minecraft.server;

import java.util.Random;
import java.util.Set;

public class WorldGenTrees extends WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> {
    private static final IBlockData b = Blocks.OAK_LOG.getBlockData();
    private static final IBlockData c = Blocks.OAK_LEAVES.getBlockData();
    protected final int a;
    private final boolean d;
    private final IBlockData aH;
    private final IBlockData aI;

    public WorldGenTrees(boolean flag) {
        this(flag, 4, b, c, false);
    }

    public WorldGenTrees(boolean flag, int i, IBlockData iblockdata, IBlockData iblockdata1, boolean flag1) {
        super(flag);
        this.a = i;
        this.aH = iblockdata;
        this.aI = iblockdata1;
        this.d = flag1;
    }

    public boolean a(Set<BlockPosition> set, GeneratorAccess generatoraccess, Random random, BlockPosition blockposition) {
        int i = this.a(random);
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
                    boolean flag1 = true;
                    boolean flag2 = false;

                    for(int j2 = blockposition.getY() - 3 + i; j2 <= blockposition.getY() + i; ++j2) {
                        int j3 = j2 - (blockposition.getY() + i);
                        int i1 = 1 - j3 / 2;

                        for(int j1 = blockposition.getX() - i1; j1 <= blockposition.getX() + i1; ++j1) {
                            int k1 = j1 - blockposition.getX();

                            for(int l1 = blockposition.getZ() - i1; l1 <= blockposition.getZ() + i1; ++l1) {
                                int i2 = l1 - blockposition.getZ();
                                if (Math.abs(k1) != i1 || Math.abs(i2) != i1 || random.nextInt(2) != 0 && j3 != 0) {
                                    BlockPosition blockposition1 = new BlockPosition(j1, j2, l1);
                                    IBlockData iblockdata = generatoraccess.getType(blockposition1);
                                    Material material = iblockdata.getMaterial();
                                    if (iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES) || material == Material.REPLACEABLE_PLANT) {
                                        this.a(generatoraccess, blockposition1, this.aI);
                                    }
                                }
                            }
                        }
                    }

                    for(int k2 = 0; k2 < i; ++k2) {
                        IBlockData iblockdata1 = generatoraccess.getType(blockposition.up(k2));
                        Material material1 = iblockdata1.getMaterial();
                        if (iblockdata1.isAir() || iblockdata1.a(TagsBlock.LEAVES) || material1 == Material.REPLACEABLE_PLANT) {
                            this.a(set, generatoraccess, blockposition.up(k2), this.aH);
                            if (this.d && k2 > 0) {
                                if (random.nextInt(3) > 0 && generatoraccess.isEmpty(blockposition.a(-1, k2, 0))) {
                                    this.a(generatoraccess, blockposition.a(-1, k2, 0), BlockVine.EAST);
                                }

                                if (random.nextInt(3) > 0 && generatoraccess.isEmpty(blockposition.a(1, k2, 0))) {
                                    this.a(generatoraccess, blockposition.a(1, k2, 0), BlockVine.WEST);
                                }

                                if (random.nextInt(3) > 0 && generatoraccess.isEmpty(blockposition.a(0, k2, -1))) {
                                    this.a(generatoraccess, blockposition.a(0, k2, -1), BlockVine.SOUTH);
                                }

                                if (random.nextInt(3) > 0 && generatoraccess.isEmpty(blockposition.a(0, k2, 1))) {
                                    this.a(generatoraccess, blockposition.a(0, k2, 1), BlockVine.NORTH);
                                }
                            }
                        }
                    }

                    if (this.d) {
                        for(int l2 = blockposition.getY() - 3 + i; l2 <= blockposition.getY() + i; ++l2) {
                            int k3 = l2 - (blockposition.getY() + i);
                            int l3 = 2 - k3 / 2;
                            BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

                            for(int i4 = blockposition.getX() - l3; i4 <= blockposition.getX() + l3; ++i4) {
                                for(int j4 = blockposition.getZ() - l3; j4 <= blockposition.getZ() + l3; ++j4) {
                                    blockposition$mutableblockposition1.c(i4, l2, j4);
                                    if (generatoraccess.getType(blockposition$mutableblockposition1).a(TagsBlock.LEAVES)) {
                                        BlockPosition blockposition2 = blockposition$mutableblockposition1.west();
                                        BlockPosition blockposition3 = blockposition$mutableblockposition1.east();
                                        BlockPosition blockposition4 = blockposition$mutableblockposition1.north();
                                        BlockPosition blockposition5 = blockposition$mutableblockposition1.south();
                                        if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition2).isAir()) {
                                            this.b(generatoraccess, blockposition2, BlockVine.EAST);
                                        }

                                        if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition3).isAir()) {
                                            this.b(generatoraccess, blockposition3, BlockVine.WEST);
                                        }

                                        if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition4).isAir()) {
                                            this.b(generatoraccess, blockposition4, BlockVine.SOUTH);
                                        }

                                        if (random.nextInt(4) == 0 && generatoraccess.getType(blockposition5).isAir()) {
                                            this.b(generatoraccess, blockposition5, BlockVine.NORTH);
                                        }
                                    }
                                }
                            }
                        }

                        if (random.nextInt(5) == 0 && i > 5) {
                            for(int i3 = 0; i3 < 2; ++i3) {
                                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                                    if (random.nextInt(4 - i3) == 0) {
                                        EnumDirection enumdirection1 = enumdirection.opposite();
                                        this.a(generatoraccess, random.nextInt(3), blockposition.a(enumdirection1.getAdjacentX(), i - 5 + i3, enumdirection1.getAdjacentZ()), enumdirection);
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

    protected int a(Random random) {
        return this.a + random.nextInt(3);
    }

    private void a(GeneratorAccess generatoraccess, int i, BlockPosition blockposition, EnumDirection enumdirection) {
        this.a(generatoraccess, blockposition, (IBlockData)((IBlockData)Blocks.COCOA.getBlockData().set(BlockCocoa.AGE, Integer.valueOf(i))).set(BlockCocoa.FACING, enumdirection));
    }

    private void a(GeneratorAccess generatoraccess, BlockPosition blockposition, BlockStateBoolean blockstateboolean) {
        this.a(generatoraccess, blockposition, (IBlockData)Blocks.VINE.getBlockData().set(blockstateboolean, Boolean.valueOf(true)));
    }

    private void b(GeneratorAccess generatoraccess, BlockPosition blockposition, BlockStateBoolean blockstateboolean) {
        this.a(generatoraccess, blockposition, blockstateboolean);
        int i = 4;

        for(BlockPosition blockposition1 = blockposition.down(); generatoraccess.getType(blockposition1).isAir() && i > 0; --i) {
            this.a(generatoraccess, blockposition1, blockstateboolean);
            blockposition1 = blockposition1.down();
        }

    }
}
