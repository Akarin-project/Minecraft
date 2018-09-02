package net.minecraft.server;

public class FluidTypeEmpty extends FluidType {
    public FluidTypeEmpty() {
    }

    public Item b() {
        return Items.AIR;
    }

    public boolean a(Fluid var1, FluidType var2, EnumDirection var3) {
        return true;
    }

    public Vec3D a(IWorldReader var1, BlockPosition var2, Fluid var3) {
        return Vec3D.a;
    }

    public int a(IWorldReader var1) {
        return 0;
    }

    protected boolean c() {
        return true;
    }

    protected float d() {
        return 0.0F;
    }

    public float a(Fluid var1) {
        return 0.0F;
    }

    protected IBlockData b(Fluid var1) {
        return Blocks.AIR.getBlockData();
    }

    public boolean c(Fluid var1) {
        return false;
    }

    public int d(Fluid var1) {
        return 0;
    }
}
