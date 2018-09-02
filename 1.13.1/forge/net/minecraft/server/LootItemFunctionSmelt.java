package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootItemFunctionSmelt extends LootItemFunction {
    private static final Logger a = LogManager.getLogger();

    public LootItemFunctionSmelt(LootItemCondition[] alootitemcondition) {
        super(alootitemcondition);
    }

    public ItemStack a(ItemStack itemstack, Random var2, LootTableInfo loottableinfo) {
        if (itemstack.isEmpty()) {
            return itemstack;
        } else {
            IRecipe irecipe = a(loottableinfo, itemstack);
            if (irecipe != null) {
                ItemStack itemstack1 = irecipe.d();
                if (!itemstack1.isEmpty()) {
                    ItemStack itemstack2 = itemstack1.cloneItemStack();
                    itemstack2.setCount(itemstack.getCount());
                    return itemstack2;
                }
            }

            a.warn("Couldn't smelt {} because there is no smelting recipe", itemstack);
            return itemstack;
        }
    }

    @Nullable
    public static IRecipe a(LootTableInfo loottableinfo, ItemStack itemstack) {
        for(IRecipe irecipe : loottableinfo.h().E().b()) {
            if (irecipe instanceof FurnaceRecipe && ((RecipeItemStack)irecipe.e().get(0)).a(itemstack)) {
                return irecipe;
            }
        }

        return null;
    }

    public static class a extends LootItemFunction.a<LootItemFunctionSmelt> {
        protected a() {
            super(new MinecraftKey("furnace_smelt"), LootItemFunctionSmelt.class);
        }

        public void a(JsonObject var1, LootItemFunctionSmelt var2, JsonSerializationContext var3) {
        }

        public LootItemFunctionSmelt a(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] alootitemcondition) {
            return new LootItemFunctionSmelt(alootitemcondition);
        }

        // $FF: synthetic method
        public LootItemFunction b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            return this.a(jsonobject, jsondeserializationcontext, alootitemcondition);
        }
    }
}
