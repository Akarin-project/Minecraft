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

public class CriterionTriggerNetherTravel implements CriterionTrigger<CriterionTriggerNetherTravel.b> {
    private static final MinecraftKey a = new MinecraftKey("nether_travel");
    private final Map<AdvancementDataPlayer, CriterionTriggerNetherTravel.a> b = Maps.newHashMap();

    public CriterionTriggerNetherTravel() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerNetherTravel.b> criteriontrigger$a) {
        CriterionTriggerNetherTravel.a criteriontriggernethertravel$a = (CriterionTriggerNetherTravel.a)this.b.get(advancementdataplayer);
        if (criteriontriggernethertravel$a == null) {
            criteriontriggernethertravel$a = new CriterionTriggerNetherTravel.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggernethertravel$a);
        }

        criteriontriggernethertravel$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerNetherTravel.b> criteriontrigger$a) {
        CriterionTriggerNetherTravel.a criteriontriggernethertravel$a = (CriterionTriggerNetherTravel.a)this.b.get(advancementdataplayer);
        if (criteriontriggernethertravel$a != null) {
            criteriontriggernethertravel$a.b(criteriontrigger$a);
            if (criteriontriggernethertravel$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerNetherTravel.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionLocation criterionconditionlocation = CriterionConditionLocation.a(jsonobject.get("entered"));
        CriterionConditionLocation criterionconditionlocation1 = CriterionConditionLocation.a(jsonobject.get("exited"));
        CriterionConditionDistance criterionconditiondistance = CriterionConditionDistance.a(jsonobject.get("distance"));
        return new CriterionTriggerNetherTravel.b(criterionconditionlocation, criterionconditionlocation1, criterionconditiondistance);
    }

    public void a(EntityPlayer entityplayer, Vec3D vec3d) {
        CriterionTriggerNetherTravel.a criteriontriggernethertravel$a = (CriterionTriggerNetherTravel.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggernethertravel$a != null) {
            criteriontriggernethertravel$a.a(entityplayer.getWorldServer(), vec3d, entityplayer.locX, entityplayer.locY, entityplayer.locZ);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerNetherTravel.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerNetherTravel.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerNetherTravel.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(WorldServer worldserver, Vec3D vec3d, double d0, double d1, double d2) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerNetherTravel.b)criteriontrigger$a.a()).a(worldserver, vec3d, d0, d1, d2)) {
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
        private final CriterionConditionLocation a;
        private final CriterionConditionLocation b;
        private final CriterionConditionDistance c;

        public b(CriterionConditionLocation criterionconditionlocation, CriterionConditionLocation criterionconditionlocation1, CriterionConditionDistance criterionconditiondistance) {
            super(CriterionTriggerNetherTravel.a);
            this.a = criterionconditionlocation;
            this.b = criterionconditionlocation1;
            this.c = criterionconditiondistance;
        }

        public static CriterionTriggerNetherTravel.b a(CriterionConditionDistance criterionconditiondistance) {
            return new CriterionTriggerNetherTravel.b(CriterionConditionLocation.a, CriterionConditionLocation.a, criterionconditiondistance);
        }

        public boolean a(WorldServer worldserver, Vec3D vec3d, double d0, double d1, double d2) {
            if (!this.a.a(worldserver, vec3d.x, vec3d.y, vec3d.z)) {
                return false;
            } else if (!this.b.a(worldserver, d0, d1, d2)) {
                return false;
            } else {
                return this.c.a(vec3d.x, vec3d.y, vec3d.z, d0, d1, d2);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entered", this.a.a());
            jsonobject.add("exited", this.b.a());
            jsonobject.add("distance", this.c.a());
            return jsonobject;
        }
    }
}
