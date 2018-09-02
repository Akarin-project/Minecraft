package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class LootItemConditionRandomChance implements LootItemCondition {
    private final float a;

    public LootItemConditionRandomChance(float f) {
        this.a = f;
    }

    public boolean a(Random random, LootTableInfo var2) {
        return random.nextFloat() < this.a;
    }

    public static class a extends LootItemCondition.a<LootItemConditionRandomChance> {
        protected a() {
            super(new MinecraftKey("random_chance"), LootItemConditionRandomChance.class);
        }

        public void a(JsonObject jsonobject, LootItemConditionRandomChance lootitemconditionrandomchance, JsonSerializationContext var3) {
            jsonobject.addProperty("chance", lootitemconditionrandomchance.a);
        }

        public LootItemConditionRandomChance a(JsonObject jsonobject, JsonDeserializationContext var2) {
            return new LootItemConditionRandomChance(ChatDeserializer.l(jsonobject, "chance"));
        }

        // $FF: synthetic method
        public LootItemCondition b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            return this.a(jsonobject, jsondeserializationcontext);
        }
    }
}
