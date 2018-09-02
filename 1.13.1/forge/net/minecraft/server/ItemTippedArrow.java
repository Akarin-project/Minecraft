package net.minecraft.server;

public class ItemTippedArrow extends ItemArrow {
    public ItemTippedArrow(Item.Info item$info) {
        super(item$info);
    }

    public EntityArrow a(World world, ItemStack itemstack, EntityLiving entityliving) {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, entityliving);
        entitytippedarrow.b(itemstack);
        return entitytippedarrow;
    }

    public void a(CreativeModeTab creativemodetab, NonNullList<ItemStack> nonnulllist) {
        if (this.a(creativemodetab)) {
            for(PotionRegistry potionregistry : IRegistry.POTION) {
                if (!potionregistry.a().isEmpty()) {
                    nonnulllist.add(PotionUtil.a(new ItemStack(this), potionregistry));
                }
            }
        }

    }

    public String h(ItemStack itemstack) {
        return PotionUtil.d(itemstack).b(this.getName() + ".effect.");
    }
}
