package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import java.util.BitSet;
import java.util.Set;

public abstract class WorldGenCarverAbstract<C extends WorldGenFeatureConfiguration> implements WorldGenCarver<C> {
    protected static final IBlockData a = Blocks.AIR.getBlockData();
    protected static final IBlockData b = Blocks.CAVE_AIR.getBlockData();
    protected static final Fluid c = FluidTypes.c.i();
    protected static final Fluid d = FluidTypes.e.i();
    protected Set<Block> e = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, new Block[]{Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW, Blocks.PACKED_ICE});
    protected Set<FluidType> f = ImmutableSet.of(FluidTypes.c);

    public WorldGenCarverAbstract() {
    }

    public int a() {
        return 4;
    }

    protected abstract boolean a(GeneratorAccess var1, long var2, int var4, int var5, double var6, double var8, double var10, double var12, double var14, BitSet var16);

    protected boolean a(IBlockData iblockdata) {
        return this.e.contains(iblockdata.getBlock());
    }

    protected boolean a(IBlockData iblockdata, IBlockData iblockdata1) {
        Block block = iblockdata.getBlock();
        return this.a(iblockdata) || (block == Blocks.SAND || block == Blocks.GRAVEL) && !iblockdata1.s().a(TagsFluid.WATER);
    }

    protected boolean a(IWorldReader iworldreader, int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int i2 = k; i2 < l; ++i2) {
            for(int j2 = k1; j2 < l1; ++j2) {
                for(int k2 = i1 - 1; k2 <= j1 + 1; ++k2) {
                    if (this.f.contains(iworldreader.b(blockposition$mutableblockposition.c(i2 + i * 16, k2, j2 + j * 16)).c())) {
                        return true;
                    }

                    if (k2 != j1 + 1 && !this.a(k, l, k1, l1, i2, j2)) {
                        k2 = j1;
                    }
                }
            }
        }

        return false;
    }

    private boolean a(int i, int j, int k, int l, int i1, int j1) {
        return i1 == i || i1 == j - 1 || j1 == k || j1 == l - 1;
    }

    protected boolean a(int i, int j, double d0, double d1, int k, int l, float fx) {
        double d2 = (double)(i * 16 + 8);
        double d3 = (double)(j * 16 + 8);
        double d4 = d0 - d2;
        double d5 = d1 - d3;
        double d6 = (double)(l - k);
        double d7 = (double)(fx + 2.0F + 16.0F);
        return d4 * d4 + d5 * d5 - d6 * d6 <= d7 * d7;
    }
}
