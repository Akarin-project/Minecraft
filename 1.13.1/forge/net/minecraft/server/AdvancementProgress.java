package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
    private final Map<String, CriterionProgress> a = Maps.newHashMap();
    private String[][] b = new String[0][];

    public AdvancementProgress() {
    }

    public void a(Map<String, Criterion> map, String[][] astring) {
        Set set = map.keySet();
        this.a.entrySet().removeIf((entry) -> {
            return !set.contains(entry.getKey());
        });

        for(String s : set) {
            if (!this.a.containsKey(s)) {
                this.a.put(s, new CriterionProgress());
            }
        }

        this.b = astring;
    }

    public boolean isDone() {
        if (this.b.length == 0) {
            return false;
        } else {
            for(String[] astring : this.b) {
                boolean flag = false;

                for(String s : astring) {
                    CriterionProgress criterionprogress = this.getCriterionProgress(s);
                    if (criterionprogress != null && criterionprogress.a()) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean b() {
        for(CriterionProgress criterionprogress : this.a.values()) {
            if (criterionprogress.a()) {
                return true;
            }
        }

        return false;
    }

    public boolean a(String s) {
        CriterionProgress criterionprogress = (CriterionProgress)this.a.get(s);
        if (criterionprogress != null && !criterionprogress.a()) {
            criterionprogress.b();
            return true;
        } else {
            return false;
        }
    }

    public boolean b(String s) {
        CriterionProgress criterionprogress = (CriterionProgress)this.a.get(s);
        if (criterionprogress != null && criterionprogress.a()) {
            criterionprogress.c();
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + this.a + ", requirements=" + Arrays.deepToString(this.b) + '}';
    }

    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.d(this.a.size());

        for(Entry entry : this.a.entrySet()) {
            packetdataserializer.a((String)entry.getKey());
            ((CriterionProgress)entry.getValue()).a(packetdataserializer);
        }

    }

    public static AdvancementProgress b(PacketDataSerializer packetdataserializer) {
        AdvancementProgress advancementprogress = new AdvancementProgress();
        int i = packetdataserializer.g();

        for(int j = 0; j < i; ++j) {
            advancementprogress.a.put(packetdataserializer.e(32767), CriterionProgress.b(packetdataserializer));
        }

        return advancementprogress;
    }

    @Nullable
    public CriterionProgress getCriterionProgress(String s) {
        return (CriterionProgress)this.a.get(s);
    }

    public Iterable<String> getRemainingCriteria() {
        ArrayList arraylist = Lists.newArrayList();

        for(Entry entry : this.a.entrySet()) {
            if (!((CriterionProgress)entry.getValue()).a()) {
                arraylist.add(entry.getKey());
            }
        }

        return arraylist;
    }

    public Iterable<String> getAwardedCriteria() {
        ArrayList arraylist = Lists.newArrayList();

        for(Entry entry : this.a.entrySet()) {
            if (((CriterionProgress)entry.getValue()).a()) {
                arraylist.add(entry.getKey());
            }
        }

        return arraylist;
    }

    @Nullable
    public Date g() {
        Date date = null;

        for(CriterionProgress criterionprogress : this.a.values()) {
            if (criterionprogress.a() && (date == null || criterionprogress.getDate().before(date))) {
                date = criterionprogress.getDate();
            }
        }

        return date;
    }

    public int a(AdvancementProgress advancementprogress1) {
        Date date = this.g();
        Date date1 = advancementprogress1.g();
        if (date == null && date1 != null) {
            return 1;
        } else if (date != null && date1 == null) {
            return -1;
        } else {
            return date == null && date1 == null ? 0 : date.compareTo(date1);
        }
    }

    // $FF: synthetic method
    public int compareTo(Object object) {
        return this.a((AdvancementProgress)object);
    }

    public static class a implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
        public a() {
        }

        public JsonElement a(AdvancementProgress advancementprogress, Type var2, JsonSerializationContext var3) {
            JsonObject jsonobject = new JsonObject();
            JsonObject jsonobject1 = new JsonObject();

            for(Entry entry : advancementprogress.a.entrySet()) {
                CriterionProgress criterionprogress = (CriterionProgress)entry.getValue();
                if (criterionprogress.a()) {
                    jsonobject1.add((String)entry.getKey(), criterionprogress.e());
                }
            }

            if (!jsonobject1.entrySet().isEmpty()) {
                jsonobject.add("criteria", jsonobject1);
            }

            jsonobject.addProperty("done", advancementprogress.isDone());
            return jsonobject;
        }

        public AdvancementProgress a(JsonElement jsonelement, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "advancement");
            JsonObject jsonobject1 = ChatDeserializer.a(jsonobject, "criteria", new JsonObject());
            AdvancementProgress advancementprogress = new AdvancementProgress();

            for(Entry entry : jsonobject1.entrySet()) {
                String s = (String)entry.getKey();
                advancementprogress.a.put(s, CriterionProgress.a(ChatDeserializer.a((JsonElement)entry.getValue(), s)));
            }

            return advancementprogress;
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((AdvancementProgress)object, type, jsonserializationcontext);
        }
    }
}
