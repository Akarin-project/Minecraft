package net.minecraft.server;

import it.unimi.dsi.fastutil.doubles.DoubleList;

interface VoxelShapeMerger {
    DoubleList a();

    boolean a(VoxelShapeMerger.a var1);

    public interface a {
        boolean merge(int var1, int var2, int var3);
    }
}
