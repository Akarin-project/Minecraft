package net.minecraft.server;

import java.util.Random;

public class BlockGlass extends BlockHalfTransparent {
    public BlockGlass(Block.Info block$info) {
        super(block$info);
    }

    public boolean a_(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return true;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    protected boolean X_() {
        return true;
    }
}
