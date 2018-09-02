package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public interface GeneratorAccess extends IWorldReader, IPersistentAccess, IWorldWriter {
    long getSeed();

    default float ah() {
        return WorldProvider.a[this.o().a(this.getWorldData().getDayTime())];
    }

    default float k(float f) {
        return this.o().a(this.getWorldData().getDayTime(), f);
    }

    TickList<Block> J();

    TickList<FluidType> I();

    default IChunkAccess y(BlockPosition blockposition) {
        return this.b(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }

    IChunkAccess b(int var1, int var2);

    World getMinecraftWorld();

    WorldData getWorldData();

    DifficultyDamageScaler getDamageScaler(BlockPosition var1);

    default EnumDifficulty getDifficulty() {
        return this.getWorldData().getDifficulty();
    }

    IChunkProvider getChunkProvider();

    IDataManager getDataManager();

    Random m();

    void update(BlockPosition var1, Block var2);

    BlockPosition getSpawn();

    void a(@Nullable EntityHuman var1, BlockPosition var2, SoundEffect var3, SoundCategory var4, float var5, float var6);

    void addParticle(ParticleParam var1, double var2, double var4, double var6, double var8, double var10, double var12);
}
