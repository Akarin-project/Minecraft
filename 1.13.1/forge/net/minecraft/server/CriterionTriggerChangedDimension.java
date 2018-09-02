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

public class CriterionTriggerChangedDimension implements CriterionTrigger<CriterionTriggerChangedDimension.b> {
    private static final MinecraftKey a = new MinecraftKey("changed_dimension");
    private final Map<AdvancementDataPlayer, CriterionTriggerChangedDimension.a> b = Maps.newHashMap();

    public CriterionTriggerChangedDimension() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerChangedDimension.b> criteriontrigger$a) {
        CriterionTriggerChangedDimension.a criteriontriggerchangeddimension$a = (CriterionTriggerChangedDimension.a)this.b.get(advancementdataplayer);
        if (criteriontriggerchangeddimension$a == null) {
            criteriontriggerchangeddimension$a = new CriterionTriggerChangedDimension.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerchangeddimension$a);
        }

        criteriontriggerchangeddimension$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerChangedDimension.b> criteriontrigger$a) {
        CriterionTriggerChangedDimension.a criteriontriggerchangeddimension$a = (CriterionTriggerChangedDimension.a)this.b.get(advancementdataplayer);
        if (criteriontriggerchangeddimension$a != null) {
            criteriontriggerchangeddimension$a.b(criteriontrigger$a);
            if (criteriontriggerchangeddimension$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerChangedDimension.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        DimensionManager dimensionmanager = jsonobject.has("from") ? DimensionManager.a(new MinecraftKey(ChatDeserializer.h(jsonobject, "from"))) : null;
        DimensionManager dimensionmanager1 = jsonobject.has("to") ? DimensionManager.a(new MinecraftKey(ChatDeserializer.h(jsonobject, "to"))) : null;
        return new CriterionTriggerChangedDimension.b(dimensionmanager, dimensionmanager1);
    }

    public void a(EntityPlayer entityplayer, DimensionManager dimensionmanager, DimensionManager dimensionmanager1) {
        CriterionTriggerChangedDimension.a criteriontriggerchangeddimension$a = (CriterionTriggerChangedDimension.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerchangeddimension$a != null) {
            criteriontriggerchangeddimension$a.a(dimensionmanager, dimensionmanager1);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerChangedDimension.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerChangedDimension.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerChangedDimension.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(DimensionManager dimensionmanager, DimensionManager dimensionmanager1) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerChangedDimension.b)criteriontrigger$a.a()).b(dimensionmanager, dimensionmanager1)) {
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
        @Nullable
        private final DimensionManager a;
        @Nullable
        private final DimensionManager b;

        public b(@Nullable DimensionManager dimensionmanager, @Nullable DimensionManager dimensionmanager1) {
            super(CriterionTriggerChangedDimension.a);
            this.a = dimensionmanager;
            this.b = dimensionmanager1;
        }

        public static CriterionTriggerChangedDimension.b a(DimensionManager dimensionmanager) {
            return new CriterionTriggerChangedDimension.b((DimensionManager)null, dimensionmanager);
        }

        public boolean b(DimensionManager dimensionmanager, DimensionManager dimensionmanager1) {
            if (this.a != null && this.a != dimensionmanager) {
                return false;
            } else {
                return this.b == null || this.b == dimensionmanager1;
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            if (this.a != null) {
                jsonobject.addProperty("from", DimensionManager.a(this.a).toString());
            }

            if (this.b != null) {
                jsonobject.addProperty("to", DimensionManager.a(this.b).toString());
            }

            return jsonobject;
        }
    }
}
