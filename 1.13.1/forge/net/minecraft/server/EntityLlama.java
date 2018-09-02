package net.minecraft.server;

import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EntityLlama extends EntityHorseChestedAbstract implements IRangedEntity {
    private static final DataWatcherObject<Integer> bM = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> bN = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> bO = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
    private boolean bP;
    @Nullable
    private EntityLlama bQ;
    @Nullable
    private EntityLlama bR;

    public EntityLlama(World world) {
        super(EntityTypes.LLAMA, world);
        this.setSize(0.9F, 1.87F);
    }

    public void setStrength(int i) {
        this.datawatcher.set(bM, Math.max(1, Math.min(5, i)));
    }

    private void eo() {
        int i = this.random.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(1 + this.random.nextInt(i));
    }

    public int getStrength() {
        return this.datawatcher.get(bM);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Variant", this.getVariant());
        nbttagcompound.setInt("Strength", this.getStrength());
        if (!this.inventoryChest.getItem(1).isEmpty()) {
            nbttagcompound.set("DecorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.setStrength(nbttagcompound.getInt("Strength"));
        super.a(nbttagcompound);
        this.setVariant(nbttagcompound.getInt("Variant"));
        if (nbttagcompound.hasKeyOfType("DecorItem", 10)) {
            this.inventoryChest.setItem(1, ItemStack.a(nbttagcompound.getCompound("DecorItem")));
        }

        this.dS();
    }

    protected void n() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
        this.goalSelector.a(2, new PathfinderGoalLlamaFollow(this, (double)2.1F));
        this.goalSelector.a(3, new PathfinderGoalArrowAttack(this, 1.25D, 40, 20.0F));
        this.goalSelector.a(3, new PathfinderGoalPanic(this, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalRandomStrollLand(this, 0.7D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new EntityLlama.c(this));
        this.targetSelector.a(2, new EntityLlama.a(this));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(bM, 0);
        this.datawatcher.register(bN, -1);
        this.datawatcher.register(bO, 0);
    }

    public int getVariant() {
        return MathHelper.clamp(this.datawatcher.get(bO), 0, 3);
    }

    public void setVariant(int i) {
        this.datawatcher.set(bO, i);
    }

    protected int dA() {
        return this.isCarryingChest() ? 2 + 3 * this.dH() : super.dA();
    }

    public void k(Entity entity) {
        if (this.w(entity)) {
            float f = MathHelper.cos(this.aQ * ((float)Math.PI / 180F));
            float f1 = MathHelper.sin(this.aQ * ((float)Math.PI / 180F));
            float f2 = 0.3F;
            entity.setPosition(this.locX + (double)(0.3F * f1), this.locY + this.aJ() + entity.aI(), this.locZ - (double)(0.3F * f));
        }
    }

    public double aJ() {
        return (double)this.length * 0.67D;
    }

    public boolean dh() {
        return false;
    }

    protected boolean b(EntityHuman entityhuman, ItemStack itemstack) {
        byte b0 = 0;
        byte b1 = 0;
        float f = 0.0F;
        boolean flag = false;
        Item item = itemstack.getItem();
        if (item == Items.WHEAT) {
            b0 = 10;
            b1 = 3;
            f = 2.0F;
        } else if (item == Blocks.HAY_BLOCK.getItem()) {
            b0 = 90;
            b1 = 6;
            f = 10.0F;
            if (this.isTamed() && this.getAge() == 0 && this.dD()) {
                flag = true;
                this.f(entityhuman);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }

        if (this.isBaby() && b0 > 0) {
            this.world.addParticle(Particles.z, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + 0.5D + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);
            if (!this.world.isClientSide) {
                this.setAge(b0);
            }

            flag = true;
        }

        if (b1 > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxDomestication()) {
            flag = true;
            if (!this.world.isClientSide) {
                this.r(b1);
            }
        }

        if (flag && !this.isSilent()) {
            this.world.a((EntityHuman)null, this.locX, this.locY, this.locZ, SoundEffects.ENTITY_LLAMA_EAT, this.bV(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        return flag;
    }

    protected boolean isFrozen() {
        return this.getHealth() <= 0.0F || this.dN();
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity, nbttagcompound);
        this.eo();
        int i;
        if (groupdataentity instanceof EntityLlama.b) {
            i = ((EntityLlama.b)groupdataentity).a;
        } else {
            i = this.random.nextInt(4);
            groupdataentity = new EntityLlama.b(i);
        }

        this.setVariant(i);
        return groupdataentity;
    }

    protected SoundEffect dB() {
        return SoundEffects.ENTITY_LLAMA_ANGRY;
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_LLAMA_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_LLAMA_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_LLAMA_DEATH;
    }

    protected void a(BlockPosition var1, IBlockData var2) {
        this.a(SoundEffects.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }

    protected void dC() {
        this.a(SoundEffects.ENTITY_LLAMA_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public void dZ() {
        SoundEffect soundeffect = this.dB();
        if (soundeffect != null) {
            this.a(soundeffect, this.cD(), this.cE());
        }

    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.aD;
    }

    public int dH() {
        return this.getStrength();
    }

    public boolean ef() {
        return true;
    }

    public boolean g(ItemStack itemstack) {
        Item item = itemstack.getItem();
        return TagsItem.CARPETS.isTagged(item);
    }

    public boolean dU() {
        return false;
    }

    public void a(IInventory iinventory) {
        EnumColor enumcolor = this.ej();
        super.a(iinventory);
        EnumColor enumcolor1 = this.ej();
        if (this.ticksLived > 20 && enumcolor1 != null && enumcolor1 != enumcolor) {
            this.a(SoundEffects.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
        }

    }

    protected void dS() {
        if (!this.world.isClientSide) {
            super.dS();
            this.a(h(this.inventoryChest.getItem(1)));
        }
    }

    private void a(@Nullable EnumColor enumcolor) {
        this.datawatcher.set(bN, enumcolor == null ? -1 : enumcolor.getColorIndex());
    }

    @Nullable
    private static EnumColor h(ItemStack itemstack) {
        Block block = Block.asBlock(itemstack.getItem());
        return block instanceof BlockCarpet ? ((BlockCarpet)block).d() : null;
    }

    @Nullable
    public EnumColor ej() {
        int i = this.datawatcher.get(bN);
        return i == -1 ? null : EnumColor.fromColorIndex(i);
    }

    public int getMaxDomestication() {
        return 30;
    }

    public boolean mate(EntityAnimal entityanimal) {
        return entityanimal != this && entityanimal instanceof EntityLlama && this.eb() && ((EntityLlama)entityanimal).eb();
    }

    public EntityLlama b(EntityAgeable entityageable) {
        EntityLlama entityllama1 = new EntityLlama(this.world);
        this.a(entityageable, entityllama1);
        EntityLlama entityllama2 = (EntityLlama)entityageable;
        int i = this.random.nextInt(Math.max(this.getStrength(), entityllama2.getStrength())) + 1;
        if (this.random.nextFloat() < 0.03F) {
            ++i;
        }

        entityllama1.setStrength(i);
        entityllama1.setVariant(this.random.nextBoolean() ? this.getVariant() : entityllama2.getVariant());
        return entityllama1;
    }

    private void f(EntityLiving entityliving) {
        EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.world, this);
        double d0 = entityliving.locX - this.locX;
        double d1 = entityliving.getBoundingBox().b + (double)(entityliving.length / 3.0F) - entityllamaspit.locY;
        double d2 = entityliving.locZ - this.locZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        entityllamaspit.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.a((EntityHuman)null, this.locX, this.locY, this.locZ, SoundEffects.ENTITY_LLAMA_SPIT, this.bV(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        this.world.addEntity(entityllamaspit);
        this.bP = true;
    }

    private void B(boolean flag) {
        this.bP = flag;
    }

    public void c(float f, float f1) {
        int i = MathHelper.f((f * 0.5F - 3.0F) * f1);
        if (i > 0) {
            if (f >= 6.0F) {
                this.damageEntity(DamageSource.FALL, (float)i);
                if (this.isVehicle()) {
                    for(Entity entity : this.bQ()) {
                        entity.damageEntity(DamageSource.FALL, (float)i);
                    }
                }
            }

            IBlockData iblockdata = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - (double)this.lastYaw, this.locZ));
            Block block = iblockdata.getBlock();
            if (!iblockdata.isAir() && !this.isSilent()) {
                SoundEffectType soundeffecttype = block.getStepSound();
                this.world.a((EntityHuman)null, this.locX, this.locY, this.locZ, soundeffecttype.d(), this.bV(), soundeffecttype.a() * 0.5F, soundeffecttype.b() * 0.75F);
            }

        }
    }

    public void ek() {
        if (this.bQ != null) {
            this.bQ.bR = null;
        }

        this.bQ = null;
    }

    public void a(EntityLlama entityllama1) {
        this.bQ = entityllama1;
        this.bQ.bR = this;
    }

    public boolean el() {
        return this.bR != null;
    }

    public boolean em() {
        return this.bQ != null;
    }

    @Nullable
    public EntityLlama en() {
        return this.bQ;
    }

    protected double dx() {
        return 2.0D;
    }

    protected void dX() {
        if (!this.em() && this.isBaby()) {
            super.dX();
        }

    }

    public boolean dY() {
        return false;
    }

    public void a(EntityLiving entityliving, float var2) {
        this.f(entityliving);
    }

    public void s(boolean var1) {
    }

    // $FF: synthetic method
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    static class a extends PathfinderGoalNearestAttackableTarget<EntityWolf> {
        public a(EntityLlama entityllama) {
            super(entityllama, EntityWolf.class, 16, false, true, (Predicate)null);
        }

        public boolean a() {
            if (super.a() && this.d != null && !((EntityWolf)this.d).isTamed()) {
                return true;
            } else {
                this.e.setGoalTarget((EntityLiving)null);
                return false;
            }
        }

        protected double i() {
            return super.i() * 0.25D;
        }
    }

    static class b implements GroupDataEntity {
        public int a;

        private b(int i) {
            this.a = i;
        }
    }

    static class c extends PathfinderGoalHurtByTarget {
        public c(EntityLlama entityllama) {
            super(entityllama, false);
        }

        public boolean b() {
            if (this.e instanceof EntityLlama) {
                EntityLlama entityllama = (EntityLlama)this.e;
                if (entityllama.bP) {
                    entityllama.B(false);
                    return false;
                }
            }

            return super.b();
        }
    }
}
