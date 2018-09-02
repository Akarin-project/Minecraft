package net.minecraft.server;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataPaletteBlock<T> implements DataPaletteExpandable<T> {
    private final DataPalette<T> b;
    private final DataPaletteExpandable<T> c = (var0, var1) -> {
        return 0;
    };
    private final RegistryBlockID<T> d;
    private final Function<NBTTagCompound, T> e;
    private final Function<T, NBTTagCompound> f;
    private final T g;
    protected DataBits a;
    private DataPalette<T> h;
    private int i;
    private final ReentrantLock j = new ReentrantLock();

    private void b() {
        if (this.j.isLocked() && !this.j.isHeldByCurrentThread()) {
            String s = (String)Thread.getAllStackTraces().keySet().stream().filter(Objects::nonNull).map((thread) -> {
                return thread.getName() + ": \n\tat " + (String)Arrays.stream(thread.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "));
            }).collect(Collectors.joining("\n"));
            CrashReport crashreport = new CrashReport("Writing into PalettedContainer from multiple threads", new IllegalStateException());
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Thread dumps");
            crashreportsystemdetails.a("Thread dumps", s);
            throw new ReportedException(crashreport);
        } else {
            this.j.lock();
        }
    }

    private void c() {
        this.j.unlock();
    }

    public DataPaletteBlock(DataPalette<T> datapalette, RegistryBlockID<T> registryblockid, Function<NBTTagCompound, T> function, Function<T, NBTTagCompound> function1, T object) {
        this.b = datapalette;
        this.d = registryblockid;
        this.e = function;
        this.f = function1;
        this.g = (T)object;
        this.b(4);
    }

    private static int b(int ix, int jx, int k) {
        return jx << 8 | k << 4 | ix;
    }

    private void b(int ix) {
        if (ix != this.i) {
            this.i = ix;
            if (this.i <= 4) {
                this.i = 4;
                this.h = new DataPaletteLinear<T>(this.d, this.i, this, this.e);
            } else if (this.i < 9) {
                this.h = new DataPaletteHash<T>(this.d, this.i, this, this.e, this.f);
            } else {
                this.h = this.b;
                this.i = MathHelper.d(this.d.a());
            }

            this.h.a(this.g);
            this.a = new DataBits(this.i, 4096);
        }
    }

    public int onResize(int ix, T object) {
        this.b();
        DataBits databits = this.a;
        DataPalette datapalette = this.h;
        this.b(ix);

        for(int jx = 0; jx < databits.b(); ++jx) {
            Object object1 = datapalette.a(databits.a(jx));
            if (object1 != null) {
                this.setBlockIndex(jx, object1);
            }
        }

        int k = this.h.a((T)object);
        this.c();
        return k;
    }

    public void setBlock(int ix, int jx, int k, T object) {
        this.b();
        this.setBlockIndex(b(ix, jx, k), object);
        this.c();
    }

    protected void setBlockIndex(int ix, T object) {
        int jx = this.h.a((T)object);
        this.a.a(ix, jx);
    }

    public T a(int ix, int jx, int k) {
        return (T)this.a(b(ix, jx, k));
    }

    protected T a(int ix) {
        Object object = this.h.a(this.a.a(ix));
        return (T)(object == null ? this.g : object);
    }

    public void b(PacketDataSerializer packetdataserializer) {
        this.b();
        packetdataserializer.writeByte(this.i);
        this.h.b(packetdataserializer);
        packetdataserializer.a(this.a.a());
        this.c();
    }

    public void a(NBTTagCompound nbttagcompound, String s, String s1) {
        this.b();
        NBTTagList nbttaglist = nbttagcompound.getList(s, 10);
        int ix = Math.max(4, MathHelper.d(nbttaglist.size()));
        if (ix != this.i) {
            this.b(ix);
        }

        this.h.a(nbttaglist);
        long[] along = nbttagcompound.o(s1);
        int jx = along.length * 64 / 4096;
        if (this.h == this.b) {
            DataPaletteHash datapalettehash = new DataPaletteHash(this.d, ix, this.c, this.e, this.f);
            datapalettehash.a(nbttaglist);
            DataBits databits = new DataBits(ix, 4096, along);

            for(int k = 0; k < 4096; ++k) {
                this.a.a(k, this.b.a((T)datapalettehash.a(databits.a(k))));
            }
        } else if (jx == this.i) {
            System.arraycopy(along, 0, this.a.a(), 0, along.length);
        } else {
            DataBits databits1 = new DataBits(jx, 4096, along);

            for(int l = 0; l < 4096; ++l) {
                this.a.a(l, databits1.a(l));
            }
        }

        this.c();
    }

    public void b(NBTTagCompound nbttagcompound, String s, String s1) {
        this.b();
        DataPaletteHash datapalettehash = new DataPaletteHash(this.d, this.i, this.c, this.e, this.f);
        datapalettehash.a(this.g);
        int[] aint = new int[4096];

        for(int ix = 0; ix < 4096; ++ix) {
            aint[ix] = datapalettehash.a(this.a(ix));
        }

        NBTTagList nbttaglist = new NBTTagList();
        datapalettehash.b(nbttaglist);
        nbttagcompound.set(s, nbttaglist);
        int jx = Math.max(4, MathHelper.d(nbttaglist.size()));
        DataBits databits = new DataBits(jx, 4096);

        for(int k = 0; k < aint.length; ++k) {
            databits.a(k, aint[k]);
        }

        nbttagcompound.a(s1, databits.a());
        this.c();
    }

    public int a() {
        return 1 + this.h.a() + PacketDataSerializer.a(this.a.b()) + this.a.a().length * 8;
    }
}
