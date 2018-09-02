package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;

public class ExpiringMap<T> extends Long2ObjectOpenHashMap<T> {
    private final int a;
    private final Long2LongMap b = new Long2LongLinkedOpenHashMap();

    public ExpiringMap(int i, int j) {
        super(i);
        this.a = j;
    }

    private void a(long i) {
        long j = SystemUtils.b();
        this.b.put(i, j);
        ObjectIterator objectiterator = this.b.long2LongEntrySet().iterator();

        while(objectiterator.hasNext()) {
            Entry entry = (Entry)objectiterator.next();
            Object object = super.get(entry.getLongKey());
            if (j - entry.getLongValue() <= (long)this.a) {
                break;
            }

            if (object != null && this.a(object)) {
                super.remove(entry.getLongKey());
                objectiterator.remove();
            }
        }

    }

    protected boolean a(T var1) {
        return true;
    }

    public T put(long i, T object) {
        this.a(i);
        return (T)super.put(i, object);
    }

    public T put(Long olong, T object) {
        this.a(olong);
        return (T)super.put(olong, object);
    }

    public T get(long i) {
        this.a(i);
        return (T)super.get(i);
    }

    public void putAll(Map<? extends Long, ? extends T> var1) {
        throw new RuntimeException("Not implemented");
    }

    public T remove(long var1) {
        throw new RuntimeException("Not implemented");
    }

    public T remove(Object var1) {
        throw new RuntimeException("Not implemented");
    }
}
