package net.minecraft.server;

public class ItemFish extends ItemFood {
    private final boolean a;
    private final ItemFish.EnumFish b;

    public ItemFish(ItemFish.EnumFish itemfish$enumfish, boolean flag, Item.Info item$info) {
        super(0, 0.0F, false, item$info);
        this.b = itemfish$enumfish;
        this.a = flag;
    }

    public int getNutrition(ItemStack itemstack) {
        ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(itemstack);
        return this.a && itemfish$enumfish.e() ? itemfish$enumfish.c() : itemfish$enumfish.a();
    }

    public float getSaturationModifier(ItemStack var1) {
        return this.a && this.b.e() ? this.b.d() : this.b.b();
    }

    protected void a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(itemstack);
        if (itemfish$enumfish == ItemFish.EnumFish.PUFFERFISH) {
            entityhuman.addEffect(new MobEffect(MobEffects.POISON, 1200, 3));
            entityhuman.addEffect(new MobEffect(MobEffects.HUNGER, 300, 2));
            entityhuman.addEffect(new MobEffect(MobEffects.CONFUSION, 300, 1));
        }

        super.a(itemstack, world, entityhuman);
    }

    public static enum EnumFish {
        COD(2, 0.1F, 5, 0.6F),
        SALMON(2, 0.1F, 6, 0.8F),
        TROPICAL_FISH(1, 0.1F),
        PUFFERFISH(1, 0.1F);

        private final int e;
        private final float f;
        private final int g;
        private final float h;
        private final boolean i;

        private EnumFish(int jx, float fx, int k, float f1) {
            this.e = jx;
            this.f = fx;
            this.g = k;
            this.h = f1;
            this.i = k != 0;
        }

        private EnumFish(int jx, float fx) {
            this(jx, fx, 0, 0.0F);
        }

        public int a() {
            return this.e;
        }

        public float b() {
            return this.f;
        }

        public int c() {
            return this.g;
        }

        public float d() {
            return this.h;
        }

        public boolean e() {
            return this.i;
        }

        public static ItemFish.EnumFish a(ItemStack itemstack) {
            Item item = itemstack.getItem();
            return item instanceof ItemFish ? ((ItemFish)item).b : COD;
        }
    }
}
