package net.minecraft.server;

public class ControllerLook {
    protected final EntityInsentient a;
    protected float b;
    protected float c;
    protected boolean d;
    protected double e;
    protected double f;
    protected double g;

    public ControllerLook(EntityInsentient entityinsentient) {
        this.a = entityinsentient;
    }

    public void a(Entity entity, float fx, float f1) {
        this.e = entity.locX;
        if (entity instanceof EntityLiving) {
            this.f = entity.locY + (double)entity.getHeadHeight();
        } else {
            this.f = (entity.getBoundingBox().b + entity.getBoundingBox().e) / 2.0D;
        }

        this.g = entity.locZ;
        this.b = fx;
        this.c = f1;
        this.d = true;
    }

    public void a(double d0, double d1, double d2, float fx, float f1) {
        this.e = d0;
        this.f = d1;
        this.g = d2;
        this.b = fx;
        this.c = f1;
        this.d = true;
    }

    public void a() {
        this.a.pitch = 0.0F;
        if (this.d) {
            this.d = false;
            double d0 = this.e - this.a.locX;
            double d1 = this.f - (this.a.locY + (double)this.a.getHeadHeight());
            double d2 = this.g - this.a.locZ;
            double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
            float fx = (float)(MathHelper.c(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            float f1 = (float)(-(MathHelper.c(d1, d3) * (double)(180F / (float)Math.PI)));
            this.a.pitch = this.a(this.a.pitch, f1, this.c);
            this.a.aS = this.a(this.a.aS, fx, this.b);
        } else {
            this.a.aS = this.a(this.a.aS, this.a.aQ, 10.0F);
        }

        float f2 = MathHelper.g(this.a.aS - this.a.aQ);
        if (!this.a.getNavigation().p()) {
            if (f2 < -75.0F) {
                this.a.aS = this.a.aQ - 75.0F;
            }

            if (f2 > 75.0F) {
                this.a.aS = this.a.aQ + 75.0F;
            }
        }

    }

    protected float a(float fx, float f1, float f2) {
        float f3 = MathHelper.g(f1 - fx);
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return fx + f3;
    }

    public boolean b() {
        return this.d;
    }

    public double e() {
        return this.e;
    }

    public double f() {
        return this.f;
    }

    public double g() {
        return this.g;
    }
}
