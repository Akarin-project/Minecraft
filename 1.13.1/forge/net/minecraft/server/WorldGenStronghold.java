package net.minecraft.server;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenStronghold extends StructureGenerator<WorldGenFeatureStrongholdConfiguration> {
    private boolean b;
    private ChunkCoordIntPair[] c;
    private long d;

    public WorldGenStronghold() {
    }

    protected boolean a(ChunkGenerator<?> chunkgenerator, Random var2, int i, int j) {
        if (this.d != chunkgenerator.getSeed()) {
            this.c();
        }

        if (!this.b) {
            this.a(chunkgenerator);
            this.b = true;
        }

        for(ChunkCoordIntPair chunkcoordintpair : this.c) {
            if (i == chunkcoordintpair.x && j == chunkcoordintpair.z) {
                return true;
            }
        }

        return false;
    }

    private void c() {
        this.b = false;
        this.c = null;
    }

    protected boolean a(GeneratorAccess generatoraccess) {
        return generatoraccess.getWorldData().shouldGenerateMapFeatures();
    }

    protected StructureStart a(GeneratorAccess generatoraccess, ChunkGenerator<?> chunkgenerator, SeededRandom seededrandom, int i, int j) {
        BiomeBase biomebase = chunkgenerator.getWorldChunkManager().getBiome(new BlockPosition((i << 4) + 9, 0, (j << 4) + 9), Biomes.b);
        int k = 0;

        WorldGenStronghold.a worldgenstronghold$a;
        for(worldgenstronghold$a = new WorldGenStronghold.a(generatoraccess, seededrandom, i, j, biomebase, k++); worldgenstronghold$a.d().isEmpty() || ((WorldGenStrongholdPieces.WorldGenStrongholdStart)worldgenstronghold$a.d().get(0)).b == null; worldgenstronghold$a = new WorldGenStronghold.a(generatoraccess, seededrandom, i, j, biomebase, k++)) {
            ;
        }

        return worldgenstronghold$a;
    }

    protected String a() {
        return "Stronghold";
    }

    public int b() {
        return 8;
    }

    @Nullable
    public BlockPosition getNearestGeneratedFeature(World world, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, BlockPosition blockposition, int var4, boolean var5) {
        if (!chunkgenerator.getWorldChunkManager().a(this)) {
            return null;
        } else {
            if (this.d != world.getSeed()) {
                this.c();
            }

            if (!this.b) {
                this.a(chunkgenerator);
                this.b = true;
            }

            BlockPosition blockposition1 = null;
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(0, 0, 0);
            double d0 = Double.MAX_VALUE;

            for(ChunkCoordIntPair chunkcoordintpair : this.c) {
                blockposition$mutableblockposition.c((chunkcoordintpair.x << 4) + 8, 32, (chunkcoordintpair.z << 4) + 8);
                double d1 = blockposition$mutableblockposition.n(blockposition);
                if (blockposition1 == null) {
                    blockposition1 = new BlockPosition(blockposition$mutableblockposition);
                    d0 = d1;
                } else if (d1 < d0) {
                    blockposition1 = new BlockPosition(blockposition$mutableblockposition);
                    d0 = d1;
                }
            }

            return blockposition1;
        }
    }

    private void a(ChunkGenerator<?> chunkgenerator) {
        this.d = chunkgenerator.getSeed();
        ArrayList arraylist = Lists.newArrayList();

        for(BiomeBase biomebase : IRegistry.BIOME) {
            if (biomebase != null && chunkgenerator.canSpawnStructure(biomebase, WorldGenerator.m)) {
                arraylist.add(biomebase);
            }
        }

        int i2 = chunkgenerator.getSettings().e();
        int j2 = chunkgenerator.getSettings().f();
        int i = chunkgenerator.getSettings().g();
        this.c = new ChunkCoordIntPair[j2];
        int j = 0;
        Long2ObjectMap long2objectmap = chunkgenerator.getStructureStartCache(this);
        synchronized(long2objectmap) {
            ObjectIterator objectiterator = long2objectmap.values().iterator();

            while(objectiterator.hasNext()) {
                StructureStart structurestart = (StructureStart)objectiterator.next();
                if (j < this.c.length) {
                    this.c[j++] = new ChunkCoordIntPair(structurestart.e(), structurestart.f());
                }
            }
        }

        Random random = new Random();
        random.setSeed(chunkgenerator.getSeed());
        double d1 = random.nextDouble() * Math.PI * 2.0D;
        int k = long2objectmap.size();
        if (k < this.c.length) {
            int l = 0;
            int i1 = 0;

            for(int j1 = 0; j1 < this.c.length; ++j1) {
                double d0 = (double)(4 * i2 + i2 * i1 * 6) + (random.nextDouble() - 0.5D) * (double)i2 * 2.5D;
                int k1 = (int)Math.round(Math.cos(d1) * d0);
                int l1 = (int)Math.round(Math.sin(d1) * d0);
                BlockPosition blockposition = chunkgenerator.getWorldChunkManager().a((k1 << 4) + 8, (l1 << 4) + 8, 112, arraylist, random);
                if (blockposition != null) {
                    k1 = blockposition.getX() >> 4;
                    l1 = blockposition.getZ() >> 4;
                }

                if (j1 >= k) {
                    this.c[j1] = new ChunkCoordIntPair(k1, l1);
                }

                d1 += (Math.PI * 2D) / (double)i;
                ++l;
                if (l == i) {
                    ++i1;
                    l = 0;
                    i = i + 2 * i / (i1 + 1);
                    i = Math.min(i, this.c.length - j1);
                    d1 += random.nextDouble() * Math.PI * 2.0D;
                }
            }
        }

    }

    public static class a extends StructureStart {
        public a() {
        }

        public a(GeneratorAccess generatoraccess, SeededRandom seededrandom, int i, int j, BiomeBase biomebase, int k) {
            super(i, j, biomebase, seededrandom, generatoraccess.getSeed() + (long)k);
            WorldGenStrongholdPieces.b();
            WorldGenStrongholdPieces.WorldGenStrongholdStart worldgenstrongholdpieces$worldgenstrongholdstart = new WorldGenStrongholdPieces.WorldGenStrongholdStart(0, seededrandom, (i << 4) + 2, (j << 4) + 2);
            this.a.add(worldgenstrongholdpieces$worldgenstrongholdstart);
            worldgenstrongholdpieces$worldgenstrongholdstart.a(worldgenstrongholdpieces$worldgenstrongholdstart, this.a, seededrandom);
            List list = worldgenstrongholdpieces$worldgenstrongholdstart.c;

            while(!list.isEmpty()) {
                int l = seededrandom.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece)list.remove(l);
                structurepiece.a(worldgenstrongholdpieces$worldgenstrongholdstart, this.a, seededrandom);
            }

            this.a(generatoraccess);
            this.a(generatoraccess, seededrandom, 10);
        }
    }
}
