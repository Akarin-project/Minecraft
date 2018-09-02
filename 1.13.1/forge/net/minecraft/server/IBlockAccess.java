package net.minecraft.server;

import javax.annotation.Nullable;

public interface IBlockAccess {
    @Nullable
    TileEntity getTileEntity(BlockPosition var1);

    IBlockData getType(BlockPosition var1);

    Fluid b(BlockPosition var1);

    default int K() {
        return 15;
    }
}
