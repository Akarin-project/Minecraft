package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutMultiBlockChange implements Packet<PacketListenerPlayOut> {
    private ChunkCoordIntPair a;
    private PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[] b;

    public PacketPlayOutMultiBlockChange() {
    }

    public PacketPlayOutMultiBlockChange(int i, short[] ashort, Chunk chunk) {
        this.a = new ChunkCoordIntPair(chunk.locX, chunk.locZ);
        this.b = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[i];

        for(int j = 0; j < this.b.length; ++j) {
            this.b[j] = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo(ashort[j], chunk);
        }

    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = new ChunkCoordIntPair(packetdataserializer.readInt(), packetdataserializer.readInt());
        this.b = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[packetdataserializer.g()];

        for(int i = 0; i < this.b.length; ++i) {
            this.b[i] = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo(packetdataserializer.readShort(), Block.REGISTRY_ID.fromId(packetdataserializer.g()));
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeInt(this.a.x);
        packetdataserializer.writeInt(this.a.z);
        packetdataserializer.d(this.b.length);

        for(PacketPlayOutMultiBlockChange.MultiBlockChangeInfo packetplayoutmultiblockchange$multiblockchangeinfo : this.b) {
            packetdataserializer.writeShort(packetplayoutmultiblockchange$multiblockchangeinfo.b());
            packetdataserializer.d(Block.getCombinedId(packetplayoutmultiblockchange$multiblockchangeinfo.c()));
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public class MultiBlockChangeInfo {
        private final short b;
        private final IBlockData c;

        public MultiBlockChangeInfo(short short1, IBlockData iblockdata) {
            this.b = short1;
            this.c = iblockdata;
        }

        public MultiBlockChangeInfo(short short1, Chunk chunk) {
            this.b = short1;
            this.c = chunk.getType(this.a());
        }

        public BlockPosition a() {
            return new BlockPosition(PacketPlayOutMultiBlockChange.this.a.a(this.b >> 12 & 15, this.b & 255, this.b >> 8 & 15));
        }

        public short b() {
            return this.b;
        }

        public IBlockData c() {
            return this.c;
        }
    }
}
