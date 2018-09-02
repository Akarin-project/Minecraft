package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoChunk implements IChunkAccess {
    private static final Logger a = LogManager.getLogger();
    private final ChunkCoordIntPair b;
    private boolean c;
    private final AtomicInteger d;
    private BiomeBase[] e;
    private final Map<HeightMap.Type, HeightMap> f;
    private volatile ChunkStatus g;
    private final Map<BlockPosition, TileEntity> h;
    private final Map<BlockPosition, NBTTagCompound> i;
    private final ChunkSection[] j;
    private final List<NBTTagCompound> k;
    private final List<BlockPosition> l;
    private final ShortList[] m;
    private final Map<String, StructureStart> n;
    private final Map<String, LongSet> o;
    private final ChunkConverter p;
    private final ProtoChunkTickList<Block> q;
    private final ProtoChunkTickList<FluidType> r;
    private long s;
    private final Map<WorldGenStage.Features, BitSet> t;
    private boolean u;

    public ProtoChunk(int ix, int jx, ChunkConverter chunkconverter) {
        this(new ChunkCoordIntPair(ix, jx), chunkconverter);
    }

    public ProtoChunk(ChunkCoordIntPair chunkcoordintpair, ChunkConverter chunkconverter) {
        this.d = new AtomicInteger();
        this.f = Maps.newEnumMap(HeightMap.Type.class);
        this.g = ChunkStatus.EMPTY;
        this.h = Maps.newHashMap();
        this.i = Maps.newHashMap();
        this.j = new ChunkSection[16];
        this.k = Lists.newArrayList();
        this.l = Lists.newArrayList();
        this.m = new ShortList[16];
        this.n = Maps.newHashMap();
        this.o = Maps.newHashMap();
        this.t = Maps.newHashMap();
        this.b = chunkcoordintpair;
        this.p = chunkconverter;
        this.q = new ProtoChunkTickList<Block>((block) -> {
            return block == null || block.getBlockData().isAir();
        }, IRegistry.BLOCK::getKey, IRegistry.BLOCK::getOrDefault, chunkcoordintpair);
        this.r = new ProtoChunkTickList<FluidType>((fluidtype) -> {
            return fluidtype == null || fluidtype == FluidTypes.a;
        }, IRegistry.FLUID::getKey, IRegistry.FLUID::getOrDefault, chunkcoordintpair);
    }

    public static ShortList a(ShortList[] ashortlist, int ix) {
        if (ashortlist[ix] == null) {
            ashortlist[ix] = new ShortArrayList();
        }

        return ashortlist[ix];
    }

    @Nullable
    public IBlockData getType(BlockPosition blockposition) {
        int ix = blockposition.getX();
        int jx = blockposition.getY();
        int kx = blockposition.getZ();
        if (jx >= 0 && jx < 256) {
            return this.j[jx >> 4] == Chunk.a ? Blocks.AIR.getBlockData() : this.j[jx >> 4].getType(ix & 15, jx & 15, kx & 15);
        } else {
            return Blocks.VOID_AIR.getBlockData();
        }
    }

    public Fluid b(BlockPosition blockposition) {
        int ix = blockposition.getX();
        int jx = blockposition.getY();
        int kx = blockposition.getZ();
        return jx >= 0 && jx < 256 && this.j[jx >> 4] != Chunk.a ? this.j[jx >> 4].b(ix & 15, jx & 15, kx & 15) : FluidTypes.a.i();
    }

    public List<BlockPosition> j() {
        return this.l;
    }

    public ShortList[] p() {
        ShortList[] ashortlist = new ShortList[16];

        for(BlockPosition blockposition : this.l) {
            a(ashortlist, blockposition.getY() >> 4).add(i(blockposition));
        }

        return ashortlist;
    }

    public void a(short short1, int ix) {
        this.h(a(short1, ix, this.b));
    }

    public void h(BlockPosition blockposition) {
        this.l.add(blockposition);
    }

    @Nullable
    public IBlockData a(BlockPosition blockposition, IBlockData iblockdata, boolean var3) {
        int ix = blockposition.getX();
        int jx = blockposition.getY();
        int kx = blockposition.getZ();
        if (jx >= 0 && jx < 256) {
            if (iblockdata.e() > 0) {
                this.l.add(new BlockPosition((ix & 15) + this.getPos().d(), jx, (kx & 15) + this.getPos().e()));
            }

            if (this.j[jx >> 4] == Chunk.a) {
                if (iblockdata.getBlock() == Blocks.AIR) {
                    return iblockdata;
                }

                this.j[jx >> 4] = new ChunkSection(jx >> 4 << 4, this.x());
            }

            IBlockData iblockdata1 = this.j[jx >> 4].getType(ix & 15, jx & 15, kx & 15);
            this.j[jx >> 4].setType(ix & 15, jx & 15, kx & 15, iblockdata);
            if (this.u) {
                this.c(HeightMap.Type.MOTION_BLOCKING).a(ix & 15, jx, kx & 15, iblockdata);
                this.c(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES).a(ix & 15, jx, kx & 15, iblockdata);
                this.c(HeightMap.Type.OCEAN_FLOOR).a(ix & 15, jx, kx & 15, iblockdata);
                this.c(HeightMap.Type.WORLD_SURFACE).a(ix & 15, jx, kx & 15, iblockdata);
            }

            return iblockdata1;
        } else {
            return Blocks.VOID_AIR.getBlockData();
        }
    }

    public void a(BlockPosition blockposition, TileEntity tileentity) {
        tileentity.setPosition(blockposition);
        this.h.put(blockposition, tileentity);
    }

    public Set<BlockPosition> q() {
        HashSet hashset = Sets.newHashSet(this.i.keySet());
        hashset.addAll(this.h.keySet());
        return hashset;
    }

    @Nullable
    public TileEntity getTileEntity(BlockPosition blockposition) {
        return (TileEntity)this.h.get(blockposition);
    }

    public Map<BlockPosition, TileEntity> r() {
        return this.h;
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.k.add(nbttagcompound);
    }

    public void a(Entity entity) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        entity.d(nbttagcompound);
        this.b(nbttagcompound);
    }

    public List<NBTTagCompound> s() {
        return this.k;
    }

    public void a(BiomeBase[] abiomebase) {
        this.e = abiomebase;
    }

    public BiomeBase[] getBiomeIndex() {
        return this.e;
    }

    public void a(boolean flag) {
        this.c = flag;
    }

    public boolean h() {
        return this.c;
    }

    public ChunkStatus i() {
        return this.g;
    }

    public void a(ChunkStatus chunkstatus) {
        this.g = chunkstatus;
        this.a(true);
    }

    public void c(String sx) {
        this.a(ChunkStatus.a(sx));
    }

    public ChunkSection[] getSections() {
        return this.j;
    }

    public int a(EnumSkyBlock enumskyblock, BlockPosition blockposition, boolean flag) {
        int ix = blockposition.getX() & 15;
        int jx = blockposition.getY();
        int kx = blockposition.getZ() & 15;
        int lx = jx >> 4;
        if (lx >= 0 && lx <= this.j.length - 1) {
            ChunkSection chunksection = this.j[lx];
            if (chunksection == Chunk.a) {
                return this.c(blockposition) ? enumskyblock.c : 0;
            } else if (enumskyblock == EnumSkyBlock.SKY) {
                return !flag ? 0 : chunksection.c(ix, jx & 15, kx);
            } else {
                return enumskyblock == EnumSkyBlock.BLOCK ? chunksection.d(ix, jx & 15, kx) : enumskyblock.c;
            }
        } else {
            return 0;
        }
    }

    public int a(BlockPosition blockposition, int ix, boolean flag) {
        int jx = blockposition.getX() & 15;
        int kx = blockposition.getY();
        int lx = blockposition.getZ() & 15;
        int i1 = kx >> 4;
        if (i1 >= 0 && i1 <= this.j.length - 1) {
            ChunkSection chunksection = this.j[i1];
            if (chunksection == Chunk.a) {
                return this.x() && ix < EnumSkyBlock.SKY.c ? EnumSkyBlock.SKY.c - ix : 0;
            } else {
                int j1 = flag ? chunksection.c(jx, kx & 15, lx) : 0;
                j1 = j1 - ix;
                int k1 = chunksection.d(jx, kx & 15, lx);
                if (k1 > j1) {
                    j1 = k1;
                }

                return j1;
            }
        } else {
            return 0;
        }
    }

    public boolean c(BlockPosition blockposition) {
        int ix = blockposition.getX() & 15;
        int jx = blockposition.getY();
        int kx = blockposition.getZ() & 15;
        return jx >= this.a(HeightMap.Type.MOTION_BLOCKING, ix, kx);
    }

    public void a(ChunkSection[] achunksection) {
        if (this.j.length != achunksection.length) {
            a.warn("Could not set level chunk sections, array length is {} instead of {}", achunksection.length, this.j.length);
        } else {
            System.arraycopy(achunksection, 0, this.j, 0, this.j.length);
        }
    }

    public Set<HeightMap.Type> t() {
        return this.f.keySet();
    }

    @Nullable
    public HeightMap b(HeightMap.Type heightmap$type) {
        return (HeightMap)this.f.get(heightmap$type);
    }

    public void a(HeightMap.Type heightmap$type, long[] along) {
        this.c(heightmap$type).a(along);
    }

    public void a(HeightMap.Type... aheightmap$type) {
        for(HeightMap.Type heightmap$type : aheightmap$type) {
            this.c(heightmap$type);
        }

    }

    private HeightMap c(HeightMap.Type heightmap$type) {
        return (HeightMap)this.f.computeIfAbsent(heightmap$type, (heightmap$type1) -> {
            HeightMap heightmap = new HeightMap(this, heightmap$type1);
            heightmap.a();
            return heightmap;
        });
    }

    public int a(HeightMap.Type heightmap$type, int ix, int jx) {
        HeightMap heightmap = (HeightMap)this.f.get(heightmap$type);
        if (heightmap == null) {
            this.a(heightmap$type);
            heightmap = (HeightMap)this.f.get(heightmap$type);
        }

        return heightmap.a(ix & 15, jx & 15) - 1;
    }

    public ChunkCoordIntPair getPos() {
        return this.b;
    }

    public void setLastSaved(long var1) {
    }

    @Nullable
    public StructureStart a(String sx) {
        return (StructureStart)this.n.get(sx);
    }

    public void a(String sx, StructureStart structurestart) {
        this.n.put(sx, structurestart);
        this.c = true;
    }

    public Map<String, StructureStart> e() {
        return Collections.unmodifiableMap(this.n);
    }

    public void a(Map<String, StructureStart> map) {
        this.n.clear();
        this.n.putAll(map);
        this.c = true;
    }

    @Nullable
    public LongSet b(String sx) {
        return (LongSet)this.o.computeIfAbsent(sx, (var0) -> {
            return new LongOpenHashSet();
        });
    }

    public void a(String sx, long ix) {
        ((LongSet)this.o.computeIfAbsent(sx, (var0) -> {
            return new LongOpenHashSet();
        })).add(ix);
        this.c = true;
    }

    public Map<String, LongSet> f() {
        return Collections.unmodifiableMap(this.o);
    }

    public void b(Map<String, LongSet> map) {
        this.o.clear();
        this.o.putAll(map);
        this.c = true;
    }

    public void a(EnumSkyBlock enumskyblock, boolean flag, BlockPosition blockposition, int ix) {
        int jx = blockposition.getX() & 15;
        int kx = blockposition.getY();
        int lx = blockposition.getZ() & 15;
        int i1 = kx >> 4;
        if (i1 < 16 && i1 >= 0) {
            if (this.j[i1] == Chunk.a) {
                if (ix == enumskyblock.c) {
                    return;
                }

                this.j[i1] = new ChunkSection(i1 << 4, this.x());
            }

            if (enumskyblock == EnumSkyBlock.SKY) {
                if (flag) {
                    this.j[i1].a(jx, kx & 15, lx, ix);
                }
            } else if (enumskyblock == EnumSkyBlock.BLOCK) {
                this.j[i1].b(jx, kx & 15, lx, ix);
            }

        }
    }

    public static short i(BlockPosition blockposition) {
        int ix = blockposition.getX();
        int jx = blockposition.getY();
        int kx = blockposition.getZ();
        int lx = ix & 15;
        int i1 = jx & 15;
        int j1 = kx & 15;
        return (short)(lx | i1 << 4 | j1 << 8);
    }

    public static BlockPosition a(short short1, int ix, ChunkCoordIntPair chunkcoordintpair) {
        int jx = (short1 & 15) + (chunkcoordintpair.x << 4);
        int kx = (short1 >>> 4 & 15) + (ix << 4);
        int lx = (short1 >>> 8 & 15) + (chunkcoordintpair.z << 4);
        return new BlockPosition(jx, kx, lx);
    }

    public void e(BlockPosition blockposition) {
        a(this.m, blockposition.getY() >> 4).add(i(blockposition));
    }

    public ShortList[] u() {
        return this.m;
    }

    public void b(short short1, int ix) {
        a(this.m, ix).add(short1);
    }

    public ProtoChunkTickList<Block> n() {
        return this.q;
    }

    public ProtoChunkTickList<FluidType> o() {
        return this.r;
    }

    private boolean x() {
        return true;
    }

    public ChunkConverter v() {
        return this.p;
    }

    public void b(long ix) {
        this.s = ix;
    }

    public long m() {
        return this.s;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.i.put(new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z")), nbttagcompound);
    }

    public Map<BlockPosition, NBTTagCompound> w() {
        return Collections.unmodifiableMap(this.i);
    }

    public NBTTagCompound g(BlockPosition blockposition) {
        return (NBTTagCompound)this.i.get(blockposition);
    }

    public void d(BlockPosition blockposition) {
        this.h.remove(blockposition);
        this.i.remove(blockposition);
    }

    public BitSet a(WorldGenStage.Features worldgenstage$features) {
        return (BitSet)this.t.computeIfAbsent(worldgenstage$features, (var0) -> {
            return new BitSet(65536);
        });
    }

    public void a(WorldGenStage.Features worldgenstage$features, BitSet bitset) {
        this.t.put(worldgenstage$features, bitset);
    }

    public void a(int ix) {
        this.d.addAndGet(ix);
    }

    public boolean ab_() {
        return this.d.get() > 0;
    }

    public void b(boolean flag) {
        this.u = flag;
    }

    // $FF: synthetic method
    public TickList l() {
        return this.o();
    }

    // $FF: synthetic method
    public TickList k() {
        return this.n();
    }
}
