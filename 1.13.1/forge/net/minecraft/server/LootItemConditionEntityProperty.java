package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public class LootItemConditionEntityProperty implements LootItemCondition {
    private final LootEntityProperty[] a;
    private final LootTableInfo.EntityTarget b;

    public LootItemConditionEntityProperty(LootEntityProperty[] alootentityproperty, LootTableInfo.EntityTarget loottableinfo$entitytarget) {
        this.a = alootentityproperty;
        this.b = loottableinfo$entitytarget;
    }

    public boolean a(Random random, LootTableInfo loottableinfo) {
        Entity entity = loottableinfo.a(this.b);
        if (entity == null) {
            return false;
        } else {
            for(LootEntityProperty lootentityproperty : this.a) {
                if (!lootentityproperty.a(random, entity)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static class a extends LootItemCondition.a<LootItemConditionEntityProperty> {
        protected a() {
            super(new MinecraftKey("entity_properties"), LootItemConditionEntityProperty.class);
        }

        public void a(JsonObject jsonobject, LootItemConditionEntityProperty lootitemconditionentityproperty, JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject1 = new JsonObject();

            for(LootEntityProperty lootentityproperty : lootitemconditionentityproperty.a) {
                LootEntityProperty.a lootentityproperty$a = LootEntityProperties.a(lootentityproperty);
                jsonobject1.add(lootentityproperty$a.a().toString(), lootentityproperty$a.a(lootentityproperty, jsonserializationcontext));
            }

            jsonobject.add("properties", jsonobject1);
            jsonobject.add("entity", jsonserializationcontext.serialize(lootitemconditionentityproperty.b));
        }

        public LootItemConditionEntityProperty a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            Set set = ChatDeserializer.t(jsonobject, "properties").entrySet();
            LootEntityProperty[] alootentityproperty = new LootEntityProperty[set.size()];
            int i = 0;

            for(Entry entry : set) {
                alootentityproperty[i++] = LootEntityProperties.a(new MinecraftKey((String)entry.getKey())).a((JsonElement)entry.getValue(), jsondeserializationcontext);
            }

            return new LootItemConditionEntityProperty(alootentityproperty, (LootTableInfo.EntityTarget)ChatDeserializer.a(jsonobject, "entity", jsondeserializationcontext, LootTableInfo.EntityTarget.class));
        }

        // $FF: synthetic method
        public LootItemCondition b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            return this.a(jsonobject, jsondeserializationcontext);
        }
    }
}
