package net.minecraft.server;

public class EntitySmallFireball extends EntityFireball {
    public EntitySmallFireball(World world) {
        super(EntityTypes.SMALL_FIREBALL, world, 0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
        super(EntityTypes.SMALL_FIREBALL, entityliving, d0, d1, d2, world, 0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(EntityTypes.SMALL_FIREBALL, d0, d1, d2, d3, d4, d5, world, 0.3125F, 0.3125F);
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.world.isClientSide) {
            if (movingobjectposition.entity != null) {
                if (!movingobjectposition.entity.isFireProof()) {
                    movingobjectposition.entity.setOnFire(5);
                    boolean flag = movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5.0F);
                    if (flag) {
                        this.a(this.shooter, movingobjectposition.entity);
                    }
                }
            } else {
                boolean flag1 = true;
                if (this.shooter != null && this.shooter instanceof EntityInsentient) {
                    flag1 = this.world.getGameRules().getBoolean("mobGriefing");
                }

                if (flag1) {
                    BlockPosition blockposition = movingobjectposition.a().shift(movingobjectposition.direction);
                    if (this.world.isEmpty(blockposition)) {
                        this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
                    }
                }
            }

            this.die();
        }

    }

    public boolean isInteractable() {
        return false;
    }

    public boolean damageEntity(DamageSource var1, float var2) {
        return false;
    }
}
