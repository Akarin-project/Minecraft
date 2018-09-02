package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;

public interface IChunkAccess extends IBlockAccess {
    @Nullable
    IBlockData a(BlockPosition var1, IBlockData var2, boolean var3);

    void a(BlockPosition var1, TileEntity var2);

    void a(Entity var1);

    void a(ChunkStatus var1);

    @Nullable
    default ChunkSection a() {
        ChunkSection[] achunksection = this.getSections();

        for(int i = achunksection.length - 1; i >= 0; --i) {
            if (achunksection[i] != Chunk.a) {
                return achunksection[i];
            }
        }

        return null;
    }

    default int b() {
        ChunkSection chunksection = this.a();
        return chunksection == null ? 0 : chunksection.getYPosition();
    }

    ChunkSection[] getSections();

    int a(EnumSkyBlock var1, BlockPosition var2, boolean var3);

    int a(BlockPosition var1, int var2, boolean var3);

    boolean c(BlockPosition var1);

    int a(HeightMap.Type var1, int var2, int var3);

    ChunkCoordIntPair getPos();

    void setLastSaved(long var1);

    @Nullable
    StructureStart a(String var1);

    void a(String var1, StructureStart var2);

    Map<String, StructureStart> e();

    @Nullable
    LongSet b(String var1);

    void a(String var1, long var2);

    Map<String, LongSet> f();

    BiomeBase[] getBiomeIndex();

    ChunkStatus i();

    void d(BlockPosition var1);

    void a(EnumSkyBlock var1, boolean var2, BlockPosition var3, int var4);

    default void e(BlockPosition blockposition) {
        LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", blockposition);
    }

    default void a(NBTTagCompound var1) {
        LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
    }

    @Nullable
    default NBTTagCompound g(BlockPosition var1) {
        throw new UnsupportedOperationException();
    }

    default void a(BiomeBase[] var1) {
        throw new UnsupportedOperationException();
    }

    default void a(HeightMap.Type... var1) {
        throw new UnsupportedOperationException();
    }

    default List<BlockPosition> j() {
        throw new UnsupportedOperationException();
    }

    TickList<Block> k();

    TickList<FluidType> l();

    BitSet a(WorldGenStage.Features var1);
}
