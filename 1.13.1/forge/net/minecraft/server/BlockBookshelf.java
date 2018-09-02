package net.minecraft.server;

import java.util.Random;

public class BlockBookshelf extends Block {
    public BlockBookshelf(Block.Info block$info) {
        super(block$info);
    }

    public int a(IBlockData var1, Random var2) {
        return 3;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.BOOK;
    }
}
