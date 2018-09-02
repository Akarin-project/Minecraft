package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class CriterionConditionMobEffect {
    public static final CriterionConditionMobEffect a = new CriterionConditionMobEffect(Collections.emptyMap());
    private final Map<MobEffectList, CriterionConditionMobEffect.a> b;

    public CriterionConditionMobEffect(Map<MobEffectList, CriterionConditionMobEffect.a> map) {
        this.b = map;
    }

    public static CriterionConditionMobEffect a() {
        return new CriterionConditionMobEffect(Maps.newHashMap());
    }

    public CriterionConditionMobEffect a(MobEffectList mobeffectlist) {
        this.b.put(mobeffectlist, new CriterionConditionMobEffect.a());
        return this;
    }

    public boolean a(Entity entity) {
        if (this == a) {
            return true;
        } else {
            return entity instanceof EntityLiving ? this.a(((EntityLiving)entity).co()) : false;
        }
    }

    public boolean a(EntityLiving entityliving) {
        return this == a ? true : this.a(entityliving.co());
    }

    public boolean a(Map<MobEffectList, MobEffect> map) {
        if (this == a) {
            return true;
        } else {
            for(Entry entry : this.b.entrySet()) {
                MobEffect mobeffect = (MobEffect)map.get(entry.getKey());
                if (!((CriterionConditionMobEffect.a)entry.getValue()).a(mobeffect)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static CriterionConditionMobEffect a(@Nullable JsonElement jsonelement) {
        if (jsonelement != null && !jsonelement.isJsonNull()) {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "effects");
            HashMap hashmap = Maps.newHashMap();

            for(Entry entry : jsonobject.entrySet()) {
                MinecraftKey minecraftkey = new MinecraftKey((String)entry.getKey());
                MobEffectList mobeffectlist = IRegistry.MOB_EFFECT.get(minecraftkey);
                if (mobeffectlist == null) {
                    throw new JsonSyntaxException("Unknown effect '" + minecraftkey + "'");
                }

                CriterionConditionMobEffect.a criterionconditionmobeffect$a = CriterionConditionMobEffect.a.a(ChatDeserializer.m((JsonElement)entry.getValue(), (String)entry.getKey()));
                hashmap.put(mobeffectlist, criterionconditionmobeffect$a);
            }

            return new CriterionConditionMobEffect(hashmap);
        } else {
            return a;
        }
    }

    public JsonElement b() {
        if (this == a) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();

            for(Entry entry : this.b.entrySet()) {
                jsonobject.add(IRegistry.MOB_EFFECT.getKey((MobEffectList)entry.getKey()).toString(), ((CriterionConditionMobEffect.a)entry.getValue()).a());
            }

            return jsonobject;
        }
    }

    public static class a {
        private final CriterionConditionValue.d a;
        private final CriterionConditionValue.d b;
        @Nullable
        private final Boolean c;
        @Nullable
        private final Boolean d;

        public a(CriterionConditionValue.d criterionconditionvalue$d, CriterionConditionValue.d criterionconditionvalue$d1, @Nullable Boolean obool, @Nullable Boolean obool1) {
            this.a = criterionconditionvalue$d;
            this.b = criterionconditionvalue$d1;
            this.c = obool;
            this.d = obool1;
        }

        public a() {
            this(CriterionConditionValue.d.e, CriterionConditionValue.d.e, (Boolean)null, (Boolean)null);
        }

        public boolean a(@Nullable MobEffect mobeffect) {
            if (mobeffect == null) {
                return false;
            } else if (!this.a.d(mobeffect.getAmplifier())) {
                return false;
            } else if (!this.b.d(mobeffect.getDuration())) {
                return false;
            } else if (this.c != null && this.c != mobeffect.isAmbient()) {
                return false;
            } else {
                return this.d == null || this.d == mobeffect.isShowParticles();
            }
        }

        public JsonElement a() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("amplifier", this.a.d());
            jsonobject.add("duration", this.b.d());
            jsonobject.addProperty("ambient", this.c);
            jsonobject.addProperty("visible", this.d);
            return jsonobject;
        }

        public static CriterionConditionMobEffect.a a(JsonObject jsonobject) {
            CriterionConditionValue.d criterionconditionvalue$d = CriterionConditionValue.d.a(jsonobject.get("amplifier"));
            CriterionConditionValue.d criterionconditionvalue$d1 = CriterionConditionValue.d.a(jsonobject.get("duration"));
            Boolean obool = jsonobject.has("ambient") ? ChatDeserializer.j(jsonobject, "ambient") : null;
            Boolean obool1 = jsonobject.has("visible") ? ChatDeserializer.j(jsonobject, "visible") : null;
            return new CriterionConditionMobEffect.a(criterionconditionvalue$d, criterionconditionvalue$d1, obool, obool1);
        }
    }
}
