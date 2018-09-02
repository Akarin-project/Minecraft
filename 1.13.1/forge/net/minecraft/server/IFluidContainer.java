package net.minecraft.server;

public interface IFluidContainer {
    boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData var3, FluidType var4);

    boolean place(GeneratorAccess var1, BlockPosition var2, IBlockData var3, Fluid var4);
}
