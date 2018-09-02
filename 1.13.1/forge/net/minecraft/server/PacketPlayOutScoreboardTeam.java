package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;

public class PacketPlayOutScoreboardTeam implements Packet<PacketListenerPlayOut> {
    private String a = "";
    private IChatBaseComponent b = new ChatComponentText("");
    private IChatBaseComponent c = new ChatComponentText("");
    private IChatBaseComponent d = new ChatComponentText("");
    private String e;
    private String f;
    private EnumChatFormat g;
    private final Collection<String> h;
    private int i;
    private int j;

    public PacketPlayOutScoreboardTeam() {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = ScoreboardTeamBase.EnumTeamPush.ALWAYS.e;
        this.g = EnumChatFormat.RESET;
        this.h = Lists.newArrayList();
    }

    public PacketPlayOutScoreboardTeam(ScoreboardTeam scoreboardteam, int ix) {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = ScoreboardTeamBase.EnumTeamPush.ALWAYS.e;
        this.g = EnumChatFormat.RESET;
        this.h = Lists.newArrayList();
        this.a = scoreboardteam.getName();
        this.i = ix;
        if (ix == 0 || ix == 2) {
            this.b = scoreboardteam.getDisplayName();
            this.j = scoreboardteam.packOptionData();
            this.e = scoreboardteam.getNameTagVisibility().e;
            this.f = scoreboardteam.getCollisionRule().e;
            this.g = scoreboardteam.getColor();
            this.c = scoreboardteam.getPrefix();
            this.d = scoreboardteam.getSuffix();
        }

        if (ix == 0) {
            this.h.addAll(scoreboardteam.getPlayerNameSet());
        }

    }

    public PacketPlayOutScoreboardTeam(ScoreboardTeam scoreboardteam, Collection<String> collection, int ix) {
        this.e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
        this.f = ScoreboardTeamBase.EnumTeamPush.ALWAYS.e;
        this.g = EnumChatFormat.RESET;
        this.h = Lists.newArrayList();
        if (ix != 3 && ix != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        } else if (collection != null && !collection.isEmpty()) {
            this.i = ix;
            this.a = scoreboardteam.getName();
            this.h.addAll(collection);
        } else {
            throw new IllegalArgumentException("Players cannot be null/empty");
        }
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e(16);
        this.i = packetdataserializer.readByte();
        if (this.i == 0 || this.i == 2) {
            this.b = packetdataserializer.f();
            this.j = packetdataserializer.readByte();
            this.e = packetdataserializer.e(40);
            this.f = packetdataserializer.e(40);
            this.g = (EnumChatFormat)packetdataserializer.a(EnumChatFormat.class);
            this.c = packetdataserializer.f();
            this.d = packetdataserializer.f();
        }

        if (this.i == 0 || this.i == 3 || this.i == 4) {
            int ix = packetdataserializer.g();

            for(int jx = 0; jx < ix; ++jx) {
                this.h.add(packetdataserializer.e(40));
            }
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a(this.a);
        packetdataserializer.writeByte(this.i);
        if (this.i == 0 || this.i == 2) {
            packetdataserializer.a(this.b);
            packetdataserializer.writeByte(this.j);
            packetdataserializer.a(this.e);
            packetdataserializer.a(this.f);
            packetdataserializer.a((Enum)this.g);
            packetdataserializer.a(this.c);
            packetdataserializer.a(this.d);
        }

        if (this.i == 0 || this.i == 3 || this.i == 4) {
            packetdataserializer.d(this.h.size());

            for(String s : this.h) {
                packetdataserializer.a(s);
            }
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
