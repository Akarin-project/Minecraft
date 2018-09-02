package net.minecraft.server;

public enum BlockPropertyHalf implements INamable {
    TOP("top"),
    BOTTOM("bottom");

    private final String c;

    private BlockPropertyHalf(String s1) {
        this.c = s1;
    }

    public String toString() {
        return this.c;
    }

    public String getName() {
        return this.c;
    }
}
