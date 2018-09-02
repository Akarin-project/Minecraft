package net.minecraft.server;

public interface IWorldWriter {
    boolean setTypeAndData(BlockPosition var1, IBlockData var2, int var3);

    boolean addEntity(Entity var1);

    boolean setAir(BlockPosition var1);

    void a(EnumSkyBlock var1, BlockPosition var2, int var3);

    boolean setAir(BlockPosition var1, boolean var2);
}
