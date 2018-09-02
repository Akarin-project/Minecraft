package net.minecraft.server;

public class ItemShield extends Item {
    public ItemShield(Item.Info item$info) {
        super(item$info);
        this.a(new MinecraftKey("blocking"), (itemstack, var1, entityliving) -> {
            return entityliving != null && entityliving.isHandRaised() && entityliving.cW() == itemstack ? 1.0F : 0.0F;
        });
        BlockDispenser.a(this, ItemArmor.a);
    }

    public String h(ItemStack itemstack) {
        return itemstack.b("BlockEntityTag") != null ? this.getName() + '.' + e(itemstack).b() : super.h(itemstack);
    }

    public EnumAnimation d(ItemStack var1) {
        return EnumAnimation.BLOCK;
    }

    public int c(ItemStack var1) {
        return 72000;
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        entityhuman.c(enumhand);
        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
    }

    public boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return TagsItem.PLANKS.isTagged(itemstack1.getItem()) || super.a(itemstack, itemstack1);
    }

    public static EnumColor e(ItemStack itemstack) {
        return EnumColor.fromColorIndex(itemstack.a("BlockEntityTag").getInt("Base"));
    }
}
