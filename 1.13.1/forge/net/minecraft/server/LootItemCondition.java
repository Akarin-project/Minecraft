package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public interface LootItemCondition {
    boolean a(Random var1, LootTableInfo var2);

    public abstract static class a<T extends LootItemCondition> {
        private final MinecraftKey a;
        private final Class<T> b;

        protected a(MinecraftKey minecraftkey, Class<T> oclass) {
            this.a = minecraftkey;
            this.b = oclass;
        }

        public MinecraftKey a() {
            return this.a;
        }

        public Class<T> b() {
            return this.b;
        }

        public abstract void a(JsonObject var1, T var2, JsonSerializationContext var3);

        public abstract T b(JsonObject var1, JsonDeserializationContext var2);
    }
}
