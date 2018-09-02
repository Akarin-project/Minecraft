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

public class CriterionTriggerPlayerHurtEntity implements CriterionTrigger<CriterionTriggerPlayerHurtEntity.b> {
    private static final MinecraftKey a = new MinecraftKey("player_hurt_entity");
    private final Map<AdvancementDataPlayer, CriterionTriggerPlayerHurtEntity.a> b = Maps.newHashMap();

    public CriterionTriggerPlayerHurtEntity() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerPlayerHurtEntity.b> criteriontrigger$a) {
        CriterionTriggerPlayerHurtEntity.a criteriontriggerplayerhurtentity$a = (CriterionTriggerPlayerHurtEntity.a)this.b.get(advancementdataplayer);
        if (criteriontriggerplayerhurtentity$a == null) {
            criteriontriggerplayerhurtentity$a = new CriterionTriggerPlayerHurtEntity.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerplayerhurtentity$a);
        }

        criteriontriggerplayerhurtentity$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerPlayerHurtEntity.b> criteriontrigger$a) {
        CriterionTriggerPlayerHurtEntity.a criteriontriggerplayerhurtentity$a = (CriterionTriggerPlayerHurtEntity.a)this.b.get(advancementdataplayer);
        if (criteriontriggerplayerhurtentity$a != null) {
            criteriontriggerplayerhurtentity$a.b(criteriontrigger$a);
            if (criteriontriggerplayerhurtentity$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerPlayerHurtEntity.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionDamage criterionconditiondamage = CriterionConditionDamage.a(jsonobject.get("damage"));
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("entity"));
        return new CriterionTriggerPlayerHurtEntity.b(criterionconditiondamage, criterionconditionentity);
    }

    public void a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource, float f, float f1, boolean flag) {
        CriterionTriggerPlayerHurtEntity.a criteriontriggerplayerhurtentity$a = (CriterionTriggerPlayerHurtEntity.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerplayerhurtentity$a != null) {
            criteriontriggerplayerhurtentity$a.a(entityplayer, entity, damagesource, f, f1, flag);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerPlayerHurtEntity.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerPlayerHurtEntity.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerPlayerHurtEntity.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource, float f, float f1, boolean flag) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerPlayerHurtEntity.b)criteriontrigger$a.a()).a(entityplayer, entity, damagesource, f, f1, flag)) {
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
        private final CriterionConditionDamage a;
        private final CriterionConditionEntity b;

        public b(CriterionConditionDamage criterionconditiondamage, CriterionConditionEntity criterionconditionentity) {
            super(CriterionTriggerPlayerHurtEntity.a);
            this.a = criterionconditiondamage;
            this.b = criterionconditionentity;
        }

        public static CriterionTriggerPlayerHurtEntity.b a(CriterionConditionDamage.a criterionconditiondamage$a) {
            return new CriterionTriggerPlayerHurtEntity.b(criterionconditiondamage$a.b(), CriterionConditionEntity.a);
        }

        public boolean a(EntityPlayer entityplayer, Entity entity, DamageSource damagesource, float f, float f1, boolean flag) {
            if (!this.a.a(entityplayer, damagesource, f, f1, flag)) {
                return false;
            } else {
                return this.b.a(entityplayer, entity);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("damage", this.a.a());
            jsonobject.add("entity", this.b.a());
            return jsonobject;
        }
    }
}
