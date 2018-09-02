package net.minecraft.server;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WorldGenDecoratorSpike extends WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> {
    private static final LoadingCache<Long, WorldGenEnder.Spike[]> a = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new WorldGenDecoratorSpike.a());

    public WorldGenDecoratorSpike() {
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureDecoratorEmptyConfiguration var5, WorldGenerator<C> worldgenerator, C var7) {
        WorldGenEnder.Spike[] aworldgenender$spike = a(generatoraccess);
        boolean flag = false;

        for(WorldGenEnder.Spike worldgenender$spike : aworldgenender$spike) {
            if (worldgenender$spike.a(blockposition)) {
                ((WorldGenEnder)worldgenerator).a(worldgenender$spike);
                flag |= ((WorldGenEnder)worldgenerator).a(generatoraccess, chunkgenerator, random, new BlockPosition(worldgenender$spike.a(), 45, worldgenender$spike.b()), WorldGenFeatureConfiguration.e);
            }
        }

        return flag;
    }

    public static WorldGenEnder.Spike[] a(GeneratorAccess generatoraccess) {
        Random random = new Random(generatoraccess.getSeed());
        long i = random.nextLong() & 65535L;
        return (WorldGenEnder.Spike[])a.getUnchecked(i);
    }

    static class a extends CacheLoader<Long, WorldGenEnder.Spike[]> {
        private a() {
        }

        public WorldGenEnder.Spike[] a(Long olong) throws Exception {
            ArrayList arraylist = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(0, 10), DiscreteDomain.integers()));
            Collections.shuffle(arraylist, new Random(olong));
            WorldGenEnder.Spike[] aworldgenender$spike = new WorldGenEnder.Spike[10];

            for(int i = 0; i < 10; ++i) {
                int j = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int k = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int l = arraylist.get(i);
                int i1 = 2 + l / 3;
                int j1 = 76 + l * 3;
                boolean flag = l == 1 || l == 2;
                aworldgenender$spike[i] = new WorldGenEnder.Spike(j, k, i1, j1, flag);
            }

            return aworldgenender$spike;
        }

        // $FF: synthetic method
        public Object load(Object object) throws Exception {
            return this.a((Long)object);
        }
    }
}
