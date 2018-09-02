package net.minecraft.server;

public class EnchantmentTridentLoyalty extends Enchantment {
    public EnchantmentTridentLoyalty(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.TRIDENT, aenumitemslot);
    }

    public int a(int i) {
        return 5 + i * 7;
    }

    public int b(int var1) {
        return 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean a(Enchantment enchantment) {
        return super.a(enchantment);
    }
}
