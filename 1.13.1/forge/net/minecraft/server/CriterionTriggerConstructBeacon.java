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

public class CriterionTriggerConstructBeacon implements CriterionTrigger<CriterionTriggerConstructBeacon.b> {
    private static final MinecraftKey a = new MinecraftKey("construct_beacon");
    private final Map<AdvancementDataPlayer, CriterionTriggerConstructBeacon.a> b = Maps.newHashMap();

    public CriterionTriggerConstructBeacon() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerConstructBeacon.b> criteriontrigger$a) {
        CriterionTriggerConstructBeacon.a criteriontriggerconstructbeacon$a = (CriterionTriggerConstructBeacon.a)this.b.get(advancementdataplayer);
        if (criteriontriggerconstructbeacon$a == null) {
            criteriontriggerconstructbeacon$a = new CriterionTriggerConstructBeacon.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerconstructbeacon$a);
        }

        criteriontriggerconstructbeacon$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerConstructBeacon.b> criteriontrigger$a) {
        CriterionTriggerConstructBeacon.a criteriontriggerconstructbeacon$a = (CriterionTriggerConstructBeacon.a)this.b.get(advancementdataplayer);
        if (criteriontriggerconstructbeacon$a != null) {
            criteriontriggerconstructbeacon$a.b(criteriontrigger$a);
            if (criteriontriggerconstructbeacon$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerConstructBeacon.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject.get("level"));
        return new CriterionTriggerConstructBeacon.b(criterionconditionvalue$d);
    }

    public void a(EntityPlayer entityplayer, TileEntityBeacon tileentitybeacon) {
        CriterionTriggerConstructBeacon.a criteriontriggerconstructbeacon$a = (CriterionTriggerConstructBeacon.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerconstructbeacon$a != null) {
            criteriontriggerconstructbeacon$a.a(tileentitybeacon);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerConstructBeacon.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerConstructBeacon.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerConstructBeacon.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(TileEntityBeacon tileentitybeacon) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerConstructBeacon.b)criteriontrigger$a.a()).a(tileentitybeacon)) {
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
        private final CriterionConditionValue.d a;

        public b(CriterionConditionValue.d criterionconditionvalue$d) {
            super(CriterionTriggerConstructBeacon.a);
            this.a = criterionconditionvalue$d;
        }

        public static CriterionTriggerConstructBeacon.b a(CriterionConditionValue.d criterionconditionvalue$d) {
            return new CriterionTriggerConstructBeacon.b(criterionconditionvalue$d);
        }

        public boolean a(TileEntityBeacon tileentitybeacon) {
            return this.a.d(tileentitybeacon.s());
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("level", this.a.d());
            return jsonobject;
        }
    }
}
