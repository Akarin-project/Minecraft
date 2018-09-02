package net.minecraft.server;

import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityEnderDragon extends EntityInsentient implements IComplex, IMonster {
    private static final Logger bQ = LogManager.getLogger();
    public static final DataWatcherObject<Integer> PHASE = DataWatcher.a(EntityEnderDragon.class, DataWatcherRegistry.b);
    public double[][] b = new double[64][3];
    public int c = -1;
    public EntityComplexPart[] children;
    public EntityComplexPart bD = new EntityComplexPart(this, "head", 6.0F, 6.0F);
    public EntityComplexPart bE = new EntityComplexPart(this, "neck", 6.0F, 6.0F);
    public EntityComplexPart bF = new EntityComplexPart(this, "body", 8.0F, 8.0F);
    public EntityComplexPart bG = new EntityComplexPart(this, "tail", 4.0F, 4.0F);
    public EntityComplexPart bH = new EntityComplexPart(this, "tail", 4.0F, 4.0F);
    public EntityComplexPart bI = new EntityComplexPart(this, "tail", 4.0F, 4.0F);
    public EntityComplexPart bJ = new EntityComplexPart(this, "wing", 4.0F, 4.0F);
    public EntityComplexPart bK = new EntityComplexPart(this, "wing", 4.0F, 4.0F);
    public float bL;
    public float bM;
    public boolean bN;
    public int bO;
    public EntityEnderCrystal currentEnderCrystal;
    private final EnderDragonBattle bR;
    private final DragonControllerManager bS;
    private int bT = 100;
    private int bU;
    private final PathPoint[] bV = new PathPoint[24];
    private final int[] bW = new int[24];
    private final Path bX = new Path();

    public EntityEnderDragon(World world) {
        super(EntityTypes.ENDER_DRAGON, world);
        this.children = new EntityComplexPart[]{this.bD, this.bE, this.bF, this.bG, this.bH, this.bI, this.bJ, this.bK};
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0F, 8.0F);
        this.noclip = true;
        this.fireProof = true;
        this.ak = true;
        if (!world.isClientSide && world.worldProvider instanceof WorldProviderTheEnd) {
            this.bR = ((WorldProviderTheEnd)world.worldProvider).r();
        } else {
            this.bR = null;
        }

        this.bS = new DragonControllerManager(this);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0D);
    }

    protected void x_() {
        super.x_();
        this.getDataWatcher().register(PHASE, DragonControllerPhase.k.b());
    }

    public double[] a(int i, float f) {
        if (this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        f = 1.0F - f;
        int j = this.c - i & 63;
        int k = this.c - i - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.b[j][0];
        double d1 = MathHelper.g(this.b[k][0] - d0);
        adouble[0] = d0 + d1 * (double)f;
        d0 = this.b[j][1];
        d1 = this.b[k][1] - d0;
        adouble[1] = d0 + d1 * (double)f;
        adouble[2] = this.b[j][2] + (this.b[k][2] - this.b[j][2]) * (double)f;
        return adouble;
    }

    public void k() {
        if (this.world.isClientSide) {
            this.setHealth(this.getHealth());
            if (!this.isSilent()) {
                float f = MathHelper.cos(this.bM * ((float)Math.PI * 2F));
                float f1 = MathHelper.cos(this.bL * ((float)Math.PI * 2F));
                if (f1 <= -0.3F && f >= -0.3F) {
                    this.world.a(this.locX, this.locY, this.locZ, SoundEffects.ENTITY_ENDER_DRAGON_FLAP, this.bV(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
                }

                if (!this.bS.a().a() && --this.bT < 0) {
                    this.world.a(this.locX, this.locY, this.locZ, SoundEffects.ENTITY_ENDER_DRAGON_GROWL, this.bV(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
                    this.bT = 200 + this.random.nextInt(200);
                }
            }
        }

        this.bL = this.bM;
        if (this.getHealth() <= 0.0F) {
            float f12 = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f13 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f15 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(Particles.u, this.locX + (double)f12, this.locY + 2.0D + (double)f13, this.locZ + (double)f15, 0.0D, 0.0D, 0.0D);
        } else {
            this.dt();
            float f11 = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
            f11 = f11 * (float)Math.pow(2.0D, this.motY);
            if (this.bS.a().a()) {
                this.bM += 0.1F;
            } else if (this.bN) {
                this.bM += f11 * 0.5F;
            } else {
                this.bM += f11;
            }

            this.yaw = MathHelper.g(this.yaw);
            if (this.isNoAI()) {
                this.bM = 0.5F;
            } else {
                if (this.c < 0) {
                    for(int i = 0; i < this.b.length; ++i) {
                        this.b[i][0] = (double)this.yaw;
                        this.b[i][1] = this.locY;
                    }
                }

                if (++this.c == this.b.length) {
                    this.c = 0;
                }

                this.b[this.c][0] = (double)this.yaw;
                this.b[this.c][1] = this.locY;
                if (this.world.isClientSide) {
                    if (this.bl > 0) {
                        double d5 = this.locX + (this.bm - this.locX) / (double)this.bl;
                        double d0 = this.locY + (this.bn - this.locY) / (double)this.bl;
                        double d1 = this.locZ + (this.bo - this.locZ) / (double)this.bl;
                        double d2 = MathHelper.g(this.bp - (double)this.yaw);
                        this.yaw = (float)((double)this.yaw + d2 / (double)this.bl);
                        this.pitch = (float)((double)this.pitch + (this.bq - (double)this.pitch) / (double)this.bl);
                        --this.bl;
                        this.setPosition(d5, d0, d1);
                        this.setYawPitch(this.yaw, this.pitch);
                    }

                    this.bS.a().b();
                } else {
                    IDragonController idragoncontroller = this.bS.a();
                    idragoncontroller.c();
                    if (this.bS.a() != idragoncontroller) {
                        idragoncontroller = this.bS.a();
                        idragoncontroller.c();
                    }

                    Vec3D vec3d = idragoncontroller.g();
                    if (vec3d != null) {
                        double d6 = vec3d.x - this.locX;
                        double d7 = vec3d.y - this.locY;
                        double d8 = vec3d.z - this.locZ;
                        double d3 = d6 * d6 + d7 * d7 + d8 * d8;
                        float f5 = idragoncontroller.f();
                        d7 = MathHelper.a(d7 / (double)MathHelper.sqrt(d6 * d6 + d8 * d8), (double)(-f5), (double)f5);
                        this.motY += d7 * (double)0.1F;
                        this.yaw = MathHelper.g(this.yaw);
                        double d4 = MathHelper.a(MathHelper.g(180.0D - MathHelper.c(d6, d8) * (double)(180F / (float)Math.PI) - (double)this.yaw), -50.0D, 50.0D);
                        Vec3D vec3d1 = (new Vec3D(vec3d.x - this.locX, vec3d.y - this.locY, vec3d.z - this.locZ)).a();
                        Vec3D vec3d2 = (new Vec3D((double)MathHelper.sin(this.yaw * ((float)Math.PI / 180F)), this.motY, (double)(-MathHelper.cos(this.yaw * ((float)Math.PI / 180F))))).a();
                        float f7 = Math.max(((float)vec3d2.b(vec3d1) + 0.5F) / 1.5F, 0.0F);
                        this.bk *= 0.8F;
                        this.bk = (float)((double)this.bk + d4 * (double)idragoncontroller.h());
                        this.yaw += this.bk * 0.1F;
                        float f8 = (float)(2.0D / (d3 + 1.0D));
                        float f9 = 0.06F;
                        this.a(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + (1.0F - f8)));
                        if (this.bN) {
                            this.move(EnumMoveType.SELF, this.motX * (double)0.8F, this.motY * (double)0.8F, this.motZ * (double)0.8F);
                        } else {
                            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
                        }

                        Vec3D vec3d3 = (new Vec3D(this.motX, this.motY, this.motZ)).a();
                        float f10 = ((float)vec3d3.b(vec3d2) + 1.0F) / 2.0F;
                        f10 = 0.8F + 0.15F * f10;
                        this.motX *= (double)f10;
                        this.motZ *= (double)f10;
                        this.motY *= (double)0.91F;
                    }
                }

                this.aQ = this.yaw;
                this.bD.width = 1.0F;
                this.bD.length = 1.0F;
                this.bE.width = 3.0F;
                this.bE.length = 3.0F;
                this.bG.width = 2.0F;
                this.bG.length = 2.0F;
                this.bH.width = 2.0F;
                this.bH.length = 2.0F;
                this.bI.width = 2.0F;
                this.bI.length = 2.0F;
                this.bF.length = 3.0F;
                this.bF.width = 5.0F;
                this.bJ.length = 2.0F;
                this.bJ.width = 4.0F;
                this.bK.length = 3.0F;
                this.bK.width = 4.0F;
                Vec3D[] avec3d = new Vec3D[this.children.length];

                for(int j = 0; j < this.children.length; ++j) {
                    avec3d[j] = new Vec3D(this.children[j].locX, this.children[j].locY, this.children[j].locZ);
                }

                float f14 = (float)(this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F * ((float)Math.PI / 180F);
                float f16 = MathHelper.cos(f14);
                float f2 = MathHelper.sin(f14);
                float f17 = this.yaw * ((float)Math.PI / 180F);
                float f3 = MathHelper.sin(f17);
                float f18 = MathHelper.cos(f17);
                this.bF.tick();
                this.bF.setPositionRotation(this.locX + (double)(f3 * 0.5F), this.locY, this.locZ - (double)(f18 * 0.5F), 0.0F, 0.0F);
                this.bJ.tick();
                this.bJ.setPositionRotation(this.locX + (double)(f18 * 4.5F), this.locY + 2.0D, this.locZ + (double)(f3 * 4.5F), 0.0F, 0.0F);
                this.bK.tick();
                this.bK.setPositionRotation(this.locX - (double)(f18 * 4.5F), this.locY + 2.0D, this.locZ - (double)(f3 * 4.5F), 0.0F, 0.0F);
                if (!this.world.isClientSide && this.hurtTicks == 0) {
                    this.a(this.world.getEntities(this, this.bJ.getBoundingBox().grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
                    this.a(this.world.getEntities(this, this.bK.getBoundingBox().grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
                    this.b(this.world.getEntities(this, this.bD.getBoundingBox().g(1.0D)));
                    this.b(this.world.getEntities(this, this.bE.getBoundingBox().g(1.0D)));
                }

                double[] adouble = this.a(5, 1.0F);
                float f19 = MathHelper.sin(this.yaw * ((float)Math.PI / 180F) - this.bk * 0.01F);
                float f4 = MathHelper.cos(this.yaw * ((float)Math.PI / 180F) - this.bk * 0.01F);
                this.bD.tick();
                this.bE.tick();
                float f20 = this.u(1.0F);
                this.bD.setPositionRotation(this.locX + (double)(f19 * 6.5F * f16), this.locY + (double)f20 + (double)(f2 * 6.5F), this.locZ - (double)(f4 * 6.5F * f16), 0.0F, 0.0F);
                this.bE.setPositionRotation(this.locX + (double)(f19 * 5.5F * f16), this.locY + (double)f20 + (double)(f2 * 5.5F), this.locZ - (double)(f4 * 5.5F * f16), 0.0F, 0.0F);

                for(int k = 0; k < 3; ++k) {
                    EntityComplexPart entitycomplexpart = null;
                    if (k == 0) {
                        entitycomplexpart = this.bG;
                    }

                    if (k == 1) {
                        entitycomplexpart = this.bH;
                    }

                    if (k == 2) {
                        entitycomplexpart = this.bI;
                    }

                    double[] adouble1 = this.a(12 + k * 2, 1.0F);
                    float f21 = this.yaw * ((float)Math.PI / 180F) + this.c(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F);
                    float f6 = MathHelper.sin(f21);
                    float f22 = MathHelper.cos(f21);
                    float f23 = 1.5F;
                    float f24 = (float)(k + 1) * 2.0F;
                    entitycomplexpart.tick();
                    entitycomplexpart.setPositionRotation(this.locX - (double)((f3 * 1.5F + f6 * f24) * f16), this.locY + (adouble1[1] - adouble[1]) - (double)((f24 + 1.5F) * f2) + 1.5D, this.locZ + (double)((f18 * 1.5F + f22 * f24) * f16), 0.0F, 0.0F);
                }

                if (!this.world.isClientSide) {
                    this.bN = this.b(this.bD.getBoundingBox()) | this.b(this.bE.getBoundingBox()) | this.b(this.bF.getBoundingBox());
                    if (this.bR != null) {
                        this.bR.b(this);
                    }
                }

                for(int l = 0; l < this.children.length; ++l) {
                    this.children[l].lastX = avec3d[l].x;
                    this.children[l].lastY = avec3d[l].y;
                    this.children[l].lastZ = avec3d[l].z;
                }

            }
        }
    }

    private float u(float var1) {
        double d0;
        if (this.bS.a().a()) {
            d0 = -1.0D;
        } else {
            double[] adouble = this.a(5, 1.0F);
            double[] adouble1 = this.a(0, 1.0F);
            d0 = adouble[1] - adouble1[1];
        }

        return (float)d0;
    }

    private void dt() {
        if (this.currentEnderCrystal != null) {
            if (this.currentEnderCrystal.dead) {
                this.currentEnderCrystal = null;
            } else if (this.ticksLived % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0F);
            }
        }

        if (this.random.nextInt(10) == 0) {
            List list = this.world.a(EntityEnderCrystal.class, this.getBoundingBox().g(32.0D));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = Double.MAX_VALUE;

            for(EntityEnderCrystal entityendercrystal1 : list) {
                double d1 = entityendercrystal1.h(this);
                if (d1 < d0) {
                    d0 = d1;
                    entityendercrystal = entityendercrystal1;
                }
            }

            this.currentEnderCrystal = entityendercrystal;
        }

    }

    private void a(List<Entity> list) {
        double d0 = (this.bF.getBoundingBox().a + this.bF.getBoundingBox().d) / 2.0D;
        double d1 = (this.bF.getBoundingBox().c + this.bF.getBoundingBox().f) / 2.0D;

        for(Entity entity : list) {
            if (entity instanceof EntityLiving) {
                double d2 = entity.locX - d0;
                double d3 = entity.locZ - d1;
                double d4 = d2 * d2 + d3 * d3;
                entity.f(d2 / d4 * 4.0D, (double)0.2F, d3 / d4 * 4.0D);
                if (!this.bS.a().a() && ((EntityLiving)entity).cg() < entity.ticksLived - 2) {
                    entity.damageEntity(DamageSource.mobAttack(this), 5.0F);
                    this.a(this, entity);
                }
            }
        }

    }

    private void b(List<Entity> list) {
        for(int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if (entity instanceof EntityLiving) {
                entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
                this.a(this, entity);
            }
        }

    }

    private float c(double d0) {
        return (float)MathHelper.g(d0);
    }

    private boolean b(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.floor(axisalignedbb.b);
        int k = MathHelper.floor(axisalignedbb.c);
        int l = MathHelper.floor(axisalignedbb.d);
        int i1 = MathHelper.floor(axisalignedbb.e);
        int j1 = MathHelper.floor(axisalignedbb.f);
        boolean flag = false;
        boolean flag1 = false;

        for(int k1 = i; k1 <= l; ++k1) {
            for(int l1 = j; l1 <= i1; ++l1) {
                for(int i2 = k; i2 <= j1; ++i2) {
                    BlockPosition blockposition = new BlockPosition(k1, l1, i2);
                    IBlockData iblockdata = this.world.getType(blockposition);
                    Block block = iblockdata.getBlock();
                    if (!iblockdata.isAir() && iblockdata.getMaterial() != Material.FIRE) {
                        if (!this.world.getGameRules().getBoolean("mobGriefing")) {
                            flag = true;
                        } else if (block != Blocks.BARRIER && block != Blocks.OBSIDIAN && block != Blocks.END_STONE && block != Blocks.BEDROCK && block != Blocks.END_PORTAL && block != Blocks.END_PORTAL_FRAME) {
                            if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && block != Blocks.END_GATEWAY) {
                                flag1 = this.world.setAir(blockposition) || flag1;
                            } else {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1) {
            double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double)this.random.nextFloat();
            double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double)this.random.nextFloat();
            double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double)this.random.nextFloat();
            this.world.addParticle(Particles.u, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }

    public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        f = this.bS.a().a(entitycomplexpart, damagesource, f);
        if (entitycomplexpart != this.bD) {
            f = f / 4.0F + Math.min(f, 1.0F);
        }

        if (f < 0.01F) {
            return false;
        } else {
            if (damagesource.getEntity() instanceof EntityHuman || damagesource.isExplosion()) {
                float f1 = this.getHealth();
                this.dealDamage(damagesource, f);
                if (this.getHealth() <= 0.0F && !this.bS.a().a()) {
                    this.setHealth(1.0F);
                    this.bS.setControllerPhase(DragonControllerPhase.j);
                }

                if (this.bS.a().a()) {
                    this.bU = (int)((float)this.bU + (f1 - this.getHealth()));
                    if ((float)this.bU > 0.25F * this.getMaxHealth()) {
                        this.bU = 0;
                        this.bS.setControllerPhase(DragonControllerPhase.e);
                    }
                }
            }

            return true;
        }
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource instanceof EntityDamageSource && ((EntityDamageSource)damagesource).y()) {
            this.a(this.bF, damagesource, f);
        }

        return false;
    }

    protected boolean dealDamage(DamageSource damagesource, float f) {
        return super.damageEntity(damagesource, f);
    }

    public void killEntity() {
        this.die();
        if (this.bR != null) {
            this.bR.b(this);
            this.bR.a(this);
        }

    }

    protected void cb() {
        if (this.bR != null) {
            this.bR.b(this);
        }

        ++this.bO;
        if (this.bO >= 180 && this.bO <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(Particles.t, this.locX + (double)f, this.locY + 2.0D + (double)f1, this.locZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
        short short1 = 500;
        if (this.bR != null && !this.bR.d()) {
            short1 = 12000;
        }

        if (!this.world.isClientSide) {
            if (this.bO > 150 && this.bO % 5 == 0 && flag) {
                this.a(MathHelper.d((float)short1 * 0.08F));
            }

            if (this.bO == 1) {
                this.world.a(1028, new BlockPosition(this), 0);
            }
        }

        this.move(EnumMoveType.SELF, 0.0D, (double)0.1F, 0.0D);
        this.yaw += 20.0F;
        this.aQ = this.yaw;
        if (this.bO == 200 && !this.world.isClientSide) {
            if (flag) {
                this.a(MathHelper.d((float)short1 * 0.2F));
            }

            if (this.bR != null) {
                this.bR.a(this);
            }

            this.die();
        }

    }

    private void a(int i) {
        while(i > 0) {
            int j = EntityExperienceOrb.getOrbValue(i);
            i -= j;
            this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
        }

    }

    public int l() {
        if (this.bV[0] == null) {
            for(int i = 0; i < 24; ++i) {
                int j = 5;
                int l;
                int i1;
                if (i < 12) {
                    l = (int)(60.0F * MathHelper.cos(2.0F * (-(float)Math.PI + 0.2617994F * (float)i)));
                    i1 = (int)(60.0F * MathHelper.sin(2.0F * (-(float)Math.PI + 0.2617994F * (float)i)));
                } else if (i < 20) {
                    int k = i - 12;
                    l = (int)(40.0F * MathHelper.cos(2.0F * (-(float)Math.PI + ((float)Math.PI / 8F) * (float)k)));
                    i1 = (int)(40.0F * MathHelper.sin(2.0F * (-(float)Math.PI + ((float)Math.PI / 8F) * (float)k)));
                    j += 10;
                } else {
                    int k1 = i - 20;
                    l = (int)(20.0F * MathHelper.cos(2.0F * (-(float)Math.PI + ((float)Math.PI / 4F) * (float)k1)));
                    i1 = (int)(20.0F * MathHelper.sin(2.0F * (-(float)Math.PI + ((float)Math.PI / 4F) * (float)k1)));
                }

                int j1 = Math.max(this.world.getSeaLevel() + 10, this.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPosition(l, 0, i1)).getY() + j);
                this.bV[i] = new PathPoint(l, j1, i1);
            }

            this.bW[0] = 6146;
            this.bW[1] = 8197;
            this.bW[2] = 8202;
            this.bW[3] = 16404;
            this.bW[4] = 32808;
            this.bW[5] = 32848;
            this.bW[6] = 65696;
            this.bW[7] = 131392;
            this.bW[8] = 131712;
            this.bW[9] = 263424;
            this.bW[10] = 526848;
            this.bW[11] = 525313;
            this.bW[12] = 1581057;
            this.bW[13] = 3166214;
            this.bW[14] = 2138120;
            this.bW[15] = 6373424;
            this.bW[16] = 4358208;
            this.bW[17] = 12910976;
            this.bW[18] = 9044480;
            this.bW[19] = 9706496;
            this.bW[20] = 15216640;
            this.bW[21] = 13688832;
            this.bW[22] = 11763712;
            this.bW[23] = 8257536;
        }

        return this.k(this.locX, this.locY, this.locZ);
    }

    public int k(double d0, double d1, double d2) {
        float f = 10000.0F;
        int i = 0;
        PathPoint pathpoint = new PathPoint(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
        byte b0 = 0;
        if (this.bR == null || this.bR.c() == 0) {
            b0 = 12;
        }

        for(int j = b0; j < 24; ++j) {
            if (this.bV[j] != null) {
                float f1 = this.bV[j].b(pathpoint);
                if (f1 < f) {
                    f = f1;
                    i = j;
                }
            }
        }

        return i;
    }

    @Nullable
    public PathEntity a(int i, int j, @Nullable PathPoint pathpoint) {
        for(int k = 0; k < 24; ++k) {
            PathPoint pathpoint1 = this.bV[k];
            pathpoint1.i = false;
            pathpoint1.g = 0.0F;
            pathpoint1.e = 0.0F;
            pathpoint1.f = 0.0F;
            pathpoint1.h = null;
            pathpoint1.d = -1;
        }

        PathPoint pathpoint5 = this.bV[i];
        PathPoint pathpoint6 = this.bV[j];
        pathpoint5.e = 0.0F;
        pathpoint5.f = pathpoint5.a(pathpoint6);
        pathpoint5.g = pathpoint5.f;
        this.bX.a();
        this.bX.a(pathpoint5);
        PathPoint pathpoint2 = pathpoint5;
        byte b0 = 0;
        if (this.bR == null || this.bR.c() == 0) {
            b0 = 12;
        }

        while(!this.bX.e()) {
            PathPoint pathpoint3 = this.bX.c();
            if (pathpoint3.equals(pathpoint6)) {
                if (pathpoint != null) {
                    pathpoint.h = pathpoint6;
                    pathpoint6 = pathpoint;
                }

                return this.a(pathpoint5, pathpoint6);
            }

            if (pathpoint3.a(pathpoint6) < pathpoint2.a(pathpoint6)) {
                pathpoint2 = pathpoint3;
            }

            pathpoint3.i = true;
            int l = 0;

            for(int i1 = 0; i1 < 24; ++i1) {
                if (this.bV[i1] == pathpoint3) {
                    l = i1;
                    break;
                }
            }

            for(int j1 = b0; j1 < 24; ++j1) {
                if ((this.bW[l] & 1 << j1) > 0) {
                    PathPoint pathpoint4 = this.bV[j1];
                    if (!pathpoint4.i) {
                        float f = pathpoint3.e + pathpoint3.a(pathpoint4);
                        if (!pathpoint4.a() || f < pathpoint4.e) {
                            pathpoint4.h = pathpoint3;
                            pathpoint4.e = f;
                            pathpoint4.f = pathpoint4.a(pathpoint6);
                            if (pathpoint4.a()) {
                                this.bX.a(pathpoint4, pathpoint4.e + pathpoint4.f);
                            } else {
                                pathpoint4.g = pathpoint4.e + pathpoint4.f;
                                this.bX.a(pathpoint4);
                            }
                        }
                    }
                }
            }
        }

        if (pathpoint2 == pathpoint5) {
            return null;
        } else {
            bQ.debug("Failed to find path from {} to {}", i, j);
            if (pathpoint != null) {
                pathpoint.h = pathpoint2;
                pathpoint2 = pathpoint;
            }

            return this.a(pathpoint5, pathpoint2);
        }
    }

    private PathEntity a(PathPoint var1, PathPoint pathpoint) {
        int i = 1;

        for(PathPoint pathpoint1 = pathpoint; pathpoint1.h != null; pathpoint1 = pathpoint1.h) {
            ++i;
        }

        PathPoint[] apathpoint = new PathPoint[i];
        PathPoint pathpoint2 = pathpoint;
        --i;

        for(apathpoint[i] = pathpoint; pathpoint2.h != null; apathpoint[i] = pathpoint2) {
            pathpoint2 = pathpoint2.h;
            --i;
        }

        return new PathEntity(apathpoint);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("DragonPhase", this.bS.a().getControllerPhase().b());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("DragonPhase")) {
            this.bS.setControllerPhase(DragonControllerPhase.getById(nbttagcompound.getInt("DragonPhase")));
        }

    }

    protected void I() {
    }

    public Entity[] bi() {
        return this.children;
    }

    public boolean isInteractable() {
        return false;
    }

    public World J_() {
        return this.world;
    }

    public SoundCategory bV() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_ENDER_DRAGON_HURT;
    }

    protected float cD() {
        return 5.0F;
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.aH;
    }

    public Vec3D a(float f) {
        IDragonController idragoncontroller = this.bS.a();
        DragonControllerPhase dragoncontrollerphase = idragoncontroller.getControllerPhase();
        Vec3D vec3d;
        if (dragoncontrollerphase != DragonControllerPhase.d && dragoncontrollerphase != DragonControllerPhase.e) {
            if (idragoncontroller.a()) {
                float f5 = this.pitch;
                float f6 = 1.5F;
                this.pitch = -45.0F;
                vec3d = this.f(f);
                this.pitch = f5;
            } else {
                vec3d = this.f(f);
            }
        } else {
            BlockPosition blockposition = this.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, WorldGenEndTrophy.a);
            float f1 = Math.max(MathHelper.sqrt(this.d(blockposition)) / 4.0F, 1.0F);
            float f2 = 6.0F / f1;
            float f3 = this.pitch;
            float f4 = 1.5F;
            this.pitch = -f2 * 1.5F * 5.0F;
            vec3d = this.f(f);
            this.pitch = f3;
        }

        return vec3d;
    }

    public void a(EntityEnderCrystal entityendercrystal, BlockPosition blockposition, DamageSource damagesource) {
        EntityHuman entityhuman;
        if (damagesource.getEntity() instanceof EntityHuman) {
            entityhuman = (EntityHuman)damagesource.getEntity();
        } else {
            entityhuman = this.world.a(blockposition, 64.0D, 64.0D);
        }

        if (entityendercrystal == this.currentEnderCrystal) {
            this.a(this.bD, DamageSource.b(entityhuman), 10.0F);
        }

        this.bS.a().a(entityendercrystal, blockposition, damagesource, entityhuman);
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (PHASE.equals(datawatcherobject) && this.world.isClientSide) {
            this.bS.setControllerPhase(DragonControllerPhase.getById(this.getDataWatcher().get(PHASE)));
        }

        super.a(datawatcherobject);
    }

    public DragonControllerManager getDragonControllerManager() {
        return this.bS;
    }

    @Nullable
    public EnderDragonBattle ds() {
        return this.bR;
    }

    public boolean addEffect(MobEffect var1) {
        return false;
    }

    protected boolean n(Entity var1) {
        return false;
    }

    public boolean bm() {
        return false;
    }
}
