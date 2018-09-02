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

public class CriterionTriggerCuredZombieVillager implements CriterionTrigger<CriterionTriggerCuredZombieVillager.b> {
    private static final MinecraftKey a = new MinecraftKey("cured_zombie_villager");
    private final Map<AdvancementDataPlayer, CriterionTriggerCuredZombieVillager.a> b = Maps.newHashMap();

    public CriterionTriggerCuredZombieVillager() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerCuredZombieVillager.b> criteriontrigger$a) {
        CriterionTriggerCuredZombieVillager.a criteriontriggercuredzombievillager$a = (CriterionTriggerCuredZombieVillager.a)this.b.get(advancementdataplayer);
        if (criteriontriggercuredzombievillager$a == null) {
            criteriontriggercuredzombievillager$a = new CriterionTriggerCuredZombieVillager.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggercuredzombievillager$a);
        }

        criteriontriggercuredzombievillager$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerCuredZombieVillager.b> criteriontrigger$a) {
        CriterionTriggerCuredZombieVillager.a criteriontriggercuredzombievillager$a = (CriterionTriggerCuredZombieVillager.a)this.b.get(advancementdataplayer);
        if (criteriontriggercuredzombievillager$a != null) {
            criteriontriggercuredzombievillager$a.b(criteriontrigger$a);
            if (criteriontriggercuredzombievillager$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerCuredZombieVillager.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("zombie"));
        CriterionConditionEntity criterionconditionentity1 = CriterionConditionEntity.a(jsonobject.get("villager"));
        return new CriterionTriggerCuredZombieVillager.b(criterionconditionentity, criterionconditionentity1);
    }

    public void a(EntityPlayer entityplayer, EntityZombie entityzombie, EntityVillager entityvillager) {
        CriterionTriggerCuredZombieVillager.a criteriontriggercuredzombievillager$a = (CriterionTriggerCuredZombieVillager.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggercuredzombievillager$a != null) {
            criteriontriggercuredzombievillager$a.a(entityplayer, entityzombie, entityvillager);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerCuredZombieVillager.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerCuredZombieVillager.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerCuredZombieVillager.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, EntityZombie entityzombie, EntityVillager entityvillager) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerCuredZombieVillager.b)criteriontrigger$a.a()).a(entityplayer, entityzombie, entityvillager)) {
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

        public b(CriterionConditionEntity criterionconditionentity, CriterionConditionEntity criterionconditionentity1) {
            super(CriterionTriggerCuredZombieVillager.a);
            this.a = criterionconditionentity;
            this.b = criterionconditionentity1;
        }

        public static CriterionTriggerCuredZombieVillager.b c() {
            return new CriterionTriggerCuredZombieVillager.b(CriterionConditionEntity.a, CriterionConditionEntity.a);
        }

        public boolean a(EntityPlayer entityplayer, EntityZombie entityzombie, EntityVillager entityvillager) {
            if (!this.a.a(entityplayer, entityzombie)) {
                return false;
            } else {
                return this.b.a(entityplayer, entityvillager);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("zombie", this.a.a());
            jsonobject.add("villager", this.b.a());
            return jsonobject;
        }
    }
}
