package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TickListServer<T> implements TickList<T> {
    protected final Predicate<T> a;
    protected final Function<T, MinecraftKey> b;
    protected final Function<MinecraftKey, T> c;
    protected final Set<NextTickListEntry<T>> nextTickListHash = Sets.newHashSet();
    protected final TreeSet<NextTickListEntry<T>> nextTickList = new TreeSet();
    private final WorldServer f;
    private final List<NextTickListEntry<T>> g = Lists.newArrayList();
    private final Consumer<NextTickListEntry<T>> h;

    public TickListServer(WorldServer worldserver, Predicate<T> predicate, Function<T, MinecraftKey> function, Function<MinecraftKey, T> function1, Consumer<NextTickListEntry<T>> consumer) {
        this.a = predicate;
        this.b = function;
        this.c = function1;
        this.f = worldserver;
        this.h = consumer;
    }

    public void a() {
        int i = this.nextTickList.size();
        if (i != this.nextTickListHash.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        } else {
            if (i > 65536) {
                i = 65536;
            }

            this.f.methodProfiler.a("cleaning");

            for(int j = 0; j < i; ++j) {
                NextTickListEntry nextticklistentry = (NextTickListEntry)this.nextTickList.first();
                if (nextticklistentry.b > this.f.getTime()) {
                    break;
                }

                this.nextTickList.remove(nextticklistentry);
                this.nextTickListHash.remove(nextticklistentry);
                this.g.add(nextticklistentry);
            }

            this.f.methodProfiler.e();
            this.f.methodProfiler.a("ticking");
            Iterator iterator = this.g.iterator();

            while(iterator.hasNext()) {
                NextTickListEntry nextticklistentry1 = (NextTickListEntry)iterator.next();
                iterator.remove();
                boolean flag = false;
                if (this.f.areChunksLoadedBetween(nextticklistentry1.a.a(0, 0, 0), nextticklistentry1.a.a(0, 0, 0))) {
                    try {
                        this.h.accept(nextticklistentry1);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
                        CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry1.a, (IBlockData)null);
                        throw new ReportedException(crashreport);
                    }
                } else {
                    this.a(nextticklistentry1.a, nextticklistentry1.a(), 0);
                }
            }

            this.f.methodProfiler.e();
            this.g.clear();
        }
    }

    public boolean b(BlockPosition blockposition, T object) {
        return this.g.contains(new NextTickListEntry(blockposition, object));
    }

    public List<NextTickListEntry<T>> a(Chunk chunk, boolean flag) {
        ChunkCoordIntPair chunkcoordintpair = chunk.getPos();
        int i = (chunkcoordintpair.x << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkcoordintpair.z << 4) - 2;
        int l = k + 16 + 2;
        return this.a(new StructureBoundingBox(i, 0, k, j, 256, l), flag);
    }

    public List<NextTickListEntry<T>> a(StructureBoundingBox structureboundingbox, boolean flag) {
        ArrayList arraylist = null;

        for(int i = 0; i < 2; ++i) {
            Iterator iterator;
            if (i == 0) {
                iterator = this.nextTickList.iterator();
            } else {
                iterator = this.g.iterator();
            }

            while(iterator.hasNext()) {
                NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
                BlockPosition blockposition = nextticklistentry.a;
                if (blockposition.getX() >= structureboundingbox.a && blockposition.getX() < structureboundingbox.d && blockposition.getZ() >= structureboundingbox.c && blockposition.getZ() < structureboundingbox.f) {
                    if (flag) {
                        if (i == 0) {
                            this.nextTickListHash.remove(nextticklistentry);
                        }

                        iterator.remove();
                    }

                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(nextticklistentry);
                }
            }
        }

        return (List<NextTickListEntry<T>>)(arraylist == null ? Collections.emptyList() : arraylist);
    }

    public void a(StructureBoundingBox structureboundingbox, BlockPosition blockposition) {
        for(NextTickListEntry nextticklistentry : this.a(structureboundingbox, false)) {
            if (structureboundingbox.b(nextticklistentry.a)) {
                BlockPosition blockposition1 = nextticklistentry.a.a(blockposition);
                this.b(blockposition1, nextticklistentry.a(), (int)(nextticklistentry.b - this.f.getWorldData().getTime()), nextticklistentry.c);
            }
        }

    }

    public NBTTagList a(Chunk chunk) {
        List list = this.a(chunk, false);
        long i = this.f.getTime();
        NBTTagList nbttaglist = new NBTTagList();

        for(NextTickListEntry nextticklistentry : list) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("i", ((MinecraftKey)this.b.apply(nextticklistentry.a())).toString());
            nbttagcompound.setInt("x", nextticklistentry.a.getX());
            nbttagcompound.setInt("y", nextticklistentry.a.getY());
            nbttagcompound.setInt("z", nextticklistentry.a.getZ());
            nbttagcompound.setInt("t", (int)(nextticklistentry.b - i));
            nbttagcompound.setInt("p", nextticklistentry.c.a());
            nbttaglist.add((NBTBase)nbttagcompound);
        }

        return nbttaglist;
    }

    public void a(NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            Object object = this.c.apply(new MinecraftKey(nbttagcompound.getString("i")));
            if (object != null) {
                this.b(new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z")), object, nbttagcompound.getInt("t"), TickListPriority.a(nbttagcompound.getInt("p")));
            }
        }

    }

    public boolean a(BlockPosition blockposition, T object) {
        return this.nextTickListHash.contains(new NextTickListEntry(blockposition, object));
    }

    public void a(BlockPosition blockposition, T object, int i, TickListPriority ticklistpriority) {
        if (!this.a.test(object)) {
            if (this.f.isLoaded(blockposition)) {
                this.c(blockposition, object, i, ticklistpriority);
            }

        }
    }

    protected void b(BlockPosition blockposition, T object, int i, TickListPriority ticklistpriority) {
        if (!this.a.test(object)) {
            this.c(blockposition, object, i, ticklistpriority);
        }

    }

    private void c(BlockPosition blockposition, T object, int i, TickListPriority ticklistpriority) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, object, (long)i + this.f.getTime(), ticklistpriority);
        if (!this.nextTickListHash.contains(nextticklistentry)) {
            this.nextTickListHash.add(nextticklistentry);
            this.nextTickList.add(nextticklistentry);
        }

    }
}
