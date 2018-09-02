package net.minecraft.server;

public class ChunkTaskBase extends ChunkTask {
    public ChunkTaskBase() {
    }

    protected ProtoChunk a(ChunkStatus var1, World var2, ChunkGenerator<?> chunkgenerator, ProtoChunk[] aprotochunk, int var5, int var6) {
        ProtoChunk protochunk = aprotochunk[aprotochunk.length / 2];
        chunkgenerator.createChunk(protochunk);
        return protochunk;
    }
}
