package net.minecraft.server;

public class ItemSpectralArrow extends ItemArrow {
    public ItemSpectralArrow(Item.Info item$info) {
        super(item$info);
    }

    public EntityArrow a(World world, ItemStack var2, EntityLiving entityliving) {
        return new EntitySpectralArrow(world, entityliving);
    }
}
