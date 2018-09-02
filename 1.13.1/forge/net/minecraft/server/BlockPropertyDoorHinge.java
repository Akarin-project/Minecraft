package net.minecraft.server;

public enum BlockPropertyDoorHinge implements INamable {
    LEFT,
    RIGHT;

    private BlockPropertyDoorHinge() {
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this == LEFT ? "left" : "right";
    }
}
