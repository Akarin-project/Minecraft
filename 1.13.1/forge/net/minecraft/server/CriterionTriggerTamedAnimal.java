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

public class CriterionTriggerTamedAnimal implements CriterionTrigger<CriterionTriggerTamedAnimal.b> {
    private static final MinecraftKey a = new MinecraftKey("tame_animal");
    private final Map<AdvancementDataPlayer, CriterionTriggerTamedAnimal.a> b = Maps.newHashMap();

    public CriterionTriggerTamedAnimal() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerTamedAnimal.b> criteriontrigger$a) {
        CriterionTriggerTamedAnimal.a criteriontriggertamedanimal$a = (CriterionTriggerTamedAnimal.a)this.b.get(advancementdataplayer);
        if (criteriontriggertamedanimal$a == null) {
            criteriontriggertamedanimal$a = new CriterionTriggerTamedAnimal.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggertamedanimal$a);
        }

        criteriontriggertamedanimal$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerTamedAnimal.b> criteriontrigger$a) {
        CriterionTriggerTamedAnimal.a criteriontriggertamedanimal$a = (CriterionTriggerTamedAnimal.a)this.b.get(advancementdataplayer);
        if (criteriontriggertamedanimal$a != null) {
            criteriontriggertamedanimal$a.b(criteriontrigger$a);
            if (criteriontriggertamedanimal$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerTamedAnimal.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("entity"));
        return new CriterionTriggerTamedAnimal.b(criterionconditionentity);
    }

    public void a(EntityPlayer entityplayer, EntityAnimal entityanimal) {
        CriterionTriggerTamedAnimal.a criteriontriggertamedanimal$a = (CriterionTriggerTamedAnimal.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggertamedanimal$a != null) {
            criteriontriggertamedanimal$a.a(entityplayer, entityanimal);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerTamedAnimal.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerTamedAnimal.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerTamedAnimal.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, EntityAnimal entityanimal) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerTamedAnimal.b)criteriontrigger$a.a()).a(entityplayer, entityanimal)) {
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
            super(CriterionTriggerTamedAnimal.a);
            this.a = criterionconditionentity;
        }

        public static CriterionTriggerTamedAnimal.b c() {
            return new CriterionTriggerTamedAnimal.b(CriterionConditionEntity.a);
        }

        public boolean a(EntityPlayer entityplayer, EntityAnimal entityanimal) {
            return this.a.a(entityplayer, entityanimal);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entity", this.a.a());
            return jsonobject;
        }
    }
}
