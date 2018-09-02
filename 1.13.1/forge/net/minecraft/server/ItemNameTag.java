package net.minecraft.server;

public class ItemNameTag extends Item {
    public ItemNameTag(Item.Info item$info) {
        super(item$info);
    }

    public boolean a(ItemStack itemstack, EntityHuman var2, EntityLiving entityliving, EnumHand var4) {
        if (itemstack.hasName() && !(entityliving instanceof EntityHuman)) {
            entityliving.setCustomName(itemstack.getName());
            if (entityliving instanceof EntityInsentient) {
                ((EntityInsentient)entityliving).di();
            }

            itemstack.subtract(1);
            return true;
        } else {
            return false;
        }
    }
}
