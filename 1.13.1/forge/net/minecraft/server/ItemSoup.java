package net.minecraft.server;

public class ItemSoup extends ItemFood {
    public ItemSoup(int i, Item.Info item$info) {
        super(i, 0.6F, false, item$info);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
        super.a(itemstack, world, entityliving);
        return new ItemStack(Items.BOWL);
    }
}
