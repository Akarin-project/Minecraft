package net.minecraft.server;

public class NextTickListEntry<T> implements Comparable<NextTickListEntry<T>> {
    private static long d;
    private final T e;
    public final BlockPosition a;
    public final long b;
    public final TickListPriority c;
    private final long f;

    public NextTickListEntry(BlockPosition blockposition, T object) {
        this(blockposition, object, 0L, TickListPriority.NORMAL);
    }

    public NextTickListEntry(BlockPosition blockposition, T object, long i, TickListPriority ticklistpriority) {
        this.f = (long)(d++);
        this.a = blockposition.h();
        this.e = (T)object;
        this.b = i;
        this.c = ticklistpriority;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        } else {
            NextTickListEntry nextticklistentry1 = (NextTickListEntry)object;
            return this.a.equals(nextticklistentry1.a) && this.e == nextticklistentry1.e;
        }
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public int a(NextTickListEntry nextticklistentry1) {
        if (this.b < nextticklistentry1.b) {
            return -1;
        } else if (this.b > nextticklistentry1.b) {
            return 1;
        } else if (this.c.ordinal() < nextticklistentry1.c.ordinal()) {
            return -1;
        } else if (this.c.ordinal() > nextticklistentry1.c.ordinal()) {
            return 1;
        } else if (this.f < nextticklistentry1.f) {
            return -1;
        } else {
            return this.f > nextticklistentry1.f ? 1 : 0;
        }
    }

    public String toString() {
        return this.e + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
    }

    public T a() {
        return this.e;
    }

    // $FF: synthetic method
    public int compareTo(Object object) {
        return this.a((NextTickListEntry)object);
    }
}
