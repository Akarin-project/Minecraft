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

public class CriterionTriggerEntityHurtPlayer implements CriterionTrigger<CriterionTriggerEntityHurtPlayer.b> {
    private static final MinecraftKey a = new MinecraftKey("entity_hurt_player");
    private final Map<AdvancementDataPlayer, CriterionTriggerEntityHurtPlayer.a> b = Maps.newHashMap();

    public CriterionTriggerEntityHurtPlayer() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEntityHurtPlayer.b> criteriontrigger$a) {
        CriterionTriggerEntityHurtPlayer.a criteriontriggerentityhurtplayer$a = (CriterionTriggerEntityHurtPlayer.a)this.b.get(advancementdataplayer);
        if (criteriontriggerentityhurtplayer$a == null) {
            criteriontriggerentityhurtplayer$a = new CriterionTriggerEntityHurtPlayer.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerentityhurtplayer$a);
        }

        criteriontriggerentityhurtplayer$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEntityHurtPlayer.b> criteriontrigger$a) {
        CriterionTriggerEntityHurtPlayer.a criteriontriggerentityhurtplayer$a = (CriterionTriggerEntityHurtPlayer.a)this.b.get(advancementdataplayer);
        if (criteriontriggerentityhurtplayer$a != null) {
            criteriontriggerentityhurtplayer$a.b(criteriontrigger$a);
            if (criteriontriggerentityhurtplayer$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerEntityHurtPlayer.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionDamage criterionconditiondamage = CriterionConditionDamage.a(jsonobject.get("damage"));
        return new CriterionTriggerEntityHurtPlayer.b(criterionconditiondamage);
    }

    public void a(EntityPlayer entityplayer, DamageSource damagesource, float f, float f1, boolean flag) {
        CriterionTriggerEntityHurtPlayer.a criteriontriggerentityhurtplayer$a = (CriterionTriggerEntityHurtPlayer.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerentityhurtplayer$a != null) {
            criteriontriggerentityhurtplayer$a.a(entityplayer, damagesource, f, f1, flag);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerEntityHurtPlayer.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerEntityHurtPlayer.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerEntityHurtPlayer.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, DamageSource damagesource, float f, float f1, boolean flag) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerEntityHurtPlayer.b)criteriontrigger$a.a()).a(entityplayer, damagesource, f, f1, flag)) {
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

        public b(CriterionConditionDamage criterionconditiondamage) {
            super(CriterionTriggerEntityHurtPlayer.a);
            this.a = criterionconditiondamage;
        }

        public static CriterionTriggerEntityHurtPlayer.b a(CriterionConditionDamage.a criterionconditiondamage$a) {
            return new CriterionTriggerEntityHurtPlayer.b(criterionconditiondamage$a.b());
        }

        public boolean a(EntityPlayer entityplayer, DamageSource damagesource, float f, float f1, boolean flag) {
            return this.a.a(entityplayer, damagesource, f, f1, flag);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("damage", this.a.a());
            return jsonobject;
        }
    }
}
