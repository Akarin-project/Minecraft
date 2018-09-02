package net.minecraft.server;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Random;

public abstract class FluidType {
    public static final RegistryBlockID<Fluid> c = new RegistryBlockID<Fluid>();
    protected final BlockStateList<FluidType, Fluid> d;
    private Fluid a;

    protected FluidType() {
        BlockStateList.a blockstatelist$a = new BlockStateList.a(this);
        this.a(blockstatelist$a);
        this.d = blockstatelist$a.a(FluidImpl::new);
        this.f(this.d.getBlockData());
    }

    protected void a(BlockStateList.a<FluidType, Fluid> var1) {
    }

    public BlockStateList<FluidType, Fluid> h() {
        return this.d;
    }

    protected final void f(Fluid fluid) {
        this.a = fluid;
    }

    public final Fluid i() {
        return this.a;
    }

    public abstract Item b();

    protected void a(World var1, BlockPosition var2, Fluid var3) {
    }

    protected void b(World var1, BlockPosition var2, Fluid var3, Random var4) {
    }

    protected abstract boolean a(Fluid var1, FluidType var2, EnumDirection var3);

    protected abstract Vec3D a(IWorldReader var1, BlockPosition var2, Fluid var3);

    public abstract int a(IWorldReader var1);

    protected boolean k() {
        return false;
    }

    protected boolean c() {
        return false;
    }

    protected abstract float d();

    public abstract float a(Fluid var1);

    protected abstract IBlockData b(Fluid var1);

    public abstract boolean c(Fluid var1);

    public abstract int d(Fluid var1);

    public boolean a(FluidType fluidtype1) {
        return fluidtype1 == this;
    }

    public boolean a(Tag<FluidType> tag) {
        return tag.isTagged(this);
    }

    public static void l() {
        a(IRegistry.FLUID.b(), new FluidTypeEmpty());
        a("flowing_water", new FluidTypeWater.a());
        a("water", new FluidTypeWater.b());
        a("flowing_lava", new FluidTypeLava.a());
        a("lava", new FluidTypeLava.b());

        for(FluidType fluidtype : IRegistry.FLUID) {
            UnmodifiableIterator unmodifiableiterator = fluidtype.h().a().iterator();

            while(unmodifiableiterator.hasNext()) {
                Fluid fluid = (Fluid)unmodifiableiterator.next();
                c.b(fluid);
            }
        }

    }

    private static void a(String s, FluidType fluidtype) {
        a(new MinecraftKey(s), fluidtype);
    }

    private static void a(MinecraftKey minecraftkey, FluidType fluidtype) {
        IRegistry.FLUID.a(minecraftkey, fluidtype);
    }
}
