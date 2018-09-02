package net.minecraft.server;

public class ItemArrow extends Item {
    public ItemArrow(Item.Info item$info) {
        super(item$info);
    }

    public EntityArrow a(World world, ItemStack itemstack, EntityLiving entityliving) {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, entityliving);
        entitytippedarrow.b(itemstack);
        return entitytippedarrow;
    }
}
