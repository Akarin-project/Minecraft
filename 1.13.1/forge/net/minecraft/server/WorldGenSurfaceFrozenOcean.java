package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceFrozenOcean implements WorldGenSurface<WorldGenSurfaceConfigurationBase> {
    protected static final IBlockData a = Blocks.PACKED_ICE.getBlockData();
    protected static final IBlockData b = Blocks.SNOW_BLOCK.getBlockData();
    private static final IBlockData c = Blocks.AIR.getBlockData();
    private static final IBlockData d = Blocks.GRAVEL.getBlockData();
    private static final IBlockData e = Blocks.ICE.getBlockData();
    private NoiseGenerator3 f;
    private NoiseGenerator3 g;
    private long h;

    public WorldGenSurfaceFrozenOcean() {
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long var12, WorldGenSurfaceConfigurationBase var14) {
        double d1 = 0.0D;
        double d2 = 0.0D;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        float fx = biomebase.c(blockposition$mutableblockposition.c(i, 63, j));
        double d3 = Math.min(Math.abs(d0), this.f.a((double)i * 0.1D, (double)j * 0.1D));
        if (d3 > 1.8D) {
            double d4 = 0.09765625D;
            double d5 = Math.abs(this.g.a((double)i * 0.09765625D, (double)j * 0.09765625D));
            d1 = d3 * d3 * 1.2D;
            double d6 = Math.ceil(d5 * 40.0D) + 14.0D;
            if (d1 > d6) {
                d1 = d6;
            }

            if (fx > 0.1F) {
                d1 -= 2.0D;
            }

            if (d1 > 2.0D) {
                d2 = (double)l - d1 - 7.0D;
                d1 = d1 + (double)l;
            } else {
                d1 = 0.0D;
            }
        }

        int k2 = i & 15;
        int i1 = j & 15;
        IBlockData iblockdata4 = biomebase.r().b();
        IBlockData iblockdata2 = biomebase.r().a();
        int l2 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int j1 = -1;
        int k1 = 0;
        int l1 = 2 + random.nextInt(4);
        int i2 = l + 18 + random.nextInt(10);

        for(int j2 = Math.max(k, (int)d1 + 1); j2 >= 0; --j2) {
            blockposition$mutableblockposition.c(k2, j2, i1);
            if (ichunkaccess.getType(blockposition$mutableblockposition).isAir() && j2 < (int)d1 && random.nextDouble() > 0.01D) {
                ichunkaccess.a(blockposition$mutableblockposition, a, false);
            } else if (ichunkaccess.getType(blockposition$mutableblockposition).getMaterial() == Material.WATER && j2 > (int)d2 && j2 < l && d2 != 0.0D && random.nextDouble() > 0.15D) {
                ichunkaccess.a(blockposition$mutableblockposition, a, false);
            }

            IBlockData iblockdata3 = ichunkaccess.getType(blockposition$mutableblockposition);
            if (iblockdata3.isAir()) {
                j1 = -1;
            } else if (iblockdata3.getBlock() != iblockdata.getBlock()) {
                if (iblockdata3.getBlock() == Blocks.PACKED_ICE && k1 <= l1 && j2 > i2) {
                    ichunkaccess.a(blockposition$mutableblockposition, b, false);
                    ++k1;
                }
            } else if (j1 == -1) {
                if (l2 <= 0) {
                    iblockdata2 = c;
                    iblockdata4 = iblockdata;
                } else if (j2 >= l - 4 && j2 <= l + 1) {
                    iblockdata2 = biomebase.r().a();
                    iblockdata4 = biomebase.r().b();
                }

                if (j2 < l && (iblockdata2 == null || iblockdata2.isAir())) {
                    if (biomebase.c(blockposition$mutableblockposition.c(i, j2, j)) < 0.15F) {
                        iblockdata2 = e;
                    } else {
                        iblockdata2 = iblockdata1;
                    }
                }

                j1 = l2;
                if (j2 >= l - 1) {
                    ichunkaccess.a(blockposition$mutableblockposition, iblockdata2, false);
                } else if (j2 < l - 7 - l2) {
                    iblockdata2 = c;
                    iblockdata4 = iblockdata;
                    ichunkaccess.a(blockposition$mutableblockposition, d, false);
                } else {
                    ichunkaccess.a(blockposition$mutableblockposition, iblockdata4, false);
                }
            } else if (j1 > 0) {
                --j1;
                ichunkaccess.a(blockposition$mutableblockposition, iblockdata4, false);
                if (j1 == 0 && iblockdata4.getBlock() == Blocks.SAND && l2 > 1) {
                    j1 = random.nextInt(4) + Math.max(0, j2 - 63);
                    iblockdata4 = iblockdata4.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getBlockData() : Blocks.SANDSTONE.getBlockData();
                }
            }
        }

    }

    public void a(long i) {
        if (this.h != i || this.f == null || this.g == null) {
            SeededRandom seededrandom = new SeededRandom(i);
            this.f = new NoiseGenerator3(seededrandom, 4);
            this.g = new NoiseGenerator3(seededrandom, 1);
        }

        this.h = i;
    }
}
