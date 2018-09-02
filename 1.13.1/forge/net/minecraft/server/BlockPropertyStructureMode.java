package net.minecraft.server;

public enum BlockPropertyStructureMode implements INamable {
    SAVE("save"),
    LOAD("load"),
    CORNER("corner"),
    DATA("data");

    private final String e;

    private BlockPropertyStructureMode(String s1) {
        this.e = s1;
    }

    public String getName() {
        return this.e;
    }
}
