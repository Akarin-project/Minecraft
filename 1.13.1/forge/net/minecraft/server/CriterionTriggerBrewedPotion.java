package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class CriterionTriggerBrewedPotion implements CriterionTrigger<CriterionTriggerBrewedPotion.b> {
    private static final MinecraftKey a = new MinecraftKey("brewed_potion");
    private final Map<AdvancementDataPlayer, CriterionTriggerBrewedPotion.a> b = Maps.newHashMap();

    public CriterionTriggerBrewedPotion() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerBrewedPotion.b> criteriontrigger$a) {
        CriterionTriggerBrewedPotion.a criteriontriggerbrewedpotion$a = (CriterionTriggerBrewedPotion.a)this.b.get(advancementdataplayer);
        if (criteriontriggerbrewedpotion$a == null) {
            criteriontriggerbrewedpotion$a = new CriterionTriggerBrewedPotion.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerbrewedpotion$a);
        }

        criteriontriggerbrewedpotion$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerBrewedPotion.b> criteriontrigger$a) {
        CriterionTriggerBrewedPotion.a criteriontriggerbrewedpotion$a = (CriterionTriggerBrewedPotion.a)this.b.get(advancementdataplayer);
        if (criteriontriggerbrewedpotion$a != null) {
            criteriontriggerbrewedpotion$a.b(criteriontrigger$a);
            if (criteriontriggerbrewedpotion$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerBrewedPotion.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        PotionRegistry potionregistry = null;
        if (jsonobject.has("potion")) {
            MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "potion"));
            if (!IRegistry.POTION.c(minecraftkey)) {
                throw new JsonSyntaxException("Unknown potion '" + minecraftkey + "'");
            }

            potionregistry = IRegistry.POTION.getOrDefault(minecraftkey);
        }

        return new CriterionTriggerBrewedPotion.b(potionregistry);
    }

    public void a(EntityPlayer entityplayer, PotionRegistry potionregistry) {
        CriterionTriggerBrewedPotion.a criteriontriggerbrewedpotion$a = (CriterionTriggerBrewedPotion.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerbrewedpotion$a != null) {
            criteriontriggerbrewedpotion$a.a(potionregistry);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerBrewedPotion.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerBrewedPotion.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerBrewedPotion.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(PotionRegistry potionregistry) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerBrewedPotion.b)criteriontrigger$a.a()).a(potionregistry)) {
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
        private final PotionRegistry a;

        public b(@Nullable PotionRegistry potionregistry) {
            super(CriterionTriggerBrewedPotion.a);
            this.a = potionregistry;
        }

        public static CriterionTriggerBrewedPotion.b c() {
            return new CriterionTriggerBrewedPotion.b((PotionRegistry)null);
        }

        public boolean a(PotionRegistry potionregistry) {
            return this.a == null || this.a == potionregistry;
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            if (this.a != null) {
                jsonobject.addProperty("potion", IRegistry.POTION.getKey(this.a).toString());
            }

            return jsonobject;
        }
    }
}
