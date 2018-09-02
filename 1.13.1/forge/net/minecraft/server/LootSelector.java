package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

public class LootSelector {
    private final LootSelectorEntry[] a;
    private final LootItemCondition[] b;
    private final LootValueBounds c;
    private final LootValueBounds d;

    public LootSelector(LootSelectorEntry[] alootselectorentry, LootItemCondition[] alootitemcondition, LootValueBounds lootvaluebounds, LootValueBounds lootvaluebounds1) {
        this.a = alootselectorentry;
        this.b = alootitemcondition;
        this.c = lootvaluebounds;
        this.d = lootvaluebounds1;
    }

    protected void a(Collection<ItemStack> collection, Random random, LootTableInfo loottableinfo) {
        ArrayList arraylist = Lists.newArrayList();
        int i = 0;

        for(LootSelectorEntry lootselectorentry : this.a) {
            if (LootItemConditions.a(lootselectorentry.e, random, loottableinfo)) {
                int j = lootselectorentry.a(loottableinfo.g());
                if (j > 0) {
                    arraylist.add(lootselectorentry);
                    i += j;
                }
            }
        }

        if (i != 0 && !arraylist.isEmpty()) {
            int k = random.nextInt(i);

            for(LootSelectorEntry lootselectorentry1 : arraylist) {
                k -= lootselectorentry1.a(loottableinfo.g());
                if (k < 0) {
                    lootselectorentry1.a(collection, random, loottableinfo);
                    return;
                }
            }

        }
    }

    public void b(Collection<ItemStack> collection, Random random, LootTableInfo loottableinfo) {
        if (LootItemConditions.a(this.b, random, loottableinfo)) {
            int i = this.c.a(random) + MathHelper.d(this.d.b(random) * loottableinfo.g());

            for(int j = 0; j < i; ++j) {
                this.a(collection, random, loottableinfo);
            }

        }
    }

    public static class a implements JsonDeserializer<LootSelector>, JsonSerializer<LootSelector> {
        public a() {
        }

        public LootSelector a(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "loot pool");
            LootSelectorEntry[] alootselectorentry = (LootSelectorEntry[])ChatDeserializer.a(jsonobject, "entries", jsondeserializationcontext, LootSelectorEntry[].class);
            LootItemCondition[] alootitemcondition = (LootItemCondition[])ChatDeserializer.a(jsonobject, "conditions", new LootItemCondition[0], jsondeserializationcontext, LootItemCondition[].class);
            LootValueBounds lootvaluebounds = (LootValueBounds)ChatDeserializer.a(jsonobject, "rolls", jsondeserializationcontext, LootValueBounds.class);
            LootValueBounds lootvaluebounds1 = (LootValueBounds)ChatDeserializer.a(jsonobject, "bonus_rolls", new LootValueBounds(0.0F, 0.0F), jsondeserializationcontext, LootValueBounds.class);
            return new LootSelector(alootselectorentry, alootitemcondition, lootvaluebounds, lootvaluebounds1);
        }

        public JsonElement a(LootSelector lootselector, Type var2, JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entries", jsonserializationcontext.serialize(lootselector.a));
            jsonobject.add("rolls", jsonserializationcontext.serialize(lootselector.c));
            if (lootselector.d.a() != 0.0F && lootselector.d.b() != 0.0F) {
                jsonobject.add("bonus_rolls", jsonserializationcontext.serialize(lootselector.d));
            }

            if (!ArrayUtils.isEmpty(lootselector.b)) {
                jsonobject.add("conditions", jsonserializationcontext.serialize(lootselector.b));
            }

            return jsonobject;
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((LootSelector)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
