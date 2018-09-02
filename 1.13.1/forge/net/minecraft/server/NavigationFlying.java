package net.minecraft.server;

public class NavigationFlying extends NavigationAbstract {
    public NavigationFlying(EntityInsentient entityinsentient, World world) {
        super(entityinsentient, world);
    }

    protected Pathfinder a() {
        this.o = new PathfinderFlying();
        this.o.a(true);
        return new Pathfinder(this.o);
    }

    protected boolean b() {
        return this.t() && this.r() || !this.a.isPassenger();
    }

    protected Vec3D c() {
        return new Vec3D(this.a.locX, this.a.locY, this.a.locZ);
    }

    public PathEntity a(Entity entity) {
        return this.b(new BlockPosition(entity));
    }

    public void d() {
        ++this.e;
        if (this.m) {
            this.l();
        }

        if (!this.p()) {
            if (this.b()) {
                this.o();
            } else if (this.c != null && this.c.e() < this.c.d()) {
                Vec3D vec3d = this.c.a(this.a, this.c.e());
                if (MathHelper.floor(this.a.locX) == MathHelper.floor(vec3d.x) && MathHelper.floor(this.a.locY) == MathHelper.floor(vec3d.y) && MathHelper.floor(this.a.locZ) == MathHelper.floor(vec3d.z)) {
                    this.c.c(this.c.e() + 1);
                }
            }

            this.n();
            if (!this.p()) {
                Vec3D vec3d1 = this.c.a(this.a);
                this.a.getControllerMove().a(vec3d1.x, vec3d1.y, vec3d1.z, this.d);
            }
        }
    }

    protected boolean a(Vec3D vec3d, Vec3D vec3d1, int var3, int var4, int var5) {
        int i = MathHelper.floor(vec3d.x);
        int j = MathHelper.floor(vec3d.y);
        int k = MathHelper.floor(vec3d.z);
        double d0 = vec3d1.x - vec3d.x;
        double d1 = vec3d1.y - vec3d.y;
        double d2 = vec3d1.z - vec3d.z;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0E-8D) {
            return false;
        } else {
            double d4 = 1.0D / Math.sqrt(d3);
            d0 = d0 * d4;
            d1 = d1 * d4;
            d2 = d2 * d4;
            double d5 = 1.0D / Math.abs(d0);
            double d6 = 1.0D / Math.abs(d1);
            double d7 = 1.0D / Math.abs(d2);
            double d8 = (double)i - vec3d.x;
            double d9 = (double)j - vec3d.y;
            double d10 = (double)k - vec3d.z;
            if (d0 >= 0.0D) {
                ++d8;
            }

            if (d1 >= 0.0D) {
                ++d9;
            }

            if (d2 >= 0.0D) {
                ++d10;
            }

            d8 = d8 / d0;
            d9 = d9 / d1;
            d10 = d10 / d2;
            int l = d0 < 0.0D ? -1 : 1;
            int i1 = d1 < 0.0D ? -1 : 1;
            int j1 = d2 < 0.0D ? -1 : 1;
            int k1 = MathHelper.floor(vec3d1.x);
            int l1 = MathHelper.floor(vec3d1.y);
            int i2 = MathHelper.floor(vec3d1.z);
            int j2 = k1 - i;
            int k2 = l1 - j;
            int l2 = i2 - k;

            while(j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
                if (d8 < d10 && d8 <= d9) {
                    d8 += d5;
                    i += l;
                    j2 = k1 - i;
                } else if (d9 < d8 && d9 <= d10) {
                    d9 += d6;
                    j += i1;
                    k2 = l1 - j;
                } else {
                    d10 += d7;
                    k += j1;
                    l2 = i2 - k;
                }
            }

            return true;
        }
    }

    public void a(boolean flag) {
        this.o.b(flag);
    }

    public void b(boolean flag) {
        this.o.a(flag);
    }

    public boolean a(BlockPosition blockposition) {
        return this.b.getType(blockposition).q();
    }
}
