package net.minecraft.server;

public class EnchantmentWaterWorker extends Enchantment {
    public EnchantmentWaterWorker(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.ARMOR_HEAD, aenumitemslot);
    }

    public int a(int var1) {
        return 1;
    }

    public int b(int i) {
        return this.a(i) + 40;
    }

    public int getMaxLevel() {
        return 1;
    }
}
