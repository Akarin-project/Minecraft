package net.minecraft.server;

import javax.annotation.Nullable;

public abstract class CreativeModeTab {
    public static final CreativeModeTab[] a = new CreativeModeTab[12];
    public static final CreativeModeTab b = (new CreativeModeTab(0, "buildingBlocks") {
    }).b("building_blocks");
    public static final CreativeModeTab c = new CreativeModeTab(1, "decorations") {
    };
    public static final CreativeModeTab d = new CreativeModeTab(2, "redstone") {
    };
    public static final CreativeModeTab e = new CreativeModeTab(3, "transportation") {
    };
    public static final CreativeModeTab f = new CreativeModeTab(6, "misc") {
    };
    public static final CreativeModeTab g = (new CreativeModeTab(5, "search") {
    }).a("item_search.png");
    public static final CreativeModeTab h = new CreativeModeTab(7, "food") {
    };
    public static final CreativeModeTab i = (new CreativeModeTab(8, "tools") {
    }).a(new EnchantmentSlotType[]{EnchantmentSlotType.ALL, EnchantmentSlotType.DIGGER, EnchantmentSlotType.FISHING_ROD, EnchantmentSlotType.BREAKABLE});
    public static final CreativeModeTab j = (new CreativeModeTab(9, "combat") {
    }).a(new EnchantmentSlotType[]{EnchantmentSlotType.ALL, EnchantmentSlotType.ARMOR, EnchantmentSlotType.ARMOR_FEET, EnchantmentSlotType.ARMOR_HEAD, EnchantmentSlotType.ARMOR_LEGS, EnchantmentSlotType.ARMOR_CHEST, EnchantmentSlotType.BOW, EnchantmentSlotType.WEAPON, EnchantmentSlotType.WEARABLE, EnchantmentSlotType.BREAKABLE, EnchantmentSlotType.TRIDENT});
    public static final CreativeModeTab k = new CreativeModeTab(10, "brewing") {
    };
    public static final CreativeModeTab l = f;
    public static final CreativeModeTab m = new CreativeModeTab(4, "hotbar") {
    };
    public static final CreativeModeTab n = (new CreativeModeTab(11, "inventory") {
    }).a("inventory.png").k().i();
    private final int o;
    private final String p;
    private String q;
    private String r = "items.png";
    private boolean s = true;
    private boolean t = true;
    private EnchantmentSlotType[] u = new EnchantmentSlotType[0];
    private ItemStack v;

    public CreativeModeTab(int ix, String sx) {
        this.o = ix;
        this.p = sx;
        this.v = ItemStack.a;
        a[ix] = this;
    }

    public String c() {
        return this.q == null ? this.p : this.q;
    }

    public CreativeModeTab a(String sx) {
        this.r = sx;
        return this;
    }

    public CreativeModeTab b(String sx) {
        this.q = sx;
        return this;
    }

    public CreativeModeTab i() {
        this.t = false;
        return this;
    }

    public CreativeModeTab k() {
        this.s = false;
        return this;
    }

    public EnchantmentSlotType[] o() {
        return this.u;
    }

    public CreativeModeTab a(EnchantmentSlotType... aenchantmentslottype) {
        this.u = aenchantmentslottype;
        return this;
    }

    public boolean a(@Nullable EnchantmentSlotType enchantmentslottype) {
        if (enchantmentslottype != null) {
            for(EnchantmentSlotType enchantmentslottype1 : this.u) {
                if (enchantmentslottype1 == enchantmentslottype) {
                    return true;
                }
            }
        }

        return false;
    }
}
