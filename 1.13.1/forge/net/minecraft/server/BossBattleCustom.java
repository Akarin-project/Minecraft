package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BossBattleCustom extends BossBattleServer {
    private final MinecraftKey h;
    private final Set<UUID> i = Sets.newHashSet();
    private int j;
    private int k = 100;

    public BossBattleCustom(MinecraftKey minecraftkey, IChatBaseComponent ichatbasecomponent) {
        super(ichatbasecomponent, BossBattle.BarColor.WHITE, BossBattle.BarStyle.PROGRESS);
        this.h = minecraftkey;
        this.setProgress(0.0F);
    }

    public MinecraftKey a() {
        return this.h;
    }

    public void addPlayer(EntityPlayer entityplayer) {
        super.addPlayer(entityplayer);
        this.i.add(entityplayer.getUniqueID());
    }

    public void a(UUID uuid) {
        this.i.add(uuid);
    }

    public void removePlayer(EntityPlayer entityplayer) {
        super.removePlayer(entityplayer);
        this.i.remove(entityplayer.getUniqueID());
    }

    public void b() {
        super.b();
        this.i.clear();
    }

    public int c() {
        return this.j;
    }

    public int d() {
        return this.k;
    }

    public void a(int ix) {
        this.j = ix;
        this.setProgress(MathHelper.a((float)ix / (float)this.k, 0.0F, 1.0F));
    }

    public void b(int ix) {
        this.k = ix;
        this.setProgress(MathHelper.a((float)this.j / (float)ix, 0.0F, 1.0F));
    }

    public final IChatBaseComponent e() {
        return ChatComponentUtils.a(this.j()).a((chatmodifier) -> {
            chatmodifier.setColor(this.l().a()).setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_TEXT, new ChatComponentText(this.a().toString()))).setInsertion(this.a().toString());
        });
    }

    public boolean a(Collection<EntityPlayer> collection) {
        HashSet hashset = Sets.newHashSet();
        HashSet hashset1 = Sets.newHashSet();

        for(UUID uuid : this.i) {
            boolean flag = false;

            for(EntityPlayer entityplayer : collection) {
                if (entityplayer.getUniqueID().equals(uuid)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                hashset.add(uuid);
            }
        }

        for(EntityPlayer entityplayer1 : collection) {
            boolean flag1 = false;

            for(UUID uuid2 : this.i) {
                if (entityplayer1.getUniqueID().equals(uuid2)) {
                    flag1 = true;
                    break;
                }
            }

            if (!flag1) {
                hashset1.add(entityplayer1);
            }
        }

        for(UUID uuid1 : hashset) {
            for(EntityPlayer entityplayer3 : this.getPlayers()) {
                if (entityplayer3.getUniqueID().equals(uuid1)) {
                    this.removePlayer(entityplayer3);
                    break;
                }
            }

            this.i.remove(uuid1);
        }

        for(EntityPlayer entityplayer2 : hashset1) {
            this.addPlayer(entityplayer2);
        }

        return !hashset.isEmpty() || !hashset1.isEmpty();
    }

    public NBTTagCompound f() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("Name", IChatBaseComponent.ChatSerializer.a(this.title));
        nbttagcompound.setBoolean("Visible", this.g());
        nbttagcompound.setInt("Value", this.j);
        nbttagcompound.setInt("Max", this.k);
        nbttagcompound.setString("Color", this.l().b());
        nbttagcompound.setString("Overlay", this.m().a());
        nbttagcompound.setBoolean("DarkenScreen", this.n());
        nbttagcompound.setBoolean("PlayBossMusic", this.o());
        nbttagcompound.setBoolean("CreateWorldFog", this.p());
        NBTTagList nbttaglist = new NBTTagList();

        for(UUID uuid : this.i) {
            nbttaglist.add((NBTBase)GameProfileSerializer.a(uuid));
        }

        nbttagcompound.set("Players", nbttaglist);
        return nbttagcompound;
    }

    public static BossBattleCustom a(NBTTagCompound nbttagcompound, MinecraftKey minecraftkey) {
        BossBattleCustom bossbattlecustom = new BossBattleCustom(minecraftkey, IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("Name")));
        bossbattlecustom.setVisible(nbttagcompound.getBoolean("Visible"));
        bossbattlecustom.a(nbttagcompound.getInt("Value"));
        bossbattlecustom.b(nbttagcompound.getInt("Max"));
        bossbattlecustom.a(BossBattle.BarColor.a(nbttagcompound.getString("Color")));
        bossbattlecustom.a(BossBattle.BarStyle.a(nbttagcompound.getString("Overlay")));
        bossbattlecustom.setDarkenSky(nbttagcompound.getBoolean("DarkenScreen"));
        bossbattlecustom.setPlayMusic(nbttagcompound.getBoolean("PlayBossMusic"));
        bossbattlecustom.setCreateFog(nbttagcompound.getBoolean("CreateWorldFog"));
        NBTTagList nbttaglist = nbttagcompound.getList("Players", 10);

        for(int ix = 0; ix < nbttaglist.size(); ++ix) {
            bossbattlecustom.a(GameProfileSerializer.b(nbttaglist.getCompound(ix)));
        }

        return bossbattlecustom;
    }

    public void c(EntityPlayer entityplayer) {
        if (this.i.contains(entityplayer.getUniqueID())) {
            this.addPlayer(entityplayer);
        }

    }

    public void d(EntityPlayer entityplayer) {
        super.removePlayer(entityplayer);
    }
}
