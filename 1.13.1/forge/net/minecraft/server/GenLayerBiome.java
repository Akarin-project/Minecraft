package net.minecraft.server;

public class GenLayerBiome implements AreaTransformer5 {
    private static final int a = IRegistry.BIOME.a(Biomes.C);
    private static final int b = IRegistry.BIOME.a(Biomes.d);
    private static final int c = IRegistry.BIOME.a(Biomes.e);
    private static final int d = IRegistry.BIOME.a(Biomes.f);
    private static final int e = IRegistry.BIOME.a(Biomes.n);
    private static final int f = IRegistry.BIOME.a(Biomes.w);
    private static final int g = IRegistry.BIOME.a(Biomes.O);
    private static final int h = IRegistry.BIOME.a(Biomes.N);
    private static final int i = IRegistry.BIOME.a(Biomes.p);
    private static final int j = IRegistry.BIOME.a(Biomes.c);
    private static final int k = IRegistry.BIOME.a(Biomes.H);
    private static final int l = IRegistry.BIOME.a(Biomes.E);
    private static final int m = IRegistry.BIOME.a(Biomes.K);
    private static final int n = IRegistry.BIOME.a(Biomes.h);
    private static final int o = IRegistry.BIOME.a(Biomes.g);
    private static final int p = IRegistry.BIOME.a(Biomes.F);
    private static final int[] q = new int[]{b, d, c, n, j, o};
    private static final int[] r = new int[]{b, b, b, m, m, j};
    private static final int[] s = new int[]{d, l, c, j, a, n};
    private static final int[] t = new int[]{d, c, o, j};
    private static final int[] u = new int[]{e, e, e, p};
    private final GeneratorSettingsOverworld v;
    private int[] w;

    public GenLayerBiome(WorldType worldtype, GeneratorSettingsOverworld generatorsettingsoverworld) {
        this.w = r;
        if (worldtype == WorldType.NORMAL_1_1) {
            this.w = q;
            this.v = null;
        } else {
            this.v = generatorsettingsoverworld;
        }

    }

    public int a(WorldGenContext worldgencontext, int ix) {
        if (this.v != null && this.v.v() >= 0) {
            return this.v.v();
        } else {
            int jx = (ix & 3840) >> 8;
            ix = ix & -3841;
            if (!GenLayers.a(ix) && ix != i) {
                switch(ix) {
                case 1:
                    if (jx > 0) {
                        return worldgencontext.a(3) == 0 ? g : h;
                    }

                    return this.w[worldgencontext.a(this.w.length)];
                case 2:
                    if (jx > 0) {
                        return f;
                    }

                    return s[worldgencontext.a(s.length)];
                case 3:
                    if (jx > 0) {
                        return k;
                    }

                    return t[worldgencontext.a(t.length)];
                case 4:
                    return u[worldgencontext.a(u.length)];
                default:
                    return i;
                }
            } else {
                return ix;
            }
        }
    }
}
