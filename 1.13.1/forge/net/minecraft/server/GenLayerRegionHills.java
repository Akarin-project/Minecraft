package net.minecraft.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum GenLayerRegionHills implements AreaTransformer3, AreaTransformerOffset1 {
    INSTANCE;

    private static final Logger b = LogManager.getLogger();
    private static final int c = IRegistry.BIOME.a(Biomes.C);
    private static final int d = IRegistry.BIOME.a(Biomes.D);
    private static final int e = IRegistry.BIOME.a(Biomes.d);
    private static final int f = IRegistry.BIOME.a(Biomes.s);
    private static final int g = IRegistry.BIOME.a(Biomes.e);
    private static final int h = IRegistry.BIOME.a(Biomes.J);
    private static final int i = IRegistry.BIOME.a(Biomes.f);
    private static final int j = IRegistry.BIOME.a(Biomes.t);
    private static final int k = IRegistry.BIOME.a(Biomes.n);
    private static final int l = IRegistry.BIOME.a(Biomes.o);
    private static final int m = IRegistry.BIOME.a(Biomes.w);
    private static final int n = IRegistry.BIOME.a(Biomes.x);
    private static final int o = IRegistry.BIOME.a(Biomes.M);
    private static final int p = IRegistry.BIOME.a(Biomes.N);
    private static final int q = IRegistry.BIOME.a(Biomes.c);
    private static final int r = IRegistry.BIOME.a(Biomes.H);
    private static final int s = IRegistry.BIOME.a(Biomes.I);
    private static final int t = IRegistry.BIOME.a(Biomes.E);
    private static final int u = IRegistry.BIOME.a(Biomes.K);
    private static final int v = IRegistry.BIOME.a(Biomes.L);
    private static final int w = IRegistry.BIOME.a(Biomes.g);
    private static final int x = IRegistry.BIOME.a(Biomes.F);
    private static final int y = IRegistry.BIOME.a(Biomes.G);
    private static final int z = IRegistry.BIOME.a(Biomes.u);

    private GenLayerRegionHills() {
    }

    public int a(WorldGenContext worldgencontext, AreaDimension var2, Area area, Area area1, int ix, int jx) {
        int kx = area.a(ix + 1, jx + 1);
        int lx = area1.a(ix + 1, jx + 1);
        if (kx > 255) {
            b.debug("old! {}", kx);
        }

        int i1 = (lx - 2) % 29;
        if (!GenLayers.b(kx) && lx >= 2 && i1 == 1) {
            BiomeBase biomebase = IRegistry.BIOME.fromId(kx);
            if (biomebase == null || !biomebase.b()) {
                BiomeBase biomebase2 = BiomeBase.a(biomebase);
                return biomebase2 == null ? kx : IRegistry.BIOME.a(biomebase2);
            }
        }

        if (worldgencontext.a(3) == 0 || i1 == 0) {
            int j1 = kx;
            if (kx == e) {
                j1 = f;
            } else if (kx == i) {
                j1 = j;
            } else if (kx == c) {
                j1 = d;
            } else if (kx == t) {
                j1 = q;
            } else if (kx == w) {
                j1 = z;
            } else if (kx == r) {
                j1 = s;
            } else if (kx == x) {
                j1 = y;
            } else if (kx == q) {
                j1 = worldgencontext.a(3) == 0 ? j : i;
            } else if (kx == k) {
                j1 = l;
            } else if (kx == m) {
                j1 = n;
            } else if (kx == GenLayers.c) {
                j1 = GenLayers.h;
            } else if (kx == GenLayers.b) {
                j1 = GenLayers.g;
            } else if (kx == GenLayers.d) {
                j1 = GenLayers.i;
            } else if (kx == GenLayers.e) {
                j1 = GenLayers.j;
            } else if (kx == g) {
                j1 = h;
            } else if (kx == u) {
                j1 = v;
            } else if (GenLayers.a(kx, p)) {
                j1 = o;
            } else if ((kx == GenLayers.h || kx == GenLayers.g || kx == GenLayers.i || kx == GenLayers.j) && worldgencontext.a(3) == 0) {
                j1 = worldgencontext.a(2) == 0 ? q : i;
            }

            if (i1 == 0 && j1 != kx) {
                BiomeBase biomebase1 = BiomeBase.a(IRegistry.BIOME.fromId(j1));
                j1 = biomebase1 == null ? kx : IRegistry.BIOME.a(biomebase1);
            }

            if (j1 != kx) {
                int k1 = 0;
                if (GenLayers.a(area.a(ix + 1, jx + 0), kx)) {
                    ++k1;
                }

                if (GenLayers.a(area.a(ix + 2, jx + 1), kx)) {
                    ++k1;
                }

                if (GenLayers.a(area.a(ix + 0, jx + 1), kx)) {
                    ++k1;
                }

                if (GenLayers.a(area.a(ix + 1, jx + 2), kx)) {
                    ++k1;
                }

                if (k1 >= 3) {
                    return j1;
                }
            }
        }

        return kx;
    }
}
