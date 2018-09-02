package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderFlat extends ChunkGeneratorAbstract<GeneratorSettingsFlat> {
    private static final Logger f = LogManager.getLogger();
    private final GeneratorSettingsFlat g;
    private final BiomeBase h;
    private final MobSpawnerPhantom i = new MobSpawnerPhantom();

    public ChunkProviderFlat(GeneratorAccess generatoraccess, WorldChunkManager worldchunkmanager, GeneratorSettingsFlat generatorsettingsflat) {
        super(generatoraccess, worldchunkmanager);
        this.g = generatorsettingsflat;
        this.h = this.g();
    }

    private BiomeBase g() {
        BiomeBase biomebase = this.g.t();
        ChunkProviderFlat.a chunkproviderflat$a = new ChunkProviderFlat.a(biomebase.q(), biomebase.c(), biomebase.p(), biomebase.h(), biomebase.l(), biomebase.getTemperature(), biomebase.getHumidity(), biomebase.n(), biomebase.o(), biomebase.s());
        Map map = this.g.u();

        for(String s : map.keySet()) {
            WorldGenFeatureComposite[] aworldgenfeaturecomposite = (WorldGenFeatureComposite[])GeneratorSettingsFlat.u.get(s);
            if (aworldgenfeaturecomposite != null) {
                for(WorldGenFeatureComposite worldgenfeaturecomposite : aworldgenfeaturecomposite) {
                    chunkproviderflat$a.a((WorldGenStage.Decoration)GeneratorSettingsFlat.t.get(worldgenfeaturecomposite), worldgenfeaturecomposite);
                    WorldGenerator worldgenerator = worldgenfeaturecomposite.a();
                    if (worldgenerator instanceof StructureGenerator) {
                        WorldGenFeatureConfiguration worldgenfeatureconfiguration = biomebase.b((StructureGenerator)worldgenerator);
                        chunkproviderflat$a.a((StructureGenerator)worldgenerator, worldgenfeatureconfiguration != null ? worldgenfeatureconfiguration : (WorldGenFeatureConfiguration)GeneratorSettingsFlat.v.get(worldgenfeaturecomposite));
                    }
                }
            }
        }

        boolean flag = (!this.g.y() || biomebase == Biomes.aa) && map.containsKey("decoration");
        if (flag) {
            ArrayList arraylist = Lists.newArrayList();
            arraylist.add(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES);
            arraylist.add(WorldGenStage.Decoration.SURFACE_STRUCTURES);

            for(WorldGenStage.Decoration worldgenstage$decoration : WorldGenStage.Decoration.values()) {
                if (!arraylist.contains(worldgenstage$decoration)) {
                    for(WorldGenFeatureComposite worldgenfeaturecomposite1 : biomebase.a(worldgenstage$decoration)) {
                        chunkproviderflat$a.a(worldgenstage$decoration, worldgenfeaturecomposite1);
                    }
                }
            }
        }

        return chunkproviderflat$a;
    }

    public void createChunk(IChunkAccess ichunkaccess) {
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        int ix = chunkcoordintpair.x;
        int j = chunkcoordintpair.z;
        BiomeBase[] abiomebase = this.c.getBiomeBlock(ix * 16, j * 16, 16, 16);
        ichunkaccess.a(abiomebase);
        this.a(ix, j, ichunkaccess);
        ichunkaccess.a(HeightMap.Type.WORLD_SURFACE_WG, HeightMap.Type.OCEAN_FLOOR_WG);
        ichunkaccess.a(ChunkStatus.BASE);
    }

    public void addFeatures(RegionLimitedWorldAccess regionlimitedworldaccess, WorldGenStage.Features var2) {
        boolean flag = true;
        int ix = regionlimitedworldaccess.a();
        int j = regionlimitedworldaccess.b();
        BitSet bitset = new BitSet(65536);
        SeededRandom seededrandom = new SeededRandom();

        for(int k = ix - 8; k <= ix + 8; ++k) {
            for(int l = j - 8; l <= j + 8; ++l) {
                List list = this.h.a(WorldGenStage.Features.AIR);
                ListIterator listiterator = list.listIterator();

                while(listiterator.hasNext()) {
                    int i1 = listiterator.nextIndex();
                    WorldGenCarverWrapper worldgencarverwrapper = (WorldGenCarverWrapper)listiterator.next();
                    seededrandom.c(regionlimitedworldaccess.getMinecraftWorld().getSeed() + (long)i1, k, l);
                    if (worldgencarverwrapper.a(regionlimitedworldaccess, seededrandom, k, l, WorldGenFeatureConfiguration.e)) {
                        worldgencarverwrapper.a(regionlimitedworldaccess, seededrandom, k, l, ix, j, bitset, WorldGenFeatureConfiguration.e);
                    }
                }
            }
        }

    }

    public GeneratorSettingsFlat f() {
        return this.g;
    }

    public double[] a(int var1, int var2) {
        return new double[0];
    }

    public int getSpawnHeight() {
        IChunkAccess ichunkaccess = this.a.b(0, 0);
        return ichunkaccess.a(HeightMap.Type.MOTION_BLOCKING, 8, 8);
    }

    public void addDecorations(RegionLimitedWorldAccess regionlimitedworldaccess) {
        int ix = regionlimitedworldaccess.a();
        int j = regionlimitedworldaccess.b();
        int k = ix * 16;
        int l = j * 16;
        BlockPosition blockposition = new BlockPosition(k, 0, l);
        SeededRandom seededrandom = new SeededRandom();
        long i1 = seededrandom.a(regionlimitedworldaccess.getSeed(), k, l);

        for(WorldGenStage.Decoration worldgenstage$decoration : WorldGenStage.Decoration.values()) {
            this.h.a(worldgenstage$decoration, this, regionlimitedworldaccess, i1, seededrandom, blockposition);
        }

    }

    public void addMobs(RegionLimitedWorldAccess var1) {
    }

    public void a(int var1, int var2, IChunkAccess ichunkaccess) {
        IBlockData[] aiblockdata = this.g.A();
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int ix = 0; ix < aiblockdata.length; ++ix) {
            IBlockData iblockdata = aiblockdata[ix];
            if (iblockdata != null) {
                for(int j = 0; j < 16; ++j) {
                    for(int k = 0; k < 16; ++k) {
                        ichunkaccess.a(blockposition$mutableblockposition.c(j, ix, k), iblockdata, false);
                    }
                }
            }
        }

    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        BiomeBase biomebase = this.a.getBiome(blockposition);
        return biomebase.getMobs(enumcreaturetype);
    }

    public int a(World world, boolean flag, boolean flag1) {
        int ix = 0;
        ix = ix + this.i.a(world, flag, flag1);
        return ix;
    }

    public boolean canSpawnStructure(BiomeBase var1, StructureGenerator<? extends WorldGenFeatureConfiguration> structuregenerator) {
        return this.h.a(structuregenerator);
    }

    @Nullable
    public WorldGenFeatureConfiguration getFeatureConfiguration(BiomeBase var1, StructureGenerator<? extends WorldGenFeatureConfiguration> structuregenerator) {
        return this.h.b(structuregenerator);
    }

    @Nullable
    public BlockPosition findNearestMapFeature(World world, String s, BlockPosition blockposition, int ix, boolean flag) {
        return !this.g.u().keySet().contains(s) ? null : super.findNearestMapFeature(world, s, blockposition, ix, flag);
    }

    // $FF: synthetic method
    public GeneratorSettings getSettings() {
        return this.f();
    }

    class a extends BiomeBase {
        protected a(WorldGenSurfaceComposite worldgensurfacecomposite, BiomeBase.Precipitation biomebase$precipitation, BiomeBase.Geography biomebase$geography, float f, float f1, float f2, float f3, int i, int j, String s) {
            super((new BiomeBase.a()).a(worldgensurfacecomposite).a(biomebase$precipitation).a(biomebase$geography).a(f).b(f1).c(f2).d(f3).a(i).b(j).a(s));
        }
    }
}
