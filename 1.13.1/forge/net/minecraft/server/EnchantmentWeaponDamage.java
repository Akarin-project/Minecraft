package net.minecraft.server;

public class EnchantmentWeaponDamage extends Enchantment {
    private static final String[] d = new String[]{"all", "undead", "arthropods"};
    private static final int[] e = new int[]{1, 5, 5};
    private static final int[] f = new int[]{11, 8, 8};
    private static final int[] g = new int[]{20, 20, 20};
    public final int a;

    public EnchantmentWeaponDamage(Enchantment.Rarity enchantment$rarity, int i, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.WEAPON, aenumitemslot);
        this.a = i;
    }

    public int a(int i) {
        return e[this.a] + (i - 1) * f[this.a];
    }

    public int b(int i) {
        return this.a(i) + g[this.a];
    }

    public int getMaxLevel() {
        return 5;
    }

    public float a(int i, EnumMonsterType enummonstertype) {
        if (this.a == 0) {
            return 1.0F + (float)Math.max(0, i - 1) * 0.5F;
        } else if (this.a == 1 && enummonstertype == EnumMonsterType.UNDEAD) {
            return (float)i * 2.5F;
        } else {
            return this.a == 2 && enummonstertype == EnumMonsterType.ARTHROPOD ? (float)i * 2.5F : 0.0F;
        }
    }

    public boolean a(Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentWeaponDamage);
    }

    public boolean canEnchant(ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemAxe ? true : super.canEnchant(itemstack);
    }

    public void a(EntityLiving entityliving, Entity entity, int i) {
        if (entity instanceof EntityLiving) {
            EntityLiving entityliving1 = (EntityLiving)entity;
            if (this.a == 2 && entityliving1.getMonsterType() == EnumMonsterType.ARTHROPOD) {
                int j = 20 + entityliving.getRandom().nextInt(10 * i);
                entityliving1.addEffect(new MobEffect(MobEffects.SLOWER_MOVEMENT, j, 3));
            }
        }

    }
}
