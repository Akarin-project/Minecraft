package net.minecraft.server;

import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;

public interface IChunkProvider extends AutoCloseable {
    @Nullable
    Chunk getChunkAt(int var1, int var2, boolean var3, boolean var4);

    @Nullable
    default IChunkAccess a(int i, int j, boolean flag) {
        Chunk chunk = this.getChunkAt(i, j, true, false);
        if (chunk == null && flag) {
            throw new UnsupportedOperationException("Could not create an empty chunk");
        } else {
            return chunk;
        }
    }

    boolean unloadChunks(BooleanSupplier var1);

    String getName();

    ChunkGenerator<?> getChunkGenerator();

    default void close() {
    }
}
