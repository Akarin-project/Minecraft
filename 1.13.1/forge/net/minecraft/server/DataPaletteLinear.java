package net.minecraft.server;

import java.util.function.Function;
import javax.annotation.Nullable;

public class DataPaletteLinear<T> implements DataPalette<T> {
    private final RegistryBlockID<T> a;
    private final T[] b;
    private final DataPaletteExpandable<T> c;
    private final Function<NBTTagCompound, T> d;
    private final int e;
    private int f;

    public DataPaletteLinear(RegistryBlockID<T> registryblockid, int i, DataPaletteExpandable<T> datapaletteexpandable, Function<NBTTagCompound, T> function) {
        this.a = registryblockid;
        this.b = (T[])(new Object[1 << i]);
        this.e = i;
        this.c = datapaletteexpandable;
        this.d = function;
    }

    public int a(T object) {
        for(int i = 0; i < this.f; ++i) {
            if (this.b[i] == object) {
                return i;
            }
        }

        int j = this.f;
        if (j < this.b.length) {
            this.b[j] = object;
            ++this.f;
            return j;
        } else {
            return this.c.onResize(this.e + 1, (T)object);
        }
    }

    @Nullable
    public T a(int i) {
        return (T)(i >= 0 && i < this.f ? this.b[i] : null);
    }

    public void b(PacketDataSerializer packetdataserializer) {
        packetdataserializer.d(this.f);

        for(int i = 0; i < this.f; ++i) {
            packetdataserializer.d(this.a.getId((T)this.b[i]));
        }

    }

    public int a() {
        int i = PacketDataSerializer.a(this.b());

        for(int j = 0; j < this.b(); ++j) {
            i += PacketDataSerializer.a(this.a.getId((T)this.b[j]));
        }

        return i;
    }

    public int b() {
        return this.f;
    }

    public void a(NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.size(); ++i) {
            this.b[i] = this.d.apply(nbttaglist.getCompound(i));
        }

        this.f = nbttaglist.size();
    }
}
