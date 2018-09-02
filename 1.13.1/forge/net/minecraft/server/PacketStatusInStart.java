package net.minecraft.server;

import java.io.IOException;

public class PacketStatusInStart implements Packet<PacketStatusInListener> {
    public PacketStatusInStart() {
    }

    public void a(PacketDataSerializer var1) throws IOException {
    }

    public void b(PacketDataSerializer var1) throws IOException {
    }

    public void a(PacketStatusInListener packetstatusinlistener) {
        packetstatusinlistener.a(this);
    }
}
