package net.minecraft.server;

public class ItemFood extends Item {
    private final int a;
    private final float b;
    private final boolean c;
    private boolean d;
    private boolean e;
    private MobEffect k;
    private float l;

    public ItemFood(int i, float f, boolean flag, Item.Info item$info) {
        super(item$info);
        this.a = i;
        this.c = flag;
        this.b = f;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
        if (entityliving instanceof EntityHuman) {
            EntityHuman entityhuman = (EntityHuman)entityliving;
            entityhuman.getFoodData().a(this, itemstack);
            world.a((EntityHuman)null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
            this.a(itemstack, world, entityhuman);
            entityhuman.b(StatisticList.ITEM_USED.b(this));
            if (entityhuman instanceof EntityPlayer) {
                CriterionTriggers.z.a((EntityPlayer)entityhuman, itemstack);
            }
        }

        itemstack.subtract(1);
        return itemstack;
    }

    protected void a(ItemStack var1, World world, EntityHuman entityhuman) {
        if (!world.isClientSide && this.k != null && world.random.nextFloat() < this.l) {
            entityhuman.addEffect(new MobEffect(this.k));
        }

    }

    public int c(ItemStack var1) {
        return this.e ? 16 : 32;
    }

    public EnumAnimation d(ItemStack var1) {
        return EnumAnimation.EAT;
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        if (entityhuman.q(this.d)) {
            entityhuman.c(enumhand);
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        }
    }

    public int getNutrition(ItemStack var1) {
        return this.a;
    }

    public float getSaturationModifier(ItemStack var1) {
        return this.b;
    }

    public boolean d() {
        return this.c;
    }

    public ItemFood a(MobEffect mobeffect, float f) {
        this.k = mobeffect;
        this.l = f;
        return this;
    }

    public ItemFood e() {
        this.d = true;
        return this;
    }

    public ItemFood f() {
        this.e = true;
        return this;
    }
}
