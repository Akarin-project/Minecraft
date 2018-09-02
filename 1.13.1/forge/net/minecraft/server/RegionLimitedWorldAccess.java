package net.minecraft.server;

import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionLimitedWorldAccess implements GeneratorAccess {
    private static final Logger a = LogManager.getLogger();
    private final ProtoChunk[] b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final World g;
    private final long h;
    private final int i;
    private final WorldData j;
    private final Random k;
    private final WorldProvider l;
    private final GeneratorSettings m;
    private final TickList<Block> n = new TickListWorldGen<Block>((blockposition) -> {
        return this.y(blockposition).k();
    });
    private final TickList<FluidType> o = new TickListWorldGen<FluidType>((blockposition) -> {
        return this.y(blockposition).l();
    });

    public RegionLimitedWorldAccess(ProtoChunk[] aprotochunk, int ix, int jx, int kx, int lx, World world) {
        this.b = aprotochunk;
        this.c = kx;
        this.d = lx;
        this.e = ix;
        this.f = jx;
        this.g = world;
        this.h = world.getSeed();
        this.m = world.getChunkProvider().getChunkGenerator().getSettings();
        this.i = world.getSeaLevel();
        this.j = world.getWorldData();
        this.k = world.m();
        this.l = world.o();
    }

    public int a() {
        return this.c;
    }

    public int b() {
        return this.d;
    }

    public boolean a(int ix, int jx) {
        ProtoChunk protochunk = this.b[0];
        ProtoChunk protochunk1 = this.b[this.b.length - 1];
        return ix >= protochunk.getPos().x && ix <= protochunk1.getPos().x && jx >= protochunk.getPos().z && jx <= protochunk1.getPos().z;
    }

    public IChunkAccess b(int ix, int jx) {
        if (this.a(ix, jx)) {
            int kx = ix - this.b[0].getPos().x;
            int lx = jx - this.b[0].getPos().z;
            return this.b[kx + lx * this.e];
        } else {
            ProtoChunk protochunk = this.b[0];
            ProtoChunk protochunk1 = this.b[this.b.length - 1];
            a.error("Requested chunk : {} {}", ix, jx);
            a.error("Region bounds : {} {} | {} {}", protochunk.getPos().x, protochunk.getPos().z, protochunk1.getPos().x, protochunk1.getPos().z);
            throw new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", ix, jx));
        }
    }

    public IBlockData getType(BlockPosition blockposition) {
        return this.y(blockposition).getType(blockposition);
    }

    public Fluid b(BlockPosition blockposition) {
        return this.y(blockposition).b(blockposition);
    }

    @Nullable
    public EntityHuman a(double var1, double var3, double var5, double var7, Predicate<Entity> var9) {
        return null;
    }

    public int c() {
        return 0;
    }

    public boolean isEmpty(BlockPosition blockposition) {
        return this.getType(blockposition).isAir();
    }

    public BiomeBase getBiome(BlockPosition blockposition) {
        BiomeBase biomebase = this.y(blockposition).getBiomeIndex()[blockposition.getX() & 15 | (blockposition.getZ() & 15) << 4];
        if (biomebase == null) {
            throw new RuntimeException(String.format("Biome is null @ %s", blockposition));
        } else {
            return biomebase;
        }
    }

    public int getBrightness(EnumSkyBlock enumskyblock, BlockPosition blockposition) {
        IChunkAccess ichunkaccess = this.y(blockposition);
        return ichunkaccess.a(enumskyblock, blockposition, this.o().g());
    }

    public int getLightLevel(BlockPosition blockposition, int ix) {
        return this.y(blockposition).a(blockposition, ix, this.o().g());
    }

    public boolean isChunkLoaded(int ix, int jx, boolean var3) {
        return this.a(ix, jx);
    }

    public boolean setAir(BlockPosition blockposition, boolean flag) {
        IBlockData iblockdata = this.getType(blockposition);
        if (iblockdata.isAir()) {
            return false;
        } else {
            if (flag) {
                iblockdata.a(this.g, blockposition, 0);
            }

            return this.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
        }
    }

    public boolean e(BlockPosition blockposition) {
        return this.y(blockposition).c(blockposition);
    }

    @Nullable
    public TileEntity getTileEntity(BlockPosition blockposition) {
        IChunkAccess ichunkaccess = this.y(blockposition);
        TileEntity tileentity = ichunkaccess.getTileEntity(blockposition);
        if (tileentity != null) {
            return tileentity;
        } else {
            NBTTagCompound nbttagcompound = ichunkaccess.g(blockposition);
            if (nbttagcompound != null) {
                if ("DUMMY".equals(nbttagcompound.getString("id"))) {
                    tileentity = ((ITileEntity)this.getType(blockposition).getBlock()).a(this.g);
                } else {
                    tileentity = TileEntity.create(nbttagcompound);
                }

                if (tileentity != null) {
                    ichunkaccess.a(blockposition, tileentity);
                    return tileentity;
                }
            }

            if (ichunkaccess.getType(blockposition).getBlock() instanceof ITileEntity) {
                a.warn("Tried to access a block entity before it was created. {}", blockposition);
            }

            return null;
        }
    }

    public boolean setTypeAndData(BlockPosition blockposition, IBlockData iblockdata, int var3) {
        IChunkAccess ichunkaccess = this.y(blockposition);
        IBlockData iblockdata1 = ichunkaccess.a(blockposition, iblockdata, false);
        Block block = iblockdata.getBlock();
        if (block.isTileEntity()) {
            if (ichunkaccess.i().d() == ChunkStatus.Type.LEVELCHUNK) {
                ichunkaccess.a(blockposition, ((ITileEntity)block).a(this));
            } else {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInt("x", blockposition.getX());
                nbttagcompound.setInt("y", blockposition.getY());
                nbttagcompound.setInt("z", blockposition.getZ());
                nbttagcompound.setString("id", "DUMMY");
                ichunkaccess.a(nbttagcompound);
            }
        } else if (iblockdata1 != null && iblockdata1.getBlock().isTileEntity()) {
            ichunkaccess.d(blockposition);
        }

        if (iblockdata.l(this, blockposition)) {
            this.i(blockposition);
        }

        return true;
    }

    private void i(BlockPosition blockposition) {
        this.y(blockposition).e(blockposition);
    }

    public boolean addEntity(Entity entity) {
        int ix = MathHelper.floor(entity.locX / 16.0D);
        int jx = MathHelper.floor(entity.locZ / 16.0D);
        this.b(ix, jx).a(entity);
        return true;
    }

    public boolean setAir(BlockPosition blockposition) {
        return this.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
    }

    public void a(EnumSkyBlock enumskyblock, BlockPosition blockposition, int ix) {
        this.y(blockposition).a(enumskyblock, this.l.g(), blockposition, ix);
    }

    public WorldBorder getWorldBorder() {
        return this.g.getWorldBorder();
    }

    public boolean a(@Nullable Entity var1, VoxelShape var2) {
        return true;
    }

    public int a(BlockPosition blockposition, EnumDirection enumdirection) {
        return this.getType(blockposition).b(this, blockposition, enumdirection);
    }

    public boolean e() {
        return false;
    }

    @Deprecated
    public World getMinecraftWorld() {
        return this.g;
    }

    public WorldData getWorldData() {
        return this.j;
    }

    public DifficultyDamageScaler getDamageScaler(BlockPosition blockposition) {
        if (!this.a(blockposition.getX() >> 4, blockposition.getZ() >> 4)) {
            throw new RuntimeException("We are asking a region for a chunk out of bound");
        } else {
            return new DifficultyDamageScaler(this.g.getDifficulty(), this.g.getDayTime(), 0L, this.g.ah());
        }
    }

    @Nullable
    public PersistentCollection h() {
        return this.g.h();
    }

    public IChunkProvider getChunkProvider() {
        return this.g.getChunkProvider();
    }

    public IDataManager getDataManager() {
        return this.g.getDataManager();
    }

    public long getSeed() {
        return this.h;
    }

    public TickList<Block> J() {
        return this.n;
    }

    public TickList<FluidType> I() {
        return this.o;
    }

    public int getSeaLevel() {
        return this.i;
    }

    public Random m() {
        return this.k;
    }

    public void update(BlockPosition var1, Block var2) {
    }

    public int a(HeightMap.Type heightmap$type, int ix, int jx) {
        return this.b(ix >> 4, jx >> 4).a(heightmap$type, ix & 15, jx & 15) + 1;
    }

    public void a(@Nullable EntityHuman var1, BlockPosition var2, SoundEffect var3, SoundCategory var4, float var5, float var6) {
    }

    public void addParticle(ParticleParam var1, double var2, double var4, double var6, double var8, double var10, double var12) {
    }

    public BlockPosition getSpawn() {
        return this.g.getSpawn();
    }

    public WorldProvider o() {
        return this.l;
    }
}
