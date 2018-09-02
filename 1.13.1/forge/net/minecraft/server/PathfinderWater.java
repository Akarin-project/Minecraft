package net.minecraft.server;

import javax.annotation.Nullable;

public class PathfinderWater extends PathfinderAbstract {
    private final boolean j;

    public PathfinderWater(boolean flag) {
        this.j = flag;
    }

    public PathPoint b() {
        return super.a(MathHelper.floor(this.b.getBoundingBox().a), MathHelper.floor(this.b.getBoundingBox().b + 0.5D), MathHelper.floor(this.b.getBoundingBox().c));
    }

    public PathPoint a(double d0, double d1, double d2) {
        return super.a(MathHelper.floor(d0 - (double)(this.b.width / 2.0F)), MathHelper.floor(d1 + 0.5D), MathHelper.floor(d2 - (double)(this.b.width / 2.0F)));
    }

    public int a(PathPoint[] apathpoint, PathPoint pathpoint, PathPoint pathpoint1, float f) {
        int i = 0;

        for(EnumDirection enumdirection : EnumDirection.values()) {
            PathPoint pathpoint2 = this.b(pathpoint.a + enumdirection.getAdjacentX(), pathpoint.b + enumdirection.getAdjacentY(), pathpoint.c + enumdirection.getAdjacentZ());
            if (pathpoint2 != null && !pathpoint2.i && pathpoint2.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint2;
            }
        }

        return i;
    }

    public PathType a(IBlockAccess iblockaccess, int i, int jx, int k, EntityInsentient var5, int var6, int var7, int var8, boolean var9, boolean var10) {
        return this.a(iblockaccess, i, jx, k);
    }

    public PathType a(IBlockAccess iblockaccess, int i, int jx, int k) {
        BlockPosition blockposition = new BlockPosition(i, jx, k);
        Fluid fluid = iblockaccess.b(blockposition);
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        if (fluid.e() && iblockdata.a(iblockaccess, blockposition.down(), PathMode.WATER) && iblockdata.isAir()) {
            return PathType.BREACH;
        } else {
            return fluid.a(TagsFluid.WATER) && iblockdata.a(iblockaccess, blockposition, PathMode.WATER) ? PathType.WATER : PathType.BLOCKED;
        }
    }

    @Nullable
    private PathPoint b(int i, int jx, int k) {
        PathType pathtype = this.c(i, jx, k);
        return (!this.j || pathtype != PathType.BREACH) && pathtype != PathType.WATER ? null : this.a(i, jx, k);
    }

    @Nullable
    protected PathPoint a(int i, int jx, int k) {
        PathPoint pathpoint = null;
        PathType pathtype = this.a(this.b.world, i, jx, k);
        float f = this.b.a(pathtype);
        if (f >= 0.0F) {
            pathpoint = super.a(i, jx, k);
            pathpoint.m = pathtype;
            pathpoint.l = Math.max(pathpoint.l, f);
            if (this.a.b(new BlockPosition(i, jx, k)).e()) {
                pathpoint.l += 8.0F;
            }
        }

        return pathtype == PathType.OPEN ? pathpoint : pathpoint;
    }

    private PathType c(int i, int jx, int k) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int l = i; l < i + this.d; ++l) {
            for(int i1 = jx; i1 < jx + this.e; ++i1) {
                for(int j1 = k; j1 < k + this.f; ++j1) {
                    Fluid fluid = this.a.b(blockposition$mutableblockposition.c(l, i1, j1));
                    IBlockData iblockdata = this.a.getType(blockposition$mutableblockposition.c(l, i1, j1));
                    if (fluid.e() && iblockdata.a(this.a, blockposition$mutableblockposition.down(), PathMode.WATER) && iblockdata.isAir()) {
                        return PathType.BREACH;
                    }

                    if (!fluid.a(TagsFluid.WATER)) {
                        return PathType.BLOCKED;
                    }
                }
            }
        }

        IBlockData iblockdata1 = this.a.getType(blockposition$mutableblockposition);
        if (iblockdata1.a(this.a, blockposition$mutableblockposition, PathMode.WATER)) {
            return PathType.WATER;
        } else {
            return PathType.BLOCKED;
        }
    }
}
