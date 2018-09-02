package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EntityWither extends EntityMonster implements IRangedEntity {
    private static final DataWatcherObject<Integer> a = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> c = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
    private static final List<DataWatcherObject<Integer>> bC = ImmutableList.of(a, b, c);
    private static final DataWatcherObject<Integer> bD = DataWatcher.a(EntityWither.class, DataWatcherRegistry.b);
    private final float[] bE = new float[2];
    private final float[] bF = new float[2];
    private final float[] bG = new float[2];
    private final float[] bH = new float[2];
    private final int[] bI = new int[2];
    private final int[] bJ = new int[2];
    private int bK;
    private final BossBattleServer bL = (BossBattleServer)(new BossBattleServer(this.getScoreboardDisplayName(), BossBattle.BarColor.PURPLE, BossBattle.BarStyle.PROGRESS)).setDarkenSky(true);
    private static final Predicate<Entity> bM = (entity) -> {
        return entity instanceof EntityLiving && ((EntityLiving)entity).getMonsterType() != EnumMonsterType.UNDEAD && ((EntityLiving)entity).df();
    };

    public EntityWither(World world) {
        super(EntityTypes.WITHER, world);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9F, 3.5F);
        this.fireProof = true;
        ((Navigation)this.getNavigation()).d(true);
        this.b_ = 50;
    }

    protected void n() {
        this.goalSelector.a(0, new EntityWither.a());
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 40, 20.0F));
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, false, false, bM));
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(a, 0);
        this.datawatcher.register(b, 0);
        this.datawatcher.register(c, 0);
        this.datawatcher.register(bD, 0);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.dz());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.d(nbttagcompound.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bL.a(this.getScoreboardDisplayName());
        }

    }

    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {
        super.setCustomName(ichatbasecomponent);
        this.bL.a(this.getScoreboardDisplayName());
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_WITHER_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_WITHER_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_WITHER_DEATH;
    }

    public void k() {
        this.motY *= (double)0.6F;
        if (!this.world.isClientSide && this.p(0) > 0) {
            Entity entity = this.world.getEntity(this.p(0));
            if (entity != null) {
                if (this.locY < entity.locY || !this.dA() && this.locY < entity.locY + 5.0D) {
                    if (this.motY < 0.0D) {
                        this.motY = 0.0D;
                    }

                    this.motY += (0.5D - this.motY) * (double)0.6F;
                }

                double d0 = entity.locX - this.locX;
                double d1 = entity.locZ - this.locZ;
                double d3 = d0 * d0 + d1 * d1;
                if (d3 > 9.0D) {
                    double d5 = (double)MathHelper.sqrt(d3);
                    this.motX += (d0 / d5 * 0.5D - this.motX) * (double)0.6F;
                    this.motZ += (d1 / d5 * 0.5D - this.motZ) * (double)0.6F;
                }
            }
        }

        if (this.motX * this.motX + this.motZ * this.motZ > (double)0.05F) {
            this.yaw = (float)MathHelper.c(this.motZ, this.motX) * (180F / (float)Math.PI) - 90.0F;
        }

        super.k();

        for(int i = 0; i < 2; ++i) {
            this.bH[i] = this.bF[i];
            this.bG[i] = this.bE[i];
        }

        for(int j = 0; j < 2; ++j) {
            int k = this.p(j + 1);
            Entity entity1 = null;
            if (k > 0) {
                entity1 = this.world.getEntity(k);
            }

            if (entity1 != null) {
                double d11 = this.q(j + 1);
                double d12 = this.r(j + 1);
                double d13 = this.s(j + 1);
                double d6 = entity1.locX - d11;
                double d7 = entity1.locY + (double)entity1.getHeadHeight() - d12;
                double d8 = entity1.locZ - d13;
                double d9 = (double)MathHelper.sqrt(d6 * d6 + d8 * d8);
                float f = (float)(MathHelper.c(d8, d6) * (double)(180F / (float)Math.PI)) - 90.0F;
                float f1 = (float)(-(MathHelper.c(d7, d9) * (double)(180F / (float)Math.PI)));
                this.bE[j] = this.c(this.bE[j], f1, 40.0F);
                this.bF[j] = this.c(this.bF[j], f, 10.0F);
            } else {
                this.bF[j] = this.c(this.bF[j], this.aQ, 10.0F);
            }
        }

        boolean flag = this.dA();

        for(int l = 0; l < 3; ++l) {
            double d10 = this.q(l);
            double d2 = this.r(l);
            double d4 = this.s(l);
            this.world.addParticle(Particles.M, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, d4 + this.random.nextGaussian() * (double)0.3F, 0.0D, 0.0D, 0.0D);
            if (flag && this.world.random.nextInt(4) == 0) {
                this.world.addParticle(Particles.s, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, d4 + this.random.nextGaussian() * (double)0.3F, (double)0.7F, (double)0.7F, 0.5D);
            }
        }

        if (this.dz() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
                this.world.addParticle(Particles.s, this.locX + this.random.nextGaussian(), this.locY + (double)(this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian(), (double)0.7F, (double)0.7F, (double)0.9F);
            }
        }

    }

    protected void mobTick() {
        if (this.dz() > 0) {
            int j1 = this.dz() - 1;
            if (j1 <= 0) {
                this.world.createExplosion(this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, 7.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
                this.world.a(1023, new BlockPosition(this), 0);
            }

            this.d(j1);
            if (this.ticksLived % 10 == 0) {
                this.heal(10.0F);
            }

        } else {
            super.mobTick();

            for(int i = 1; i < 3; ++i) {
                if (this.ticksLived >= this.bI[i - 1]) {
                    this.bI[i - 1] = this.ticksLived + 10 + this.random.nextInt(10);
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
                        int j3 = i - 1;
                        int k3 = this.bJ[i - 1];
                        this.bJ[j3] = this.bJ[i - 1] + 1;
                        if (k3 > 15) {
                            float f = 10.0F;
                            float f1 = 5.0F;
                            double d0 = MathHelper.a(this.random, this.locX - 10.0D, this.locX + 10.0D);
                            double d1 = MathHelper.a(this.random, this.locY - 5.0D, this.locY + 5.0D);
                            double d2 = MathHelper.a(this.random, this.locZ - 10.0D, this.locZ + 10.0D);
                            this.a(i + 1, d0, d1, d2, true);
                            this.bJ[i - 1] = 0;
                        }
                    }

                    int k1 = this.p(i);
                    if (k1 > 0) {
                        Entity entity = this.world.getEntity(k1);
                        if (entity != null && entity.isAlive() && !(this.h(entity) > 900.0D) && this.hasLineOfSight(entity)) {
                            if (entity instanceof EntityHuman && ((EntityHuman)entity).abilities.isInvulnerable) {
                                this.a(i, 0);
                            } else {
                                this.a(i + 1, (EntityLiving)entity);
                                this.bI[i - 1] = this.ticksLived + 40 + this.random.nextInt(20);
                                this.bJ[i - 1] = 0;
                            }
                        } else {
                            this.a(i, 0);
                        }
                    } else {
                        List list = this.world.a(EntityLiving.class, this.getBoundingBox().grow(20.0D, 8.0D, 20.0D), bM.and(IEntitySelector.f));

                        for(int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2) {
                            EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));
                            if (entityliving != this && entityliving.isAlive() && this.hasLineOfSight(entityliving)) {
                                if (entityliving instanceof EntityHuman) {
                                    if (!((EntityHuman)entityliving).abilities.isInvulnerable) {
                                        this.a(i, entityliving.getId());
                                    }
                                } else {
                                    this.a(i, entityliving.getId());
                                }
                                break;
                            }

                            list.remove(entityliving);
                        }
                    }
                }
            }

            if (this.getGoalTarget() != null) {
                this.a(0, this.getGoalTarget().getId());
            } else {
                this.a(0, 0);
            }

            if (this.bK > 0) {
                --this.bK;
                if (this.bK == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    int i1 = MathHelper.floor(this.locY);
                    int l1 = MathHelper.floor(this.locX);
                    int i2 = MathHelper.floor(this.locZ);
                    boolean flag = false;

                    for(int k2 = -1; k2 <= 1; ++k2) {
                        for(int l2 = -1; l2 <= 1; ++l2) {
                            for(int j = 0; j <= 3; ++j) {
                                int i3 = l1 + k2;
                                int k = i1 + j;
                                int l = i2 + l2;
                                BlockPosition blockposition = new BlockPosition(i3, k, l);
                                IBlockData iblockdata = this.world.getType(blockposition);
                                Block block = iblockdata.getBlock();
                                if (!iblockdata.isAir() && a(block)) {
                                    flag = this.world.setAir(blockposition, true) || flag;
                                }
                            }
                        }
                    }

                    if (flag) {
                        this.world.a((EntityHuman)null, 1022, new BlockPosition(this), 0);
                    }
                }
            }

            if (this.ticksLived % 20 == 0) {
                this.heal(1.0F);
            }

            this.bL.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    public static boolean a(Block block) {
        return block != Blocks.BEDROCK && block != Blocks.END_PORTAL && block != Blocks.END_PORTAL_FRAME && block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.BARRIER && block != Blocks.STRUCTURE_BLOCK && block != Blocks.STRUCTURE_VOID && block != Blocks.MOVING_PISTON && block != Blocks.END_GATEWAY;
    }

    public void l() {
        this.d(220);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    public void bh() {
    }

    public void b(EntityPlayer entityplayer) {
        super.b(entityplayer);
        this.bL.addPlayer(entityplayer);
    }

    public void c(EntityPlayer entityplayer) {
        super.c(entityplayer);
        this.bL.removePlayer(entityplayer);
    }

    private double q(int i) {
        if (i <= 0) {
            return this.locX;
        } else {
            float f = (this.aQ + (float)(180 * (i - 1))) * ((float)Math.PI / 180F);
            float f1 = MathHelper.cos(f);
            return this.locX + (double)f1 * 1.3D;
        }
    }

    private double r(int i) {
        return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double s(int i) {
        if (i <= 0) {
            return this.locZ;
        } else {
            float f = (this.aQ + (float)(180 * (i - 1))) * ((float)Math.PI / 180F);
            float f1 = MathHelper.sin(f);
            return this.locZ + (double)f1 * 1.3D;
        }
    }

    private float c(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }

    private void a(int i, EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + (double)entityliving.getHeadHeight() * 0.5D, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001F);
    }

    private void a(int i, double d0, double d1, double d2, boolean flag) {
        this.world.a((EntityHuman)null, 1024, new BlockPosition(this), 0);
        double d3 = this.q(i);
        double d4 = this.r(i);
        double d5 = this.s(i);
        double d6 = d0 - d3;
        double d7 = d1 - d4;
        double d8 = d2 - d5;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d6, d7, d8);
        if (flag) {
            entitywitherskull.setCharged(true);
        }

        entitywitherskull.locY = d4;
        entitywitherskull.locX = d3;
        entitywitherskull.locZ = d5;
        this.world.addEntity(entitywitherskull);
    }

    public void a(EntityLiving entityliving, float var2) {
        this.a(0, entityliving);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (damagesource != DamageSource.DROWN && !(damagesource.getEntity() instanceof EntityWither)) {
            if (this.dz() > 0 && damagesource != DamageSource.OUT_OF_WORLD) {
                return false;
            } else {
                if (this.dA()) {
                    Entity entity = damagesource.j();
                    if (entity instanceof EntityArrow) {
                        return false;
                    }
                }

                Entity entity1 = damagesource.getEntity();
                if (entity1 != null && !(entity1 instanceof EntityHuman) && entity1 instanceof EntityLiving && ((EntityLiving)entity1).getMonsterType() == this.getMonsterType()) {
                    return false;
                } else {
                    if (this.bK <= 0) {
                        this.bK = 20;
                    }

                    for(int i = 0; i < this.bJ.length; ++i) {
                        this.bJ[i] += 3;
                    }

                    return super.damageEntity(damagesource, f);
                }
            }
        } else {
            return false;
        }
    }

    protected void dropDeathLoot(boolean var1, int var2) {
        EntityItem entityitem = this.a(Items.NETHER_STAR);
        if (entityitem != null) {
            entityitem.s();
        }

    }

    protected void I() {
        this.ticksFarFromPlayer = 0;
    }

    public void c(float var1, float var2) {
    }

    public boolean addEffect(MobEffect var1) {
        return false;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)0.6F);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
        this.getAttributeInstance(GenericAttributes.h).setValue(4.0D);
    }

    public int dz() {
        return this.datawatcher.get(bD);
    }

    public void d(int i) {
        this.datawatcher.set(bD, i);
    }

    public int p(int i) {
        return this.datawatcher.get((DataWatcherObject)bC.get(i));
    }

    public void a(int i, int j) {
        this.datawatcher.set((DataWatcherObject)bC.get(i), j);
    }

    public boolean dA() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    protected boolean n(Entity var1) {
        return false;
    }

    public boolean bm() {
        return false;
    }

    public void s(boolean var1) {
    }

    class a extends PathfinderGoal {
        public a() {
            this.a(7);
        }

        public boolean a() {
            return EntityWither.this.dz() > 0;
        }
    }
}
