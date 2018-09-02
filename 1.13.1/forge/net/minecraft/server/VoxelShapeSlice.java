package net.minecraft.server;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class VoxelShapeSlice extends VoxelShape {
    private final VoxelShape b;
    private final EnumDirection.EnumAxis c;
    private final DoubleList d = new VoxelShapeCubePoint(1);

    public VoxelShapeSlice(VoxelShape voxelshape, EnumDirection.EnumAxis enumdirection$enumaxis, int i) {
        super(a(voxelshape.a, enumdirection$enumaxis, i));
        this.b = voxelshape;
        this.c = enumdirection$enumaxis;
    }

    private static VoxelShapeDiscrete a(VoxelShapeDiscrete voxelshapediscrete, EnumDirection.EnumAxis enumdirection$enumaxis, int i) {
        return new VoxelShapeDiscreteSlice(voxelshapediscrete, enumdirection$enumaxis.a(i, 0, 0), enumdirection$enumaxis.a(0, i, 0), enumdirection$enumaxis.a(0, 0, i), enumdirection$enumaxis.a(i + 1, voxelshapediscrete.a, voxelshapediscrete.a), enumdirection$enumaxis.a(voxelshapediscrete.b, i + 1, voxelshapediscrete.b), enumdirection$enumaxis.a(voxelshapediscrete.c, voxelshapediscrete.c, i + 1));
    }

    protected DoubleList a(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return enumdirection$enumaxis == this.c ? this.d : this.b.a(enumdirection$enumaxis);
    }
}
