package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PacketPlayOutUpdateAttributes implements Packet<PacketListenerPlayOut> {
    private int a;
    private final List<PacketPlayOutUpdateAttributes.AttributeSnapshot> b = Lists.newArrayList();

    public PacketPlayOutUpdateAttributes() {
    }

    public PacketPlayOutUpdateAttributes(int i, Collection<AttributeInstance> collection) {
        this.a = i;

        for(AttributeInstance attributeinstance : collection) {
            this.b.add(new PacketPlayOutUpdateAttributes.AttributeSnapshot(attributeinstance.getAttribute().getName(), attributeinstance.b(), attributeinstance.c()));
        }

    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
        int i = packetdataserializer.readInt();

        for(int j = 0; j < i; ++j) {
            String s = packetdataserializer.e(64);
            double d0 = packetdataserializer.readDouble();
            ArrayList arraylist = Lists.newArrayList();
            int k = packetdataserializer.g();

            for(int l = 0; l < k; ++l) {
                UUID uuid = packetdataserializer.i();
                arraylist.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", packetdataserializer.readDouble(), packetdataserializer.readByte()));
            }

            this.b.add(new PacketPlayOutUpdateAttributes.AttributeSnapshot(s, d0, arraylist));
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
        packetdataserializer.writeInt(this.b.size());

        for(PacketPlayOutUpdateAttributes.AttributeSnapshot packetplayoutupdateattributes$attributesnapshot : this.b) {
            packetdataserializer.a(packetplayoutupdateattributes$attributesnapshot.a());
            packetdataserializer.writeDouble(packetplayoutupdateattributes$attributesnapshot.b());
            packetdataserializer.d(packetplayoutupdateattributes$attributesnapshot.c().size());

            for(AttributeModifier attributemodifier : packetplayoutupdateattributes$attributesnapshot.c()) {
                packetdataserializer.a(attributemodifier.a());
                packetdataserializer.writeDouble(attributemodifier.d());
                packetdataserializer.writeByte(attributemodifier.c());
            }
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public class AttributeSnapshot {
        private final String b;
        private final double c;
        private final Collection<AttributeModifier> d;

        public AttributeSnapshot(String s, double d0, Collection collection) {
            this.b = s;
            this.c = d0;
            this.d = collection;
        }

        public String a() {
            return this.b;
        }

        public double b() {
            return this.c;
        }

        public Collection<AttributeModifier> c() {
            return this.d;
        }
    }
}
