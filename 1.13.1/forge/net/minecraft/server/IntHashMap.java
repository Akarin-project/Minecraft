package net.minecraft.server;

import javax.annotation.Nullable;

public class IntHashMap<V> {
    private transient IntHashMap.IntHashMapEntry<V>[] a = new IntHashMap.IntHashMapEntry[16];
    private transient int b;
    private int c = 12;
    private final float d = 0.75F;

    public IntHashMap() {
    }

    private static int g(int i) {
        i = i ^ i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    private static int a(int i, int j) {
        return i & j - 1;
    }

    @Nullable
    public V get(int i) {
        int j = g(i);

        for(IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[a(j, this.a.length)]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c) {
            if (inthashmap$inthashmapentry.a == i) {
                return inthashmap$inthashmapentry.b;
            }
        }

        return (V)null;
    }

    public boolean b(int i) {
        return this.c(i) != null;
    }

    @Nullable
    final IntHashMap.IntHashMapEntry<V> c(int i) {
        int j = g(i);

        for(IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[a(j, this.a.length)]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c) {
            if (inthashmap$inthashmapentry.a == i) {
                return inthashmap$inthashmapentry;
            }
        }

        return null;
    }

    public void a(int i, V object) {
        int j = g(i);
        int k = a(j, this.a.length);

        for(IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[k]; inthashmap$inthashmapentry != null; inthashmap$inthashmapentry = inthashmap$inthashmapentry.c) {
            if (inthashmap$inthashmapentry.a == i) {
                inthashmap$inthashmapentry.b = (V)object;
                return;
            }
        }

        this.a(j, i, object, k);
    }

    private void h(int i) {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry = this.a;
        int j = ainthashmap$inthashmapentry.length;
        if (j == 1073741824) {
            this.c = Integer.MAX_VALUE;
        } else {
            IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry1 = new IntHashMap.IntHashMapEntry[i];
            this.a(ainthashmap$inthashmapentry1);
            this.a = ainthashmap$inthashmapentry1;
            this.c = (int)((float)i * this.d);
        }
    }

    private void a(IntHashMap.IntHashMapEntry<V>[] ainthashmap$inthashmapentry) {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry1 = this.a;
        int i = ainthashmap$inthashmapentry.length;

        for(int j = 0; j < ainthashmap$inthashmapentry1.length; ++j) {
            IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = ainthashmap$inthashmapentry1[j];
            if (inthashmap$inthashmapentry != null) {
                ainthashmap$inthashmapentry1[j] = null;

                while(true) {
                    IntHashMap.IntHashMapEntry inthashmap$inthashmapentry1 = inthashmap$inthashmapentry.c;
                    int k = a(inthashmap$inthashmapentry.d, i);
                    inthashmap$inthashmapentry.c = ainthashmap$inthashmapentry[k];
                    ainthashmap$inthashmapentry[k] = inthashmap$inthashmapentry;
                    inthashmap$inthashmapentry = inthashmap$inthashmapentry1;
                    if (inthashmap$inthashmapentry1 == null) {
                        break;
                    }
                }
            }
        }

    }

    @Nullable
    public V d(int i) {
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.e(i);
        return (V)(inthashmap$inthashmapentry == null ? null : inthashmap$inthashmapentry.b);
    }

    @Nullable
    final IntHashMap.IntHashMapEntry<V> e(int i) {
        int j = g(i);
        int k = a(j, this.a.length);
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[k];

        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry1;
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry2;
        for(inthashmap$inthashmapentry1 = inthashmap$inthashmapentry; inthashmap$inthashmapentry1 != null; inthashmap$inthashmapentry1 = inthashmap$inthashmapentry2) {
            inthashmap$inthashmapentry2 = inthashmap$inthashmapentry1.c;
            if (inthashmap$inthashmapentry1.a == i) {
                --this.b;
                if (inthashmap$inthashmapentry == inthashmap$inthashmapentry1) {
                    this.a[k] = inthashmap$inthashmapentry2;
                } else {
                    inthashmap$inthashmapentry.c = inthashmap$inthashmapentry2;
                }

                return inthashmap$inthashmapentry1;
            }

            inthashmap$inthashmapentry = inthashmap$inthashmapentry1;
        }

        return inthashmap$inthashmapentry1;
    }

    public void c() {
        IntHashMap.IntHashMapEntry[] ainthashmap$inthashmapentry = this.a;

        for(int i = 0; i < ainthashmap$inthashmapentry.length; ++i) {
            ainthashmap$inthashmapentry[i] = null;
        }

        this.b = 0;
    }

    private void a(int i, int j, V object, int k) {
        IntHashMap.IntHashMapEntry inthashmap$inthashmapentry = this.a[k];
        this.a[k] = new IntHashMap.IntHashMapEntry(i, j, object, inthashmap$inthashmapentry);
        if (this.b++ >= this.c) {
            this.h(2 * this.a.length);
        }

    }

    static class IntHashMapEntry<V> {
        private final int a;
        private V b;
        private IntHashMap.IntHashMapEntry<V> c;
        private final int d;

        IntHashMapEntry(int i, int j, V object, IntHashMap.IntHashMapEntry<V> inthashmap$inthashmapentry1) {
            this.b = (V)object;
            this.c = inthashmap$inthashmapentry1;
            this.a = j;
            this.d = i;
        }

        public final int a() {
            return this.a;
        }

        public final V b() {
            return this.b;
        }

        public final boolean equals(Object object) {
            if (!(object instanceof IntHashMap.IntHashMapEntry)) {
                return false;
            } else {
                IntHashMap.IntHashMapEntry inthashmap$inthashmapentry1 = (IntHashMap.IntHashMapEntry)object;
                if (this.a == inthashmap$inthashmapentry1.a) {
                    Object object1 = this.b();
                    Object object2 = inthashmap$inthashmapentry1.b();
                    if (object1 == object2 || object1 != null && object1.equals(object2)) {
                        return true;
                    }
                }

                return false;
            }
        }

        public final int hashCode() {
            return IntHashMap.g(this.a);
        }

        public final String toString() {
            return this.a() + "=" + this.b();
        }
    }
}
