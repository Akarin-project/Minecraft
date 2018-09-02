package net.minecraft.server;

public interface AreaTransformer2 extends AreaTransformer {
    default <R extends Area> AreaFactory<R> a(AreaContextTransformed<R> areacontexttransformed, AreaFactory<R> areafactory) {
        return (areadimension) -> {
            Area area = areafactory.make(this.a(areadimension));
            return areacontexttransformed.a(areadimension, (i, j) -> {
                areacontexttransformed.a((long)(i + areadimension.a()), (long)(j + areadimension.b()));
                return this.a(areacontexttransformed, areadimension, area, i, j);
            }, area);
        };
    }

    int a(AreaContextTransformed<?> var1, AreaDimension var2, Area var3, int var4, int var5);
}
