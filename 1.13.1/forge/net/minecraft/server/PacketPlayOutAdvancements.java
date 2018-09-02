package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class PacketPlayOutAdvancements implements Packet<PacketListenerPlayOut> {
    private boolean a;
    private Map<MinecraftKey, Advancement.SerializedAdvancement> b;
    private Set<MinecraftKey> c;
    private Map<MinecraftKey, AdvancementProgress> d;

    public PacketPlayOutAdvancements() {
    }

    public PacketPlayOutAdvancements(boolean flag, Collection<Advancement> collection, Set<MinecraftKey> set, Map<MinecraftKey, AdvancementProgress> map) {
        this.a = flag;
        this.b = Maps.newHashMap();

        for(Advancement advancement : collection) {
            this.b.put(advancement.getName(), advancement.a());
        }

        this.c = set;
        this.d = Maps.newHashMap(map);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readBoolean();
        this.b = Maps.newHashMap();
        this.c = Sets.newLinkedHashSet();
        this.d = Maps.newHashMap();
        int i = packetdataserializer.g();

        for(int j = 0; j < i; ++j) {
            MinecraftKey minecraftkey = packetdataserializer.l();
            Advancement.SerializedAdvancement advancement$serializedadvancement = Advancement.SerializedAdvancement.b(packetdataserializer);
            this.b.put(minecraftkey, advancement$serializedadvancement);
        }

        i = packetdataserializer.g();

        for(int k = 0; k < i; ++k) {
            MinecraftKey minecraftkey1 = packetdataserializer.l();
            this.c.add(minecraftkey1);
        }

        i = packetdataserializer.g();

        for(int l = 0; l < i; ++l) {
            MinecraftKey minecraftkey2 = packetdataserializer.l();
            this.d.put(minecraftkey2, AdvancementProgress.b(packetdataserializer));
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeBoolean(this.a);
        packetdataserializer.d(this.b.size());

        for(Entry entry : this.b.entrySet()) {
            MinecraftKey minecraftkey = (MinecraftKey)entry.getKey();
            Advancement.SerializedAdvancement advancement$serializedadvancement = (Advancement.SerializedAdvancement)entry.getValue();
            packetdataserializer.a(minecraftkey);
            advancement$serializedadvancement.a(packetdataserializer);
        }

        packetdataserializer.d(this.c.size());

        for(MinecraftKey minecraftkey1 : this.c) {
            packetdataserializer.a(minecraftkey1);
        }

        packetdataserializer.d(this.d.size());

        for(Entry entry1 : this.d.entrySet()) {
            packetdataserializer.a((MinecraftKey)entry1.getKey());
            ((AdvancementProgress)entry1.getValue()).a(packetdataserializer);
        }

    }
}
