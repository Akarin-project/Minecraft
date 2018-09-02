package net.minecraft.server;

import java.util.Collection;

public class TagsFluid {
    private static Tags<FluidType> c = new Tags<FluidType>((var0) -> {
        return false;
    }, (var0) -> {
        return null;
    }, "", false, "");
    private static int d;
    public static final Tag<FluidType> WATER = a("water");
    public static final Tag<FluidType> LAVA = a("lava");

    public static void a(Tags<FluidType> tags) {
        c = tags;
        ++d;
    }

    private static Tag<FluidType> a(String s) {
        return new TagsFluid.a(new MinecraftKey(s));
    }

    public static class a extends Tag<FluidType> {
        private int a = -1;
        private Tag<FluidType> b;

        public a(MinecraftKey minecraftkey) {
            super(minecraftkey);
        }

        public boolean a(FluidType fluidtype) {
            if (this.a != TagsFluid.d) {
                this.b = TagsFluid.c.b(this.c());
                this.a = TagsFluid.d;
            }

            return this.b.isTagged(fluidtype);
        }

        public Collection<FluidType> a() {
            if (this.a != TagsFluid.d) {
                this.b = TagsFluid.c.b(this.c());
                this.a = TagsFluid.d;
            }

            return this.b.a();
        }

        public Collection<Tag.b<FluidType>> b() {
            if (this.a != TagsFluid.d) {
                this.b = TagsFluid.c.b(this.c());
                this.a = TagsFluid.d;
            }

            return this.b.b();
        }
    }
}
