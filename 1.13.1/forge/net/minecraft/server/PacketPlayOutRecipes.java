package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class PacketPlayOutRecipes implements Packet<PacketListenerPlayOut> {
    private PacketPlayOutRecipes.Action a;
    private List<MinecraftKey> b;
    private List<MinecraftKey> c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;

    public PacketPlayOutRecipes() {
    }

    public PacketPlayOutRecipes(PacketPlayOutRecipes.Action packetplayoutrecipes$action, Collection<MinecraftKey> collection, Collection<MinecraftKey> collection1, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        this.a = packetplayoutrecipes$action;
        this.b = ImmutableList.copyOf(collection);
        this.c = ImmutableList.copyOf(collection1);
        this.d = flag;
        this.e = flag1;
        this.f = flag2;
        this.g = flag3;
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (PacketPlayOutRecipes.Action)packetdataserializer.a(PacketPlayOutRecipes.Action.class);
        this.d = packetdataserializer.readBoolean();
        this.e = packetdataserializer.readBoolean();
        this.f = packetdataserializer.readBoolean();
        this.g = packetdataserializer.readBoolean();
        int i = packetdataserializer.g();
        this.b = Lists.newArrayList();

        for(int j = 0; j < i; ++j) {
            this.b.add(packetdataserializer.l());
        }

        if (this.a == PacketPlayOutRecipes.Action.INIT) {
            i = packetdataserializer.g();
            this.c = Lists.newArrayList();

            for(int k = 0; k < i; ++k) {
                this.c.add(packetdataserializer.l());
            }
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum)this.a);
        packetdataserializer.writeBoolean(this.d);
        packetdataserializer.writeBoolean(this.e);
        packetdataserializer.writeBoolean(this.f);
        packetdataserializer.writeBoolean(this.g);
        packetdataserializer.d(this.b.size());

        for(MinecraftKey minecraftkey : this.b) {
            packetdataserializer.a(minecraftkey);
        }

        if (this.a == PacketPlayOutRecipes.Action.INIT) {
            packetdataserializer.d(this.c.size());

            for(MinecraftKey minecraftkey1 : this.c) {
                packetdataserializer.a(minecraftkey1);
            }
        }

    }

    public static enum Action {
        INIT,
        ADD,
        REMOVE;

        private Action() {
        }
    }
}
