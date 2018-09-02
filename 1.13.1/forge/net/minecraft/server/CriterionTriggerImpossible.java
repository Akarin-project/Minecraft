package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class CriterionTriggerImpossible implements CriterionTrigger<CriterionTriggerImpossible.a> {
    private static final MinecraftKey a = new MinecraftKey("impossible");

    public CriterionTriggerImpossible() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer var1, CriterionTrigger.a<CriterionTriggerImpossible.a> var2) {
    }

    public void b(AdvancementDataPlayer var1, CriterionTrigger.a<CriterionTriggerImpossible.a> var2) {
    }

    public void a(AdvancementDataPlayer var1) {
    }

    public CriterionTriggerImpossible.a b(JsonObject var1, JsonDeserializationContext var2) {
        return new CriterionTriggerImpossible.a();
    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    public static class a extends CriterionInstanceAbstract {
        public a() {
            super(CriterionTriggerImpossible.a);
        }
    }
}
