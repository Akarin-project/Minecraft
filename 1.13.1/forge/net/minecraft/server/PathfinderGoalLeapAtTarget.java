package net.minecraft.server;

public class PathfinderGoalLeapAtTarget extends PathfinderGoal {
    private final EntityInsentient a;
    private EntityLiving b;
    private final float c;

    public PathfinderGoalLeapAtTarget(EntityInsentient entityinsentient, float f) {
        this.a = entityinsentient;
        this.c = f;
        this.a(5);
    }

    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (this.b == null) {
            return false;
        } else {
            double d0 = this.a.h(this.b);
            if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
                if (!this.a.onGround) {
                    return false;
                } else {
                    return this.a.getRandom().nextInt(5) == 0;
                }
            } else {
                return false;
            }
        }
    }

    public boolean b() {
        return !this.a.onGround;
    }

    public void c() {
        double d0 = this.b.locX - this.a.locX;
        double d1 = this.b.locZ - this.a.locZ;
        float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
        if ((double)f >= 1.0E-4D) {
            this.a.motX += d0 / (double)f * 0.5D * (double)0.8F + this.a.motX * (double)0.2F;
            this.a.motZ += d1 / (double)f * 0.5D * (double)0.8F + this.a.motZ * (double)0.2F;
        }

        this.a.motY = (double)this.c;
    }
}
