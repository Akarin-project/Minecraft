package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutSetCooldown implements Packet<PacketListenerPlayOut> {
    private Item a;
    private int b;

    public PacketPlayOutSetCooldown() {
    }

    public PacketPlayOutSetCooldown(Item item, int i) {
        this.a = item;
        this.b = i;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = Item.getById(packetdataserializer.g());
        this.b = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(Item.getId(this.a));
        packetdataserializer.d(this.b);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
