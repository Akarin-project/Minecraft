package net.minecraft.server;

public enum GenLayerOceanEdge implements AreaTransformer1 {
    INSTANCE;

    private GenLayerOceanEdge() {
    }

    public int a(WorldGenContext worldgencontext, AreaDimension areadimension, int i, int j) {
        NoiseGeneratorPerlin noisegeneratorperlin = worldgencontext.a();
        double d0 = noisegeneratorperlin.a((double)(i + areadimension.a()) / 8.0D, (double)(j + areadimension.b()) / 8.0D);
        if (d0 > 0.4D) {
            return GenLayers.a;
        } else if (d0 > 0.2D) {
            return GenLayers.b;
        } else if (d0 < -0.4D) {
            return GenLayers.e;
        } else {
            return d0 < -0.2D ? GenLayers.d : GenLayers.c;
        }
    }
}
