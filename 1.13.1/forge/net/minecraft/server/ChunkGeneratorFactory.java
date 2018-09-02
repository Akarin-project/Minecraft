package net.minecraft.server;

interface ChunkGeneratorFactory<C extends GeneratorSettings, T extends ChunkGenerator<C>> {
    T create(World var1, WorldChunkManager var2, C var3);
}
