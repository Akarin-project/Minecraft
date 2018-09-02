package net.minecraft.server;

public enum BlockPropertyStairsShape implements INamable {
    STRAIGHT("straight"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right");

    private final String f;

    private BlockPropertyStairsShape(String s1) {
        this.f = s1;
    }

    public String toString() {
        return this.f;
    }

    public String getName() {
        return this.f;
    }
}
