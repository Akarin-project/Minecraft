package net.minecraft.server;

public enum BlockPropertyBedPart implements INamable {
    HEAD("head"),
    FOOT("foot");

    private final String c;

    private BlockPropertyBedPart(String s1) {
        this.c = s1;
    }

    public String toString() {
        return this.c;
    }

    public String getName() {
        return this.c;
    }
}
