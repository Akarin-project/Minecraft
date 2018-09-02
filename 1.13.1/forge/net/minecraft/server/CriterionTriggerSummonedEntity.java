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

public class CriterionTriggerSummonedEntity implements CriterionTrigger<CriterionTriggerSummonedEntity.b> {
    private static final MinecraftKey a = new MinecraftKey("summoned_entity");
    private final Map<AdvancementDataPlayer, CriterionTriggerSummonedEntity.a> b = Maps.newHashMap();

    public CriterionTriggerSummonedEntity() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerSummonedEntity.b> criteriontrigger$a) {
        CriterionTriggerSummonedEntity.a criteriontriggersummonedentity$a = (CriterionTriggerSummonedEntity.a)this.b.get(advancementdataplayer);
        if (criteriontriggersummonedentity$a == null) {
            criteriontriggersummonedentity$a = new CriterionTriggerSummonedEntity.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggersummonedentity$a);
        }

        criteriontriggersummonedentity$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerSummonedEntity.b> criteriontrigger$a) {
        CriterionTriggerSummonedEntity.a criteriontriggersummonedentity$a = (CriterionTriggerSummonedEntity.a)this.b.get(advancementdataplayer);
        if (criteriontriggersummonedentity$a != null) {
            criteriontriggersummonedentity$a.b(criteriontrigger$a);
            if (criteriontriggersummonedentity$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerSummonedEntity.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("entity"));
        return new CriterionTriggerSummonedEntity.b(criterionconditionentity);
    }

    public void a(EntityPlayer entityplayer, Entity entity) {
        CriterionTriggerSummonedEntity.a criteriontriggersummonedentity$a = (CriterionTriggerSummonedEntity.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggersummonedentity$a != null) {
            criteriontriggersummonedentity$a.a(entityplayer, entity);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerSummonedEntity.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerSummonedEntity.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerSummonedEntity.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, Entity entity) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerSummonedEntity.b)criteriontrigger$a.a()).a(entityplayer, entity)) {
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

        public b(CriterionConditionEntity criterionconditionentity) {
            super(CriterionTriggerSummonedEntity.a);
            this.a = criterionconditionentity;
        }

        public static CriterionTriggerSummonedEntity.b a(CriterionConditionEntity.a criterionconditionentity$a) {
            return new CriterionTriggerSummonedEntity.b(criterionconditionentity$a.b());
        }

        public boolean a(EntityPlayer entityplayer, Entity entity) {
            return this.a.a(entityplayer, entity);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entity", this.a.a());
            return jsonobject;
        }
    }
}
