package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockTrapdoor extends BlockFacingHorizontal implements IFluidSource, IFluidContainer {
    public static final BlockStateBoolean OPEN = BlockProperties.r;
    public static final BlockStateEnum<BlockPropertyHalf> HALF = BlockProperties.Q;
    public static final BlockStateBoolean c = BlockProperties.t;
    public static final BlockStateBoolean o = BlockProperties.y;
    protected static final VoxelShape p = Block.a(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    protected static final VoxelShape q = Block.a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape r = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape s = Block.a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape t = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape u = Block.a(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    protected BlockTrapdoor(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(OPEN, Boolean.valueOf(false))).set(HALF, BlockPropertyHalf.BOTTOM)).set(c, Boolean.valueOf(false))).set(o, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        if (!iblockdata.get(OPEN)) {
            return iblockdata.get(HALF) == BlockPropertyHalf.TOP ? u : t;
        } else {
            switch((EnumDirection)iblockdata.get(FACING)) {
            case NORTH:
            default:
                return s;
            case SOUTH:
                return r;
            case WEST:
                return q;
            case EAST:
                return p;
            }
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, PathMode pathmode) {
        switch(pathmode) {
        case LAND:
            return iblockdata.get(OPEN);
        case WATER:
            return iblockdata.get(o);
        case AIR:
            return iblockdata.get(OPEN);
        default:
            return false;
        }
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (this.material == Material.ORE) {
            return false;
        } else {
            iblockdata = (IBlockData)iblockdata.a(OPEN);
            world.setTypeAndData(blockposition, iblockdata, 2);
            if (iblockdata.get(o)) {
                world.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(world));
            }

            this.a(entityhuman, world, blockposition, iblockdata.get(OPEN));
            return true;
        }
    }

    protected void a(@Nullable EntityHuman entityhuman, World world, BlockPosition blockposition, boolean flag) {
        if (flag) {
            int i = this.material == Material.ORE ? 1037 : 1007;
            world.a(entityhuman, i, blockposition, 0);
        } else {
            int j = this.material == Material.ORE ? 1036 : 1013;
            world.a(entityhuman, j, blockposition, 0);
        }

    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            boolean flag = world.isBlockIndirectlyPowered(blockposition);
            if (flag != iblockdata.get(c)) {
                if (iblockdata.get(OPEN) != flag) {
                    iblockdata = (IBlockData)iblockdata.set(OPEN, Boolean.valueOf(flag));
                    this.a((EntityHuman)null, world, blockposition, flag);
                }

                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(c, Boolean.valueOf(flag)), 2);
                if (iblockdata.get(o)) {
                    world.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(world));
                }
            }

        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = this.getBlockData();
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        EnumDirection enumdirection = blockactioncontext.getClickedFace();
        if (!blockactioncontext.c() && enumdirection.k().c()) {
            iblockdata = (IBlockData)((IBlockData)iblockdata.set(FACING, enumdirection)).set(HALF, blockactioncontext.n() > 0.5F ? BlockPropertyHalf.TOP : BlockPropertyHalf.BOTTOM);
        } else {
            iblockdata = (IBlockData)((IBlockData)iblockdata.set(FACING, blockactioncontext.f().opposite())).set(HALF, enumdirection == EnumDirection.UP ? BlockPropertyHalf.BOTTOM : BlockPropertyHalf.TOP);
        }

        if (blockactioncontext.getWorld().isBlockIndirectlyPowered(blockactioncontext.getClickPosition())) {
            iblockdata = (IBlockData)((IBlockData)iblockdata.set(OPEN, Boolean.valueOf(true))).set(c, Boolean.valueOf(true));
        }

        return (IBlockData)iblockdata.set(o, Boolean.valueOf(fluid.c() == FluidTypes.c));
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, OPEN, HALF, c, o);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData iblockdata, BlockPosition var3, EnumDirection enumdirection) {
        return (enumdirection == EnumDirection.UP && iblockdata.get(HALF) == BlockPropertyHalf.TOP || enumdirection == EnumDirection.DOWN && iblockdata.get(HALF) == BlockPropertyHalf.BOTTOM) && !iblockdata.get(OPEN) ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(o)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(o, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(o) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(o) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(o) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(o, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(o)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }
}
