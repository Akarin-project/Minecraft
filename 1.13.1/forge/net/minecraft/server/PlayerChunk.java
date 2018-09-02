package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerChunk {
    private static final Logger a = LogManager.getLogger();
    private final PlayerChunkMap playerChunkMap;
    private final List<EntityPlayer> c = Lists.newArrayList();
    private final ChunkCoordIntPair location;
    private final short[] dirtyBlocks = new short[64];
    @Nullable
    private Chunk chunk;
    private int dirtyCount;
    private int h;
    private long i;
    private boolean done;

    public PlayerChunk(PlayerChunkMap playerchunkmap, int ix, int j) {
        this.playerChunkMap = playerchunkmap;
        this.location = new ChunkCoordIntPair(ix, j);
        ChunkProviderServer chunkproviderserver = playerchunkmap.getWorld().getChunkProviderServer();
        chunkproviderserver.a(ix, j);
        this.chunk = chunkproviderserver.getChunkAt(ix, j, true, false);
    }

    public ChunkCoordIntPair a() {
        return this.location;
    }

    public void a(EntityPlayer entityplayer) {
        if (this.c.contains(entityplayer)) {
            a.debug("Failed to add player. {} already is in chunk {}, {}", entityplayer, this.location.x, this.location.z);
        } else {
            if (this.c.isEmpty()) {
                this.i = this.playerChunkMap.getWorld().getTime();
            }

            this.c.add(entityplayer);
            if (this.done) {
                this.sendChunk(entityplayer);
            }

        }
    }

    public void b(EntityPlayer entityplayer) {
        if (this.c.contains(entityplayer)) {
            if (this.done) {
                entityplayer.playerConnection.sendPacket(new PacketPlayOutUnloadChunk(this.location.x, this.location.z));
            }

            this.c.remove(entityplayer);
            if (this.c.isEmpty()) {
                this.playerChunkMap.b(this);
            }

        }
    }

    public boolean a(boolean flag) {
        if (this.chunk != null) {
            return true;
        } else {
            this.chunk = this.playerChunkMap.getWorld().getChunkProviderServer().getChunkAt(this.location.x, this.location.z, true, flag);
            return this.chunk != null;
        }
    }

    public boolean b() {
        if (this.done) {
            return true;
        } else if (this.chunk == null) {
            return false;
        } else if (!this.chunk.isReady()) {
            return false;
        } else {
            this.dirtyCount = 0;
            this.h = 0;
            this.done = true;
            if (!this.c.isEmpty()) {
                PacketPlayOutMapChunk packetplayoutmapchunk = new PacketPlayOutMapChunk(this.chunk, 65535);

                for(EntityPlayer entityplayer : this.c) {
                    entityplayer.playerConnection.sendPacket(packetplayoutmapchunk);
                    this.playerChunkMap.getWorld().getTracker().a(entityplayer, this.chunk);
                }
            }

            return true;
        }
    }

    public void sendChunk(EntityPlayer entityplayer) {
        if (this.done) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(this.chunk, 65535));
            this.playerChunkMap.getWorld().getTracker().a(entityplayer, this.chunk);
        }
    }

    public void c() {
        long ix = this.playerChunkMap.getWorld().getTime();
        if (this.chunk != null) {
            this.chunk.b(this.chunk.m() + ix - this.i);
        }

        this.i = ix;
    }

    public void a(int ix, int j, int k) {
        if (this.done) {
            if (this.dirtyCount == 0) {
                this.playerChunkMap.a(this);
            }

            this.h |= 1 << (j >> 4);
            if (this.dirtyCount < 64) {
                short short1 = (short)(ix << 12 | k << 8 | j);

                for(int l = 0; l < this.dirtyCount; ++l) {
                    if (this.dirtyBlocks[l] == short1) {
                        return;
                    }
                }

                this.dirtyBlocks[this.dirtyCount++] = short1;
            }

        }
    }

    public void a(Packet<?> packet) {
        if (this.done) {
            for(int ix = 0; ix < this.c.size(); ++ix) {
                ((EntityPlayer)this.c.get(ix)).playerConnection.sendPacket(packet);
            }

        }
    }

    public void d() {
        if (this.done && this.chunk != null) {
            if (this.dirtyCount != 0) {
                if (this.dirtyCount == 1) {
                    int ix = (this.dirtyBlocks[0] >> 12 & 15) + this.location.x * 16;
                    int j = this.dirtyBlocks[0] & 255;
                    int k = (this.dirtyBlocks[0] >> 8 & 15) + this.location.z * 16;
                    BlockPosition blockposition = new BlockPosition(ix, j, k);
                    this.a(new PacketPlayOutBlockChange(this.playerChunkMap.getWorld(), blockposition));
                    if (this.playerChunkMap.getWorld().getType(blockposition).getBlock().isTileEntity()) {
                        this.a(this.playerChunkMap.getWorld().getTileEntity(blockposition));
                    }
                } else if (this.dirtyCount == 64) {
                    this.a(new PacketPlayOutMapChunk(this.chunk, this.h));
                } else {
                    this.a(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, this.chunk));

                    for(int l = 0; l < this.dirtyCount; ++l) {
                        int i1 = (this.dirtyBlocks[l] >> 12 & 15) + this.location.x * 16;
                        int j1 = this.dirtyBlocks[l] & 255;
                        int k1 = (this.dirtyBlocks[l] >> 8 & 15) + this.location.z * 16;
                        BlockPosition blockposition1 = new BlockPosition(i1, j1, k1);
                        if (this.playerChunkMap.getWorld().getType(blockposition1).getBlock().isTileEntity()) {
                            this.a(this.playerChunkMap.getWorld().getTileEntity(blockposition1));
                        }
                    }
                }

                this.dirtyCount = 0;
                this.h = 0;
            }
        }
    }

    private void a(@Nullable TileEntity tileentity) {
        if (tileentity != null) {
            PacketPlayOutTileEntityData packetplayouttileentitydata = tileentity.getUpdatePacket();
            if (packetplayouttileentitydata != null) {
                this.a(packetplayouttileentitydata);
            }
        }

    }

    public boolean d(EntityPlayer entityplayer) {
        return this.c.contains(entityplayer);
    }

    public boolean a(Predicate<EntityPlayer> predicate) {
        return this.c.stream().anyMatch(predicate);
    }

    public boolean a(double d0, Predicate<EntityPlayer> predicate) {
        int ix = 0;

        for(int j = this.c.size(); ix < j; ++ix) {
            EntityPlayer entityplayer = (EntityPlayer)this.c.get(ix);
            if (predicate.test(entityplayer) && this.location.a(entityplayer) < d0 * d0) {
                return true;
            }
        }

        return false;
    }

    public boolean e() {
        return this.done;
    }

    @Nullable
    public Chunk f() {
        return this.chunk;
    }

    public double g() {
        double d0 = Double.MAX_VALUE;

        for(EntityPlayer entityplayer : this.c) {
            double d1 = this.location.a(entityplayer);
            if (d1 < d0) {
                d0 = d1;
            }
        }

        return d0;
    }
}
