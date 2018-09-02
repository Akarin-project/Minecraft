package net.minecraft.server;

import java.util.BitSet;

public final class VoxelShapeBitSet extends VoxelShapeDiscrete {
    private final BitSet d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    public VoxelShapeBitSet(int ix, int jx, int k) {
        this(ix, jx, k, ix, jx, k, 0, 0, 0);
    }

    public VoxelShapeBitSet(int ix, int jx, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        super(ix, jx, k);
        this.d = new BitSet(ix * jx * k);
        this.e = l;
        this.f = i1;
        this.g = j1;
        this.h = k1;
        this.i = l1;
        this.j = i2;
    }

    public VoxelShapeBitSet(VoxelShapeDiscrete voxelshapediscrete) {
        super(voxelshapediscrete.a, voxelshapediscrete.b, voxelshapediscrete.c);
        if (voxelshapediscrete instanceof VoxelShapeBitSet) {
            this.d = (BitSet)((VoxelShapeBitSet)voxelshapediscrete).d.clone();
        } else {
            this.d = new BitSet(this.a * this.b * this.c);

            for(int ix = 0; ix < this.a; ++ix) {
                for(int jx = 0; jx < this.b; ++jx) {
                    for(int k = 0; k < this.c; ++k) {
                        if (voxelshapediscrete.b(ix, jx, k)) {
                            this.d.set(this.a(ix, jx, k));
                        }
                    }
                }
            }
        }

        this.e = voxelshapediscrete.a(EnumDirection.EnumAxis.X);
        this.f = voxelshapediscrete.a(EnumDirection.EnumAxis.Y);
        this.g = voxelshapediscrete.a(EnumDirection.EnumAxis.Z);
        this.h = voxelshapediscrete.b(EnumDirection.EnumAxis.X);
        this.i = voxelshapediscrete.b(EnumDirection.EnumAxis.Y);
        this.j = voxelshapediscrete.b(EnumDirection.EnumAxis.Z);
    }

    protected int a(int ix, int jx, int k) {
        return (ix * this.b + jx) * this.c + k;
    }

    public boolean b(int ix, int jx, int k) {
        return this.d.get(this.a(ix, jx, k));
    }

    public void a(int ix, int jx, int k, boolean flag, boolean flag1) {
        this.d.set(this.a(ix, jx, k), flag1);
        if (flag && flag1) {
            this.e = Math.min(this.e, ix);
            this.f = Math.min(this.f, jx);
            this.g = Math.min(this.g, k);
            this.h = Math.max(this.h, ix + 1);
            this.i = Math.max(this.i, jx + 1);
            this.j = Math.max(this.j, k + 1);
        }

    }

    public boolean a() {
        return this.d.isEmpty();
    }

    public int a(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return enumdirection$enumaxis.a(this.e, this.f, this.g);
    }

    public int b(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return enumdirection$enumaxis.a(this.h, this.i, this.j);
    }

    protected boolean a(int ix, int jx, int k, int l) {
        if (k >= 0 && l >= 0 && ix >= 0) {
            if (k < this.a && l < this.b && jx <= this.c) {
                return this.d.nextClearBit(this.a(k, l, ix)) >= this.a(k, l, jx);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected void a(int ix, int jx, int k, int l, boolean flag) {
        this.d.set(this.a(k, l, ix), this.a(k, l, jx), flag);
    }

    static VoxelShapeBitSet a(VoxelShapeDiscrete voxelshapediscrete, VoxelShapeDiscrete voxelshapediscrete1, VoxelShapeMerger voxelshapemerger, VoxelShapeMerger voxelshapemerger1, VoxelShapeMerger voxelshapemerger2, OperatorBoolean operatorboolean) {
        VoxelShapeBitSet voxelshapebitset = new VoxelShapeBitSet(voxelshapemerger.a().size() - 1, voxelshapemerger1.a().size() - 1, voxelshapemerger2.a().size() - 1);
        int[] aint = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        voxelshapemerger.a((ix, jx, k) -> {
            boolean[] aboolean = new boolean[]{false};
            boolean flag = voxelshapemerger1.a((k1, l1, i2) -> {
                boolean[] aboolean2 = new boolean[]{false};
                boolean flag1 = voxelshapemerger2.a((l3, i4, j4) -> {
                    boolean flag2 = operatorboolean.apply(voxelshapediscrete.c(ix, k1, l3), voxelshapediscrete1.c(jx, l1, i4));
                    if (flag2) {
                        voxelshapebitset.d.set(voxelshapebitset.a(k, i2, j4));
                        aint[2] = Math.min(aint[2], j4);
                        aint[5] = Math.max(aint[5], j4);
                        aboolean2[0] = true;
                    }

                    return true;
                });
                if (aboolean2[0]) {
                    aint[1] = Math.min(aint[1], i2);
                    aint[4] = Math.max(aint[4], i2);
                    aboolean[0] = true;
                }

                return flag1;
            });
            if (aboolean[0]) {
                aint[0] = Math.min(aint[0], k);
                aint[3] = Math.max(aint[3], k);
            }

            return flag;
        });
        voxelshapebitset.e = aint[0];
        voxelshapebitset.f = aint[1];
        voxelshapebitset.g = aint[2];
        voxelshapebitset.h = aint[3] + 1;
        voxelshapebitset.i = aint[4] + 1;
        voxelshapebitset.j = aint[5] + 1;
        return voxelshapebitset;
    }
}
