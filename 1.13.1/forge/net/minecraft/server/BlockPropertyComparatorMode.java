package net.minecraft.server;

public enum BlockPropertyComparatorMode implements INamable {
    COMPARE("compare"),
    SUBTRACT("subtract");

    private final String c;

    private BlockPropertyComparatorMode(String s1) {
        this.c = s1;
    }

    public String toString() {
        return this.c;
    }

    public String getName() {
        return this.c;
    }
}
