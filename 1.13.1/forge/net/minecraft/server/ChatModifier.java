package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;

public class ChatModifier {
    private ChatModifier a;
    private EnumChatFormat b;
    private Boolean c;
    private Boolean d;
    private Boolean e;
    private Boolean f;
    private Boolean g;
    private ChatClickable h;
    private ChatHoverable i;
    private String j;
    private static final ChatModifier k = new ChatModifier() {
        @Nullable
        public EnumChatFormat getColor() {
            return null;
        }

        public boolean isBold() {
            return false;
        }

        public boolean isItalic() {
            return false;
        }

        public boolean isStrikethrough() {
            return false;
        }

        public boolean isUnderlined() {
            return false;
        }

        public boolean isRandom() {
            return false;
        }

        @Nullable
        public ChatClickable h() {
            return null;
        }

        @Nullable
        public ChatHoverable i() {
            return null;
        }

        @Nullable
        public String j() {
            return null;
        }

        public ChatModifier setColor(EnumChatFormat var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setBold(Boolean var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setItalic(Boolean var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setStrikethrough(Boolean var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setUnderline(Boolean var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setRandom(Boolean var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setChatClickable(ChatClickable var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setChatHoverable(ChatHoverable var1) {
            throw new UnsupportedOperationException();
        }

        public ChatModifier setChatModifier(ChatModifier var1) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return "Style.ROOT";
        }

        public ChatModifier clone() {
            return this;
        }

        public ChatModifier n() {
            return this;
        }

        public String k() {
            return "";
        }
    };

    public ChatModifier() {
    }

    @Nullable
    public EnumChatFormat getColor() {
        return this.b == null ? this.o().getColor() : this.b;
    }

    public boolean isBold() {
        return this.c == null ? this.o().isBold() : this.c;
    }

    public boolean isItalic() {
        return this.d == null ? this.o().isItalic() : this.d;
    }

    public boolean isStrikethrough() {
        return this.f == null ? this.o().isStrikethrough() : this.f;
    }

    public boolean isUnderlined() {
        return this.e == null ? this.o().isUnderlined() : this.e;
    }

    public boolean isRandom() {
        return this.g == null ? this.o().isRandom() : this.g;
    }

    public boolean g() {
        return this.c == null && this.d == null && this.f == null && this.e == null && this.g == null && this.b == null && this.h == null && this.i == null && this.j == null;
    }

    @Nullable
    public ChatClickable h() {
        return this.h == null ? this.o().h() : this.h;
    }

    @Nullable
    public ChatHoverable i() {
        return this.i == null ? this.o().i() : this.i;
    }

    @Nullable
    public String j() {
        return this.j == null ? this.o().j() : this.j;
    }

    public ChatModifier setColor(EnumChatFormat enumchatformat) {
        this.b = enumchatformat;
        return this;
    }

    public ChatModifier setBold(Boolean obool) {
        this.c = obool;
        return this;
    }

    public ChatModifier setItalic(Boolean obool) {
        this.d = obool;
        return this;
    }

    public ChatModifier setStrikethrough(Boolean obool) {
        this.f = obool;
        return this;
    }

    public ChatModifier setUnderline(Boolean obool) {
        this.e = obool;
        return this;
    }

    public ChatModifier setRandom(Boolean obool) {
        this.g = obool;
        return this;
    }

    public ChatModifier setChatClickable(ChatClickable chatclickable) {
        this.h = chatclickable;
        return this;
    }

    public ChatModifier setChatHoverable(ChatHoverable chathoverable) {
        this.i = chathoverable;
        return this;
    }

    public ChatModifier setInsertion(String s) {
        this.j = s;
        return this;
    }

    public ChatModifier setChatModifier(ChatModifier chatmodifier1) {
        this.a = chatmodifier1;
        return this;
    }

    public String k() {
        if (this.g()) {
            return this.a != null ? this.a.k() : "";
        } else {
            StringBuilder stringbuilder = new StringBuilder();
            if (this.getColor() != null) {
                stringbuilder.append(this.getColor());
            }

            if (this.isBold()) {
                stringbuilder.append(EnumChatFormat.BOLD);
            }

            if (this.isItalic()) {
                stringbuilder.append(EnumChatFormat.ITALIC);
            }

            if (this.isUnderlined()) {
                stringbuilder.append(EnumChatFormat.UNDERLINE);
            }

            if (this.isRandom()) {
                stringbuilder.append(EnumChatFormat.OBFUSCATED);
            }

            if (this.isStrikethrough()) {
                stringbuilder.append(EnumChatFormat.STRIKETHROUGH);
            }

            return stringbuilder.toString();
        }
    }

    private ChatModifier o() {
        return this.a == null ? k : this.a;
    }

    public String toString() {
        return "Style{hasParent=" + (this.a != null) + ", color=" + this.b + ", bold=" + this.c + ", italic=" + this.d + ", underlined=" + this.e + ", obfuscated=" + this.g + ", clickEvent=" + this.h() + ", hoverEvent=" + this.i() + ", insertion=" + this.j() + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ChatModifier)) {
            return false;
        } else {
            boolean flag;
            label77: {
                ChatModifier chatmodifier1 = (ChatModifier)object;
                if (this.isBold() == chatmodifier1.isBold() && this.getColor() == chatmodifier1.getColor() && this.isItalic() == chatmodifier1.isItalic() && this.isRandom() == chatmodifier1.isRandom() && this.isStrikethrough() == chatmodifier1.isStrikethrough() && this.isUnderlined() == chatmodifier1.isUnderlined()) {
                    label71: {
                        if (this.h() != null) {
                            if (!this.h().equals(chatmodifier1.h())) {
                                break label71;
                            }
                        } else if (chatmodifier1.h() != null) {
                            break label71;
                        }

                        if (this.i() != null) {
                            if (!this.i().equals(chatmodifier1.i())) {
                                break label71;
                            }
                        } else if (chatmodifier1.i() != null) {
                            break label71;
                        }

                        if (this.j() != null) {
                            if (this.j().equals(chatmodifier1.j())) {
                                break label77;
                            }
                        } else if (chatmodifier1.j() == null) {
                            break label77;
                        }
                    }
                }

                flag = false;
                return flag;
            }

            flag = true;
            return flag;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j});
    }

    public ChatModifier clone() {
        ChatModifier chatmodifier1 = new ChatModifier();
        chatmodifier1.c = this.c;
        chatmodifier1.d = this.d;
        chatmodifier1.f = this.f;
        chatmodifier1.e = this.e;
        chatmodifier1.g = this.g;
        chatmodifier1.b = this.b;
        chatmodifier1.h = this.h;
        chatmodifier1.i = this.i;
        chatmodifier1.a = this.a;
        chatmodifier1.j = this.j;
        return chatmodifier1;
    }

    public ChatModifier n() {
        ChatModifier chatmodifier1 = new ChatModifier();
        chatmodifier1.setBold(this.isBold());
        chatmodifier1.setItalic(this.isItalic());
        chatmodifier1.setStrikethrough(this.isStrikethrough());
        chatmodifier1.setUnderline(this.isUnderlined());
        chatmodifier1.setRandom(this.isRandom());
        chatmodifier1.setColor(this.getColor());
        chatmodifier1.setChatClickable(this.h());
        chatmodifier1.setChatHoverable(this.i());
        chatmodifier1.setInsertion(this.j());
        return chatmodifier1;
    }

    public static class ChatModifierSerializer implements JsonDeserializer<ChatModifier>, JsonSerializer<ChatModifier> {
        public ChatModifierSerializer() {
        }

        @Nullable
        public ChatModifier a(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            if (jsonelement.isJsonObject()) {
                ChatModifier chatmodifier = new ChatModifier();
                JsonObject jsonobject = jsonelement.getAsJsonObject();
                if (jsonobject == null) {
                    return null;
                } else {
                    if (jsonobject.has("bold")) {
                        chatmodifier.c = jsonobject.get("bold").getAsBoolean();
                    }

                    if (jsonobject.has("italic")) {
                        chatmodifier.d = jsonobject.get("italic").getAsBoolean();
                    }

                    if (jsonobject.has("underlined")) {
                        chatmodifier.e = jsonobject.get("underlined").getAsBoolean();
                    }

                    if (jsonobject.has("strikethrough")) {
                        chatmodifier.f = jsonobject.get("strikethrough").getAsBoolean();
                    }

                    if (jsonobject.has("obfuscated")) {
                        chatmodifier.g = jsonobject.get("obfuscated").getAsBoolean();
                    }

                    if (jsonobject.has("color")) {
                        chatmodifier.b = (EnumChatFormat)jsondeserializationcontext.deserialize(jsonobject.get("color"), EnumChatFormat.class);
                    }

                    if (jsonobject.has("insertion")) {
                        chatmodifier.j = jsonobject.get("insertion").getAsString();
                    }

                    if (jsonobject.has("clickEvent")) {
                        JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
                        if (jsonobject1 != null) {
                            JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
                            ChatClickable.EnumClickAction chatclickable$enumclickaction = jsonprimitive == null ? null : ChatClickable.EnumClickAction.a(jsonprimitive.getAsString());
                            JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
                            String s = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();
                            if (chatclickable$enumclickaction != null && s != null && chatclickable$enumclickaction.a()) {
                                chatmodifier.h = new ChatClickable(chatclickable$enumclickaction, s);
                            }
                        }
                    }

                    if (jsonobject.has("hoverEvent")) {
                        JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
                        if (jsonobject2 != null) {
                            JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
                            ChatHoverable.EnumHoverAction chathoverable$enumhoveraction = jsonprimitive2 == null ? null : ChatHoverable.EnumHoverAction.a(jsonprimitive2.getAsString());
                            IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)jsondeserializationcontext.deserialize(jsonobject2.get("value"), IChatBaseComponent.class);
                            if (chathoverable$enumhoveraction != null && ichatbasecomponent != null && chathoverable$enumhoveraction.a()) {
                                chatmodifier.i = new ChatHoverable(chathoverable$enumhoveraction, ichatbasecomponent);
                            }
                        }
                    }

                    return chatmodifier;
                }
            } else {
                return null;
            }
        }

        @Nullable
        public JsonElement a(ChatModifier chatmodifier, Type var2, JsonSerializationContext jsonserializationcontext) {
            if (chatmodifier.g()) {
                return null;
            } else {
                JsonObject jsonobject = new JsonObject();
                if (chatmodifier.c != null) {
                    jsonobject.addProperty("bold", chatmodifier.c);
                }

                if (chatmodifier.d != null) {
                    jsonobject.addProperty("italic", chatmodifier.d);
                }

                if (chatmodifier.e != null) {
                    jsonobject.addProperty("underlined", chatmodifier.e);
                }

                if (chatmodifier.f != null) {
                    jsonobject.addProperty("strikethrough", chatmodifier.f);
                }

                if (chatmodifier.g != null) {
                    jsonobject.addProperty("obfuscated", chatmodifier.g);
                }

                if (chatmodifier.b != null) {
                    jsonobject.add("color", jsonserializationcontext.serialize(chatmodifier.b));
                }

                if (chatmodifier.j != null) {
                    jsonobject.add("insertion", jsonserializationcontext.serialize(chatmodifier.j));
                }

                if (chatmodifier.h != null) {
                    JsonObject jsonobject1 = new JsonObject();
                    jsonobject1.addProperty("action", chatmodifier.h.a().b());
                    jsonobject1.addProperty("value", chatmodifier.h.b());
                    jsonobject.add("clickEvent", jsonobject1);
                }

                if (chatmodifier.i != null) {
                    JsonObject jsonobject2 = new JsonObject();
                    jsonobject2.addProperty("action", chatmodifier.i.a().b());
                    jsonobject2.add("value", jsonserializationcontext.serialize(chatmodifier.i.b()));
                    jsonobject.add("hoverEvent", jsonobject2);
                }

                return jsonobject;
            }
        }

        // $FF: synthetic method
        @Nullable
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((ChatModifier)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        @Nullable
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
