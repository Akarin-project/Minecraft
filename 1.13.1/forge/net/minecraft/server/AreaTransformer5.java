package net.minecraft.server;

public interface AreaTransformer5 extends AreaTransformer2, AreaTransformerIdentity {
    int a(WorldGenContext var1, int var2);

    default int a(AreaContextTransformed<?> areacontexttransformed, AreaDimension var2, Area area, int i, int j) {
        return this.a(areacontexttransformed, area.a(i, j));
    }
}
