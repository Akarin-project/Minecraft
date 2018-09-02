package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;

public class LootSelectorEmpty extends LootSelectorEntry {
    public LootSelectorEmpty(int i, int j, LootItemCondition[] alootitemcondition) {
        super(i, j, alootitemcondition);
    }

    public void a(Collection<ItemStack> var1, Random var2, LootTableInfo var3) {
    }

    protected void a(JsonObject var1, JsonSerializationContext var2) {
    }

    public static LootSelectorEmpty a(JsonObject var0, JsonDeserializationContext var1, int i, int j, LootItemCondition[] alootitemcondition) {
        return new LootSelectorEmpty(i, j, alootitemcondition);
    }
}
