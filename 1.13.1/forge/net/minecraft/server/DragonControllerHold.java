package net.minecraft.server;

import javax.annotation.Nullable;

public class DragonControllerHold extends AbstractDragonController {
    private PathEntity b;
    private Vec3D c;
    private boolean d;

    public DragonControllerHold(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public DragonControllerPhase<DragonControllerHold> getControllerPhase() {
        return DragonControllerPhase.a;
    }

    public void c() {
        double d0 = this.c == null ? 0.0D : this.c.c(this.a.locX, this.a.locY, this.a.locZ);
        if (d0 < 100.0D || d0 > 22500.0D || this.a.positionChanged || this.a.C) {
            this.j();
        }

    }

    public void d() {
        this.b = null;
        this.c = null;
    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    private void j() {
        if (this.b != null && this.b.b()) {
            BlockPosition blockposition = this.a.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPosition(WorldGenEndTrophy.a));
            int i = this.a.ds() == null ? 0 : this.a.ds().c();
            if (this.a.getRandom().nextInt(i + 3) == 0) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.c);
                return;
            }

            double d0 = 64.0D;
            EntityHuman entityhuman = this.a.world.a(blockposition, d0, d0);
            if (entityhuman != null) {
                d0 = entityhuman.d(blockposition) / 512.0D;
            }

            if (entityhuman != null && (this.a.getRandom().nextInt(MathHelper.a((int)d0) + 2) == 0 || this.a.getRandom().nextInt(i + 2) == 0)) {
                this.a(entityhuman);
                return;
            }
        }

        if (this.b == null || this.b.b()) {
            int j = this.a.l();
            int k = j;
            if (this.a.getRandom().nextInt(8) == 0) {
                this.d = !this.d;
                k = j + 6;
            }

            if (this.d) {
                ++k;
            } else {
                --k;
            }

            if (this.a.ds() != null && this.a.ds().c() >= 0) {
                k = k % 12;
                if (k < 0) {
                    k += 12;
                }
            } else {
                k = k - 12;
                k = k & 7;
                k = k + 12;
            }

            this.b = this.a.a(j, k, (PathPoint)null);
            if (this.b != null) {
                this.b.a();
            }
        }

        this.k();
    }

    private void a(EntityHuman entityhuman) {
        this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.b);
        ((DragonControllerStrafe)this.a.getDragonControllerManager().b(DragonControllerPhase.b)).a(entityhuman);
    }

    private void k() {
        if (this.b != null && !this.b.b()) {
            Vec3D vec3d = this.b.f();
            this.b.a();
            double d0 = vec3d.x;
            double d1 = vec3d.z;

            double d2;
            while(true) {
                d2 = vec3d.y + (double)(this.a.getRandom().nextFloat() * 20.0F);
                if (!(d2 < vec3d.y)) {
                    break;
                }
            }

            this.c = new Vec3D(d0, d2, d1);
        }

    }

    public void a(EntityEnderCrystal var1, BlockPosition var2, DamageSource var3, @Nullable EntityHuman entityhuman) {
        if (entityhuman != null && !entityhuman.abilities.isInvulnerable) {
            this.a(entityhuman);
        }

    }
}
