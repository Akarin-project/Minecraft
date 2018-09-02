package net.minecraft.server;

public class DragonControllerLandedSearch extends AbstractDragonControllerLanded {
    private int b;

    public DragonControllerLandedSearch(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public void c() {
        ++this.b;
        EntityHuman entityhuman = this.a.world.a(this.a, 20.0D, 10.0D);
        if (entityhuman != null) {
            if (this.b > 25) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.h);
            } else {
                Vec3D vec3d = (new Vec3D(entityhuman.locX - this.a.locX, 0.0D, entityhuman.locZ - this.a.locZ)).a();
                Vec3D vec3d1 = (new Vec3D((double)MathHelper.sin(this.a.yaw * ((float)Math.PI / 180F)), 0.0D, (double)(-MathHelper.cos(this.a.yaw * ((float)Math.PI / 180F))))).a();
                float f = (float)vec3d1.b(vec3d);
                float f1 = (float)(Math.acos((double)f) * (double)(180F / (float)Math.PI)) + 0.5F;
                if (f1 < 0.0F || f1 > 10.0F) {
                    double d0 = entityhuman.locX - this.a.bD.locX;
                    double d1 = entityhuman.locZ - this.a.bD.locZ;
                    double d2 = MathHelper.a(MathHelper.g(180.0D - MathHelper.c(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.a.yaw), -100.0D, 100.0D);
                    this.a.bk *= 0.8F;
                    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
                    float f3 = f2;
                    if (f2 > 40.0F) {
                        f2 = 40.0F;
                    }

                    this.a.bk = (float)((double)this.a.bk + d2 * (double)(0.7F / f2 / f3));
                    this.a.yaw += this.a.bk;
                }
            }
        } else if (this.b >= 100) {
            entityhuman = this.a.world.a(this.a, 150.0D, 150.0D);
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.e);
            if (entityhuman != null) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.i);
                ((DragonControllerCharge)this.a.getDragonControllerManager().b(DragonControllerPhase.i)).a(new Vec3D(entityhuman.locX, entityhuman.locY, entityhuman.locZ));
            }
        }

    }

    public void d() {
        this.b = 0;
    }

    public DragonControllerPhase<DragonControllerLandedSearch> getControllerPhase() {
        return DragonControllerPhase.g;
    }
}