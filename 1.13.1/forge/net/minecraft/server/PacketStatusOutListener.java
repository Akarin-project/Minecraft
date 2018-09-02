package net.minecraft.server;

public interface PacketStatusOutListener extends PacketListener {
    void a(PacketStatusOutServerInfo var1);

    void a(PacketStatusOutPong var1);
}
