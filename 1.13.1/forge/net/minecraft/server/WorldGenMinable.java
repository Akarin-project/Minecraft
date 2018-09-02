package net.minecraft.server;

import java.util.BitSet;
import java.util.Random;

public class WorldGenMinable extends WorldGenerator<WorldGenFeatureOreConfiguration> {
    public WorldGenMinable() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureOreConfiguration worldgenfeatureoreconfiguration) {
        float f = random.nextFloat() * (float)Math.PI;
        float f1 = (float)worldgenfeatureoreconfiguration.c / 8.0F;
        int i = MathHelper.f(((float)worldgenfeatureoreconfiguration.c / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)((float)blockposition.getX() + MathHelper.sin(f) * f1);
        double d1 = (double)((float)blockposition.getX() - MathHelper.sin(f) * f1);
        double d2 = (double)((float)blockposition.getZ() + MathHelper.cos(f) * f1);
        double d3 = (double)((float)blockposition.getZ() - MathHelper.cos(f) * f1);
        boolean flag = true;
        double d4 = (double)(blockposition.getY() + random.nextInt(3) - 2);
        double d5 = (double)(blockposition.getY() + random.nextInt(3) - 2);
        int j = blockposition.getX() - MathHelper.f(f1) - i;
        int k = blockposition.getY() - 2 - i;
        int l = blockposition.getZ() - MathHelper.f(f1) - i;
        int i1 = 2 * (MathHelper.f(f1) + i);
        int j1 = 2 * (2 + i);

        for(int k1 = j; k1 <= j + i1; ++k1) {
            for(int l1 = l; l1 <= l + i1; ++l1) {
                if (k <= generatoraccess.a(HeightMap.Type.OCEAN_FLOOR_WG, k1, l1)) {
                    return this.a(generatoraccess, random, worldgenfeatureoreconfiguration, d0, d1, d2, d3, d4, d5, j, k, l, i1, j1);
                }
            }
        }

        return false;
    }

    protected boolean a(GeneratorAccess generatoraccess, Random random, WorldGenFeatureOreConfiguration worldgenfeatureoreconfiguration, double d0, double d1, double d2, double d3, double d4, double d5, int i, int j, int k, int l, int i1) {
        int j1 = 0;
        BitSet bitset = new BitSet(l * i1 * l);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        double[] adouble = new double[worldgenfeatureoreconfiguration.c * 4];

        for(int k1 = 0; k1 < worldgenfeatureoreconfiguration.c; ++k1) {
            float f = (float)k1 / (float)worldgenfeatureoreconfiguration.c;
            double d6 = d0 + (d1 - d0) * (double)f;
            double d8 = d4 + (d5 - d4) * (double)f;
            double d10 = d2 + (d3 - d2) * (double)f;
            double d12 = random.nextDouble() * (double)worldgenfeatureoreconfiguration.c / 16.0D;
            double d13 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * d12 + 1.0D) / 2.0D;
            adouble[k1 * 4 + 0] = d6;
            adouble[k1 * 4 + 1] = d8;
            adouble[k1 * 4 + 2] = d10;
            adouble[k1 * 4 + 3] = d13;
        }

        for(int i4 = 0; i4 < worldgenfeatureoreconfiguration.c - 1; ++i4) {
            if (!(adouble[i4 * 4 + 3] <= 0.0D)) {
                for(int k4 = i4 + 1; k4 < worldgenfeatureoreconfiguration.c; ++k4) {
                    if (!(adouble[k4 * 4 + 3] <= 0.0D)) {
                        double d18 = adouble[i4 * 4 + 0] - adouble[k4 * 4 + 0];
                        double d19 = adouble[i4 * 4 + 1] - adouble[k4 * 4 + 1];
                        double d20 = adouble[i4 * 4 + 2] - adouble[k4 * 4 + 2];
                        double d21 = adouble[i4 * 4 + 3] - adouble[k4 * 4 + 3];
                        if (d21 * d21 > d18 * d18 + d19 * d19 + d20 * d20) {
                            if (d21 > 0.0D) {
                                adouble[k4 * 4 + 3] = -1.0D;
                            } else {
                                adouble[i4 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int j4 = 0; j4 < worldgenfeatureoreconfiguration.c; ++j4) {
            double d17 = adouble[j4 * 4 + 3];
            if (!(d17 < 0.0D)) {
                double d7 = adouble[j4 * 4 + 0];
                double d9 = adouble[j4 * 4 + 1];
                double d11 = adouble[j4 * 4 + 2];
                int l1 = Math.max(MathHelper.floor(d7 - d17), i);
                int l4 = Math.max(MathHelper.floor(d9 - d17), j);
                int i2 = Math.max(MathHelper.floor(d11 - d17), k);
                int j2 = Math.max(MathHelper.floor(d7 + d17), l1);
                int k2 = Math.max(MathHelper.floor(d9 + d17), l4);
                int l2 = Math.max(MathHelper.floor(d11 + d17), i2);

                for(int i3 = l1; i3 <= j2; ++i3) {
                    double d14 = ((double)i3 + 0.5D - d7) / d17;
                    if (d14 * d14 < 1.0D) {
                        for(int j3 = l4; j3 <= k2; ++j3) {
                            double d15 = ((double)j3 + 0.5D - d9) / d17;
                            if (d14 * d14 + d15 * d15 < 1.0D) {
                                for(int k3 = i2; k3 <= l2; ++k3) {
                                    double d16 = ((double)k3 + 0.5D - d11) / d17;
                                    if (d14 * d14 + d15 * d15 + d16 * d16 < 1.0D) {
                                        int l3 = i3 - i + (j3 - j) * l + (k3 - k) * l * i1;
                                        if (!bitset.get(l3)) {
                                            bitset.set(l3);
                                            blockposition$mutableblockposition.c(i3, j3, k3);
                                            if (worldgenfeatureoreconfiguration.b.test(generatoraccess.getType(blockposition$mutableblockposition))) {
                                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, worldgenfeatureoreconfiguration.d, 2);
                                                ++j1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return j1 > 0;
    }
}
