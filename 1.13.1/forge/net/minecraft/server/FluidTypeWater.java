package net.minecraft.server;

public abstract class FluidTypeWater extends FluidTypeFlowing {
    public FluidTypeWater() {
    }

    public FluidType e() {
        return FluidTypes.b;
    }

    public FluidType f() {
        return FluidTypes.c;
    }

    public Item b() {
        return Items.WATER_BUCKET;
    }

    protected boolean g() {
        return true;
    }

    protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        iblockdata.a(generatoraccess.getMinecraftWorld(), blockposition, 0);
    }

    public int b(IWorldReader var1) {
        return 4;
    }

    public IBlockData b(Fluid fluid) {
        return (IBlockData)Blocks.WATER.getBlockData().set(BlockFluids.LEVEL, Integer.valueOf(e(fluid)));
    }

    public boolean a(FluidType fluidtype) {
        return fluidtype == FluidTypes.c || fluidtype == FluidTypes.b;
    }

    public int c(IWorldReader var1) {
        return 1;
    }

    public int a(IWorldReader var1) {
        return 5;
    }

    public boolean a(Fluid var1, FluidType fluidtype, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN && !fluidtype.a(TagsFluid.WATER);
    }

    protected float d() {
        return 100.0F;
    }

    public static class a extends FluidTypeWater {
        public a() {
        }

        protected void a(BlockStateList.a<FluidType, Fluid> blockstatelist$a) {
            super.a(blockstatelist$a);
            blockstatelist$a.a(LEVEL);
        }

        public int d(Fluid fluid) {
            return fluid.get(LEVEL);
        }

        public boolean c(Fluid var1) {
            return false;
        }
    }

    public static class b extends FluidTypeWater {
        public b() {
        }

        public int d(Fluid var1) {
            return 8;
        }

        public boolean c(Fluid var1) {
            return true;
        }
    }
}
