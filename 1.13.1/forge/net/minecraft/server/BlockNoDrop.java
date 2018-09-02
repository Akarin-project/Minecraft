package net.minecraft.server;

public class BlockNoDrop extends Block {
    public BlockNoDrop(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }
}
