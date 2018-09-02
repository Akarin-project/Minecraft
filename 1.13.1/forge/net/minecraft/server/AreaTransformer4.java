package net.minecraft.server;

public interface AreaTransformer4 extends AreaTransformer2, AreaTransformerOffset1 {
    int a(WorldGenContext var1, int var2, int var3, int var4, int var5, int var6);

    default int a(AreaContextTransformed<?> areacontexttransformed, AreaDimension var2, Area area, int i, int j) {
        return this.a(areacontexttransformed, area.a(i + 0, j + 2), area.a(i + 2, j + 2), area.a(i + 2, j + 0), area.a(i + 0, j + 0), area.a(i + 1, j + 1));
    }
}
