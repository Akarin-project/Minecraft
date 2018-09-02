package net.minecraft.server;

import java.util.AbstractList;

public abstract class NBTList<T extends NBTBase> extends AbstractList<T> implements NBTBase {
    public NBTList() {
    }

    public abstract int size();

    public T get(int i) {
        return (T)this.c(i);
    }

    public T set(int i, T nbtbase) {
        NBTBase nbtbase1 = this.get(i);
        this.a(i, nbtbase);
        return (T)nbtbase1;
    }

    public abstract T c(int var1);

    public abstract void a(int var1, NBTBase var2);

    public abstract void b(int var1);

    // $FF: synthetic method
    public Object set(int i, Object object) {
        return this.set(i, (NBTBase)object);
    }

    // $FF: synthetic method
    public Object get(int i) {
        return this.get(i);
    }
}
