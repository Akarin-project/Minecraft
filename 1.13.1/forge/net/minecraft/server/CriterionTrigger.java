package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public interface CriterionTrigger<T extends CriterionInstance> {
    MinecraftKey a();

    void a(AdvancementDataPlayer var1, CriterionTrigger.a<T> var2);

    void b(AdvancementDataPlayer var1, CriterionTrigger.a<T> var2);

    void a(AdvancementDataPlayer var1);

    T a(JsonObject var1, JsonDeserializationContext var2);

    public static class a<T extends CriterionInstance> {
        private final T a;
        private final Advancement b;
        private final String c;

        public a(T criterioninstance, Advancement advancement, String s) {
            this.a = criterioninstance;
            this.b = advancement;
            this.c = s;
        }

        public T a() {
            return this.a;
        }

        public void a(AdvancementDataPlayer advancementdataplayer) {
            advancementdataplayer.grantCriteria(this.b, this.c);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            } else if (object != null && this.getClass() == object.getClass()) {
                CriterionTrigger.a criteriontrigger$a1 = (CriterionTrigger.a)object;
                if (!this.a.equals(criteriontrigger$a1.a)) {
                    return false;
                } else {
                    return !this.b.equals(criteriontrigger$a1.b) ? false : this.c.equals(criteriontrigger$a1.c);
                }
            } else {
                return false;
            }
        }

        public int hashCode() {
            int i = this.a.hashCode();
            i = 31 * i + this.b.hashCode();
            i = 31 * i + this.c.hashCode();
            return i;
        }
    }
}
