package net.minecraft.server;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class BossBattleServer extends BossBattle {
    private final Set<EntityPlayer> h = Sets.newHashSet();
    private final Set<EntityPlayer> i;
    public boolean visible;

    public BossBattleServer(IChatBaseComponent ichatbasecomponent, BossBattle.BarColor bossbattle$barcolor, BossBattle.BarStyle bossbattle$barstyle) {
        super(MathHelper.a(), ichatbasecomponent, bossbattle$barcolor, bossbattle$barstyle);
        this.i = Collections.unmodifiableSet(this.h);
        this.visible = true;
    }

    public void setProgress(float f) {
        if (f != this.b) {
            super.a(f);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PCT);
        }

    }

    public void a(BossBattle.BarColor bossbattle$barcolor) {
        if (bossbattle$barcolor != this.color) {
            super.a(bossbattle$barcolor);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_STYLE);
        }

    }

    public void a(BossBattle.BarStyle bossbattle$barstyle) {
        if (bossbattle$barstyle != this.style) {
            super.a(bossbattle$barstyle);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_STYLE);
        }

    }

    public BossBattle setDarkenSky(boolean flag) {
        if (flag != this.e) {
            super.a(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossBattle setPlayMusic(boolean flag) {
        if (flag != this.f) {
            super.b(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossBattle setCreateFog(boolean flag) {
        if (flag != this.g) {
            super.c(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
        if (!Objects.equal(ichatbasecomponent, this.title)) {
            super.a(ichatbasecomponent);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_NAME);
        }

    }

    public void sendUpdate(PacketPlayOutBoss.Action packetplayoutboss$action) {
        if (this.visible) {
            PacketPlayOutBoss packetplayoutboss = new PacketPlayOutBoss(packetplayoutboss$action, this);

            for(EntityPlayer entityplayer : this.h) {
                entityplayer.playerConnection.sendPacket(packetplayoutboss);
            }
        }

    }

    public void addPlayer(EntityPlayer entityplayer) {
        if (this.h.add(entityplayer) && this.visible) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(PacketPlayOutBoss.Action.ADD, this));
        }

    }

    public void removePlayer(EntityPlayer entityplayer) {
        if (this.h.remove(entityplayer) && this.visible) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(PacketPlayOutBoss.Action.REMOVE, this));
        }

    }

    public void b() {
        if (!this.h.isEmpty()) {
            for(EntityPlayer entityplayer : this.h) {
                this.removePlayer(entityplayer);
            }
        }

    }

    public boolean g() {
        return this.visible;
    }

    public void setVisible(boolean flag) {
        if (flag != this.visible) {
            this.visible = flag;

            for(EntityPlayer entityplayer : this.h) {
                entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(flag ? PacketPlayOutBoss.Action.ADD : PacketPlayOutBoss.Action.REMOVE, this));
            }
        }

    }

    public Collection<EntityPlayer> getPlayers() {
        return this.i;
    }
}
