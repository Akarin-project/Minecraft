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

public class CriterionTriggerLevitation implements CriterionTrigger<CriterionTriggerLevitation.b> {
    private static final MinecraftKey a = new MinecraftKey("levitation");
    private final Map<AdvancementDataPlayer, CriterionTriggerLevitation.a> b = Maps.newHashMap();

    public CriterionTriggerLevitation() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerLevitation.b> criteriontrigger$a) {
        CriterionTriggerLevitation.a criteriontriggerlevitation$a = (CriterionTriggerLevitation.a)this.b.get(advancementdataplayer);
        if (criteriontriggerlevitation$a == null) {
            criteriontriggerlevitation$a = new CriterionTriggerLevitation.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerlevitation$a);
        }

        criteriontriggerlevitation$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerLevitation.b> criteriontrigger$a) {
        CriterionTriggerLevitation.a criteriontriggerlevitation$a = (CriterionTriggerLevitation.a)this.b.get(advancementdataplayer);
        if (criteriontriggerlevitation$a != null) {
            criteriontriggerlevitation$a.b(criteriontrigger$a);
            if (criteriontriggerlevitation$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerLevitation.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionDistance criterionconditiondistance = CriterionConditionDistance.a(jsonobject.get("distance"));
        CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject.get("duration"));
        return new CriterionTriggerLevitation.b(criterionconditiondistance, criterionconditionvalue$d);
    }

    public void a(EntityPlayer entityplayer, Vec3D vec3d, int i) {
        CriterionTriggerLevitation.a criteriontriggerlevitation$a = (CriterionTriggerLevitation.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerlevitation$a != null) {
            criteriontriggerlevitation$a.a(entityplayer, vec3d, i);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerLevitation.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerLevitation.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerLevitation.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, Vec3D vec3d, int i) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerLevitation.b)criteriontrigger$a.a()).a(entityplayer, vec3d, i)) {
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
        private final CriterionConditionDistance a;
        private final CriterionConditionValue.d b;

        public b(CriterionConditionDistance criterionconditiondistance, CriterionConditionValue.d criterionconditionvalue$d) {
            super(CriterionTriggerLevitation.a);
            this.a = criterionconditiondistance;
            this.b = criterionconditionvalue$d;
        }

        public static CriterionTriggerLevitation.b a(CriterionConditionDistance criterionconditiondistance) {
            return new CriterionTriggerLevitation.b(criterionconditiondistance, CriterionConditionValue.d.e);
        }

        public boolean a(EntityPlayer entityplayer, Vec3D vec3d, int i) {
            if (!this.a.a(vec3d.x, vec3d.y, vec3d.z, entityplayer.locX, entityplayer.locY, entityplayer.locZ)) {
                return false;
            } else {
                return this.b.d(i);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("distance", this.a.a());
            jsonobject.add("duration", this.b.d());
            return jsonobject;
        }
    }
}
