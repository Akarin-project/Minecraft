package net.minecraft.server;

public enum ChatMessageType {
    CHAT((byte)0),
    SYSTEM((byte)1),
    GAME_INFO((byte)2);

    private final byte d;

    private ChatMessageType(byte b0) {
        this.d = b0;
    }

    public byte a() {
        return this.d;
    }

    public static ChatMessageType a(byte b0) {
        for(ChatMessageType chatmessagetype : values()) {
            if (b0 == chatmessagetype.d) {
                return chatmessagetype;
            }
        }

        return CHAT;
    }
}
