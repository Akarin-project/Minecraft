package net.minecraft.server;

public class EnchantmentBinding extends Enchantment {
    public EnchantmentBinding(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.WEARABLE, aenumitemslot);
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

    public boolean isTreasure() {
        return true;
    }

    public boolean c() {
        return true;
    }
}
