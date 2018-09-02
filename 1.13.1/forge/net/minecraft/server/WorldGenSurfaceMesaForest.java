package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceMesaForest extends WorldGenSurfaceMesa {
    private static final IBlockData f = Blocks.WHITE_TERRACOTTA.getBlockData();
    private static final IBlockData g = Blocks.ORANGE_TERRACOTTA.getBlockData();
    private static final IBlockData h = Blocks.TERRACOTTA.getBlockData();

    public WorldGenSurfaceMesaForest() {
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long var12, WorldGenSurfaceConfigurationBase var14) {
        int i1 = i & 15;
        int j1 = j & 15;
        IBlockData iblockdata2 = f;
        IBlockData iblockdata3 = biomebase.r().b();
        int k1 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag = Math.cos(d0 / 3.0D * Math.PI) > 0.0D;
        int l1 = -1;
        boolean flag1 = false;
        int i2 = 0;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int j2 = k; j2 >= 0; --j2) {
            if (i2 < 15) {
                blockposition$mutableblockposition.c(i1, j2, j1);
                IBlockData iblockdata4 = ichunkaccess.getType(blockposition$mutableblockposition);
                if (iblockdata4.isAir()) {
                    l1 = -1;
                } else if (iblockdata4.getBlock() == iblockdata.getBlock()) {
                    if (l1 == -1) {
                        flag1 = false;
                        if (k1 <= 0) {
                            iblockdata2 = Blocks.AIR.getBlockData();
                            iblockdata3 = iblockdata;
                        } else if (j2 >= l - 4 && j2 <= l + 1) {
                            iblockdata2 = f;
                            iblockdata3 = biomebase.r().b();
                        }

                        if (j2 < l && (iblockdata2 == null || iblockdata2.isAir())) {
                            iblockdata2 = iblockdata1;
                        }

                        l1 = k1 + Math.max(0, j2 - l);
                        if (j2 >= l - 1) {
                            if (j2 > 86 + k1 * 2) {
                                if (flag) {
                                    ichunkaccess.a(blockposition$mutableblockposition, Blocks.COARSE_DIRT.getBlockData(), false);
                                } else {
                                    ichunkaccess.a(blockposition$mutableblockposition, Blocks.GRASS_BLOCK.getBlockData(), false);
                                }
                            } else if (j2 > l + 3 + k1) {
                                IBlockData iblockdata5;
                                if (j2 >= 64 && j2 <= 127) {
                                    if (flag) {
                                        iblockdata5 = h;
                                    } else {
                                        iblockdata5 = this.a(i, j2, j);
                                    }
                                } else {
                                    iblockdata5 = g;
                                }

                                ichunkaccess.a(blockposition$mutableblockposition, iblockdata5, false);
                            } else {
                                ichunkaccess.a(blockposition$mutableblockposition, biomebase.r().a(), false);
                                flag1 = true;
                            }
                        } else {
                            ichunkaccess.a(blockposition$mutableblockposition, iblockdata3, false);
                            if (iblockdata3.getBlock() == f) {
                                ichunkaccess.a(blockposition$mutableblockposition, g, false);
                            }
                        }
                    } else if (l1 > 0) {
                        --l1;
                        if (flag1) {
                            ichunkaccess.a(blockposition$mutableblockposition, g, false);
                        } else {
                            ichunkaccess.a(blockposition$mutableblockposition, this.a(i, j2, j), false);
                        }
                    }

                    ++i2;
                }
            }
        }

    }
}
