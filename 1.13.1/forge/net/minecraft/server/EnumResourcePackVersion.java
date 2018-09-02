package net.minecraft.server;

public enum EnumResourcePackVersion {
    TOO_OLD("old"),
    TOO_NEW("new"),
    COMPATIBLE("compatible");

    private final IChatBaseComponent d;
    private final IChatBaseComponent e;

    private EnumResourcePackVersion(String s1) {
        this.d = new ChatMessage("resourcePack.incompatible." + s1, new Object[0]);
        this.e = new ChatMessage("resourcePack.incompatible.confirm." + s1, new Object[0]);
    }

    public boolean a() {
        return this == COMPATIBLE;
    }

    public static EnumResourcePackVersion a(int i) {
        if (i < 4) {
            return TOO_OLD;
        } else {
            return i > 4 ? TOO_NEW : COMPATIBLE;
        }
    }
}
