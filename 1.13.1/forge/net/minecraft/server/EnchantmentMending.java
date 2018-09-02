package net.minecraft.server;

public class EnchantmentMending extends Enchantment {
    public EnchantmentMending(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.BREAKABLE, aenumitemslot);
    }

    public int a(int i) {
        return i * 25;
    }

    public int b(int i) {
        return this.a(i) + 50;
    }

    public boolean isTreasure() {
        return true;
    }

    public int getMaxLevel() {
        return 1;
    }
}
