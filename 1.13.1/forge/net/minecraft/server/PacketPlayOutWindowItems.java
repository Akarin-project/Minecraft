package net.minecraft.server;

import java.io.IOException;
import java.util.List;

public class PacketPlayOutWindowItems implements Packet<PacketListenerPlayOut> {
    private int a;
    private List<ItemStack> b;

    public PacketPlayOutWindowItems() {
    }

    public PacketPlayOutWindowItems(int i, NonNullList<ItemStack> nonnulllist) {
        this.a = i;
        this.b = NonNullList.<ItemStack>a(nonnulllist.size(), ItemStack.a);

        for(int j = 0; j < this.b.size(); ++j) {
            this.b.set(j, ((ItemStack)nonnulllist.get(j)).cloneItemStack());
        }

    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readUnsignedByte();
        short short1 = packetdataserializer.readShort();
        this.b = NonNullList.<ItemStack>a(short1, ItemStack.a);

        for(int i = 0; i < short1; ++i) {
            this.b.set(i, packetdataserializer.k());
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeByte(this.a);
        packetdataserializer.writeShort(this.b.size());

        for(ItemStack itemstack : this.b) {
            packetdataserializer.a(itemstack);
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
