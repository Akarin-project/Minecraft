package net.minecraft.server;

import java.util.Random;

public class EnchantmentDurability extends Enchantment {
    protected EnchantmentDurability(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.BREAKABLE, aenumitemslot);
    }

    public int a(int i) {
        return 5 + (i - 1) * 8;
    }

    public int b(int i) {
        return super.a(i) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canEnchant(ItemStack itemstack) {
        return itemstack.e() ? true : super.canEnchant(itemstack);
    }

    public static boolean a(ItemStack itemstack, int i, Random random) {
        if (itemstack.getItem() instanceof ItemArmor && random.nextFloat() < 0.6F) {
            return false;
        } else {
            return random.nextInt(i + 1) > 0;
        }
    }
}
