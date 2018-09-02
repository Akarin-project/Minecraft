package net.minecraft.server;

public class BlockCobbleWall extends BlockTall {
    public static final BlockStateBoolean UP = BlockProperties.B;
    private final VoxelShape[] u;
    private final VoxelShape[] v;

    public BlockCobbleWall(Block.Info block$info) {
        super(0.0F, 3.0F, 0.0F, 14.0F, 24.0F, block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(UP, Boolean.valueOf(true))).set(NORTH, Boolean.valueOf(false))).set(EAST, Boolean.valueOf(false))).set(SOUTH, Boolean.valueOf(false))).set(WEST, Boolean.valueOf(false))).set(p, Boolean.valueOf(false)));
        this.u = this.a(4.0F, 3.0F, 16.0F, 0.0F, 14.0F);
        this.v = this.a(4.0F, 3.0F, 24.0F, 0.0F, 24.0F);
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.get(UP) ? this.u[this.k(iblockdata)] : super.a(iblockdata, iblockaccess, blockposition);
    }

    public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.get(UP) ? this.v[this.k(iblockdata)] : super.f(iblockdata, iblockaccess, blockposition);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }

    private boolean a(IBlockData iblockdata, EnumBlockFaceShape enumblockfaceshape) {
        Block block = iblockdata.getBlock();
        boolean flag = enumblockfaceshape == EnumBlockFaceShape.MIDDLE_POLE_THICK || enumblockfaceshape == EnumBlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate;
        return !f(block) && enumblockfaceshape == EnumBlockFaceShape.SOLID || flag;
    }

    public static boolean f(Block block) {
        return Block.b(block) || block == Blocks.BARRIER || block == Blocks.MELON || block == Blocks.PUMPKIN || block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN || block == Blocks.FROSTED_ICE || block == Blocks.TNT;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        BlockPosition blockposition1 = blockposition.north();
        BlockPosition blockposition2 = blockposition.east();
        BlockPosition blockposition3 = blockposition.south();
        BlockPosition blockposition4 = blockposition.west();
        IBlockData iblockdata = world.getType(blockposition1);
        IBlockData iblockdata1 = world.getType(blockposition2);
        IBlockData iblockdata2 = world.getType(blockposition3);
        IBlockData iblockdata3 = world.getType(blockposition4);
        boolean flag = this.a(iblockdata, iblockdata.c(world, blockposition1, EnumDirection.SOUTH));
        boolean flag1 = this.a(iblockdata1, iblockdata1.c(world, blockposition2, EnumDirection.WEST));
        boolean flag2 = this.a(iblockdata2, iblockdata2.c(world, blockposition3, EnumDirection.NORTH));
        boolean flag3 = this.a(iblockdata3, iblockdata3.c(world, blockposition4, EnumDirection.EAST));
        boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(UP, Boolean.valueOf(flag4 || !world.isEmpty(blockposition.up())))).set(NORTH, Boolean.valueOf(flag))).set(EAST, Boolean.valueOf(flag1))).set(SOUTH, Boolean.valueOf(flag2))).set(WEST, Boolean.valueOf(flag3))).set(p, Boolean.valueOf(fluid.c() == FluidTypes.c));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(p)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        if (enumdirection == EnumDirection.DOWN) {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            boolean flag = enumdirection == EnumDirection.NORTH ? this.a(iblockdata1, iblockdata1.c(generatoraccess, blockposition1, enumdirection.opposite())) : iblockdata.get(NORTH);
            boolean flag1 = enumdirection == EnumDirection.EAST ? this.a(iblockdata1, iblockdata1.c(generatoraccess, blockposition1, enumdirection.opposite())) : iblockdata.get(EAST);
            boolean flag2 = enumdirection == EnumDirection.SOUTH ? this.a(iblockdata1, iblockdata1.c(generatoraccess, blockposition1, enumdirection.opposite())) : iblockdata.get(SOUTH);
            boolean flag3 = enumdirection == EnumDirection.WEST ? this.a(iblockdata1, iblockdata1.c(generatoraccess, blockposition1, enumdirection.opposite())) : iblockdata.get(WEST);
            boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(UP, Boolean.valueOf(flag4 || !generatoraccess.isEmpty(blockposition.up())))).set(NORTH, Boolean.valueOf(flag))).set(EAST, Boolean.valueOf(flag1))).set(SOUTH, Boolean.valueOf(flag2))).set(WEST, Boolean.valueOf(flag3));
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(UP, NORTH, EAST, WEST, SOUTH, p);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection != EnumDirection.UP && enumdirection != EnumDirection.DOWN ? EnumBlockFaceShape.MIDDLE_POLE_THICK : EnumBlockFaceShape.CENTER_BIG;
    }
}
