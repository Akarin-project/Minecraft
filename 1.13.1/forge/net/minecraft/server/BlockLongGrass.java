package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockLongGrass extends BlockPlant implements IBlockFragilePlantElement {
    protected static final VoxelShape a = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    protected BlockLongGrass(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public IMaterial getDropType(IBlockData var1, World world, BlockPosition var3, int var4) {
        return world.random.nextInt(8) == 0 ? Items.WHEAT_SEEDS : Items.AIR;
    }

    public int getDropCount(IBlockData var1, int i, World var3, BlockPosition var4, Random random) {
        return 1 + random.nextInt(i * 2 + 1);
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(this));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, boolean var4) {
        return true;
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public void b(World world, Random var2, BlockPosition blockposition, IBlockData var4) {
        BlockTallPlant blocktallplant = (BlockTallPlant)(this == Blocks.FERN ? Blocks.LARGE_FERN : Blocks.TALL_GRASS);
        if (blocktallplant.getBlockData().canPlace(world, blockposition) && world.isEmpty(blockposition.up())) {
            blocktallplant.a(world, blockposition, 2);
        }

    }

    public Block.EnumRandomOffset q() {
        return Block.EnumRandomOffset.XYZ;
    }
}
