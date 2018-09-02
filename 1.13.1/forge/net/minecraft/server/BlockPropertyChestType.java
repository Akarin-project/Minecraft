package net.minecraft.server;

public enum BlockPropertyChestType implements INamable {
    SINGLE("single", 0),
    LEFT("left", 2),
    RIGHT("right", 1);

    public static final BlockPropertyChestType[] d = values();
    private final String e;
    private final int f;

    private BlockPropertyChestType(String s1, int j) {
        this.e = s1;
        this.f = j;
    }

    public String getName() {
        return this.e;
    }

    public BlockPropertyChestType a() {
        return d[this.f];
    }
}
