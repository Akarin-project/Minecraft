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

public class CriterionTriggerKilled implements CriterionTrigger<CriterionTriggerKilled.b> {
    private final Map<AdvancementDataPlayer, CriterionTriggerKilled.a> a = Maps.newHashMap();
    private final MinecraftKey b;

    public CriterionTriggerKilled(MinecraftKey minecraftkey) {
        this.b = minecraftkey;
    }

    public MinecraftKey a() {
        return this.b;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerKilled.b> criteriontrigger$a) {
        CriterionTriggerKilled.a criteriontriggerkilled$a = (CriterionTriggerKilled.a)this.a.get(advancementdataplayer);
        if (criteriontriggerkilled$a == null) {
            criteriontriggerkilled$a = new CriterionTriggerKilled.a(advancementdataplayer);
            this.a.put(advancementdataplayer, criteriontriggerkilled$a);
        }

        criteriontriggerkilled$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerKilled.b> criteriontrigger$a) {
        CriterionTriggerKilled.a criteriontriggerkilled$a = (CriterionTriggerKilled.a)this.a.get(advancementdataplayer);
        if (criteriontriggerkilled$a != null) {
            criteriontriggerkilled$a.b(criteriontrigger$a);
            if (criteriontriggerkilled$a.a()) {
                this.a.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.a.remove(advancementdataplayer);
    }

    public CriterionTriggerKilled.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        return new CriterionTriggerKilled.b(this.b, CriterionConditionEntity.a(jsonobject.get("entity")), CriterionConditionDamageSource.a(jsonobject.get("killing_blow")));
    }

    public void a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource) {
        CriterionTriggerKilled.a criteriontriggerkilled$a = (CriterionTriggerKilled.a)this.a.get(entityplayer.getAdvancementData());
        if (criteriontriggerkilled$a != null) {
            criteriontriggerkilled$a.a(entityplayer, entity, damagesource);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerKilled.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerKilled.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerKilled.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerKilled.b)criteriontrigger$a.a()).a(entityplayer, entity, damagesource)) {
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
        private final CriterionConditionDamageSource b;

        public b(MinecraftKey minecraftkey, CriterionConditionEntity criterionconditionentity, CriterionConditionDamageSource criterionconditiondamagesource) {
            super(minecraftkey);
            this.a = criterionconditionentity;
            this.b = criterionconditiondamagesource;
        }

        public static CriterionTriggerKilled.b a(CriterionConditionEntity.a criterionconditionentity$a) {
            return new CriterionTriggerKilled.b(CriterionTriggers.b.b, criterionconditionentity$a.b(), CriterionConditionDamageSource.a);
        }

        public static CriterionTriggerKilled.b c() {
            return new CriterionTriggerKilled.b(CriterionTriggers.b.b, CriterionConditionEntity.a, CriterionConditionDamageSource.a);
        }

        public static CriterionTriggerKilled.b a(CriterionConditionEntity.a criterionconditionentity$a, CriterionConditionDamageSource.a criterionconditiondamagesource$a) {
            return new CriterionTriggerKilled.b(CriterionTriggers.b.b, criterionconditionentity$a.b(), criterionconditiondamagesource$a.b());
        }

        public static CriterionTriggerKilled.b d() {
            return new CriterionTriggerKilled.b(CriterionTriggers.c.b, CriterionConditionEntity.a, CriterionConditionDamageSource.a);
        }

        public boolean a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource) {
            return !this.b.a(entityplayer, damagesource) ? false : this.a.a(entityplayer, entity);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entity", this.a.a());
            jsonobject.add("killing_blow", this.b.a());
            return jsonobject;
        }
    }
}
