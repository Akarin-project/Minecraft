package net.minecraft.server;

public class EnchantmentLootBonus extends Enchantment {
    protected EnchantmentLootBonus(Enchantment.Rarity enchantment$rarity, EnchantmentSlotType enchantmentslottype, EnumItemSlot... aenumitemslot) {
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

    public boolean a(Enchantment enchantment) {
        return super.a(enchantment) && enchantment != Enchantments.SILK_TOUCH;
    }
}
