package net.minecraft.server;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;

public class RegistryID<K> implements Registry<K> {
    private static final Object a = null;
    private K[] b;
    private int[] c;
    private K[] d;
    private int e;
    private int f;

    public RegistryID(int i) {
        i = (int)((float)i / 0.8F);
        this.b = (K[])(new Object[i]);
        this.c = new int[i];
        this.d = (K[])(new Object[i]);
    }

    public int getId(@Nullable K object) {
        return this.c(this.b(object, this.d(object)));
    }

    @Nullable
    public K fromId(int i) {
        return (K)(i >= 0 && i < this.d.length ? this.d[i] : null);
    }

    private int c(int i) {
        return i == -1 ? -1 : this.c[i];
    }

    public int c(K object) {
        int i = this.c();
        this.a(object, i);
        return i;
    }

    private int c() {
        while(this.e < this.d.length && this.d[this.e] != null) {
            ++this.e;
        }

        return this.e;
    }

    private void d(int i) {
        Object[] aobject = this.b;
        int[] aint = this.c;
        this.b = (K[])(new Object[i]);
        this.c = new int[i];
        this.d = (K[])(new Object[i]);
        this.e = 0;
        this.f = 0;

        for(int j = 0; j < aobject.length; ++j) {
            if (aobject[j] != null) {
                this.a(aobject[j], aint[j]);
            }
        }

    }

    public void a(K object, int i) {
        int j = Math.max(i, this.f + 1);
        if ((float)j >= (float)this.b.length * 0.8F) {
            int k;
            for(k = this.b.length << 1; k < i; k <<= 1) {
                ;
            }

            this.d(k);
        }

        int l = this.e(this.d(object));
        this.b[l] = object;
        this.c[l] = i;
        this.d[i] = object;
        ++this.f;
        if (i == this.e) {
            ++this.e;
        }

    }

    private int d(@Nullable K object) {
        return (MathHelper.f(System.identityHashCode(object)) & Integer.MAX_VALUE) % this.b.length;
    }

    private int b(@Nullable K object, int i) {
        for(int j = i; j < this.b.length; ++j) {
            if (this.b[j] == object) {
                return j;
            }

            if (this.b[j] == a) {
                return -1;
            }
        }

        for(int k = 0; k < i; ++k) {
            if (this.b[k] == object) {
                return k;
            }

            if (this.b[k] == a) {
                return -1;
            }
        }

        return -1;
    }

    private int e(int i) {
        for(int j = i; j < this.b.length; ++j) {
            if (this.b[j] == a) {
                return j;
            }
        }

        for(int k = 0; k < i; ++k) {
            if (this.b[k] == a) {
                return k;
            }
        }

        throw new RuntimeException("Overflowed :(");
    }

    public Iterator<K> iterator() {
        return Iterators.filter(Iterators.forArray(this.d), Predicates.notNull());
    }

    public void a() {
        Arrays.fill(this.b, (Object)null);
        Arrays.fill(this.d, (Object)null);
        this.e = 0;
        this.f = 0;
    }

    public int b() {
        return this.f;
    }
}
