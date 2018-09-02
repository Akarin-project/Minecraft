package net.minecraft.server;

public enum BlockPropertyPistonType implements INamable {
    DEFAULT("normal"),
    STICKY("sticky");

    private final String c;

    private BlockPropertyPistonType(String s1) {
        this.c = s1;
    }

    public String toString() {
        return this.c;
    }

    public String getName() {
        return this.c;
    }
}
