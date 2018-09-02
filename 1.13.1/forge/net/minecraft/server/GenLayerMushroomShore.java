package net.minecraft.server;

public enum GenLayerMushroomShore implements AreaTransformer7 {
    INSTANCE;

    private static final int b = IRegistry.BIOME.a(Biomes.r);
    private static final int c = IRegistry.BIOME.a(Biomes.B);
    private static final int d = IRegistry.BIOME.a(Biomes.d);
    private static final int e = IRegistry.BIOME.a(Biomes.e);
    private static final int f = IRegistry.BIOME.a(Biomes.J);
    private static final int g = IRegistry.BIOME.a(Biomes.f);
    private static final int h = IRegistry.BIOME.a(Biomes.w);
    private static final int i = IRegistry.BIOME.a(Biomes.y);
    private static final int j = IRegistry.BIOME.a(Biomes.x);
    private static final int k = IRegistry.BIOME.a(Biomes.M);
    private static final int l = IRegistry.BIOME.a(Biomes.N);
    private static final int m = IRegistry.BIOME.a(Biomes.O);
    private static final int n = IRegistry.BIOME.a(Biomes.at);
    private static final int o = IRegistry.BIOME.a(Biomes.au);
    private static final int p = IRegistry.BIOME.a(Biomes.av);
    private static final int q = IRegistry.BIOME.a(Biomes.p);
    private static final int r = IRegistry.BIOME.a(Biomes.q);
    private static final int s = IRegistry.BIOME.a(Biomes.i);
    private static final int t = IRegistry.BIOME.a(Biomes.v);
    private static final int u = IRegistry.BIOME.a(Biomes.A);
    private static final int v = IRegistry.BIOME.a(Biomes.h);
    private static final int w = IRegistry.BIOME.a(Biomes.g);

    private GenLayerMushroomShore() {
    }

    public int a(WorldGenContext var1, int ix, int jx, int kx, int lx, int i1) {
        BiomeBase biomebase = IRegistry.BIOME.fromId(i1);
        if (i1 == q) {
            if (GenLayers.b(ix) || GenLayers.b(jx) || GenLayers.b(kx) || GenLayers.b(lx)) {
                return r;
            }
        } else if (biomebase != null && biomebase.p() == BiomeBase.Geography.JUNGLE) {
            if (!a(ix) || !a(jx) || !a(kx) || !a(lx)) {
                return i;
            }

            if (GenLayers.a(ix) || GenLayers.a(jx) || GenLayers.a(kx) || GenLayers.a(lx)) {
                return b;
            }
        } else if (i1 != e && i1 != f && i1 != t) {
            if (biomebase != null && biomebase.c() == BiomeBase.Precipitation.SNOW) {
                if (!GenLayers.a(i1) && (GenLayers.a(ix) || GenLayers.a(jx) || GenLayers.a(kx) || GenLayers.a(lx))) {
                    return c;
                }
            } else if (i1 != k && i1 != l) {
                if (!GenLayers.a(i1) && i1 != s && i1 != v && (GenLayers.a(ix) || GenLayers.a(jx) || GenLayers.a(kx) || GenLayers.a(lx))) {
                    return b;
                }
            } else if (!GenLayers.a(ix) && !GenLayers.a(jx) && !GenLayers.a(kx) && !GenLayers.a(lx) && (!this.b(ix) || !this.b(jx) || !this.b(kx) || !this.b(lx))) {
                return d;
            }
        } else if (!GenLayers.a(i1) && (GenLayers.a(ix) || GenLayers.a(jx) || GenLayers.a(kx) || GenLayers.a(lx))) {
            return u;
        }

        return i1;
    }

    private static boolean a(int ix) {
        if (IRegistry.BIOME.fromId(ix) != null && ((BiomeBase)IRegistry.BIOME.fromId(ix)).p() == BiomeBase.Geography.JUNGLE) {
            return true;
        } else {
            return ix == i || ix == h || ix == j || ix == g || ix == w || GenLayers.a(ix);
        }
    }

    private boolean b(int ix) {
        return ix == k || ix == l || ix == m || ix == n || ix == o || ix == p;
    }
}
