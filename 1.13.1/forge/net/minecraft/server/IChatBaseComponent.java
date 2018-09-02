package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface IChatBaseComponent extends Message, Iterable<IChatBaseComponent> {
    IChatBaseComponent setChatModifier(ChatModifier var1);

    ChatModifier getChatModifier();

    default IChatBaseComponent a(String s) {
        return this.addSibling(new ChatComponentText(s));
    }

    IChatBaseComponent addSibling(IChatBaseComponent var1);

    String getText();

    default String getString() {
        StringBuilder stringbuilder = new StringBuilder();
        this.c().forEach((ichatbasecomponent1) -> {
            stringbuilder.append(ichatbasecomponent1.getText());
        });
        return stringbuilder.toString();
    }

    default String a(int i) {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = this.c().iterator();

        while(iterator.hasNext()) {
            int j = i - stringbuilder.length();
            if (j <= 0) {
                break;
            }

            String s = ((IChatBaseComponent)iterator.next()).getText();
            stringbuilder.append(s.length() <= j ? s : s.substring(0, j));
        }

        return stringbuilder.toString();
    }

    default String e() {
        StringBuilder stringbuilder = new StringBuilder();
        String s = "";
        Iterator iterator = this.c().iterator();

        while(iterator.hasNext()) {
            IChatBaseComponent ichatbasecomponent1 = (IChatBaseComponent)iterator.next();
            String s1 = ichatbasecomponent1.getText();
            if (!s1.isEmpty()) {
                String s2 = ichatbasecomponent1.getChatModifier().k();
                if (!s2.equals(s)) {
                    if (!s.isEmpty()) {
                        stringbuilder.append(EnumChatFormat.RESET);
                    }

                    stringbuilder.append(s2);
                    s = s2;
                }

                stringbuilder.append(s1);
            }
        }

        if (!s.isEmpty()) {
            stringbuilder.append(EnumChatFormat.RESET);
        }

        return stringbuilder.toString();
    }

    List<IChatBaseComponent> a();

    Stream<IChatBaseComponent> c();

    default Stream<IChatBaseComponent> f() {
        return this.c().map(IChatBaseComponent::b);
    }

    default Iterator<IChatBaseComponent> iterator() {
        return this.f().iterator();
    }

    IChatBaseComponent g();

    default IChatBaseComponent h() {
        IChatBaseComponent ichatbasecomponent1 = this.g();
        ichatbasecomponent1.setChatModifier(this.getChatModifier().clone());

        for(IChatBaseComponent ichatbasecomponent2 : this.a()) {
            ichatbasecomponent1.addSibling(ichatbasecomponent2.h());
        }

        return ichatbasecomponent1;
    }

    default IChatBaseComponent a(Consumer<ChatModifier> consumer) {
        consumer.accept(this.getChatModifier());
        return this;
    }

    default IChatBaseComponent a(EnumChatFormat... aenumchatformat) {
        for(EnumChatFormat enumchatformat : aenumchatformat) {
            this.a(enumchatformat);
        }

        return this;
    }

    default IChatBaseComponent a(EnumChatFormat enumchatformat) {
        ChatModifier chatmodifier = this.getChatModifier();
        if (enumchatformat.d()) {
            chatmodifier.setColor(enumchatformat);
        }

        if (enumchatformat.isFormat()) {
            switch(enumchatformat) {
            case OBFUSCATED:
                chatmodifier.setRandom(true);
                break;
            case BOLD:
                chatmodifier.setBold(true);
                break;
            case STRIKETHROUGH:
                chatmodifier.setStrikethrough(true);
                break;
            case UNDERLINE:
                chatmodifier.setUnderline(true);
                break;
            case ITALIC:
                chatmodifier.setItalic(true);
            }
        }

        return this;
    }

    static IChatBaseComponent b(IChatBaseComponent ichatbasecomponent) {
        IChatBaseComponent ichatbasecomponent1 = ichatbasecomponent.g();
        ichatbasecomponent1.setChatModifier(ichatbasecomponent.getChatModifier().n());
        return ichatbasecomponent1;
    }

    public static class ChatSerializer implements JsonDeserializer<IChatBaseComponent>, JsonSerializer<IChatBaseComponent> {
        private static final Gson a = (Gson)SystemUtils.a(() -> {
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer());
            gsonbuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer());
            gsonbuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
            return gsonbuilder.create();
        });
        private static final Field b = (Field)SystemUtils.a(() -> {
            try {
                new JsonReader(new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("pos");
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException nosuchfieldexception) {
                throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", nosuchfieldexception);
            }
        });
        private static final Field c = (Field)SystemUtils.a(() -> {
            try {
                new JsonReader(new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("lineStart");
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException nosuchfieldexception) {
                throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", nosuchfieldexception);
            }
        });

        public ChatSerializer() {
        }

        public IChatBaseComponent a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            if (jsonelement.isJsonPrimitive()) {
                return new ChatComponentText(jsonelement.getAsString());
            } else if (!jsonelement.isJsonObject()) {
                if (jsonelement.isJsonArray()) {
                    JsonArray jsonarray1 = jsonelement.getAsJsonArray();
                    IChatBaseComponent ichatbasecomponent = null;

                    for(JsonElement jsonelement1 : jsonarray1) {
                        IChatBaseComponent ichatbasecomponent1 = this.a(jsonelement1, jsonelement1.getClass(), jsondeserializationcontext);
                        if (ichatbasecomponent == null) {
                            ichatbasecomponent = ichatbasecomponent1;
                        } else {
                            ichatbasecomponent.addSibling(ichatbasecomponent1);
                        }
                    }

                    return ichatbasecomponent;
                } else {
                    throw new JsonParseException("Don't know how to turn " + jsonelement + " into a Component");
                }
            } else {
                JsonObject jsonobject = jsonelement.getAsJsonObject();
                Object object;
                if (jsonobject.has("text")) {
                    object = new ChatComponentText(jsonobject.get("text").getAsString());
                } else if (jsonobject.has("translate")) {
                    String s = jsonobject.get("translate").getAsString();
                    if (jsonobject.has("with")) {
                        JsonArray jsonarray = jsonobject.getAsJsonArray("with");
                        Object[] aobject = new Object[jsonarray.size()];

                        for(int i = 0; i < aobject.length; ++i) {
                            aobject[i] = this.a(jsonarray.get(i), type, jsondeserializationcontext);
                            if (aobject[i] instanceof ChatComponentText) {
                                ChatComponentText chatcomponenttext = (ChatComponentText)aobject[i];
                                if (chatcomponenttext.getChatModifier().g() && chatcomponenttext.a().isEmpty()) {
                                    aobject[i] = chatcomponenttext.i();
                                }
                            }
                        }

                        object = new ChatMessage(s, aobject);
                    } else {
                        object = new ChatMessage(s, new Object[0]);
                    }
                } else if (jsonobject.has("score")) {
                    JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
                    if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                        throw new JsonParseException("A score component needs a least a name and an objective");
                    }

                    object = new ChatComponentScore(ChatDeserializer.h(jsonobject1, "name"), ChatDeserializer.h(jsonobject1, "objective"));
                    if (jsonobject1.has("value")) {
                        ((ChatComponentScore)object).b(ChatDeserializer.h(jsonobject1, "value"));
                    }
                } else if (jsonobject.has("selector")) {
                    object = new ChatComponentSelector(ChatDeserializer.h(jsonobject, "selector"));
                } else {
                    if (!jsonobject.has("keybind")) {
                        throw new JsonParseException("Don't know how to turn " + jsonelement + " into a Component");
                    }

                    object = new ChatComponentKeybind(ChatDeserializer.h(jsonobject, "keybind"));
                }

                if (jsonobject.has("extra")) {
                    JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
                    if (jsonarray2.size() <= 0) {
                        throw new JsonParseException("Unexpected empty array of components");
                    }

                    for(int j = 0; j < jsonarray2.size(); ++j) {
                        ((IChatBaseComponent)object).addSibling(this.a(jsonarray2.get(j), type, jsondeserializationcontext));
                    }
                }

                ((IChatBaseComponent)object).setChatModifier((ChatModifier)jsondeserializationcontext.deserialize(jsonelement, ChatModifier.class));
                return (IChatBaseComponent)object;
            }
        }

        private void a(ChatModifier chatmodifier, JsonObject jsonobject, JsonSerializationContext jsonserializationcontext) {
            JsonElement jsonelement = jsonserializationcontext.serialize(chatmodifier);
            if (jsonelement.isJsonObject()) {
                JsonObject jsonobject1 = (JsonObject)jsonelement;

                for(Entry entry : jsonobject1.entrySet()) {
                    jsonobject.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }

        }

        public JsonElement a(IChatBaseComponent ichatbasecomponent, Type var2, JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();
            if (!ichatbasecomponent.getChatModifier().g()) {
                this.a(ichatbasecomponent.getChatModifier(), jsonobject, jsonserializationcontext);
            }

            if (!ichatbasecomponent.a().isEmpty()) {
                JsonArray jsonarray = new JsonArray();

                for(IChatBaseComponent ichatbasecomponent1 : ichatbasecomponent.a()) {
                    jsonarray.add(this.a(ichatbasecomponent1, ichatbasecomponent1.getClass(), jsonserializationcontext));
                }

                jsonobject.add("extra", jsonarray);
            }

            if (ichatbasecomponent instanceof ChatComponentText) {
                jsonobject.addProperty("text", ((ChatComponentText)ichatbasecomponent).i());
            } else if (ichatbasecomponent instanceof ChatMessage) {
                ChatMessage chatmessage = (ChatMessage)ichatbasecomponent;
                jsonobject.addProperty("translate", chatmessage.k());
                if (chatmessage.l() != null && chatmessage.l().length > 0) {
                    JsonArray jsonarray1 = new JsonArray();

                    for(Object object : chatmessage.l()) {
                        if (object instanceof IChatBaseComponent) {
                            jsonarray1.add(this.a((IChatBaseComponent)object, object.getClass(), jsonserializationcontext));
                        } else {
                            jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                        }
                    }

                    jsonobject.add("with", jsonarray1);
                }
            } else if (ichatbasecomponent instanceof ChatComponentScore) {
                ChatComponentScore chatcomponentscore = (ChatComponentScore)ichatbasecomponent;
                JsonObject jsonobject1 = new JsonObject();
                jsonobject1.addProperty("name", chatcomponentscore.i());
                jsonobject1.addProperty("objective", chatcomponentscore.k());
                jsonobject1.addProperty("value", chatcomponentscore.getText());
                jsonobject.add("score", jsonobject1);
            } else if (ichatbasecomponent instanceof ChatComponentSelector) {
                ChatComponentSelector chatcomponentselector = (ChatComponentSelector)ichatbasecomponent;
                jsonobject.addProperty("selector", chatcomponentselector.i());
            } else {
                if (!(ichatbasecomponent instanceof ChatComponentKeybind)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + ichatbasecomponent + " as a Component");
                }

                ChatComponentKeybind chatcomponentkeybind = (ChatComponentKeybind)ichatbasecomponent;
                jsonobject.addProperty("keybind", chatcomponentkeybind.j());
            }

            return jsonobject;
        }

        public static String a(IChatBaseComponent ichatbasecomponent) {
            return a.toJson(ichatbasecomponent);
        }

        public static JsonElement b(IChatBaseComponent ichatbasecomponent) {
            return a.toJsonTree(ichatbasecomponent);
        }

        @Nullable
        public static IChatBaseComponent a(String s) {
            return (IChatBaseComponent)ChatDeserializer.a(a, s, IChatBaseComponent.class, false);
        }

        @Nullable
        public static IChatBaseComponent a(JsonElement jsonelement) {
            return (IChatBaseComponent)a.fromJson(jsonelement, IChatBaseComponent.class);
        }

        @Nullable
        public static IChatBaseComponent b(String s) {
            return (IChatBaseComponent)ChatDeserializer.a(a, s, IChatBaseComponent.class, true);
        }

        public static IChatBaseComponent a(com.mojang.brigadier.StringReader stringreader) {
            try {
                JsonReader jsonreader = new JsonReader(new StringReader(stringreader.getRemaining()));
                jsonreader.setLenient(false);
                IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)a.getAdapter(IChatBaseComponent.class).read(jsonreader);
                stringreader.setCursor(stringreader.getCursor() + a(jsonreader));
                return ichatbasecomponent;
            } catch (IOException ioexception) {
                throw new JsonParseException(ioexception);
            }
        }

        private static int a(JsonReader jsonreader) {
            try {
                return b.getInt(jsonreader) - c.getInt(jsonreader) + 1;
            } catch (IllegalAccessException illegalaccessexception) {
                throw new IllegalStateException("Couldn't read position of JsonReader", illegalaccessexception);
            }
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((IChatBaseComponent)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
