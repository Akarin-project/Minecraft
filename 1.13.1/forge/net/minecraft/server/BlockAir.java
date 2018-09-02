package net.minecraft.server;

public class BlockAir extends Block {
    protected BlockAir(Block.Info block$info) {
        super(block$info);
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.INVISIBLE;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return VoxelShapes.a();
    }

    public boolean d(IBlockData var1) {
        return false;
    }

    public void dropNaturally(IBlockData var1, World var2, BlockPosition var3, float var4, int var5) {
    }

    public boolean e(IBlockData var1) {
        return true;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
