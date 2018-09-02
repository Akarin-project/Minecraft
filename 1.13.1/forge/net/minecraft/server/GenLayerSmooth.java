package net.minecraft.server;

public enum GenLayerSmooth implements AreaTransformer7 {
    INSTANCE;

    private GenLayerSmooth() {
    }

    public int a(WorldGenContext worldgencontext, int i, int j, int k, int l, int i1) {
        boolean flag = j == l;
        boolean flag1 = i == k;
        if (flag == flag1) {
            if (flag) {
                return worldgencontext.a(2) == 0 ? l : i;
            } else {
                return i1;
            }
        } else {
            return flag ? l : i;
        }
    }
}
