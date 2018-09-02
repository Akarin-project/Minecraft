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

public class CriterionTriggerConsumeItem implements CriterionTrigger<CriterionTriggerConsumeItem.b> {
    private static final MinecraftKey a = new MinecraftKey("consume_item");
    private final Map<AdvancementDataPlayer, CriterionTriggerConsumeItem.a> b = Maps.newHashMap();

    public CriterionTriggerConsumeItem() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerConsumeItem.b> criteriontrigger$a) {
        CriterionTriggerConsumeItem.a criteriontriggerconsumeitem$a = (CriterionTriggerConsumeItem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerconsumeitem$a == null) {
            criteriontriggerconsumeitem$a = new CriterionTriggerConsumeItem.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerconsumeitem$a);
        }

        criteriontriggerconsumeitem$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerConsumeItem.b> criteriontrigger$a) {
        CriterionTriggerConsumeItem.a criteriontriggerconsumeitem$a = (CriterionTriggerConsumeItem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerconsumeitem$a != null) {
            criteriontriggerconsumeitem$a.b(criteriontrigger$a);
            if (criteriontriggerconsumeitem$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerConsumeItem.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        return new CriterionTriggerConsumeItem.b(CriterionConditionItem.a(jsonobject.get("item")));
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack) {
        CriterionTriggerConsumeItem.a criteriontriggerconsumeitem$a = (CriterionTriggerConsumeItem.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerconsumeitem$a != null) {
            criteriontriggerconsumeitem$a.a(itemstack);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerConsumeItem.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerConsumeItem.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerConsumeItem.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(ItemStack itemstack) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerConsumeItem.b)criteriontrigger$a.a()).a(itemstack)) {
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

        public b(CriterionConditionItem criterionconditionitem) {
            super(CriterionTriggerConsumeItem.a);
            this.a = criterionconditionitem;
        }

        public static CriterionTriggerConsumeItem.b c() {
            return new CriterionTriggerConsumeItem.b(CriterionConditionItem.a);
        }

        public static CriterionTriggerConsumeItem.b a(IMaterial imaterial) {
            return new CriterionTriggerConsumeItem.b(new CriterionConditionItem((Tag)null, imaterial.getItem(), CriterionConditionValue.d.e, CriterionConditionValue.d.e, new CriterionConditionEnchantments[0], (PotionRegistry)null, CriterionConditionNBT.a));
        }

        public boolean a(ItemStack itemstack) {
            return this.a.a(itemstack);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("item", this.a.a());
            return jsonobject;
        }
    }
}
