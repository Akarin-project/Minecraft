package net.minecraft.server;

public interface AreaContextTransformed<R extends Area> extends WorldGenContext {
    void a(long var1, long var3);

    R a(AreaDimension var1, AreaTransformer8 var2);

    default R a(AreaDimension areadimension, AreaTransformer8 areatransformer8, R var3) {
        return (R)this.a(areadimension, areatransformer8);
    }

    default R a(AreaDimension areadimension, AreaTransformer8 areatransformer8, R var3, R var4) {
        return (R)this.a(areadimension, areatransformer8);
    }

    int a(int... var1);
}
