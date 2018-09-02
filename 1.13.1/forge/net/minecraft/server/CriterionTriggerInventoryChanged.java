package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerInventoryChanged implements CriterionTrigger<CriterionTriggerInventoryChanged.b> {
    private static final MinecraftKey a = new MinecraftKey("inventory_changed");
    private final Map<AdvancementDataPlayer, CriterionTriggerInventoryChanged.a> b = Maps.newHashMap();

    public CriterionTriggerInventoryChanged() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerInventoryChanged.b> criteriontrigger$a) {
        CriterionTriggerInventoryChanged.a criteriontriggerinventorychanged$a = (CriterionTriggerInventoryChanged.a)this.b.get(advancementdataplayer);
        if (criteriontriggerinventorychanged$a == null) {
            criteriontriggerinventorychanged$a = new CriterionTriggerInventoryChanged.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerinventorychanged$a);
        }

        criteriontriggerinventorychanged$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerInventoryChanged.b> criteriontrigger$a) {
        CriterionTriggerInventoryChanged.a criteriontriggerinventorychanged$a = (CriterionTriggerInventoryChanged.a)this.b.get(advancementdataplayer);
        if (criteriontriggerinventorychanged$a != null) {
            criteriontriggerinventorychanged$a.b(criteriontrigger$a);
            if (criteriontriggerinventorychanged$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerInventoryChanged.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        JsonObject jsonobject1 = ChatDeserializer.a(jsonobject, "slots", new JsonObject());
        CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject1.get("occupied"));
        CriterionConditionValue.d criterionconditionvalue$d1 = CriterionConditionValue.d.a(jsonobject1.get("full"));
        CriterionConditionValue.d criterionconditionvalue$d2 = CriterionConditionValue.d.a(jsonobject1.get("empty"));
        CriterionConditionItem[] acriterionconditionitem = CriterionConditionItem.b(jsonobject.get("items"));
        return new CriterionTriggerInventoryChanged.b(criterionconditionvalue$d, criterionconditionvalue$d1, criterionconditionvalue$d2, acriterionconditionitem);
    }

    public void a(EntityPlayer entityplayer, PlayerInventory playerinventory) {
        CriterionTriggerInventoryChanged.a criteriontriggerinventorychanged$a = (CriterionTriggerInventoryChanged.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerinventorychanged$a != null) {
            criteriontriggerinventorychanged$a.a(playerinventory);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerInventoryChanged.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerInventoryChanged.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerInventoryChanged.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(PlayerInventory playerinventory) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerInventoryChanged.b)criteriontrigger$a.a()).a(playerinventory)) {
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
        private final CriterionConditionValue.d b;
        private final CriterionConditionValue.d c;
        private final CriterionConditionItem[] d;

        public b(CriterionConditionValue.d criterionconditionvalue$d, CriterionConditionValue.d criterionconditionvalue$d1, CriterionConditionValue.d criterionconditionvalue$d2, CriterionConditionItem[] acriterionconditionitem) {
            super(CriterionTriggerInventoryChanged.a);
            this.a = criterionconditionvalue$d;
            this.b = criterionconditionvalue$d1;
            this.c = criterionconditionvalue$d2;
            this.d = acriterionconditionitem;
        }

        public static CriterionTriggerInventoryChanged.b a(CriterionConditionItem... acriterionconditionitem) {
            return new CriterionTriggerInventoryChanged.b(CriterionConditionValue.d.e, CriterionConditionValue.d.e, CriterionConditionValue.d.e, acriterionconditionitem);
        }

        public static CriterionTriggerInventoryChanged.b a(IMaterial... aimaterial) {
            CriterionConditionItem[] acriterionconditionitem = new CriterionConditionItem[aimaterial.length];

            for(int i = 0; i < aimaterial.length; ++i) {
                acriterionconditionitem[i] = new CriterionConditionItem((Tag)null, aimaterial[i].getItem(), CriterionConditionValue.d.e, CriterionConditionValue.d.e, new CriterionConditionEnchantments[0], (PotionRegistry)null, CriterionConditionNBT.a);
            }

            return a(acriterionconditionitem);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            if (!this.a.c() || !this.b.c() || !this.c.c()) {
                JsonObject jsonobject1 = new JsonObject();
                jsonobject1.add("occupied", this.a.d());
                jsonobject1.add("full", this.b.d());
                jsonobject1.add("empty", this.c.d());
                jsonobject.add("slots", jsonobject1);
            }

            if (this.d.length > 0) {
                JsonArray jsonarray = new JsonArray();

                for(CriterionConditionItem criterionconditionitem : this.d) {
                    jsonarray.add(criterionconditionitem.a());
                }

                jsonobject.add("items", jsonarray);
            }

            return jsonobject;
        }

        public boolean a(PlayerInventory playerinventory) {
            int i = 0;
            int j = 0;
            int k = 0;
            ArrayList arraylist = Lists.newArrayList(this.d);

            for(int l = 0; l < playerinventory.getSize(); ++l) {
                ItemStack itemstack = playerinventory.getItem(l);
                if (itemstack.isEmpty()) {
                    ++j;
                } else {
                    ++k;
                    if (itemstack.getCount() >= itemstack.getMaxStackSize()) {
                        ++i;
                    }

                    Iterator iterator = arraylist.iterator();

                    while(iterator.hasNext()) {
                        CriterionConditionItem criterionconditionitem = (CriterionConditionItem)iterator.next();
                        if (criterionconditionitem.a(itemstack)) {
                            iterator.remove();
                        }
                    }
                }
            }

            if (!this.b.d(i)) {
                return false;
            } else if (!this.c.d(j)) {
                return false;
            } else if (!this.a.d(k)) {
                return false;
            } else if (!arraylist.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
