package net.minecraft.server;

public class EnchantmentProtection extends Enchantment {
    public final EnchantmentProtection.DamageType a;

    public EnchantmentProtection(Enchantment.Rarity enchantment$rarity, EnchantmentProtection.DamageType enchantmentprotection$damagetype, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.ARMOR, aenumitemslot);
        this.a = enchantmentprotection$damagetype;
        if (enchantmentprotection$damagetype == EnchantmentProtection.DamageType.FALL) {
            this.itemTarget = EnchantmentSlotType.ARMOR_FEET;
        }

    }

    public int a(int i) {
        return this.a.b() + (i - 1) * this.a.c();
    }

    public int b(int i) {
        return this.a(i) + this.a.c();
    }

    public int getMaxLevel() {
        return 4;
    }

    public int a(int i, DamageSource damagesource) {
        if (damagesource.ignoresInvulnerability()) {
            return 0;
        } else if (this.a == EnchantmentProtection.DamageType.ALL) {
            return i;
        } else if (this.a == EnchantmentProtection.DamageType.FIRE && damagesource.p()) {
            return i * 2;
        } else if (this.a == EnchantmentProtection.DamageType.FALL && damagesource == DamageSource.FALL) {
            return i * 3;
        } else if (this.a == EnchantmentProtection.DamageType.EXPLOSION && damagesource.isExplosion()) {
            return i * 2;
        } else {
            return this.a == EnchantmentProtection.DamageType.PROJECTILE && damagesource.b() ? i * 2 : 0;
        }
    }

    public boolean a(Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            EnchantmentProtection enchantmentprotection1 = (EnchantmentProtection)enchantment;
            if (this.a == enchantmentprotection1.a) {
                return false;
            } else {
                return this.a == EnchantmentProtection.DamageType.FALL || enchantmentprotection1.a == EnchantmentProtection.DamageType.FALL;
            }
        } else {
            return super.a(enchantment);
        }
    }

    public static int a(EntityLiving entityliving, int i) {
        int j = EnchantmentManager.a(Enchantments.PROTECTION_FIRE, entityliving);
        if (j > 0) {
            i -= MathHelper.d((float)i * (float)j * 0.15F);
        }

        return i;
    }

    public static double a(EntityLiving entityliving, double d0) {
        int i = EnchantmentManager.a(Enchantments.PROTECTION_EXPLOSIONS, entityliving);
        if (i > 0) {
            d0 -= (double)MathHelper.floor(d0 * (double)((float)i * 0.15F));
        }

        return d0;
    }

    public static enum DamageType {
        ALL("all", 1, 11),
        FIRE("fire", 10, 8),
        FALL("fall", 5, 6),
        EXPLOSION("explosion", 5, 8),
        PROJECTILE("projectile", 3, 6);

        private final String f;
        private final int g;
        private final int h;

        private DamageType(String s1, int j, int k) {
            this.f = s1;
            this.g = j;
            this.h = k;
        }

        public int b() {
            return this.g;
        }

        public int c() {
            return this.h;
        }
    }
}
