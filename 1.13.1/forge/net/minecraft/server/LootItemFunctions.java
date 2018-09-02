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

public class LootItemFunctions {
    private static final Map<MinecraftKey, LootItemFunction.a<?>> a = Maps.newHashMap();
    private static final Map<Class<? extends LootItemFunction>, LootItemFunction.a<?>> b = Maps.newHashMap();

    public static <T extends LootItemFunction> void a(LootItemFunction.a<? extends T> lootitemfunction$a) {
        MinecraftKey minecraftkey = lootitemfunction$a.a();
        Class oclass = lootitemfunction$a.b();
        if (a.containsKey(minecraftkey)) {
            throw new IllegalArgumentException("Can't re-register item function name " + minecraftkey);
        } else if (b.containsKey(oclass)) {
            throw new IllegalArgumentException("Can't re-register item function class " + oclass.getName());
        } else {
            a.put(minecraftkey, lootitemfunction$a);
            b.put(oclass, lootitemfunction$a);
        }
    }

    public static LootItemFunction.a<?> a(MinecraftKey minecraftkey) {
        LootItemFunction.a lootitemfunction$a = (LootItemFunction.a)a.get(minecraftkey);
        if (lootitemfunction$a == null) {
            throw new IllegalArgumentException("Unknown loot item function '" + minecraftkey + "'");
        } else {
            return lootitemfunction$a;
        }
    }

    public static <T extends LootItemFunction> LootItemFunction.a<T> a(T lootitemfunction) {
        LootItemFunction.a lootitemfunction$a = (LootItemFunction.a)b.get(lootitemfunction.getClass());
        if (lootitemfunction$a == null) {
            throw new IllegalArgumentException("Unknown loot item function " + lootitemfunction);
        } else {
            return lootitemfunction$a;
        }
    }

    static {
        a(new LootItemFunctionSetCount.a());
        a(new LootEnchantLevel.a());
        a(new LootItemFunctionEnchant.a());
        a(new LootItemFunctionSetTag.a());
        a(new LootItemFunctionSmelt.a());
        a(new LootEnchantFunction.a());
        a(new LootItemFunctionSetDamage.a());
        a(new LootItemFunctionSetAttribute.b());
        a(new LootItemFunctionSetName.a());
        a(new LootItemFunctionExplorationMap.a());
    }

    public static class a implements JsonDeserializer<LootItemFunction>, JsonSerializer<LootItemFunction> {
        public a() {
        }

        public LootItemFunction a(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "function");
            MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "function"));

            LootItemFunction.a lootitemfunction$a;
            try {
                lootitemfunction$a = LootItemFunctions.a(minecraftkey);
            } catch (IllegalArgumentException var8) {
                throw new JsonSyntaxException("Unknown function '" + minecraftkey + "'");
            }

            return lootitemfunction$a.b(jsonobject, jsondeserializationcontext, (LootItemCondition[])ChatDeserializer.a(jsonobject, "conditions", new LootItemCondition[0], jsondeserializationcontext, LootItemCondition[].class));
        }

        public JsonElement a(LootItemFunction lootitemfunction, Type var2, JsonSerializationContext jsonserializationcontext) {
            LootItemFunction.a lootitemfunction$a = LootItemFunctions.a(lootitemfunction);
            JsonObject jsonobject = new JsonObject();
            lootitemfunction$a.a(jsonobject, lootitemfunction, jsonserializationcontext);
            jsonobject.addProperty("function", lootitemfunction$a.a().toString());
            if (lootitemfunction.b() != null && lootitemfunction.b().length > 0) {
                jsonobject.add("conditions", jsonserializationcontext.serialize(lootitemfunction.b()));
            }

            return jsonobject;
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((LootItemFunction)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
