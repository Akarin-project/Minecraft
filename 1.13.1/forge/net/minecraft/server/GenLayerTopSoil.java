package net.minecraft.server;

public enum GenLayerTopSoil implements AreaTransformer6 {
    INSTANCE;

    private GenLayerTopSoil() {
    }

    public int a(WorldGenContext worldgencontext, int i) {
        if (GenLayers.b(i)) {
            return i;
        } else {
            int j = worldgencontext.a(6);
            if (j == 0) {
                return 4;
            } else {
                return j == 1 ? 3 : 1;
            }
        }
    }
}
