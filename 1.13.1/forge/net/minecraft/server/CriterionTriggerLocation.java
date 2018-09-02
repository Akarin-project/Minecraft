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

public class CriterionTriggerLocation implements CriterionTrigger<CriterionTriggerLocation.b> {
    private final MinecraftKey a;
    private final Map<AdvancementDataPlayer, CriterionTriggerLocation.a> b = Maps.newHashMap();

    public CriterionTriggerLocation(MinecraftKey minecraftkey) {
        this.a = minecraftkey;
    }

    public MinecraftKey a() {
        return this.a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerLocation.b> criteriontrigger$a) {
        CriterionTriggerLocation.a criteriontriggerlocation$a = (CriterionTriggerLocation.a)this.b.get(advancementdataplayer);
        if (criteriontriggerlocation$a == null) {
            criteriontriggerlocation$a = new CriterionTriggerLocation.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerlocation$a);
        }

        criteriontriggerlocation$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerLocation.b> criteriontrigger$a) {
        CriterionTriggerLocation.a criteriontriggerlocation$a = (CriterionTriggerLocation.a)this.b.get(advancementdataplayer);
        if (criteriontriggerlocation$a != null) {
            criteriontriggerlocation$a.b(criteriontrigger$a);
            if (criteriontriggerlocation$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerLocation.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionLocation criterionconditionlocation = CriterionConditionLocation.a(jsonobject);
        return new CriterionTriggerLocation.b(this.a, criterionconditionlocation);
    }

    public void a(EntityPlayer entityplayer) {
        CriterionTriggerLocation.a criteriontriggerlocation$a = (CriterionTriggerLocation.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerlocation$a != null) {
            criteriontriggerlocation$a.a(entityplayer.getWorldServer(), entityplayer.locX, entityplayer.locY, entityplayer.locZ);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerLocation.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerLocation.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerLocation.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(WorldServer worldserver, double d0, double d1, double d2) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerLocation.b)criteriontrigger$a.a()).a(worldserver, d0, d1, d2)) {
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

        public b(MinecraftKey minecraftkey, CriterionConditionLocation criterionconditionlocation) {
            super(minecraftkey);
            this.a = criterionconditionlocation;
        }

        public static CriterionTriggerLocation.b a(CriterionConditionLocation criterionconditionlocation) {
            return new CriterionTriggerLocation.b(CriterionTriggers.p.a, criterionconditionlocation);
        }

        public static CriterionTriggerLocation.b c() {
            return new CriterionTriggerLocation.b(CriterionTriggers.q.a, CriterionConditionLocation.a);
        }

        public boolean a(WorldServer worldserver, double d0, double d1, double d2) {
            return this.a.a(worldserver, d0, d1, d2);
        }

        public JsonElement b() {
            return this.a.a();
        }
    }
}
