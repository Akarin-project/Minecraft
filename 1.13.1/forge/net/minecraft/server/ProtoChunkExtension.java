package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class ProtoChunkExtension extends ProtoChunk {
    private final IChunkAccess a;

    public ProtoChunkExtension(IChunkAccess ichunkaccess) {
        super(ichunkaccess.getPos(), ChunkConverter.a);
        this.a = ichunkaccess;
    }

    @Nullable
    public TileEntity getTileEntity(BlockPosition blockposition) {
        return this.a.getTileEntity(blockposition);
    }

    @Nullable
    public IBlockData getType(BlockPosition blockposition) {
        return this.a.getType(blockposition);
    }

    public Fluid b(BlockPosition blockposition) {
        return this.a.b(blockposition);
    }

    public int K() {
        return this.a.K();
    }

    @Nullable
    public IBlockData a(BlockPosition var1, IBlockData var2, boolean var3) {
        return null;
    }

    public void a(BlockPosition var1, TileEntity var2) {
    }

    public void a(Entity var1) {
    }

    public void a(ChunkStatus var1) {
    }

    public ChunkSection[] getSections() {
        return this.a.getSections();
    }

    public int a(EnumSkyBlock enumskyblock, BlockPosition blockposition, boolean flag) {
        return this.a.a(enumskyblock, blockposition, flag);
    }

    public int a(BlockPosition blockposition, int i, boolean flag) {
        return this.a.a(blockposition, i, flag);
    }

    public boolean c(BlockPosition blockposition) {
        return this.a.c(blockposition);
    }

    public void a(HeightMap.Type var1, long[] var2) {
    }

    private HeightMap.Type c(HeightMap.Type heightmap$type) {
        if (heightmap$type == HeightMap.Type.WORLD_SURFACE_WG) {
            return HeightMap.Type.WORLD_SURFACE;
        } else {
            return heightmap$type == HeightMap.Type.OCEAN_FLOOR_WG ? HeightMap.Type.OCEAN_FLOOR : heightmap$type;
        }
    }

    public int a(HeightMap.Type heightmap$type, int i, int j) {
        return this.a.a(this.c(heightmap$type), i, j);
    }

    public ChunkCoordIntPair getPos() {
        return this.a.getPos();
    }

    public void setLastSaved(long var1) {
    }

    @Nullable
    public StructureStart a(String s) {
        return this.a.a(s);
    }

    public void a(String var1, StructureStart var2) {
    }

    public Map<String, StructureStart> e() {
        return this.a.e();
    }

    public void a(Map<String, StructureStart> var1) {
    }

    @Nullable
    public LongSet b(String s) {
        return this.a.b(s);
    }

    public void a(String var1, long var2) {
    }

    public Map<String, LongSet> f() {
        return this.a.f();
    }

    public void b(Map<String, LongSet> var1) {
    }

    public BiomeBase[] getBiomeIndex() {
        return this.a.getBiomeIndex();
    }

    public void a(boolean var1) {
    }

    public boolean h() {
        return false;
    }

    public ChunkStatus i() {
        return this.a.i();
    }

    public void d(BlockPosition var1) {
    }

    public void a(EnumSkyBlock enumskyblock, boolean flag, BlockPosition blockposition, int i) {
        this.a.a(enumskyblock, flag, blockposition, i);
    }

    public void e(BlockPosition var1) {
    }

    public void a(NBTTagCompound var1) {
    }

    @Nullable
    public NBTTagCompound g(BlockPosition blockposition) {
        return this.a.g(blockposition);
    }

    public void a(BiomeBase[] var1) {
    }

    public void a(HeightMap.Type... var1) {
    }

    public List<BlockPosition> j() {
        return this.a.j();
    }

    public ProtoChunkTickList<Block> n() {
        return new ProtoChunkTickList<Block>((block) -> {
            return block.getBlockData().isAir();
        }, IRegistry.BLOCK::getKey, IRegistry.BLOCK::getOrDefault, this.getPos());
    }

    public ProtoChunkTickList<FluidType> o() {
        return new ProtoChunkTickList<FluidType>((fluidtype) -> {
            return fluidtype == FluidTypes.a;
        }, IRegistry.FLUID::getKey, IRegistry.FLUID::getOrDefault, this.getPos());
    }

    public BitSet a(WorldGenStage.Features worldgenstage$features) {
        return this.a.a(worldgenstage$features);
    }

    public void b(boolean var1) {
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
