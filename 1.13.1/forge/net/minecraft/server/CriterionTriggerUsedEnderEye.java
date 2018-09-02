package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerUsedEnderEye implements CriterionTrigger<CriterionTriggerUsedEnderEye.b> {
    private static final MinecraftKey a = new MinecraftKey("used_ender_eye");
    private final Map<AdvancementDataPlayer, CriterionTriggerUsedEnderEye.a> b = Maps.newHashMap();

    public CriterionTriggerUsedEnderEye() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerUsedEnderEye.b> criteriontrigger$a) {
        CriterionTriggerUsedEnderEye.a criteriontriggerusedendereye$a = (CriterionTriggerUsedEnderEye.a)this.b.get(advancementdataplayer);
        if (criteriontriggerusedendereye$a == null) {
            criteriontriggerusedendereye$a = new CriterionTriggerUsedEnderEye.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerusedendereye$a);
        }

        criteriontriggerusedendereye$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerUsedEnderEye.b> criteriontrigger$a) {
        CriterionTriggerUsedEnderEye.a criteriontriggerusedendereye$a = (CriterionTriggerUsedEnderEye.a)this.b.get(advancementdataplayer);
        if (criteriontriggerusedendereye$a != null) {
            criteriontriggerusedendereye$a.b(criteriontrigger$a);
            if (criteriontriggerusedendereye$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerUsedEnderEye.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionValue.c criterionconditionvalue$c = CriterionConditionValue.c.a(jsonobject.get("distance"));
        return new CriterionTriggerUsedEnderEye.b(criterionconditionvalue$c);
    }

    public void a(EntityPlayer entityplayer, BlockPosition blockposition) {
        CriterionTriggerUsedEnderEye.a criteriontriggerusedendereye$a = (CriterionTriggerUsedEnderEye.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerusedendereye$a != null) {
            double d0 = entityplayer.locX - (double)blockposition.getX();
            double d1 = entityplayer.locZ - (double)blockposition.getZ();
            criteriontriggerusedendereye$a.a(d0 * d0 + d1 * d1);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerUsedEnderEye.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerUsedEnderEye.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerUsedEnderEye.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(double d0) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerUsedEnderEye.b)criteriontrigger$a.a()).a(d0)) {
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
        private final CriterionConditionValue.c a;

        public b(CriterionConditionValue.c criterionconditionvalue$c) {
            super(CriterionTriggerUsedEnderEye.a);
            this.a = criterionconditionvalue$c;
        }

        public boolean a(double d0) {
            return this.a.a(d0);
        }
    }
}
