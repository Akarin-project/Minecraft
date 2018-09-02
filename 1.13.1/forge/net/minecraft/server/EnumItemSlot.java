package net.minecraft.server;

public enum EnumItemSlot {
    MAINHAND(EnumItemSlot.Function.HAND, 0, 0, "mainhand"),
    OFFHAND(EnumItemSlot.Function.HAND, 1, 5, "offhand"),
    FEET(EnumItemSlot.Function.ARMOR, 0, 1, "feet"),
    LEGS(EnumItemSlot.Function.ARMOR, 1, 2, "legs"),
    CHEST(EnumItemSlot.Function.ARMOR, 2, 3, "chest"),
    HEAD(EnumItemSlot.Function.ARMOR, 3, 4, "head");

    private final EnumItemSlot.Function g;
    private final int h;
    private final int i;
    private final String j;

    private EnumItemSlot(EnumItemSlot.Function enumitemslot$function, int jx, int kx, String s1) {
        this.g = enumitemslot$function;
        this.h = jx;
        this.i = kx;
        this.j = s1;
    }

    public EnumItemSlot.Function a() {
        return this.g;
    }

    public int b() {
        return this.h;
    }

    public int c() {
        return this.i;
    }

    public String d() {
        return this.j;
    }

    public static EnumItemSlot a(String s) {
        for(EnumItemSlot enumitemslot : values()) {
            if (enumitemslot.d().equals(s)) {
                return enumitemslot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + s + "'");
    }

    public static enum Function {
        HAND,
        ARMOR;

        private Function() {
        }
    }
}
