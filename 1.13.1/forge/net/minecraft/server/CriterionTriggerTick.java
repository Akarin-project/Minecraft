package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;

public class CriterionTriggerTick implements CriterionTrigger<CriterionTriggerTick.b> {
    public static final MinecraftKey a = new MinecraftKey("tick");
    private final Map<AdvancementDataPlayer, CriterionTriggerTick.a> b = Maps.newHashMap();

    public CriterionTriggerTick() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerTick.b> criteriontrigger$a) {
        CriterionTriggerTick.a criteriontriggertick$a = (CriterionTriggerTick.a)this.b.get(advancementdataplayer);
        if (criteriontriggertick$a == null) {
            criteriontriggertick$a = new CriterionTriggerTick.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggertick$a);
        }

        criteriontriggertick$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerTick.b> criteriontrigger$a) {
        CriterionTriggerTick.a criteriontriggertick$a = (CriterionTriggerTick.a)this.b.get(advancementdataplayer);
        if (criteriontriggertick$a != null) {
            criteriontriggertick$a.b(criteriontrigger$a);
            if (criteriontriggertick$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerTick.b b(JsonObject var1, JsonDeserializationContext var2) {
        return new CriterionTriggerTick.b();
    }

    public void a(EntityPlayer entityplayer) {
        CriterionTriggerTick.a criteriontriggertick$a = (CriterionTriggerTick.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggertick$a != null) {
            criteriontriggertick$a.b();
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerTick.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerTick.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerTick.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void b() {
            for(CriterionTrigger.a criteriontrigger$a : Lists.newArrayList(this.b)) {
                criteriontrigger$a.a(this.a);
            }

        }
    }

    public static class b extends CriterionInstanceAbstract {
        public b() {
            super(CriterionTriggerTick.a);
        }
    }
}
