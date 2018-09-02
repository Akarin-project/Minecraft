package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk implements IChunkAccess {
    private static final Logger d = LogManager.getLogger();
    public static final ChunkSection a = null;
    private final ChunkSection[] sections;
    private final BiomeBase[] f;
    private final boolean[] g;
    private final Map<BlockPosition, NBTTagCompound> h;
    private boolean i;
    public final World world;
    public final Map<HeightMap.Type, HeightMap> heightMap;
    public final int locX;
    public final int locZ;
    private boolean l;
    private final ChunkConverter m;
    public final Map<BlockPosition, TileEntity> tileEntities;
    public final EntitySlice<Entity>[] entitySlices;
    private final Map<String, StructureStart> p;
    private final Map<String, LongSet> q;
    private final ShortList[] r;
    private final TickList<Block> s;
    private final TickList<FluidType> t;
    private boolean u;
    private boolean v;
    private long lastSaved;
    private boolean x;
    private int y;
    private long z;
    private int A;
    private final ConcurrentLinkedQueue<BlockPosition> B;
    private ChunkStatus C;
    private int D;
    private final AtomicInteger E;

    public Chunk(World worldx, int ix, int j, BiomeBase[] abiomebase, ChunkConverter chunkconverter, TickList<Block> ticklist, TickList<FluidType> ticklist1, long k) {
        this.sections = new ChunkSection[16];
        this.g = new boolean[256];
        this.h = Maps.newHashMap();
        this.heightMap = Maps.newHashMap();
        this.tileEntities = Maps.newHashMap();
        this.p = Maps.newHashMap();
        this.q = Maps.newHashMap();
        this.r = new ShortList[16];
        this.A = 4096;
        this.B = Queues.newConcurrentLinkedQueue();
        this.C = ChunkStatus.EMPTY;
        this.E = new AtomicInteger();
        this.entitySlices = new EntitySlice[16];
        this.world = worldx;
        this.locX = ix;
        this.locZ = j;
        this.m = chunkconverter;

        for(HeightMap.Type heightmap$type : HeightMap.Type.values()) {
            if (heightmap$type.c() == HeightMap.Use.LIVE_WORLD) {
                this.heightMap.put(heightmap$type, new HeightMap(this, heightmap$type));
            }
        }

        for(int lx = 0; lx < this.entitySlices.length; ++lx) {
            this.entitySlices[lx] = new EntitySlice(Entity.class);
        }

        this.f = abiomebase;
        this.s = ticklist;
        this.t = ticklist1;
        this.z = k;
    }

    public Chunk(World worldx, ProtoChunk protochunk, int ix, int j) {
        this(worldx, ix, j, protochunk.getBiomeIndex(), protochunk.v(), protochunk.n(), protochunk.o(), protochunk.m());

        for(int k = 0; k < this.sections.length; ++k) {
            this.sections[k] = protochunk.getSections()[k];
        }

        for(NBTTagCompound nbttagcompound : protochunk.s()) {
            ChunkRegionLoader.a(nbttagcompound, worldx, this);
        }

        for(TileEntity tileentity : protochunk.r().values()) {
            this.a(tileentity);
        }

        this.h.putAll(protochunk.w());

        for(int lx = 0; lx < protochunk.u().length; ++lx) {
            this.r[lx] = protochunk.u()[lx];
        }

        this.a(protochunk.e());
        this.b(protochunk.f());

        for(HeightMap.Type heightmap$type : protochunk.t()) {
            if (heightmap$type.c() == HeightMap.Use.LIVE_WORLD) {
                ((HeightMap)this.heightMap.computeIfAbsent(heightmap$type, (heightmap$type1) -> {
                    return new HeightMap(this, heightmap$type1);
                })).a(protochunk.b(heightmap$type).b());
            }
        }

        this.x = true;
        this.a(ChunkStatus.FULLCHUNK);
    }

    public Set<BlockPosition> t() {
        HashSet hashset = Sets.newHashSet(this.h.keySet());
        hashset.addAll(this.tileEntities.keySet());
        return hashset;
    }

    public boolean a(int ix, int j) {
        return ix == this.locX && j == this.locZ;
    }

    public ChunkSection[] getSections() {
        return this.sections;
    }

    public void initLighting() {
        int ix = this.b();
        this.y = Integer.MAX_VALUE;

        for(HeightMap heightmap : this.heightMap.values()) {
            heightmap.a();
        }

        for(int i1 = 0; i1 < 16; ++i1) {
            for(int j1 = 0; j1 < 16; ++j1) {
                if (this.world.worldProvider.g()) {
                    int j = 15;
                    int k = ix + 16 - 1;

                    while(true) {
                        int lx = this.d(i1, k, j1);
                        if (lx == 0 && j != 15) {
                            lx = 1;
                        }

                        j -= lx;
                        if (j > 0) {
                            ChunkSection chunksection = this.sections[k >> 4];
                            if (chunksection != a) {
                                chunksection.a(i1, k & 15, j1, j);
                                this.world.m(new BlockPosition((this.locX << 4) + i1, k, (this.locZ << 4) + j1));
                            }
                        }

                        --k;
                        if (k <= 0 || j <= 0) {
                            break;
                        }
                    }
                }
            }
        }

        this.x = true;
    }

    private void c(int ix, int j) {
        this.g[ix + j * 16] = true;
        this.l = true;
    }

    private void g(boolean flag) {
        this.world.methodProfiler.a("recheckGaps");
        if (this.world.areChunksLoaded(new BlockPosition(this.locX * 16 + 8, 0, this.locZ * 16 + 8), 16)) {
            for(int ix = 0; ix < 16; ++ix) {
                for(int j = 0; j < 16; ++j) {
                    if (this.g[ix + j * 16]) {
                        this.g[ix + j * 16] = false;
                        int k = this.a(HeightMap.Type.LIGHT_BLOCKING, ix, j);
                        int lx = this.locX * 16 + ix;
                        int i1 = this.locZ * 16 + j;
                        int j1 = Integer.MAX_VALUE;

                        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                            j1 = Math.min(j1, this.world.d(lx + enumdirection.getAdjacentX(), i1 + enumdirection.getAdjacentZ()));
                        }

                        this.c(lx, i1, j1);

                        for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                            this.c(lx + enumdirection1.getAdjacentX(), i1 + enumdirection1.getAdjacentZ(), k);
                        }

                        if (flag) {
                            this.world.methodProfiler.e();
                            return;
                        }
                    }
                }
            }

            this.l = false;
        }

        this.world.methodProfiler.e();
    }

    private void c(int ix, int j, int k) {
        int lx = this.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, new BlockPosition(ix, 0, j)).getY();
        if (lx > k) {
            this.a(ix, j, k, lx + 1);
        } else if (lx < k) {
            this.a(ix, j, lx, k + 1);
        }

    }

    private void a(int ix, int j, int k, int lx) {
        if (lx > k && this.world.areChunksLoaded(new BlockPosition(ix, 0, j), 16)) {
            for(int i1 = k; i1 < lx; ++i1) {
                this.world.c(EnumSkyBlock.SKY, new BlockPosition(ix, i1, j));
            }

            this.x = true;
        }

    }

    private void a(int ix, int j, int k, IBlockData iblockdata) {
        HeightMap heightmap = (HeightMap)this.heightMap.get(HeightMap.Type.LIGHT_BLOCKING);
        int lx = heightmap.a(ix & 15, k & 15) & 255;
        if (heightmap.a(ix, j, k, iblockdata)) {
            int i1 = heightmap.a(ix & 15, k & 15);
            int j1 = this.locX * 16 + ix;
            int k1 = this.locZ * 16 + k;
            this.world.a(j1, k1, i1, lx);
            if (this.world.worldProvider.g()) {
                int l1 = Math.min(lx, i1);
                int i2 = Math.max(lx, i1);
                int j2 = i1 < lx ? 15 : 0;

                for(int k2 = l1; k2 < i2; ++k2) {
                    ChunkSection chunksection = this.sections[k2 >> 4];
                    if (chunksection != a) {
                        chunksection.a(ix, k2 & 15, k, j2);
                        this.world.m(new BlockPosition((this.locX << 4) + ix, k2, (this.locZ << 4) + k));
                    }
                }

                int k3 = 15;

                while(i1 > 0 && k3 > 0) {
                    --i1;
                    int l3 = this.d(ix, i1, k);
                    l3 = l3 == 0 ? 1 : l3;
                    k3 = k3 - l3;
                    k3 = Math.max(0, k3);
                    ChunkSection chunksection1 = this.sections[i1 >> 4];
                    if (chunksection1 != a) {
                        chunksection1.a(ix, i1 & 15, k, k3);
                    }
                }
            }

            if (i1 < this.y) {
                this.y = i1;
            }

            if (this.world.worldProvider.g()) {
                int l2 = heightmap.a(ix & 15, k & 15);
                int i3 = Math.min(lx, l2);
                int j3 = Math.max(lx, l2);

                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    this.a(j1 + enumdirection.getAdjacentX(), k1 + enumdirection.getAdjacentZ(), i3, j3);
                }

                this.a(j1, k1, i3, j3);
            }

            this.x = true;
        }
    }

    private int d(int ix, int j, int k) {
        return this.getBlockData(ix, j, k).b(this.world, new BlockPosition(ix, j, k));
    }

    public IBlockData getType(BlockPosition blockposition) {
        return this.getBlockData(blockposition.getX(), blockposition.getY(), blockposition.getZ());
    }

    public IBlockData getBlockData(int ix, int j, int k) {
        if (this.world.S() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            IBlockData iblockdata = null;
            if (j == 60) {
                iblockdata = Blocks.BARRIER.getBlockData();
            }

            if (j == 70) {
                iblockdata = ChunkProviderDebug.b(ix, k);
            }

            return iblockdata == null ? Blocks.AIR.getBlockData() : iblockdata;
        } else {
            try {
                if (j >= 0 && j >> 4 < this.sections.length) {
                    ChunkSection chunksection = this.sections[j >> 4];
                    if (chunksection != a) {
                        return chunksection.getType(ix & 15, j & 15, k & 15);
                    }
                }

                return Blocks.AIR.getBlockData();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Getting block state");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
                crashreportsystemdetails.a("Location", () -> {
                    return CrashReportSystemDetails.a(ix, j, k);
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    public Fluid b(BlockPosition blockposition) {
        return this.b(blockposition.getX(), blockposition.getY(), blockposition.getZ());
    }

    public Fluid b(int ix, int j, int k) {
        try {
            if (j >= 0 && j >> 4 < this.sections.length) {
                ChunkSection chunksection = this.sections[j >> 4];
                if (chunksection != a) {
                    return chunksection.b(ix & 15, j & 15, k & 15);
                }
            }

            return FluidTypes.a.i();
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Getting fluid state");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
            crashreportsystemdetails.a("Location", () -> {
                return CrashReportSystemDetails.a(ix, j, k);
            });
            throw new ReportedException(crashreport);
        }
    }

    @Nullable
    public IBlockData a(BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
        int ix = blockposition.getX() & 15;
        int j = blockposition.getY();
        int k = blockposition.getZ() & 15;
        int lx = ((HeightMap)this.heightMap.get(HeightMap.Type.LIGHT_BLOCKING)).a(ix, k);
        IBlockData iblockdata1 = this.getType(blockposition);
        if (iblockdata1 == iblockdata) {
            return null;
        } else {
            Block block = iblockdata.getBlock();
            Block block1 = iblockdata1.getBlock();
            ChunkSection chunksection = this.sections[j >> 4];
            boolean flag1 = false;
            if (chunksection == a) {
                if (iblockdata.isAir()) {
                    return null;
                }

                chunksection = new ChunkSection(j >> 4 << 4, this.world.worldProvider.g());
                this.sections[j >> 4] = chunksection;
                flag1 = j >= lx;
            }

            chunksection.setType(ix, j & 15, k, iblockdata);
            ((HeightMap)this.heightMap.get(HeightMap.Type.MOTION_BLOCKING)).a(ix, j, k, iblockdata);
            ((HeightMap)this.heightMap.get(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES)).a(ix, j, k, iblockdata);
            ((HeightMap)this.heightMap.get(HeightMap.Type.OCEAN_FLOOR)).a(ix, j, k, iblockdata);
            ((HeightMap)this.heightMap.get(HeightMap.Type.WORLD_SURFACE)).a(ix, j, k, iblockdata);
            if (!this.world.isClientSide) {
                iblockdata1.remove(this.world, blockposition, iblockdata, flag);
            } else if (block1 != block && block1 instanceof ITileEntity) {
                this.world.n(blockposition);
            }

            if (chunksection.getType(ix, j & 15, k).getBlock() != block) {
                return null;
            } else {
                if (flag1) {
                    this.initLighting();
                } else {
                    int i1 = iblockdata.b(this.world, blockposition);
                    int j1 = iblockdata1.b(this.world, blockposition);
                    this.a(ix, j, k, iblockdata);
                    if (i1 != j1 && (i1 < j1 || this.getBrightness(EnumSkyBlock.SKY, blockposition) > 0 || this.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 0)) {
                        this.c(ix, k);
                    }
                }

                if (block1 instanceof ITileEntity) {
                    TileEntity tileentity = this.a(blockposition, Chunk.EnumTileEntityState.CHECK);
                    if (tileentity != null) {
                        tileentity.invalidateBlockCache();
                    }
                }

                if (!this.world.isClientSide) {
                    iblockdata.onPlace(this.world, blockposition, iblockdata1);
                }

                if (block instanceof ITileEntity) {
                    TileEntity tileentity1 = this.a(blockposition, Chunk.EnumTileEntityState.CHECK);
                    if (tileentity1 == null) {
                        tileentity1 = ((ITileEntity)block).a(this.world);
                        this.world.setTileEntity(blockposition, tileentity1);
                    } else {
                        tileentity1.invalidateBlockCache();
                    }
                }

                this.x = true;
                return iblockdata1;
            }
        }
    }

    public int getBrightness(EnumSkyBlock enumskyblock, BlockPosition blockposition) {
        return this.a(enumskyblock, blockposition, this.world.o().g());
    }

    public int a(EnumSkyBlock enumskyblock, BlockPosition blockposition, boolean flag) {
        int ix = blockposition.getX() & 15;
        int j = blockposition.getY();
        int k = blockposition.getZ() & 15;
        int lx = j >> 4;
        if (lx >= 0 && lx <= this.sections.length - 1) {
            ChunkSection chunksection = this.sections[lx];
            if (chunksection == a) {
                return this.c(blockposition) ? enumskyblock.c : 0;
            } else if (enumskyblock == EnumSkyBlock.SKY) {
                return !flag ? 0 : chunksection.c(ix, j & 15, k);
            } else {
                return enumskyblock == EnumSkyBlock.BLOCK ? chunksection.d(ix, j & 15, k) : enumskyblock.c;
            }
        } else {
            return (enumskyblock != EnumSkyBlock.SKY || !flag) && enumskyblock != EnumSkyBlock.BLOCK ? 0 : enumskyblock.c;
        }
    }

    public void a(EnumSkyBlock enumskyblock, BlockPosition blockposition, int ix) {
        this.a(enumskyblock, this.world.o().g(), blockposition, ix);
    }

    public void a(EnumSkyBlock enumskyblock, boolean flag, BlockPosition blockposition, int ix) {
        int j = blockposition.getX() & 15;
        int k = blockposition.getY();
        int lx = blockposition.getZ() & 15;
        int i1 = k >> 4;
        if (i1 < 16 && i1 >= 0) {
            ChunkSection chunksection = this.sections[i1];
            if (chunksection == a) {
                if (ix == enumskyblock.c) {
                    return;
                }

                chunksection = new ChunkSection(i1 << 4, flag);
                this.sections[i1] = chunksection;
                this.initLighting();
            }

            if (enumskyblock == EnumSkyBlock.SKY) {
                if (this.world.worldProvider.g()) {
                    chunksection.a(j, k & 15, lx, ix);
                }
            } else if (enumskyblock == EnumSkyBlock.BLOCK) {
                chunksection.b(j, k & 15, lx, ix);
            }

            this.x = true;
        }
    }

    public int a(BlockPosition blockposition, int ix) {
        return this.a(blockposition, ix, this.world.o().g());
    }

    public int a(BlockPosition blockposition, int ix, boolean flag) {
        int j = blockposition.getX() & 15;
        int k = blockposition.getY();
        int lx = blockposition.getZ() & 15;
        int i1 = k >> 4;
        if (i1 >= 0 && i1 <= this.sections.length - 1) {
            ChunkSection chunksection = this.sections[i1];
            if (chunksection == a) {
                return flag && ix < EnumSkyBlock.SKY.c ? EnumSkyBlock.SKY.c - ix : 0;
            } else {
                int j1 = flag ? chunksection.c(j, k & 15, lx) : 0;
                j1 = j1 - ix;
                int k1 = chunksection.d(j, k & 15, lx);
                if (k1 > j1) {
                    j1 = k1;
                }

                return j1;
            }
        } else {
            return 0;
        }
    }

    public void a(Entity entity) {
        this.v = true;
        int ix = MathHelper.floor(entity.locX / 16.0D);
        int j = MathHelper.floor(entity.locZ / 16.0D);
        if (ix != this.locX || j != this.locZ) {
            d.warn("Wrong location! ({}, {}) should be ({}, {}), {}", ix, j, this.locX, this.locZ, entity);
            entity.die();
        }

        int k = MathHelper.floor(entity.locY / 16.0D);
        if (k < 0) {
            k = 0;
        }

        if (k >= this.entitySlices.length) {
            k = this.entitySlices.length - 1;
        }

        entity.inChunk = true;
        entity.ae = this.locX;
        entity.af = k;
        entity.ag = this.locZ;
        this.entitySlices[k].add(entity);
    }

    public void a(HeightMap.Type heightmap$type, long[] along) {
        ((HeightMap)this.heightMap.get(heightmap$type)).a(along);
    }

    public void b(Entity entity) {
        this.a(entity, entity.af);
    }

    public void a(Entity entity, int ix) {
        if (ix < 0) {
            ix = 0;
        }

        if (ix >= this.entitySlices.length) {
            ix = this.entitySlices.length - 1;
        }

        this.entitySlices[ix].remove(entity);
    }

    public boolean c(BlockPosition blockposition) {
        int ix = blockposition.getX() & 15;
        int j = blockposition.getY();
        int k = blockposition.getZ() & 15;
        return j >= ((HeightMap)this.heightMap.get(HeightMap.Type.LIGHT_BLOCKING)).a(ix, k);
    }

    public int a(HeightMap.Type heightmap$type, int ix, int j) {
        return ((HeightMap)this.heightMap.get(heightmap$type)).a(ix & 15, j & 15) - 1;
    }

    @Nullable
    private TileEntity j(BlockPosition blockposition) {
        IBlockData iblockdata = this.getType(blockposition);
        Block block = iblockdata.getBlock();
        return !block.isTileEntity() ? null : ((ITileEntity)block).a(this.world);
    }

    @Nullable
    public TileEntity getTileEntity(BlockPosition blockposition) {
        return this.a(blockposition, Chunk.EnumTileEntityState.CHECK);
    }

    @Nullable
    public TileEntity a(BlockPosition blockposition, Chunk.EnumTileEntityState chunk$enumtileentitystate) {
        TileEntity tileentity = (TileEntity)this.tileEntities.get(blockposition);
        if (tileentity == null) {
            if (chunk$enumtileentitystate == Chunk.EnumTileEntityState.IMMEDIATE) {
                tileentity = this.j(blockposition);
                this.world.setTileEntity(blockposition, tileentity);
            } else if (chunk$enumtileentitystate == Chunk.EnumTileEntityState.QUEUED) {
                this.B.add(blockposition);
            }
        } else if (tileentity.x()) {
            this.tileEntities.remove(blockposition);
            return null;
        }

        return tileentity;
    }

    public void a(TileEntity tileentity) {
        this.a(tileentity.getPosition(), tileentity);
        if (this.i) {
            this.world.a(tileentity);
        }

    }

    public void a(BlockPosition blockposition, TileEntity tileentity) {
        tileentity.setWorld(this.world);
        tileentity.setPosition(blockposition);
        if (this.getType(blockposition).getBlock() instanceof ITileEntity) {
            if (this.tileEntities.containsKey(blockposition)) {
                ((TileEntity)this.tileEntities.get(blockposition)).y();
            }

            tileentity.z();
            this.tileEntities.put(blockposition, tileentity);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.h.put(new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z")), nbttagcompound);
    }

    public void d(BlockPosition blockposition) {
        if (this.i) {
            TileEntity tileentity = (TileEntity)this.tileEntities.remove(blockposition);
            if (tileentity != null) {
                tileentity.y();
            }
        }

    }

    public void addEntities() {
        this.i = true;
        this.world.a(this.tileEntities.values());

        for(EntitySlice entityslice : this.entitySlices) {
            this.world.a(entityslice.stream().filter((entity) -> {
                return !(entity instanceof EntityHuman);
            }));
        }

    }

    public void removeEntities() {
        this.i = false;

        for(TileEntity tileentity : this.tileEntities.values()) {
            this.world.b(tileentity);
        }

        for(EntitySlice entityslice : this.entitySlices) {
            this.world.b(entityslice);
        }

    }

    public void markDirty() {
        this.x = true;
    }

    public void a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, List<Entity> list, Predicate<? super Entity> predicate) {
        int ix = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
        int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
        ix = MathHelper.clamp(ix, 0, this.entitySlices.length - 1);
        j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);

        for(int k = ix; k <= j; ++k) {
            if (!this.entitySlices[k].isEmpty()) {
                for(Entity entity1 : this.entitySlices[k]) {
                    if (entity1.getBoundingBox().c(axisalignedbb) && entity1 != entity) {
                        if (predicate == null || predicate.test(entity1)) {
                            list.add(entity1);
                        }

                        Entity[] aentity = entity1.bi();
                        if (aentity != null) {
                            for(Entity entity2 : aentity) {
                                if (entity2 != entity && entity2.getBoundingBox().c(axisalignedbb) && (predicate == null || predicate.test(entity2))) {
                                    list.add(entity2);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public <T extends Entity> void a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, List<T> list, @Nullable Predicate<? super T> predicate) {
        int ix = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
        int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
        ix = MathHelper.clamp(ix, 0, this.entitySlices.length - 1);
        j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);

        for(int k = ix; k <= j; ++k) {
            for(Entity entity : this.entitySlices[k].c(oclass)) {
                if (entity.getBoundingBox().c(axisalignedbb) && (predicate == null || predicate.test(entity))) {
                    list.add(entity);
                }
            }
        }

    }

    public boolean c(boolean flag) {
        if (flag) {
            if (this.v && this.world.getTime() != this.lastSaved || this.x) {
                return true;
            }
        } else if (this.v && this.world.getTime() >= this.lastSaved + 600L) {
            return true;
        }

        return this.x;
    }

    public boolean isEmpty() {
        return false;
    }

    public void d(boolean flag) {
        if (this.l && this.world.worldProvider.g() && !flag) {
            this.g(this.world.isClientSide);
        }

        this.u = true;

        while(!this.B.isEmpty()) {
            BlockPosition blockposition = (BlockPosition)this.B.poll();
            if (this.a(blockposition, Chunk.EnumTileEntityState.CHECK) == null && this.getType(blockposition).getBlock().isTileEntity()) {
                TileEntity tileentity = this.j(blockposition);
                this.world.setTileEntity(blockposition, tileentity);
                this.world.a(blockposition, blockposition);
            }
        }

    }

    public boolean isReady() {
        return this.C.a(ChunkStatus.POSTPROCESSED);
    }

    public boolean v() {
        return this.u;
    }

    public ChunkCoordIntPair getPos() {
        return new ChunkCoordIntPair(this.locX, this.locZ);
    }

    public boolean b(int ix, int j) {
        if (ix < 0) {
            ix = 0;
        }

        if (j >= 256) {
            j = 255;
        }

        for(int k = ix; k <= j; k += 16) {
            ChunkSection chunksection = this.sections[k >> 4];
            if (chunksection != a && !chunksection.a()) {
                return false;
            }
        }

        return true;
    }

    public void a(ChunkSection[] achunksection) {
        if (this.sections.length != achunksection.length) {
            d.warn("Could not set level chunk sections, array length is {} instead of {}", achunksection.length, this.sections.length);
        } else {
            System.arraycopy(achunksection, 0, this.sections, 0, this.sections.length);
        }
    }

    public BiomeBase getBiome(BlockPosition blockposition) {
        int ix = blockposition.getX() & 15;
        int j = blockposition.getZ() & 15;
        return this.f[j << 4 | ix];
    }

    public BiomeBase[] getBiomeIndex() {
        return this.f;
    }

    public void x() {
        if (this.A < 4096) {
            BlockPosition blockposition = new BlockPosition(this.locX << 4, 0, this.locZ << 4);

            for(int ix = 0; ix < 8; ++ix) {
                if (this.A >= 4096) {
                    return;
                }

                int j = this.A % 16;
                int k = this.A / 16 % 16;
                int lx = this.A / 256;
                ++this.A;

                for(int i1 = 0; i1 < 16; ++i1) {
                    BlockPosition blockposition1 = blockposition.a(k, (j << 4) + i1, lx);
                    boolean flag = i1 == 0 || i1 == 15 || k == 0 || k == 15 || lx == 0 || lx == 15;
                    if (this.sections[j] == a && flag || this.sections[j] != a && this.sections[j].getType(k, i1, lx).isAir()) {
                        for(EnumDirection enumdirection : EnumDirection.values()) {
                            BlockPosition blockposition2 = blockposition1.shift(enumdirection);
                            if (this.world.getType(blockposition2).e() > 0) {
                                this.world.r(blockposition2);
                            }
                        }

                        this.world.r(blockposition1);
                    }
                }
            }

        }
    }

    public boolean y() {
        return this.i;
    }

    public World getWorld() {
        return this.world;
    }

    public Set<HeightMap.Type> A() {
        return this.heightMap.keySet();
    }

    public HeightMap b(HeightMap.Type heightmap$type) {
        return (HeightMap)this.heightMap.get(heightmap$type);
    }

    public Map<BlockPosition, TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public EntitySlice<Entity>[] getEntitySlices() {
        return this.entitySlices;
    }

    public NBTTagCompound g(BlockPosition blockposition) {
        return (NBTTagCompound)this.h.get(blockposition);
    }

    public TickList<Block> k() {
        return this.s;
    }

    public TickList<FluidType> l() {
        return this.t;
    }

    public BitSet a(WorldGenStage.Features var1) {
        throw new RuntimeException("Not yet implemented");
    }

    public void a(boolean flag) {
        this.x = flag;
    }

    public void f(boolean flag) {
        this.v = flag;
    }

    public void setLastSaved(long ix) {
        this.lastSaved = ix;
    }

    @Nullable
    public StructureStart a(String sx) {
        return (StructureStart)this.p.get(sx);
    }

    public void a(String sx, StructureStart structurestart) {
        this.p.put(sx, structurestart);
    }

    public Map<String, StructureStart> e() {
        return this.p;
    }

    public void a(Map<String, StructureStart> map) {
        this.p.clear();
        this.p.putAll(map);
    }

    @Nullable
    public LongSet b(String sx) {
        return (LongSet)this.q.computeIfAbsent(sx, (var0) -> {
            return new LongOpenHashSet();
        });
    }

    public void a(String sx, long ix) {
        ((LongSet)this.q.computeIfAbsent(sx, (var0) -> {
            return new LongOpenHashSet();
        })).add(ix);
    }

    public Map<String, LongSet> f() {
        return this.q;
    }

    public void b(Map<String, LongSet> map) {
        this.q.clear();
        this.q.putAll(map);
    }

    public int D() {
        return this.y;
    }

    public long m() {
        return this.z;
    }

    public void b(long ix) {
        this.z = ix;
    }

    public void E() {
        if (!this.C.a(ChunkStatus.POSTPROCESSED) && this.D == 8) {
            ChunkCoordIntPair chunkcoordintpair = this.getPos();

            for(int ix = 0; ix < this.r.length; ++ix) {
                if (this.r[ix] != null) {
                    ShortListIterator shortlistiterator = this.r[ix].iterator();

                    while(shortlistiterator.hasNext()) {
                        Short oshort = (Short)shortlistiterator.next();
                        BlockPosition blockposition = ProtoChunk.a(oshort, ix, chunkcoordintpair);
                        IBlockData iblockdata = this.world.getType(blockposition);
                        IBlockData iblockdata1 = Block.b(iblockdata, this.world, blockposition);
                        this.world.setTypeAndData(blockposition, iblockdata1, 20);
                    }

                    this.r[ix].clear();
                }
            }

            if (this.s instanceof ProtoChunkTickList) {
                ((ProtoChunkTickList)this.s).a(this.world.J(), (blockposition2) -> {
                    return this.world.getType(blockposition2).getBlock();
                });
            }

            if (this.t instanceof ProtoChunkTickList) {
                ((ProtoChunkTickList)this.t).a(this.world.I(), (blockposition2) -> {
                    return this.world.b(blockposition2).c();
                });
            }

            for(Entry entry : this.h.entrySet()) {
                BlockPosition blockposition1 = (BlockPosition)entry.getKey();
                NBTTagCompound nbttagcompound = (NBTTagCompound)entry.getValue();
                if (this.getTileEntity(blockposition1) == null) {
                    TileEntity tileentity;
                    if ("DUMMY".equals(nbttagcompound.getString("id"))) {
                        Block block = this.getType(blockposition1).getBlock();
                        if (block instanceof ITileEntity) {
                            tileentity = ((ITileEntity)block).a(this.world);
                        } else {
                            tileentity = null;
                            d.warn("Tried to load a DUMMY block entity @ {} but found not tile entity block {} at location", blockposition1, this.getType(blockposition1));
                        }
                    } else {
                        tileentity = TileEntity.create(nbttagcompound);
                    }

                    if (tileentity != null) {
                        tileentity.setPosition(blockposition1);
                        this.a(tileentity);
                    } else {
                        d.warn("Tried to load a block entity for block {} but failed at location {}", this.getType(blockposition1), blockposition1);
                    }
                }
            }

            this.h.clear();
            this.a(ChunkStatus.POSTPROCESSED);
            this.m.a(this);
        }
    }

    public ChunkConverter F() {
        return this.m;
    }

    public ShortList[] G() {
        return this.r;
    }

    public void a(short short1, int ix) {
        ProtoChunk.a(this.r, ix).add(short1);
    }

    public ChunkStatus i() {
        return this.C;
    }

    public void a(ChunkStatus chunkstatus) {
        this.C = chunkstatus;
    }

    public void c(String sx) {
        this.a(ChunkStatus.a(sx));
    }

    public void H() {
        ++this.D;
        if (this.D > 8) {
            throw new RuntimeException("Error while adding chunk to cache. Too many neighbors");
        } else {
            if (this.J()) {
                ((IAsyncTaskHandler)this.world).postToMainThread(this::E);
            }

        }
    }

    public void I() {
        --this.D;
        if (this.D < 0) {
            throw new RuntimeException("Error while removing chunk from cache. Not enough neighbors");
        }
    }

    public boolean J() {
        return this.D == 8;
    }

    public static enum EnumTileEntityState {
        IMMEDIATE,
        QUEUED,
        CHECK;

        private EnumTileEntityState() {
        }
    }
}
