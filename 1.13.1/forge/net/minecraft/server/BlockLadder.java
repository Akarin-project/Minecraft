package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockLadder extends Block implements IFluidSource, IFluidContainer {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean b = BlockProperties.y;
    protected static final VoxelShape c = Block.a(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    protected static final VoxelShape o = Block.a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape p = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape q = Block.a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);

    protected BlockLadder(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(b, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((EnumDirection)iblockdata.get(FACING)) {
        case NORTH:
            return q;
        case SOUTH:
            return p;
        case WEST:
            return o;
        case EAST:
        default:
            return c;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        boolean flag = b(iblockdata.getBlock());
        return !flag && iblockdata.c(iblockaccess, blockposition, enumdirection) == EnumBlockFaceShape.SOLID && !iblockdata.isPowerSource();
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        return this.a(iworldreader, blockposition.shift(enumdirection.opposite()), enumdirection);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection.opposite() == iblockdata.get(FACING) && !iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            if (iblockdata.get(b)) {
                generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            }

            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        if (!blockactioncontext.c()) {
            IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition().shift(blockactioncontext.getClickedFace().opposite()));
            if (iblockdata.getBlock() == this && iblockdata.get(FACING) == blockactioncontext.getClickedFace()) {
                return null;
            }
        }

        IBlockData iblockdata1 = this.getBlockData();
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());

        for(EnumDirection enumdirection : blockactioncontext.e()) {
            if (enumdirection.k().c()) {
                iblockdata1 = (IBlockData)iblockdata1.set(FACING, enumdirection.opposite());
                if (iblockdata1.canPlace(world, blockposition)) {
                    return (IBlockData)iblockdata1.set(b, Boolean.valueOf(fluid.c() == FluidTypes.c));
                }
            }
        }

        return null;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, b);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
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
}
