package net.minecraft.server;

final class VoxelShapeDiscreteSlice extends VoxelShapeDiscrete {
    private final VoxelShapeDiscrete d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private final int i;
    private final int j;

    public VoxelShapeDiscreteSlice(VoxelShapeDiscrete voxelshapediscrete, int ix, int jx, int k, int l, int i1, int j1) {
        super(l - ix, i1 - jx, j1 - k);
        this.d = voxelshapediscrete;
        this.e = ix;
        this.f = jx;
        this.g = k;
        this.h = l;
        this.i = i1;
        this.j = j1;
    }

    public boolean b(int ix, int jx, int k) {
        return this.d.b(this.e + ix, this.f + jx, this.g + k);
    }

    public void a(int ix, int jx, int k, boolean flag, boolean flag1) {
        this.d.a(this.e + ix, this.f + jx, this.g + k, flag, flag1);
    }

    public int a(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return Math.max(0, this.d.a(enumdirection$enumaxis) - enumdirection$enumaxis.a(this.e, this.f, this.g));
    }

    public int b(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return Math.min(enumdirection$enumaxis.a(this.h, this.i, this.j), this.d.b(enumdirection$enumaxis) - enumdirection$enumaxis.a(this.e, this.f, this.g));
    }
}
