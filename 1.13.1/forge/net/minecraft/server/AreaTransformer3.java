package net.minecraft.server;

public interface AreaTransformer3 extends AreaTransformer {
    default <R extends Area> AreaFactory<R> a(AreaContextTransformed<R> areacontexttransformed, AreaFactory<R> areafactory, AreaFactory<R> areafactory1) {
        return (areadimension) -> {
            Area area = areafactory.make(this.a(areadimension));
            Area area1 = areafactory1.make(this.a(areadimension));
            return areacontexttransformed.a(areadimension, (i, j) -> {
                areacontexttransformed.a((long)(i + areadimension.a()), (long)(j + areadimension.b()));
                return this.a((WorldGenContext)areacontexttransformed, areadimension, area, area1, i, j);
            }, area, area1);
        };
    }

    int a(WorldGenContext var1, AreaDimension var2, Area var3, Area var4, int var5, int var6);
}
