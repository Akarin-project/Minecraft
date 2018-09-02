package net.minecraft.server;

import java.util.Random;

public class BlockMelon extends BlockStemmed {
    protected BlockMelon(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.MELON_SLICE;
    }

    public int a(IBlockData var1, Random random) {
        return 3 + random.nextInt(5);
    }

    public int getDropCount(IBlockData iblockdata, int i, World var3, BlockPosition var4, Random random) {
        return Math.min(9, this.a(iblockdata, random) + random.nextInt(1 + i));
    }

    public BlockStem d() {
        return (BlockStem)Blocks.MELON_STEM;
    }

    public BlockStemAttached e() {
        return (BlockStemAttached)Blocks.ATTACHED_MELON_STEM;
    }
}
