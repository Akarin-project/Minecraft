package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatisticManager extends StatisticManager {
    private static final Logger b = LogManager.getLogger();
    private final MinecraftServer c;
    private final File d;
    private final Set<Statistic<?>> e = Sets.newHashSet();
    private int f = -300;

    public ServerStatisticManager(MinecraftServer minecraftserver, File file1) {
        this.c = minecraftserver;
        this.d = file1;
        if (file1.isFile()) {
            try {
                this.a(minecraftserver.az(), FileUtils.readFileToString(file1));
            } catch (IOException ioexception) {
                b.error("Couldn't read statistics file {}", file1, ioexception);
            } catch (JsonParseException jsonparseexception) {
                b.error("Couldn't parse statistics file {}", file1, jsonparseexception);
            }
        }

    }

    public void a() {
        try {
            FileUtils.writeStringToFile(this.d, this.b());
        } catch (IOException ioexception) {
            b.error("Couldn't save stats", ioexception);
        }

    }

    public void setStatistic(EntityHuman entityhuman, Statistic<?> statistic, int i) {
        super.setStatistic(entityhuman, statistic, i);
        this.e.add(statistic);
    }

    private Set<Statistic<?>> d() {
        HashSet hashset = Sets.newHashSet(this.e);
        this.e.clear();
        return hashset;
    }

    public void a(DataFixer datafixer, String s) {
        try {
            JsonReader jsonreader = new JsonReader(new StringReader(s));
            Throwable throwable = null;

            try {
                jsonreader.setLenient(false);
                JsonElement jsonelement = Streams.parse(jsonreader);
                if (!jsonelement.isJsonNull()) {
                    NBTTagCompound nbttagcompound = a(jsonelement.getAsJsonObject());
                    if (!nbttagcompound.hasKeyOfType("DataVersion", 99)) {
                        nbttagcompound.setInt("DataVersion", 1343);
                    }

                    nbttagcompound = GameProfileSerializer.a(datafixer, DataFixTypes.STATS, nbttagcompound, nbttagcompound.getInt("DataVersion"));
                    if (nbttagcompound.hasKeyOfType("stats", 10)) {
                        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("stats");

                        for(String s1 : nbttagcompound1.getKeys()) {
                            if (nbttagcompound1.hasKeyOfType(s1, 10)) {
                                StatisticWrapper statisticwrapper = IRegistry.STATS.get(new MinecraftKey(s1));
                                if (statisticwrapper == null) {
                                    b.warn("Invalid statistic type in {}: Don't know what {} is", this.d, s1);
                                } else {
                                    NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompound(s1);

                                    for(String s2 : nbttagcompound2.getKeys()) {
                                        if (nbttagcompound2.hasKeyOfType(s2, 99)) {
                                            Statistic statistic = this.a(statisticwrapper, s2);
                                            if (statistic == null) {
                                                b.warn("Invalid statistic in {}: Don't know what {} is", this.d, s2);
                                            } else {
                                                this.a.put(statistic, nbttagcompound2.getInt(s2));
                                            }
                                        } else {
                                            b.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.d, nbttagcompound2.get(s2), s2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    b.error("Unable to parse Stat data from {}", this.d);
                }
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (jsonreader != null) {
                    if (throwable != null) {
                        try {
                            jsonreader.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        jsonreader.close();
                    }
                }

            }
        } catch (IOException | JsonParseException jsonparseexception) {
            b.error("Unable to parse Stat data from {}", this.d, jsonparseexception);
        }

    }

    @Nullable
    private <T> Statistic<T> a(StatisticWrapper<T> statisticwrapper, String s) {
        MinecraftKey minecraftkey = MinecraftKey.a(s);
        if (minecraftkey == null) {
            return null;
        } else {
            Object object = statisticwrapper.a().get(minecraftkey);
            return object == null ? null : statisticwrapper.b(object);
        }
    }

    private static NBTTagCompound a(JsonObject jsonobject) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for(Entry entry : jsonobject.entrySet()) {
            JsonElement jsonelement = (JsonElement)entry.getValue();
            if (jsonelement.isJsonObject()) {
                nbttagcompound.set((String)entry.getKey(), a(jsonelement.getAsJsonObject()));
            } else if (jsonelement.isJsonPrimitive()) {
                JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();
                if (jsonprimitive.isNumber()) {
                    nbttagcompound.setInt((String)entry.getKey(), jsonprimitive.getAsInt());
                }
            }
        }

        return nbttagcompound;
    }

    protected String b() {
        HashMap hashmap = Maps.newHashMap();
        ObjectIterator objectiterator = this.a.object2IntEntrySet().iterator();

        while(objectiterator.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2IntMap.Entry entry = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry)objectiterator.next();
            Statistic statistic = (Statistic)entry.getKey();
            ((JsonObject)hashmap.computeIfAbsent(statistic.a(), (var0) -> {
                return new JsonObject();
            })).addProperty(b(statistic).toString(), entry.getIntValue());
        }

        JsonObject jsonobject = new JsonObject();

        for(Entry entry1 : hashmap.entrySet()) {
            jsonobject.add(IRegistry.STATS.getKey((StatisticWrapper<?>)entry1.getKey()).toString(), (JsonElement)entry1.getValue());
        }

        JsonObject jsonobject1 = new JsonObject();
        jsonobject1.add("stats", jsonobject);
        jsonobject1.addProperty("DataVersion", 1628);
        return jsonobject1.toString();
    }

    private static <T> MinecraftKey b(Statistic<T> statistic) {
        return statistic.a().a().getKey(statistic.b());
    }

    public void c() {
        this.e.addAll(this.a.keySet());
    }

    public void a(EntityPlayer entityplayer) {
        int i = this.c.ah();
        Object2IntOpenHashMap object2intopenhashmap = new Object2IntOpenHashMap();
        if (i - this.f > 300) {
            this.f = i;

            for(Statistic statistic : this.d()) {
                object2intopenhashmap.put(statistic, this.getStatisticValue(statistic));
            }
        }

        entityplayer.playerConnection.sendPacket(new PacketPlayOutStatistic(object2intopenhashmap));
    }
}
