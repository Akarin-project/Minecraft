package net.minecraft.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class World implements IEntityAccess, GeneratorAccess, IIBlockAccess, AutoCloseable {
    protected static final Logger e = LogManager.getLogger();
    private static final EnumDirection[] a = EnumDirection.values();
    private int b = 63;
    public final List<Entity> entityList = Lists.newArrayList();
    protected final List<Entity> g = Lists.newArrayList();
    public final List<TileEntity> tileEntityList = Lists.newArrayList();
    public final List<TileEntity> tileEntityListTick = Lists.newArrayList();
    private final List<TileEntity> c = Lists.newArrayList();
    private final List<TileEntity> tileEntityListUnload = Lists.newArrayList();
    public final List<EntityHuman> players = Lists.newArrayList();
    public final List<Entity> k = Lists.newArrayList();
    protected final IntHashMap<Entity> entitiesById = new IntHashMap<Entity>();
    private final long F = 16777215L;
    private int G;
    protected int m = (new Random()).nextInt();
    protected final int n = 1013904223;
    protected float o;
    protected float p;
    protected float q;
    protected float r;
    private int H;
    public final Random random = new Random();
    public WorldProvider worldProvider;
    protected NavigationListener u = new NavigationListener();
    protected List<IWorldAccess> v;
    protected IChunkProvider chunkProvider;
    protected final IDataManager dataManager;
    public WorldData worldData;
    @Nullable
    public final PersistentCollection worldMaps;
    protected PersistentVillage villages;
    public final MethodProfiler methodProfiler;
    public final boolean isClientSide;
    public boolean allowMonsters;
    public boolean allowAnimals;
    private boolean J;
    private final WorldBorder K;
    int[] E;

    protected World(IDataManager idatamanager, @Nullable PersistentCollection persistentcollection, WorldData worlddata, WorldProvider worldprovider, MethodProfiler methodprofiler, boolean flag) {
        this.v = Lists.newArrayList(new IWorldAccess[]{this.u});
        this.allowMonsters = true;
        this.allowAnimals = true;
        this.E = new int['\u8000'];
        this.dataManager = idatamanager;
        this.worldMaps = persistentcollection;
        this.methodProfiler = methodprofiler;
        this.worldData = worlddata;
        this.worldProvider = worldprovider;
        this.isClientSide = flag;
        this.K = worldprovider.getWorldBorder();
    }

    public BiomeBase getBiome(BlockPosition blockposition) {
        if (this.isLoaded(blockposition)) {
            Chunk chunk = this.getChunkAtWorldCoords(blockposition);

            try {
                return chunk.getBiome(blockposition);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Coordinates of biome request");
                crashreportsystemdetails.a("Location", () -> {
                    return CrashReportSystemDetails.a(blockposition);
                });
                throw new ReportedException(crashreport);
            }
        } else {
            return this.chunkProvider.getChunkGenerator().getWorldChunkManager().getBiome(blockposition, Biomes.c);
        }
    }

    protected abstract IChunkProvider r();

    public void a(WorldSettings var1) {
        this.worldData.d(true);
    }

    public boolean e() {
        return this.isClientSide;
    }

    @Nullable
    public MinecraftServer getMinecraftServer() {
        return null;
    }

    public IBlockData i(BlockPosition blockposition) {
        BlockPosition blockposition1;
        for(blockposition1 = new BlockPosition(blockposition.getX(), this.getSeaLevel(), blockposition.getZ()); !this.isEmpty(blockposition1.up()); blockposition1 = blockposition1.up()) {
            ;
        }

        return this.getType(blockposition1);
    }

    public static boolean isValidLocation(BlockPosition blockposition) {
        return !k(blockposition) && blockposition.getX() >= -30000000 && blockposition.getZ() >= -30000000 && blockposition.getX() < 30000000 && blockposition.getZ() < 30000000;
    }

    public static boolean k(BlockPosition blockposition) {
        return blockposition.getY() < 0 || blockposition.getY() >= 256;
    }

    public boolean isEmpty(BlockPosition blockposition) {
        return this.getType(blockposition).isAir();
    }

    public Chunk getChunkAtWorldCoords(BlockPosition blockposition) {
        return this.getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }

    public Chunk getChunkAt(int i, int j) {
        Chunk chunk = this.chunkProvider.getChunkAt(i, j, true, true);
        if (chunk == null) {
            throw new IllegalStateException("Should always be able to create a chunk!");
        } else {
            return chunk;
        }
    }

    public boolean setTypeAndData(BlockPosition blockposition, IBlockData iblockdata, int i) {
        if (k(blockposition)) {
            return false;
        } else if (!this.isClientSide && this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            return false;
        } else {
            Chunk chunk = this.getChunkAtWorldCoords(blockposition);
            Block block = iblockdata.getBlock();
            IBlockData iblockdata1 = chunk.a(blockposition, iblockdata, (i & 64) != 0);
            if (iblockdata1 == null) {
                return false;
            } else {
                IBlockData iblockdata2 = this.getType(blockposition);
                if (iblockdata2.b(this, blockposition) != iblockdata1.b(this, blockposition) || iblockdata2.e() != iblockdata1.e()) {
                    this.methodProfiler.a("checkLight");
                    this.r(blockposition);
                    this.methodProfiler.e();
                }

                if (iblockdata2 == iblockdata) {
                    if (iblockdata1 != iblockdata2) {
                        this.a(blockposition, blockposition);
                    }

                    if ((i & 2) != 0 && (!this.isClientSide || (i & 4) == 0) && chunk.isReady()) {
                        this.notify(blockposition, iblockdata1, iblockdata, i);
                    }

                    if (!this.isClientSide && (i & 1) != 0) {
                        this.update(blockposition, iblockdata1.getBlock());
                        if (iblockdata.isComplexRedstone()) {
                            this.updateAdjacentComparators(blockposition, block);
                        }
                    }

                    if ((i & 16) == 0) {
                        int j = i & -2;
                        iblockdata1.b(this, blockposition, j);
                        iblockdata.a((GeneratorAccess)this, blockposition, j);
                        iblockdata.b(this, blockposition, j);
                    }
                }

                return true;
            }
        }
    }

    public boolean setAir(BlockPosition blockposition) {
        Fluid fluid = this.b(blockposition);
        return this.setTypeAndData(blockposition, fluid.i(), 3);
    }

    public boolean setAir(BlockPosition blockposition, boolean flag) {
        IBlockData iblockdata = this.getType(blockposition);
        if (iblockdata.isAir()) {
            return false;
        } else {
            Fluid fluid = this.b(blockposition);
            this.triggerEffect(2001, blockposition, Block.getCombinedId(iblockdata));
            if (flag) {
                iblockdata.a(this, blockposition, 0);
            }

            return this.setTypeAndData(blockposition, fluid.i(), 3);
        }
    }

    public boolean setTypeUpdate(BlockPosition blockposition, IBlockData iblockdata) {
        return this.setTypeAndData(blockposition, iblockdata, 3);
    }

    public void notify(BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1, int i) {
        for(int j = 0; j < this.v.size(); ++j) {
            ((IWorldAccess)this.v.get(j)).a(this, blockposition, iblockdata, iblockdata1, i);
        }

    }

    public void update(BlockPosition blockposition, Block block) {
        if (this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
            this.applyPhysics(blockposition, block);
        }

    }

    public void a(int i, int j, int kx, int l) {
        if (kx > l) {
            int i1 = l;
            l = kx;
            kx = i1;
        }

        if (this.worldProvider.g()) {
            for(int j1 = kx; j1 <= l; ++j1) {
                this.c(EnumSkyBlock.SKY, new BlockPosition(i, j1, j));
            }
        }

        this.a(i, kx, j, i, l, j);
    }

    public void a(BlockPosition blockposition, BlockPosition blockposition1) {
        this.a(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
    }

    public void a(int i, int j, int kx, int l, int i1, int j1) {
        for(int k1 = 0; k1 < this.v.size(); ++k1) {
            ((IWorldAccess)this.v.get(k1)).a(i, j, kx, l, i1, j1);
        }

    }

    public void applyPhysics(BlockPosition blockposition, Block block) {
        this.a(blockposition.west(), block, blockposition);
        this.a(blockposition.east(), block, blockposition);
        this.a(blockposition.down(), block, blockposition);
        this.a(blockposition.up(), block, blockposition);
        this.a(blockposition.north(), block, blockposition);
        this.a(blockposition.south(), block, blockposition);
    }

    public void a(BlockPosition blockposition, Block block, EnumDirection enumdirection) {
        if (enumdirection != EnumDirection.WEST) {
            this.a(blockposition.west(), block, blockposition);
        }

        if (enumdirection != EnumDirection.EAST) {
            this.a(blockposition.east(), block, blockposition);
        }

        if (enumdirection != EnumDirection.DOWN) {
            this.a(blockposition.down(), block, blockposition);
        }

        if (enumdirection != EnumDirection.UP) {
            this.a(blockposition.up(), block, blockposition);
        }

        if (enumdirection != EnumDirection.NORTH) {
            this.a(blockposition.north(), block, blockposition);
        }

        if (enumdirection != EnumDirection.SOUTH) {
            this.a(blockposition.south(), block, blockposition);
        }

    }

    public void a(BlockPosition blockposition, Block block, BlockPosition blockposition1) {
        if (!this.isClientSide) {
            IBlockData iblockdata = this.getType(blockposition);

            try {
                iblockdata.doPhysics(this, blockposition, block, blockposition1);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
                crashreportsystemdetails.a("Source block type", () -> {
                    try {
                        return String.format("ID #%s (%s // %s)", IRegistry.BLOCK.getKey(block), block.m(), block.getClass().getCanonicalName());
                    } catch (Throwable var2) {
                        return "ID #" + IRegistry.BLOCK.getKey(block);
                    }
                });
                CrashReportSystemDetails.a(crashreportsystemdetails, blockposition, iblockdata);
                throw new ReportedException(crashreport);
            }
        }
    }

    public boolean e(BlockPosition blockposition) {
        return this.getChunkAtWorldCoords(blockposition).c(blockposition);
    }

    public int getLightLevel(BlockPosition blockposition, int i) {
        if (blockposition.getX() >= -30000000 && blockposition.getZ() >= -30000000 && blockposition.getX() < 30000000 && blockposition.getZ() < 30000000) {
            if (blockposition.getY() < 0) {
                return 0;
            } else {
                if (blockposition.getY() >= 256) {
                    blockposition = new BlockPosition(blockposition.getX(), 255, blockposition.getZ());
                }

                return this.getChunkAtWorldCoords(blockposition).a(blockposition, i);
            }
        } else {
            return 15;
        }
    }

    public int a(HeightMap.Type heightmap$type, int i, int j) {
        int kx;
        if (i >= -30000000 && j >= -30000000 && i < 30000000 && j < 30000000) {
            if (this.isChunkLoaded(i >> 4, j >> 4, true)) {
                kx = this.getChunkAt(i >> 4, j >> 4).a(heightmap$type, i & 15, j & 15) + 1;
            } else {
                kx = 0;
            }
        } else {
            kx = this.getSeaLevel() + 1;
        }

        return kx;
    }

    @Deprecated
    public int d(int i, int j) {
        if (i >= -30000000 && j >= -30000000 && i < 30000000 && j < 30000000) {
            if (!this.isChunkLoaded(i >> 4, j >> 4, true)) {
                return 0;
            } else {
                Chunk chunk = this.getChunkAt(i >> 4, j >> 4);
                return chunk.D();
            }
        } else {
            return this.getSeaLevel() + 1;
        }
    }

    public int getBrightness(EnumSkyBlock enumskyblock, BlockPosition blockposition) {
        if (blockposition.getY() < 0) {
            blockposition = new BlockPosition(blockposition.getX(), 0, blockposition.getZ());
        }

        if (!isValidLocation(blockposition)) {
            return enumskyblock.c;
        } else {
            return !this.isLoaded(blockposition) ? enumskyblock.c : this.getChunkAtWorldCoords(blockposition).getBrightness(enumskyblock, blockposition);
        }
    }

    public void a(EnumSkyBlock enumskyblock, BlockPosition blockposition, int i) {
        if (isValidLocation(blockposition)) {
            if (this.isLoaded(blockposition)) {
                this.getChunkAtWorldCoords(blockposition).a(enumskyblock, blockposition, i);
                this.m(blockposition);
            }
        }
    }

    public void m(BlockPosition blockposition) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(blockposition);
        }

    }

    public IBlockData getType(BlockPosition blockposition) {
        if (k(blockposition)) {
            return Blocks.VOID_AIR.getBlockData();
        } else {
            Chunk chunk = this.getChunkAtWorldCoords(blockposition);
            return chunk.getType(blockposition);
        }
    }

    public Fluid b(BlockPosition blockposition) {
        if (k(blockposition)) {
            return FluidTypes.a.i();
        } else {
            Chunk chunk = this.getChunkAtWorldCoords(blockposition);
            return chunk.b(blockposition);
        }
    }

    public boolean L() {
        return this.G < 4;
    }

    @Nullable
    public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1) {
        return this.rayTrace(vec3d, vec3d1, FluidCollisionOption.NEVER, false, false);
    }

    @Nullable
    public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, FluidCollisionOption fluidcollisionoption) {
        return this.rayTrace(vec3d, vec3d1, fluidcollisionoption, false, false);
    }

    @Nullable
    public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, FluidCollisionOption fluidcollisionoption, boolean flag, boolean flag1) {
        double d0 = vec3d.x;
        double d1 = vec3d.y;
        double d2 = vec3d.z;
        if (!Double.isNaN(d0) && !Double.isNaN(d1) && !Double.isNaN(d2)) {
            if (!Double.isNaN(vec3d1.x) && !Double.isNaN(vec3d1.y) && !Double.isNaN(vec3d1.z)) {
                int i = MathHelper.floor(vec3d1.x);
                int j = MathHelper.floor(vec3d1.y);
                int kx = MathHelper.floor(vec3d1.z);
                int l = MathHelper.floor(d0);
                int i1 = MathHelper.floor(d1);
                int j1 = MathHelper.floor(d2);
                BlockPosition blockposition = new BlockPosition(l, i1, j1);
                IBlockData iblockdata = this.getType(blockposition);
                Fluid fluid = this.b(blockposition);
                if (!flag || !iblockdata.h(this, blockposition).b()) {
                    boolean flag2 = iblockdata.getBlock().d(iblockdata);
                    boolean flag3 = fluidcollisionoption.d.test(fluid);
                    if (flag2 || flag3) {
                        MovingObjectPosition movingobjectposition = null;
                        if (flag2) {
                            movingobjectposition = Block.a(iblockdata, this, blockposition, vec3d, vec3d1);
                        }

                        if (movingobjectposition == null && flag3) {
                            movingobjectposition = VoxelShapes.a(0.0D, 0.0D, 0.0D, 1.0D, (double)fluid.f(), 1.0D).a(vec3d, vec3d1, blockposition);
                        }

                        if (movingobjectposition != null) {
                            return movingobjectposition;
                        }
                    }
                }

                MovingObjectPosition movingobjectposition2 = null;
                int k1 = 200;

                while(k1-- >= 0) {
                    if (Double.isNaN(d0) || Double.isNaN(d1) || Double.isNaN(d2)) {
                        return null;
                    }

                    if (l == i && i1 == j && j1 == kx) {
                        return flag1 ? movingobjectposition2 : null;
                    }

                    boolean flag6 = true;
                    boolean flag7 = true;
                    boolean flag8 = true;
                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    if (i > l) {
                        d3 = (double)l + 1.0D;
                    } else if (i < l) {
                        d3 = (double)l + 0.0D;
                    } else {
                        flag6 = false;
                    }

                    if (j > i1) {
                        d4 = (double)i1 + 1.0D;
                    } else if (j < i1) {
                        d4 = (double)i1 + 0.0D;
                    } else {
                        flag7 = false;
                    }

                    if (kx > j1) {
                        d5 = (double)j1 + 1.0D;
                    } else if (kx < j1) {
                        d5 = (double)j1 + 0.0D;
                    } else {
                        flag8 = false;
                    }

                    double d6 = 999.0D;
                    double d7 = 999.0D;
                    double d8 = 999.0D;
                    double d9 = vec3d1.x - d0;
                    double d10 = vec3d1.y - d1;
                    double d11 = vec3d1.z - d2;
                    if (flag6) {
                        d6 = (d3 - d0) / d9;
                    }

                    if (flag7) {
                        d7 = (d4 - d1) / d10;
                    }

                    if (flag8) {
                        d8 = (d5 - d2) / d11;
                    }

                    if (d6 == -0.0D) {
                        d6 = -1.0E-4D;
                    }

                    if (d7 == -0.0D) {
                        d7 = -1.0E-4D;
                    }

                    if (d8 == -0.0D) {
                        d8 = -1.0E-4D;
                    }

                    EnumDirection enumdirection;
                    if (d6 < d7 && d6 < d8) {
                        enumdirection = i > l ? EnumDirection.WEST : EnumDirection.EAST;
                        d0 = d3;
                        d1 += d10 * d6;
                        d2 += d11 * d6;
                    } else if (d7 < d8) {
                        enumdirection = j > i1 ? EnumDirection.DOWN : EnumDirection.UP;
                        d0 += d9 * d7;
                        d1 = d4;
                        d2 += d11 * d7;
                    } else {
                        enumdirection = kx > j1 ? EnumDirection.NORTH : EnumDirection.SOUTH;
                        d0 += d9 * d8;
                        d1 += d10 * d8;
                        d2 = d5;
                    }

                    l = MathHelper.floor(d0) - (enumdirection == EnumDirection.EAST ? 1 : 0);
                    i1 = MathHelper.floor(d1) - (enumdirection == EnumDirection.UP ? 1 : 0);
                    j1 = MathHelper.floor(d2) - (enumdirection == EnumDirection.SOUTH ? 1 : 0);
                    blockposition = new BlockPosition(l, i1, j1);
                    IBlockData iblockdata1 = this.getType(blockposition);
                    Fluid fluid1 = this.b(blockposition);
                    if (!flag || iblockdata1.getMaterial() == Material.PORTAL || !iblockdata1.h(this, blockposition).b()) {
                        boolean flag4 = iblockdata1.getBlock().d(iblockdata1);
                        boolean flag5 = fluidcollisionoption.d.test(fluid1);
                        if (!flag4 && !flag5) {
                            movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.EnumMovingObjectType.MISS, new Vec3D(d0, d1, d2), enumdirection, blockposition);
                        } else {
                            MovingObjectPosition movingobjectposition1 = null;
                            if (flag4) {
                                movingobjectposition1 = Block.a(iblockdata1, this, blockposition, vec3d, vec3d1);
                            }

                            if (movingobjectposition1 == null && flag5) {
                                movingobjectposition1 = VoxelShapes.a(0.0D, 0.0D, 0.0D, 1.0D, (double)fluid1.f(), 1.0D).a(vec3d, vec3d1, blockposition);
                            }

                            if (movingobjectposition1 != null) {
                                return movingobjectposition1;
                            }
                        }
                    }
                }

                return flag1 ? movingobjectposition2 : null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void a(@Nullable EntityHuman entityhuman, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
        this.a(entityhuman, (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D, soundeffect, soundcategory, f, f1);
    }

    public void a(@Nullable EntityHuman entityhuman, double d0, double d1, double d2, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(entityhuman, soundeffect, soundcategory, d0, d1, d2, f, f1);
        }

    }

    public void a(double var1, double var3, double var5, SoundEffect var7, SoundCategory var8, float var9, float var10, boolean var11) {
    }

    public void a(BlockPosition blockposition, @Nullable SoundEffect soundeffect) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(soundeffect, blockposition);
        }

    }

    public void addParticle(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(particleparam, particleparam.b().e(), d0, d1, d2, d3, d4, d5);
        }

    }

    public void b(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(particleparam, false, true, d0, d1, d2, d3, d4, d5);
        }

    }

    public boolean strikeLightning(Entity entity) {
        this.k.add(entity);
        return true;
    }

    public boolean addEntity(Entity entity) {
        int i = MathHelper.floor(entity.locX / 16.0D);
        int j = MathHelper.floor(entity.locZ / 16.0D);
        boolean flag = entity.attachedToPlayer;
        if (entity instanceof EntityHuman) {
            flag = true;
        }

        if (!flag && !this.isChunkLoaded(i, j, false)) {
            return false;
        } else {
            if (entity instanceof EntityHuman) {
                EntityHuman entityhuman = (EntityHuman)entity;
                this.players.add(entityhuman);
                this.everyoneSleeping();
            }

            this.getChunkAt(i, j).a(entity);
            this.entityList.add(entity);
            this.b(entity);
            return true;
        }
    }

    protected void b(Entity entity) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).a(entity);
        }

    }

    protected void c(Entity entity) {
        for(int i = 0; i < this.v.size(); ++i) {
            ((IWorldAccess)this.v.get(i)).b(entity);
        }

    }

    public void kill(Entity entity) {
        if (entity.isVehicle()) {
            entity.ejectPassengers();
        }

        if (entity.isPassenger()) {
            entity.stopRiding();
        }

        entity.die();
        if (entity instanceof EntityHuman) {
            this.players.remove(entity);
            this.everyoneSleeping();
            this.c(entity);
        }

    }

    public void removeEntity(Entity entity) {
        entity.b(false);
        entity.die();
        if (entity instanceof EntityHuman) {
            this.players.remove(entity);
            this.everyoneSleeping();
        }

        int i = entity.ae;
        int j = entity.ag;
        if (entity.inChunk && this.isChunkLoaded(i, j, true)) {
            this.getChunkAt(i, j).b(entity);
        }

        this.entityList.remove(entity);
        this.c(entity);
    }

    public void addIWorldAccess(IWorldAccess iworldaccess) {
        this.v.add(iworldaccess);
    }

    public int a(float f) {
        float f1 = this.k(f);
        float f2 = 1.0F - (MathHelper.cos(f1 * ((float)Math.PI * 2F)) * 2.0F + 0.5F);
        f2 = MathHelper.a(f2, 0.0F, 1.0F);
        f2 = 1.0F - f2;
        f2 = (float)((double)f2 * (1.0D - (double)(this.i(f) * 5.0F) / 16.0D));
        f2 = (float)((double)f2 * (1.0D - (double)(this.g(f) * 5.0F) / 16.0D));
        f2 = 1.0F - f2;
        return (int)(f2 * 11.0F);
    }

    public float c(float f) {
        float f1 = this.k(f);
        return f1 * ((float)Math.PI * 2F);
    }

    public void tickEntities() {
        this.methodProfiler.a("entities");
        this.methodProfiler.a("global");

        for(int i = 0; i < this.k.size(); ++i) {
            Entity entity = (Entity)this.k.get(i);

            try {
                ++entity.ticksLived;
                entity.tick();
            } catch (Throwable throwable2) {
                CrashReport crashreport = CrashReport.a(throwable2, "Ticking entity");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
                if (entity == null) {
                    crashreportsystemdetails.a("Entity", "~~NULL~~");
                } else {
                    entity.appendEntityCrashDetails(crashreportsystemdetails);
                }

                throw new ReportedException(crashreport);
            }

            if (entity.dead) {
                this.k.remove(i--);
            }
        }

        this.methodProfiler.c("remove");
        this.entityList.removeAll(this.g);

        for(int kx = 0; kx < this.g.size(); ++kx) {
            Entity entity1 = (Entity)this.g.get(kx);
            int j = entity1.ae;
            int k1 = entity1.ag;
            if (entity1.inChunk && this.isChunkLoaded(j, k1, true)) {
                this.getChunkAt(j, k1).b(entity1);
            }
        }

        for(int l = 0; l < this.g.size(); ++l) {
            this.c((Entity)this.g.get(l));
        }

        this.g.clear();
        this.p_();
        this.methodProfiler.c("regular");

        for(int i1 = 0; i1 < this.entityList.size(); ++i1) {
            Entity entity2 = (Entity)this.entityList.get(i1);
            Entity entity3 = entity2.getVehicle();
            if (entity3 != null) {
                if (!entity3.dead && entity3.w(entity2)) {
                    continue;
                }

                entity2.stopRiding();
            }

            this.methodProfiler.a("tick");
            if (!entity2.dead && !(entity2 instanceof EntityPlayer)) {
                try {
                    this.g(entity2);
                } catch (Throwable throwable1) {
                    CrashReport crashreport1 = CrashReport.a(throwable1, "Ticking entity");
                    CrashReportSystemDetails crashreportsystemdetails1 = crashreport1.a("Entity being ticked");
                    entity2.appendEntityCrashDetails(crashreportsystemdetails1);
                    throw new ReportedException(crashreport1);
                }
            }

            this.methodProfiler.e();
            this.methodProfiler.a("remove");
            if (entity2.dead) {
                int l1 = entity2.ae;
                int i2 = entity2.ag;
                if (entity2.inChunk && this.isChunkLoaded(l1, i2, true)) {
                    this.getChunkAt(l1, i2).b(entity2);
                }

                this.entityList.remove(i1--);
                this.c(entity2);
            }

            this.methodProfiler.e();
        }

        this.methodProfiler.c("blockEntities");
        if (!this.tileEntityListUnload.isEmpty()) {
            this.tileEntityListTick.removeAll(this.tileEntityListUnload);
            this.tileEntityList.removeAll(this.tileEntityListUnload);
            this.tileEntityListUnload.clear();
        }

        this.J = true;
        Iterator iterator = this.tileEntityListTick.iterator();

        while(iterator.hasNext()) {
            TileEntity tileentity = (TileEntity)iterator.next();
            if (!tileentity.x() && tileentity.hasWorld()) {
                BlockPosition blockposition = tileentity.getPosition();
                if (this.isLoaded(blockposition) && this.K.a(blockposition)) {
                    try {
                        this.methodProfiler.a(() -> {
                            return String.valueOf(TileEntityTypes.a(tileentity.C()));
                        });
                        ((ITickable)tileentity).Y_();
                        this.methodProfiler.e();
                    } catch (Throwable throwable) {
                        CrashReport crashreport2 = CrashReport.a(throwable, "Ticking block entity");
                        CrashReportSystemDetails crashreportsystemdetails2 = crashreport2.a("Block entity being ticked");
                        tileentity.a(crashreportsystemdetails2);
                        throw new ReportedException(crashreport2);
                    }
                }
            }

            if (tileentity.x()) {
                iterator.remove();
                this.tileEntityList.remove(tileentity);
                if (this.isLoaded(tileentity.getPosition())) {
                    this.getChunkAtWorldCoords(tileentity.getPosition()).d(tileentity.getPosition());
                }
            }
        }

        this.J = false;
        this.methodProfiler.c("pendingBlockEntities");
        if (!this.c.isEmpty()) {
            for(int j1 = 0; j1 < this.c.size(); ++j1) {
                TileEntity tileentity1 = (TileEntity)this.c.get(j1);
                if (!tileentity1.x()) {
                    if (!this.tileEntityList.contains(tileentity1)) {
                        this.a(tileentity1);
                    }

                    if (this.isLoaded(tileentity1.getPosition())) {
                        Chunk chunk = this.getChunkAtWorldCoords(tileentity1.getPosition());
                        IBlockData iblockdata = chunk.getType(tileentity1.getPosition());
                        chunk.a(tileentity1.getPosition(), tileentity1);
                        this.notify(tileentity1.getPosition(), iblockdata, iblockdata, 3);
                    }
                }
            }

            this.c.clear();
        }

        this.methodProfiler.e();
        this.methodProfiler.e();
    }

    protected void p_() {
    }

    public boolean a(TileEntity tileentity) {
        boolean flag = this.tileEntityList.add(tileentity);
        if (flag && tileentity instanceof ITickable) {
            this.tileEntityListTick.add(tileentity);
        }

        if (this.isClientSide) {
            BlockPosition blockposition = tileentity.getPosition();
            IBlockData iblockdata = this.getType(blockposition);
            this.notify(blockposition, iblockdata, iblockdata, 2);
        }

        return flag;
    }

    public void a(Collection<TileEntity> collection) {
        if (this.J) {
            this.c.addAll(collection);
        } else {
            for(TileEntity tileentity : collection) {
                this.a(tileentity);
            }
        }

    }

    public void g(Entity entity) {
        this.entityJoinedWorld(entity, true);
    }

    public void entityJoinedWorld(Entity entity, boolean flag) {
        if (!(entity instanceof EntityHuman)) {
            int i = MathHelper.floor(entity.locX);
            int j = MathHelper.floor(entity.locZ);
            boolean flag1 = true;
            if (flag && !this.isAreaLoaded(i - 32, 0, j - 32, i + 32, 0, j + 32, true)) {
                return;
            }
        }

        entity.N = entity.locX;
        entity.O = entity.locY;
        entity.P = entity.locZ;
        entity.lastYaw = entity.yaw;
        entity.lastPitch = entity.pitch;
        if (flag && entity.inChunk) {
            ++entity.ticksLived;
            if (entity.isPassenger()) {
                entity.aH();
            } else {
                this.methodProfiler.a(() -> {
                    return IRegistry.ENTITY_TYPE.getKey(entity.P()).toString();
                });
                entity.tick();
                this.methodProfiler.e();
            }
        }

        this.methodProfiler.a("chunkCheck");
        if (Double.isNaN(entity.locX) || Double.isInfinite(entity.locX)) {
            entity.locX = entity.N;
        }

        if (Double.isNaN(entity.locY) || Double.isInfinite(entity.locY)) {
            entity.locY = entity.O;
        }

        if (Double.isNaN(entity.locZ) || Double.isInfinite(entity.locZ)) {
            entity.locZ = entity.P;
        }

        if (Double.isNaN((double)entity.pitch) || Double.isInfinite((double)entity.pitch)) {
            entity.pitch = entity.lastPitch;
        }

        if (Double.isNaN((double)entity.yaw) || Double.isInfinite((double)entity.yaw)) {
            entity.yaw = entity.lastYaw;
        }

        int kx = MathHelper.floor(entity.locX / 16.0D);
        int l = MathHelper.floor(entity.locY / 16.0D);
        int i1 = MathHelper.floor(entity.locZ / 16.0D);
        if (!entity.inChunk || entity.ae != kx || entity.af != l || entity.ag != i1) {
            if (entity.inChunk && this.isChunkLoaded(entity.ae, entity.ag, true)) {
                this.getChunkAt(entity.ae, entity.ag).a(entity, entity.af);
            }

            if (!entity.bN() && !this.isChunkLoaded(kx, i1, true)) {
                entity.inChunk = false;
            } else {
                this.getChunkAt(kx, i1).a(entity);
            }
        }

        this.methodProfiler.e();
        if (flag && entity.inChunk) {
            for(Entity entity1 : entity.bP()) {
                if (!entity1.dead && entity1.getVehicle() == entity) {
                    this.g(entity1);
                } else {
                    entity1.stopRiding();
                }
            }
        }

    }

    public boolean a(@Nullable Entity entity, VoxelShape voxelshape) {
        if (voxelshape.b()) {
            return true;
        } else {
            List list = this.getEntities((Entity)null, voxelshape.a());

            for(int i = 0; i < list.size(); ++i) {
                Entity entity1 = (Entity)list.get(i);
                if (!entity1.dead && entity1.j && entity1 != entity && (entity == null || !entity1.x(entity)) && VoxelShapes.c(voxelshape, VoxelShapes.a(entity1.getBoundingBox()), OperatorBoolean.AND)) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean a(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int kx = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.e);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = kx; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        IBlockData iblockdata = this.getType(blockposition$b.f(k1, l1, i2));
                        if (!iblockdata.isAir()) {
                            boolean flag = true;
                            return flag;
                        }
                    }
                }
            }

            return false;
        }
    }

    public boolean b(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int kx = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.e);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);
        if (this.isAreaLoaded(i, kx, i1, j, l, j1, true)) {
            try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
                for(int k1 = i; k1 < j; ++k1) {
                    for(int l1 = kx; l1 < l; ++l1) {
                        for(int i2 = i1; i2 < j1; ++i2) {
                            Block block = this.getType(blockposition$b.f(k1, l1, i2)).getBlock();
                            if (block == Blocks.FIRE || block == Blocks.LAVA) {
                                boolean flag = true;
                                return flag;
                            }
                        }
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    public IBlockData a(AxisAlignedBB axisalignedbb, Block block) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int kx = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.e);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);
        if (this.isAreaLoaded(i, kx, i1, j, l, j1, true)) {
            try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
                for(int k1 = i; k1 < j; ++k1) {
                    for(int l1 = kx; l1 < l; ++l1) {
                        for(int i2 = i1; i2 < j1; ++i2) {
                            IBlockData iblockdata = this.getType(blockposition$b.f(k1, l1, i2));
                            if (iblockdata.getBlock() == block) {
                                IBlockData iblockdata1 = iblockdata;
                                return iblockdata1;
                            }
                        }
                    }
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public boolean a(AxisAlignedBB axisalignedbb, Material material) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int kx = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.e);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);
        MaterialPredicate materialpredicate = MaterialPredicate.a(material);

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = kx; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        if (materialpredicate.a(this.getType(blockposition$b.f(k1, l1, i2)))) {
                            boolean flag = true;
                            return flag;
                        }
                    }
                }
            }

            return false;
        }
    }

    public Explosion explode(@Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag) {
        return this.createExplosion(entity, (DamageSource)null, d0, d1, d2, f, false, flag);
    }

    public Explosion createExplosion(@Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        return this.createExplosion(entity, (DamageSource)null, d0, d1, d2, f, flag, flag1);
    }

    public Explosion createExplosion(@Nullable Entity entity, @Nullable DamageSource damagesource, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        Explosion explosion = new Explosion(this, entity, d0, d1, d2, f, flag, flag1);
        if (damagesource != null) {
            explosion.a(damagesource);
        }

        explosion.a();
        explosion.a(true);
        return explosion;
    }

    public float a(Vec3D vec3d, AxisAlignedBB axisalignedbb) {
        double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
        double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
        double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
        double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
        double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
        if (!(d0 < 0.0D) && !(d1 < 0.0D) && !(d2 < 0.0D)) {
            int i = 0;
            int j = 0;

            for(float f = 0.0F; f <= 1.0F; f = (float)((double)f + d0)) {
                for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1)) {
                    for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2)) {
                        double d5 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double)f;
                        double d6 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double)f1;
                        double d7 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double)f2;
                        if (this.rayTrace(new Vec3D(d5 + d3, d6, d7 + d4), vec3d) == null) {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return (float)i / (float)j;
        } else {
            return 0.0F;
        }
    }

    public boolean douseFire(@Nullable EntityHuman entityhuman, BlockPosition blockposition, EnumDirection enumdirection) {
        blockposition = blockposition.shift(enumdirection);
        if (this.getType(blockposition).getBlock() == Blocks.FIRE) {
            this.a(entityhuman, 1009, blockposition, 0);
            this.setAir(blockposition);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public TileEntity getTileEntity(BlockPosition blockposition) {
        if (k(blockposition)) {
            return null;
        } else {
            TileEntity tileentity = null;
            if (this.J) {
                tileentity = this.E(blockposition);
            }

            if (tileentity == null) {
                tileentity = this.getChunkAtWorldCoords(blockposition).a(blockposition, Chunk.EnumTileEntityState.IMMEDIATE);
            }

            if (tileentity == null) {
                tileentity = this.E(blockposition);
            }

            return tileentity;
        }
    }

    @Nullable
    private TileEntity E(BlockPosition blockposition) {
        for(int i = 0; i < this.c.size(); ++i) {
            TileEntity tileentity = (TileEntity)this.c.get(i);
            if (!tileentity.x() && tileentity.getPosition().equals(blockposition)) {
                return tileentity;
            }
        }

        return null;
    }

    public void setTileEntity(BlockPosition blockposition, @Nullable TileEntity tileentity) {
        if (!k(blockposition)) {
            if (tileentity != null && !tileentity.x()) {
                if (this.J) {
                    tileentity.setPosition(blockposition);
                    Iterator iterator = this.c.iterator();

                    while(iterator.hasNext()) {
                        TileEntity tileentity1 = (TileEntity)iterator.next();
                        if (tileentity1.getPosition().equals(blockposition)) {
                            tileentity1.y();
                            iterator.remove();
                        }
                    }

                    this.c.add(tileentity);
                } else {
                    this.getChunkAtWorldCoords(blockposition).a(blockposition, tileentity);
                    this.a(tileentity);
                }
            }

        }
    }

    public void n(BlockPosition blockposition) {
        TileEntity tileentity = this.getTileEntity(blockposition);
        if (tileentity != null && this.J) {
            tileentity.y();
            this.c.remove(tileentity);
        } else {
            if (tileentity != null) {
                this.c.remove(tileentity);
                this.tileEntityList.remove(tileentity);
                this.tileEntityListTick.remove(tileentity);
            }

            this.getChunkAtWorldCoords(blockposition).d(blockposition);
        }

    }

    public void b(TileEntity tileentity) {
        this.tileEntityListUnload.add(tileentity);
    }

    public boolean o(BlockPosition blockposition) {
        return Block.a(this.getType(blockposition).h(this, blockposition));
    }

    public boolean p(BlockPosition blockposition) {
        if (k(blockposition)) {
            return false;
        } else {
            Chunk chunk = this.chunkProvider.getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4, false, false);
            return chunk != null && !chunk.isEmpty();
        }
    }

    public boolean q(BlockPosition blockposition) {
        return this.p(blockposition) && this.getType(blockposition).q();
    }

    public void P() {
        int i = this.a(1.0F);
        if (i != this.G) {
            this.G = i;
        }

    }

    public void setSpawnFlags(boolean flag, boolean flag1) {
        this.allowMonsters = flag;
        this.allowAnimals = flag1;
    }

    public void doTick(BooleanSupplier var1) {
        this.K.r();
        this.w();
    }

    protected void Q() {
        if (this.worldData.hasStorm()) {
            this.p = 1.0F;
            if (this.worldData.isThundering()) {
                this.r = 1.0F;
            }
        }

    }

    public void close() {
        this.chunkProvider.close();
    }

    protected void w() {
        if (this.worldProvider.g()) {
            if (!this.isClientSide) {
                boolean flag = this.getGameRules().getBoolean("doWeatherCycle");
                if (flag) {
                    int i = this.worldData.z();
                    if (i > 0) {
                        --i;
                        this.worldData.g(i);
                        this.worldData.setThunderDuration(this.worldData.isThundering() ? 1 : 2);
                        this.worldData.setWeatherDuration(this.worldData.hasStorm() ? 1 : 2);
                    }

                    int j = this.worldData.getThunderDuration();
                    if (j <= 0) {
                        if (this.worldData.isThundering()) {
                            this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
                        } else {
                            this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
                        }
                    } else {
                        --j;
                        this.worldData.setThunderDuration(j);
                        if (j <= 0) {
                            this.worldData.setThundering(!this.worldData.isThundering());
                        }
                    }

                    int kx = this.worldData.getWeatherDuration();
                    if (kx <= 0) {
                        if (this.worldData.hasStorm()) {
                            this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
                        } else {
                            this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
                        }
                    } else {
                        --kx;
                        this.worldData.setWeatherDuration(kx);
                        if (kx <= 0) {
                            this.worldData.setStorm(!this.worldData.hasStorm());
                        }
                    }
                }

                this.q = this.r;
                if (this.worldData.isThundering()) {
                    this.r = (float)((double)this.r + 0.01D);
                } else {
                    this.r = (float)((double)this.r - 0.01D);
                }

                this.r = MathHelper.a(this.r, 0.0F, 1.0F);
                this.o = this.p;
                if (this.worldData.hasStorm()) {
                    this.p = (float)((double)this.p + 0.01D);
                } else {
                    this.p = (float)((double)this.p - 0.01D);
                }

                this.p = MathHelper.a(this.p, 0.0F, 1.0F);
            }
        }
    }

    protected void n_() {
    }

    public boolean r(BlockPosition blockposition) {
        boolean flag = false;
        if (this.worldProvider.g()) {
            flag |= this.c(EnumSkyBlock.SKY, blockposition);
        }

        flag = flag | this.c(EnumSkyBlock.BLOCK, blockposition);
        return flag;
    }

    private int a(BlockPosition blockposition, EnumSkyBlock enumskyblock) {
        if (enumskyblock == EnumSkyBlock.SKY && this.e(blockposition)) {
            return 15;
        } else {
            IBlockData iblockdata = this.getType(blockposition);
            int i = enumskyblock == EnumSkyBlock.SKY ? 0 : iblockdata.e();
            int j = iblockdata.b(this, blockposition);
            if (j >= 15 && iblockdata.e() > 0) {
                j = 1;
            }

            if (j < 1) {
                j = 1;
            }

            if (j >= 15) {
                return 0;
            } else if (i >= 14) {
                return i;
            } else {
                try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
                    for(EnumDirection enumdirection : a) {
                        blockposition$b.j(blockposition).d(enumdirection);
                        int kx = this.getBrightness(enumskyblock, blockposition$b) - j;
                        if (kx > i) {
                            i = kx;
                        }

                        if (i >= 14) {
                            int l = i;
                            return l;
                        }
                    }

                    return i;
                }
            }
        }
    }

    public boolean c(EnumSkyBlock enumskyblock, BlockPosition blockposition) {
        if (!this.areChunksLoaded(blockposition, 17, false)) {
            return false;
        } else {
            int i = 0;
            int j = 0;
            this.methodProfiler.a("getBrightness");
            int kx = this.getBrightness(enumskyblock, blockposition);
            int l = this.a(blockposition, enumskyblock);
            int i1 = blockposition.getX();
            int j1 = blockposition.getY();
            int k1 = blockposition.getZ();
            if (l > kx) {
                this.E[j++] = 133152;
            } else if (l < kx) {
                this.E[j++] = 133152 | kx << 18;

                while(i < j) {
                    int l1 = this.E[i++];
                    int i2 = (l1 & 63) - 32 + i1;
                    int j2 = (l1 >> 6 & 63) - 32 + j1;
                    int k2 = (l1 >> 12 & 63) - 32 + k1;
                    int l2 = l1 >> 18 & 15;
                    BlockPosition blockposition1 = new BlockPosition(i2, j2, k2);
                    int i3 = this.getBrightness(enumskyblock, blockposition1);
                    if (i3 == l2) {
                        this.a(enumskyblock, blockposition1, 0);
                        if (l2 > 0) {
                            int j3 = MathHelper.a(i2 - i1);
                            int k3 = MathHelper.a(j2 - j1);
                            int l3 = MathHelper.a(k2 - k1);
                            if (j3 + k3 + l3 < 17) {
                                try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
                                    for(EnumDirection enumdirection : a) {
                                        int i4 = i2 + enumdirection.getAdjacentX();
                                        int j4 = j2 + enumdirection.getAdjacentY();
                                        int k4 = k2 + enumdirection.getAdjacentZ();
                                        blockposition$b.f(i4, j4, k4);
                                        int l4 = Math.max(1, this.getType(blockposition$b).b(this, blockposition$b));
                                        i3 = this.getBrightness(enumskyblock, blockposition$b);
                                        if (i3 == l2 - l4 && j < this.E.length) {
                                            this.E[j++] = i4 - i1 + 32 | j4 - j1 + 32 << 6 | k4 - k1 + 32 << 12 | l2 - l4 << 18;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                i = 0;
            }

            this.methodProfiler.e();
            this.methodProfiler.a("checkedPosition < toCheckCount");

            while(i < j) {
                int i5 = this.E[i++];
                int j5 = (i5 & 63) - 32 + i1;
                int k5 = (i5 >> 6 & 63) - 32 + j1;
                int l5 = (i5 >> 12 & 63) - 32 + k1;
                BlockPosition blockposition2 = new BlockPosition(j5, k5, l5);
                int i6 = this.getBrightness(enumskyblock, blockposition2);
                int j6 = this.a(blockposition2, enumskyblock);
                if (j6 != i6) {
                    this.a(enumskyblock, blockposition2, j6);
                    if (j6 > i6) {
                        int k6 = Math.abs(j5 - i1);
                        int l6 = Math.abs(k5 - j1);
                        int i7 = Math.abs(l5 - k1);
                        boolean flag = j < this.E.length - 6;
                        if (k6 + l6 + i7 < 17 && flag) {
                            if (this.getBrightness(enumskyblock, blockposition2.west()) < j6) {
                                this.E[j++] = j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                            }

                            if (this.getBrightness(enumskyblock, blockposition2.east()) < j6) {
                                this.E[j++] = j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                            }

                            if (this.getBrightness(enumskyblock, blockposition2.down()) < j6) {
                                this.E[j++] = j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                            }

                            if (this.getBrightness(enumskyblock, blockposition2.up()) < j6) {
                                this.E[j++] = j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                            }

                            if (this.getBrightness(enumskyblock, blockposition2.north()) < j6) {
                                this.E[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
                            }

                            if (this.getBrightness(enumskyblock, blockposition2.south()) < j6) {
                                this.E[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
                            }
                        }
                    }
                }
            }

            this.methodProfiler.e();
            return true;
        }
    }

    public Stream<VoxelShape> a(@Nullable Entity entity, VoxelShape voxelshape, VoxelShape voxelshape1, Set<Entity> set) {
        Stream stream = super.a(entity, voxelshape, voxelshape1, set);
        return entity == null ? stream : Stream.concat(stream, this.a(entity, voxelshape, set));
    }

    public List<Entity> getEntities(@Nullable Entity entity, AxisAlignedBB axisalignedbb, @Nullable Predicate<? super Entity> predicate) {
        ArrayList arraylist = Lists.newArrayList();
        int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
        int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
        int kx = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
        int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);

        for(int i1 = i; i1 <= j; ++i1) {
            for(int j1 = kx; j1 <= l; ++j1) {
                if (this.isChunkLoaded(i1, j1, true)) {
                    this.getChunkAt(i1, j1).a(entity, axisalignedbb, arraylist, predicate);
                }
            }
        }

        return arraylist;
    }

    public <T extends Entity> List<T> a(Class<? extends T> oclass, Predicate<? super T> predicate) {
        ArrayList arraylist = Lists.newArrayList();

        for(Entity entity : this.entityList) {
            if (oclass.isAssignableFrom(entity.getClass()) && predicate.test(entity)) {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public <T extends Entity> List<T> b(Class<? extends T> oclass, Predicate<? super T> predicate) {
        ArrayList arraylist = Lists.newArrayList();

        for(Entity entity : this.players) {
            if (oclass.isAssignableFrom(entity.getClass()) && predicate.test(entity)) {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public <T extends Entity> List<T> a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb) {
        return this.a(oclass, axisalignedbb, IEntitySelector.f);
    }

    public <T extends Entity> List<T> a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, @Nullable Predicate<? super T> predicate) {
        int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
        int j = MathHelper.f((axisalignedbb.d + 2.0D) / 16.0D);
        int kx = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
        int l = MathHelper.f((axisalignedbb.f + 2.0D) / 16.0D);
        ArrayList arraylist = Lists.newArrayList();

        for(int i1 = i; i1 < j; ++i1) {
            for(int j1 = kx; j1 < l; ++j1) {
                if (this.isChunkLoaded(i1, j1, true)) {
                    this.getChunkAt(i1, j1).a(oclass, axisalignedbb, arraylist, predicate);
                }
            }
        }

        return arraylist;
    }

    @Nullable
    public <T extends Entity> T a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, T entity) {
        List list = this.a(oclass, axisalignedbb);
        Entity entity1 = null;
        double d0 = Double.MAX_VALUE;

        for(int i = 0; i < list.size(); ++i) {
            Entity entity2 = (Entity)list.get(i);
            if (entity2 != entity && IEntitySelector.f.test(entity2)) {
                double d1 = entity.h(entity2);
                if (!(d1 > d0)) {
                    entity1 = entity2;
                    d0 = d1;
                }
            }
        }

        return (T)entity1;
    }

    @Nullable
    public Entity getEntity(int i) {
        return this.entitiesById.get(i);
    }

    public void b(BlockPosition blockposition, TileEntity var2) {
        if (this.isLoaded(blockposition)) {
            this.getChunkAtWorldCoords(blockposition).markDirty();
        }

    }

    public int a(Class<?> oclass, int i) {
        int j = 0;
        Iterator iterator = this.entityList.iterator();

        while(true) {
            if (!iterator.hasNext()) {
                return j;
            }

            Entity entity = (Entity)iterator.next();
            if (!(entity instanceof EntityInsentient) || !((EntityInsentient)entity).isPersistent()) {
                if (oclass.isAssignableFrom(entity.getClass())) {
                    ++j;
                }

                if (j > i) {
                    break;
                }
            }
        }

        return j;
    }

    public void a(Stream<Entity> stream) {
        stream.forEach((entity) -> {
            this.entityList.add(entity);
            this.b(entity);
        });
    }

    public void b(Collection<Entity> collection) {
        this.g.addAll(collection);
    }

    public int getSeaLevel() {
        return this.b;
    }

    public World getMinecraftWorld() {
        return this;
    }

    public void b(int i) {
        this.b = i;
    }

    public int a(BlockPosition blockposition, EnumDirection enumdirection) {
        return this.getType(blockposition).b(this, blockposition, enumdirection);
    }

    public WorldType S() {
        return this.worldData.getType();
    }

    public int getBlockPower(BlockPosition blockposition) {
        int i = 0;
        i = Math.max(i, this.a(blockposition.down(), EnumDirection.DOWN));
        if (i >= 15) {
            return i;
        } else {
            i = Math.max(i, this.a(blockposition.up(), EnumDirection.UP));
            if (i >= 15) {
                return i;
            } else {
                i = Math.max(i, this.a(blockposition.north(), EnumDirection.NORTH));
                if (i >= 15) {
                    return i;
                } else {
                    i = Math.max(i, this.a(blockposition.south(), EnumDirection.SOUTH));
                    if (i >= 15) {
                        return i;
                    } else {
                        i = Math.max(i, this.a(blockposition.west(), EnumDirection.WEST));
                        if (i >= 15) {
                            return i;
                        } else {
                            i = Math.max(i, this.a(blockposition.east(), EnumDirection.EAST));
                            return i >= 15 ? i : i;
                        }
                    }
                }
            }
        }
    }

    public boolean isBlockFacePowered(BlockPosition blockposition, EnumDirection enumdirection) {
        return this.getBlockFacePower(blockposition, enumdirection) > 0;
    }

    public int getBlockFacePower(BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata = this.getType(blockposition);
        return iblockdata.isOccluding() ? this.getBlockPower(blockposition) : iblockdata.a(this, blockposition, enumdirection);
    }

    public boolean isBlockIndirectlyPowered(BlockPosition blockposition) {
        if (this.getBlockFacePower(blockposition.down(), EnumDirection.DOWN) > 0) {
            return true;
        } else if (this.getBlockFacePower(blockposition.up(), EnumDirection.UP) > 0) {
            return true;
        } else if (this.getBlockFacePower(blockposition.north(), EnumDirection.NORTH) > 0) {
            return true;
        } else if (this.getBlockFacePower(blockposition.south(), EnumDirection.SOUTH) > 0) {
            return true;
        } else if (this.getBlockFacePower(blockposition.west(), EnumDirection.WEST) > 0) {
            return true;
        } else {
            return this.getBlockFacePower(blockposition.east(), EnumDirection.EAST) > 0;
        }
    }

    public int u(BlockPosition blockposition) {
        int i = 0;

        for(EnumDirection enumdirection : a) {
            int j = this.getBlockFacePower(blockposition.shift(enumdirection), enumdirection);
            if (j >= 15) {
                return 15;
            }

            if (j > i) {
                i = j;
            }
        }

        return i;
    }

    @Nullable
    public EntityHuman a(double d0, double d1, double d2, double d3, Predicate<Entity> predicate) {
        double d4 = -1.0D;
        EntityHuman entityhuman = null;

        for(int i = 0; i < this.players.size(); ++i) {
            EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
            if (predicate.test(entityhuman1)) {
                double d5 = entityhuman1.d(d0, d1, d2);
                if ((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1.0D || d5 < d4)) {
                    d4 = d5;
                    entityhuman = entityhuman1;
                }
            }
        }

        return entityhuman;
    }

    public boolean isPlayerNearby(double d0, double d1, double d2, double d3) {
        for(int i = 0; i < this.players.size(); ++i) {
            EntityHuman entityhuman = (EntityHuman)this.players.get(i);
            if (IEntitySelector.f.test(entityhuman)) {
                double d4 = entityhuman.d(d0, d1, d2);
                if (d3 < 0.0D || d4 < d3 * d3) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean b(double d0, double d1, double d2, double d3) {
        for(EntityHuman entityhuman : this.players) {
            if (IEntitySelector.f.test(entityhuman) && IEntitySelector.b.test(entityhuman)) {
                double d4 = entityhuman.d(d0, d1, d2);
                if (d3 < 0.0D || d4 < d3 * d3) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nullable
    public EntityHuman a(Entity entity, double d0, double d1) {
        return this.a(entity.locX, entity.locY, entity.locZ, d0, d1, (Function)null, (Predicate)null);
    }

    @Nullable
    public EntityHuman a(BlockPosition blockposition, double d0, double d1) {
        return this.a((double)((float)blockposition.getX() + 0.5F), (double)((float)blockposition.getY() + 0.5F), (double)((float)blockposition.getZ() + 0.5F), d0, d1, (Function)null, (Predicate)null);
    }

    @Nullable
    public EntityHuman a(double d0, double d1, double d2, double d3, double d4, @Nullable Function<EntityHuman, Double> function, @Nullable Predicate<EntityHuman> predicate) {
        double d5 = -1.0D;
        EntityHuman entityhuman = null;

        for(int i = 0; i < this.players.size(); ++i) {
            EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
            if (!entityhuman1.abilities.isInvulnerable && entityhuman1.isAlive() && !entityhuman1.isSpectator() && (predicate == null || predicate.test(entityhuman1))) {
                double d6 = entityhuman1.d(d0, entityhuman1.locY, d2);
                double d7 = d3;
                if (entityhuman1.isSneaking()) {
                    d7 = d3 * (double)0.8F;
                }

                if (entityhuman1.isInvisible()) {
                    float f = entityhuman1.dk();
                    if (f < 0.1F) {
                        f = 0.1F;
                    }

                    d7 *= (double)(0.7F * f);
                }

                if (function != null) {
                    d7 *= MoreObjects.firstNonNull(function.apply(entityhuman1), 1.0D);
                }

                if ((d4 < 0.0D || Math.abs(entityhuman1.locY - d1) < d4 * d4) && (d3 < 0.0D || d6 < d7 * d7) && (d5 == -1.0D || d6 < d5)) {
                    d5 = d6;
                    entityhuman = entityhuman1;
                }
            }
        }

        return entityhuman;
    }

    @Nullable
    public EntityHuman a(String s) {
        for(int i = 0; i < this.players.size(); ++i) {
            EntityHuman entityhuman = (EntityHuman)this.players.get(i);
            if (s.equals(entityhuman.getDisplayName().getString())) {
                return entityhuman;
            }
        }

        return null;
    }

    @Nullable
    public EntityHuman b(UUID uuid) {
        for(int i = 0; i < this.players.size(); ++i) {
            EntityHuman entityhuman = (EntityHuman)this.players.get(i);
            if (uuid.equals(entityhuman.getUniqueID())) {
                return entityhuman;
            }
        }

        return null;
    }

    public void checkSession() throws ExceptionWorldConflict {
        this.dataManager.checkSession();
    }

    public long getSeed() {
        return this.worldData.getSeed();
    }

    public long getTime() {
        return this.worldData.getTime();
    }

    public long getDayTime() {
        return this.worldData.getDayTime();
    }

    public void setDayTime(long i) {
        this.worldData.setDayTime(i);
    }

    public BlockPosition getSpawn() {
        BlockPosition blockposition = new BlockPosition(this.worldData.b(), this.worldData.c(), this.worldData.d());
        if (!this.getWorldBorder().a(blockposition)) {
            blockposition = this.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, new BlockPosition(this.getWorldBorder().getCenterX(), 0.0D, this.getWorldBorder().getCenterZ()));
        }

        return blockposition;
    }

    public void v(BlockPosition blockposition) {
        this.worldData.setSpawn(blockposition);
    }

    public boolean a(EntityHuman var1, BlockPosition var2) {
        return true;
    }

    public void broadcastEntityEffect(Entity var1, byte var2) {
    }

    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }

    public void playBlockAction(BlockPosition blockposition, Block var2, int i, int j) {
        this.getType(blockposition).a(this, blockposition, i, j);
    }

    public IDataManager getDataManager() {
        return this.dataManager;
    }

    public WorldData getWorldData() {
        return this.worldData;
    }

    public GameRules getGameRules() {
        return this.worldData.w();
    }

    public void everyoneSleeping() {
    }

    public float g(float f) {
        return (this.q + (this.r - this.q) * f) * this.i(f);
    }

    public float i(float f) {
        return this.o + (this.p - this.o) * f;
    }

    public boolean Y() {
        if (this.worldProvider.g() && !this.worldProvider.h()) {
            return (double)this.g(1.0F) > 0.9D;
        } else {
            return false;
        }
    }

    public boolean isRaining() {
        return (double)this.i(1.0F) > 0.2D;
    }

    public boolean isRainingAt(BlockPosition blockposition) {
        if (!this.isRaining()) {
            return false;
        } else if (!this.e(blockposition)) {
            return false;
        } else if (this.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, blockposition).getY() > blockposition.getY()) {
            return false;
        } else {
            return this.getBiome(blockposition).c() == BiomeBase.Precipitation.RAIN;
        }
    }

    public boolean x(BlockPosition blockposition) {
        BiomeBase biomebase = this.getBiome(blockposition);
        return biomebase.d();
    }

    @Nullable
    public PersistentCollection h() {
        return this.worldMaps;
    }

    public void a(int i, BlockPosition blockposition, int j) {
        for(int kx = 0; kx < this.v.size(); ++kx) {
            ((IWorldAccess)this.v.get(kx)).a(i, blockposition, j);
        }

    }

    public void triggerEffect(int i, BlockPosition blockposition, int j) {
        this.a((EntityHuman)null, i, blockposition, j);
    }

    public void a(@Nullable EntityHuman entityhuman, int i, BlockPosition blockposition, int j) {
        try {
            for(int kx = 0; kx < this.v.size(); ++kx) {
                ((IWorldAccess)this.v.get(kx)).a(entityhuman, i, blockposition, j);
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
            crashreportsystemdetails.a("Block coordinates", CrashReportSystemDetails.a(blockposition));
            crashreportsystemdetails.a("Event source", entityhuman);
            crashreportsystemdetails.a("Event type", i);
            crashreportsystemdetails.a("Event data", j);
            throw new ReportedException(crashreport);
        }
    }

    public int getHeight() {
        return 256;
    }

    public int ab() {
        return this.worldProvider.h() ? 128 : 256;
    }

    public CrashReportSystemDetails a(CrashReport crashreport) {
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
        crashreportsystemdetails.a("Level name", this.worldData == null ? "????" : this.worldData.getName());
        crashreportsystemdetails.a("All players", () -> {
            return this.players.size() + " total; " + this.players;
        });
        crashreportsystemdetails.a("Chunk stats", () -> {
            return this.chunkProvider.getName();
        });

        try {
            this.worldData.a(crashreportsystemdetails);
        } catch (Throwable throwable) {
            crashreportsystemdetails.a("Level Data Unobtainable", throwable);
        }

        return crashreportsystemdetails;
    }

    public void c(int i, BlockPosition blockposition, int j) {
        for(int kx = 0; kx < this.v.size(); ++kx) {
            IWorldAccess iworldaccess = (IWorldAccess)this.v.get(kx);
            iworldaccess.b(i, blockposition, j);
        }

    }

    public abstract Scoreboard getScoreboard();

    public void updateAdjacentComparators(BlockPosition blockposition, Block block) {
        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);
            if (this.isLoaded(blockposition1)) {
                IBlockData iblockdata = this.getType(blockposition1);
                if (iblockdata.getBlock() == Blocks.COMPARATOR) {
                    iblockdata.doPhysics(this, blockposition1, block, blockposition);
                } else if (iblockdata.isOccluding()) {
                    blockposition1 = blockposition1.shift(enumdirection);
                    iblockdata = this.getType(blockposition1);
                    if (iblockdata.getBlock() == Blocks.COMPARATOR) {
                        iblockdata.doPhysics(this, blockposition1, block, blockposition);
                    }
                }
            }
        }

    }

    public DifficultyDamageScaler getDamageScaler(BlockPosition blockposition) {
        long i = 0L;
        float f = 0.0F;
        if (this.isLoaded(blockposition)) {
            f = this.ah();
            i = this.getChunkAtWorldCoords(blockposition).m();
        }

        return new DifficultyDamageScaler(this.getDifficulty(), this.getDayTime(), i, f);
    }

    public int c() {
        return this.G;
    }

    public void c(int i) {
        this.G = i;
    }

    public void d(int i) {
        this.H = i;
    }

    public PersistentVillage af() {
        return this.villages;
    }

    public WorldBorder getWorldBorder() {
        return this.K;
    }

    public boolean e(int i, int j) {
        BlockPosition blockposition = this.getSpawn();
        int kx = i * 16 + 8 - blockposition.getX();
        int l = j * 16 + 8 - blockposition.getZ();
        boolean flag = true;
        return kx >= -128 && kx <= 128 && l >= -128 && l <= 128;
    }

    public LongSet ag() {
        ForcedChunk forcedchunk = (ForcedChunk)this.a(this.worldProvider.getDimensionManager(), ForcedChunk::new, "chunks");
        return (LongSet)(forcedchunk != null ? LongSets.unmodifiable(forcedchunk.a()) : LongSets.EMPTY_SET);
    }

    public boolean f(int i, int j) {
        ForcedChunk forcedchunk = (ForcedChunk)this.a(this.worldProvider.getDimensionManager(), ForcedChunk::new, "chunks");
        return forcedchunk != null && forcedchunk.a().contains(ChunkCoordIntPair.a(i, j));
    }

    public boolean b(int i, int j, boolean flag) {
        String s = "chunks";
        ForcedChunk forcedchunk = (ForcedChunk)this.a(this.worldProvider.getDimensionManager(), ForcedChunk::new, "chunks");
        if (forcedchunk == null) {
            forcedchunk = new ForcedChunk("chunks");
            this.a(this.worldProvider.getDimensionManager(), "chunks", forcedchunk);
        }

        long kx = ChunkCoordIntPair.a(i, j);
        boolean flag1;
        if (flag) {
            flag1 = forcedchunk.a().add(kx);
            if (flag1) {
                this.getChunkAt(i, j);
            }
        } else {
            flag1 = forcedchunk.a().remove(kx);
        }

        forcedchunk.a(flag1);
        return flag1;
    }

    public void a(Packet<?> var1) {
        throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
    }

    @Nullable
    public BlockPosition a(String var1, BlockPosition var2, int var3, boolean var4) {
        return null;
    }

    public WorldProvider o() {
        return this.worldProvider;
    }

    public Random m() {
        return this.random;
    }

    public abstract CraftingManager E();

    public abstract TagRegistry F();

    // $FF: synthetic method
    public IChunkAccess b(int i, int j) {
        return this.getChunkAt(i, j);
    }
}
