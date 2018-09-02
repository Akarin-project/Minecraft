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

public class CriterionTriggerEnchantedItem implements CriterionTrigger<CriterionTriggerEnchantedItem.b> {
    private static final MinecraftKey a = new MinecraftKey("enchanted_item");
    private final Map<AdvancementDataPlayer, CriterionTriggerEnchantedItem.a> b = Maps.newHashMap();

    public CriterionTriggerEnchantedItem() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEnchantedItem.b> criteriontrigger$a) {
        CriterionTriggerEnchantedItem.a criteriontriggerenchanteditem$a = (CriterionTriggerEnchantedItem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerenchanteditem$a == null) {
            criteriontriggerenchanteditem$a = new CriterionTriggerEnchantedItem.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerenchanteditem$a);
        }

        criteriontriggerenchanteditem$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEnchantedItem.b> criteriontrigger$a) {
        CriterionTriggerEnchantedItem.a criteriontriggerenchanteditem$a = (CriterionTriggerEnchantedItem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerenchanteditem$a != null) {
            criteriontriggerenchanteditem$a.b(criteriontrigger$a);
            if (criteriontriggerenchanteditem$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerEnchantedItem.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("item"));
        CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject.get("levels"));
        return new CriterionTriggerEnchantedItem.b(criterionconditionitem, criterionconditionvalue$d);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack, int i) {
        CriterionTriggerEnchantedItem.a criteriontriggerenchanteditem$a = (CriterionTriggerEnchantedItem.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerenchanteditem$a != null) {
            criteriontriggerenchanteditem$a.a(itemstack, i);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerEnchantedItem.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerEnchantedItem.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerEnchantedItem.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(ItemStack itemstack, int i) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerEnchantedItem.b)criteriontrigger$a.a()).a(itemstack, i)) {
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
        private final CriterionConditionItem a;
        private final CriterionConditionValue.d b;

        public b(CriterionConditionItem criterionconditionitem, CriterionConditionValue.d criterionconditionvalue$d) {
            super(CriterionTriggerEnchantedItem.a);
            this.a = criterionconditionitem;
            this.b = criterionconditionvalue$d;
        }

        public static CriterionTriggerEnchantedItem.b c() {
            return new CriterionTriggerEnchantedItem.b(CriterionConditionItem.a, CriterionConditionValue.d.e);
        }

        public boolean a(ItemStack itemstack, int i) {
            if (!this.a.a(itemstack)) {
                return false;
            } else {
                return this.b.d(i);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("item", this.a.a());
            jsonobject.add("levels", this.b.d());
            return jsonobject;
        }
    }
}
