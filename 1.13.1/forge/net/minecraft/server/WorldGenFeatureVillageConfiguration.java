package net.minecraft.server;

public class WorldGenFeatureVillageConfiguration implements WorldGenFeatureConfiguration {
    public final int a;
    public final WorldGenVillagePieces.Material b;

    public WorldGenFeatureVillageConfiguration(int i, WorldGenVillagePieces.Material worldgenvillagepieces$material) {
        this.a = i;
        this.b = worldgenvillagepieces$material;
    }
}
