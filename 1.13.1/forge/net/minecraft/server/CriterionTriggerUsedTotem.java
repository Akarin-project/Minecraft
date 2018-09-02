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

public class CriterionTriggerUsedTotem implements CriterionTrigger<CriterionTriggerUsedTotem.b> {
    private static final MinecraftKey a = new MinecraftKey("used_totem");
    private final Map<AdvancementDataPlayer, CriterionTriggerUsedTotem.a> b = Maps.newHashMap();

    public CriterionTriggerUsedTotem() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerUsedTotem.b> criteriontrigger$a) {
        CriterionTriggerUsedTotem.a criteriontriggerusedtotem$a = (CriterionTriggerUsedTotem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerusedtotem$a == null) {
            criteriontriggerusedtotem$a = new CriterionTriggerUsedTotem.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerusedtotem$a);
        }

        criteriontriggerusedtotem$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerUsedTotem.b> criteriontrigger$a) {
        CriterionTriggerUsedTotem.a criteriontriggerusedtotem$a = (CriterionTriggerUsedTotem.a)this.b.get(advancementdataplayer);
        if (criteriontriggerusedtotem$a != null) {
            criteriontriggerusedtotem$a.b(criteriontrigger$a);
            if (criteriontriggerusedtotem$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerUsedTotem.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("item"));
        return new CriterionTriggerUsedTotem.b(criterionconditionitem);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack) {
        CriterionTriggerUsedTotem.a criteriontriggerusedtotem$a = (CriterionTriggerUsedTotem.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerusedtotem$a != null) {
            criteriontriggerusedtotem$a.a(itemstack);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerUsedTotem.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerUsedTotem.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerUsedTotem.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(ItemStack itemstack) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerUsedTotem.b)criteriontrigger$a.a()).a(itemstack)) {
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
            super(CriterionTriggerUsedTotem.a);
            this.a = criterionconditionitem;
        }

        public static CriterionTriggerUsedTotem.b a(IMaterial imaterial) {
            return new CriterionTriggerUsedTotem.b(CriterionConditionItem.a.a().a(imaterial).b());
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
