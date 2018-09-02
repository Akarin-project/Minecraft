package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockStepAbstract extends Block implements IFluidSource, IFluidContainer {
    public static final BlockStateEnum<BlockPropertySlabType> a = BlockProperties.au;
    public static final BlockStateBoolean b = BlockProperties.y;
    protected static final VoxelShape c = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape o = Block.a(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BlockStepAbstract(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)this.getBlockData().set(a, BlockPropertySlabType.BOTTOM)).set(b, Boolean.valueOf(false)));
    }

    public int j(IBlockData var1, IBlockAccess iblockaccess, BlockPosition var3) {
        return iblockaccess.K();
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    protected boolean X_() {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        BlockPropertySlabType blockpropertyslabtype = (BlockPropertySlabType)iblockdata.get(a);
        switch(blockpropertyslabtype) {
        case DOUBLE:
            return VoxelShapes.b();
        case TOP:
            return o;
        default:
            return c;
        }
    }

    public boolean r(IBlockData iblockdata) {
        return iblockdata.get(a) == BlockPropertySlabType.DOUBLE || iblockdata.get(a) == BlockPropertySlabType.TOP;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData iblockdata, BlockPosition var3, EnumDirection enumdirection) {
        BlockPropertySlabType blockpropertyslabtype = (BlockPropertySlabType)iblockdata.get(a);
        if (blockpropertyslabtype == BlockPropertySlabType.DOUBLE) {
            return EnumBlockFaceShape.SOLID;
        } else if (enumdirection == EnumDirection.UP && blockpropertyslabtype == BlockPropertySlabType.TOP) {
            return EnumBlockFaceShape.SOLID;
        } else {
            return enumdirection == EnumDirection.DOWN && blockpropertyslabtype == BlockPropertySlabType.BOTTOM ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        if (iblockdata.getBlock() == this) {
            return (IBlockData)((IBlockData)iblockdata.set(a, BlockPropertySlabType.DOUBLE)).set(b, Boolean.valueOf(false));
        } else {
            Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
            IBlockData iblockdata1 = (IBlockData)((IBlockData)this.getBlockData().set(a, BlockPropertySlabType.BOTTOM)).set(b, Boolean.valueOf(fluid.c() == FluidTypes.c));
            EnumDirection enumdirection = blockactioncontext.getClickedFace();
            return enumdirection != EnumDirection.DOWN && (enumdirection == EnumDirection.UP || !((double)blockactioncontext.n() > 0.5D)) ? iblockdata1 : (IBlockData)iblockdata1.set(a, BlockPropertySlabType.TOP);
        }
    }

    public int a(IBlockData iblockdata, Random var2) {
        return iblockdata.get(a) == BlockPropertySlabType.DOUBLE ? 2 : 1;
    }

    public boolean a(IBlockData iblockdata) {
        return iblockdata.get(a) == BlockPropertySlabType.DOUBLE;
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        ItemStack itemstack = blockactioncontext.getItemStack();
        BlockPropertySlabType blockpropertyslabtype = (BlockPropertySlabType)iblockdata.get(a);
        if (blockpropertyslabtype != BlockPropertySlabType.DOUBLE && itemstack.getItem() == this.getItem()) {
            if (blockactioncontext.c()) {
                boolean flag = (double)blockactioncontext.n() > 0.5D;
                EnumDirection enumdirection = blockactioncontext.getClickedFace();
                if (blockpropertyslabtype == BlockPropertySlabType.BOTTOM) {
                    return enumdirection == EnumDirection.UP || flag && enumdirection.k().c();
                } else {
                    return enumdirection == EnumDirection.DOWN || !flag && enumdirection.k().c();
                }
            } else {
                return true;
            }
        } else {
            return false;
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
        return iblockdata.get(a) != BlockPropertySlabType.DOUBLE && !iblockdata.get(b) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (iblockdata.get(a) != BlockPropertySlabType.DOUBLE && !iblockdata.get(b) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(b)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
        switch(pathmode) {
        case LAND:
            return iblockdata.get(a) == BlockPropertySlabType.BOTTOM;
        case WATER:
            return iblockaccess.b(blockposition).a(TagsFluid.WATER);
        case AIR:
            return false;
        default:
            return false;
        }
    }
}
