package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import javax.annotation.Nullable;

public interface ChunkGenerator<C extends GeneratorSettings> {
    void createChunk(IChunkAccess var1);

    void addFeatures(RegionLimitedWorldAccess var1, WorldGenStage.Features var2);

    void addDecorations(RegionLimitedWorldAccess var1);

    void addMobs(RegionLimitedWorldAccess var1);

    List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType var1, BlockPosition var2);

    @Nullable
    BlockPosition findNearestMapFeature(World var1, String var2, BlockPosition var3, int var4, boolean var5);

    C getSettings();

    int a(World var1, boolean var2, boolean var3);

    boolean canSpawnStructure(BiomeBase var1, StructureGenerator<? extends WorldGenFeatureConfiguration> var2);

    @Nullable
    WorldGenFeatureConfiguration getFeatureConfiguration(BiomeBase var1, StructureGenerator<? extends WorldGenFeatureConfiguration> var2);

    Long2ObjectMap<StructureStart> getStructureStartCache(StructureGenerator<? extends WorldGenFeatureConfiguration> var1);

    Long2ObjectMap<LongSet> getStructureCache(StructureGenerator<? extends WorldGenFeatureConfiguration> var1);

    WorldChunkManager getWorldChunkManager();

    long getSeed();

    int getSpawnHeight();

    int e();
}
