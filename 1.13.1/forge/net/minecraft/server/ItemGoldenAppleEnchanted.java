package net.minecraft.server;

public class ItemGoldenAppleEnchanted extends ItemFood {
    public ItemGoldenAppleEnchanted(int i, float f, boolean flag, Item.Info item$info) {
        super(i, f, flag, item$info);
    }

    protected void a(ItemStack var1, World world, EntityHuman entityhuman) {
        if (!world.isClientSide) {
            entityhuman.addEffect(new MobEffect(MobEffects.REGENERATION, 400, 1));
            entityhuman.addEffect(new MobEffect(MobEffects.RESISTANCE, 6000, 0));
            entityhuman.addEffect(new MobEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
            entityhuman.addEffect(new MobEffect(MobEffects.ABSORBTION, 2400, 3));
        }

    }
}
