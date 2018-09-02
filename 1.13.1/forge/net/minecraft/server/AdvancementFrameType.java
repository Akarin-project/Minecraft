package net.minecraft.server;

public enum AdvancementFrameType {
    TASK("task", 0, EnumChatFormat.GREEN),
    CHALLENGE("challenge", 26, EnumChatFormat.DARK_PURPLE),
    GOAL("goal", 52, EnumChatFormat.GREEN);

    private final String d;
    private final int e;
    private final EnumChatFormat f;

    private AdvancementFrameType(String s1, int j, EnumChatFormat enumchatformat) {
        this.d = s1;
        this.e = j;
        this.f = enumchatformat;
    }

    public String a() {
        return this.d;
    }

    public static AdvancementFrameType a(String s) {
        for(AdvancementFrameType advancementframetype : values()) {
            if (advancementframetype.d.equals(s)) {
                return advancementframetype;
            }
        }

        throw new IllegalArgumentException("Unknown frame type '" + s + "'");
    }

    public EnumChatFormat c() {
        return this.f;
    }
}
