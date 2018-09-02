package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import java.util.BitSet;
import java.util.Random;

public class WorldGenCavesHell extends WorldGenCaves {
    public WorldGenCavesHell() {
        this.e = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, new Block[]{Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK});
        this.f = ImmutableSet.of(FluidTypes.e, FluidTypes.c);
    }

    public boolean a(IBlockAccess var1, Random random, int var3, int var4, WorldGenFeatureConfigurationChance worldgenfeatureconfigurationchance) {
        return random.nextFloat() <= worldgenfeatureconfigurationchance.a;
    }

    public boolean a(GeneratorAccess generatoraccess, Random random, int i, int j, int k, int l, BitSet bitset, WorldGenFeatureConfigurationChance var8) {
        int i1 = (this.a() * 2 - 1) * 16;
        int j1 = random.nextInt(random.nextInt(random.nextInt(10) + 1) + 1);

        for(int k1 = 0; k1 < j1; ++k1) {
            double d0 = (double)(i * 16 + random.nextInt(16));
            double d1 = (double)random.nextInt(128);
            double d2 = (double)(j * 16 + random.nextInt(16));
            int l1 = 1;
            if (random.nextInt(4) == 0) {
                double d3 = 0.5D;
                float f1 = 1.0F + random.nextFloat() * 6.0F;
                this.a(generatoraccess, random.nextLong(), k, l, d0, d1, d2, f1, 0.5D, bitset);
                l1 += random.nextInt(4);
            }

            for(int j2 = 0; j2 < l1; ++j2) {
                float f = random.nextFloat() * ((float)Math.PI * 2F);
                float f3 = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
                double d4 = 5.0D;
                float f2 = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
                int i2 = i1 - random.nextInt(i1 / 4);
                boolean flag = false;
                this.a(generatoraccess, random.nextLong(), k, l, d0, d1, d2, f2, f, f3, 0, i2, 5.0D, bitset);
            }
        }

        return true;
    }

    protected boolean a(GeneratorAccess generatoraccess, long var2, int i, int j, double d0, double d1, double d2, double d3, double d4, BitSet bitset) {
        double d5 = (double)(i * 16 + 8);
        double d6 = (double)(j * 16 + 8);
        if (!(d0 < d5 - 16.0D - d3 * 2.0D) && !(d2 < d6 - 16.0D - d3 * 2.0D) && !(d0 > d5 + 16.0D + d3 * 2.0D) && !(d2 > d6 + 16.0D + d3 * 2.0D)) {
            int k = Math.max(MathHelper.floor(d0 - d3) - i * 16 - 1, 0);
            int l = Math.min(MathHelper.floor(d0 + d3) - i * 16 + 1, 16);
            int i1 = Math.max(MathHelper.floor(d1 - d4) - 1, 1);
            int j1 = Math.min(MathHelper.floor(d1 + d4) + 1, 120);
            int k1 = Math.max(MathHelper.floor(d2 - d3) - j * 16 - 1, 0);
            int l1 = Math.min(MathHelper.floor(d2 + d3) - j * 16 + 1, 16);
            if (this.a(generatoraccess, i, j, k, l, i1, j1, k1, l1)) {
                return false;
            } else if (k <= l && i1 <= j1 && k1 <= l1) {
                boolean flag = false;

                for(int i2 = k; i2 < l; ++i2) {
                    int j2 = i2 + i * 16;
                    double d7 = ((double)j2 + 0.5D - d0) / d3;

                    for(int k2 = k1; k2 < l1; ++k2) {
                        int l2 = k2 + j * 16;
                        double d8 = ((double)l2 + 0.5D - d2) / d3;

                        for(int i3 = j1; i3 > i1; --i3) {
                            double d9 = ((double)(i3 - 1) + 0.5D - d1) / d4;
                            if (d9 > -0.7D && d7 * d7 + d9 * d9 + d8 * d8 < 1.0D) {
                                int j3 = i2 | k2 << 4 | i3 << 8;
                                if (!bitset.get(j3)) {
                                    bitset.set(j3);
                                    if (this.a(generatoraccess.getType(new BlockPosition(j2, i3, l2)))) {
                                        if (i3 <= 31) {
                                            generatoraccess.setTypeAndData(new BlockPosition(j2, i3, l2), d.i(), 2);
                                        } else {
                                            generatoraccess.setTypeAndData(new BlockPosition(j2, i3, l2), b, 2);
                                        }

                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }

                return flag;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
