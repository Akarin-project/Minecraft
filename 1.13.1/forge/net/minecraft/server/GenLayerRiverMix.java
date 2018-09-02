package net.minecraft.server;

public enum GenLayerRiverMix implements AreaTransformer3, AreaTransformerIdentity {
    INSTANCE;

    private static final int b = IRegistry.BIOME.a(Biomes.m);
    private static final int c = IRegistry.BIOME.a(Biomes.n);
    private static final int d = IRegistry.BIOME.a(Biomes.p);
    private static final int e = IRegistry.BIOME.a(Biomes.q);
    private static final int f = IRegistry.BIOME.a(Biomes.i);

    private GenLayerRiverMix() {
    }

    public int a(WorldGenContext var1, AreaDimension var2, Area area, Area area1, int i, int j) {
        int k = area.a(i, j);
        int l = area1.a(i, j);
        if (GenLayers.a(k)) {
            return k;
        } else if (l == f) {
            if (k == c) {
                return b;
            } else {
                return k != d && k != e ? l & 255 : e;
            }
        } else {
            return k;
        }
    }
}
