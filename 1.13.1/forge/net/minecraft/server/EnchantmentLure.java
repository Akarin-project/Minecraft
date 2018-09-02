package net.minecraft.server;

public class EnchantmentLure extends Enchantment {
    protected EnchantmentLure(Enchantment.Rarity enchantment$rarity, EnchantmentSlotType enchantmentslottype, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, enchantmentslottype, aenumitemslot);
    }

    public int a(int i) {
        return 15 + (i - 1) * 9;
    }

    public int b(int i) {
        return super.a(i) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }
}
