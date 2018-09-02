package net.minecraft.server;

public class BlockTallPlantShearable extends BlockTallPlant {
    public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> b = BlockTallPlant.HALF;
    private final Block c;

    public BlockTallPlantShearable(Block block, Block.Info block$info) {
        super(block$info);
        this.c = block;
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        boolean flag = super.a(iblockdata, blockactioncontext);
        return flag && blockactioncontext.getItemStack().getItem() == this.getItem() ? false : flag;
    }

    protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, ItemStack itemstack) {
        if (itemstack.getItem() == Items.SHEARS) {
            a(world, blockposition, new ItemStack(this.c, 2));
        } else {
            super.a(iblockdata, world, blockposition, itemstack);
        }

    }

    public IMaterial getDropType(IBlockData iblockdata, World world, BlockPosition var3, int var4) {
        return iblockdata.get(b) == BlockPropertyDoubleBlockHalf.LOWER && this == Blocks.TALL_GRASS && world.random.nextInt(8) == 0 ? Items.WHEAT_SEEDS : Items.AIR;
    }
}
