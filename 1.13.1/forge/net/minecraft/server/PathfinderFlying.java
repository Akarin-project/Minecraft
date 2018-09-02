package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import javax.annotation.Nullable;

public class PathfinderFlying extends PathfinderNormal {
    public PathfinderFlying() {
    }

    public void a(IBlockAccess iblockaccess, EntityInsentient entityinsentient) {
        super.a(iblockaccess, entityinsentient);
        this.j = entityinsentient.a(PathType.WATER);
    }

    public void a() {
        this.b.a(PathType.WATER, this.j);
        super.a();
    }

    public PathPoint b() {
        int i;
        if (this.e() && this.b.isInWater()) {
            i = (int)this.b.getBoundingBox().b;
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ));

            for(Block block = this.a.getType(blockposition$mutableblockposition).getBlock(); block == Blocks.WATER; block = this.a.getType(blockposition$mutableblockposition).getBlock()) {
                ++i;
                blockposition$mutableblockposition.c(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ));
            }
        } else {
            i = MathHelper.floor(this.b.getBoundingBox().b + 0.5D);
        }

        BlockPosition blockposition1 = new BlockPosition(this.b);
        PathType pathtype1 = this.a(this.b, blockposition1.getX(), i, blockposition1.getZ());
        if (this.b.a(pathtype1) < 0.0F) {
            HashSet hashset = Sets.newHashSet();
            hashset.add(new BlockPosition(this.b.getBoundingBox().a, (double)i, this.b.getBoundingBox().c));
            hashset.add(new BlockPosition(this.b.getBoundingBox().a, (double)i, this.b.getBoundingBox().f));
            hashset.add(new BlockPosition(this.b.getBoundingBox().d, (double)i, this.b.getBoundingBox().c));
            hashset.add(new BlockPosition(this.b.getBoundingBox().d, (double)i, this.b.getBoundingBox().f));

            for(BlockPosition blockposition : hashset) {
                PathType pathtype = this.a(this.b, blockposition);
                if (this.b.a(pathtype) >= 0.0F) {
                    return super.a(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                }
            }
        }

        return super.a(blockposition1.getX(), i, blockposition1.getZ());
    }

    public PathPoint a(double d0, double d1, double d2) {
        return super.a(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public int a(PathPoint[] apathpoint, PathPoint pathpoint, PathPoint pathpoint1, float f) {
        int i = 0;
        PathPoint pathpoint2 = this.a(pathpoint.a, pathpoint.b, pathpoint.c + 1);
        PathPoint pathpoint3 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c);
        PathPoint pathpoint4 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c);
        PathPoint pathpoint5 = this.a(pathpoint.a, pathpoint.b, pathpoint.c - 1);
        PathPoint pathpoint6 = this.a(pathpoint.a, pathpoint.b + 1, pathpoint.c);
        PathPoint pathpoint7 = this.a(pathpoint.a, pathpoint.b - 1, pathpoint.c);
        if (pathpoint2 != null && !pathpoint2.i && pathpoint2.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint2;
        }

        if (pathpoint3 != null && !pathpoint3.i && pathpoint3.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint3;
        }

        if (pathpoint4 != null && !pathpoint4.i && pathpoint4.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint4;
        }

        if (pathpoint5 != null && !pathpoint5.i && pathpoint5.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint5;
        }

        if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint6;
        }

        if (pathpoint7 != null && !pathpoint7.i && pathpoint7.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint7;
        }

        boolean flag = pathpoint5 == null || pathpoint5.l != 0.0F;
        boolean flag1 = pathpoint2 == null || pathpoint2.l != 0.0F;
        boolean flag2 = pathpoint4 == null || pathpoint4.l != 0.0F;
        boolean flag3 = pathpoint3 == null || pathpoint3.l != 0.0F;
        boolean flag4 = pathpoint6 == null || pathpoint6.l != 0.0F;
        boolean flag5 = pathpoint7 == null || pathpoint7.l != 0.0F;
        if (flag && flag3) {
            PathPoint pathpoint8 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c - 1);
            if (pathpoint8 != null && !pathpoint8.i && pathpoint8.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint8;
            }
        }

        if (flag && flag2) {
            PathPoint pathpoint9 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c - 1);
            if (pathpoint9 != null && !pathpoint9.i && pathpoint9.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint9;
            }
        }

        if (flag1 && flag3) {
            PathPoint pathpoint10 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c + 1);
            if (pathpoint10 != null && !pathpoint10.i && pathpoint10.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint10;
            }
        }

        if (flag1 && flag2) {
            PathPoint pathpoint11 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c + 1);
            if (pathpoint11 != null && !pathpoint11.i && pathpoint11.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint11;
            }
        }

        if (flag && flag4) {
            PathPoint pathpoint12 = this.a(pathpoint.a, pathpoint.b + 1, pathpoint.c - 1);
            if (pathpoint12 != null && !pathpoint12.i && pathpoint12.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint12;
            }
        }

        if (flag1 && flag4) {
            PathPoint pathpoint13 = this.a(pathpoint.a, pathpoint.b + 1, pathpoint.c + 1);
            if (pathpoint13 != null && !pathpoint13.i && pathpoint13.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint13;
            }
        }

        if (flag2 && flag4) {
            PathPoint pathpoint14 = this.a(pathpoint.a + 1, pathpoint.b + 1, pathpoint.c);
            if (pathpoint14 != null && !pathpoint14.i && pathpoint14.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint14;
            }
        }

        if (flag3 && flag4) {
            PathPoint pathpoint15 = this.a(pathpoint.a - 1, pathpoint.b + 1, pathpoint.c);
            if (pathpoint15 != null && !pathpoint15.i && pathpoint15.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint15;
            }
        }

        if (flag && flag5) {
            PathPoint pathpoint16 = this.a(pathpoint.a, pathpoint.b - 1, pathpoint.c - 1);
            if (pathpoint16 != null && !pathpoint16.i && pathpoint16.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint16;
            }
        }

        if (flag1 && flag5) {
            PathPoint pathpoint17 = this.a(pathpoint.a, pathpoint.b - 1, pathpoint.c + 1);
            if (pathpoint17 != null && !pathpoint17.i && pathpoint17.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint17;
            }
        }

        if (flag2 && flag5) {
            PathPoint pathpoint18 = this.a(pathpoint.a + 1, pathpoint.b - 1, pathpoint.c);
            if (pathpoint18 != null && !pathpoint18.i && pathpoint18.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint18;
            }
        }

        if (flag3 && flag5) {
            PathPoint pathpoint19 = this.a(pathpoint.a - 1, pathpoint.b - 1, pathpoint.c);
            if (pathpoint19 != null && !pathpoint19.i && pathpoint19.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint19;
            }
        }

        return i;
    }

    @Nullable
    protected PathPoint a(int i, int j, int k) {
        PathPoint pathpoint = null;
        PathType pathtype = this.a(this.b, i, j, k);
        float f = this.b.a(pathtype);
        if (f >= 0.0F) {
            pathpoint = super.a(i, j, k);
            pathpoint.m = pathtype;
            pathpoint.l = Math.max(pathpoint.l, f);
            if (pathtype == PathType.WALKABLE) {
                ++pathpoint.l;
            }
        }

        return pathtype != PathType.OPEN && pathtype != PathType.WALKABLE ? pathpoint : pathpoint;
    }

    public PathType a(IBlockAccess iblockaccess, int i, int j, int k, EntityInsentient entityinsentient, int l, int i1, int j1, boolean flag, boolean flag1) {
        EnumSet enumset = EnumSet.noneOf(PathType.class);
        PathType pathtype = PathType.BLOCKED;
        BlockPosition blockposition = new BlockPosition(entityinsentient);
        pathtype = this.a(iblockaccess, i, j, k, l, i1, j1, flag, flag1, enumset, pathtype, blockposition);
        if (enumset.contains(PathType.FENCE)) {
            return PathType.FENCE;
        } else {
            PathType pathtype1 = PathType.BLOCKED;

            for(PathType pathtype2 : enumset) {
                if (entityinsentient.a(pathtype2) < 0.0F) {
                    return pathtype2;
                }

                if (entityinsentient.a(pathtype2) >= entityinsentient.a(pathtype1)) {
                    pathtype1 = pathtype2;
                }
            }

            if (pathtype == PathType.OPEN && entityinsentient.a(pathtype1) == 0.0F) {
                return PathType.OPEN;
            } else {
                return pathtype1;
            }
        }
    }

    public PathType a(IBlockAccess iblockaccess, int i, int j, int k) {
        PathType pathtype = this.b(iblockaccess, i, j, k);
        if (pathtype == PathType.OPEN && j >= 1) {
            Block block = iblockaccess.getType(new BlockPosition(i, j - 1, k)).getBlock();
            PathType pathtype1 = this.b(iblockaccess, i, j - 1, k);
            if (pathtype1 != PathType.DAMAGE_FIRE && block != Blocks.MAGMA_BLOCK && pathtype1 != PathType.LAVA) {
                if (pathtype1 == PathType.DAMAGE_CACTUS) {
                    pathtype = PathType.DAMAGE_CACTUS;
                } else {
                    pathtype = pathtype1 != PathType.WALKABLE && pathtype1 != PathType.OPEN && pathtype1 != PathType.WATER ? PathType.WALKABLE : PathType.OPEN;
                }
            } else {
                pathtype = PathType.DAMAGE_FIRE;
            }
        }

        pathtype = this.a(iblockaccess, i, j, k, pathtype);
        return pathtype;
    }

    private PathType a(EntityInsentient entityinsentient, BlockPosition blockposition) {
        return this.a(entityinsentient, blockposition.getX(), blockposition.getY(), blockposition.getZ());
    }

    private PathType a(EntityInsentient entityinsentient, int i, int j, int k) {
        return this.a(this.a, i, j, k, entityinsentient, this.d, this.e, this.f, this.d(), this.c());
    }
}
