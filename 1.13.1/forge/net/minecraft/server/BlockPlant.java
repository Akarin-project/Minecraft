package net.minecraft.server;

public class BlockPlant extends Block {
    protected BlockPlant(Block.Info block$info) {
        super(block$info);
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        Block block = iblockdata.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();
        return this.b(iworldreader.getType(blockposition1), iworldreader, blockposition1);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public int j(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return 0;
    }
}
