package net.minecraft.server;

public class BlockFenceGate extends BlockFacingHorizontal {
    public static final BlockStateBoolean OPEN = BlockProperties.r;
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    public static final BlockStateBoolean IN_WALL = BlockProperties.n;
    protected static final VoxelShape o = Block.a(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape p = Block.a(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape q = Block.a(0.0D, 0.0D, 6.0D, 16.0D, 13.0D, 10.0D);
    protected static final VoxelShape r = Block.a(6.0D, 0.0D, 0.0D, 10.0D, 13.0D, 16.0D);
    protected static final VoxelShape s = Block.a(0.0D, 0.0D, 6.0D, 16.0D, 24.0D, 10.0D);
    protected static final VoxelShape t = Block.a(6.0D, 0.0D, 0.0D, 10.0D, 24.0D, 16.0D);
    protected static final VoxelShape u = VoxelShapes.a(Block.a(0.0D, 5.0D, 7.0D, 2.0D, 16.0D, 9.0D), Block.a(14.0D, 5.0D, 7.0D, 16.0D, 16.0D, 9.0D));
    protected static final VoxelShape v = VoxelShapes.a(Block.a(7.0D, 5.0D, 0.0D, 9.0D, 16.0D, 2.0D), Block.a(7.0D, 5.0D, 14.0D, 9.0D, 16.0D, 16.0D));
    protected static final VoxelShape w = VoxelShapes.a(Block.a(0.0D, 2.0D, 7.0D, 2.0D, 13.0D, 9.0D), Block.a(14.0D, 2.0D, 7.0D, 16.0D, 13.0D, 9.0D));
    protected static final VoxelShape x = VoxelShapes.a(Block.a(7.0D, 2.0D, 0.0D, 9.0D, 13.0D, 2.0D), Block.a(7.0D, 2.0D, 14.0D, 9.0D, 13.0D, 16.0D));

    public BlockFenceGate(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(OPEN, Boolean.valueOf(false))).set(POWERED, Boolean.valueOf(false))).set(IN_WALL, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        if (iblockdata.get(IN_WALL)) {
            return ((EnumDirection)iblockdata.get(FACING)).k() == EnumDirection.EnumAxis.X ? r : q;
        } else {
            return ((EnumDirection)iblockdata.get(FACING)).k() == EnumDirection.EnumAxis.X ? p : o;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
        if (((EnumDirection)iblockdata.get(FACING)).e().k() != enumdirection$enumaxis) {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            boolean flag = this.k(iblockdata1) || this.k(generatoraccess.getType(blockposition.shift(enumdirection.opposite())));
            return (IBlockData)iblockdata.set(IN_WALL, Boolean.valueOf(flag));
        }
    }

    public VoxelShape f(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        if (iblockdata.get(OPEN)) {
            return VoxelShapes.a();
        } else {
            return ((EnumDirection)iblockdata.get(FACING)).k() == EnumDirection.EnumAxis.Z ? s : t;
        }
    }

    public VoxelShape g(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        if (iblockdata.get(IN_WALL)) {
            return ((EnumDirection)iblockdata.get(FACING)).k() == EnumDirection.EnumAxis.X ? x : w;
        } else {
            return ((EnumDirection)iblockdata.get(FACING)).k() == EnumDirection.EnumAxis.X ? v : u;
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
            return false;
        case AIR:
            return iblockdata.get(OPEN);
        default:
            return false;
        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        boolean flag = world.isBlockIndirectlyPowered(blockposition);
        EnumDirection enumdirection = blockactioncontext.f();
        EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
        boolean flag1 = enumdirection$enumaxis == EnumDirection.EnumAxis.Z && (this.k(world.getType(blockposition.west())) || this.k(world.getType(blockposition.east()))) || enumdirection$enumaxis == EnumDirection.EnumAxis.X && (this.k(world.getType(blockposition.north())) || this.k(world.getType(blockposition.south())));
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(FACING, enumdirection)).set(OPEN, Boolean.valueOf(flag))).set(POWERED, Boolean.valueOf(flag))).set(IN_WALL, Boolean.valueOf(flag1));
    }

    private boolean k(IBlockData iblockdata) {
        return iblockdata.getBlock() == Blocks.COBBLESTONE_WALL || iblockdata.getBlock() == Blocks.MOSSY_COBBLESTONE_WALL;
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (iblockdata.get(OPEN)) {
            iblockdata = (IBlockData)iblockdata.set(OPEN, Boolean.valueOf(false));
            world.setTypeAndData(blockposition, iblockdata, 10);
        } else {
            EnumDirection enumdirection = entityhuman.getDirection();
            if (iblockdata.get(FACING) == enumdirection.opposite()) {
                iblockdata = (IBlockData)iblockdata.set(FACING, enumdirection);
            }

            iblockdata = (IBlockData)iblockdata.set(OPEN, Boolean.valueOf(true));
            world.setTypeAndData(blockposition, iblockdata, 10);
        }

        world.a(entityhuman, iblockdata.get(OPEN) ? 1008 : 1014, blockposition, 0);
        return true;
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            boolean flag = world.isBlockIndirectlyPowered(blockposition);
            if (iblockdata.get(POWERED) != flag) {
                world.setTypeAndData(blockposition, (IBlockData)((IBlockData)iblockdata.set(POWERED, Boolean.valueOf(flag))).set(OPEN, Boolean.valueOf(flag)), 2);
                if (iblockdata.get(OPEN) != flag) {
                    world.a((EntityHuman)null, flag ? 1008 : 1014, blockposition, 0);
                }
            }

        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, OPEN, POWERED, IN_WALL);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData iblockdata, BlockPosition var3, EnumDirection enumdirection) {
        if (enumdirection != EnumDirection.UP && enumdirection != EnumDirection.DOWN) {
            return ((EnumDirection)iblockdata.get(FACING)).k() == enumdirection.e().k() ? EnumBlockFaceShape.MIDDLE_POLE : EnumBlockFaceShape.UNDEFINED;
        } else {
            return EnumBlockFaceShape.UNDEFINED;
        }
    }
}
