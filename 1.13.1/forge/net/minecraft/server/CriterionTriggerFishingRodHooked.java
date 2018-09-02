package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerFishingRodHooked implements CriterionTrigger<CriterionTriggerFishingRodHooked.b> {
    private static final MinecraftKey a = new MinecraftKey("fishing_rod_hooked");
    private final Map<AdvancementDataPlayer, CriterionTriggerFishingRodHooked.a> b = Maps.newHashMap();

    public CriterionTriggerFishingRodHooked() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerFishingRodHooked.b> criteriontrigger$a) {
        CriterionTriggerFishingRodHooked.a criteriontriggerfishingrodhooked$a = (CriterionTriggerFishingRodHooked.a)this.b.get(advancementdataplayer);
        if (criteriontriggerfishingrodhooked$a == null) {
            criteriontriggerfishingrodhooked$a = new CriterionTriggerFishingRodHooked.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerfishingrodhooked$a);
        }

        criteriontriggerfishingrodhooked$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerFishingRodHooked.b> criteriontrigger$a) {
        CriterionTriggerFishingRodHooked.a criteriontriggerfishingrodhooked$a = (CriterionTriggerFishingRodHooked.a)this.b.get(advancementdataplayer);
        if (criteriontriggerfishingrodhooked$a != null) {
            criteriontriggerfishingrodhooked$a.b(criteriontrigger$a);
            if (criteriontriggerfishingrodhooked$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerFishingRodHooked.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        CriterionConditionItem criterionconditionitem = CriterionConditionItem.a(jsonobject.get("rod"));
        CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("entity"));
        CriterionConditionItem criterionconditionitem1 = CriterionConditionItem.a(jsonobject.get("item"));
        return new CriterionTriggerFishingRodHooked.b(criterionconditionitem, criterionconditionentity, criterionconditionitem1);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack, EntityFishingHook entityfishinghook, Collection<ItemStack> collection) {
        CriterionTriggerFishingRodHooked.a criteriontriggerfishingrodhooked$a = (CriterionTriggerFishingRodHooked.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerfishingrodhooked$a != null) {
            criteriontriggerfishingrodhooked$a.a(entityplayer, itemstack, entityfishinghook, collection);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerFishingRodHooked.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerFishingRodHooked.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerFishingRodHooked.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(EntityPlayer entityplayer, ItemStack itemstack, EntityFishingHook entityfishinghook, Collection<ItemStack> collection) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerFishingRodHooked.b)criteriontrigger$a.a()).a(entityplayer, itemstack, entityfishinghook, collection)) {
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
        private final CriterionConditionEntity b;
        private final CriterionConditionItem c;

        public b(CriterionConditionItem criterionconditionitem, CriterionConditionEntity criterionconditionentity, CriterionConditionItem criterionconditionitem1) {
            super(CriterionTriggerFishingRodHooked.a);
            this.a = criterionconditionitem;
            this.b = criterionconditionentity;
            this.c = criterionconditionitem1;
        }

        public static CriterionTriggerFishingRodHooked.b a(CriterionConditionItem criterionconditionitem, CriterionConditionEntity criterionconditionentity, CriterionConditionItem criterionconditionitem1) {
            return new CriterionTriggerFishingRodHooked.b(criterionconditionitem, criterionconditionentity, criterionconditionitem1);
        }

        public boolean a(EntityPlayer entityplayer, ItemStack itemstack, EntityFishingHook entityfishinghook, Collection<ItemStack> collection) {
            if (!this.a.a(itemstack)) {
                return false;
            } else if (!this.b.a(entityplayer, entityfishinghook.hooked)) {
                return false;
            } else {
                if (this.c != CriterionConditionItem.a) {
                    boolean flag = false;
                    if (entityfishinghook.hooked instanceof EntityItem) {
                        EntityItem entityitem = (EntityItem)entityfishinghook.hooked;
                        if (this.c.a(entityitem.getItemStack())) {
                            flag = true;
                        }
                    }

                    for(ItemStack itemstack1 : collection) {
                        if (this.c.a(itemstack1)) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }

                return true;
            }
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("rod", this.a.a());
            jsonobject.add("entity", this.b.a());
            jsonobject.add("item", this.c.a());
            return jsonobject;
        }
    }
}
