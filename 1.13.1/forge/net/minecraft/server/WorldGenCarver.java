package net.minecraft.server;

import java.util.BitSet;
import java.util.Random;

public interface WorldGenCarver<C extends WorldGenFeatureConfiguration> {
    boolean a(IBlockAccess var1, Random var2, int var3, int var4, C var5);

    boolean a(GeneratorAccess var1, Random var2, int var3, int var4, int var5, int var6, BitSet var7, C var8);
}
