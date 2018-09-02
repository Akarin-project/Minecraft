package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public abstract class VoxelShape {
    protected final VoxelShapeDiscrete a;

    VoxelShape(VoxelShapeDiscrete voxelshapediscrete) {
        this.a = voxelshapediscrete;
    }

    public double b(EnumDirection.EnumAxis enumdirection$enumaxis) {
        int i = this.a.a(enumdirection$enumaxis);
        return i >= this.a.c(enumdirection$enumaxis) ? Double.POSITIVE_INFINITY : this.a(enumdirection$enumaxis, i);
    }

    public double c(EnumDirection.EnumAxis enumdirection$enumaxis) {
        int i = this.a.b(enumdirection$enumaxis);
        return i <= 0 ? Double.NEGATIVE_INFINITY : this.a(enumdirection$enumaxis, i);
    }

    public AxisAlignedBB a() {
        if (this.b()) {
            throw new UnsupportedOperationException("No bounds for empty shape.");
        } else {
            return new AxisAlignedBB(this.b(EnumDirection.EnumAxis.X), this.b(EnumDirection.EnumAxis.Y), this.b(EnumDirection.EnumAxis.Z), this.c(EnumDirection.EnumAxis.X), this.c(EnumDirection.EnumAxis.Y), this.c(EnumDirection.EnumAxis.Z));
        }
    }

    protected double a(EnumDirection.EnumAxis enumdirection$enumaxis, int i) {
        return this.a(enumdirection$enumaxis).getDouble(i);
    }

    protected abstract DoubleList a(EnumDirection.EnumAxis var1);

    public boolean b() {
        return this.a.a();
    }

    public VoxelShape a(double d0, double d1, double d2) {
        return (VoxelShape)(this.b() ? VoxelShapes.a() : new VoxelShapeArray(this.a, new DoubleListOffset(this.a(EnumDirection.EnumAxis.X), d0), new DoubleListOffset(this.a(EnumDirection.EnumAxis.Y), d1), new DoubleListOffset(this.a(EnumDirection.EnumAxis.Z), d2)));
    }

    public VoxelShape c() {
        VoxelShape[] avoxelshape = new VoxelShape[]{VoxelShapes.a()};
        this.b((d0, d1, d2, d3, d4, d5) -> {
            avoxelshape[0] = VoxelShapes.b(avoxelshape[0], VoxelShapes.a(d0, d1, d2, d3, d4, d5), OperatorBoolean.OR);
        });
        return avoxelshape[0];
    }

    public void b(VoxelShapes.a voxelshapes$a) {
        this.a.b((i, j, k, l, i1, j1) -> {
            voxelshapes$a.consume(this.a(EnumDirection.EnumAxis.X, i), this.a(EnumDirection.EnumAxis.Y, j), this.a(EnumDirection.EnumAxis.Z, k), this.a(EnumDirection.EnumAxis.X, l), this.a(EnumDirection.EnumAxis.Y, i1), this.a(EnumDirection.EnumAxis.Z, j1));
        }, true);
    }

    public List<AxisAlignedBB> d() {
        ArrayList arraylist = Lists.newArrayList();
        this.b((d0, d1, d2, d3, d4, d5) -> {
            arraylist.add(new AxisAlignedBB(d0, d1, d2, d3, d4, d5));
        });
        return arraylist;
    }

    protected int a(EnumDirection.EnumAxis enumdirection$enumaxis, double d0) {
        return MathHelper.a(0, this.a.c(enumdirection$enumaxis) + 1, (i) -> {
            if (i < 0) {
                return false;
            } else if (i > this.a.c(enumdirection$enumaxis)) {
                return true;
            } else {
                return d0 < this.a(enumdirection$enumaxis, i);
            }
        }) - 1;
    }

    protected boolean b(double d0, double d1, double d2) {
        return this.a.c(this.a(EnumDirection.EnumAxis.X, d0), this.a(EnumDirection.EnumAxis.Y, d1), this.a(EnumDirection.EnumAxis.Z, d2));
    }

    @Nullable
    public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1, BlockPosition blockposition) {
        if (this.b()) {
            return null;
        } else {
            Vec3D vec3d2 = vec3d1.d(vec3d);
            if (vec3d2.c() < 1.0E-7D) {
                return null;
            } else {
                Vec3D vec3d3 = vec3d.e(vec3d2.a(0.001D));
                Vec3D vec3d4 = vec3d.e(vec3d2.a(0.001D)).a((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
                return this.b(vec3d4.x, vec3d4.y, vec3d4.z) ? new MovingObjectPosition(vec3d3, EnumDirection.a(vec3d2.x, vec3d2.y, vec3d2.z), blockposition) : AxisAlignedBB.a(this.d(), vec3d, vec3d1, blockposition);
            }
        }
    }

    public VoxelShape a(EnumDirection enumdirection) {
        if (!this.b() && this != VoxelShapes.b()) {
            EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
            EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection = enumdirection.c();
            DoubleList doublelist = this.a(enumdirection$enumaxis);
            if (doublelist.size() == 2 && DoubleMath.fuzzyEquals(doublelist.getDouble(0), 0.0D, 1.0E-7D) && DoubleMath.fuzzyEquals(doublelist.getDouble(1), 1.0D, 1.0E-7D)) {
                return this;
            } else {
                int i = this.a(enumdirection$enumaxis, enumdirection$enumaxisdirection == EnumDirection.EnumAxisDirection.POSITIVE ? 0.9999999D : 1.0E-7D);
                return new VoxelShapeSlice(this, enumdirection$enumaxis, i);
            }
        } else {
            return this;
        }
    }

    public double a(EnumDirection.EnumAxis enumdirection$enumaxis, AxisAlignedBB axisalignedbb, double d0) {
        return this.a(EnumAxisCycle.a(enumdirection$enumaxis, EnumDirection.EnumAxis.X), axisalignedbb, d0);
    }

    protected double a(EnumAxisCycle enumaxiscycle, AxisAlignedBB axisalignedbb, double d0) {
        if (this.b()) {
            return d0;
        } else if (Math.abs(d0) < 1.0E-7D) {
            return 0.0D;
        } else {
            EnumAxisCycle enumaxiscycle1 = enumaxiscycle.a();
            EnumDirection.EnumAxis enumdirection$enumaxis = enumaxiscycle1.a(EnumDirection.EnumAxis.X);
            EnumDirection.EnumAxis enumdirection$enumaxis1 = enumaxiscycle1.a(EnumDirection.EnumAxis.Y);
            EnumDirection.EnumAxis enumdirection$enumaxis2 = enumaxiscycle1.a(EnumDirection.EnumAxis.Z);
            double d1 = axisalignedbb.b(enumdirection$enumaxis);
            double d2 = axisalignedbb.a(enumdirection$enumaxis);
            int i = this.a(enumdirection$enumaxis, d2 + 1.0E-7D);
            int j = this.a(enumdirection$enumaxis, d1 - 1.0E-7D);
            int k = Math.max(0, this.a(enumdirection$enumaxis1, axisalignedbb.a(enumdirection$enumaxis1) + 1.0E-7D));
            int l = Math.min(this.a.c(enumdirection$enumaxis1), this.a(enumdirection$enumaxis1, axisalignedbb.b(enumdirection$enumaxis1) - 1.0E-7D) + 1);
            int i1 = Math.max(0, this.a(enumdirection$enumaxis2, axisalignedbb.a(enumdirection$enumaxis2) + 1.0E-7D));
            int j1 = Math.min(this.a.c(enumdirection$enumaxis2), this.a(enumdirection$enumaxis2, axisalignedbb.b(enumdirection$enumaxis2) - 1.0E-7D) + 1);
            int k1 = this.a.c(enumdirection$enumaxis);
            if (d0 > 0.0D) {
                for(int l1 = j + 1; l1 < k1; ++l1) {
                    for(int i2 = k; i2 < l; ++i2) {
                        for(int j2 = i1; j2 < j1; ++j2) {
                            if (this.a.a(enumaxiscycle1, l1, i2, j2)) {
                                double d3 = this.a(enumdirection$enumaxis, l1) - d1;
                                if (d3 >= -1.0E-7D) {
                                    d0 = Math.min(d0, d3);
                                }

                                return d0;
                            }
                        }
                    }
                }
            } else if (d0 < 0.0D) {
                for(int k2 = i - 1; k2 >= 0; --k2) {
                    for(int l2 = k; l2 < l; ++l2) {
                        for(int i3 = i1; i3 < j1; ++i3) {
                            if (this.a.a(enumaxiscycle1, k2, l2, i3)) {
                                double d4 = this.a(enumdirection$enumaxis, k2 + 1) - d2;
                                if (d4 <= 1.0E-7D) {
                                    d0 = Math.max(d0, d4);
                                }

                                return d0;
                            }
                        }
                    }
                }
            }

            return d0;
        }
    }

    public String toString() {
        return this.b() ? "EMPTY" : "VoxelShape[" + this.a() + "]";
    }
}
