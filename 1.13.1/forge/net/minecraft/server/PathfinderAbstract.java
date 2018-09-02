package net.minecraft.server;

public abstract class PathfinderAbstract {
    protected IBlockAccess a;
    protected EntityInsentient b;
    protected final IntHashMap<PathPoint> c = new IntHashMap<PathPoint>();
    protected int d;
    protected int e;
    protected int f;
    protected boolean g;
    protected boolean h;
    protected boolean i;

    public PathfinderAbstract() {
    }

    public void a(IBlockAccess iblockaccess, EntityInsentient entityinsentient) {
        this.a = iblockaccess;
        this.b = entityinsentient;
        this.c.c();
        this.d = MathHelper.d(entityinsentient.width + 1.0F);
        this.e = MathHelper.d(entityinsentient.length + 1.0F);
        this.f = MathHelper.d(entityinsentient.width + 1.0F);
    }

    public void a() {
        this.a = null;
        this.b = null;
    }

    protected PathPoint a(int ix, int j, int k) {
        int l = PathPoint.b(ix, j, k);
        PathPoint pathpoint = this.c.get(l);
        if (pathpoint == null) {
            pathpoint = new PathPoint(ix, j, k);
            this.c.a(l, pathpoint);
        }

        return pathpoint;
    }

    public abstract PathPoint b();

    public abstract PathPoint a(double var1, double var3, double var5);

    public abstract int a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4);

    public abstract PathType a(IBlockAccess var1, int var2, int var3, int var4, EntityInsentient var5, int var6, int var7, int var8, boolean var9, boolean var10);

    public abstract PathType a(IBlockAccess var1, int var2, int var3, int var4);

    public void a(boolean flag) {
        this.g = flag;
    }

    public void b(boolean flag) {
        this.h = flag;
    }

    public void c(boolean flag) {
        this.i = flag;
    }

    public boolean c() {
        return this.g;
    }

    public boolean d() {
        return this.h;
    }

    public boolean e() {
        return this.i;
    }
}
