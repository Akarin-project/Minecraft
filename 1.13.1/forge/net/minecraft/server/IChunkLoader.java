package net.minecraft.server;

import java.io.IOException;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public interface IChunkLoader {
    @Nullable
    Chunk a(GeneratorAccess var1, int var2, int var3, Consumer<Chunk> var4) throws IOException;

    @Nullable
    ProtoChunk b(GeneratorAccess var1, int var2, int var3, Consumer<IChunkAccess> var4) throws IOException;

    void saveChunk(World var1, IChunkAccess var2) throws IOException, ExceptionWorldConflict;

    void b();
}
