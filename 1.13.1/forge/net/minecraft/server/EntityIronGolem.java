package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityIronGolem extends EntityGolem {
    protected static final DataWatcherObject<Byte> a = DataWatcher.a(EntityIronGolem.class, DataWatcherRegistry.a);
    private int b;
    @Nullable
    private Village c;
    private int bC;
    private int bD;

    public EntityIronGolem(World world) {
        super(EntityTypes.IRON_GOLEM, world);
        this.setSize(1.4F, 2.7F);
    }

    protected void n() {
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStrollLand(this, 0.6D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 10, false, true, (entityinsentient) -> {
            return entityinsentient != null && IMonster.e.test(entityinsentient) && !(entityinsentient instanceof EntityCreeper);
        }));
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(a, (byte)0);
    }

    protected void mobTick() {
        if (--this.b <= 0) {
            this.b = 70 + this.random.nextInt(50);
            this.c = this.world.af().getClosestVillage(new BlockPosition(this), 32);
            if (this.c == null) {
                this.dv();
            } else {
                BlockPosition blockposition = this.c.a();
                this.a(blockposition, (int)((float)this.c.b() * 0.6F));
            }
        }

        super.mobTick();
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
        this.getAttributeInstance(GenericAttributes.c).setValue(1.0D);
    }

    protected int k(int i) {
        return i;
    }

    protected void C(Entity entity) {
        if (entity instanceof IMonster && !(entity instanceof EntityCreeper) && this.getRandom().nextInt(20) == 0) {
            this.setGoalTarget((EntityLiving)entity);
        }

        super.C(entity);
    }

    public void k() {
        super.k();
        if (this.bC > 0) {
            --this.bC;
        }

        if (this.bD > 0) {
            --this.bD;
        }

        if (this.motX * this.motX + this.motZ * this.motZ > (double)2.5000003E-7F && this.random.nextInt(5) == 0) {
            int i = MathHelper.floor(this.locX);
            int j = MathHelper.floor(this.locY - (double)0.2F);
            int k = MathHelper.floor(this.locZ);
            IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
            if (!iblockdata.isAir()) {
                this.world.addParticle(new ParticleParamBlock(Particles.d, iblockdata), this.locX + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, this.getBoundingBox().b + 0.1D, this.locZ + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.random.nextFloat() - 0.5D), 0.5D, ((double)this.random.nextFloat() - 0.5D) * 4.0D);
            }
        }

    }

    public boolean b(Class<? extends EntityLiving> oclass) {
        if (this.isPlayerCreated() && EntityHuman.class.isAssignableFrom(oclass)) {
            return false;
        } else {
            return oclass == EntityCreeper.class ? false : super.b(oclass);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
    }

    public boolean B(Entity entity) {
        this.bC = 10;
        this.world.broadcastEntityEffect(this, (byte)4);
        boolean flag = entity.damageEntity(DamageSource.mobAttack(this), (float)(7 + this.random.nextInt(15)));
        if (flag) {
            entity.motY += (double)0.4F;
            this.a(this, entity);
        }

        this.a(SoundEffects.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    public Village l() {
        return this.c;
    }

    public void a(boolean flag) {
        if (flag) {
            this.bD = 400;
            this.world.broadcastEntityEffect(this, (byte)11);
        } else {
            this.bD = 0;
            this.world.broadcastEntityEffect(this, (byte)34);
        }

    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_IRON_GOLEM_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_IRON_GOLEM_DEATH;
    }

    protected void a(BlockPosition var1, IBlockData var2) {
        this.a(SoundEffects.ENTITY_IRON_GOLEM_STEP, 1.0F, 1.0F);
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.G;
    }

    public int dz() {
        return this.bD;
    }

    public boolean isPlayerCreated() {
        return (this.datawatcher.get(a) & 1) != 0;
    }

    public void setPlayerCreated(boolean flag) {
        byte b0 = this.datawatcher.get(a);
        if (flag) {
            this.datawatcher.set(a, (byte)(b0 | 1));
        } else {
            this.datawatcher.set(a, (byte)(b0 & -2));
        }

    }

    public void die(DamageSource damagesource) {
        if (!this.isPlayerCreated() && this.killer != null && this.c != null) {
            this.c.a(this.killer.getProfile().getName(), -5);
        }

        super.die(damagesource);
    }

    public boolean a(IWorldReader iworldreader) {
        BlockPosition blockposition = new BlockPosition(this.locX, this.locY, this.locZ);
        IBlockData iblockdata = iworldreader.getType(blockposition);
        IBlockData iblockdata1 = iworldreader.getType(blockposition.down());
        IBlockData iblockdata2 = iworldreader.getType(blockposition.up());
        return iblockdata1.q() && SpawnerCreature.a(iblockdata2, iblockdata2.s()) && SpawnerCreature.a(iblockdata, FluidTypes.a.i()) && iworldreader.getCubes(this, this.getBoundingBox()) && iworldreader.a_(this, this.getBoundingBox());
    }
}
