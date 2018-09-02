package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class LootItemConditionKilledByPlayer implements LootItemCondition {
    private final boolean a;

    public LootItemConditionKilledByPlayer(boolean flag) {
        this.a = flag;
    }

    public boolean a(Random var1, LootTableInfo loottableinfo) {
        boolean flag = loottableinfo.b() != null;
        return flag == !this.a;
    }

    public static class a extends LootItemCondition.a<LootItemConditionKilledByPlayer> {
        protected a() {
            super(new MinecraftKey("killed_by_player"), LootItemConditionKilledByPlayer.class);
        }

        public void a(JsonObject jsonobject, LootItemConditionKilledByPlayer lootitemconditionkilledbyplayer, JsonSerializationContext var3) {
            jsonobject.addProperty("inverse", lootitemconditionkilledbyplayer.a);
        }

        public LootItemConditionKilledByPlayer a(JsonObject jsonobject, JsonDeserializationContext var2) {
            return new LootItemConditionKilledByPlayer(ChatDeserializer.a(jsonobject, "inverse", false));
        }

        // $FF: synthetic method
        public LootItemCondition b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            return this.a(jsonobject, jsondeserializationcontext);
        }
    }
}
