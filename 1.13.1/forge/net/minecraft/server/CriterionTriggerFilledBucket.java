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

public class CriterionTriggerFilledBucket implements CriterionTrigger<CriterionTriggerFilledBucket.b> {
    private static final MinecraftKey a = new MinecraftKey("filled_bucket");
    private final Map<AdvancementDataPlayer, CriterionTriggerFilledBucket.a> b = Maps.newHashMap();

    public CriterionTriggerFilledBucket() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerFilledBucket.b> criteriontrigger$a) {
        CriterionTriggerFilledBucket.a criteriontriggerfilledbucket$a = (CriterionTriggerFilledBucket.a)this.b.get(advancementdataplayer);
        if (criteriontriggerfilledbucket$a == null) {
            criteriontriggerfilledbucket$a = new CriterionTriggerFilledBucket.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerfilledbucket$a);
        }

        criteriontriggerfilledbucket$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerFilledBucket.b> criteriontrigger$a) {
        CriterionTriggerFilledBucket.a criteriontriggerfilledbucket$a = (CriterionTriggerFilledBucket.a)this.b.get(advancementdataplayer);
        if (criteriontriggerfilledbucket$a != null) {
            criteriontriggerfilledbucket$a.b(criteriontrigger$a);
            if (criteriontriggerfilledbucket$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerFilledBucket.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("item"));
        return new CriterionTriggerFilledBucket.b(criterionconditionitem);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack) {
        CriterionTriggerFilledBucket.a criteriontriggerfilledbucket$a = (CriterionTriggerFilledBucket.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerfilledbucket$a != null) {
            criteriontriggerfilledbucket$a.a(itemstack);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerFilledBucket.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerFilledBucket.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerFilledBucket.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(ItemStack itemstack) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerFilledBucket.b)criteriontrigger$a.a()).a(itemstack)) {
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
            super(CriterionTriggerFilledBucket.a);
            this.a = criterionconditionitem;
        }

        public static CriterionTriggerFilledBucket.b a(CriterionConditionItem criterionconditionitem) {
            return new CriterionTriggerFilledBucket.b(criterionconditionitem);
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
