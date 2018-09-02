package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceMesaBryce extends WorldGenSurfaceMesa {
    private static final IBlockData f = Blocks.WHITE_TERRACOTTA.getBlockData();
    private static final IBlockData g = Blocks.ORANGE_TERRACOTTA.getBlockData();
    private static final IBlockData h = Blocks.TERRACOTTA.getBlockData();

    public WorldGenSurfaceMesaBryce() {
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long var12, WorldGenSurfaceConfigurationBase var14) {
        double d1 = 0.0D;
        double d2 = Math.min(Math.abs(d0), this.c.a((double)i * 0.25D, (double)j * 0.25D));
        if (d2 > 0.0D) {
            double d3 = 0.001953125D;
            double d4 = Math.abs(this.d.a((double)i * 0.001953125D, (double)j * 0.001953125D));
            d1 = d2 * d2 * 2.5D;
            double d5 = Math.ceil(d4 * 50.0D) + 14.0D;
            if (d1 > d5) {
                d1 = d5;
            }

            d1 = d1 + 64.0D;
        }

        int l1 = i & 15;
        int i1 = j & 15;
        IBlockData iblockdata4 = f;
        IBlockData iblockdata2 = biomebase.r().b();
        int i2 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag = Math.cos(d0 / 3.0D * Math.PI) > 0.0D;
        int j1 = -1;
        boolean flag1 = false;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int k1 = Math.max(k, (int)d1 + 1); k1 >= 0; --k1) {
            blockposition$mutableblockposition.c(l1, k1, i1);
            if (ichunkaccess.getType(blockposition$mutableblockposition).isAir() && k1 < (int)d1) {
                ichunkaccess.a(blockposition$mutableblockposition, iblockdata, false);
            }

            IBlockData iblockdata3 = ichunkaccess.getType(blockposition$mutableblockposition);
            if (iblockdata3.isAir()) {
                j1 = -1;
            } else if (iblockdata3.getBlock() == iblockdata.getBlock()) {
                if (j1 == -1) {
                    flag1 = false;
                    if (i2 <= 0) {
                        iblockdata4 = Blocks.AIR.getBlockData();
                        iblockdata2 = iblockdata;
                    } else if (k1 >= l - 4 && k1 <= l + 1) {
                        iblockdata4 = f;
                        iblockdata2 = biomebase.r().b();
                    }

                    if (k1 < l && (iblockdata4 == null || iblockdata4.isAir())) {
                        iblockdata4 = iblockdata1;
                    }

                    j1 = i2 + Math.max(0, k1 - l);
                    if (k1 >= l - 1) {
                        if (k1 <= l + 3 + i2) {
                            ichunkaccess.a(blockposition$mutableblockposition, biomebase.r().a(), false);
                            flag1 = true;
                        } else {
                            IBlockData iblockdata5;
                            if (k1 >= 64 && k1 <= 127) {
                                if (flag) {
                                    iblockdata5 = h;
                                } else {
                                    iblockdata5 = this.a(i, k1, j);
                                }
                            } else {
                                iblockdata5 = g;
                            }

                            ichunkaccess.a(blockposition$mutableblockposition, iblockdata5, false);
                        }
                    } else {
                        ichunkaccess.a(blockposition$mutableblockposition, iblockdata2, false);
                        Block block = iblockdata2.getBlock();
                        if (block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA) {
                            ichunkaccess.a(blockposition$mutableblockposition, g, false);
                        }
                    }
                } else if (j1 > 0) {
                    --j1;
                    if (flag1) {
                        ichunkaccess.a(blockposition$mutableblockposition, g, false);
                    } else {
                        ichunkaccess.a(blockposition$mutableblockposition, this.a(i, k1, j), false);
                    }
                }
            }
        }

    }
}
