package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.concurrent.ThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldUpgrader {
    private static final Logger a = LogManager.getLogger();
    private static final ThreadFactory b = (new ThreadFactoryBuilder()).setDaemon(true).build();
    private final String c;
    private final IDataManager d;
    private final PersistentCollection e;
    private final Thread f;
    private volatile boolean g = true;
    private volatile boolean h = false;
    private volatile float i;
    private volatile int j;
    private volatile int k = 0;
    private volatile int l = 0;
    private final Object2FloatMap<DimensionManager> m = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap(SystemUtils.g()));
    private volatile IChatBaseComponent n = new ChatMessage("optimizeWorld.stage.counting", new Object[0]);

    public WorldUpgrader(String s, Convertable convertable, WorldData worlddata) {
        this.c = worlddata.getName();
        this.d = convertable.a(s, (MinecraftServer)null);
        this.d.saveWorldData(worlddata);
        this.e = new PersistentCollection(this.d);
        this.f = b.newThread(this::i);
        this.f.setUncaughtExceptionHandler(this::a);
        this.f.start();
    }

    private void a(Thread var1, Throwable throwable) {
        a.error("Error upgrading world", throwable);
        this.g = false;
        this.n = new ChatMessage("optimizeWorld.stage.failed", new Object[0]);
    }

    public void a() {
        this.g = false;

        try {
            this.f.join();
        } catch (InterruptedException var2) {
            ;
        }

    }

    private void i() {
        File file1 = this.d.getDirectory();
        WorldUpgraderIterator worldupgraderiterator = new WorldUpgraderIterator(file1);
        Builder builder = ImmutableMap.builder();

        for(DimensionManager dimensionmanager : DimensionManager.b()) {
            builder.put(dimensionmanager, new ChunkRegionLoader(dimensionmanager.a(file1), this.d.i()));
        }

        ImmutableMap immutablemap = builder.build();
        long ix = SystemUtils.b();
        this.j = 0;
        Builder builder1 = ImmutableMap.builder();

        for(DimensionManager dimensionmanager1 : DimensionManager.b()) {
            List list = worldupgraderiterator.a(dimensionmanager1);
            builder1.put(dimensionmanager1, list.listIterator());
            this.j += list.size();
        }

        ImmutableMap immutablemap1 = builder1.build();
        float f1 = (float)this.j;
        this.n = new ChatMessage("optimizeWorld.stage.structures", new Object[0]);

        for(Entry entry : immutablemap.entrySet()) {
            ((ChunkRegionLoader)entry.getValue()).a((DimensionManager)entry.getKey(), this.e);
        }

        this.e.a();
        this.n = new ChatMessage("optimizeWorld.stage.upgrading", new Object[0]);
        if (f1 <= 0.0F) {
            for(DimensionManager dimensionmanager3 : DimensionManager.b()) {
                this.m.put(dimensionmanager3, 1.0F / (float)immutablemap.size());
            }
        }

        while(this.g) {
            boolean flag = false;
            float f2 = 0.0F;

            for(DimensionManager dimensionmanager2 : DimensionManager.b()) {
                ListIterator listiterator = (ListIterator)immutablemap1.get(dimensionmanager2);
                flag |= this.a((ChunkRegionLoader)immutablemap.get(dimensionmanager2), listiterator, dimensionmanager2);
                if (f1 > 0.0F) {
                    float fx = (float)listiterator.nextIndex() / f1;
                    this.m.put(dimensionmanager2, fx);
                    f2 += fx;
                }
            }

            this.i = f2;
            if (!flag) {
                this.g = false;
            }
        }

        this.n = new ChatMessage("optimizeWorld.stage.finished", new Object[0]);
        ix = SystemUtils.b() - ix;
        a.info("World optimizaton finished after {} ms", ix);
        immutablemap.values().forEach(ChunkRegionLoader::b);
        this.e.a();
        this.d.a();
        this.h = true;
    }

    private boolean a(ChunkRegionLoader chunkregionloader, ListIterator<ChunkCoordIntPair> listiterator, DimensionManager dimensionmanager) {
        if (listiterator.hasNext()) {
            boolean flag;
            synchronized(chunkregionloader) {
                flag = chunkregionloader.a((ChunkCoordIntPair)listiterator.next(), dimensionmanager, this.e);
            }

            if (flag) {
                ++this.k;
            } else {
                ++this.l;
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean b() {
        return this.h;
    }

    public int d() {
        return this.j;
    }

    public int e() {
        return this.k;
    }

    public int f() {
        return this.l;
    }

    public IChatBaseComponent g() {
        return this.n;
    }
}
