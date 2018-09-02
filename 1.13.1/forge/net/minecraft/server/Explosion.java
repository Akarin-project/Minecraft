package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class Explosion {
    private final boolean a;
    private final boolean b;
    private final Random c = new Random();
    private final World world;
    private final double posX;
    private final double posY;
    private final double posZ;
    public final Entity source;
    private final float size;
    private DamageSource j;
    private final List<BlockPosition> blocks = Lists.newArrayList();
    private final Map<EntityHuman, Vec3D> l = Maps.newHashMap();

    public Explosion(World worldx, @Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        this.world = worldx;
        this.source = entity;
        this.size = f;
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        this.a = flag;
        this.b = flag1;
        this.j = DamageSource.explosion(this);
    }

    public void a() {
        HashSet hashset = Sets.newHashSet();
        boolean flag = true;

        for(int i = 0; i < 16; ++i) {
            for(int jx = 0; jx < 16; ++jx) {
                for(int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || jx == 0 || jx == 15 || k == 0 || k == 15) {
                        double d0 = (double)((float)i / 15.0F * 2.0F - 1.0F);
                        double d1 = (double)((float)jx / 15.0F * 2.0F - 1.0F);
                        double d2 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
                        double d4 = this.posX;
                        double d6 = this.posY;
                        double d8 = this.posZ;

                        for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPosition blockposition = new BlockPosition(d4, d6, d8);
                            IBlockData iblockdata = this.world.getType(blockposition);
                            Fluid fluid = this.world.b(blockposition);
                            if (!iblockdata.isAir() || !fluid.e()) {
                                float f2 = Math.max(iblockdata.getBlock().getDurability(), fluid.l());
                                if (this.source != null) {
                                    f2 = this.source.a(this, this.world, blockposition, iblockdata, fluid, f2);
                                }

                                f -= (f2 + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && (this.source == null || this.source.a(this, this.world, blockposition, iblockdata, f))) {
                                hashset.add(blockposition);
                            }

                            d4 += d0 * (double)0.3F;
                            d6 += d1 * (double)0.3F;
                            d8 += d2 * (double)0.3F;
                        }
                    }
                }
            }
        }

        this.blocks.addAll(hashset);
        float f3 = this.size * 2.0F;
        int j1 = MathHelper.floor(this.posX - (double)f3 - 1.0D);
        int k1 = MathHelper.floor(this.posX + (double)f3 + 1.0D);
        int l1 = MathHelper.floor(this.posY - (double)f3 - 1.0D);
        int lx = MathHelper.floor(this.posY + (double)f3 + 1.0D);
        int i2 = MathHelper.floor(this.posZ - (double)f3 - 1.0D);
        int i1 = MathHelper.floor(this.posZ + (double)f3 + 1.0D);
        List list = this.world.getEntities(this.source, new AxisAlignedBB((double)j1, (double)l1, (double)i2, (double)k1, (double)lx, (double)i1));
        Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);

        for(int j2 = 0; j2 < list.size(); ++j2) {
            Entity entity = (Entity)list.get(j2);
            if (!entity.bL()) {
                double d12 = entity.e(this.posX, this.posY, this.posZ) / (double)f3;
                if (d12 <= 1.0D) {
                    double d5 = entity.locX - this.posX;
                    double d7 = entity.locY + (double)entity.getHeadHeight() - this.posY;
                    double d9 = entity.locZ - this.posZ;
                    double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0D) {
                        d5 = d5 / d13;
                        d7 = d7 / d13;
                        d9 = d9 / d13;
                        double d14 = (double)this.world.a(vec3d, entity.getBoundingBox());
                        double d10 = (1.0D - d12) * d14;
                        entity.damageEntity(this.b(), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof EntityLiving) {
                            d11 = EnchantmentProtection.a((EntityLiving)entity, d10);
                        }

                        entity.motX += d5 * d11;
                        entity.motY += d7 * d11;
                        entity.motZ += d9 * d11;
                        if (entity instanceof EntityHuman) {
                            EntityHuman entityhuman = (EntityHuman)entity;
                            if (!entityhuman.isSpectator() && (!entityhuman.u() || !entityhuman.abilities.isFlying)) {
                                this.l.put(entityhuman, new Vec3D(d5 * d10, d7 * d10, d9 * d10));
                            }
                        }
                    }
                }
            }
        }

    }

    public void a(boolean flag) {
        this.world.a((EntityHuman)null, this.posX, this.posY, this.posZ, SoundEffects.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        if (!(this.size < 2.0F) && this.b) {
            this.world.addParticle(Particles.t, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        } else {
            this.world.addParticle(Particles.u, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        }

        if (this.b) {
            for(BlockPosition blockposition : this.blocks) {
                IBlockData iblockdata = this.world.getType(blockposition);
                Block block = iblockdata.getBlock();
                if (flag) {
                    double d0 = (double)((float)blockposition.getX() + this.world.random.nextFloat());
                    double d1 = (double)((float)blockposition.getY() + this.world.random.nextFloat());
                    double d2 = (double)((float)blockposition.getZ() + this.world.random.nextFloat());
                    double d3 = d0 - this.posX;
                    double d4 = d1 - this.posY;
                    double d5 = d2 - this.posZ;
                    double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 = d3 / d6;
                    d4 = d4 / d6;
                    d5 = d5 / d6;
                    double d7 = 0.5D / (d6 / (double)this.size + 0.1D);
                    d7 = d7 * (double)(this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F);
                    d3 = d3 * d7;
                    d4 = d4 * d7;
                    d5 = d5 * d7;
                    this.world.addParticle(Particles.J, (d0 + this.posX) / 2.0D, (d1 + this.posY) / 2.0D, (d2 + this.posZ) / 2.0D, d3, d4, d5);
                    this.world.addParticle(Particles.M, d0, d1, d2, d3, d4, d5);
                }

                if (!iblockdata.isAir()) {
                    if (block.a(this)) {
                        iblockdata.dropNaturally(this.world, blockposition, 1.0F / this.size, 0);
                    }

                    this.world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
                    block.wasExploded(this.world, blockposition, this);
                }
            }
        }

        if (this.a) {
            for(BlockPosition blockposition1 : this.blocks) {
                if (this.world.getType(blockposition1).isAir() && this.world.getType(blockposition1.down()).f(this.world, blockposition1.down()) && this.c.nextInt(3) == 0) {
                    this.world.setTypeUpdate(blockposition1, Blocks.FIRE.getBlockData());
                }
            }
        }

    }

    public DamageSource b() {
        return this.j;
    }

    public void a(DamageSource damagesource) {
        this.j = damagesource;
    }

    public Map<EntityHuman, Vec3D> c() {
        return this.l;
    }

    @Nullable
    public EntityLiving getSource() {
        if (this.source == null) {
            return null;
        } else if (this.source instanceof EntityTNTPrimed) {
            return ((EntityTNTPrimed)this.source).getSource();
        } else {
            return this.source instanceof EntityLiving ? (EntityLiving)this.source : null;
        }
    }

    public void clearBlocks() {
        this.blocks.clear();
    }

    public List<BlockPosition> getBlocks() {
        return this.blocks;
    }
}
