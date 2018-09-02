package net.minecraft.server;

public enum TextureType {
    SOLID("Solid"),
    CUTOUT_MIPPED("Mipped Cutout"),
    CUTOUT("Cutout"),
    TRANSLUCENT("Translucent");

    private final String e;

    private TextureType(String s1) {
        this.e = s1;
    }

    public String toString() {
        return this.e;
    }
}
