package net.minecraft.server;

public enum GenLayerIsland implements AreaTransformer4 {
    INSTANCE;

    private GenLayerIsland() {
    }

    public int a(WorldGenContext worldgencontext, int i, int j, int k, int l, int i1) {
        if (!GenLayers.b(i1) || GenLayers.b(l) && GenLayers.b(k) && GenLayers.b(i) && GenLayers.b(j)) {
            if (!GenLayers.b(i1) && (GenLayers.b(l) || GenLayers.b(i) || GenLayers.b(k) || GenLayers.b(j)) && worldgencontext.a(5) == 0) {
                if (GenLayers.b(l)) {
                    return i1 == 4 ? 4 : l;
                }

                if (GenLayers.b(i)) {
                    return i1 == 4 ? 4 : i;
                }

                if (GenLayers.b(k)) {
                    return i1 == 4 ? 4 : k;
                }

                if (GenLayers.b(j)) {
                    return i1 == 4 ? 4 : j;
                }
            }

            return i1;
        } else {
            int j1 = 1;
            int k1 = 1;
            if (!GenLayers.b(l) && worldgencontext.a(j1++) == 0) {
                k1 = l;
            }

            if (!GenLayers.b(k) && worldgencontext.a(j1++) == 0) {
                k1 = k;
            }

            if (!GenLayers.b(i) && worldgencontext.a(j1++) == 0) {
                k1 = i;
            }

            if (!GenLayers.b(j) && worldgencontext.a(j1++) == 0) {
                k1 = j;
            }

            if (worldgencontext.a(3) == 0) {
                return k1;
            } else {
                return k1 == 4 ? 4 : i1;
            }
        }
    }
}
