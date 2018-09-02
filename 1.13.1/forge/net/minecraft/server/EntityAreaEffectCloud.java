package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAreaEffectCloud extends Entity {
    private static final Logger a = LogManager.getLogger();
    private static final DataWatcherObject<Float> b = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.c);
    private static final DataWatcherObject<Integer> c = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<ParticleParam> e = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.j);
    private PotionRegistry potionRegistry;
    public List<MobEffect> effects;
    private final Map<Entity, Integer> h;
    private int aw;
    public int waitTime;
    public int reapplicationDelay;
    private boolean hasColor;
    public int durationOnUse;
    public float radiusOnUse;
    public float radiusPerTick;
    private EntityLiving aD;
    private UUID aE;

    public EntityAreaEffectCloud(World world) {
        super(EntityTypes.AREA_EFFECT_CLOUD, world);
        this.potionRegistry = Potions.EMPTY;
        this.effects = Lists.newArrayList();
        this.h = Maps.newHashMap();
        this.aw = 600;
        this.waitTime = 20;
        this.reapplicationDelay = 20;
        this.noclip = true;
        this.fireProof = true;
        this.setRadius(3.0F);
    }

    public EntityAreaEffectCloud(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
    }

    protected void x_() {
        this.getDataWatcher().register(c, 0);
        this.getDataWatcher().register(b, 0.5F);
        this.getDataWatcher().register(d, false);
        this.getDataWatcher().register(e, Particles.s);
    }

    public void setRadius(float f) {
        double d0 = this.locX;
        double d1 = this.locY;
        double d2 = this.locZ;
        this.setSize(f * 2.0F, 0.5F);
        this.setPosition(d0, d1, d2);
        if (!this.world.isClientSide) {
            this.getDataWatcher().set(b, f);
        }

    }

    public float getRadius() {
        return this.getDataWatcher().get(b);
    }

    public void a(PotionRegistry potionregistry) {
        this.potionRegistry = potionregistry;
        if (!this.hasColor) {
            this.x();
        }

    }

    private void x() {
        if (this.potionRegistry == Potions.EMPTY && this.effects.isEmpty()) {
            this.getDataWatcher().set(c, 0);
        } else {
            this.getDataWatcher().set(c, PotionUtil.a(PotionUtil.a(this.potionRegistry, this.effects)));
        }

    }

    public void a(MobEffect mobeffect) {
        this.effects.add(mobeffect);
        if (!this.hasColor) {
            this.x();
        }

    }

    public int getColor() {
        return this.getDataWatcher().get(c);
    }

    public void setColor(int i) {
        this.hasColor = true;
        this.getDataWatcher().set(c, i);
    }

    public ParticleParam getParticle() {
        return (ParticleParam)this.getDataWatcher().get(e);
    }

    public void setParticle(ParticleParam particleparam) {
        this.getDataWatcher().set(e, particleparam);
    }

    protected void a(boolean flag) {
        this.getDataWatcher().set(d, flag);
    }

    public boolean l() {
        return this.getDataWatcher().get(d);
    }

    public int getDuration() {
        return this.aw;
    }

    public void setDuration(int i) {
        this.aw = i;
    }

    public void tick() {
        super.tick();
        boolean flag = this.l();
        float f = this.getRadius();
        if (this.world.isClientSide) {
            ParticleParam particleparam = this.getParticle();
            if (flag) {
                if (this.random.nextBoolean()) {
                    for(int i = 0; i < 2; ++i) {
                        float f1 = this.random.nextFloat() * ((float)Math.PI * 2F);
                        float f2 = MathHelper.c(this.random.nextFloat()) * 0.2F;
                        float f3 = MathHelper.cos(f1) * f2;
                        float f4 = MathHelper.sin(f1) * f2;
                        if (particleparam.b() == Particles.s) {
                            int j = this.random.nextBoolean() ? 16777215 : this.getColor();
                            int k = j >> 16 & 255;
                            int l = j >> 8 & 255;
                            int i1 = j & 255;
                            this.world.b(particleparam, this.locX + (double)f3, this.locY, this.locZ + (double)f4, (double)((float)k / 255.0F), (double)((float)l / 255.0F), (double)((float)i1 / 255.0F));
                        } else {
                            this.world.b(particleparam, this.locX + (double)f3, this.locY, this.locZ + (double)f4, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            } else {
                float f5 = (float)Math.PI * f * f;

                for(int k1 = 0; (float)k1 < f5; ++k1) {
                    float f6 = this.random.nextFloat() * ((float)Math.PI * 2F);
                    float f7 = MathHelper.c(this.random.nextFloat()) * f;
                    float f8 = MathHelper.cos(f6) * f7;
                    float f9 = MathHelper.sin(f6) * f7;
                    if (particleparam.b() == Particles.s) {
                        int l1 = this.getColor();
                        int i2 = l1 >> 16 & 255;
                        int j2 = l1 >> 8 & 255;
                        int j1 = l1 & 255;
                        this.world.b(particleparam, this.locX + (double)f8, this.locY, this.locZ + (double)f9, (double)((float)i2 / 255.0F), (double)((float)j2 / 255.0F), (double)((float)j1 / 255.0F));
                    } else {
                        this.world.b(particleparam, this.locX + (double)f8, this.locY, this.locZ + (double)f9, (0.5D - this.random.nextDouble()) * 0.15D, (double)0.01F, (0.5D - this.random.nextDouble()) * 0.15D);
                    }
                }
            }
        } else {
            if (this.ticksLived >= this.waitTime + this.aw) {
                this.die();
                return;
            }

            boolean flag1 = this.ticksLived < this.waitTime;
            if (flag != flag1) {
                this.a(flag1);
            }

            if (flag1) {
                return;
            }

            if (this.radiusPerTick != 0.0F) {
                f += this.radiusPerTick;
                if (f < 0.5F) {
                    this.die();
                    return;
                }

                this.setRadius(f);
            }

            if (this.ticksLived % 5 == 0) {
                Iterator iterator = this.h.entrySet().iterator();

                while(iterator.hasNext()) {
                    Entry entry = (Entry)iterator.next();
                    if (this.ticksLived >= entry.getValue()) {
                        iterator.remove();
                    }
                }

                ArrayList arraylist = Lists.newArrayList();

                for(MobEffect mobeffect1 : this.potionRegistry.a()) {
                    arraylist.add(new MobEffect(mobeffect1.getMobEffect(), mobeffect1.getDuration() / 4, mobeffect1.getAmplifier(), mobeffect1.isAmbient(), mobeffect1.isShowParticles()));
                }

                arraylist.addAll(this.effects);
                if (arraylist.isEmpty()) {
                    this.h.clear();
                } else {
                    List list = this.world.a(EntityLiving.class, this.getBoundingBox());
                    if (!list.isEmpty()) {
                        for(EntityLiving entityliving : list) {
                            if (!this.h.containsKey(entityliving) && entityliving.de()) {
                                double d0 = entityliving.locX - this.locX;
                                double d1 = entityliving.locZ - this.locZ;
                                double d2 = d0 * d0 + d1 * d1;
                                if (d2 <= (double)(f * f)) {
                                    this.h.put(entityliving, this.ticksLived + this.reapplicationDelay);

                                    for(MobEffect mobeffect : arraylist) {
                                        if (mobeffect.getMobEffect().isInstant()) {
                                            mobeffect.getMobEffect().applyInstantEffect(this, this.getSource(), entityliving, mobeffect.getAmplifier(), 0.5D);
                                        } else {
                                            entityliving.addEffect(new MobEffect(mobeffect));
                                        }
                                    }

                                    if (this.radiusOnUse != 0.0F) {
                                        f += this.radiusOnUse;
                                        if (f < 0.5F) {
                                            this.die();
                                            return;
                                        }

                                        this.setRadius(f);
                                    }

                                    if (this.durationOnUse != 0) {
                                        this.aw += this.durationOnUse;
                                        if (this.aw <= 0) {
                                            this.die();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void setRadiusOnUse(float f) {
        this.radiusOnUse = f;
    }

    public void setRadiusPerTick(float f) {
        this.radiusPerTick = f;
    }

    public void setWaitTime(int i) {
        this.waitTime = i;
    }

    public void setSource(@Nullable EntityLiving entityliving) {
        this.aD = entityliving;
        this.aE = entityliving == null ? null : entityliving.getUniqueID();
    }

    @Nullable
    public EntityLiving getSource() {
        if (this.aD == null && this.aE != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntity(this.aE);
            if (entity instanceof EntityLiving) {
                this.aD = (EntityLiving)entity;
            }
        }

        return this.aD;
    }

    protected void a(NBTTagCompound nbttagcompound) {
        this.ticksLived = nbttagcompound.getInt("Age");
        this.aw = nbttagcompound.getInt("Duration");
        this.waitTime = nbttagcompound.getInt("WaitTime");
        this.reapplicationDelay = nbttagcompound.getInt("ReapplicationDelay");
        this.durationOnUse = nbttagcompound.getInt("DurationOnUse");
        this.radiusOnUse = nbttagcompound.getFloat("RadiusOnUse");
        this.radiusPerTick = nbttagcompound.getFloat("RadiusPerTick");
        this.setRadius(nbttagcompound.getFloat("Radius"));
        this.aE = nbttagcompound.a("OwnerUUID");
        if (nbttagcompound.hasKeyOfType("Particle", 8)) {
            try {
                this.setParticle(ArgumentParticle.b(new StringReader(nbttagcompound.getString("Particle"))));
            } catch (CommandSyntaxException commandsyntaxexception) {
                a.warn("Couldn't load custom particle {}", nbttagcompound.getString("Particle"), commandsyntaxexception);
            }
        }

        if (nbttagcompound.hasKeyOfType("Color", 99)) {
            this.setColor(nbttagcompound.getInt("Color"));
        }

        if (nbttagcompound.hasKeyOfType("Potion", 8)) {
            this.a(PotionUtil.c(nbttagcompound));
        }

        if (nbttagcompound.hasKeyOfType("Effects", 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList("Effects", 10);
            this.effects.clear();

            for(int i = 0; i < nbttaglist.size(); ++i) {
                MobEffect mobeffect = MobEffect.b(nbttaglist.getCompound(i));
                if (mobeffect != null) {
                    this.a(mobeffect);
                }
            }
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("Age", this.ticksLived);
        nbttagcompound.setInt("Duration", this.aw);
        nbttagcompound.setInt("WaitTime", this.waitTime);
        nbttagcompound.setInt("ReapplicationDelay", this.reapplicationDelay);
        nbttagcompound.setInt("DurationOnUse", this.durationOnUse);
        nbttagcompound.setFloat("RadiusOnUse", this.radiusOnUse);
        nbttagcompound.setFloat("RadiusPerTick", this.radiusPerTick);
        nbttagcompound.setFloat("Radius", this.getRadius());
        nbttagcompound.setString("Particle", this.getParticle().a());
        if (this.aE != null) {
            nbttagcompound.a("OwnerUUID", this.aE);
        }

        if (this.hasColor) {
            nbttagcompound.setInt("Color", this.getColor());
        }

        if (this.potionRegistry != Potions.EMPTY && this.potionRegistry != null) {
            nbttagcompound.setString("Potion", IRegistry.POTION.getKey(this.potionRegistry).toString());
        }

        if (!this.effects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(MobEffect mobeffect : this.effects) {
                nbttaglist.add((NBTBase)mobeffect.a(new NBTTagCompound()));
            }

            nbttagcompound.set("Effects", nbttaglist);
        }

    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (b.equals(datawatcherobject)) {
            this.setRadius(this.getRadius());
        }

        super.a(datawatcherobject);
    }

    public EnumPistonReaction getPushReaction() {
        return EnumPistonReaction.IGNORE;
    }
}
