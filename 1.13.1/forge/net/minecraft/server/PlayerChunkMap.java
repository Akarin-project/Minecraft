package net.minecraft.server;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class PlayerChunkMap {
    private static final Predicate<EntityPlayer> a = (entityplayer) -> {
        return entityplayer != null && !entityplayer.isSpectator();
    };
    private static final Predicate<EntityPlayer> b = (entityplayer) -> {
        return entityplayer != null && (!entityplayer.isSpectator() || entityplayer.getWorldServer().getGameRules().getBoolean("spectatorsGenerateChunks"));
    };
    private final WorldServer world;
    private final List<EntityPlayer> managedPlayers = Lists.newArrayList();
    private final Long2ObjectMap<PlayerChunk> e = new Long2ObjectOpenHashMap(4096);
    private final Set<PlayerChunk> f = Sets.newHashSet();
    private final List<PlayerChunk> g = Lists.newLinkedList();
    private final List<PlayerChunk> h = Lists.newLinkedList();
    private final List<PlayerChunk> i = Lists.newArrayList();
    private int j;
    private long k;
    private boolean l = true;
    private boolean m = true;

    public PlayerChunkMap(WorldServer worldserver) {
        this.world = worldserver;
        this.a(worldserver.getMinecraftServer().getPlayerList().s());
    }

    public WorldServer getWorld() {
        return this.world;
    }

    public Iterator<Chunk> b() {
        final Iterator iterator = this.i.iterator();
        return new AbstractIterator<Chunk>() {
            protected Chunk a() {
                while(true) {
                    if (iterator.hasNext()) {
                        PlayerChunk playerchunk = (PlayerChunk)iterator.next();
                        Chunk chunk = playerchunk.f();
                        if (chunk == null) {
                            continue;
                        }

                        if (!chunk.v()) {
                            return chunk;
                        }

                        if (!playerchunk.a(128.0D, PlayerChunkMap.a)) {
                            continue;
                        }

                        return chunk;
                    }

                    return (Chunk)this.endOfData();
                }
            }

            // $FF: synthetic method
            protected Object computeNext() {
                return this.a();
            }
        };
    }

    public void flush() {
        long ix = this.world.getTime();
        if (ix - this.k > 8000L) {
            this.k = ix;

            for(int jx = 0; jx < this.i.size(); ++jx) {
                PlayerChunk playerchunk = (PlayerChunk)this.i.get(jx);
                playerchunk.d();
                playerchunk.c();
            }
        }

        if (!this.f.isEmpty()) {
            for(PlayerChunk playerchunk2 : this.f) {
                playerchunk2.d();
            }

            this.f.clear();
        }

        if (this.l && ix % 4L == 0L) {
            this.l = false;
            Collections.sort(this.h, (playerchunk4, playerchunk5) -> {
                return ComparisonChain.start().compare(playerchunk4.g(), playerchunk5.g()).result();
            });
        }

        if (this.m && ix % 4L == 2L) {
            this.m = false;
            Collections.sort(this.g, (playerchunk4, playerchunk5) -> {
                return ComparisonChain.start().compare(playerchunk4.g(), playerchunk5.g()).result();
            });
        }

        if (!this.h.isEmpty()) {
            long lx = SystemUtils.c() + 50000000L;
            int kx = 49;
            Iterator iterator = this.h.iterator();

            while(iterator.hasNext()) {
                PlayerChunk playerchunk1 = (PlayerChunk)iterator.next();
                if (playerchunk1.f() == null) {
                    boolean flag = playerchunk1.a(b);
                    if (playerchunk1.a(flag)) {
                        iterator.remove();
                        if (playerchunk1.b()) {
                            this.g.remove(playerchunk1);
                        }

                        --kx;
                        if (kx < 0 || SystemUtils.c() > lx) {
                            break;
                        }
                    }
                }
            }
        }

        if (!this.g.isEmpty()) {
            int i1 = 81;
            Iterator iterator1 = this.g.iterator();

            while(iterator1.hasNext()) {
                PlayerChunk playerchunk3 = (PlayerChunk)iterator1.next();
                if (playerchunk3.b()) {
                    iterator1.remove();
                    --i1;
                    if (i1 < 0) {
                        break;
                    }
                }
            }
        }

        if (this.managedPlayers.isEmpty()) {
            WorldProvider worldprovider = this.world.worldProvider;
            if (!worldprovider.p()) {
                this.world.getChunkProviderServer().b();
            }
        }

    }

    public boolean a(int ix, int jx) {
        long kx = d(ix, jx);
        return this.e.get(kx) != null;
    }

    @Nullable
    public PlayerChunk getChunk(int ix, int jx) {
        return (PlayerChunk)this.e.get(d(ix, jx));
    }

    private PlayerChunk c(int ix, int jx) {
        long kx = d(ix, jx);
        PlayerChunk playerchunk = (PlayerChunk)this.e.get(kx);
        if (playerchunk == null) {
            playerchunk = new PlayerChunk(this, ix, jx);
            this.e.put(kx, playerchunk);
            this.i.add(playerchunk);
            if (playerchunk.f() == null) {
                this.h.add(playerchunk);
            }

            if (!playerchunk.b()) {
                this.g.add(playerchunk);
            }
        }

        return playerchunk;
    }

    public void flagDirty(BlockPosition blockposition) {
        int ix = blockposition.getX() >> 4;
        int jx = blockposition.getZ() >> 4;
        PlayerChunk playerchunk = this.getChunk(ix, jx);
        if (playerchunk != null) {
            playerchunk.a(blockposition.getX() & 15, blockposition.getY(), blockposition.getZ() & 15);
        }

    }

    public void addPlayer(EntityPlayer entityplayer) {
        int ix = (int)entityplayer.locX >> 4;
        int jx = (int)entityplayer.locZ >> 4;
        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;

        for(int kx = ix - this.j; kx <= ix + this.j; ++kx) {
            for(int lx = jx - this.j; lx <= jx + this.j; ++lx) {
                this.c(kx, lx).a(entityplayer);
            }
        }

        this.managedPlayers.add(entityplayer);
        this.e();
    }

    public void removePlayer(EntityPlayer entityplayer) {
        int ix = (int)entityplayer.d >> 4;
        int jx = (int)entityplayer.e >> 4;

        for(int kx = ix - this.j; kx <= ix + this.j; ++kx) {
            for(int lx = jx - this.j; lx <= jx + this.j; ++lx) {
                PlayerChunk playerchunk = this.getChunk(kx, lx);
                if (playerchunk != null) {
                    playerchunk.b(entityplayer);
                }
            }
        }

        this.managedPlayers.remove(entityplayer);
        this.e();
    }

    private boolean a(int ix, int jx, int kx, int lx, int i1) {
        int j1 = ix - kx;
        int k1 = jx - lx;
        if (j1 >= -i1 && j1 <= i1) {
            return k1 >= -i1 && k1 <= i1;
        } else {
            return false;
        }
    }

    public void movePlayer(EntityPlayer entityplayer) {
        int ix = (int)entityplayer.locX >> 4;
        int jx = (int)entityplayer.locZ >> 4;
        double d0 = entityplayer.d - entityplayer.locX;
        double d1 = entityplayer.e - entityplayer.locZ;
        double d2 = d0 * d0 + d1 * d1;
        if (!(d2 < 64.0D)) {
            int kx = (int)entityplayer.d >> 4;
            int lx = (int)entityplayer.e >> 4;
            int i1 = this.j;
            int j1 = ix - kx;
            int k1 = jx - lx;
            if (j1 != 0 || k1 != 0) {
                for(int l1 = ix - i1; l1 <= ix + i1; ++l1) {
                    for(int i2 = jx - i1; i2 <= jx + i1; ++i2) {
                        if (!this.a(l1, i2, kx, lx, i1)) {
                            this.c(l1, i2).a(entityplayer);
                        }

                        if (!this.a(l1 - j1, i2 - k1, ix, jx, i1)) {
                            PlayerChunk playerchunk = this.getChunk(l1 - j1, i2 - k1);
                            if (playerchunk != null) {
                                playerchunk.b(entityplayer);
                            }
                        }
                    }
                }

                entityplayer.d = entityplayer.locX;
                entityplayer.e = entityplayer.locZ;
                this.e();
            }
        }
    }

    public boolean a(EntityPlayer entityplayer, int ix, int jx) {
        PlayerChunk playerchunk = this.getChunk(ix, jx);
        return playerchunk != null && playerchunk.d(entityplayer) && playerchunk.e();
    }

    public void a(int ix) {
        ix = MathHelper.clamp(ix, 3, 32);
        if (ix != this.j) {
            int jx = ix - this.j;

            for(EntityPlayer entityplayer : Lists.newArrayList(this.managedPlayers)) {
                int kx = (int)entityplayer.locX >> 4;
                int lx = (int)entityplayer.locZ >> 4;
                if (jx > 0) {
                    for(int k1 = kx - ix; k1 <= kx + ix; ++k1) {
                        for(int l1 = lx - ix; l1 <= lx + ix; ++l1) {
                            PlayerChunk playerchunk = this.c(k1, l1);
                            if (!playerchunk.d(entityplayer)) {
                                playerchunk.a(entityplayer);
                            }
                        }
                    }
                } else {
                    for(int i1 = kx - this.j; i1 <= kx + this.j; ++i1) {
                        for(int j1 = lx - this.j; j1 <= lx + this.j; ++j1) {
                            if (!this.a(i1, j1, kx, lx, ix)) {
                                this.c(i1, j1).b(entityplayer);
                            }
                        }
                    }
                }
            }

            this.j = ix;
            this.e();
        }
    }

    private void e() {
        this.l = true;
        this.m = true;
    }

    public static int getFurthestViewableBlock(int ix) {
        return ix * 16 - 16;
    }

    private static long d(int ix, int jx) {
        return (long)ix + 2147483647L | (long)jx + 2147483647L << 32;
    }

    public void a(PlayerChunk playerchunk) {
        this.f.add(playerchunk);
    }

    public void b(PlayerChunk playerchunk) {
        ChunkCoordIntPair chunkcoordintpair = playerchunk.a();
        long ix = d(chunkcoordintpair.x, chunkcoordintpair.z);
        playerchunk.c();
        this.e.remove(ix);
        this.i.remove(playerchunk);
        this.f.remove(playerchunk);
        this.g.remove(playerchunk);
        this.h.remove(playerchunk);
        Chunk chunk = playerchunk.f();
        if (chunk != null) {
            this.getWorld().getChunkProviderServer().unload(chunk);
        }

    }
}
