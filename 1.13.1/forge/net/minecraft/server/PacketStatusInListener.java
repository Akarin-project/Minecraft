package net.minecraft.server;

public interface PacketStatusInListener extends PacketListener {
    void a(PacketStatusInPing var1);

    void a(PacketStatusInStart var1);
}
