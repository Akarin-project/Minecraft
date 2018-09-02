package net.minecraft.server;

public class EnchantmentSilkTouch extends Enchantment {
    protected EnchantmentSilkTouch(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.DIGGER, aenumitemslot);
    }

    public int a(int var1) {
        return 15;
    }

    public int b(int i) {
        return super.a(i) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean a(Enchantment enchantment) {
        return super.a(enchantment) && enchantment != Enchantments.LOOT_BONUS_BLOCKS;
    }
}
