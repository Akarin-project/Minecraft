package net.minecraft.server;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class EntityArrow extends Entity implements IProjectile {
    private static final Predicate<Entity> g = IEntitySelector.f.and(IEntitySelector.a.and(Entity::isInteractable));
    private static final DataWatcherObject<Byte> h = DataWatcher.a(EntityArrow.class, DataWatcherRegistry.a);
    protected static final DataWatcherObject<Optional<UUID>> a = DataWatcher.a(EntityArrow.class, DataWatcherRegistry.o);
    public int tileX;
    public int tileY;
    public int tileZ;
    @Nullable
    private IBlockData az;
    public boolean inGround;
    protected int c;
    public EntityArrow.PickupStatus fromPlayer;
    public int shake;
    public UUID shooter;
    private int despawnCounter;
    private int aB;
    private double damage;
    public int knockbackStrength;

    protected EntityArrow(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
        this.tileX = -1;
        this.tileY = -1;
        this.tileZ = -1;
        this.fromPlayer = EntityArrow.PickupStatus.DISALLOWED;
        this.damage = 2.0D;
        this.setSize(0.5F, 0.5F);
    }

    protected EntityArrow(EntityTypes<?> entitytypes, double d0, double d1, double d2, World world) {
        this(entitytypes, world);
        this.setPosition(d0, d1, d2);
    }

    protected EntityArrow(EntityTypes<?> entitytypes, EntityLiving entityliving, World world) {
        this(entitytypes, entityliving.locX, entityliving.locY + (double)entityliving.getHeadHeight() - (double)0.1F, entityliving.locZ, world);
        this.setShooter(entityliving);
        if (entityliving instanceof EntityHuman) {
            this.fromPlayer = EntityArrow.PickupStatus.ALLOWED;
        }

    }

    protected void x_() {
        this.datawatcher.register(h, (byte)0);
        this.datawatcher.register(a, Optional.empty());
    }

    public void a(Entity entity, float f, float f1, float var4, float f2, float f3) {
        float f4 = -MathHelper.sin(f1 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
        float f5 = -MathHelper.sin(f * ((float)Math.PI / 180F));
        float f6 = MathHelper.cos(f1 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
        this.shoot((double)f4, (double)f5, (double)f6, f2, f3);
        this.motX += entity.motX;
        this.motZ += entity.motZ;
        if (!entity.onGround) {
            this.motY += entity.motY;
        }

    }

    public void shoot(double d0, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / (double)f2;
        d1 = d1 / (double)f2;
        d2 = d2 / (double)f2;
        d0 = d0 + this.random.nextGaussian() * (double)0.0075F * (double)f1;
        d1 = d1 + this.random.nextGaussian() * (double)0.0075F * (double)f1;
        d2 = d2 + this.random.nextGaussian() * (double)0.0075F * (double)f1;
        d0 = d0 * (double)f;
        d1 = d1 * (double)f;
        d2 = d2 * (double)f;
        this.motX = d0;
        this.motY = d1;
        this.motZ = d2;
        float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        this.yaw = (float)(MathHelper.c(d0, d2) * (double)(180F / (float)Math.PI));
        this.pitch = (float)(MathHelper.c(d1, (double)f3) * (double)(180F / (float)Math.PI));
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.despawnCounter = 0;
    }

    public void tick() {
        super.tick();
        boolean flag = this.q();
        if (this.lastPitch == 0.0F && this.lastYaw == 0.0F) {
            float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(MathHelper.c(this.motX, this.motZ) * (double)(180F / (float)Math.PI));
            this.pitch = (float)(MathHelper.c(this.motY, (double)f) * (double)(180F / (float)Math.PI));
            this.lastYaw = this.yaw;
            this.lastPitch = this.pitch;
        }

        BlockPosition blockposition = new BlockPosition(this.tileX, this.tileY, this.tileZ);
        IBlockData iblockdata = this.world.getType(blockposition);
        if (!iblockdata.isAir() && !flag) {
            VoxelShape voxelshape = iblockdata.h(this.world, blockposition);
            if (!voxelshape.b()) {
                for(AxisAlignedBB axisalignedbb : voxelshape.d()) {
                    if (axisalignedbb.a(blockposition).b(new Vec3D(this.locX, this.locY, this.locZ))) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shake > 0) {
            --this.shake;
        }

        if (this.ao()) {
            this.extinguish();
        }

        if (this.inGround && !flag) {
            if (this.az != iblockdata && this.world.getCubes((Entity)null, this.getBoundingBox().g(0.05D))) {
                this.inGround = false;
                this.motX *= (double)(this.random.nextFloat() * 0.2F);
                this.motY *= (double)(this.random.nextFloat() * 0.2F);
                this.motZ *= (double)(this.random.nextFloat() * 0.2F);
                this.despawnCounter = 0;
                this.aB = 0;
            } else {
                this.f();
            }

            ++this.c;
        } else {
            this.c = 0;
            ++this.aB;
            Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, FluidCollisionOption.NEVER, true, false);
            vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            if (movingobjectposition != null) {
                vec3d1 = new Vec3D(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
            }

            Entity entity = this.a(vec3d, vec3d1);
            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entity instanceof EntityHuman) {
                EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
                Entity entity1 = this.getShooter();
                if (entity1 instanceof EntityHuman && !((EntityHuman)entity1).a(entityhuman)) {
                    movingobjectposition = null;
                }
            }

            if (movingobjectposition != null && !flag) {
                this.a(movingobjectposition);
                this.impulse = true;
            }

            if (this.isCritical()) {
                for(int j = 0; j < 4; ++j) {
                    this.world.addParticle(Particles.h, this.locX + this.motX * (double)j / 4.0D, this.locY + this.motY * (double)j / 4.0D, this.locZ + this.motZ * (double)j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
                }
            }

            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (flag) {
                this.yaw = (float)(MathHelper.c(-this.motX, -this.motZ) * (double)(180F / (float)Math.PI));
            } else {
                this.yaw = (float)(MathHelper.c(this.motX, this.motZ) * (double)(180F / (float)Math.PI));
            }

            for(this.pitch = (float)(MathHelper.c(this.motY, (double)f3) * (double)(180F / (float)Math.PI)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
                ;
            }

            while(this.pitch - this.lastPitch >= 180.0F) {
                this.lastPitch += 360.0F;
            }

            while(this.yaw - this.lastYaw < -180.0F) {
                this.lastYaw -= 360.0F;
            }

            while(this.yaw - this.lastYaw >= 180.0F) {
                this.lastYaw += 360.0F;
            }

            this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
            this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
            float f4 = 0.99F;
            float f1 = 0.05F;
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f2 = 0.25F;
                    this.world.addParticle(Particles.e, this.locX - this.motX * 0.25D, this.locY - this.motY * 0.25D, this.locZ - this.motZ * 0.25D, this.motX, this.motY, this.motZ);
                }

                f4 = this.p();
            }

            this.motX *= (double)f4;
            this.motY *= (double)f4;
            this.motZ *= (double)f4;
            if (!this.isNoGravity() && !flag) {
                this.motY -= (double)0.05F;
            }

            this.setPosition(this.locX, this.locY, this.locZ);
            this.checkBlockCollisions();
        }
    }

    protected void f() {
        ++this.despawnCounter;
        if (this.despawnCounter >= 1200) {
            this.die();
        }

    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null) {
            this.b(movingobjectposition);
        } else {
            BlockPosition blockposition = movingobjectposition.a();
            this.tileX = blockposition.getX();
            this.tileY = blockposition.getY();
            this.tileZ = blockposition.getZ();
            IBlockData iblockdata = this.world.getType(blockposition);
            this.az = iblockdata;
            this.motX = (double)((float)(movingobjectposition.pos.x - this.locX));
            this.motY = (double)((float)(movingobjectposition.pos.y - this.locY));
            this.motZ = (double)((float)(movingobjectposition.pos.z - this.locZ));
            float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ) * 20.0F;
            this.locX -= this.motX / (double)f;
            this.locY -= this.motY / (double)f;
            this.locZ -= this.motZ / (double)f;
            this.a(this.i(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.shake = 7;
            this.setCritical(false);
            if (!iblockdata.isAir()) {
                this.az.a(this.world, blockposition, this);
            }
        }

    }

    protected void b(MovingObjectPosition movingobjectposition) {
        Entity entity = movingobjectposition.entity;
        float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
        int i = MathHelper.f((double)f * this.damage);
        if (this.isCritical()) {
            i += this.random.nextInt(i / 2 + 2);
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
        }

        if (this.isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setOnFire(5);
        }

        if (entity.damageEntity(damagesource, (float)i)) {
            if (entity instanceof EntityLiving) {
                EntityLiving entityliving = (EntityLiving)entity;
                if (!this.world.isClientSide) {
                    entityliving.setArrowCount(entityliving.getArrowCount() + 1);
                }

                if (this.knockbackStrength > 0) {
                    float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    if (f1 > 0.0F) {
                        entityliving.f(this.motX * (double)this.knockbackStrength * (double)0.6F / (double)f1, 0.1D, this.motZ * (double)this.knockbackStrength * (double)0.6F / (double)f1);
                    }
                }

                if (entity1 instanceof EntityLiving) {
                    EnchantmentManager.a(entityliving, entity1);
                    EnchantmentManager.b((EntityLiving)entity1, entityliving);
                }

                this.a(entityliving);
                if (entity1 != null && entityliving != entity1 && entityliving instanceof EntityHuman && entity1 instanceof EntityPlayer) {
                    ((EntityPlayer)entity1).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
                }
            }

            this.a(SoundEffects.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (!(entity instanceof EntityEnderman)) {
                this.die();
            }
        } else {
            this.motX *= (double)-0.1F;
            this.motY *= (double)-0.1F;
            this.motZ *= (double)-0.1F;
            this.yaw += 180.0F;
            this.lastYaw += 180.0F;
            this.aB = 0;
            if (!this.world.isClientSide && this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ < (double)0.001F) {
                if (this.fromPlayer == EntityArrow.PickupStatus.ALLOWED) {
                    this.a(this.getItemStack(), 0.1F);
                }

                this.die();
            }
        }

    }

    protected SoundEffect i() {
        return SoundEffects.ENTITY_ARROW_HIT;
    }

    public void move(EnumMoveType enummovetype, double d0, double d1, double d2) {
        super.move(enummovetype, d0, d1, d2);
        if (this.inGround) {
            this.tileX = MathHelper.floor(this.locX);
            this.tileY = MathHelper.floor(this.locY);
            this.tileZ = MathHelper.floor(this.locZ);
        }

    }

    protected void a(EntityLiving var1) {
    }

    @Nullable
    protected Entity a(Vec3D vec3d, Vec3D vec3d1) {
        Entity entity = null;
        List list = this.world.getEntities(this, this.getBoundingBox().b(this.motX, this.motY, this.motZ).g(1.0D), g);
        double d0 = 0.0D;

        for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            if (entity1 != this.getShooter() || this.aB >= 5) {
                AxisAlignedBB axisalignedbb = entity1.getBoundingBox().g((double)0.3F);
                MovingObjectPosition movingobjectposition = axisalignedbb.b(vec3d, vec3d1);
                if (movingobjectposition != null) {
                    double d1 = vec3d.distanceSquared(movingobjectposition.pos);
                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("xTile", this.tileX);
        nbttagcompound.setInt("yTile", this.tileY);
        nbttagcompound.setInt("zTile", this.tileZ);
        nbttagcompound.setShort("life", (short)this.despawnCounter);
        if (this.az != null) {
            nbttagcompound.set("inBlockState", GameProfileSerializer.a(this.az));
        }

        nbttagcompound.setByte("shake", (byte)this.shake);
        nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbttagcompound.setByte("pickup", (byte)this.fromPlayer.ordinal());
        nbttagcompound.setDouble("damage", this.damage);
        nbttagcompound.setBoolean("crit", this.isCritical());
        if (this.shooter != null) {
            nbttagcompound.a("OwnerUUID", this.shooter);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.tileX = nbttagcompound.getInt("xTile");
        this.tileY = nbttagcompound.getInt("yTile");
        this.tileZ = nbttagcompound.getInt("zTile");
        this.despawnCounter = nbttagcompound.getShort("life");
        if (nbttagcompound.hasKeyOfType("inBlockState", 10)) {
            this.az = GameProfileSerializer.d(nbttagcompound.getCompound("inBlockState"));
        }

        this.shake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        if (nbttagcompound.hasKeyOfType("damage", 99)) {
            this.damage = nbttagcompound.getDouble("damage");
        }

        if (nbttagcompound.hasKeyOfType("pickup", 99)) {
            this.fromPlayer = EntityArrow.PickupStatus.a(nbttagcompound.getByte("pickup"));
        } else if (nbttagcompound.hasKeyOfType("player", 99)) {
            this.fromPlayer = nbttagcompound.getBoolean("player") ? EntityArrow.PickupStatus.ALLOWED : EntityArrow.PickupStatus.DISALLOWED;
        }

        this.setCritical(nbttagcompound.getBoolean("crit"));
        if (nbttagcompound.b("OwnerUUID")) {
            this.shooter = nbttagcompound.a("OwnerUUID");
        }

    }

    public void setShooter(@Nullable Entity entity) {
        this.shooter = entity == null ? null : entity.getUniqueID();
    }

    @Nullable
    public Entity getShooter() {
        return this.shooter != null && this.world instanceof WorldServer ? ((WorldServer)this.world).getEntity(this.shooter) : null;
    }

    public void d(EntityHuman entityhuman) {
        if (!this.world.isClientSide && (this.inGround || this.q()) && this.shake <= 0) {
            boolean flag = this.fromPlayer == EntityArrow.PickupStatus.ALLOWED || this.fromPlayer == EntityArrow.PickupStatus.CREATIVE_ONLY && entityhuman.abilities.canInstantlyBuild || this.q() && this.getShooter().getUniqueID() == entityhuman.getUniqueID();
            if (this.fromPlayer == EntityArrow.PickupStatus.ALLOWED && !entityhuman.inventory.pickup(this.getItemStack())) {
                flag = false;
            }

            if (flag) {
                entityhuman.receive(this, 1);
                this.die();
            }

        }
    }

    protected abstract ItemStack getItemStack();

    protected boolean playStepSound() {
        return false;
    }

    public void setDamage(double d0) {
        this.damage = d0;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setKnockbackStrength(int i) {
        this.knockbackStrength = i;
    }

    public boolean bk() {
        return false;
    }

    public float getHeadHeight() {
        return 0.0F;
    }

    public void setCritical(boolean flag) {
        this.a(1, flag);
    }

    private void a(int i, boolean flag) {
        byte b0 = this.datawatcher.get(h);
        if (flag) {
            this.datawatcher.set(h, (byte)(b0 | i));
        } else {
            this.datawatcher.set(h, (byte)(b0 & ~i));
        }

    }

    public boolean isCritical() {
        byte b0 = this.datawatcher.get(h);
        return (b0 & 1) != 0;
    }

    public void a(EntityLiving entityliving, float f) {
        int i = EnchantmentManager.a(Enchantments.ARROW_DAMAGE, entityliving);
        int j = EnchantmentManager.a(Enchantments.ARROW_KNOCKBACK, entityliving);
        this.setDamage((double)(f * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().a() * 0.11F));
        if (i > 0) {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0) {
            this.setKnockbackStrength(j);
        }

        if (EnchantmentManager.a(Enchantments.ARROW_FIRE, entityliving) > 0) {
            this.setOnFire(100);
        }

    }

    protected float p() {
        return 0.6F;
    }

    public void o(boolean flag) {
        this.noclip = flag;
        this.a(2, flag);
    }

    public boolean q() {
        if (!this.world.isClientSide) {
            return this.noclip;
        } else {
            return (this.datawatcher.get(h) & 2) != 0;
        }
    }

    public static enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY;

        private PickupStatus() {
        }

        public static EntityArrow.PickupStatus a(int i) {
            if (i < 0 || i > values().length) {
                i = 0;
            }

            return values()[i];
        }
    }
}
