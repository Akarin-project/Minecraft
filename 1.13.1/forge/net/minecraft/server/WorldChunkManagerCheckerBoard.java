package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class WorldChunkManagerCheckerBoard extends WorldChunkManager {
    private final BiomeBase[] c;
    private final int d;

    public WorldChunkManagerCheckerBoard(BiomeLayoutCheckerboardConfiguration biomelayoutcheckerboardconfiguration) {
        this.c = biomelayoutcheckerboardconfiguration.a();
        this.d = biomelayoutcheckerboardconfiguration.b() + 4;
    }

    public BiomeBase getBiome(BlockPosition blockposition, @Nullable BiomeBase var2) {
        return this.c[Math.abs(((blockposition.getX() >> this.d) + (blockposition.getZ() >> this.d)) % this.c.length)];
    }

    public BiomeBase[] getBiomes(int i, int j, int k, int l) {
        return this.getBiomeBlock(i, j, k, l);
    }

    public BiomeBase[] a(int i, int j, int k, int l, boolean var5) {
        BiomeBase[] abiomebase = new BiomeBase[k * l];

        for(int i1 = 0; i1 < l; ++i1) {
            for(int j1 = 0; j1 < k; ++j1) {
                int k1 = Math.abs(((i + i1 >> this.d) + (j + j1 >> this.d)) % this.c.length);
                BiomeBase biomebase = this.c[k1];
                abiomebase[i1 * k + j1] = biomebase;
            }
        }

        return abiomebase;
    }

    @Nullable
    public BlockPosition a(int var1, int var2, int var3, List<BiomeBase> var4, Random var5) {
        return null;
    }

    public boolean a(StructureGenerator<?> structuregenerator) {
        return this.a.computeIfAbsent(structuregenerator, (structuregenerator1) -> {
            for(BiomeBase biomebase : this.c) {
                if (biomebase.a(structuregenerator1)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<IBlockData> b() {
        if (this.b.isEmpty()) {
            for(BiomeBase biomebase : this.c) {
                this.b.add(biomebase.r().a());
            }
        }

        return this.b;
    }

    public Set<BiomeBase> a(int var1, int var2, int var3) {
        return Sets.newHashSet(this.c);
    }
}
