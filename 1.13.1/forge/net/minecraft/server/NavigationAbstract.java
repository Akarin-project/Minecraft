package net.minecraft.server;

import javax.annotation.Nullable;

public abstract class NavigationAbstract {
    protected EntityInsentient a;
    protected World b;
    @Nullable
    protected PathEntity c;
    protected double d;
    private final AttributeInstance p;
    protected int e;
    protected int f;
    protected Vec3D g = Vec3D.a;
    protected Vec3D h = Vec3D.a;
    protected long i;
    protected long j;
    protected double k;
    protected float l = 0.5F;
    protected boolean m;
    protected long n;
    protected PathfinderAbstract o;
    private BlockPosition q;
    private Pathfinder r;

    public NavigationAbstract(EntityInsentient entityinsentient, World world) {
        this.a = entityinsentient;
        this.b = world;
        this.p = entityinsentient.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        this.r = this.a();
    }

    public BlockPosition i() {
        return this.q;
    }

    protected abstract Pathfinder a();

    public void a(double d0) {
        this.d = d0;
    }

    public float j() {
        return (float)this.p.getValue();
    }

    public boolean k() {
        return this.m;
    }

    public void l() {
        if (this.b.getTime() - this.n > 20L) {
            if (this.q != null) {
                this.c = null;
                this.c = this.b(this.q);
                this.n = this.b.getTime();
                this.m = false;
            }
        } else {
            this.m = true;
        }

    }

    @Nullable
    public final PathEntity a(double d0, double d1, double d2) {
        return this.b(new BlockPosition(d0, d1, d2));
    }

    @Nullable
    public PathEntity b(BlockPosition blockposition) {
        if (!this.b()) {
            return null;
        } else if (this.c != null && !this.c.b() && blockposition.equals(this.q)) {
            return this.c;
        } else {
            this.q = blockposition;
            float fx = this.j();
            this.b.methodProfiler.a("pathfind");
            BlockPosition blockposition1 = new BlockPosition(this.a);
            int ix = (int)(fx + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.b, blockposition1.a(-ix, -ix, -ix), blockposition1.a(ix, ix, ix), 0);
            PathEntity pathentity = this.r.a(chunkcache, this.a, this.q, fx);
            this.b.methodProfiler.e();
            return pathentity;
        }
    }

    @Nullable
    public PathEntity a(Entity entity) {
        if (!this.b()) {
            return null;
        } else {
            BlockPosition blockposition = new BlockPosition(entity);
            if (this.c != null && !this.c.b() && blockposition.equals(this.q)) {
                return this.c;
            } else {
                this.q = blockposition;
                float fx = this.j();
                this.b.methodProfiler.a("pathfind");
                BlockPosition blockposition1 = (new BlockPosition(this.a)).up();
                int ix = (int)(fx + 16.0F);
                ChunkCache chunkcache = new ChunkCache(this.b, blockposition1.a(-ix, -ix, -ix), blockposition1.a(ix, ix, ix), 0);
                PathEntity pathentity = this.r.a(chunkcache, this.a, entity, fx);
                this.b.methodProfiler.e();
                return pathentity;
            }
        }
    }

    public boolean a(double d0, double d1, double d2, double d3) {
        return this.a(this.a(d0, d1, d2), d3);
    }

    public boolean a(Entity entity, double d0) {
        PathEntity pathentity = this.a(entity);
        return pathentity != null && this.a(pathentity, d0);
    }

    public boolean a(@Nullable PathEntity pathentity, double d0) {
        if (pathentity == null) {
            this.c = null;
            return false;
        } else {
            if (!pathentity.a(this.c)) {
                this.c = pathentity;
            }

            this.E_();
            if (this.c.d() <= 0) {
                return false;
            } else {
                this.d = d0;
                Vec3D vec3d = this.c();
                this.f = this.e;
                this.g = vec3d;
                return true;
            }
        }
    }

    @Nullable
    public PathEntity m() {
        return this.c;
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
                Vec3D vec3d = this.c();
                Vec3D vec3d1 = this.c.a(this.a, this.c.e());
                if (vec3d.y > vec3d1.y && !this.a.onGround && MathHelper.floor(vec3d.x) == MathHelper.floor(vec3d1.x) && MathHelper.floor(vec3d.z) == MathHelper.floor(vec3d1.z)) {
                    this.c.c(this.c.e() + 1);
                }
            }

            this.n();
            if (!this.p()) {
                Vec3D vec3d2 = this.c.a(this.a);
                BlockPosition blockposition = new BlockPosition(vec3d2);
                this.a.getControllerMove().a(vec3d2.x, this.b.getType(blockposition.down()).isAir() ? vec3d2.y : PathfinderNormal.a(this.b, blockposition), vec3d2.z, this.d);
            }
        }
    }

    protected void n() {
    }

    protected void o() {
        Vec3D vec3d = this.c();
        int ix = this.c.d();

        for(int jx = this.c.e(); jx < this.c.d(); ++jx) {
            if ((double)this.c.a(jx).b != Math.floor(vec3d.y)) {
                ix = jx;
                break;
            }
        }

        this.l = this.a.width > 0.75F ? this.a.width / 2.0F : 0.75F - this.a.width / 2.0F;
        Vec3D vec3d1 = this.c.f();
        if (MathHelper.e((float)(this.a.locX - (vec3d1.x + 0.5D))) < this.l && MathHelper.e((float)(this.a.locZ - (vec3d1.z + 0.5D))) < this.l && Math.abs(this.a.locY - vec3d1.y) < 1.0D) {
            this.c.c(this.c.e() + 1);
        }

        int kx = MathHelper.f(this.a.width);
        int lx = MathHelper.f(this.a.length);
        int i1 = kx;

        for(int j1 = ix - 1; j1 >= this.c.e(); --j1) {
            if (this.a(vec3d, this.c.a(this.a, j1), kx, lx, i1)) {
                this.c.c(j1);
                break;
            }
        }

        this.a(vec3d);
    }

    protected void a(Vec3D vec3d) {
        if (this.e - this.f > 100) {
            if (vec3d.distanceSquared(this.g) < 2.25D) {
                this.q();
            }

            this.f = this.e;
            this.g = vec3d;
        }

        if (this.c != null && !this.c.b()) {
            Vec3D vec3d1 = this.c.f();
            if (vec3d1.equals(this.h)) {
                this.i += SystemUtils.b() - this.j;
            } else {
                this.h = vec3d1;
                double d0 = vec3d.f(this.h);
                this.k = this.a.cK() > 0.0F ? d0 / (double)this.a.cK() * 1000.0D : 0.0D;
            }

            if (this.k > 0.0D && (double)this.i > this.k * 3.0D) {
                this.h = Vec3D.a;
                this.i = 0L;
                this.k = 0.0D;
                this.q();
            }

            this.j = SystemUtils.b();
        }

    }

    public boolean p() {
        return this.c == null || this.c.b();
    }

    public void q() {
        this.c = null;
    }

    protected abstract Vec3D c();

    protected abstract boolean b();

    protected boolean r() {
        return this.a.aq() || this.a.ax();
    }

    protected void E_() {
        if (this.c != null) {
            for(int ix = 0; ix < this.c.d(); ++ix) {
                PathPoint pathpoint = this.c.a(ix);
                PathPoint pathpoint1 = ix + 1 < this.c.d() ? this.c.a(ix + 1) : null;
                IBlockData iblockdata = this.b.getType(new BlockPosition(pathpoint.a, pathpoint.b, pathpoint.c));
                Block block = iblockdata.getBlock();
                if (block == Blocks.CAULDRON) {
                    this.c.a(ix, pathpoint.a(pathpoint.a, pathpoint.b + 1, pathpoint.c));
                    if (pathpoint1 != null && pathpoint.b >= pathpoint1.b) {
                        this.c.a(ix + 1, pathpoint1.a(pathpoint1.a, pathpoint.b + 1, pathpoint1.c));
                    }
                }
            }

        }
    }

    protected abstract boolean a(Vec3D var1, Vec3D var2, int var3, int var4, int var5);

    public boolean a(BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();
        return this.b.getType(blockposition1).f(this.b, blockposition1);
    }

    public PathfinderAbstract s() {
        return this.o;
    }

    public void d(boolean flag) {
        this.o.c(flag);
    }

    public boolean t() {
        return this.o.e();
    }
}
