package net.minecraft.server;

public class BlockGlassPane extends BlockIronBars {
    protected BlockGlassPane(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }
}
