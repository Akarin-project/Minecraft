package net.minecraft.server;

public class BiomeLayoutCheckerboardConfiguration implements BiomeLayoutConfiguration {
    private BiomeBase[] a = new BiomeBase[]{Biomes.c};
    private int b = 1;

    public BiomeLayoutCheckerboardConfiguration() {
    }

    public BiomeLayoutCheckerboardConfiguration a(BiomeBase[] abiomebase) {
        this.a = abiomebase;
        return this;
    }

    public BiomeLayoutCheckerboardConfiguration a(int i) {
        this.b = i;
        return this;
    }

    public BiomeBase[] a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }
}
