package net.minecraft.server;

import java.util.Random;

public interface WorldGenSurface<C extends WorldGenSurfaceConfiguration> {
    void a(Random var1, IChunkAccess var2, BiomeBase var3, int var4, int var5, int var6, double var7, IBlockData var9, IBlockData var10, int var11, long var12, C var14);

    default void a(long var1) {
    }
}
