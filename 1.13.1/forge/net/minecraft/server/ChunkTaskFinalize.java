package net.minecraft.server;

public class ChunkTaskFinalize extends ChunkTask {
    public ChunkTaskFinalize() {
    }

    protected ProtoChunk a(ChunkStatus var1, World var2, ChunkGenerator<?> var3, ProtoChunk[] aprotochunk, int var5, int var6) {
        ProtoChunk protochunk = aprotochunk[aprotochunk.length / 2];
        protochunk.a(ChunkStatus.FINALIZED);
        protochunk.a(HeightMap.Type.MOTION_BLOCKING, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, HeightMap.Type.LIGHT_BLOCKING, HeightMap.Type.OCEAN_FLOOR, HeightMap.Type.WORLD_SURFACE);
        return protochunk;
    }
}
