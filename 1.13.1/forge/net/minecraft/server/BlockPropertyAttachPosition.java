package net.minecraft.server;

public enum BlockPropertyAttachPosition implements INamable {
    FLOOR("floor"),
    WALL("wall"),
    CEILING("ceiling");

    private final String d;

    private BlockPropertyAttachPosition(String s1) {
        this.d = s1;
    }

    public String getName() {
        return this.d;
    }
}
