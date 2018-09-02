package net.minecraft.server;

public interface PacketLoginOutListener extends PacketListener {
    void a(PacketLoginOutEncryptionBegin var1);

    void a(PacketLoginOutSuccess var1);

    void a(PacketLoginOutDisconnect var1);

    void a(PacketLoginOutSetCompression var1);

    void a(PacketLoginOutCustomPayload var1);
}
