package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class ChunkProviderHell extends ChunkGeneratorAbstract<GeneratorSettingsNether> {
    protected static final IBlockData f = Blocks.AIR.getBlockData();
    protected static final IBlockData g = Blocks.NETHERRACK.getBlockData();
    protected static final IBlockData h = Blocks.LAVA.getBlockData();
    private final NoiseGeneratorOctaves i;
    private final NoiseGeneratorOctaves j;
    private final NoiseGeneratorOctaves k;
    private final NoiseGeneratorOctaves l;
    private final NoiseGeneratorOctaves m;
    private final NoiseGeneratorOctaves n;
    private final GeneratorSettingsNether o;
    private final IBlockData p;
    private final IBlockData q;

    public ChunkProviderHell(World world, WorldChunkManager worldchunkmanager, GeneratorSettingsNether generatorsettingsnether) {
        super(world, worldchunkmanager);
        this.o = generatorsettingsnether;
        this.p = this.o.r();
        this.q = this.o.s();
        SeededRandom seededrandom = new SeededRandom(this.b);
        this.i = new NoiseGeneratorOctaves(seededrandom, 16);
        this.j = new NoiseGeneratorOctaves(seededrandom, 16);
        this.k = new NoiseGeneratorOctaves(seededrandom, 8);
        seededrandom.a(1048);
        this.l = new NoiseGeneratorOctaves(seededrandom, 4);
        this.m = new NoiseGeneratorOctaves(seededrandom, 10);
        this.n = new NoiseGeneratorOctaves(seededrandom, 16);
        world.b(63);
    }

    public void a(int ix, int jx, IChunkAccess ichunkaccess) {
        boolean flag = true;
        int kx = this.a.getSeaLevel() / 2 + 1;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        double[] adouble = this.a(ix * 4, 0, jx * 4, 5, 17, 5);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int lx = 0; lx < 4; ++lx) {
            for(int i1 = 0; i1 < 4; ++i1) {
                for(int j1 = 0; j1 < 16; ++j1) {
                    double d0 = 0.125D;
                    double d1 = adouble[((lx + 0) * 5 + i1 + 0) * 17 + j1 + 0];
                    double d2 = adouble[((lx + 0) * 5 + i1 + 1) * 17 + j1 + 0];
                    double d3 = adouble[((lx + 1) * 5 + i1 + 0) * 17 + j1 + 0];
                    double d4 = adouble[((lx + 1) * 5 + i1 + 1) * 17 + j1 + 0];
                    double d5 = (adouble[((lx + 0) * 5 + i1 + 0) * 17 + j1 + 1] - d1) * 0.125D;
                    double d6 = (adouble[((lx + 0) * 5 + i1 + 1) * 17 + j1 + 1] - d2) * 0.125D;
                    double d7 = (adouble[((lx + 1) * 5 + i1 + 0) * 17 + j1 + 1] - d3) * 0.125D;
                    double d8 = (adouble[((lx + 1) * 5 + i1 + 1) * 17 + j1 + 1] - d4) * 0.125D;

                    for(int k1 = 0; k1 < 8; ++k1) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int l1 = 0; l1 < 4; ++l1) {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for(int i2 = 0; i2 < 4; ++i2) {
                                IBlockData iblockdata = f;
                                if (j1 * 8 + k1 < kx) {
                                    iblockdata = this.q;
                                }

                                if (d15 > 0.0D) {
                                    iblockdata = this.p;
                                }

                                int j2 = l1 + lx * 4;
                                int k2 = k1 + j1 * 8;
                                int l2 = i2 + i1 * 4;
                                ichunkaccess.a(blockposition$mutableblockposition.c(j2, k2, l2), iblockdata, false);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }

    }

    protected void a(IChunkAccess ichunkaccess, Random random) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        int ix = ichunkaccess.getPos().d();
        int jx = ichunkaccess.getPos().e();

        for(BlockPosition blockposition : BlockPosition.a(ix, 0, jx, ix + 16, 0, jx + 16)) {
            for(int kx = 127; kx > 122; --kx) {
                if (kx >= 127 - random.nextInt(5)) {
                    ichunkaccess.a(blockposition$mutableblockposition.c(blockposition.getX(), kx, blockposition.getZ()), Blocks.BEDROCK.getBlockData(), false);
                }
            }

            for(int lx = 4; lx >= 0; --lx) {
                if (lx <= random.nextInt(5)) {
                    ichunkaccess.a(blockposition$mutableblockposition.c(blockposition.getX(), lx, blockposition.getZ()), Blocks.BEDROCK.getBlockData(), false);
                }
            }
        }

    }

    public double[] a(int ix, int jx) {
        double d0 = 0.03125D;
        return this.l.a(ix << 4, jx << 4, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);
    }

    public void createChunk(IChunkAccess ichunkaccess) {
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        int ix = chunkcoordintpair.x;
        int jx = chunkcoordintpair.z;
        SeededRandom seededrandom = new SeededRandom();
        seededrandom.a(ix, jx);
        BiomeBase[] abiomebase = this.c.getBiomeBlock(ix * 16, jx * 16, 16, 16);
        ichunkaccess.a(abiomebase);
        this.a(ix, jx, ichunkaccess);
        this.a(ichunkaccess, abiomebase, seededrandom, this.a.getSeaLevel());
        this.a(ichunkaccess, seededrandom);
        ichunkaccess.a(HeightMap.Type.WORLD_SURFACE_WG, HeightMap.Type.OCEAN_FLOOR_WG);
        ichunkaccess.a(ChunkStatus.BASE);
    }

    private double[] a(int ix, int jx, int kx, int lx, int i1, int j1) {
        double[] adouble = new double[lx * i1 * j1];
        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.m.a(ix, jx, kx, lx, 1, j1, 1.0D, 0.0D, 1.0D);
        this.n.a(ix, jx, kx, lx, 1, j1, 100.0D, 0.0D, 100.0D);
        double[] adouble1 = this.k.a(ix, jx, kx, lx, i1, j1, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        double[] adouble2 = this.i.a(ix, jx, kx, lx, i1, j1, 684.412D, 2053.236D, 684.412D);
        double[] adouble3 = this.j.a(ix, jx, kx, lx, i1, j1, 684.412D, 2053.236D, 684.412D);
        double[] adouble4 = new double[i1];

        for(int k1 = 0; k1 < i1; ++k1) {
            adouble4[k1] = Math.cos((double)k1 * Math.PI * 6.0D / (double)i1) * 2.0D;
            double d2 = (double)k1;
            if (k1 > i1 / 2) {
                d2 = (double)(i1 - 1 - k1);
            }

            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble4[k1] -= d2 * d2 * d2 * 10.0D;
            }
        }

        int j2 = 0;

        for(int k2 = 0; k2 < lx; ++k2) {
            for(int l1 = 0; l1 < j1; ++l1) {
                double d3 = 0.0D;

                for(int i2 = 0; i2 < i1; ++i2) {
                    double d4 = adouble4[i2];
                    double d5 = adouble2[j2] / 512.0D;
                    double d6 = adouble3[j2] / 512.0D;
                    double d7 = (adouble1[j2] / 10.0D + 1.0D) / 2.0D;
                    double d8;
                    if (d7 < 0.0D) {
                        d8 = d5;
                    } else if (d7 > 1.0D) {
                        d8 = d6;
                    } else {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 = d8 - d4;
                    if (i2 > i1 - 4) {
                        double d9 = (double)((float)(i2 - (i1 - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) - 10.0D * d9;
                    }

                    if ((double)i2 < 0.0D) {
                        double d10 = (0.0D - (double)i2) / 4.0D;
                        d10 = MathHelper.a(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) - 10.0D * d10;
                    }

                    adouble[j2] = d8;
                    ++j2;
                }
            }
        }

        return adouble;
    }

    public void addMobs(RegionLimitedWorldAccess var1) {
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        if (enumcreaturetype == EnumCreatureType.MONSTER) {
            if (WorldGenerator.p.c(this.a, blockposition)) {
                return WorldGenerator.p.d();
            }

            if (WorldGenerator.p.b(this.a, blockposition) && this.a.getType(blockposition.down()).getBlock() == Blocks.NETHER_BRICKS) {
                return WorldGenerator.p.d();
            }
        }

        BiomeBase biomebase = this.a.getBiome(blockposition);
        return biomebase.getMobs(enumcreaturetype);
    }

    public int a(World var1, boolean var2, boolean var3) {
        return 0;
    }

    public GeneratorSettingsNether f() {
        return this.o;
    }

    public int getSpawnHeight() {
        return this.a.getSeaLevel() + 1;
    }

    public int e() {
        return 128;
    }

    // $FF: synthetic method
    public GeneratorSettings getSettings() {
        return this.f();
    }
}
