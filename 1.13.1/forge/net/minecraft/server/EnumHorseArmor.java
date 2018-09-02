package net.minecraft.server;

public enum EnumHorseArmor {
    NONE(0),
    IRON(5, "iron", "meo"),
    GOLD(7, "gold", "goo"),
    DIAMOND(11, "diamond", "dio");

    private final String e;
    private final String f;
    private final int g;

    private EnumHorseArmor(int j) {
        this.g = j;
        this.e = null;
        this.f = "";
    }

    private EnumHorseArmor(int j, String s1, String s2) {
        this.g = j;
        this.e = "textures/entity/horse/armor/horse_armor_" + s1 + ".png";
        this.f = s2;
    }

    public int a() {
        return this.ordinal();
    }

    public int c() {
        return this.g;
    }

    public static EnumHorseArmor a(int i) {
        return values()[i];
    }

    public static EnumHorseArmor a(ItemStack itemstack) {
        return itemstack.isEmpty() ? NONE : a(itemstack.getItem());
    }

    public static EnumHorseArmor a(Item item) {
        if (item == Items.IRON_HORSE_ARMOR) {
            return IRON;
        } else if (item == Items.GOLDEN_HORSE_ARMOR) {
            return GOLD;
        } else {
            return item == Items.DIAMOND_HORSE_ARMOR ? DIAMOND : NONE;
        }
    }

    public static boolean b(Item item) {
        return a(item) != NONE;
    }
}
