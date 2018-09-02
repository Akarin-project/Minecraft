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

public class CriterionTriggerVillagerTrade implements CriterionTrigger<CriterionTriggerVillagerTrade.b> {
    private static final MinecraftKey a = new MinecraftKey("villager_trade");
    private final Map<AdvancementDataPlayer, CriterionTriggerVillagerTrade.a> b = Maps.newHashMap();

    public CriterionTriggerVillagerTrade() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerVillagerTrade.b> criteriontrigger$a) {
        CriterionTriggerVillagerTrade.a criteriontriggervillagertrade$a = (CriterionTriggerVillagerTrade.a)this.b.get(advancementdataplayer);
        if (criteriontriggervillagertrade$a == null) {
            criteriontriggervillagertrade$a = new CriterionTriggerVillagerTrade.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggervillagertrade$a);
        }

        criteriontriggervillagertrade$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerVillagerTrade.b> criteriontrigger$a) {
        CriterionTriggerVillagerTrade.a criteriontriggervillagertrade$a = (CriterionTriggerVillagerTrade.a)this.b.get(advancementdataplayer);
        if (criteriontriggervillagertrade$a != null) {
            criteriontriggervillagertrade$a.b(criteriontrigger$a);
            if (criteriontriggervillagertrade$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerVillagerTrade.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("villager"));
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("item"));
        return new CriterionTriggerVillagerTrade.b(criterionconditionentity, criterionconditionitem);
    }

    public void a(EntityPlayer entityplayer, EntityVillager entityvillager, ItemStack itemstack) {
        CriterionTriggerVillagerTrade.a criteriontriggervillagertrade$a = (CriterionTriggerVillagerTrade.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggervillagertrade$a != null) {
            criteriontriggervillagertrade$a.a(entityplayer, entityvillager, itemstack);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerVillagerTrade.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerVillagerTrade.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerVillagerTrade.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, EntityVillager entityvillager, ItemStack itemstack) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerVillagerTrade.b)criteriontrigger$a.a()).a(entityplayer, entityvillager, itemstack)) {
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
        private final CriterionConditionEntity a;
        private final CriterionConditionItem b;

        public b(CriterionConditionEntity criterionconditionentity, CriterionConditionItem criterionconditionitem) {
            super(CriterionTriggerVillagerTrade.a);
            this.a = criterionconditionentity;
            this.b = criterionconditionitem;
        }

        public static CriterionTriggerVillagerTrade.b c() {
            return new CriterionTriggerVillagerTrade.b(CriterionConditionEntity.a, CriterionConditionItem.a);
        }

        public boolean a(EntityPlayer entityplayer, EntityVillager entityvillager, ItemStack itemstack) {
            if (!this.a.a(entityplayer, entityvillager)) {
                return false;
            } else {
                return this.b.a(itemstack);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("item", this.b.a());
            jsonobject.add("villager", this.a.a());
            return jsonobject;
        }
    }
}
