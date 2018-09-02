package net.minecraft.server;

import javax.annotation.Nullable;

public class DragonControllerLanding extends AbstractDragonController {
    private Vec3D b;

    public DragonControllerLanding(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public void b() {
        Vec3D vec3d = this.a.a(1.0F).a();
        vec3d.b((-(float)Math.PI / 4F));
        double d0 = this.a.bD.locX;
        double d1 = this.a.bD.locY + (double)(this.a.bD.length / 2.0F);
        double d2 = this.a.bD.locZ;

        for(int i = 0; i < 8; ++i) {
            double d3 = d0 + this.a.getRandom().nextGaussian() / 2.0D;
            double d4 = d1 + this.a.getRandom().nextGaussian() / 2.0D;
            double d5 = d2 + this.a.getRandom().nextGaussian() / 2.0D;
            this.a.world.addParticle(Particles.j, d3, d4, d5, -vec3d.x * (double)0.08F + this.a.motX, -vec3d.y * (double)0.3F + this.a.motY, -vec3d.z * (double)0.08F + this.a.motZ);
            vec3d.b(0.19634955F);
        }

    }

    public void c() {
        if (this.b == null) {
            this.b = new Vec3D(this.a.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, WorldGenEndTrophy.a));
        }

        if (this.b.c(this.a.locX, this.a.locY, this.a.locZ) < 1.0D) {
            ((DragonControllerLandedFlame)this.a.getDragonControllerManager().b(DragonControllerPhase.f)).j();
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.g);
        }

    }

    public float f() {
        return 1.5F;
    }

    public float h() {
        float f = MathHelper.sqrt(this.a.motX * this.a.motX + this.a.motZ * this.a.motZ) + 1.0F;
        float f1 = Math.min(f, 40.0F);
        return f1 / f;
    }

    public void d() {
        this.b = null;
    }

    @Nullable
    public Vec3D g() {
        return this.b;
    }

    public DragonControllerPhase<DragonControllerLanding> getControllerPhase() {
        return DragonControllerPhase.d;
    }
}
