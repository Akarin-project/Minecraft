package net.minecraft.server;

import java.util.BitSet;
import java.util.Random;

public class WorldGenCanyon extends WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> {
    private final float[] g = new float[1024];

    public WorldGenCanyon() {
    }

    public boolean a(IBlockAccess var1, Random random, int var3, int var4, WorldGenFeatureConfigurationChance worldgenfeatureconfigurationchance) {
        return random.nextFloat() <= worldgenfeatureconfigurationchance.a;
    }

    public boolean a(GeneratorAccess generatoraccess, Random random, int i, int j, int k, int l, BitSet bitset, WorldGenFeatureConfigurationChance var8) {
        int i1 = (this.a() * 2 - 1) * 16;
        double d0 = (double)(i * 16 + random.nextInt(16));
        double d1 = (double)(random.nextInt(random.nextInt(40) + 8) + 20);
        double d2 = (double)(j * 16 + random.nextInt(16));
        float f = random.nextFloat() * ((float)Math.PI * 2F);
        float f1 = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
        double d3 = 3.0D;
        float f2 = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
        int j1 = i1 - random.nextInt(i1 / 4);
        boolean flag = false;
        this.a(generatoraccess, random.nextLong(), k, l, d0, d1, d2, f2, f, f1, 0, j1, 3.0D, bitset);
        return true;
    }

    private void a(GeneratorAccess generatoraccess, long i, int j, int k, double d0, double d1, double d2, float f, float f1, float f2, int l, int i1, double d3, BitSet bitset) {
        Random random = new Random(i);
        float f3 = 1.0F;

        for(int j1 = 0; j1 < 256; ++j1) {
            if (j1 == 0 || random.nextInt(3) == 0) {
                f3 = 1.0F + random.nextFloat() * random.nextFloat();
            }

            this.g[j1] = f3 * f3;
        }

        float f7 = 0.0F;
        float f4 = 0.0F;

        for(int k1 = l; k1 < i1; ++k1) {
            double d4 = 1.5D + (double)(MathHelper.sin((float)k1 * (float)Math.PI / (float)i1) * f);
            double d5 = d4 * d3;
            d4 = d4 * ((double)random.nextFloat() * 0.25D + 0.75D);
            d5 = d5 * ((double)random.nextFloat() * 0.25D + 0.75D);
            float f5 = MathHelper.cos(f2);
            float f6 = MathHelper.sin(f2);
            d0 += (double)(MathHelper.cos(f1) * f5);
            d1 += (double)f6;
            d2 += (double)(MathHelper.sin(f1) * f5);
            f2 = f2 * 0.7F;
            f2 = f2 + f4 * 0.05F;
            f1 += f7 * 0.05F;
            f4 = f4 * 0.8F;
            f7 = f7 * 0.5F;
            f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f7 = f7 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (random.nextInt(4) != 0) {
                if (!this.a(j, k, d0, d2, k1, i1, f)) {
                    return;
                }

                this.a(generatoraccess, i, j, k, d0, d1, d2, d4, d5, bitset);
            }
        }

    }

    protected boolean a(GeneratorAccess generatoraccess, long var2, int i, int j, double d0, double d1, double d2, double d3, double d4, BitSet bitset) {
        double d5 = (double)(i * 16 + 8);
        double d6 = (double)(j * 16 + 8);
        if (!(d0 < d5 - 16.0D - d3 * 2.0D) && !(d2 < d6 - 16.0D - d3 * 2.0D) && !(d0 > d5 + 16.0D + d3 * 2.0D) && !(d2 > d6 + 16.0D + d3 * 2.0D)) {
            int k = Math.max(MathHelper.floor(d0 - d3) - i * 16 - 1, 0);
            int l = Math.min(MathHelper.floor(d0 + d3) - i * 16 + 1, 16);
            int i1 = Math.max(MathHelper.floor(d1 - d4) - 1, 1);
            int j1 = Math.min(MathHelper.floor(d1 + d4) + 1, 248);
            int k1 = Math.max(MathHelper.floor(d2 - d3) - j * 16 - 1, 0);
            int l1 = Math.min(MathHelper.floor(d2 + d3) - j * 16 + 1, 16);
            if (this.a(generatoraccess, i, j, k, l, i1, j1, k1, l1)) {
                return false;
            } else if (k <= l && i1 <= j1 && k1 <= l1) {
                boolean flag = false;
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition2 = new BlockPosition.MutableBlockPosition();

                for(int i2 = k; i2 < l; ++i2) {
                    int j2 = i2 + i * 16;
                    double d7 = ((double)j2 + 0.5D - d0) / d3;

                    for(int k2 = k1; k2 < l1; ++k2) {
                        int l2 = k2 + j * 16;
                        double d8 = ((double)l2 + 0.5D - d2) / d3;
                        if (d7 * d7 + d8 * d8 < 1.0D) {
                            boolean flag1 = false;

                            for(int i3 = j1; i3 > i1; --i3) {
                                double d9 = ((double)(i3 - 1) + 0.5D - d1) / d4;
                                if ((d7 * d7 + d8 * d8) * (double)this.g[i3 - 1] + d9 * d9 / 6.0D < 1.0D) {
                                    int j3 = i2 | k2 << 4 | i3 << 8;
                                    if (!bitset.get(j3)) {
                                        bitset.set(j3);
                                        blockposition$mutableblockposition.c(j2, i3, l2);
                                        IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition);
                                        blockposition$mutableblockposition1.g(blockposition$mutableblockposition).c(EnumDirection.UP);
                                        blockposition$mutableblockposition2.g(blockposition$mutableblockposition).c(EnumDirection.DOWN);
                                        IBlockData iblockdata1 = generatoraccess.getType(blockposition$mutableblockposition1);
                                        if (iblockdata.getBlock() == Blocks.GRASS_BLOCK || iblockdata.getBlock() == Blocks.MYCELIUM) {
                                            flag1 = true;
                                        }

                                        if (this.a(iblockdata, iblockdata1)) {
                                            if (i3 - 1 < 10) {
                                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, d.i(), 2);
                                            } else {
                                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, b, 2);
                                                if (flag1 && generatoraccess.getType(blockposition$mutableblockposition2).getBlock() == Blocks.DIRT) {
                                                    generatoraccess.setTypeAndData(blockposition$mutableblockposition2, generatoraccess.getBiome(blockposition$mutableblockposition).r().a(), 2);
                                                }
                                            }

                                            flag = true;
                                        }
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
