package net.minecraft.server;

import java.util.BitSet;
import java.util.Random;

public class WorldGenCaves extends WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> {
    public WorldGenCaves() {
    }

    public boolean a(IBlockAccess var1, Random random, int var3, int var4, WorldGenFeatureConfigurationChance worldgenfeatureconfigurationchance) {
        return random.nextFloat() <= worldgenfeatureconfigurationchance.a;
    }

    public boolean a(GeneratorAccess generatoraccess, Random random, int i, int j, int k, int l, BitSet bitset, WorldGenFeatureConfigurationChance var8) {
        int i1 = (this.a() * 2 - 1) * 16;
        int j1 = random.nextInt(random.nextInt(random.nextInt(15) + 1) + 1);

        for(int k1 = 0; k1 < j1; ++k1) {
            double d0 = (double)(i * 16 + random.nextInt(16));
            double d1 = (double)random.nextInt(random.nextInt(120) + 8);
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
                float f3 = (random.nextFloat() - 0.5F) / 4.0F;
                double d4 = 1.0D;
                float f2 = random.nextFloat() * 2.0F + random.nextFloat();
                if (random.nextInt(10) == 0) {
                    f2 *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
                }

                int i2 = i1 - random.nextInt(i1 / 4);
                boolean flag = false;
                this.a(generatoraccess, random.nextLong(), k, l, d0, d1, d2, f2, f, f3, 0, i2, 1.0D, bitset);
            }
        }

        return true;
    }

    protected void a(GeneratorAccess generatoraccess, long i, int j, int k, double d0, double d1, double d2, float f, double d3, BitSet bitset) {
        double d4 = 1.5D + (double)(MathHelper.sin(((float)Math.PI / 2F)) * f);
        double d5 = d4 * d3;
        this.a(generatoraccess, i, j, k, d0 + 1.0D, d1, d2, d4, d5, bitset);
    }

    protected void a(GeneratorAccess generatoraccess, long i, int j, int k, double d0, double d1, double d2, float f, float f1, float f2, int l, int i1, double d3, BitSet bitset) {
        Random random = new Random(i);
        int j1 = random.nextInt(i1 / 2) + i1 / 4;
        boolean flag = random.nextInt(6) == 0;
        float f3 = 0.0F;
        float f4 = 0.0F;

        for(int k1 = l; k1 < i1; ++k1) {
            double d4 = 1.5D + (double)(MathHelper.sin((float)Math.PI * (float)k1 / (float)i1) * f);
            double d5 = d4 * d3;
            float f5 = MathHelper.cos(f2);
            d0 += (double)(MathHelper.cos(f1) * f5);
            d1 += (double)MathHelper.sin(f2);
            d2 += (double)(MathHelper.sin(f1) * f5);
            f2 = f2 * (flag ? 0.92F : 0.7F);
            f2 = f2 + f4 * 0.1F;
            f1 += f3 * 0.1F;
            f4 = f4 * 0.9F;
            f3 = f3 * 0.75F;
            f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 = f3 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (k1 == j1 && f > 1.0F) {
                this.a(generatoraccess, random.nextLong(), j, k, d0, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 - ((float)Math.PI / 2F), f2 / 3.0F, k1, i1, 1.0D, bitset);
                this.a(generatoraccess, random.nextLong(), j, k, d0, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 + ((float)Math.PI / 2F), f2 / 3.0F, k1, i1, 1.0D, bitset);
                return;
            }

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
            } else {
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
                        if (!(d7 * d7 + d8 * d8 >= 1.0D)) {
                            boolean flag1 = false;

                            for(int i3 = j1; i3 > i1; --i3) {
                                double d9 = ((double)i3 - 0.5D - d1) / d4;
                                if (!(d9 <= -0.7D) && !(d7 * d7 + d9 * d9 + d8 * d8 >= 1.0D)) {
                                    int j3 = i2 | k2 << 4 | i3 << 8;
                                    if (!bitset.get(j3)) {
                                        bitset.set(j3);
                                        blockposition$mutableblockposition.c(j2, i3, l2);
                                        IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition);
                                        IBlockData iblockdata1 = generatoraccess.getType(blockposition$mutableblockposition1.g(blockposition$mutableblockposition).c(EnumDirection.UP));
                                        if (iblockdata.getBlock() == Blocks.GRASS_BLOCK || iblockdata.getBlock() == Blocks.MYCELIUM) {
                                            flag1 = true;
                                        }

                                        if (this.a(iblockdata, iblockdata1)) {
                                            if (i3 < 11) {
                                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, d.i(), 2);
                                            } else {
                                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, b, 2);
                                                if (flag1) {
                                                    blockposition$mutableblockposition2.g(blockposition$mutableblockposition).c(EnumDirection.DOWN);
                                                    if (generatoraccess.getType(blockposition$mutableblockposition2).getBlock() == Blocks.DIRT) {
                                                        IBlockData iblockdata2 = generatoraccess.getBiome(blockposition$mutableblockposition).r().a();
                                                        generatoraccess.setTypeAndData(blockposition$mutableblockposition2, iblockdata2, 2);
                                                    }
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
            }
        } else {
            return false;
        }
    }
}