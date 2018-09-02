package net.minecraft.server;

public class BlockIronBars extends BlockTall {
    protected BlockIronBars(Block.Info block$info) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(NORTH, Boolean.valueOf(false))).set(EAST, Boolean.valueOf(false))).set(SOUTH, Boolean.valueOf(false))).set(WEST, Boolean.valueOf(false))).set(p, Boolean.valueOf(false)));
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        BlockPosition blockposition1 = blockposition.north();
        BlockPosition blockposition2 = blockposition.south();
        BlockPosition blockposition3 = blockposition.west();
        BlockPosition blockposition4 = blockposition.east();
        IBlockData iblockdata = world.getType(blockposition1);
        IBlockData iblockdata1 = world.getType(blockposition2);
        IBlockData iblockdata2 = world.getType(blockposition3);
        IBlockData iblockdata3 = world.getType(blockposition4);
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(NORTH, Boolean.valueOf(this.a(iblockdata, iblockdata.c(world, blockposition1, EnumDirection.SOUTH))))).set(SOUTH, Boolean.valueOf(this.a(iblockdata1, iblockdata1.c(world, blockposition2, EnumDirection.NORTH))))).set(WEST, Boolean.valueOf(this.a(iblockdata2, iblockdata2.c(world, blockposition3, EnumDirection.EAST))))).set(EAST, Boolean.valueOf(this.a(iblockdata3, iblockdata3.c(world, blockposition4, EnumDirection.WEST))))).set(p, Boolean.valueOf(fluid.c() == FluidTypes.c));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(p)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return enumdirection.k().c() ? (IBlockData)iblockdata.set((IBlockState)q.get(enumdirection), Boolean.valueOf(this.a(iblockdata1, iblockdata1.c(generatoraccess, blockposition1, enumdirection.opposite())))) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public final boolean a(IBlockData iblockdata, EnumBlockFaceShape enumblockfaceshape) {
        Block block = iblockdata.getBlock();
        return !f(block) && enumblockfaceshape == EnumBlockFaceShape.SOLID || enumblockfaceshape == EnumBlockFaceShape.MIDDLE_POLE_THIN;
    }

    public static boolean f(Block block) {
        return block instanceof BlockShulkerBox || block instanceof BlockLeaves || block == Blocks.BEACON || block == Blocks.CAULDRON || block == Blocks.GLOWSTONE || block == Blocks.ICE || block == Blocks.SEA_LANTERN || block == Blocks.PISTON || block == Blocks.STICKY_PISTON || block == Blocks.PISTON_HEAD || block == Blocks.MELON || block == Blocks.PUMPKIN || block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN || block == Blocks.BARRIER;
    }

    protected boolean X_() {
        return true;
    }

    public TextureType c() {
        return TextureType.CUTOUT_MIPPED;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(NORTH, EAST, WEST, SOUTH, p);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection != EnumDirection.UP && enumdirection != EnumDirection.DOWN ? EnumBlockFaceShape.MIDDLE_POLE_THIN : EnumBlockFaceShape.CENTER_SMALL;
    }
}
