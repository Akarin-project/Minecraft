package net.minecraft.server;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class BlockDataAbstract<O, S> implements IBlockDataHolder<S> {
    private static final Function<Entry<IBlockState<?>, Comparable<?>>, String> b = new Function<Entry<IBlockState<?>, Comparable<?>>, String>() {
        public String a(@Nullable Entry<IBlockState<?>, Comparable<?>> entry) {
            if (entry == null) {
                return "<NULL>";
            } else {
                IBlockState iblockstate = (IBlockState)entry.getKey();
                return iblockstate.a() + "=" + this.a(iblockstate, (Comparable)entry.getValue());
            }
        }

        private <T extends Comparable<T>> String a(IBlockState<T> iblockstate, Comparable<?> comparable) {
            return iblockstate.a(comparable);
        }

        // $FF: synthetic method
        public Object apply(@Nullable Object object) {
            return this.a((Entry)object);
        }
    };
    protected final O e_;
    private final ImmutableMap<IBlockState<?>, Comparable<?>> c;
    private final int d;
    private Table<IBlockState<?>, Comparable<?>, S> e;

    protected BlockDataAbstract(O object, ImmutableMap<IBlockState<?>, Comparable<?>> immutablemap) {
        this.e_ = (O)object;
        this.c = immutablemap;
        this.d = immutablemap.hashCode();
    }

    public <T extends Comparable<T>> S a(IBlockState<T> iblockstate) {
        return (S)this.set(iblockstate, (Comparable)a(iblockstate.d(), this.get(iblockstate)));
    }

    protected static <T> T a(Collection<T> collection, T object) {
        Iterator iterator = collection.iterator();

        while(iterator.hasNext()) {
            if (iterator.next().equals(object)) {
                if (iterator.hasNext()) {
                    return (T)iterator.next();
                }

                return (T)collection.iterator().next();
            }
        }

        return (T)iterator.next();
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.e_);
        if (!this.b().isEmpty()) {
            stringbuilder.append('[');
            stringbuilder.append((String)this.b().entrySet().stream().map(b).collect(Collectors.joining(",")));
            stringbuilder.append(']');
        }

        return stringbuilder.toString();
    }

    public Collection<IBlockState<?>> a() {
        return Collections.unmodifiableCollection(this.c.keySet());
    }

    public <T extends Comparable<T>> boolean b(IBlockState<T> iblockstate) {
        return this.c.containsKey(iblockstate);
    }

    public <T extends Comparable<T>> T get(IBlockState<T> iblockstate) {
        Comparable comparable = (Comparable)this.c.get(iblockstate);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot get property " + iblockstate + " as it does not exist in " + this.e_);
        } else {
            return (T)(iblockstate.b().cast(comparable));
        }
    }

    public <T extends Comparable<T>, V extends T> S set(IBlockState<T> iblockstate, V comparable) {
        Comparable comparable1 = (Comparable)this.c.get(iblockstate);
        if (comparable1 == null) {
            throw new IllegalArgumentException("Cannot set property " + iblockstate + " as it does not exist in " + this.e_);
        } else if (comparable1 == comparable) {
            return (S)this;
        } else {
            Object object = this.e.get(iblockstate, comparable);
            if (object == null) {
                throw new IllegalArgumentException("Cannot set property " + iblockstate + " to " + comparable + " on " + this.e_ + ", it is not an allowed value");
            } else {
                return (S)object;
            }
        }
    }

    public void a(Map<Map<IBlockState<?>, Comparable<?>>, S> map) {
        if (this.e != null) {
            throw new IllegalStateException();
        } else {
            HashBasedTable hashbasedtable = HashBasedTable.create();
            UnmodifiableIterator unmodifiableiterator = this.c.entrySet().iterator();

            while(unmodifiableiterator.hasNext()) {
                Entry entry = (Entry)unmodifiableiterator.next();
                IBlockState iblockstate = (IBlockState)entry.getKey();

                for(Comparable comparable : iblockstate.d()) {
                    if (comparable != entry.getValue()) {
                        hashbasedtable.put(iblockstate, comparable, map.get(this.b(iblockstate, comparable)));
                    }
                }
            }

            this.e = (Table<IBlockState<?>, Comparable<?>, S>)(hashbasedtable.isEmpty() ? hashbasedtable : ArrayTable.create(hashbasedtable));
        }
    }

    private Map<IBlockState<?>, Comparable<?>> b(IBlockState<?> iblockstate, Comparable<?> comparable) {
        HashMap hashmap = Maps.newHashMap(this.c);
        hashmap.put(iblockstate, comparable);
        return hashmap;
    }

    public ImmutableMap<IBlockState<?>, Comparable<?>> b() {
        return this.c;
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public int hashCode() {
        return this.d;
    }
}
