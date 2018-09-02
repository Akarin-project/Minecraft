package net.minecraft.server;

public class BlockBarrier extends Block {
    protected BlockBarrier(Block.Info block$info) {
        super(block$info);
    }

    public boolean a_(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return true;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.INVISIBLE;
    }

    public boolean f(IBlockData var1) {
        return false;
    }

    public void dropNaturally(IBlockData var1, World var2, BlockPosition var3, float var4, int var5) {
    }
}
