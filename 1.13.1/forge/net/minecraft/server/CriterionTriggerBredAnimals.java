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
import javax.annotation.Nullable;

public class CriterionTriggerBredAnimals implements CriterionTrigger<CriterionTriggerBredAnimals.b> {
    private static final MinecraftKey a = new MinecraftKey("bred_animals");
    private final Map<AdvancementDataPlayer, CriterionTriggerBredAnimals.a> b = Maps.newHashMap();

    public CriterionTriggerBredAnimals() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerBredAnimals.b> criteriontrigger$a) {
        CriterionTriggerBredAnimals.a criteriontriggerbredanimals$a = (CriterionTriggerBredAnimals.a)this.b.get(advancementdataplayer);
        if (criteriontriggerbredanimals$a == null) {
            criteriontriggerbredanimals$a = new CriterionTriggerBredAnimals.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerbredanimals$a);
        }

        criteriontriggerbredanimals$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerBredAnimals.b> criteriontrigger$a) {
        CriterionTriggerBredAnimals.a criteriontriggerbredanimals$a = (CriterionTriggerBredAnimals.a)this.b.get(advancementdataplayer);
        if (criteriontriggerbredanimals$a != null) {
            criteriontriggerbredanimals$a.b(criteriontrigger$a);
            if (criteriontriggerbredanimals$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerBredAnimals.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("parent"));
        CriterionConditionEntity criterionconditionentity1 = CriterionConditionEntity.a(jsonobject.get("partner"));
        CriterionConditionEntity criterionconditionentity2 = CriterionConditionEntity.a(jsonobject.get("child"));
        return new CriterionTriggerBredAnimals.b(criterionconditionentity, criterionconditionentity1, criterionconditionentity2);
    }

    public void a(EntityPlayer entityplayer, EntityAnimal entityanimal, EntityAnimal entityanimal1, @Nullable EntityAgeable entityageable) {
        CriterionTriggerBredAnimals.a criteriontriggerbredanimals$a = (CriterionTriggerBredAnimals.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerbredanimals$a != null) {
            criteriontriggerbredanimals$a.a(entityplayer, entityanimal, entityanimal1, entityageable);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerBredAnimals.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerBredAnimals.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerBredAnimals.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, EntityAnimal entityanimal, EntityAnimal entityanimal1, @Nullable EntityAgeable entityageable) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerBredAnimals.b)criteriontrigger$a.a()).a(entityplayer, entityanimal, entityanimal1, entityageable)) {
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
        private final CriterionConditionEntity b;
        private final CriterionConditionEntity c;

        public b(CriterionConditionEntity criterionconditionentity, CriterionConditionEntity criterionconditionentity1, CriterionConditionEntity criterionconditionentity2) {
            super(CriterionTriggerBredAnimals.a);
            this.a = criterionconditionentity;
            this.b = criterionconditionentity1;
            this.c = criterionconditionentity2;
        }

        public static CriterionTriggerBredAnimals.b c() {
            return new CriterionTriggerBredAnimals.b(CriterionConditionEntity.a, CriterionConditionEntity.a, CriterionConditionEntity.a);
        }

        public static CriterionTriggerBredAnimals.b a(CriterionConditionEntity.a criterionconditionentity$a) {
            return new CriterionTriggerBredAnimals.b(criterionconditionentity$a.b(), CriterionConditionEntity.a, CriterionConditionEntity.a);
        }

        public boolean a(EntityPlayer entityplayer, EntityAnimal entityanimal, EntityAnimal entityanimal1, @Nullable EntityAgeable entityageable) {
            if (!this.c.a(entityplayer, entityageable)) {
                return false;
            } else {
                return this.a.a(entityplayer, entityanimal) && this.b.a(entityplayer, entityanimal1) || this.a.a(entityplayer, entityanimal1) && this.b.a(entityplayer, entityanimal);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("parent", this.a.a());
            jsonobject.add("partner", this.b.a());
            jsonobject.add("child", this.c.a());
            return jsonobject;
        }
    }
}
