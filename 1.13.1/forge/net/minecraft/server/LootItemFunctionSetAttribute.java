package net.minecraft.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootItemFunctionSetAttribute extends LootItemFunction {
    private static final Logger a = LogManager.getLogger();
    private final LootItemFunctionSetAttribute.a[] b;

    public LootItemFunctionSetAttribute(LootItemCondition[] alootitemcondition, LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute$a) {
        super(alootitemcondition);
        this.b = alootitemfunctionsetattribute$a;
    }

    public ItemStack a(ItemStack itemstack, Random random, LootTableInfo var3) {
        for(LootItemFunctionSetAttribute.a lootitemfunctionsetattribute$a : this.b) {
            UUID uuid = lootitemfunctionsetattribute$a.e;
            if (uuid == null) {
                uuid = UUID.randomUUID();
            }

            EnumItemSlot enumitemslot = lootitemfunctionsetattribute$a.f[random.nextInt(lootitemfunctionsetattribute$a.f.length)];
            itemstack.a(lootitemfunctionsetattribute$a.b, new AttributeModifier(uuid, lootitemfunctionsetattribute$a.a, (double)lootitemfunctionsetattribute$a.d.b(random), lootitemfunctionsetattribute$a.c), enumitemslot);
        }

        return itemstack;
    }

    static class a {
        private final String a;
        private final String b;
        private final int c;
        private final LootValueBounds d;
        @Nullable
        private final UUID e;
        private final EnumItemSlot[] f;

        private a(String s, String s1, int i, LootValueBounds lootvaluebounds, EnumItemSlot[] aenumitemslot, @Nullable UUID uuid) {
            this.a = s;
            this.b = s1;
            this.c = i;
            this.d = lootvaluebounds;
            this.e = uuid;
            this.f = aenumitemslot;
        }

        public JsonObject a(JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("name", this.a);
            jsonobject.addProperty("attribute", this.b);
            jsonobject.addProperty("operation", a(this.c));
            jsonobject.add("amount", jsonserializationcontext.serialize(this.d));
            if (this.e != null) {
                jsonobject.addProperty("id", this.e.toString());
            }

            if (this.f.length == 1) {
                jsonobject.addProperty("slot", this.f[0].d());
            } else {
                JsonArray jsonarray = new JsonArray();

                for(EnumItemSlot enumitemslot : this.f) {
                    jsonarray.add(new JsonPrimitive(enumitemslot.d()));
                }

                jsonobject.add("slot", jsonarray);
            }

            return jsonobject;
        }

        public static LootItemFunctionSetAttribute.a a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            String s = ChatDeserializer.h(jsonobject, "name");
            String s1 = ChatDeserializer.h(jsonobject, "attribute");
            int i = a(ChatDeserializer.h(jsonobject, "operation"));
            LootValueBounds lootvaluebounds = (LootValueBounds)ChatDeserializer.a(jsonobject, "amount", jsondeserializationcontext, LootValueBounds.class);
            UUID uuid = null;
            EnumItemSlot[] aenumitemslot;
            if (ChatDeserializer.a(jsonobject, "slot")) {
                aenumitemslot = new EnumItemSlot[]{EnumItemSlot.a(ChatDeserializer.h(jsonobject, "slot"))};
            } else {
                if (!ChatDeserializer.d(jsonobject, "slot")) {
                    throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
                }

                JsonArray jsonarray = ChatDeserializer.u(jsonobject, "slot");
                aenumitemslot = new EnumItemSlot[jsonarray.size()];
                int j = 0;

                for(JsonElement jsonelement : jsonarray) {
                    aenumitemslot[j++] = EnumItemSlot.a(ChatDeserializer.a(jsonelement, "slot"));
                }

                if (aenumitemslot.length == 0) {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            }

            if (jsonobject.has("id")) {
                String s2 = ChatDeserializer.h(jsonobject, "id");

                try {
                    uuid = UUID.fromString(s2);
                } catch (IllegalArgumentException var12) {
                    throw new JsonSyntaxException("Invalid attribute modifier id '" + s2 + "' (must be UUID format, with dashes)");
                }
            }

            return new LootItemFunctionSetAttribute.a(s, s1, i, lootvaluebounds, aenumitemslot, uuid);
        }

        private static String a(int i) {
            switch(i) {
            case 0:
                return "addition";
            case 1:
                return "multiply_base";
            case 2:
                return "multiply_total";
            default:
                throw new IllegalArgumentException("Unknown operation " + i);
            }
        }

        private static int a(String s) {
            if ("addition".equals(s)) {
                return 0;
            } else if ("multiply_base".equals(s)) {
                return 1;
            } else if ("multiply_total".equals(s)) {
                return 2;
            } else {
                throw new JsonSyntaxException("Unknown attribute modifier operation " + s);
            }
        }
    }

    public static class b extends LootItemFunction.a<LootItemFunctionSetAttribute> {
        public b() {
            super(new MinecraftKey("set_attributes"), LootItemFunctionSetAttribute.class);
        }

        public void a(JsonObject jsonobject, LootItemFunctionSetAttribute lootitemfunctionsetattribute, JsonSerializationContext jsonserializationcontext) {
            JsonArray jsonarray = new JsonArray();

            for(LootItemFunctionSetAttribute.a lootitemfunctionsetattribute$a : lootitemfunctionsetattribute.b) {
                jsonarray.add(lootitemfunctionsetattribute$a.a(jsonserializationcontext));
            }

            jsonobject.add("modifiers", jsonarray);
        }

        public LootItemFunctionSetAttribute a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            JsonArray jsonarray = ChatDeserializer.u(jsonobject, "modifiers");
            LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute$a = new LootItemFunctionSetAttribute.a[jsonarray.size()];
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
                alootitemfunctionsetattribute$a[i++] = LootItemFunctionSetAttribute.a.a(ChatDeserializer.m(jsonelement, "modifier"), jsondeserializationcontext);
            }

            if (alootitemfunctionsetattribute$a.length == 0) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            } else {
                return new LootItemFunctionSetAttribute(alootitemcondition, alootitemfunctionsetattribute$a);
            }
        }

        // $FF: synthetic method
        public LootItemFunction b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            return this.a(jsonobject, jsondeserializationcontext, alootitemcondition);
        }
    }
}
