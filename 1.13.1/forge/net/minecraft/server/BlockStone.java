package net.minecraft.server;

public class BlockStone extends Block {
    public BlockStone(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.COBBLESTONE;
    }
}
