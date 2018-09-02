package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public abstract class LootItemFunction {
    private final LootItemCondition[] a;

    protected LootItemFunction(LootItemCondition[] alootitemcondition) {
        this.a = alootitemcondition;
    }

    public abstract ItemStack a(ItemStack var1, Random var2, LootTableInfo var3);

    public LootItemCondition[] b() {
        return this.a;
    }

    public abstract static class a<T extends LootItemFunction> {
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

        public abstract T b(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);
    }
}
