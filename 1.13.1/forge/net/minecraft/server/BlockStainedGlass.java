package net.minecraft.server;

import java.util.Random;

public class BlockStainedGlass extends BlockHalfTransparent {
    private final EnumColor color;

    public BlockStainedGlass(EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.color = enumcolor;
    }

    public boolean a_(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return true;
    }

    public EnumColor d() {
        return this.color;
    }

    public TextureType c() {
        return TextureType.TRANSLUCENT;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    protected boolean X_() {
        return true;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata1.getBlock() != iblockdata.getBlock()) {
            if (!world.isClientSide) {
                BlockBeacon.a(world, blockposition);
            }

        }
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean var5) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (!world.isClientSide) {
                BlockBeacon.a(world, blockposition);
            }

        }
    }
}
