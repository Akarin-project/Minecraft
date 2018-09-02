package net.minecraft.server;

import java.util.Arrays;
import java.util.Random;

public class WorldGenSurfaceMesa implements WorldGenSurface<WorldGenSurfaceConfigurationBase> {
    private static final IBlockData f = Blocks.WHITE_TERRACOTTA.getBlockData();
    private static final IBlockData g = Blocks.ORANGE_TERRACOTTA.getBlockData();
    private static final IBlockData h = Blocks.TERRACOTTA.getBlockData();
    private static final IBlockData i = Blocks.YELLOW_TERRACOTTA.getBlockData();
    private static final IBlockData j = Blocks.BROWN_TERRACOTTA.getBlockData();
    private static final IBlockData k = Blocks.RED_TERRACOTTA.getBlockData();
    private static final IBlockData l = Blocks.LIGHT_GRAY_TERRACOTTA.getBlockData();
    protected IBlockData[] a;
    protected long b;
    protected NoiseGenerator3 c;
    protected NoiseGenerator3 d;
    protected NoiseGenerator3 e;

    public WorldGenSurfaceMesa() {
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int ix, int jx, int kx, double d0, IBlockData iblockdata, IBlockData iblockdata1, int lx, long var12, WorldGenSurfaceConfigurationBase var14) {
        int i1 = ix & 15;
        int j1 = jx & 15;
        IBlockData iblockdata2 = f;
        IBlockData iblockdata3 = biomebase.r().b();
        int k1 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag = Math.cos(d0 / 3.0D * Math.PI) > 0.0D;
        int l1 = -1;
        boolean flag1 = false;
        int i2 = 0;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int j2 = kx; j2 >= 0; --j2) {
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
                        } else if (j2 >= lx - 4 && j2 <= lx + 1) {
                            iblockdata2 = f;
                            iblockdata3 = biomebase.r().b();
                        }

                        if (j2 < lx && (iblockdata2 == null || iblockdata2.isAir())) {
                            iblockdata2 = iblockdata1;
                        }

                        l1 = k1 + Math.max(0, j2 - lx);
                        if (j2 >= lx - 1) {
                            if (j2 > lx + 3 + k1) {
                                IBlockData iblockdata5;
                                if (j2 >= 64 && j2 <= 127) {
                                    if (flag) {
                                        iblockdata5 = h;
                                    } else {
                                        iblockdata5 = this.a(ix, j2, jx);
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
                            Block block = iblockdata3.getBlock();
                            if (block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA) {
                                ichunkaccess.a(blockposition$mutableblockposition, g, false);
                            }
                        }
                    } else if (l1 > 0) {
                        --l1;
                        if (flag1) {
                            ichunkaccess.a(blockposition$mutableblockposition, g, false);
                        } else {
                            ichunkaccess.a(blockposition$mutableblockposition, this.a(ix, j2, jx), false);
                        }
                    }

                    ++i2;
                }
            }
        }

    }

    public void a(long ix) {
        if (this.b != ix || this.a == null) {
            this.b(ix);
        }

        if (this.b != ix || this.c == null || this.d == null) {
            SeededRandom seededrandom = new SeededRandom(ix);
            this.c = new NoiseGenerator3(seededrandom, 4);
            this.d = new NoiseGenerator3(seededrandom, 1);
        }

        this.b = ix;
    }

    protected void b(long ix) {
        this.a = new IBlockData[64];
        Arrays.fill(this.a, h);
        SeededRandom seededrandom = new SeededRandom(ix);
        this.e = new NoiseGenerator3(seededrandom, 1);

        for(int i2 = 0; i2 < 64; ++i2) {
            i2 += seededrandom.nextInt(5) + 1;
            if (i2 < 64) {
                this.a[i2] = g;
            }
        }

        int j2 = seededrandom.nextInt(4) + 2;

        for(int jx = 0; jx < j2; ++jx) {
            int kx = seededrandom.nextInt(3) + 1;
            int lx = seededrandom.nextInt(64);

            for(int i1 = 0; lx + i1 < 64 && i1 < kx; ++i1) {
                this.a[lx + i1] = i;
            }
        }

        int k2 = seededrandom.nextInt(4) + 2;

        for(int l2 = 0; l2 < k2; ++l2) {
            int j3 = seededrandom.nextInt(3) + 2;
            int i4 = seededrandom.nextInt(64);

            for(int j1 = 0; i4 + j1 < 64 && j1 < j3; ++j1) {
                this.a[i4 + j1] = j;
            }
        }

        int i3 = seededrandom.nextInt(4) + 2;

        for(int k3 = 0; k3 < i3; ++k3) {
            int j4 = seededrandom.nextInt(3) + 1;
            int l4 = seededrandom.nextInt(64);

            for(int k1 = 0; l4 + k1 < 64 && k1 < j4; ++k1) {
                this.a[l4 + k1] = k;
            }
        }

        int l3 = seededrandom.nextInt(3) + 3;
        int k4 = 0;

        for(int i5 = 0; i5 < l3; ++i5) {
            boolean flag = true;
            k4 += seededrandom.nextInt(16) + 4;

            for(int l1 = 0; k4 + l1 < 64 && l1 < 1; ++l1) {
                this.a[k4 + l1] = f;
                if (k4 + l1 > 1 && seededrandom.nextBoolean()) {
                    this.a[k4 + l1 - 1] = l;
                }

                if (k4 + l1 < 63 && seededrandom.nextBoolean()) {
                    this.a[k4 + l1 + 1] = l;
                }
            }
        }

    }

    protected IBlockData a(int ix, int jx, int kx) {
        int lx = (int)Math.round(this.e.a((double)ix / 512.0D, (double)kx / 512.0D) * 2.0D);
        return this.a[(jx + lx + 64) % 64];
    }
}
