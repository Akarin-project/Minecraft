package net.minecraft.server;

public class ItemElytra extends Item {
    public ItemElytra(Item.Info item$info) {
        super(item$info);
        this.a(new MinecraftKey("broken"), (itemstack, var1, var2) -> {
            return e(itemstack) ? 0.0F : 1.0F;
        });
        BlockDispenser.a(this, ItemArmor.a);
    }

    public static boolean e(ItemStack itemstack) {
        return itemstack.getDamage() < itemstack.h() - 1;
    }

    public boolean a(ItemStack var1, ItemStack itemstack) {
        return itemstack.getItem() == Items.PHANTOM_MEMBRANE;
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        EnumItemSlot enumitemslot = EntityInsentient.e(itemstack);
        ItemStack itemstack1 = entityhuman.getEquipment(enumitemslot);
        if (itemstack1.isEmpty()) {
            entityhuman.setSlot(enumitemslot, itemstack.cloneItemStack());
            itemstack.setCount(0);
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        }
    }
}
