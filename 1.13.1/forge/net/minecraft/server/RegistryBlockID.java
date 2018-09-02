package net.minecraft.server;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class RegistryBlockID<T> implements Registry<T> {
    private int a;
    private final IdentityHashMap<T, Integer> b;
    private final List<T> c;

    public RegistryBlockID() {
        this(512);
    }

    public RegistryBlockID(int i) {
        this.c = Lists.newArrayListWithExpectedSize(i);
        this.b = new IdentityHashMap(i);
    }

    public void a(T object, int i) {
        this.b.put(object, i);

        while(this.c.size() <= i) {
            this.c.add((Object)null);
        }

        this.c.set(i, object);
        if (this.a <= i) {
            this.a = i + 1;
        }

    }

    public void b(T object) {
        this.a(object, this.a);
    }

    public int getId(T object) {
        Integer integer = (Integer)this.b.get(object);
        return integer == null ? -1 : integer;
    }

    @Nullable
    public final T fromId(int i) {
        return (T)(i >= 0 && i < this.c.size() ? this.c.get(i) : null);
    }

    public Iterator<T> iterator() {
        return Iterators.filter(this.c.iterator(), Predicates.notNull());
    }

    public int a() {
        return this.b.size();
    }
}
