package net.minecraft.server;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderGenerate extends ChunkGeneratorAbstract<GeneratorSettingsOverworld> {
    private static final Logger f = LogManager.getLogger();
    private final NoiseGeneratorOctaves g;
    private final NoiseGeneratorOctaves h;
    private final NoiseGeneratorOctaves i;
    private final NoiseGenerator3 j;
    private final GeneratorSettingsOverworld k;
    private final NoiseGeneratorOctaves l;
    private final NoiseGeneratorOctaves m;
    private final WorldType n;
    private final float[] o;
    private final MobSpawnerPhantom p = new MobSpawnerPhantom();
    private final IBlockData q;
    private final IBlockData r;

    public ChunkProviderGenerate(GeneratorAccess generatoraccess, WorldChunkManager worldchunkmanager, GeneratorSettingsOverworld generatorsettingsoverworld) {
        super(generatoraccess, worldchunkmanager);
        this.n = generatoraccess.getWorldData().getType();
        SeededRandom seededrandom = new SeededRandom(this.b);
        this.g = new NoiseGeneratorOctaves(seededrandom, 16);
        this.h = new NoiseGeneratorOctaves(seededrandom, 16);
        this.i = new NoiseGeneratorOctaves(seededrandom, 8);
        this.j = new NoiseGenerator3(seededrandom, 4);
        this.l = new NoiseGeneratorOctaves(seededrandom, 10);
        this.m = new NoiseGeneratorOctaves(seededrandom, 16);
        this.o = new float[25];

        for(int ix = -2; ix <= 2; ++ix) {
            for(int jx = -2; jx <= 2; ++jx) {
                float fx = 10.0F / MathHelper.c((float)(ix * ix + jx * jx) + 0.2F);
                this.o[ix + 2 + (jx + 2) * 5] = fx;
            }
        }

        this.k = generatorsettingsoverworld;
        this.q = this.k.r();
        this.r = this.k.s();
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
        ichunkaccess.a(HeightMap.Type.WORLD_SURFACE_WG, HeightMap.Type.OCEAN_FLOOR_WG);
        this.a(ichunkaccess, abiomebase, seededrandom, this.a.getSeaLevel());
        this.a(ichunkaccess, seededrandom);
        ichunkaccess.a(HeightMap.Type.WORLD_SURFACE_WG, HeightMap.Type.OCEAN_FLOOR_WG);
        ichunkaccess.a(ChunkStatus.BASE);
    }

    public void addMobs(RegionLimitedWorldAccess regionlimitedworldaccess) {
        int ix = regionlimitedworldaccess.a();
        int jx = regionlimitedworldaccess.b();
        BiomeBase biomebase = regionlimitedworldaccess.b(ix, jx).getBiomeIndex()[0];
        SeededRandom seededrandom = new SeededRandom();
        seededrandom.a(regionlimitedworldaccess.getSeed(), ix << 4, jx << 4);
        SpawnerCreature.a(regionlimitedworldaccess, biomebase, ix, jx, seededrandom);
    }

    public void a(int var1, int var2, IChunkAccess ichunkaccess) {
        BiomeBase[] abiomebase = this.c.getBiomes(ichunkaccess.getPos().x * 4 - 2, ichunkaccess.getPos().z * 4 - 2, 10, 10);
        double[] adouble = new double[825];
        this.a(abiomebase, ichunkaccess.getPos().x * 4, 0, ichunkaccess.getPos().z * 4, adouble);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int ix = 0; ix < 4; ++ix) {
            int jx = ix * 5;
            int kx = (ix + 1) * 5;

            for(int lx = 0; lx < 4; ++lx) {
                int i1 = (jx + lx) * 33;
                int j1 = (jx + lx + 1) * 33;
                int k1 = (kx + lx) * 33;
                int l1 = (kx + lx + 1) * 33;

                for(int i2 = 0; i2 < 32; ++i2) {
                    double d0 = 0.125D;
                    double d1 = adouble[i1 + i2];
                    double d2 = adouble[j1 + i2];
                    double d3 = adouble[k1 + i2];
                    double d4 = adouble[l1 + i2];
                    double d5 = (adouble[i1 + i2 + 1] - d1) * 0.125D;
                    double d6 = (adouble[j1 + i2 + 1] - d2) * 0.125D;
                    double d7 = (adouble[k1 + i2 + 1] - d3) * 0.125D;
                    double d8 = (adouble[l1 + i2 + 1] - d4) * 0.125D;

                    for(int j2 = 0; j2 < 8; ++j2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int k2 = 0; k2 < 4; ++k2) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * 0.25D;
                            double d15 = d10 - d16;

                            for(int l2 = 0; l2 < 4; ++l2) {
                                blockposition$mutableblockposition.c(ix * 4 + k2, i2 * 8 + j2, lx * 4 + l2);
                                if ((d15 += d16) > 0.0D) {
                                    ichunkaccess.a(blockposition$mutableblockposition, this.q, false);
                                } else if (i2 * 8 + j2 < this.k.w()) {
                                    ichunkaccess.a(blockposition$mutableblockposition, this.r, false);
                                }
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

    private void a(BiomeBase[] abiomebase, int ix, int jx, int kx, double[] adouble) {
        double[] adouble1 = this.m.a(ix, kx, 5, 5, this.k.x(), this.k.y(), this.k.z());
        float fx = this.k.A();
        float f1 = this.k.B();
        double[] adouble2 = this.i.a(ix, jx, kx, 5, 33, 5, (double)(fx / this.k.C()), (double)(f1 / this.k.D()), (double)(fx / this.k.E()));
        double[] adouble3 = this.g.a(ix, jx, kx, 5, 33, 5, (double)fx, (double)f1, (double)fx);
        double[] adouble4 = this.h.a(ix, jx, kx, 5, 33, 5, (double)fx, (double)f1, (double)fx);
        int lx = 0;
        int i1 = 0;

        for(int j1 = 0; j1 < 5; ++j1) {
            for(int k1 = 0; k1 < 5; ++k1) {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                boolean flag = true;
                BiomeBase biomebase = abiomebase[j1 + 2 + (k1 + 2) * 10];

                for(int l1 = -2; l1 <= 2; ++l1) {
                    for(int i2 = -2; i2 <= 2; ++i2) {
                        BiomeBase biomebase1 = abiomebase[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f5 = this.k.F() + biomebase1.h() * this.k.G();
                        float f6 = this.k.H() + biomebase1.l() * this.k.I();
                        if (this.n == WorldType.AMPLIFIED && f5 > 0.0F) {
                            f5 = 1.0F + f5 * 2.0F;
                            f6 = 1.0F + f6 * 4.0F;
                        }

                        float f7 = this.o[l1 + 2 + (i2 + 2) * 5] / (f5 + 2.0F);
                        if (biomebase1.h() > biomebase.h()) {
                            f7 /= 2.0F;
                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 = f2 / f4;
                f3 = f3 / f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d7 = adouble1[i1] / 8000.0D;
                if (d7 < 0.0D) {
                    d7 = -d7 * 0.3D;
                }

                d7 = d7 * 3.0D - 2.0D;
                if (d7 < 0.0D) {
                    d7 = d7 / 2.0D;
                    if (d7 < -1.0D) {
                        d7 = -1.0D;
                    }

                    d7 = d7 / 1.4D;
                    d7 = d7 / 2.0D;
                } else {
                    if (d7 > 1.0D) {
                        d7 = 1.0D;
                    }

                    d7 = d7 / 8.0D;
                }

                ++i1;
                double d8 = (double)f3;
                double d9 = (double)f2;
                d8 = d8 + d7 * 0.2D;
                d8 = d8 * this.k.J() / 8.0D;
                double d0 = this.k.J() + d8 * 4.0D;

                for(int j2 = 0; j2 < 33; ++j2) {
                    double d1 = ((double)j2 - d0) * this.k.K() * 128.0D / 256.0D / d9;
                    if (d1 < 0.0D) {
                        d1 *= 4.0D;
                    }

                    double d2 = adouble3[lx] / this.k.L();
                    double d3 = adouble4[lx] / this.k.M();
                    double d4 = (adouble2[lx] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.b(d2, d3, d4) - d1;
                    if (j2 > 29) {
                        double d6 = (double)((float)(j2 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) - 10.0D * d6;
                    }

                    adouble[lx] = d5;
                    ++lx;
                }
            }
        }

    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        BiomeBase biomebase = this.a.getBiome(blockposition);
        if (enumcreaturetype == EnumCreatureType.MONSTER && ((WorldGenFeatureSwampHut)WorldGenerator.l).d(this.a, blockposition)) {
            return WorldGenerator.l.d();
        } else {
            return enumcreaturetype == EnumCreatureType.MONSTER && WorldGenerator.n.b(this.a, blockposition) ? WorldGenerator.n.d() : biomebase.getMobs(enumcreaturetype);
        }
    }

    public int a(World world, boolean flag, boolean flag1) {
        int ix = 0;
        ix = ix + this.p.a(world, flag, flag1);
        return ix;
    }

    public GeneratorSettingsOverworld f() {
        return this.k;
    }

    public double[] a(int ix, int jx) {
        double d0 = 0.03125D;
        return this.j.a((double)(ix << 4), (double)(jx << 4), 16, 16, 0.0625D, 0.0625D, 1.0D);
    }

    public int getSpawnHeight() {
        return this.a.getSeaLevel() + 1;
    }

    // $FF: synthetic method
    public GeneratorSettings getSettings() {
        return this.f();
    }
}
