package net.minecraft.server;

import java.util.Random;

public class BlockEndGateway extends BlockTileEntity {
    protected BlockEndGateway(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityEndGateway();
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
