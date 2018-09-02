package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public interface LootEntityProperty {
    boolean a(Random var1, Entity var2);

    public abstract static class a<T extends LootEntityProperty> {
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

        public abstract JsonElement a(T var1, JsonSerializationContext var2);

        public abstract T a(JsonElement var1, JsonDeserializationContext var2);
    }
}
