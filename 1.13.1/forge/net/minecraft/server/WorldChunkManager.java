package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class WorldChunkManager implements ITickable {
    private static final List<BiomeBase> c = Lists.newArrayList(new BiomeBase[]{Biomes.f, Biomes.c, Biomes.g, Biomes.u, Biomes.t, Biomes.w, Biomes.x});
    protected final Map<StructureGenerator<?>, Boolean> a = Maps.newHashMap();
    protected final Set<IBlockData> b = Sets.newHashSet();

    protected WorldChunkManager() {
    }

    public List<BiomeBase> a() {
        return c;
    }

    public void Y_() {
    }

    @Nullable
    public abstract BiomeBase getBiome(BlockPosition var1, @Nullable BiomeBase var2);

    public abstract BiomeBase[] getBiomes(int var1, int var2, int var3, int var4);

    public BiomeBase[] getBiomeBlock(int i, int j, int k, int l) {
        return this.a(i, j, k, l, true);
    }

    public abstract BiomeBase[] a(int var1, int var2, int var3, int var4, boolean var5);

    public abstract Set<BiomeBase> a(int var1, int var2, int var3);

    @Nullable
    public abstract BlockPosition a(int var1, int var2, int var3, List<BiomeBase> var4, Random var5);

    public float c(int var1, int var2, int var3, int var4) {
        return 0.0F;
    }

    public abstract boolean a(StructureGenerator<?> var1);

    public abstract Set<IBlockData> b();
}
