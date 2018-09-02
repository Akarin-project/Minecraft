package net.minecraft.server;

public class WeightedRandomEnchant extends WeightedRandom.WeightedRandomChoice {
    public final Enchantment enchantment;
    public final int level;

    public WeightedRandomEnchant(Enchantment enchantmentx, int i) {
        super(enchantmentx.d().a());
        this.enchantment = enchantmentx;
        this.level = i;
    }
}
