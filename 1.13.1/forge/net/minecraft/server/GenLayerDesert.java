package net.minecraft.server;

public enum GenLayerDesert implements AreaTransformer7 {
    INSTANCE;

    private static final int b = IRegistry.BIOME.a(Biomes.d);
    private static final int c = IRegistry.BIOME.a(Biomes.e);
    private static final int d = IRegistry.BIOME.a(Biomes.J);
    private static final int e = IRegistry.BIOME.a(Biomes.n);
    private static final int f = IRegistry.BIOME.a(Biomes.w);
    private static final int g = IRegistry.BIOME.a(Biomes.y);
    private static final int h = IRegistry.BIOME.a(Biomes.M);
    private static final int i = IRegistry.BIOME.a(Biomes.O);
    private static final int j = IRegistry.BIOME.a(Biomes.N);
    private static final int k = IRegistry.BIOME.a(Biomes.c);
    private static final int l = IRegistry.BIOME.a(Biomes.H);
    private static final int m = IRegistry.BIOME.a(Biomes.v);
    private static final int n = IRegistry.BIOME.a(Biomes.h);
    private static final int o = IRegistry.BIOME.a(Biomes.g);
    private static final int p = IRegistry.BIOME.a(Biomes.F);

    private GenLayerDesert() {
    }

    public int a(WorldGenContext var1, int ix, int jx, int kx, int lx, int i1) {
        int[] aint = new int[1];
        if (!this.a(aint, ix, jx, kx, lx, i1, c, m) && !this.b(aint, ix, jx, kx, lx, i1, j, h) && !this.b(aint, ix, jx, kx, lx, i1, i, h) && !this.b(aint, ix, jx, kx, lx, i1, l, o)) {
            if (i1 != b || ix != e && jx != e && lx != e && kx != e) {
                if (i1 == n) {
                    if (ix == b || jx == b || lx == b || kx == b || ix == p || jx == p || lx == p || kx == p || ix == e || jx == e || lx == e || kx == e) {
                        return k;
                    }

                    if (ix == f || kx == f || jx == f || lx == f) {
                        return g;
                    }
                }

                return i1;
            } else {
                return d;
            }
        } else {
            return aint[0];
        }
    }

    private boolean a(int[] aint, int ix, int jx, int kx, int lx, int i1, int j1, int k1) {
        if (!GenLayers.a(i1, j1)) {
            return false;
        } else {
            if (this.a(ix, j1) && this.a(jx, j1) && this.a(lx, j1) && this.a(kx, j1)) {
                aint[0] = i1;
            } else {
                aint[0] = k1;
            }

            return true;
        }
    }

    private boolean b(int[] aint, int ix, int jx, int kx, int lx, int i1, int j1, int k1) {
        if (i1 != j1) {
            return false;
        } else {
            if (GenLayers.a(ix, j1) && GenLayers.a(jx, j1) && GenLayers.a(lx, j1) && GenLayers.a(kx, j1)) {
                aint[0] = i1;
            } else {
                aint[0] = k1;
            }

            return true;
        }
    }

    private boolean a(int ix, int jx) {
        if (GenLayers.a(ix, jx)) {
            return true;
        } else {
            BiomeBase biomebase = IRegistry.BIOME.fromId(ix);
            BiomeBase biomebase1 = IRegistry.BIOME.fromId(jx);
            if (biomebase != null && biomebase1 != null) {
                BiomeBase.EnumTemperature biomebase$enumtemperature = biomebase.g();
                BiomeBase.EnumTemperature biomebase$enumtemperature1 = biomebase1.g();
                return biomebase$enumtemperature == biomebase$enumtemperature1 || biomebase$enumtemperature == BiomeBase.EnumTemperature.MEDIUM || biomebase$enumtemperature1 == BiomeBase.EnumTemperature.MEDIUM;
            } else {
                return false;
            }
        }
    }
}
