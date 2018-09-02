package net.minecraft.server;

public class EnchantmentKnockback extends Enchantment {
    protected EnchantmentKnockback(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.WEAPON, aenumitemslot);
    }

    public int a(int i) {
        return 5 + 20 * (i - 1);
    }

    public int b(int i) {
        return super.a(i) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }
}
