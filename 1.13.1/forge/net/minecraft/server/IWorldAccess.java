package net.minecraft.server;

import javax.annotation.Nullable;

public interface IWorldAccess {
    void a(IBlockAccess var1, BlockPosition var2, IBlockData var3, IBlockData var4, int var5);

    void a(BlockPosition var1);

    void a(int var1, int var2, int var3, int var4, int var5, int var6);

    void a(@Nullable EntityHuman var1, SoundEffect var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11);

    void a(SoundEffect var1, BlockPosition var2);

    void a(ParticleParam var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13);

    void a(ParticleParam var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14);

    void a(Entity var1);

    void b(Entity var1);

    void a(int var1, BlockPosition var2, int var3);

    void a(EntityHuman var1, int var2, BlockPosition var3, int var4);

    void b(int var1, BlockPosition var2, int var3);
}
