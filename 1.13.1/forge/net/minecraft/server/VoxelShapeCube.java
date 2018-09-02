package net.minecraft.server;

import it.unimi.dsi.fastutil.doubles.DoubleList;

final class VoxelShapeCube extends VoxelShape {
    VoxelShapeCube(VoxelShapeDiscrete voxelshapediscrete) {
        super(voxelshapediscrete);
    }

    protected DoubleList a(EnumDirection.EnumAxis enumdirection$enumaxis) {
        return new VoxelShapeCubePoint(this.a.c(enumdirection$enumaxis));
    }
}
