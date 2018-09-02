package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldPersistentData {
    private static final Logger a = LogManager.getLogger();
    private final DimensionManager b;
    private Map<String, PersistentBase> c = Maps.newHashMap();
    private final Object2IntMap<String> d = new Object2IntOpenHashMap();
    @Nullable
    private final IDataManager e;

    public WorldPersistentData(DimensionManager dimensionmanager, @Nullable IDataManager idatamanager) {
        this.b = dimensionmanager;
        this.e = idatamanager;
        this.d.defaultReturnValue(-1);
    }

    @Nullable
    public <T extends PersistentBase> T a(Function<String, T> function, String s) {
        PersistentBase persistentbase = (PersistentBase)this.c.get(s);
        if (persistentbase == null && this.e != null) {
            try {
                File file1 = this.e.getDataFile(this.b, s);
                if (file1 != null && file1.exists()) {
                    persistentbase = (PersistentBase)function.apply(s);
                    persistentbase.a(a(this.e, this.b, s, 1628).getCompound("data"));
                    this.c.put(s, persistentbase);
                }
            } catch (Exception exception) {
                a.error("Error loading saved data: {}", s, exception);
            }
        }

        return (T)persistentbase;
    }

    public void a(String s, PersistentBase persistentbase) {
        this.c.put(s, persistentbase);
    }

    public void a() {
        try {
            this.d.clear();
            if (this.e == null) {
                return;
            }

            File file1 = this.e.getDataFile(this.b, "idcounts");
            if (file1 != null && file1.exists()) {
                DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
                NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
                datainputstream.close();

                for(String s : nbttagcompound.getKeys()) {
                    if (nbttagcompound.hasKeyOfType(s, 99)) {
                        this.d.put(s, nbttagcompound.getInt(s));
                    }
                }
            }
        } catch (Exception exception) {
            a.error("Could not load aux values", exception);
        }

    }

    public int a(String s) {
        int i = this.d.getInt(s) + 1;
        this.d.put(s, i);
        if (this.e == null) {
            return i;
        } else {
            try {
                File file1 = this.e.getDataFile(this.b, "idcounts");
                if (file1 != null) {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    ObjectIterator objectiterator = this.d.object2IntEntrySet().iterator();

                    while(objectiterator.hasNext()) {
                        Entry entry = (Entry)objectiterator.next();
                        nbttagcompound.setInt((String)entry.getKey(), entry.getIntValue());
                    }

                    DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
                    NBTCompressedStreamTools.a(nbttagcompound, dataoutputstream);
                    dataoutputstream.close();
                }
            } catch (Exception exception) {
                a.error("Could not get free aux value {}", s, exception);
            }

            return i;
        }
    }

    public static NBTTagCompound a(IDataManager idatamanager, DimensionManager dimensionmanager, String s, int i) throws IOException {
        File file1 = idatamanager.getDataFile(dimensionmanager, s);
        FileInputStream fileinputstream = new FileInputStream(file1);
        Throwable throwable = null;

        NBTTagCompound nbttagcompound1;
        try {
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(fileinputstream);
            int j = nbttagcompound.hasKeyOfType("DataVersion", 99) ? nbttagcompound.getInt("DataVersion") : 1343;
            nbttagcompound1 = GameProfileSerializer.a(idatamanager.i(), DataFixTypes.SAVED_DATA, nbttagcompound, j, i);
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (fileinputstream != null) {
                if (throwable != null) {
                    try {
                        fileinputstream.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                } else {
                    fileinputstream.close();
                }
            }

        }

        return nbttagcompound1;
    }

    public void b() {
        if (this.e != null) {
            for(PersistentBase persistentbase : this.c.values()) {
                if (persistentbase.d()) {
                    this.a(persistentbase);
                    persistentbase.a(false);
                }
            }

        }
    }

    private void a(PersistentBase persistentbase) {
        if (this.e != null) {
            try {
                File file1 = this.e.getDataFile(this.b, persistentbase.getId());
                if (file1 != null) {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.set("data", persistentbase.b(new NBTTagCompound()));
                    nbttagcompound.setInt("DataVersion", 1628);
                    FileOutputStream fileoutputstream = new FileOutputStream(file1);
                    NBTCompressedStreamTools.a(nbttagcompound, fileoutputstream);
                    fileoutputstream.close();
                }
            } catch (Exception exception) {
                a.error("Could not save data {}", persistentbase, exception);
            }

        }
    }
}
