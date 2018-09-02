package net.minecraft.server;

import java.util.Random;

public class BlockSnowBlock extends Block {
    protected BlockSnowBlock(Block.Info block$info) {
        super(block$info);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.SNOWBALL;
    }

    public int a(IBlockData var1, Random var2) {
        return 4;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (world.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 11) {
            iblockdata.a(world, blockposition, 0);
            world.setAir(blockposition);
        }

    }
}
