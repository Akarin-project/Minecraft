package net.minecraft.server;

public class WorldGenMineshaftConfiguration implements WorldGenFeatureConfiguration {
    public final double a;
    public final WorldGenMineshaft.Type b;

    public WorldGenMineshaftConfiguration(double d0, WorldGenMineshaft.Type worldgenmineshaft$type) {
        this.a = d0;
        this.b = worldgenmineshaft$type;
    }
}
