package net.minecraft.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class PacketPlayOutPosition implements Packet<PacketListenerPlayOut> {
    private double a;
    private double b;
    private double c;
    private float d;
    private float e;
    private Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> f;
    private int g;

    public PacketPlayOutPosition() {
    }

    public PacketPlayOutPosition(double d0, double d1, double d2, float fx, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set, int i) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        this.d = fx;
        this.e = f1;
        this.f = set;
        this.g = i;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readDouble();
        this.b = packetdataserializer.readDouble();
        this.c = packetdataserializer.readDouble();
        this.d = packetdataserializer.readFloat();
        this.e = packetdataserializer.readFloat();
        this.f = PacketPlayOutPosition.EnumPlayerTeleportFlags.a(packetdataserializer.readUnsignedByte());
        this.g = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeDouble(this.a);
        packetdataserializer.writeDouble(this.b);
        packetdataserializer.writeDouble(this.c);
        packetdataserializer.writeFloat(this.d);
        packetdataserializer.writeFloat(this.e);
        packetdataserializer.writeByte(PacketPlayOutPosition.EnumPlayerTeleportFlags.a(this.f));
        packetdataserializer.d(this.g);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public static enum EnumPlayerTeleportFlags {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        private final int f;

        private EnumPlayerTeleportFlags(int j) {
            this.f = j;
        }

        private int a() {
            return 1 << this.f;
        }

        private boolean b(int i) {
            return (i & this.a()) == this.a();
        }

        public static Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> a(int i) {
            EnumSet enumset = EnumSet.noneOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.class);

            for(PacketPlayOutPosition.EnumPlayerTeleportFlags packetplayoutposition$enumplayerteleportflags : values()) {
                if (packetplayoutposition$enumplayerteleportflags.b(i)) {
                    enumset.add(packetplayoutposition$enumplayerteleportflags);
                }
            }

            return enumset;
        }

        public static int a(Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set) {
            int i = 0;

            for(PacketPlayOutPosition.EnumPlayerTeleportFlags packetplayoutposition$enumplayerteleportflags : set) {
                i |= packetplayoutposition$enumplayerteleportflags.a();
            }

            return i;
        }
    }
}
