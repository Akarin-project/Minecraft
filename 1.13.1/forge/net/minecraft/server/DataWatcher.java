package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataWatcher {
    private static final Logger a = LogManager.getLogger();
    private static final Map<Class<? extends Entity>, Integer> b = Maps.newHashMap();
    private final Entity c;
    private final Map<Integer, DataWatcher.Item<?>> d = Maps.newHashMap();
    private final ReadWriteLock e = new ReentrantReadWriteLock();
    private boolean f = true;
    private boolean g;

    public DataWatcher(Entity entity) {
        this.c = entity;
    }

    public static <T> DataWatcherObject<T> a(Class<? extends Entity> oclass, DataWatcherSerializer<T> datawatcherserializer) {
        if (a.isDebugEnabled()) {
            try {
                Class oclass1 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
                if (!oclass1.equals(oclass)) {
                    a.debug("defineId called for: {} from {}", oclass, oclass1, new RuntimeException());
                }
            } catch (ClassNotFoundException var5) {
                ;
            }
        }

        int j;
        if (b.containsKey(oclass)) {
            j = b.get(oclass) + 1;
        } else {
            int i = 0;
            Class oclass2 = oclass;

            while(oclass2 != Entity.class) {
                oclass2 = oclass2.getSuperclass();
                if (b.containsKey(oclass2)) {
                    i = b.get(oclass2) + 1;
                    break;
                }
            }

            j = i;
        }

        if (j > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + j + "! (Max is " + 254 + ")");
        } else {
            b.put(oclass, j);
            return datawatcherserializer.a(j);
        }
    }

    public <T> void register(DataWatcherObject<T> datawatcherobject, T object) {
        int i = datawatcherobject.a();
        if (i > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
        } else if (this.d.containsKey(i)) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
        } else if (DataWatcherRegistry.b(datawatcherobject.b()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + datawatcherobject.b() + " for " + i + "!");
        } else {
            this.registerObject(datawatcherobject, object);
        }
    }

    private <T> void registerObject(DataWatcherObject<T> datawatcherobject, T object) {
        DataWatcher.Item datawatcher$item = new DataWatcher.Item(datawatcherobject, object);
        this.e.writeLock().lock();
        this.d.put(datawatcherobject.a(), datawatcher$item);
        this.f = false;
        this.e.writeLock().unlock();
    }

    private <T> DataWatcher.Item<T> b(DataWatcherObject<T> datawatcherobject) {
        this.e.readLock().lock();

        DataWatcher.Item datawatcher$item;
        try {
            datawatcher$item = (DataWatcher.Item)this.d.get(datawatcherobject.a());
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Getting synched entity data");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Synched entity data");
            crashreportsystemdetails.a("Data ID", datawatcherobject);
            throw new ReportedException(crashreport);
        }

        this.e.readLock().unlock();
        return datawatcher$item;
    }

    public <T> T get(DataWatcherObject<T> datawatcherobject) {
        return (T)this.b(datawatcherobject).b();
    }

    public <T> void set(DataWatcherObject<T> datawatcherobject, T object) {
        DataWatcher.Item datawatcher$item = this.b(datawatcherobject);
        if (ObjectUtils.notEqual(object, datawatcher$item.b())) {
            datawatcher$item.a(object);
            this.c.a(datawatcherobject);
            datawatcher$item.a(true);
            this.g = true;
        }

    }

    public boolean a() {
        return this.g;
    }

    public static void a(List<DataWatcher.Item<?>> list, PacketDataSerializer packetdataserializer) throws IOException {
        if (list != null) {
            int i = 0;

            for(int j = list.size(); i < j; ++i) {
                a(packetdataserializer, (DataWatcher.Item)list.get(i));
            }
        }

        packetdataserializer.writeByte(255);
    }

    @Nullable
    public List<DataWatcher.Item<?>> b() {
        ArrayList arraylist = null;
        if (this.g) {
            this.e.readLock().lock();

            for(DataWatcher.Item datawatcher$item : this.d.values()) {
                if (datawatcher$item.c()) {
                    datawatcher$item.a(false);
                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(datawatcher$item.d());
                }
            }

            this.e.readLock().unlock();
        }

        this.g = false;
        return arraylist;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.e.readLock().lock();

        for(DataWatcher.Item datawatcher$item : this.d.values()) {
            a(packetdataserializer, datawatcher$item);
        }

        this.e.readLock().unlock();
        packetdataserializer.writeByte(255);
    }

    @Nullable
    public List<DataWatcher.Item<?>> c() {
        ArrayList arraylist = null;
        this.e.readLock().lock();

        for(DataWatcher.Item datawatcher$item : this.d.values()) {
            if (arraylist == null) {
                arraylist = Lists.newArrayList();
            }

            arraylist.add(datawatcher$item.d());
        }

        this.e.readLock().unlock();
        return arraylist;
    }

    private static <T> void a(PacketDataSerializer packetdataserializer, DataWatcher.Item<T> datawatcher$item) throws IOException {
        DataWatcherObject datawatcherobject = datawatcher$item.a();
        int i = DataWatcherRegistry.b(datawatcherobject.b());
        if (i < 0) {
            throw new EncoderException("Unknown serializer type " + datawatcherobject.b());
        } else {
            packetdataserializer.writeByte(datawatcherobject.a());
            packetdataserializer.d(i);
            datawatcherobject.b().a(packetdataserializer, datawatcher$item.b());
        }
    }

    @Nullable
    public static List<DataWatcher.Item<?>> b(PacketDataSerializer packetdataserializer) throws IOException {
        ArrayList arraylist = null;

        short short1;
        while((short1 = packetdataserializer.readUnsignedByte()) != 255) {
            if (arraylist == null) {
                arraylist = Lists.newArrayList();
            }

            int i = packetdataserializer.g();
            DataWatcherSerializer datawatcherserializer = DataWatcherRegistry.a(i);
            if (datawatcherserializer == null) {
                throw new DecoderException("Unknown serializer type " + i);
            }

            arraylist.add(a(packetdataserializer, short1, datawatcherserializer));
        }

        return arraylist;
    }

    private static <T> DataWatcher.Item<T> a(PacketDataSerializer packetdataserializer, int i, DataWatcherSerializer<T> datawatcherserializer) {
        return new DataWatcher.Item<T>(datawatcherserializer.a(i), datawatcherserializer.a(packetdataserializer));
    }

    public boolean d() {
        return this.f;
    }

    public void e() {
        this.g = false;
        this.e.readLock().lock();

        for(DataWatcher.Item datawatcher$item : this.d.values()) {
            datawatcher$item.a(false);
        }

        this.e.readLock().unlock();
    }

    public static class Item<T> {
        private final DataWatcherObject<T> a;
        private T b;
        private boolean c;

        public Item(DataWatcherObject<T> datawatcherobject, T object) {
            this.a = datawatcherobject;
            this.b = (T)object;
            this.c = true;
        }

        public DataWatcherObject<T> a() {
            return this.a;
        }

        public void a(T object) {
            this.b = (T)object;
        }

        public T b() {
            return this.b;
        }

        public boolean c() {
            return this.c;
        }

        public void a(boolean flag) {
            this.c = flag;
        }

        public DataWatcher.Item<T> d() {
            return new DataWatcher.Item<T>(this.a, this.a.b().a(this.b));
        }
    }
}
