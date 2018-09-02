package net.minecraft.server;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class EntityLlamaSpit extends Entity implements IProjectile {
    public EntityLlama shooter;
    private NBTTagCompound b;

    public EntityLlamaSpit(World world) {
        super(EntityTypes.LLAMA_SPIT, world);
        this.setSize(0.25F, 0.25F);
    }

    public EntityLlamaSpit(World world, EntityLlama entityllama) {
        this(world);
        this.shooter = entityllama;
        this.setPosition(entityllama.locX - (double)(entityllama.width + 1.0F) * 0.5D * (double)MathHelper.sin(entityllama.aQ * ((float)Math.PI / 180F)), entityllama.locY + (double)entityllama.getHeadHeight() - (double)0.1F, entityllama.locZ + (double)(entityllama.width + 1.0F) * 0.5D * (double)MathHelper.cos(entityllama.aQ * ((float)Math.PI / 180F)));
    }

    public void tick() {
        super.tick();
        if (this.b != null) {
            this.f();
        }

        Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
        Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);
        vec3d = new Vec3D(this.locX, this.locY, this.locZ);
        vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        if (movingobjectposition != null) {
            vec3d1 = new Vec3D(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }

        Entity entity = this.a(vec3d, vec3d1);
        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null) {
            this.a(movingobjectposition);
        }

        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(MathHelper.c(this.motX, this.motZ) * (double)(180F / (float)Math.PI));

        for(this.pitch = (float)(MathHelper.c(this.motY, (double)f) * (double)(180F / (float)Math.PI)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
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
        float f1 = 0.99F;
        float f2 = 0.06F;
        if (!this.world.a(this.getBoundingBox(), Material.AIR)) {
            this.die();
        } else if (this.aq()) {
            this.die();
        } else {
            this.motX *= (double)0.99F;
            this.motY *= (double)0.99F;
            this.motZ *= (double)0.99F;
            if (!this.isNoGravity()) {
                this.motY -= (double)0.06F;
            }

            this.setPosition(this.locX, this.locY, this.locZ);
        }
    }

    @Nullable
    private Entity a(Vec3D vec3d, Vec3D vec3d1) {
        Entity entity = null;
        List list = this.world.getEntities(this, this.getBoundingBox().b(this.motX, this.motY, this.motZ).g(1.0D));
        double d0 = 0.0D;

        for(Entity entity1 : list) {
            if (entity1 != this.shooter) {
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
    }

    public void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null && this.shooter != null) {
            movingobjectposition.entity.damageEntity(DamageSource.a(this, (EntityLiving)this.shooter).c(), 1.0F);
        }

        if (!this.world.isClientSide) {
            this.die();
        }

    }

    protected void x_() {
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKeyOfType("Owner", 10)) {
            this.b = nbttagcompound.getCompound("Owner");
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        if (this.shooter != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            UUID uuid = this.shooter.getUniqueID();
            nbttagcompound1.a("OwnerUUID", uuid);
            nbttagcompound.set("Owner", nbttagcompound1);
        }

    }

    private void f() {
        if (this.b != null && this.b.b("OwnerUUID")) {
            UUID uuid = this.b.a("OwnerUUID");

            for(EntityLlama entityllama : this.world.a(EntityLlama.class, this.getBoundingBox().g(15.0D))) {
                if (entityllama.getUniqueID().equals(uuid)) {
                    this.shooter = entityllama;
                    break;
                }
            }
        }

        this.b = null;
    }
}
