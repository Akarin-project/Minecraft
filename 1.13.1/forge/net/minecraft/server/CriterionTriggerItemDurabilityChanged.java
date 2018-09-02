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

public class CriterionTriggerItemDurabilityChanged implements CriterionTrigger<CriterionTriggerItemDurabilityChanged.b> {
    private static final MinecraftKey a = new MinecraftKey("item_durability_changed");
    private final Map<AdvancementDataPlayer, CriterionTriggerItemDurabilityChanged.a> b = Maps.newHashMap();

    public CriterionTriggerItemDurabilityChanged() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerItemDurabilityChanged.b> criteriontrigger$a) {
        CriterionTriggerItemDurabilityChanged.a criteriontriggeritemdurabilitychanged$a = (CriterionTriggerItemDurabilityChanged.a)this.b.get(advancementdataplayer);
        if (criteriontriggeritemdurabilitychanged$a == null) {
            criteriontriggeritemdurabilitychanged$a = new CriterionTriggerItemDurabilityChanged.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggeritemdurabilitychanged$a);
        }

        criteriontriggeritemdurabilitychanged$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerItemDurabilityChanged.b> criteriontrigger$a) {
        CriterionTriggerItemDurabilityChanged.a criteriontriggeritemdurabilitychanged$a = (CriterionTriggerItemDurabilityChanged.a)this.b.get(advancementdataplayer);
        if (criteriontriggeritemdurabilitychanged$a != null) {
            criteriontriggeritemdurabilitychanged$a.b(criteriontrigger$a);
            if (criteriontriggeritemdurabilitychanged$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerItemDurabilityChanged.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("item"));
        CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject.get("durability"));
        CriterionConditionValue.d criterionconditionvalue$d1 = CriterionConditionValue.d.a(jsonobject.get("delta"));
        return new CriterionTriggerItemDurabilityChanged.b(criterionconditionitem, criterionconditionvalue$d, criterionconditionvalue$d1);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack, int i) {
        CriterionTriggerItemDurabilityChanged.a criteriontriggeritemdurabilitychanged$a = (CriterionTriggerItemDurabilityChanged.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggeritemdurabilitychanged$a != null) {
            criteriontriggeritemdurabilitychanged$a.a(itemstack, i);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerItemDurabilityChanged.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerItemDurabilityChanged.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerItemDurabilityChanged.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(ItemStack itemstack, int i) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerItemDurabilityChanged.b)criteriontrigger$a.a()).a(itemstack, i)) {
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
        private final CriterionConditionItem a;
        private final CriterionConditionValue.d b;
        private final CriterionConditionValue.d c;

        public b(CriterionConditionItem criterionconditionitem, CriterionConditionValue.d criterionconditionvalue$d, CriterionConditionValue.d criterionconditionvalue$d1) {
            super(CriterionTriggerItemDurabilityChanged.a);
            this.a = criterionconditionitem;
            this.b = criterionconditionvalue$d;
            this.c = criterionconditionvalue$d1;
        }

        public static CriterionTriggerItemDurabilityChanged.b a(CriterionConditionItem criterionconditionitem, CriterionConditionValue.d criterionconditionvalue$d) {
            return new CriterionTriggerItemDurabilityChanged.b(criterionconditionitem, criterionconditionvalue$d, CriterionConditionValue.d.e);
        }

        public boolean a(ItemStack itemstack, int i) {
            if (!this.a.a(itemstack)) {
                return false;
            } else if (!this.b.d(itemstack.h() - i)) {
                return false;
            } else {
                return this.c.d(itemstack.getDamage() - i);
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("item", this.a.a());
            jsonobject.add("durability", this.b.d());
            jsonobject.add("delta", this.c.d());
            return jsonobject;
        }
    }
}
