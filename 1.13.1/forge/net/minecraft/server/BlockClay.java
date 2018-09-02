package net.minecraft.server;

import java.util.Random;

public class BlockClay extends Block {
    public BlockClay(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.CLAY_BALL;
    }

    public int a(IBlockData var1, Random var2) {
        return 4;
    }
}
