package net.minecraft.server;

import java.util.Random;

public class BlockTallPlantFlower extends BlockTallPlant implements IBlockFragilePlantElement {
    public BlockTallPlantFlower(Block.Info block$info) {
        super(block$info);
    }

    public boolean a(IBlockData var1, BlockActionContext var2) {
        return false;
    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, boolean var4) {
        return true;
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public void b(World world, Random var2, BlockPosition blockposition, IBlockData var4) {
        a(world, blockposition, new ItemStack(this));
    }
}
