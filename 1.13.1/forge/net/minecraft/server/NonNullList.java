package net.minecraft.server;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E> extends AbstractList<E> {
    private final List<E> a;
    private final E b;

    public static <E> NonNullList<E> a() {
        return new NonNullList<E>();
    }

    public static <E> NonNullList<E> a(int i, E object) {
        Validate.notNull(object);
        Object[] aobject = new Object[i];
        Arrays.fill(aobject, object);
        return new NonNullList<E>(Arrays.asList(aobject), object);
    }

    @SafeVarargs
    public static <E> NonNullList<E> a(E object, E... aobject) {
        return new NonNullList<E>(Arrays.asList(aobject), object);
    }

    protected NonNullList() {
        this(new ArrayList(), (Object)null);
    }

    protected NonNullList(List<E> list, @Nullable E object) {
        this.a = list;
        this.b = (E)object;
    }

    @Nonnull
    public E get(int i) {
        return (E)this.a.get(i);
    }

    public E set(int i, E object) {
        Validate.notNull(object);
        return (E)this.a.set(i, object);
    }

    public void add(int i, E object) {
        Validate.notNull(object);
        this.a.add(i, object);
    }

    public E remove(int i) {
        return (E)this.a.remove(i);
    }

    public int size() {
        return this.a.size();
    }

    public void clear() {
        if (this.b == null) {
            super.clear();
        } else {
            for(int i = 0; i < this.size(); ++i) {
                this.set(i, this.b);
            }
        }

    }
}
