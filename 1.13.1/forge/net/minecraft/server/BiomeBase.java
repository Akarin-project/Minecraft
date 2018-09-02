package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeBase {
    public static final Logger a = LogManager.getLogger();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> b = new WorldGenCaves();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> c = new WorldGenCavesHell();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> d = new WorldGenCanyon();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> e = new WorldGenCanyonOcean();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> f = new WorldGenCavesOcean();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> g = new WorldGenDecoratorHeight();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> h = new WorldGenDecoratorSkyVisible();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> i = new WorldGenDecoratorHeight32();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> j = new WorldGenDecoratorHeightDouble();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> k = new WorldGenDecoratorHeight64();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorNoiseConfiguration> l = new WorldGenDecoratorNoiseHeight32();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorNoiseConfiguration> m = new WorldGenDecoratorNoiseHeightDouble();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> n = new WorldGenDecoratorEmpty();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> o = new WorldGenDecoratorChance();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> p = new WorldGenDecoratorChanceHeight();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> q = new WorldGenDecoratorChancePass();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> r = new WorldGenDecoratorSkyVisibleBiased();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyExtraChanceConfiguration> s = new WorldGenDecoratorHeightExtraChance();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> t = new WorldGenDecoratorNetherHeight();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> u = new WorldGenDecoratorHeightBiased();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> v = new WorldGenDecoratorHeightBiased2();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> w = new WorldGenDecoratorNetherRandomCount();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> x = new WorldGenDecoratorNetherChance();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyChanceConfiguration> y = new WorldGenFeatureChanceDecorator();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyChanceConfiguration> z = new WorldGenFeatureChanceDecoratorHeight();
    public static final WorldGenDecorator<WorldGenDecoratorHeightAverageConfiguration> A = new WorldGenDecoratorHeightAverage();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> B = new WorldGenDecoratorSolidTop();
    public static final WorldGenDecorator<WorldGenDecoratorRangeConfiguration> C = new WorldGenDecoratorSolidTopHeight();
    public static final WorldGenDecorator<WorldGenDecoratorNoiseConfiguration> D = new WorldGenDecoratorSolidTopNoise();
    public static final WorldGenDecorator<WorldGenDecoratorCarveMaskConfiguration> E = new WorldGenDecoratorCarveMask();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> F = new WorldGenDecoratorForestRock();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> G = new WorldGenDecoratorNetherFire();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> H = new WorldGenDecoratorNetherMagma();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> I = new WorldGenDecoratorEmerald();
    public static final WorldGenDecorator<WorldGenDecoratorLakeChanceConfiguration> J = new WorldGenDecoratorLakeLava();
    public static final WorldGenDecorator<WorldGenDecoratorLakeChanceConfiguration> K = new WorldGenDecoratorLakeWater();
    public static final WorldGenDecorator<WorldGenDecoratorDungeonConfiguration> L = new WorldGenDecoratorDungeon();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> M = new WorldGenDecoratorRoofedTree();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> N = new WorldGenDecoratorIceburg();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> O = new WorldGenDecoratorNetherGlowstone();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> P = new WorldGenDecoratorSpike();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> Q = new WorldGenDecoratorEndIsland();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> R = new WorldGenDecoratorChorusPlant();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> S = new WorldGenDecoratorEndGateway();
    protected static final IBlockData T = Blocks.AIR.getBlockData();
    protected static final IBlockData U = Blocks.DIRT.getBlockData();
    protected static final IBlockData V = Blocks.GRASS_BLOCK.getBlockData();
    protected static final IBlockData W = Blocks.PODZOL.getBlockData();
    protected static final IBlockData X = Blocks.GRAVEL.getBlockData();
    protected static final IBlockData Y = Blocks.STONE.getBlockData();
    protected static final IBlockData Z = Blocks.COARSE_DIRT.getBlockData();
    protected static final IBlockData aa = Blocks.SAND.getBlockData();
    protected static final IBlockData ab = Blocks.RED_SAND.getBlockData();
    protected static final IBlockData ac = Blocks.WHITE_TERRACOTTA.getBlockData();
    protected static final IBlockData ad = Blocks.MYCELIUM.getBlockData();
    protected static final IBlockData ae = Blocks.NETHERRACK.getBlockData();
    protected static final IBlockData af = Blocks.END_STONE.getBlockData();
    public static final WorldGenSurfaceConfigurationBase ag = new WorldGenSurfaceConfigurationBase(T, T, T);
    public static final WorldGenSurfaceConfigurationBase ah = new WorldGenSurfaceConfigurationBase(U, U, X);
    public static final WorldGenSurfaceConfigurationBase ai = new WorldGenSurfaceConfigurationBase(V, U, X);
    public static final WorldGenSurfaceConfigurationBase aj = new WorldGenSurfaceConfigurationBase(Y, Y, X);
    public static final WorldGenSurfaceConfigurationBase ak = new WorldGenSurfaceConfigurationBase(X, X, X);
    public static final WorldGenSurfaceConfigurationBase al = new WorldGenSurfaceConfigurationBase(Z, U, X);
    public static final WorldGenSurfaceConfigurationBase am = new WorldGenSurfaceConfigurationBase(W, U, X);
    public static final WorldGenSurfaceConfigurationBase an = new WorldGenSurfaceConfigurationBase(aa, aa, aa);
    public static final WorldGenSurfaceConfigurationBase ao = new WorldGenSurfaceConfigurationBase(V, U, aa);
    public static final WorldGenSurfaceConfigurationBase ap = new WorldGenSurfaceConfigurationBase(aa, aa, X);
    public static final WorldGenSurfaceConfigurationBase aq = new WorldGenSurfaceConfigurationBase(ab, ac, X);
    public static final WorldGenSurfaceConfigurationBase ar = new WorldGenSurfaceConfigurationBase(ad, U, X);
    public static final WorldGenSurfaceConfigurationBase as = new WorldGenSurfaceConfigurationBase(ae, ae, ae);
    public static final WorldGenSurfaceConfigurationBase at = new WorldGenSurfaceConfigurationBase(af, af, af);
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> au = new WorldGenSurfaceDefaultBlock();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> av = new WorldGenSurfaceExtremeHills();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aw = new WorldGenSurfaceSavannaMutated();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> ax = new WorldGenSurfaceExtremeHillMutated();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> ay = new WorldGenSurfaceTaigaMega();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> az = new WorldGenSurfaceSwamp();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aA = new WorldGenSurfaceMesa();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aB = new WorldGenSurfaceMesaForest();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aC = new WorldGenSurfaceMesaBryce();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aD = new WorldGenSurfaceFrozenOcean();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aE = new WorldGenSurfaceNether();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aF = new WorldGenSurfaceEmpty();
    public static final Set<BiomeBase> aG = Sets.newHashSet();
    public static final RegistryBlockID<BiomeBase> aH = new RegistryBlockID<BiomeBase>();
    protected static final NoiseGenerator3 aI = new NoiseGenerator3(new Random(1234L), 1);
    public static final NoiseGenerator3 aJ = new NoiseGenerator3(new Random(2345L), 1);
    @Nullable
    protected String aK;
    protected final float aL;
    protected final float aM;
    protected final float aN;
    protected final float aO;
    protected final int aP;
    protected final int aQ;
    @Nullable
    protected final String aR;
    protected final WorldGenSurfaceComposite<?> aS;
    protected final BiomeBase.Geography aT;
    protected final BiomeBase.Precipitation aU;
    protected final Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>> aV = Maps.newHashMap();
    protected final Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite<?, ?>>> aW = Maps.newHashMap();
    protected final List<WorldGenFeatureCompositeFlower<?>> aX = Lists.newArrayList();
    protected final Map<StructureGenerator<?>, WorldGenFeatureConfiguration> aY = Maps.newHashMap();
    private final Map<EnumCreatureType, List<BiomeBase.BiomeMeta>> aZ = Maps.newHashMap();

    @Nullable
    public static BiomeBase a(BiomeBase biomebase) {
        return aH.fromId(IRegistry.BIOME.a(biomebase));
    }

    public static <C extends WorldGenFeatureConfiguration> WorldGenCarverWrapper<C> a(WorldGenCarver<C> worldgencarver, C worldgenfeatureconfiguration) {
        return new WorldGenCarverWrapper<C>(worldgencarver, worldgenfeatureconfiguration);
    }

    public static <F extends WorldGenFeatureConfiguration, D extends WorldGenFeatureDecoratorConfiguration> WorldGenFeatureComposite<F, D> a(WorldGenerator<F> worldgenerator, F worldgenfeatureconfiguration, WorldGenDecorator<D> worldgendecorator, D worldgenfeaturedecoratorconfiguration) {
        return new WorldGenFeatureComposite<F, D>(worldgenerator, worldgenfeatureconfiguration, worldgendecorator, worldgenfeaturedecoratorconfiguration);
    }

    public static <D extends WorldGenFeatureDecoratorConfiguration> WorldGenFeatureCompositeFlower<D> a(WorldGenFlowers worldgenflowers, WorldGenDecorator<D> worldgendecorator, D worldgenfeaturedecoratorconfiguration) {
        return new WorldGenFeatureCompositeFlower<D>(worldgenflowers, worldgendecorator, worldgenfeaturedecoratorconfiguration);
    }

    protected BiomeBase(BiomeBase.a biomebase$a) {
        if (biomebase$a.a != null && biomebase$a.b != null && biomebase$a.c != null && biomebase$a.d != null && biomebase$a.e != null && biomebase$a.f != null && biomebase$a.g != null && biomebase$a.h != null && biomebase$a.i != null) {
            this.aS = biomebase$a.a;
            this.aU = biomebase$a.b;
            this.aT = biomebase$a.c;
            this.aL = biomebase$a.d;
            this.aM = biomebase$a.e;
            this.aN = biomebase$a.f;
            this.aO = biomebase$a.g;
            this.aP = biomebase$a.h;
            this.aQ = biomebase$a.i;
            this.aR = biomebase$a.j;

            for(WorldGenStage.Decoration worldgenstage$decoration : WorldGenStage.Decoration.values()) {
                this.aW.put(worldgenstage$decoration, Lists.newArrayList());
            }

            for(EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
                this.aZ.put(enumcreaturetype, Lists.newArrayList());
            }

        } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome for " + this.getClass().getSimpleName() + "\n" + biomebase$a);
        }
    }

    protected void a() {
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.f, new WorldGenMineshaftConfiguration((double)0.004F, WorldGenMineshaft.Type.NORMAL), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.e, new WorldGenFeatureVillageConfiguration(0, WorldGenVillagePieces.Material.OAK), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.m, new WorldGenFeatureStrongholdConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.l, new WorldGenFeatureSwampHutConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.i, new WorldGenFeatureDesertPyramidConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.h, new WorldGenFeatureJunglePyramidConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.j, new WorldGenFeatureIglooConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.k, new WorldGenFeatureShipwreckConfiguration(false), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.n, new WorldGenMonumentConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.g, new WorldGenMansionConfiguration(), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.o, new WorldGenFeatureOceanRuinConfiguration(WorldGenFeatureOceanRuin.Temperature.COLD, 0.3F, 0.9F), n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.r, new WorldGenBuriedTreasureConfiguration(0.01F), n, WorldGenFeatureDecoratorConfiguration.e));
    }

    public boolean b() {
        return this.aR != null;
    }

    protected void a(EnumCreatureType enumcreaturetype, BiomeBase.BiomeMeta biomebase$biomemeta) {
        ((List)this.aZ.get(enumcreaturetype)).add(biomebase$biomemeta);
    }

    public List<BiomeBase.BiomeMeta> getMobs(EnumCreatureType enumcreaturetype) {
        return (List)this.aZ.get(enumcreaturetype);
    }

    public BiomeBase.Precipitation c() {
        return this.aU;
    }

    public boolean d() {
        return this.getHumidity() > 0.85F;
    }

    public float e() {
        return 0.1F;
    }

    public float c(BlockPosition blockposition) {
        if (blockposition.getY() > 64) {
            float fx = (float)(aI.a((double)((float)blockposition.getX() / 8.0F), (double)((float)blockposition.getZ() / 8.0F)) * 4.0D);
            return this.getTemperature() - (fx + (float)blockposition.getY() - 64.0F) * 0.05F / 30.0F;
        } else {
            return this.getTemperature();
        }
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        return this.a(iworldreader, blockposition, true);
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition, boolean flag) {
        if (this.c(blockposition) >= 0.15F) {
            return false;
        } else {
            if (blockposition.getY() >= 0 && blockposition.getY() < 256 && iworldreader.getBrightness(EnumSkyBlock.BLOCK, blockposition) < 10) {
                IBlockData iblockdata = iworldreader.getType(blockposition);
                Fluid fluid = iworldreader.b(blockposition);
                if (fluid.c() == FluidTypes.c && iblockdata.getBlock() instanceof BlockFluids) {
                    if (!flag) {
                        return true;
                    }

                    boolean flag1 = iworldreader.B(blockposition.west()) && iworldreader.B(blockposition.east()) && iworldreader.B(blockposition.north()) && iworldreader.B(blockposition.south());
                    if (!flag1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean b(IWorldReader iworldreader, BlockPosition blockposition) {
        if (this.c(blockposition) >= 0.15F) {
            return false;
        } else {
            if (blockposition.getY() >= 0 && blockposition.getY() < 256 && iworldreader.getBrightness(EnumSkyBlock.BLOCK, blockposition) < 10) {
                IBlockData iblockdata = iworldreader.getType(blockposition);
                if (iblockdata.isAir() && Blocks.SNOW.getBlockData().canPlace(iworldreader, blockposition)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void a(WorldGenStage.Decoration worldgenstage$decoration, WorldGenFeatureComposite<?, ?> worldgenfeaturecomposite) {
        if (worldgenfeaturecomposite instanceof WorldGenFeatureCompositeFlower) {
            this.aX.add((WorldGenFeatureCompositeFlower)worldgenfeaturecomposite);
        }

        ((List)this.aW.get(worldgenstage$decoration)).add(worldgenfeaturecomposite);
    }

    public <C extends WorldGenFeatureConfiguration> void a(WorldGenStage.Features worldgenstage$features, WorldGenCarverWrapper<C> worldgencarverwrapper) {
        ((List)this.aV.computeIfAbsent(worldgenstage$features, (var0) -> {
            return Lists.newArrayList();
        })).add(worldgencarverwrapper);
    }

    public List<WorldGenCarverWrapper<?>> a(WorldGenStage.Features worldgenstage$features) {
        return (List)this.aV.computeIfAbsent(worldgenstage$features, (var0) -> {
            return Lists.newArrayList();
        });
    }

    public <C extends WorldGenFeatureConfiguration> void a(StructureGenerator<C> structuregenerator, C worldgenfeatureconfiguration) {
        this.aY.put(structuregenerator, worldgenfeatureconfiguration);
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(StructureGenerator<C> structuregenerator) {
        return this.aY.containsKey(structuregenerator);
    }

    @Nullable
    public <C extends WorldGenFeatureConfiguration> WorldGenFeatureConfiguration b(StructureGenerator<C> structuregenerator) {
        return (WorldGenFeatureConfiguration)this.aY.get(structuregenerator);
    }

    public List<WorldGenFeatureCompositeFlower<?>> f() {
        return this.aX;
    }

    public List<WorldGenFeatureComposite<?, ?>> a(WorldGenStage.Decoration worldgenstage$decoration) {
        return (List)this.aW.get(worldgenstage$decoration);
    }

    public void a(WorldGenStage.Decoration worldgenstage$decoration, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, GeneratorAccess generatoraccess, long ix, SeededRandom seededrandom, BlockPosition blockposition) {
        int jx = 0;

        for(WorldGenFeatureComposite worldgenfeaturecomposite : (List)this.aW.get(worldgenstage$decoration)) {
            seededrandom.b(ix, jx, worldgenstage$decoration.ordinal());
            worldgenfeaturecomposite.a(generatoraccess, chunkgenerator, seededrandom, blockposition, WorldGenFeatureConfiguration.e);
            ++jx;
        }

    }

    public void a(Random random, IChunkAccess ichunkaccess, int ix, int jx, int kx, double d0, IBlockData iblockdata, IBlockData iblockdata1, int lx, long i1) {
        this.aS.a(i1);
        this.aS.a(random, ichunkaccess, this, ix, jx, kx, d0, iblockdata, iblockdata1, lx, i1, ag);
    }

    public BiomeBase.EnumTemperature g() {
        if (this.aT == BiomeBase.Geography.OCEAN) {
            return BiomeBase.EnumTemperature.OCEAN;
        } else if ((double)this.getTemperature() < 0.2D) {
            return BiomeBase.EnumTemperature.COLD;
        } else {
            return (double)this.getTemperature() < 1.0D ? BiomeBase.EnumTemperature.MEDIUM : BiomeBase.EnumTemperature.WARM;
        }
    }

    public static BiomeBase getBiome(int ix, BiomeBase biomebase) {
        BiomeBase biomebase1 = IRegistry.BIOME.fromId(ix);
        return biomebase1 == null ? biomebase : biomebase1;
    }

    public final float h() {
        return this.aL;
    }

    public final float getHumidity() {
        return this.aO;
    }

    public String k() {
        if (this.aK == null) {
            this.aK = SystemUtils.a("biome", IRegistry.BIOME.getKey(this));
        }

        return this.aK;
    }

    public final float l() {
        return this.aM;
    }

    public final float getTemperature() {
        return this.aN;
    }

    public final int n() {
        return this.aP;
    }

    public final int o() {
        return this.aQ;
    }

    public final BiomeBase.Geography p() {
        return this.aT;
    }

    public WorldGenSurfaceComposite<?> q() {
        return this.aS;
    }

    public WorldGenSurfaceConfiguration r() {
        return this.aS.a();
    }

    @Nullable
    public String s() {
        return this.aR;
    }

    public static void t() {
        a(0, "ocean", new BiomeOcean());
        a(1, "plains", new BiomePlains());
        a(2, "desert", new BiomeDesert());
        a(3, "mountains", new BiomeBigHills());
        a(4, "forest", new BiomeForest());
        a(5, "taiga", new BiomeTaiga());
        a(6, "swamp", new BiomeSwamp());
        a(7, "river", new BiomeRiver());
        a(8, "nether", new BiomeHell());
        a(9, "the_end", new BiomeTheEnd());
        a(10, "frozen_ocean", new BiomeFrozenOcean());
        a(11, "frozen_river", new BiomeFrozenRiver());
        a(12, "snowy_tundra", new BiomeIcePlains());
        a(13, "snowy_mountains", new BiomeIceMountains());
        a(14, "mushroom_fields", new BiomeMushrooms());
        a(15, "mushroom_field_shore", new BiomeMushroomIslandShore());
        a(16, "beach", new BiomeBeach());
        a(17, "desert_hills", new BiomeDesertHills());
        a(18, "wooded_hills", new BiomeForestHills());
        a(19, "taiga_hills", new BiomeTaigaHills());
        a(20, "mountain_edge", new BiomeExtremeHillsEdge());
        a(21, "jungle", new BiomeJungle());
        a(22, "jungle_hills", new BiomeJungleHills());
        a(23, "jungle_edge", new BiomeJungleEdge());
        a(24, "deep_ocean", new BiomeDeepOcean());
        a(25, "stone_shore", new BiomeStoneBeach());
        a(26, "snowy_beach", new BiomeColdBeach());
        a(27, "birch_forest", new BiomeBirchForest());
        a(28, "birch_forest_hills", new BiomeBirchForestHills());
        a(29, "dark_forest", new BiomeRoofedForest());
        a(30, "snowy_taiga", new BiomeColdTaiga());
        a(31, "snowy_taiga_hills", new BiomeColdTaigaHills());
        a(32, "giant_tree_taiga", new BiomeMegaTaiga());
        a(33, "giant_tree_taiga_hills", new BiomeMegaTaigaHills());
        a(34, "wooded_mountains", new BiomeExtremeHillsWithTrees());
        a(35, "savanna", new BiomeSavanna());
        a(36, "savanna_plateau", new BiomeSavannaPlateau());
        a(37, "badlands", new BiomeMesa());
        a(38, "wooded_badlands_plateau", new BiomeMesaPlataeu());
        a(39, "badlands_plateau", new BiomeMesaPlataeuClear());
        a(40, "small_end_islands", new BiomeTheEndFloatingIslands());
        a(41, "end_midlands", new BiomeTheEndMediumIsland());
        a(42, "end_highlands", new BiomeTheEndHighIsland());
        a(43, "end_barrens", new BiomeTheEndBarrenIsland());
        a(44, "warm_ocean", new BiomeWarmOcean());
        a(45, "lukewarm_ocean", new BiomeLukewarmOcean());
        a(46, "cold_ocean", new BiomeColdOcean());
        a(47, "deep_warm_ocean", new BiomeWarmDeepOcean());
        a(48, "deep_lukewarm_ocean", new BiomeLukewarmDeepOcean());
        a(49, "deep_cold_ocean", new BiomeColdDeepOcean());
        a(50, "deep_frozen_ocean", new BiomeFrozenDeepOcean());
        a(127, "the_void", new BiomeVoid());
        a(129, "sunflower_plains", new BiomeSunflowerPlains());
        a(130, "desert_lakes", new BiomeDesertMutated());
        a(131, "gravelly_mountains", new BiomeExtremeHillsMutated());
        a(132, "flower_forest", new BiomeFlowerForest());
        a(133, "taiga_mountains", new BiomeTaigaMutated());
        a(134, "swamp_hills", new BiomeSwamplandMutated());
        a(140, "ice_spikes", new BiomeIcePlainsSpikes());
        a(149, "modified_jungle", new BiomeJungleMutated());
        a(151, "modified_jungle_edge", new BiomeJungleEdgeMutated());
        a(155, "tall_birch_forest", new BiomeBirchForestMutated());
        a(156, "tall_birch_hills", new BiomeBirchForestHillsMutated());
        a(157, "dark_forest_hills", new BiomeRoofedForestMutated());
        a(158, "snowy_taiga_mountains", new BiomeColdTaigaMutated());
        a(160, "giant_spruce_taiga", new BiomeMegaSpruceTaiga());
        a(161, "giant_spruce_taiga_hills", new BiomeRedwoodTaigaHillsMutated());
        a(162, "modified_gravelly_mountains", new BiomeExtremeHillsWithTreesMutated());
        a(163, "shattered_savanna", new BiomeSavannaMutated());
        a(164, "shattered_savanna_plateau", new BiomeSavannaPlateauMutated());
        a(165, "eroded_badlands", new BiomeMesaBryce());
        a(166, "modified_wooded_badlands_plateau", new BiomeMesaPlateauMutated());
        a(167, "modified_badlands_plateau", new BiomeMesaPlateauClearMutated());
        Collections.addAll(aG, new BiomeBase[]{Biomes.a, Biomes.c, Biomes.d, Biomes.e, Biomes.f, Biomes.g, Biomes.h, Biomes.i, Biomes.m, Biomes.n, Biomes.o, Biomes.p, Biomes.q, Biomes.r, Biomes.s, Biomes.t, Biomes.u, Biomes.w, Biomes.x, Biomes.y, Biomes.z, Biomes.A, Biomes.B, Biomes.C, Biomes.D, Biomes.E, Biomes.F, Biomes.G, Biomes.H, Biomes.I, Biomes.J, Biomes.K, Biomes.L, Biomes.M, Biomes.N, Biomes.O});
    }

    private static void a(int ix, String sx, BiomeBase biomebase) {
        IRegistry.BIOME.a(ix, new MinecraftKey(sx), biomebase);
        if (biomebase.b()) {
            aH.a(biomebase, IRegistry.BIOME.a(IRegistry.BIOME.get(new MinecraftKey(biomebase.aR))));
        }

    }

    public static class BiomeMeta extends WeightedRandom.WeightedRandomChoice {
        public EntityTypes<? extends EntityInsentient> b;
        public int c;
        public int d;

        public BiomeMeta(EntityTypes<? extends EntityInsentient> entitytypes, int i, int j, int k) {
            super(i);
            this.b = entitytypes;
            this.c = j;
            this.d = k;
        }

        public String toString() {
            return EntityTypes.getName(this.b) + "*(" + this.c + "-" + this.d + "):" + this.a;
        }
    }

    public static enum EnumTemperature {
        OCEAN,
        COLD,
        MEDIUM,
        WARM;

        private EnumTemperature() {
        }
    }

    public static enum Geography {
        NONE,
        TAIGA,
        EXTREME_HILLS,
        JUNGLE,
        MESA,
        PLAINS,
        SAVANNA,
        ICY,
        THEEND,
        BEACH,
        FOREST,
        OCEAN,
        DESERT,
        RIVER,
        SWAMP,
        MUSHROOM,
        NETHER;

        private Geography() {
        }
    }

    public static enum Precipitation {
        NONE,
        RAIN,
        SNOW;

        private Precipitation() {
        }
    }

    public static class a {
        @Nullable
        private WorldGenSurfaceComposite<?> a;
        @Nullable
        private BiomeBase.Precipitation b;
        @Nullable
        private BiomeBase.Geography c;
        @Nullable
        private Float d;
        @Nullable
        private Float e;
        @Nullable
        private Float f;
        @Nullable
        private Float g;
        @Nullable
        private Integer h;
        @Nullable
        private Integer i;
        @Nullable
        private String j;

        public a() {
        }

        public BiomeBase.a a(WorldGenSurfaceComposite<?> worldgensurfacecomposite) {
            this.a = worldgensurfacecomposite;
            return this;
        }

        public BiomeBase.a a(BiomeBase.Precipitation biomebase$precipitation) {
            this.b = biomebase$precipitation;
            return this;
        }

        public BiomeBase.a a(BiomeBase.Geography biomebase$geography) {
            this.c = biomebase$geography;
            return this;
        }

        public BiomeBase.a a(float fx) {
            this.d = fx;
            return this;
        }

        public BiomeBase.a b(float fx) {
            this.e = fx;
            return this;
        }

        public BiomeBase.a c(float fx) {
            this.f = fx;
            return this;
        }

        public BiomeBase.a d(float fx) {
            this.g = fx;
            return this;
        }

        public BiomeBase.a a(int ix) {
            this.h = ix;
            return this;
        }

        public BiomeBase.a b(int ix) {
            this.i = ix;
            return this;
        }

        public BiomeBase.a a(@Nullable String s) {
            this.j = s;
            return this;
        }

        public String toString() {
            return "BiomeBuilder{\nsurfaceBuilder=" + this.a + ",\nprecipitation=" + this.b + ",\nbiomeCategory=" + this.c + ",\ndepth=" + this.d + ",\nscale=" + this.e + ",\ntemperature=" + this.f + ",\ndownfall=" + this.g + ",\nwaterColor=" + this.h + ",\nwaterFogColor=" + this.i + ",\nparent='" + this.j + '\'' + "\n" + '}';
        }
    }
}
