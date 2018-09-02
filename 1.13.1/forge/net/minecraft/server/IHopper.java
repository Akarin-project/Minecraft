package net.minecraft.server;

import javax.annotation.Nullable;

public interface IHopper extends IInventory {
    VoxelShape a = Block.a(2.0D, 11.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    VoxelShape b = Block.a(0.0D, 16.0D, 0.0D, 16.0D, 32.0D, 16.0D);
    VoxelShape c = VoxelShapes.a(a, b);

    default VoxelShape i() {
        return c;
    }

    @Nullable
    World getWorld();

    double G();

    double H();

    double I();
}
