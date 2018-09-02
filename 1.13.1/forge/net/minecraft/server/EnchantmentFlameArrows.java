package net.minecraft.server;

public class EnchantmentFlameArrows extends Enchantment {
    public EnchantmentFlameArrows(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.BOW, aenumitemslot);
    }

    public int a(int var1) {
        return 20;
    }

    public int b(int var1) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }
}
