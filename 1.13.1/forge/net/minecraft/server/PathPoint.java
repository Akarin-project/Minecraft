package net.minecraft.server;

public class PathPoint {
    public final int a;
    public final int b;
    public final int c;
    private final int n;
    public int d = -1;
    public float e;
    public float f;
    public float g;
    public PathPoint h;
    public boolean i;
    public float j;
    public float k;
    public float l;
    public PathType m = PathType.BLOCKED;

    public PathPoint(int ix, int jx, int kx) {
        this.a = ix;
        this.b = jx;
        this.c = kx;
        this.n = b(ix, jx, kx);
    }

    public PathPoint a(int ix, int jx, int kx) {
        PathPoint pathpoint1 = new PathPoint(ix, jx, kx);
        pathpoint1.d = this.d;
        pathpoint1.e = this.e;
        pathpoint1.f = this.f;
        pathpoint1.g = this.g;
        pathpoint1.h = this.h;
        pathpoint1.i = this.i;
        pathpoint1.j = this.j;
        pathpoint1.k = this.k;
        pathpoint1.l = this.l;
        pathpoint1.m = this.m;
        return pathpoint1;
    }

    public static int b(int ix, int jx, int kx) {
        return jx & 255 | (ix & 32767) << 8 | (kx & 32767) << 24 | (ix < 0 ? Integer.MIN_VALUE : 0) | (kx < 0 ? '\u8000' : 0);
    }

    public float a(PathPoint pathpoint1) {
        float fx = (float)(pathpoint1.a - this.a);
        float f1 = (float)(pathpoint1.b - this.b);
        float f2 = (float)(pathpoint1.c - this.c);
        return MathHelper.c(fx * fx + f1 * f1 + f2 * f2);
    }

    public float b(PathPoint pathpoint1) {
        float fx = (float)(pathpoint1.a - this.a);
        float f1 = (float)(pathpoint1.b - this.b);
        float f2 = (float)(pathpoint1.c - this.c);
        return fx * fx + f1 * f1 + f2 * f2;
    }

    public float c(PathPoint pathpoint1) {
        float fx = (float)Math.abs(pathpoint1.a - this.a);
        float f1 = (float)Math.abs(pathpoint1.b - this.b);
        float f2 = (float)Math.abs(pathpoint1.c - this.c);
        return fx + f1 + f2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof PathPoint)) {
            return false;
        } else {
            PathPoint pathpoint1 = (PathPoint)object;
            return this.n == pathpoint1.n && this.a == pathpoint1.a && this.b == pathpoint1.b && this.c == pathpoint1.c;
        }
    }

    public int hashCode() {
        return this.n;
    }

    public boolean a() {
        return this.d >= 0;
    }

    public String toString() {
        return this.a + ", " + this.b + ", " + this.c;
    }
}
