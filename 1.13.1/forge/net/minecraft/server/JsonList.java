package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonList<K, V extends JsonListEntry<K>> {
    protected static final Logger a = LogManager.getLogger();
    protected final Gson b;
    private final File c;
    private final Map<String, V> d = Maps.newHashMap();
    private boolean e = true;
    private static final ParameterizedType f = new ParameterizedType() {
        public Type[] getActualTypeArguments() {
            return new Type[]{JsonListEntry.class};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    };

    public JsonList(File file1) {
        this.c = file1;
        GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
        gsonbuilder.registerTypeHierarchyAdapter(JsonListEntry.class, new JsonList.JsonListEntrySerializer());
        this.b = gsonbuilder.create();
    }

    public boolean isEnabled() {
        return this.e;
    }

    public void a(boolean flag) {
        this.e = flag;
    }

    public File c() {
        return this.c;
    }

    public void add(V jsonlistentry) {
        this.d.put(this.a(jsonlistentry.getKey()), jsonlistentry);

        try {
            this.save();
        } catch (IOException ioexception) {
            a.warn("Could not save the list after adding a user.", ioexception);
        }

    }

    @Nullable
    public V get(K object) {
        this.h();
        return (V)(this.d.get(this.a(object)));
    }

    public void remove(K object) {
        this.d.remove(this.a(object));

        try {
            this.save();
        } catch (IOException ioexception) {
            a.warn("Could not save the list after removing a user.", ioexception);
        }

    }

    public void b(JsonListEntry<K> jsonlistentry) {
        this.remove(jsonlistentry.getKey());
    }

    public String[] getEntries() {
        return (String[])this.d.keySet().toArray(new String[this.d.size()]);
    }

    public boolean isEmpty() {
        return this.d.size() < 1;
    }

    protected String a(K object) {
        return object.toString();
    }

    protected boolean d(K object) {
        return this.d.containsKey(this.a(object));
    }

    private void h() {
        ArrayList arraylist = Lists.newArrayList();

        for(JsonListEntry jsonlistentry : this.d.values()) {
            if (jsonlistentry.hasExpired()) {
                arraylist.add(jsonlistentry.getKey());
            }
        }

        for(Object object : arraylist) {
            this.d.remove(this.a(object));
        }

    }

    protected JsonListEntry<K> a(JsonObject jsonobject) {
        return new JsonListEntry<K>((Object)null, jsonobject);
    }

    public Collection<V> e() {
        return this.d.values();
    }

    public void save() throws IOException {
        Collection collection = this.d.values();
        String s = this.b.toJson(collection);
        BufferedWriter bufferedwriter = null;

        try {
            bufferedwriter = Files.newWriter(this.c, StandardCharsets.UTF_8);
            bufferedwriter.write(s);
        } finally {
            IOUtils.closeQuietly(bufferedwriter);
        }

    }

    public void load() throws FileNotFoundException {
        if (this.c.exists()) {
            BufferedReader bufferedreader = null;

            try {
                bufferedreader = Files.newReader(this.c, StandardCharsets.UTF_8);
                Collection collection = (Collection)ChatDeserializer.a(this.b, bufferedreader, f);
                if (collection != null) {
                    this.d.clear();

                    for(JsonListEntry jsonlistentry : collection) {
                        if (jsonlistentry.getKey() != null) {
                            this.d.put(this.a(jsonlistentry.getKey()), jsonlistentry);
                        }
                    }
                }
            } finally {
                IOUtils.closeQuietly(bufferedreader);
            }

        }
    }

    class JsonListEntrySerializer implements JsonDeserializer<JsonListEntry<K>>, JsonSerializer<JsonListEntry<K>> {
        private JsonListEntrySerializer() {
        }

        public JsonElement a(JsonListEntry<K> jsonlistentry, Type var2, JsonSerializationContext var3) {
            JsonObject jsonobject = new JsonObject();
            jsonlistentry.a(jsonobject);
            return jsonobject;
        }

        public JsonListEntry<K> a(JsonElement jsonelement, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            if (jsonelement.isJsonObject()) {
                JsonObject jsonobject = jsonelement.getAsJsonObject();
                return JsonList.this.a(jsonobject);
            } else {
                return null;
            }
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((JsonListEntry)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
