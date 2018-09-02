package net.minecraft.server;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class StatisticWrapper<T> implements Iterable<Statistic<T>> {
    private final IRegistry<T> a;
    private final Map<T, Statistic<T>> b = new IdentityHashMap();

    public StatisticWrapper(IRegistry<T> iregistry) {
        this.a = iregistry;
    }

    public Statistic<T> a(T object, Counter counter) {
        return (Statistic)this.b.computeIfAbsent(object, (object1) -> {
            return new Statistic(this, object1, counter);
        });
    }

    public IRegistry<T> a() {
        return this.a;
    }

    public Iterator<Statistic<T>> iterator() {
        return this.b.values().iterator();
    }

    public Statistic<T> b(T object) {
        return this.a(object, Counter.DEFAULT);
    }
}
