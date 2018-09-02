package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class WorldChunkManagerHell extends WorldChunkManager {
    private final BiomeBase c;

    public WorldChunkManagerHell(BiomeLayoutFixedConfiguration biomelayoutfixedconfiguration) {
        this.c = biomelayoutfixedconfiguration.a();
    }

    public BiomeBase getBiome(BlockPosition var1, @Nullable BiomeBase var2) {
        return this.c;
    }

    public BiomeBase[] getBiomes(int i, int j, int k, int l) {
        return this.getBiomeBlock(i, j, k, l);
    }

    public BiomeBase[] a(int var1, int var2, int i, int j, boolean var5) {
        BiomeBase[] abiomebase = new BiomeBase[i * j];
        Arrays.fill(abiomebase, 0, i * j, this.c);
        return abiomebase;
    }

    @Nullable
    public BlockPosition a(int i, int j, int k, List<BiomeBase> list, Random random) {
        return list.contains(this.c) ? new BlockPosition(i - k + random.nextInt(k * 2 + 1), 0, j - k + random.nextInt(k * 2 + 1)) : null;
    }

    public boolean a(StructureGenerator<?> structuregenerator) {
        return this.a.computeIfAbsent(structuregenerator, this.c::a);
    }

    public Set<IBlockData> b() {
        if (this.b.isEmpty()) {
            this.b.add(this.c.r().a());
        }

        return this.b;
    }

    public Set<BiomeBase> a(int var1, int var2, int var3) {
        return Sets.newHashSet(new BiomeBase[]{this.c});
    }
}
