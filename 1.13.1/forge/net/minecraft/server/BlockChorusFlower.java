package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockChorusFlower extends Block {
    public static final BlockStateInteger AGE = BlockProperties.V;
    private final BlockChorusFruit b;

    protected BlockChorusFlower(BlockChorusFruit blockchorusfruit, Block.Info block$info) {
        super(block$info);
        this.b = blockchorusfruit;
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(AGE, Integer.valueOf(0)));
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (!iblockdata.canPlace(world, blockposition)) {
            world.setAir(blockposition, true);
        } else {
            BlockPosition blockposition1 = blockposition.up();
            if (world.isEmpty(blockposition1) && blockposition1.getY() < 256) {
                int i = iblockdata.get(AGE);
                if (i < 5) {
                    boolean flag = false;
                    boolean flag1 = false;
                    IBlockData iblockdata1 = world.getType(blockposition.down());
                    Block block = iblockdata1.getBlock();
                    if (block == Blocks.END_STONE) {
                        flag = true;
                    } else if (block == this.b) {
                        int j = 1;

                        for(int k = 0; k < 4; ++k) {
                            Block block1 = world.getType(blockposition.down(j + 1)).getBlock();
                            if (block1 != this.b) {
                                if (block1 == Blocks.END_STONE) {
                                    flag1 = true;
                                }
                                break;
                            }

                            ++j;
                        }

                        if (j < 2 || j <= random.nextInt(flag1 ? 5 : 4)) {
                            flag = true;
                        }
                    } else if (iblockdata1.isAir()) {
                        flag = true;
                    }

                    if (flag && a(world, blockposition1, (EnumDirection)null) && world.isEmpty(blockposition.up(2))) {
                        world.setTypeAndData(blockposition, this.b.a(world, blockposition), 2);
                        this.b(world, blockposition1, i);
                    } else if (i < 4) {
                        int l = random.nextInt(4);
                        if (flag1) {
                            ++l;
                        }

                        boolean flag2 = false;

                        for(int i1 = 0; i1 < l; ++i1) {
                            EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
                            BlockPosition blockposition2 = blockposition.shift(enumdirection);
                            if (world.isEmpty(blockposition2) && world.isEmpty(blockposition2.down()) && a(world, blockposition2, enumdirection.opposite())) {
                                this.b(world, blockposition2, i + 1);
                                flag2 = true;
                            }
                        }

                        if (flag2) {
                            world.setTypeAndData(blockposition, this.b.a(world, blockposition), 2);
                        } else {
                            this.a(world, blockposition);
                        }
                    } else {
                        this.a(world, blockposition);
                    }

                }
            }
        }
    }

    private void b(World world, BlockPosition blockposition, int i) {
        world.setTypeAndData(blockposition, (IBlockData)this.getBlockData().set(AGE, Integer.valueOf(i)), 2);
        world.triggerEffect(1033, blockposition, 0);
    }

    private void a(World world, BlockPosition blockposition) {
        world.setTypeAndData(blockposition, (IBlockData)this.getBlockData().set(AGE, Integer.valueOf(5)), 2);
        world.triggerEffect(1034, blockposition, 0);
    }

    private static boolean a(IWorldReader iworldreader, BlockPosition blockposition, @Nullable EnumDirection enumdirection) {
        for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            if (enumdirection1 != enumdirection && !iworldreader.isEmpty(blockposition.shift(enumdirection1))) {
                return false;
            }
        }

        return true;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection != EnumDirection.UP && !iblockdata.canPlace(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 1);
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        Block block = iblockdata.getBlock();
        if (block != this.b && block != Blocks.END_STONE) {
            if (!iblockdata.isAir()) {
                return false;
            } else {
                boolean flag = false;

                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    IBlockData iblockdata1 = iworldreader.getType(blockposition.shift(enumdirection));
                    if (iblockdata1.getBlock() == this.b) {
                        if (flag) {
                            return false;
                        }

                        flag = true;
                    } else if (!iblockdata1.isAir()) {
                        return false;
                    }
                }

                return flag;
            }
        } else {
            return true;
        }
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        a(world, blockposition, new ItemStack(this));
    }

    protected ItemStack t(IBlockData var1) {
        return ItemStack.a;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(AGE);
    }

    public static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, Random random, int i) {
        generatoraccess.setTypeAndData(blockposition, ((BlockChorusFruit)Blocks.CHORUS_PLANT).a(generatoraccess, blockposition), 2);
        a(generatoraccess, blockposition, random, blockposition, i, 0);
    }

    private static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, Random random, BlockPosition blockposition1, int i, int j) {
        BlockChorusFruit blockchorusfruit = (BlockChorusFruit)Blocks.CHORUS_PLANT;
        int k = random.nextInt(4) + 1;
        if (j == 0) {
            ++k;
        }

        for(int l = 0; l < k; ++l) {
            BlockPosition blockposition2 = blockposition.up(l + 1);
            if (!a(generatoraccess, blockposition2, (EnumDirection)null)) {
                return;
            }

            generatoraccess.setTypeAndData(blockposition2, blockchorusfruit.a(generatoraccess, blockposition2), 2);
            generatoraccess.setTypeAndData(blockposition2.down(), blockchorusfruit.a(generatoraccess, blockposition2.down()), 2);
        }

        boolean flag = false;
        if (j < 4) {
            int j1 = random.nextInt(4);
            if (j == 0) {
                ++j1;
            }

            for(int i1 = 0; i1 < j1; ++i1) {
                EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
                BlockPosition blockposition3 = blockposition.up(k).shift(enumdirection);
                if (Math.abs(blockposition3.getX() - blockposition1.getX()) < i && Math.abs(blockposition3.getZ() - blockposition1.getZ()) < i && generatoraccess.isEmpty(blockposition3) && generatoraccess.isEmpty(blockposition3.down()) && a(generatoraccess, blockposition3, enumdirection.opposite())) {
                    flag = true;
                    generatoraccess.setTypeAndData(blockposition3, blockchorusfruit.a(generatoraccess, blockposition3), 2);
                    generatoraccess.setTypeAndData(blockposition3.shift(enumdirection.opposite()), blockchorusfruit.a(generatoraccess, blockposition3.shift(enumdirection.opposite())), 2);
                    a(generatoraccess, blockposition3, random, blockposition1, i, j + 1);
                }
            }
        }

        if (!flag) {
            generatoraccess.setTypeAndData(blockposition.up(k), (IBlockData)Blocks.CHORUS_FLOWER.getBlockData().set(AGE, Integer.valueOf(5)), 2);
        }

    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
