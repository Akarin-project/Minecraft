package net.minecraft.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class CriterionConditionDamage {
    public static final CriterionConditionDamage a = CriterionConditionDamage.a.a().b();
    private final CriterionConditionValue.c b;
    private final CriterionConditionValue.c c;
    private final CriterionConditionEntity d;
    private final Boolean e;
    private final CriterionConditionDamageSource f;

    public CriterionConditionDamage() {
        this.b = CriterionConditionValue.c.e;
        this.c = CriterionConditionValue.c.e;
        this.d = CriterionConditionEntity.a;
        this.e = null;
        this.f = CriterionConditionDamageSource.a;
    }

    public CriterionConditionDamage(CriterionConditionValue.c criterionconditionvalue$c, CriterionConditionValue.c criterionconditionvalue$c1, CriterionConditionEntity criterionconditionentity, @Nullable Boolean obool, CriterionConditionDamageSource criterionconditiondamagesource) {
        this.b = criterionconditionvalue$c;
        this.c = criterionconditionvalue$c1;
        this.d = criterionconditionentity;
        this.e = obool;
        this.f = criterionconditiondamagesource;
    }

    public boolean a(EntityPlayer entityplayer, DamageSource damagesource, float fx, float f1, boolean flag) {
        if (this == a) {
            return true;
        } else if (!this.b.d(fx)) {
            return false;
        } else if (!this.c.d(f1)) {
            return false;
        } else if (!this.d.a(entityplayer, damagesource.getEntity())) {
            return false;
        } else if (this.e != null && this.e != flag) {
            return false;
        } else {
            return this.f.a(entityplayer, damagesource);
        }
    }

    public static CriterionConditionDamage a(@Nullable JsonElement jsonelement) {
        if (jsonelement != null && !jsonelement.isJsonNull()) {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "damage");
            CriterionConditionValue.c criterionconditionvalue$c = CriterionConditionValue.c.a(jsonobject.get("dealt"));
            CriterionConditionValue.c criterionconditionvalue$c1 = CriterionConditionValue.c.a(jsonobject.get("taken"));
            Boolean obool = jsonobject.has("blocked") ? ChatDeserializer.j(jsonobject, "blocked") : null;
            CriterionConditionEntity criterionconditionentity = CriterionConditionEntity.a(jsonobject.get("source_entity"));
            CriterionConditionDamageSource criterionconditiondamagesource = CriterionConditionDamageSource.a(jsonobject.get("type"));
            return new CriterionConditionDamage(criterionconditionvalue$c, criterionconditionvalue$c1, criterionconditionentity, obool, criterionconditiondamagesource);
        } else {
            return a;
        }
    }

    public JsonElement a() {
        if (this == a) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("dealt", this.b.d());
            jsonobject.add("taken", this.c.d());
            jsonobject.add("source_entity", this.d.a());
            jsonobject.add("type", this.f.a());
            if (this.e != null) {
                jsonobject.addProperty("blocked", this.e);
            }

            return jsonobject;
        }
    }

    public static class a {
        private CriterionConditionValue.c a = CriterionConditionValue.c.e;
        private CriterionConditionValue.c b = CriterionConditionValue.c.e;
        private CriterionConditionEntity c = CriterionConditionEntity.a;
        private Boolean d;
        private CriterionConditionDamageSource e = CriterionConditionDamageSource.a;

        public a() {
        }

        public static CriterionConditionDamage.a a() {
            return new CriterionConditionDamage.a();
        }

        public CriterionConditionDamage.a a(Boolean obool) {
            this.d = obool;
            return this;
        }

        public CriterionConditionDamage.a a(CriterionConditionDamageSource.a criterionconditiondamagesource$a) {
            this.e = criterionconditiondamagesource$a.b();
            return this;
        }

        public CriterionConditionDamage b() {
            return new CriterionConditionDamage(this.a, this.b, this.c, this.d, this.e);
        }
    }
}
