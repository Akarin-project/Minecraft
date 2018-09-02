package net.minecraft.server;

public interface PacketLoginInListener extends PacketListener {
    void a(PacketLoginInStart var1);

    void a(PacketLoginInEncryptionBegin var1);

    void a(PacketLoginInCustomPayload var1);
}
