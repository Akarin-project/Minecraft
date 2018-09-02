package net.minecraft.server;

import java.util.List;
import javax.annotation.Nullable;

public class EntityLeash extends EntityHanging {
    public EntityLeash(World world) {
        super(EntityTypes.LEASH_KNOT, world);
    }

    public EntityLeash(World world, BlockPosition blockposition) {
        super(EntityTypes.LEASH_KNOT, world, blockposition);
        this.setPosition((double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D);
        float f = 0.125F;
        float f1 = 0.1875F;
        float f2 = 0.25F;
        this.a(new AxisAlignedBB(this.locX - 0.1875D, this.locY - 0.25D + 0.125D, this.locZ - 0.1875D, this.locX + 0.1875D, this.locY + 0.25D + 0.125D, this.locZ + 0.1875D));
        this.attachedToPlayer = true;
    }

    public void setPosition(double d0, double d1, double d2) {
        super.setPosition((double)MathHelper.floor(d0) + 0.5D, (double)MathHelper.floor(d1) + 0.5D, (double)MathHelper.floor(d2) + 0.5D);
    }

    protected void updateBoundingBox() {
        this.locX = (double)this.blockPosition.getX() + 0.5D;
        this.locY = (double)this.blockPosition.getY() + 0.5D;
        this.locZ = (double)this.blockPosition.getZ() + 0.5D;
    }

    public void setDirection(EnumDirection var1) {
    }

    public int getWidth() {
        return 9;
    }

    public int getHeight() {
        return 9;
    }

    public float getHeadHeight() {
        return -0.0625F;
    }

    public void a(@Nullable Entity var1) {
        this.a(SoundEffects.ENTITY_LEASH_KNOT_BREAK, 1.0F, 1.0F);
    }

    public void b(NBTTagCompound var1) {
    }

    public void a(NBTTagCompound var1) {
    }

    public boolean b(EntityHuman entityhuman, EnumHand var2) {
        if (this.world.isClientSide) {
            return true;
        } else {
            boolean flag = false;
            double d0 = 7.0D;
            List list = this.world.a(EntityInsentient.class, new AxisAlignedBB(this.locX - 7.0D, this.locY - 7.0D, this.locZ - 7.0D, this.locX + 7.0D, this.locY + 7.0D, this.locZ + 7.0D));

            for(EntityInsentient entityinsentient : list) {
                if (entityinsentient.isLeashed() && entityinsentient.getLeashHolder() == entityhuman) {
                    entityinsentient.setLeashHolder(this, true);
                    flag = true;
                }
            }

            if (!flag) {
                this.die();
                if (entityhuman.abilities.canInstantlyBuild) {
                    for(EntityInsentient entityinsentient1 : list) {
                        if (entityinsentient1.isLeashed() && entityinsentient1.getLeashHolder() == this) {
                            entityinsentient1.unleash(true, false);
                        }
                    }
                }
            }

            return true;
        }
    }

    public boolean survives() {
        return this.world.getType(this.blockPosition).getBlock() instanceof BlockFence;
    }

    public static EntityLeash a(World world, BlockPosition blockposition) {
        EntityLeash entityleash = new EntityLeash(world, blockposition);
        world.addEntity(entityleash);
        entityleash.m();
        return entityleash;
    }

    @Nullable
    public static EntityLeash b(World world, BlockPosition blockposition) {
        int i = blockposition.getX();
        int j = blockposition.getY();
        int k = blockposition.getZ();

        for(EntityLeash entityleash : world.a(EntityLeash.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
            if (entityleash.getBlockPosition().equals(blockposition)) {
                return entityleash;
            }
        }

        return null;
    }

    public void m() {
        this.a(SoundEffects.ENTITY_LEASH_KNOT_PLACE, 1.0F, 1.0F);
    }
}
