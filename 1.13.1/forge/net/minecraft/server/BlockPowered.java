package net.minecraft.server;

public class BlockPowered extends Block {
    public BlockPowered(Block.Info block$info) {
        super(block$info);
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, IBlockAccess var2, BlockPosition var3, EnumDirection var4) {
        return 15;
    }
}
