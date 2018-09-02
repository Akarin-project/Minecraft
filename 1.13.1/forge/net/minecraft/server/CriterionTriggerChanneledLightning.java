package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerChanneledLightning implements CriterionTrigger<CriterionTriggerChanneledLightning.b> {
    private static final MinecraftKey a = new MinecraftKey("channeled_lightning");
    private final Map<AdvancementDataPlayer, CriterionTriggerChanneledLightning.a> b = Maps.newHashMap();

    public CriterionTriggerChanneledLightning() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerChanneledLightning.b> criteriontrigger$a) {
        CriterionTriggerChanneledLightning.a criteriontriggerchanneledlightning$a = (CriterionTriggerChanneledLightning.a)this.b.get(advancementdataplayer);
        if (criteriontriggerchanneledlightning$a == null) {
            criteriontriggerchanneledlightning$a = new CriterionTriggerChanneledLightning.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerchanneledlightning$a);
        }

        criteriontriggerchanneledlightning$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerChanneledLightning.b> criteriontrigger$a) {
        CriterionTriggerChanneledLightning.a criteriontriggerchanneledlightning$a = (CriterionTriggerChanneledLightning.a)this.b.get(advancementdataplayer);
        if (criteriontriggerchanneledlightning$a != null) {
            criteriontriggerchanneledlightning$a.b(criteriontrigger$a);
            if (criteriontriggerchanneledlightning$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerChanneledLightning.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity[] acriterionconditionentity = CriterionConditionEntity.b(jsonobject.get("victims"));
        return new CriterionTriggerChanneledLightning.b(acriterionconditionentity);
    }

    public void a(EntityPlayer entityplayer, Collection<? extends Entity> collection) {
        CriterionTriggerChanneledLightning.a criteriontriggerchanneledlightning$a = (CriterionTriggerChanneledLightning.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerchanneledlightning$a != null) {
            criteriontriggerchanneledlightning$a.a(entityplayer, collection);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerChanneledLightning.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerChanneledLightning.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerChanneledLightning.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, Collection<? extends Entity> collection) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerChanneledLightning.b)criteriontrigger$a.a()).a(entityplayer, collection)) {
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
        private final CriterionConditionEntity[] a;

        public b(CriterionConditionEntity[] acriterionconditionentity) {
            super(CriterionTriggerChanneledLightning.a);
            this.a = acriterionconditionentity;
        }

        public static CriterionTriggerChanneledLightning.b a(CriterionConditionEntity... acriterionconditionentity) {
            return new CriterionTriggerChanneledLightning.b(acriterionconditionentity);
        }

        public boolean a(EntityPlayer entityplayer, Collection<? extends Entity> collection) {
            for(CriterionConditionEntity criterionconditionentity : this.a) {
                boolean flag = false;

                for(Entity entity : collection) {
                    if (criterionconditionentity.a(entityplayer, entity)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    return false;
                }
            }

            return true;
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("victims", CriterionConditionEntity.a(this.a));
            return jsonobject;
        }
    }
}
