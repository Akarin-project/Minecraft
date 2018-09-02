package net.minecraft.server;

public class PathfinderGoalTame extends PathfinderGoal {
    private final EntityHorseAbstract entity;
    private final double b;
    private double c;
    private double d;
    private double e;

    public PathfinderGoalTame(EntityHorseAbstract entityhorseabstract, double d0) {
        this.entity = entityhorseabstract;
        this.b = d0;
        this.a(1);
    }

    public boolean a() {
        if (!this.entity.isTamed() && this.entity.isVehicle()) {
            Vec3D vec3d = RandomPositionGenerator.a(this.entity, 5, 4);
            if (vec3d == null) {
                return false;
            } else {
                this.c = vec3d.x;
                this.d = vec3d.y;
                this.e = vec3d.z;
                return true;
            }
        } else {
            return false;
        }
    }

    public void c() {
        this.entity.getNavigation().a(this.c, this.d, this.e, this.b);
    }

    public boolean b() {
        return !this.entity.isTamed() && !this.entity.getNavigation().p() && this.entity.isVehicle();
    }

    public void e() {
        if (!this.entity.isTamed() && this.entity.getRandom().nextInt(50) == 0) {
            Entity entityx = (Entity)this.entity.bP().get(0);
            if (entityx == null) {
                return;
            }

            if (entityx instanceof EntityHuman) {
                int i = this.entity.getTemper();
                int j = this.entity.getMaxDomestication();
                if (j > 0 && this.entity.getRandom().nextInt(j) < i) {
                    this.entity.h((EntityHuman)entityx);
                    return;
                }

                this.entity.r(5);
            }

            this.entity.ejectPassengers();
            this.entity.dZ();
            this.entity.world.broadcastEntityEffect(this.entity, (byte)6);
        }

    }
}
