package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerRecipeUnlocked implements CriterionTrigger<CriterionTriggerRecipeUnlocked.b> {
    private static final MinecraftKey a = new MinecraftKey("recipe_unlocked");
    private final Map<AdvancementDataPlayer, CriterionTriggerRecipeUnlocked.a> b = Maps.newHashMap();

    public CriterionTriggerRecipeUnlocked() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerRecipeUnlocked.b> criteriontrigger$a) {
        CriterionTriggerRecipeUnlocked.a criteriontriggerrecipeunlocked$a = (CriterionTriggerRecipeUnlocked.a)this.b.get(advancementdataplayer);
        if (criteriontriggerrecipeunlocked$a == null) {
            criteriontriggerrecipeunlocked$a = new CriterionTriggerRecipeUnlocked.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerrecipeunlocked$a);
        }

        criteriontriggerrecipeunlocked$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerRecipeUnlocked.b> criteriontrigger$a) {
        CriterionTriggerRecipeUnlocked.a criteriontriggerrecipeunlocked$a = (CriterionTriggerRecipeUnlocked.a)this.b.get(advancementdataplayer);
        if (criteriontriggerrecipeunlocked$a != null) {
            criteriontriggerrecipeunlocked$a.b(criteriontrigger$a);
            if (criteriontriggerrecipeunlocked$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerRecipeUnlocked.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "recipe"));
        return new CriterionTriggerRecipeUnlocked.b(minecraftkey);
    }

    public void a(EntityPlayer entityplayer, IRecipe irecipe) {
        CriterionTriggerRecipeUnlocked.a criteriontriggerrecipeunlocked$a = (CriterionTriggerRecipeUnlocked.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerrecipeunlocked$a != null) {
            criteriontriggerrecipeunlocked$a.a(irecipe);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerRecipeUnlocked.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerRecipeUnlocked.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerRecipeUnlocked.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(IRecipe irecipe) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerRecipeUnlocked.b)criteriontrigger$a.a()).a(irecipe)) {
                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(criteriontrigger$a);
                }
            }

            if (arraylist != null) {
                for(CriterionTrigger.a criteriontrigger$a1 : arraylist) {
                    criteriontrigger$a1.a(this.a);
                }
            }

        }
    }

    public static class b extends CriterionInstanceAbstract {
        private final MinecraftKey a;

        public b(MinecraftKey minecraftkey) {
            super(CriterionTriggerRecipeUnlocked.a);
            this.a = minecraftkey;
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("recipe", this.a.toString());
            return jsonobject;
        }

        public boolean a(IRecipe irecipe) {
            return this.a.equals(irecipe.getKey());
        }
    }
}
