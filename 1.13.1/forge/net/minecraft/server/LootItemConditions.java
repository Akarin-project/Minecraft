package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class LootItemConditions {
    private static final Map<MinecraftKey, LootItemCondition.a<?>> a = Maps.newHashMap();
    private static final Map<Class<? extends LootItemCondition>, LootItemCondition.a<?>> b = Maps.newHashMap();

    public static <T extends LootItemCondition> void a(LootItemCondition.a<? extends T> lootitemcondition$a) {
        MinecraftKey minecraftkey = lootitemcondition$a.a();
        Class oclass = lootitemcondition$a.b();
        if (a.containsKey(minecraftkey)) {
            throw new IllegalArgumentException("Can't re-register item condition name " + minecraftkey);
        } else if (b.containsKey(oclass)) {
            throw new IllegalArgumentException("Can't re-register item condition class " + oclass.getName());
        } else {
            a.put(minecraftkey, lootitemcondition$a);
            b.put(oclass, lootitemcondition$a);
        }
    }

    public static boolean a(@Nullable LootItemCondition[] alootitemcondition, Random random, LootTableInfo loottableinfo) {
        if (alootitemcondition == null) {
            return true;
        } else {
            for(LootItemCondition lootitemcondition : alootitemcondition) {
                if (!lootitemcondition.a(random, loottableinfo)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static LootItemCondition.a<?> a(MinecraftKey minecraftkey) {
        LootItemCondition.a lootitemcondition$a = (LootItemCondition.a)a.get(minecraftkey);
        if (lootitemcondition$a == null) {
            throw new IllegalArgumentException("Unknown loot item condition '" + minecraftkey + "'");
        } else {
            return lootitemcondition$a;
        }
    }

    public static <T extends LootItemCondition> LootItemCondition.a<T> a(T lootitemcondition) {
        LootItemCondition.a lootitemcondition$a = (LootItemCondition.a)b.get(lootitemcondition.getClass());
        if (lootitemcondition$a == null) {
            throw new IllegalArgumentException("Unknown loot item condition " + lootitemcondition);
        } else {
            return lootitemcondition$a;
        }
    }

    static {
        a(new LootItemConditionRandomChance.a());
        a(new LootItemConditionRandomChanceWithLooting.a());
        a(new LootItemConditionEntityProperty.a());
        a(new LootItemConditionKilledByPlayer.a());
        a(new LootItemConditionEntityScore.a());
    }

    public static class a implements JsonDeserializer<LootItemCondition>, JsonSerializer<LootItemCondition> {
        public a() {
        }

        public LootItemCondition a(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "condition");
            MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "condition"));

            LootItemCondition.a lootitemcondition$a;
            try {
                lootitemcondition$a = LootItemConditions.a(minecraftkey);
            } catch (IllegalArgumentException var8) {
                throw new JsonSyntaxException("Unknown condition '" + minecraftkey + "'");
            }

            return lootitemcondition$a.b(jsonobject, jsondeserializationcontext);
        }

        public JsonElement a(LootItemCondition lootitemcondition, Type var2, JsonSerializationContext jsonserializationcontext) {
            LootItemCondition.a lootitemcondition$a = LootItemConditions.a(lootitemcondition);
            JsonObject jsonobject = new JsonObject();
            lootitemcondition$a.a(jsonobject, lootitemcondition, jsonserializationcontext);
            jsonobject.addProperty("condition", lootitemcondition$a.a().toString());
            return jsonobject;
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((LootItemCondition)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
