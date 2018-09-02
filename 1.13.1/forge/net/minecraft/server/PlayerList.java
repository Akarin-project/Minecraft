package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {
    public static final File a = new File("banned-players.json");
    public static final File b = new File("banned-ips.json");
    public static final File c = new File("ops.json");
    public static final File d = new File("whitelist.json");
    private static final Logger f = LogManager.getLogger();
    private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final MinecraftServer server;
    public final List<EntityPlayer> players = Lists.newArrayList();
    private final Map<UUID, EntityPlayer> j = Maps.newHashMap();
    private final GameProfileBanList k;
    private final IpBanList l;
    private final OpList operators;
    private final WhiteList whitelist;
    private final Map<UUID, ServerStatisticManager> o;
    private final Map<UUID, AdvancementDataPlayer> p;
    public IPlayerFileData playerFileData;
    private boolean hasWhitelist;
    protected int maxPlayers;
    private int s;
    private EnumGamemode t;
    private boolean u;
    private int v;

    public PlayerList(MinecraftServer minecraftserver) {
        this.k = new GameProfileBanList(a);
        this.l = new IpBanList(b);
        this.operators = new OpList(c);
        this.whitelist = new WhiteList(d);
        this.o = Maps.newHashMap();
        this.p = Maps.newHashMap();
        this.server = minecraftserver;
        this.getProfileBans().a(true);
        this.getIPBans().a(true);
        this.maxPlayers = 8;
    }

    public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
        GameProfile gameprofile = entityplayer.getProfile();
        UserCache usercache = this.server.getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String sx = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = this.a(entityplayer);
        entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
        String s1 = "local";
        if (networkmanager.getSocketAddress() != null) {
            s1 = networkmanager.getSocketAddress().toString();
        }

        f.info("{}[{}] logged in with entity id {} at ({}, {}, {})", entityplayer.getDisplayName().getString(), s1, entityplayer.getId(), entityplayer.locX, entityplayer.locY, entityplayer.locZ);
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        WorldData worlddata = worldserver.getWorldData();
        this.a(entityplayer, (EntityPlayer)null, worldserver);
        PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);
        playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimensionManager(), worldserver.getDifficulty(), this.getMaxPlayers(), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
        playerconnection.sendPacket(new PacketPlayOutCustomPayload(PacketPlayOutCustomPayload.b, (new PacketDataSerializer(Unpooled.buffer())).a(this.getServer().getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
        playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
        playerconnection.sendPacket(new PacketPlayOutRecipeUpdate(this.server.getCraftingManager().b()));
        playerconnection.sendPacket(new PacketPlayOutTags(this.server.getTagRegistry()));
        this.f(entityplayer);
        entityplayer.getStatisticManager().c();
        entityplayer.B().a(entityplayer);
        this.sendScoreboard(worldserver.l_(), entityplayer);
        this.server.at();
        ChatMessage chatmessage;
        if (entityplayer.getProfile().getName().equalsIgnoreCase(sx)) {
            chatmessage = new ChatMessage("multiplayer.player.joined", new Object[]{entityplayer.getScoreboardDisplayName()});
        } else {
            chatmessage = new ChatMessage("multiplayer.player.joined.renamed", new Object[]{entityplayer.getScoreboardDisplayName(), sx});
        }

        this.sendMessage(chatmessage.a(EnumChatFormat.YELLOW));
        this.onPlayerJoin(entityplayer);
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        this.b(entityplayer, worldserver);
        if (!this.server.getResourcePack().isEmpty()) {
            entityplayer.setResourcePack(this.server.getResourcePack(), this.server.getResourcePackHash());
        }

        for(MobEffect mobeffect : entityplayer.getEffects()) {
            playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("RootVehicle", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("RootVehicle");
            Entity entity1 = ChunkRegionLoader.a(nbttagcompound1.getCompound("Entity"), worldserver, true);
            if (entity1 != null) {
                UUID uuid = nbttagcompound1.a("Attach");
                if (entity1.getUniqueID().equals(uuid)) {
                    entityplayer.a(entity1, true);
                } else {
                    for(Entity entity : entity1.bQ()) {
                        if (entity.getUniqueID().equals(uuid)) {
                            entityplayer.a(entity, true);
                            break;
                        }
                    }
                }

                if (!entityplayer.isPassenger()) {
                    f.warn("Couldn't reattach entity to player");
                    worldserver.removeEntity(entity1);

                    for(Entity entity2 : entity1.bQ()) {
                        worldserver.removeEntity(entity2);
                    }
                }
            }
        }

        entityplayer.syncInventory();
    }

    public void sendScoreboard(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
        HashSet hashset = Sets.newHashSet();

        for(ScoreboardTeam scoreboardteam : scoreboardserver.getTeams()) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
        }

        for(int i = 0; i < 19; ++i) {
            ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);
            if (scoreboardobjective != null && !hashset.contains(scoreboardobjective)) {
                for(Packet packet : scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective)) {
                    entityplayer.playerConnection.sendPacket(packet);
                }

                hashset.add(scoreboardobjective);
            }
        }

    }

    public void setPlayerFileData(WorldServer worldserver) {
        this.playerFileData = worldserver.getDataManager().getPlayerFileData();
        worldserver.getWorldBorder().a(new IWorldBorderListener() {
            public void a(WorldBorder worldborder, double var2) {
                PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE));
            }

            public void a(WorldBorder worldborder, double var2, double var4, long var6) {
                PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE));
            }

            public void a(WorldBorder worldborder, double var2, double var4) {
                PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER));
            }

            public void a(WorldBorder worldborder, int var2) {
                PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME));
            }

            public void b(WorldBorder worldborder, int var2) {
                PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS));
            }

            public void b(WorldBorder var1, double var2) {
            }

            public void c(WorldBorder var1, double var2) {
            }
        });
    }

    public void a(EntityPlayer entityplayer, @Nullable WorldServer worldserver) {
        WorldServer worldserver1 = entityplayer.getWorldServer();
        if (worldserver != null) {
            worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        }

        worldserver1.getPlayerChunkMap().addPlayer(entityplayer);
        worldserver1.getChunkProviderServer().getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4, true, true);
        if (worldserver != null) {
            CriterionTriggers.v.a(entityplayer, worldserver.worldProvider.getDimensionManager(), worldserver1.worldProvider.getDimensionManager());
            if (worldserver.worldProvider.getDimensionManager() == DimensionManager.NETHER && entityplayer.world.worldProvider.getDimensionManager() == DimensionManager.OVERWORLD && entityplayer.M() != null) {
                CriterionTriggers.C.a(entityplayer, entityplayer.M());
            }
        }

    }

    public int d() {
        return PlayerChunkMap.getFurthestViewableBlock(this.s());
    }

    @Nullable
    public NBTTagCompound a(EntityPlayer entityplayer) {
        NBTTagCompound nbttagcompound = this.server.getWorldServer(DimensionManager.OVERWORLD).getWorldData().h();
        NBTTagCompound nbttagcompound1;
        if (entityplayer.getDisplayName().getString().equals(this.server.G()) && nbttagcompound != null) {
            nbttagcompound1 = nbttagcompound;
            entityplayer.f(nbttagcompound);
            f.debug("loading single player");
        } else {
            nbttagcompound1 = this.playerFileData.load(entityplayer);
        }

        return nbttagcompound1;
    }

    protected void savePlayerFile(EntityPlayer entityplayer) {
        this.playerFileData.save(entityplayer);
        ServerStatisticManager serverstatisticmanager = (ServerStatisticManager)this.o.get(entityplayer.getUniqueID());
        if (serverstatisticmanager != null) {
            serverstatisticmanager.a();
        }

        AdvancementDataPlayer advancementdataplayer = (AdvancementDataPlayer)this.p.get(entityplayer.getUniqueID());
        if (advancementdataplayer != null) {
            advancementdataplayer.c();
        }

    }

    public void onPlayerJoin(EntityPlayer entityplayer) {
        this.players.add(entityplayer);
        this.j.put(entityplayer.getUniqueID(), entityplayer);
        this.sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{entityplayer}));
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);

        for(int i = 0; i < this.players.size(); ++i) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{(EntityPlayer)this.players.get(i)}));
        }

        worldserver.addEntity(entityplayer);
        this.a(entityplayer, (WorldServer)null);
        this.server.aP().a(entityplayer);
    }

    public void d(EntityPlayer entityplayer) {
        entityplayer.getWorldServer().getPlayerChunkMap().movePlayer(entityplayer);
    }

    public void disconnect(EntityPlayer entityplayer) {
        WorldServer worldserver = entityplayer.getWorldServer();
        entityplayer.a(StatisticList.LEAVE_GAME);
        this.savePlayerFile(entityplayer);
        if (entityplayer.isPassenger()) {
            Entity entity = entityplayer.getRootVehicle();
            if (entity.bR()) {
                f.debug("Removing player mount");
                entityplayer.stopRiding();
                worldserver.removeEntity(entity);

                for(Entity entity1 : entity.bQ()) {
                    worldserver.removeEntity(entity1);
                }

                worldserver.getChunkAt(entityplayer.ae, entityplayer.ag).markDirty();
            }
        }

        worldserver.kill(entityplayer);
        worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        entityplayer.getAdvancementData().a();
        this.players.remove(entityplayer);
        this.server.aP().b(entityplayer);
        UUID uuid = entityplayer.getUniqueID();
        EntityPlayer entityplayer1 = (EntityPlayer)this.j.get(uuid);
        if (entityplayer1 == entityplayer) {
            this.j.remove(uuid);
            this.o.remove(uuid);
            this.p.remove(uuid);
        }

        this.sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[]{entityplayer}));
    }

    @Nullable
    public IChatBaseComponent attemptLogin(SocketAddress socketaddress, GameProfile gameprofile) {
        if (this.k.isBanned(gameprofile)) {
            GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry)this.k.get(gameprofile);
            ChatMessage chatmessage1 = new ChatMessage("multiplayer.disconnect.banned.reason", new Object[]{gameprofilebanentry.getReason()});
            if (gameprofilebanentry.getExpires() != null) {
                chatmessage1.addSibling(new ChatMessage("multiplayer.disconnect.banned.expiration", new Object[]{g.format(gameprofilebanentry.getExpires())}));
            }

            return chatmessage1;
        } else if (!this.isWhitelisted(gameprofile)) {
            return new ChatMessage("multiplayer.disconnect.not_whitelisted", new Object[0]);
        } else if (this.l.isBanned(socketaddress)) {
            IpBanEntry ipbanentry = this.l.get(socketaddress);
            ChatMessage chatmessage = new ChatMessage("multiplayer.disconnect.banned_ip.reason", new Object[]{ipbanentry.getReason()});
            if (ipbanentry.getExpires() != null) {
                chatmessage.addSibling(new ChatMessage("multiplayer.disconnect.banned_ip.expiration", new Object[]{g.format(ipbanentry.getExpires())}));
            }

            return chatmessage;
        } else {
            return this.players.size() >= this.maxPlayers && !this.f(gameprofile) ? new ChatMessage("multiplayer.disconnect.server_full", new Object[0]) : null;
        }
    }

    public EntityPlayer processLogin(GameProfile gameprofile) {
        UUID uuid = EntityHuman.a(gameprofile);
        ArrayList arraylist = Lists.newArrayList();

        for(int i = 0; i < this.players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
            if (entityplayer.getUniqueID().equals(uuid)) {
                arraylist.add(entityplayer);
            }
        }

        EntityPlayer entityplayer2 = (EntityPlayer)this.j.get(gameprofile.getId());
        if (entityplayer2 != null && !arraylist.contains(entityplayer2)) {
            arraylist.add(entityplayer2);
        }

        for(EntityPlayer entityplayer1 : arraylist) {
            entityplayer1.playerConnection.disconnect(new ChatMessage("multiplayer.disconnect.duplicate_login", new Object[0]));
        }

        Object object;
        if (this.server.L()) {
            object = new DemoPlayerInteractManager(this.server.getWorldServer(DimensionManager.OVERWORLD));
        } else {
            object = new PlayerInteractManager(this.server.getWorldServer(DimensionManager.OVERWORLD));
        }

        return new EntityPlayer(this.server, this.server.getWorldServer(DimensionManager.OVERWORLD), gameprofile, (PlayerInteractManager)object);
    }

    public EntityPlayer moveToWorld(EntityPlayer entityplayer, DimensionManager dimensionmanager, boolean flag) {
        entityplayer.getWorldServer().getTracker().untrackPlayer(entityplayer);
        entityplayer.getWorldServer().getTracker().untrackEntity(entityplayer);
        entityplayer.getWorldServer().getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
        BlockPosition blockposition = entityplayer.getBed();
        boolean flag1 = entityplayer.isRespawnForced();
        entityplayer.dimension = dimensionmanager;
        Object object;
        if (this.server.L()) {
            object = new DemoPlayerInteractManager(this.server.getWorldServer(entityplayer.dimension));
        } else {
            object = new PlayerInteractManager(this.server.getWorldServer(entityplayer.dimension));
        }

        EntityPlayer entityplayer1 = new EntityPlayer(this.server, this.server.getWorldServer(entityplayer.dimension), entityplayer.getProfile(), (PlayerInteractManager)object);
        entityplayer1.playerConnection = entityplayer.playerConnection;
        entityplayer1.copyFrom(entityplayer, flag);
        entityplayer1.e(entityplayer.getId());
        entityplayer1.a(entityplayer.getMainHand());

        for(String sx : entityplayer.getScoreboardTags()) {
            entityplayer1.addScoreboardTag(sx);
        }

        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        this.a(entityplayer1, entityplayer, worldserver);
        if (blockposition != null) {
            BlockPosition blockposition1 = EntityHuman.getBed(this.server.getWorldServer(entityplayer.dimension), blockposition, flag1);
            if (blockposition1 != null) {
                entityplayer1.setPositionRotation((double)((float)blockposition1.getX() + 0.5F), (double)((float)blockposition1.getY() + 0.1F), (double)((float)blockposition1.getZ() + 0.5F), 0.0F, 0.0F);
                entityplayer1.setRespawnPosition(blockposition, flag1);
            } else {
                entityplayer1.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
            }
        }

        worldserver.getChunkProviderServer().getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4, true, true);

        while(!worldserver.getCubes(entityplayer1, entityplayer1.getBoundingBox()) && entityplayer1.locY < 256.0D) {
            entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
        }

        entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn(entityplayer1.dimension, entityplayer1.world.getDifficulty(), entityplayer1.world.getWorldData().getType(), entityplayer1.playerInteractManager.getGameMode()));
        BlockPosition blockposition2 = worldserver.getSpawn();
        entityplayer1.playerConnection.a(entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch);
        entityplayer1.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition2));
        entityplayer1.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer1.exp, entityplayer1.expTotal, entityplayer1.expLevel));
        this.b(entityplayer1, worldserver);
        this.f(entityplayer1);
        worldserver.getPlayerChunkMap().addPlayer(entityplayer1);
        worldserver.addEntity(entityplayer1);
        this.players.add(entityplayer1);
        this.j.put(entityplayer1.getUniqueID(), entityplayer1);
        entityplayer1.syncInventory();
        entityplayer1.setHealth(entityplayer1.getHealth());
        return entityplayer1;
    }

    public void f(EntityPlayer entityplayer) {
        GameProfile gameprofile = entityplayer.getProfile();
        int i = this.server.a(gameprofile);
        this.a(entityplayer, i);
    }

    public void a(EntityPlayer entityplayer, DimensionManager dimensionmanager) {
        DimensionManager dimensionmanager1 = entityplayer.dimension;
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        entityplayer.dimension = dimensionmanager;
        WorldServer worldserver1 = this.server.getWorldServer(entityplayer.dimension);
        entityplayer.playerConnection.sendPacket(new PacketPlayOutRespawn(entityplayer.dimension, entityplayer.world.getDifficulty(), entityplayer.world.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
        this.f(entityplayer);
        worldserver.removeEntity(entityplayer);
        entityplayer.dead = false;
        this.changeWorld(entityplayer, dimensionmanager1, worldserver, worldserver1);
        this.a(entityplayer, worldserver);
        entityplayer.playerConnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        entityplayer.playerInteractManager.a(worldserver1);
        entityplayer.playerConnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        this.b(entityplayer, worldserver1);
        this.updateClient(entityplayer);

        for(MobEffect mobeffect : entityplayer.getEffects()) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

    }

    public void changeWorld(Entity entity, DimensionManager dimensionmanager, WorldServer worldserver, WorldServer worldserver1) {
        double d0 = entity.locX;
        double d1 = entity.locZ;
        double d2 = 8.0D;
        float fx = entity.yaw;
        worldserver.methodProfiler.a("moving");
        if (entity.dimension == DimensionManager.NETHER) {
            d0 = MathHelper.a(d0 / 8.0D, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
            d1 = MathHelper.a(d1 / 8.0D, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else if (entity.dimension == DimensionManager.OVERWORLD) {
            d0 = MathHelper.a(d0 * 8.0D, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
            d1 = MathHelper.a(d1 * 8.0D, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else {
            BlockPosition blockposition;
            if (dimensionmanager == DimensionManager.THE_END) {
                blockposition = worldserver1.getSpawn();
            } else {
                blockposition = worldserver1.getDimensionSpawn();
            }

            d0 = (double)blockposition.getX();
            entity.locY = (double)blockposition.getY();
            d1 = (double)blockposition.getZ();
            entity.setPositionRotation(d0, entity.locY, d1, 90.0F, 0.0F);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        }

        worldserver.methodProfiler.e();
        if (dimensionmanager != DimensionManager.THE_END) {
            worldserver.methodProfiler.a("placing");
            d0 = (double)MathHelper.clamp((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.clamp((int)d1, -29999872, 29999872);
            if (entity.isAlive()) {
                entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
                worldserver1.getTravelAgent().a(entity, fx);
                worldserver1.addEntity(entity);
                worldserver1.entityJoinedWorld(entity, false);
            }

            worldserver.methodProfiler.e();
        }

        entity.spawnIn(worldserver1);
    }

    public void tick() {
        if (++this.v > 600) {
            this.sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, this.players));
            this.v = 0;
        }

    }

    public void sendAll(Packet<?> packet) {
        for(int i = 0; i < this.players.size(); ++i) {
            ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet);
        }

    }

    public void a(Packet<?> packet, DimensionManager dimensionmanager) {
        for(int i = 0; i < this.players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
            if (entityplayer.dimension == dimensionmanager) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }

    }

    public void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
        ScoreboardTeamBase scoreboardteambase = entityhuman.be();
        if (scoreboardteambase != null) {
            for(String sx : scoreboardteambase.getPlayerNameSet()) {
                EntityPlayer entityplayer = this.getPlayer(sx);
                if (entityplayer != null && entityplayer != entityhuman) {
                    entityplayer.sendMessage(ichatbasecomponent);
                }
            }

        }
    }

    public void b(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
        ScoreboardTeamBase scoreboardteambase = entityhuman.be();
        if (scoreboardteambase == null) {
            this.sendMessage(ichatbasecomponent);
        } else {
            for(int i = 0; i < this.players.size(); ++i) {
                EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
                if (entityplayer.be() != scoreboardteambase) {
                    entityplayer.sendMessage(ichatbasecomponent);
                }
            }

        }
    }

    public String[] f() {
        String[] astring = new String[this.players.size()];

        for(int i = 0; i < this.players.size(); ++i) {
            astring[i] = ((EntityPlayer)this.players.get(i)).getProfile().getName();
        }

        return astring;
    }

    public GameProfileBanList getProfileBans() {
        return this.k;
    }

    public IpBanList getIPBans() {
        return this.l;
    }

    public void addOp(GameProfile gameprofile) {
        this.operators.add(new OpListEntry(gameprofile, this.server.j(), this.operators.b(gameprofile)));
        EntityPlayer entityplayer = this.a(gameprofile.getId());
        if (entityplayer != null) {
            this.f(entityplayer);
        }

    }

    public void removeOp(GameProfile gameprofile) {
        this.operators.remove(gameprofile);
        EntityPlayer entityplayer = this.a(gameprofile.getId());
        if (entityplayer != null) {
            this.f(entityplayer);
        }

    }

    private void a(EntityPlayer entityplayer, int i) {
        if (entityplayer.playerConnection != null) {
            byte b0;
            if (i <= 0) {
                b0 = 24;
            } else if (i >= 4) {
                b0 = 28;
            } else {
                b0 = (byte)(24 + i);
            }

            entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, b0));
        }

        this.server.getCommandDispatcher().a(entityplayer);
    }

    public boolean isWhitelisted(GameProfile gameprofile) {
        return !this.hasWhitelist || this.operators.d(gameprofile) || this.whitelist.d(gameprofile);
    }

    public boolean isOp(GameProfile gameprofile) {
        return this.operators.d(gameprofile) || this.server.H() && this.server.getWorldServer(DimensionManager.OVERWORLD).getWorldData().u() && this.server.G().equalsIgnoreCase(gameprofile.getName()) || this.u;
    }

    @Nullable
    public EntityPlayer getPlayer(String sx) {
        for(EntityPlayer entityplayer : this.players) {
            if (entityplayer.getProfile().getName().equalsIgnoreCase(sx)) {
                return entityplayer;
            }
        }

        return null;
    }

    public void sendPacketNearby(@Nullable EntityHuman entityhuman, double d0, double d1, double d2, double d3, DimensionManager dimensionmanager, Packet<?> packet) {
        for(int i = 0; i < this.players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
            if (entityplayer != entityhuman && entityplayer.dimension == dimensionmanager) {
                double d4 = d0 - entityplayer.locX;
                double d5 = d1 - entityplayer.locY;
                double d6 = d2 - entityplayer.locZ;
                if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                    entityplayer.playerConnection.sendPacket(packet);
                }
            }
        }

    }

    public void savePlayers() {
        for(int i = 0; i < this.players.size(); ++i) {
            this.savePlayerFile((EntityPlayer)this.players.get(i));
        }

    }

    public WhiteList getWhitelist() {
        return this.whitelist;
    }

    public String[] getWhitelisted() {
        return this.whitelist.getEntries();
    }

    public OpList getOPs() {
        return this.operators;
    }

    public String[] n() {
        return this.operators.getEntries();
    }

    public void reloadWhitelist() {
    }

    public void b(EntityPlayer entityplayer, WorldServer worldserver) {
        WorldBorder worldborder = this.server.getWorldServer(DimensionManager.OVERWORLD).getWorldBorder();
        entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
        entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
        BlockPosition blockposition = worldserver.getSpawn();
        entityplayer.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition));
        if (worldserver.isRaining()) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0.0F));
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, worldserver.i(1.0F)));
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, worldserver.g(1.0F)));
        }

    }

    public void updateClient(EntityPlayer entityplayer) {
        entityplayer.updateInventory(entityplayer.defaultContainer);
        entityplayer.triggerHealthUpdate();
        entityplayer.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String[] getSeenPlayers() {
        return this.server.getWorldServer(DimensionManager.OVERWORLD).getDataManager().getPlayerFileData().getSeenPlayers();
    }

    public boolean getHasWhitelist() {
        return this.hasWhitelist;
    }

    public void setHasWhitelist(boolean flag) {
        this.hasWhitelist = flag;
    }

    public List<EntityPlayer> b(String sx) {
        ArrayList arraylist = Lists.newArrayList();

        for(EntityPlayer entityplayer : this.players) {
            if (entityplayer.v().equals(sx)) {
                arraylist.add(entityplayer);
            }
        }

        return arraylist;
    }

    public int s() {
        return this.s;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public NBTTagCompound t() {
        return null;
    }

    private void a(EntityPlayer entityplayer, EntityPlayer entityplayer1, GeneratorAccess generatoraccess) {
        if (entityplayer1 != null) {
            entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
        } else if (this.t != null) {
            entityplayer.playerInteractManager.setGameMode(this.t);
        }

        entityplayer.playerInteractManager.b(generatoraccess.getWorldData().getGameType());
    }

    public void u() {
        for(int i = 0; i < this.players.size(); ++i) {
            ((EntityPlayer)this.players.get(i)).playerConnection.disconnect(new ChatMessage("multiplayer.disconnect.server_shutdown", new Object[0]));
        }

    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag) {
        this.server.sendMessage(ichatbasecomponent);
        ChatMessageType chatmessagetype = flag ? ChatMessageType.SYSTEM : ChatMessageType.CHAT;
        this.sendAll(new PacketPlayOutChat(ichatbasecomponent, chatmessagetype));
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        this.sendMessage(ichatbasecomponent, true);
    }

    public ServerStatisticManager getStatisticManager(EntityHuman entityhuman) {
        UUID uuid = entityhuman.getUniqueID();
        ServerStatisticManager serverstatisticmanager = uuid == null ? null : (ServerStatisticManager)this.o.get(uuid);
        if (serverstatisticmanager == null) {
            File file1 = new File(this.server.getWorldServer(DimensionManager.OVERWORLD).getDataManager().getDirectory(), "stats");
            File file2 = new File(file1, uuid + ".json");
            if (!file2.exists()) {
                File file3 = new File(file1, entityhuman.getDisplayName().getString() + ".json");
                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }

            serverstatisticmanager = new ServerStatisticManager(this.server, file2);
            this.o.put(uuid, serverstatisticmanager);
        }

        return serverstatisticmanager;
    }

    public AdvancementDataPlayer h(EntityPlayer entityplayer) {
        UUID uuid = entityplayer.getUniqueID();
        AdvancementDataPlayer advancementdataplayer = (AdvancementDataPlayer)this.p.get(uuid);
        if (advancementdataplayer == null) {
            File file1 = new File(this.server.getWorldServer(DimensionManager.OVERWORLD).getDataManager().getDirectory(), "advancements");
            File file2 = new File(file1, uuid + ".json");
            advancementdataplayer = new AdvancementDataPlayer(this.server, file2, entityplayer);
            this.p.put(uuid, advancementdataplayer);
        }

        advancementdataplayer.a(entityplayer);
        return advancementdataplayer;
    }

    public void a(int i) {
        this.s = i;

        for(WorldServer worldserver : this.server.getWorlds()) {
            if (worldserver != null) {
                worldserver.getPlayerChunkMap().a(i);
                worldserver.getTracker().a(i);
            }
        }

    }

    public List<EntityPlayer> v() {
        return this.players;
    }

    @Nullable
    public EntityPlayer a(UUID uuid) {
        return (EntityPlayer)this.j.get(uuid);
    }

    public boolean f(GameProfile var1) {
        return false;
    }

    public void reload() {
        for(AdvancementDataPlayer advancementdataplayer : this.p.values()) {
            advancementdataplayer.b();
        }

        this.sendAll(new PacketPlayOutTags(this.server.getTagRegistry()));
        PacketPlayOutRecipeUpdate packetplayoutrecipeupdate = new PacketPlayOutRecipeUpdate(this.server.getCraftingManager().b());

        for(EntityPlayer entityplayer : this.players) {
            entityplayer.playerConnection.sendPacket(packetplayoutrecipeupdate);
            entityplayer.B().a(entityplayer);
        }

    }

    public boolean x() {
        return this.u;
    }
}
