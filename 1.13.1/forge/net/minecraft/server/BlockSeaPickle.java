package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockSeaPickle extends BlockPlant implements IBlockFragilePlantElement, IFluidSource, IFluidContainer {
    public static final BlockStateInteger a = BlockProperties.ak;
    public static final BlockStateBoolean b = BlockProperties.y;
    protected static final VoxelShape c = Block.a(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    protected static final VoxelShape o = Block.a(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    protected static final VoxelShape p = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
    protected static final VoxelShape q = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);

    protected BlockSeaPickle(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, Integer.valueOf(1))).set(b, Boolean.valueOf(true)));
    }

    public int m(IBlockData iblockdata) {
        return this.k(iblockdata) ? 0 : super.m(iblockdata) + 3 * iblockdata.get(a);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        if (iblockdata.getBlock() == this) {
            return (IBlockData)iblockdata.set(a, Integer.valueOf(Math.min(4, iblockdata.get(a) + 1)));
        } else {
            Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
            boolean flag = fluid.a(TagsFluid.WATER) && fluid.g() == 8;
            return (IBlockData)super.getPlacedState(blockactioncontext).set(b, Boolean.valueOf(flag));
        }
    }

    private boolean k(IBlockData iblockdata) {
        return !iblockdata.get(b);
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return !iblockdata.h(iblockaccess, blockposition).a(EnumDirection.UP).b();
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();
        return this.b(iworldreader.getType(blockposition1), iworldreader, blockposition1);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            if (iblockdata.get(b)) {
                generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            }

            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        return blockactioncontext.getItemStack().getItem() == this.getItem() && iblockdata.get(a) < 4 ? true : super.a(iblockdata, blockactioncontext);
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch(iblockdata.get(a)) {
        case 1:
        default:
            return c;
        case 2:
            return o;
        case 3:
            return p;
        case 4:
            return q;
        }
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(b)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(b) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(b) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(b) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    public int a(IBlockData iblockdata, Random var2) {
        return iblockdata.get(a);
    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, boolean var4) {
        return true;
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.k(iblockdata) && world.getType(blockposition.down()).a(TagsBlock.CORAL_BLOCKS)) {
            boolean flag = true;
            int i = 1;
            boolean flag1 = true;
            int j = 0;
            int k = blockposition.getX() - 2;
            int l = 0;

            for(int i1 = 0; i1 < 5; ++i1) {
                for(int j1 = 0; j1 < i; ++j1) {
                    int k1 = 2 + blockposition.getY() - 1;

                    for(int l1 = k1 - 2; l1 < k1; ++l1) {
                        BlockPosition blockposition1 = new BlockPosition(k + i1, l1, blockposition.getZ() - l + j1);
                        if (blockposition1 != blockposition && random.nextInt(6) == 0 && world.getType(blockposition1).getBlock() == Blocks.WATER) {
                            IBlockData iblockdata1 = world.getType(blockposition1.down());
                            if (iblockdata1.a(TagsBlock.CORAL_BLOCKS)) {
                                world.setTypeAndData(blockposition1, (IBlockData)Blocks.SEA_PICKLE.getBlockData().set(a, Integer.valueOf(random.nextInt(4) + 1)), 3);
                            }
                        }
                    }
                }

                if (j < 2) {
                    i += 2;
                    ++l;
                } else {
                    i -= 2;
                    --l;
                }

                ++j;
            }

            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Integer.valueOf(4)), 2);
        }

    }
}
