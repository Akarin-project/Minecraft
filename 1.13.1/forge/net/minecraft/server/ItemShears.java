package net.minecraft.server;

public class ItemShears extends Item {
    public ItemShears(Item.Info item$info) {
        super(item$info);
    }

    public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
        if (!world.isClientSide) {
            itemstack.damage(1, entityliving);
        }

        Block block = iblockdata.getBlock();
        return !iblockdata.a(TagsBlock.LEAVES) && block != Blocks.COBWEB && block != Blocks.GRASS && block != Blocks.FERN && block != Blocks.DEAD_BUSH && block != Blocks.VINE && block != Blocks.TRIPWIRE && !block.a(TagsBlock.WOOL) ? super.a(itemstack, world, iblockdata, blockposition, entityliving) : true;
    }

    public boolean canDestroySpecialBlock(IBlockData iblockdata) {
        Block block = iblockdata.getBlock();
        return block == Blocks.COBWEB || block == Blocks.REDSTONE_WIRE || block == Blocks.TRIPWIRE;
    }

    public float getDestroySpeed(ItemStack itemstack, IBlockData iblockdata) {
        Block block = iblockdata.getBlock();
        if (block != Blocks.COBWEB && !iblockdata.a(TagsBlock.LEAVES)) {
            return block.a(TagsBlock.WOOL) ? 5.0F : super.getDestroySpeed(itemstack, iblockdata);
        } else {
            return 15.0F;
        }
    }
}
