package net.minecraft.server;

public class BlockTorch extends Block {
    protected static final VoxelShape o = Block.a(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    protected BlockTorch(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return o;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !this.canPlace(iblockdata, generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        Block block = iblockdata.getBlock();
        boolean flag = block instanceof BlockFence || block instanceof BlockStainedGlass || block == Blocks.GLASS || block == Blocks.COBBLESTONE_WALL || block == Blocks.MOSSY_COBBLESTONE_WALL || iblockdata.q();
        return flag && block != Blocks.END_GATEWAY;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
