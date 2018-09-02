package net.minecraft.server;

public enum BlockPropertySlabType implements INamable {
    TOP("top"),
    BOTTOM("bottom"),
    DOUBLE("double");

    private final String d;

    private BlockPropertySlabType(String s1) {
        this.d = s1;
    }

    public String toString() {
        return this.d;
    }

    public String getName() {
        return this.d;
    }
}
