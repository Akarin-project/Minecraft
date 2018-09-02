package net.minecraft.server;

public class BlockCarpet extends Block {
    protected static final VoxelShape a = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private final EnumColor color;

    protected BlockCarpet(EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.color = enumcolor;
    }

    public EnumColor d() {
        return this.color;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return !iworldreader.isEmpty(blockposition.down());
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }
}
