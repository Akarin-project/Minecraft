package net.minecraft.server;

public class EnchantmentTridentChanneling extends Enchantment {
    public EnchantmentTridentChanneling(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.TRIDENT, aenumitemslot);
    }

    public int a(int var1) {
        return 25;
    }

    public int b(int var1) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean a(Enchantment enchantment) {
        return super.a(enchantment);
    }
}
