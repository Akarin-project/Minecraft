package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import java.util.function.LongFunction;

public class GenLayers {
    protected static final int a = IRegistry.BIOME.a(Biomes.T);
    protected static final int b = IRegistry.BIOME.a(Biomes.U);
    protected static final int c = IRegistry.BIOME.a(Biomes.a);
    protected static final int d = IRegistry.BIOME.a(Biomes.V);
    protected static final int e = IRegistry.BIOME.a(Biomes.l);
    protected static final int f = IRegistry.BIOME.a(Biomes.W);
    protected static final int g = IRegistry.BIOME.a(Biomes.X);
    protected static final int h = IRegistry.BIOME.a(Biomes.z);
    protected static final int i = IRegistry.BIOME.a(Biomes.Y);
    protected static final int j = IRegistry.BIOME.a(Biomes.Z);

    private static <T extends Area, C extends AreaContextTransformed<T>> AreaFactory<T> a(long ix, AreaTransformer2 areatransformer2, AreaFactory<T> areafactory, int jx, LongFunction<C> longfunction) {
        AreaFactory areafactory1 = areafactory;

        for(int k = 0; k < jx; ++k) {
            areafactory1 = areatransformer2.a((AreaContextTransformed)longfunction.apply(ix + (long)k), areafactory1);
        }

        return areafactory1;
    }

    public static <T extends Area, C extends AreaContextTransformed<T>> ImmutableList<AreaFactory<T>> a(WorldType worldtype, GeneratorSettingsOverworld generatorsettingsoverworld, LongFunction<C> longfunction) {
        AreaFactory areafactory = LayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(1L));
        areafactory = GenLayerZoom.FUZZY.a((AreaContextTransformed)longfunction.apply(2000L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(1L), areafactory);
        areafactory = GenLayerZoom.NORMAL.a((AreaContextTransformed)longfunction.apply(2001L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(50L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(70L), areafactory);
        areafactory = GenLayerIcePlains.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L), areafactory);
        AreaFactory areafactory1 = GenLayerOceanEdge.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L));
        areafactory1 = a(2001L, GenLayerZoom.NORMAL, areafactory1, 6, longfunction);
        areafactory = GenLayerTopSoil.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(3L), areafactory);
        areafactory = GenLayerSpecial.Special1.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L), areafactory);
        areafactory = GenLayerSpecial.Special2.INSTANCE.a((AreaContextTransformed)longfunction.apply(2L), areafactory);
        areafactory = GenLayerSpecial.Special3.INSTANCE.a((AreaContextTransformed)longfunction.apply(3L), areafactory);
        areafactory = GenLayerZoom.NORMAL.a((AreaContextTransformed)longfunction.apply(2002L), areafactory);
        areafactory = GenLayerZoom.NORMAL.a((AreaContextTransformed)longfunction.apply(2003L), areafactory);
        areafactory = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(4L), areafactory);
        areafactory = GenLayerMushroomIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(5L), areafactory);
        areafactory = GenLayerDeepOcean.INSTANCE.a((AreaContextTransformed)longfunction.apply(4L), areafactory);
        areafactory = a(1000L, GenLayerZoom.NORMAL, areafactory, 0, longfunction);
        int ix = 4;
        int jx = ix;
        if (generatorsettingsoverworld != null) {
            ix = generatorsettingsoverworld.t();
            jx = generatorsettingsoverworld.u();
        }

        if (worldtype == WorldType.LARGE_BIOMES) {
            ix = 6;
        }

        AreaFactory areafactory2 = a(1000L, GenLayerZoom.NORMAL, areafactory, 0, longfunction);
        areafactory2 = GenLayerCleaner.INSTANCE.a((AreaContextTransformed)longfunction.apply(100L), areafactory2);
        AreaFactory areafactory3 = (new GenLayerBiome(worldtype, generatorsettingsoverworld)).a((AreaContextTransformed)longfunction.apply(200L), areafactory);
        areafactory3 = a(1000L, GenLayerZoom.NORMAL, areafactory3, 2, longfunction);
        areafactory3 = GenLayerDesert.INSTANCE.a((AreaContextTransformed)longfunction.apply(1000L), areafactory3);
        AreaFactory areafactory4 = a(1000L, GenLayerZoom.NORMAL, areafactory2, 2, longfunction);
        areafactory3 = GenLayerRegionHills.INSTANCE.a((AreaContextTransformed)longfunction.apply(1000L), areafactory3, areafactory4);
        areafactory2 = a(1000L, GenLayerZoom.NORMAL, areafactory2, 2, longfunction);
        areafactory2 = a(1000L, GenLayerZoom.NORMAL, areafactory2, jx, longfunction);
        areafactory2 = GenLayerRiver.INSTANCE.a((AreaContextTransformed)longfunction.apply(1L), areafactory2);
        areafactory2 = GenLayerSmooth.INSTANCE.a((AreaContextTransformed)longfunction.apply(1000L), areafactory2);
        areafactory3 = GenLayerPlains.INSTANCE.a((AreaContextTransformed)longfunction.apply(1001L), areafactory3);

        for(int k = 0; k < ix; ++k) {
            areafactory3 = GenLayerZoom.NORMAL.a((AreaContextTransformed)longfunction.apply((long)(1000 + k)), areafactory3);
            if (k == 0) {
                areafactory3 = GenLayerIsland.INSTANCE.a((AreaContextTransformed)longfunction.apply(3L), areafactory3);
            }

            if (k == 1 || ix == 1) {
                areafactory3 = GenLayerMushroomShore.INSTANCE.a((AreaContextTransformed)longfunction.apply(1000L), areafactory3);
            }
        }

        areafactory3 = GenLayerSmooth.INSTANCE.a((AreaContextTransformed)longfunction.apply(1000L), areafactory3);
        areafactory3 = GenLayerRiverMix.INSTANCE.a((AreaContextTransformed)longfunction.apply(100L), areafactory3, areafactory2);
        areafactory3 = GenLayerOcean.INSTANCE.a((AreaContextTransformed)longfunction.apply(100L), areafactory3, areafactory1);
        AreaFactory areafactory5 = GenLayerZoomVoronoi.INSTANCE.a((AreaContextTransformed)longfunction.apply(10L), areafactory3);
        return ImmutableList.of(areafactory3, areafactory5, areafactory3);
    }

    public static GenLayer[] a(long ix, WorldType worldtype, GeneratorSettingsOverworld generatorsettingsoverworld) {
        boolean flag = true;
        int[] aint = new int[1];
        ImmutableList immutablelist = a(worldtype, generatorsettingsoverworld, (k) -> {
            ++aint[0];
            return new WorldGenContextArea(1, aint[0], ix, k);
        });
        GenLayer genlayer = new GenLayer((AreaFactory)immutablelist.get(0));
        GenLayer genlayer1 = new GenLayer((AreaFactory)immutablelist.get(1));
        GenLayer genlayer2 = new GenLayer((AreaFactory)immutablelist.get(2));
        return new GenLayer[]{genlayer, genlayer1, genlayer2};
    }

    public static boolean a(int ix, int jx) {
        if (ix == jx) {
            return true;
        } else {
            BiomeBase biomebase = IRegistry.BIOME.fromId(ix);
            BiomeBase biomebase1 = IRegistry.BIOME.fromId(jx);
            if (biomebase != null && biomebase1 != null) {
                if (biomebase != Biomes.N && biomebase != Biomes.O) {
                    if (biomebase.p() != BiomeBase.Geography.NONE && biomebase1.p() != BiomeBase.Geography.NONE && biomebase.p() == biomebase1.p()) {
                        return true;
                    } else {
                        return biomebase == biomebase1;
                    }
                } else {
                    return biomebase1 == Biomes.N || biomebase1 == Biomes.O;
                }
            } else {
                return false;
            }
        }
    }

    protected static boolean a(int ix) {
        return ix == a || ix == b || ix == c || ix == d || ix == e || ix == f || ix == g || ix == h || ix == i || ix == j;
    }

    protected static boolean b(int ix) {
        return ix == a || ix == b || ix == c || ix == d || ix == e;
    }
}
