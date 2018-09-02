package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceNether implements WorldGenSurface<WorldGenSurfaceConfigurationBase> {
    private static final IBlockData c = Blocks.CAVE_AIR.getBlockData();
    private static final IBlockData d = Blocks.NETHERRACK.getBlockData();
    private static final IBlockData e = Blocks.GRAVEL.getBlockData();
    private static final IBlockData f = Blocks.SOUL_SAND.getBlockData();
    protected long a;
    protected NoiseGeneratorOctaves b;

    public WorldGenSurfaceNether() {
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase var3, int i, int j, int var6, double d0, IBlockData iblockdata, IBlockData iblockdata1, int k, long var12, WorldGenSurfaceConfigurationBase var14) {
        int l = k + 1;
        int i1 = i & 15;
        int j1 = j & 15;
        double d1 = 0.03125D;
        boolean flag = this.b.a((double)i * 0.03125D, (double)j * 0.03125D, 0.0D) + random.nextDouble() * 0.2D > 0.0D;
        boolean flag1 = this.b.a((double)i * 0.03125D, 109.0D, (double)j * 0.03125D) + random.nextDouble() * 0.2D > 0.0D;
        int k1 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        int l1 = -1;
        IBlockData iblockdata2 = d;
        IBlockData iblockdata3 = d;

        for(int i2 = 127; i2 >= 0; --i2) {
            blockposition$mutableblockposition.c(i1, i2, j1);
            IBlockData iblockdata4 = ichunkaccess.getType(blockposition$mutableblockposition);
            if (iblockdata4.getBlock() != null && !iblockdata4.isAir()) {
                if (iblockdata4.getBlock() == iblockdata.getBlock()) {
                    if (l1 == -1) {
                        if (k1 <= 0) {
                            iblockdata2 = c;
                            iblockdata3 = d;
                        } else if (i2 >= l - 4 && i2 <= l + 1) {
                            iblockdata2 = d;
                            iblockdata3 = d;
                            if (flag1) {
                                iblockdata2 = e;
                                iblockdata3 = d;
                            }

                            if (flag) {
                                iblockdata2 = f;
                                iblockdata3 = f;
                            }
                        }

                        if (i2 < l && (iblockdata2 == null || iblockdata2.isAir())) {
                            iblockdata2 = iblockdata1;
                        }

                        l1 = k1;
                        if (i2 >= l - 1) {
                            ichunkaccess.a(blockposition$mutableblockposition, iblockdata2, false);
                        } else {
                            ichunkaccess.a(blockposition$mutableblockposition, iblockdata3, false);
                        }
                    } else if (l1 > 0) {
                        --l1;
                        ichunkaccess.a(blockposition$mutableblockposition, iblockdata3, false);
                    }
                }
            } else {
                l1 = -1;
            }
        }

    }

    public void a(long i) {
        if (this.a != i || this.b == null) {
            this.b = new NoiseGeneratorOctaves(new SeededRandom(i), 4);
        }

        this.a = i;
    }
}
