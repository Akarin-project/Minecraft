package net.minecraft.server;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatHoverable {
    private final ChatHoverable.EnumHoverAction a;
    private final IChatBaseComponent b;

    public ChatHoverable(ChatHoverable.EnumHoverAction chathoverable$enumhoveraction, IChatBaseComponent ichatbasecomponent) {
        this.a = chathoverable$enumhoveraction;
        this.b = ichatbasecomponent;
    }

    public ChatHoverable.EnumHoverAction a() {
        return this.a;
    }

    public IChatBaseComponent b() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            ChatHoverable chathoverable1 = (ChatHoverable)object;
            if (this.a != chathoverable1.a) {
                return false;
            } else {
                if (this.b != null) {
                    if (!this.b.equals(chathoverable1.b)) {
                        return false;
                    }
                } else if (chathoverable1.b != null) {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "HoverEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
    }

    public int hashCode() {
        int i = this.a.hashCode();
        i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
        return i;
    }

    public static enum EnumHoverAction {
        SHOW_TEXT("show_text", true),
        SHOW_ITEM("show_item", true),
        SHOW_ENTITY("show_entity", true);

        private static final Map<String, ChatHoverable.EnumHoverAction> d = (Map)Arrays.stream(values()).collect(Collectors.toMap(ChatHoverable.EnumHoverAction::b, (chathoverable$enumhoveraction) -> {
            return chathoverable$enumhoveraction;
        }));
        private final boolean e;
        private final String f;

        private EnumHoverAction(String s1, boolean flag) {
            this.f = s1;
            this.e = flag;
        }

        public boolean a() {
            return this.e;
        }

        public String b() {
            return this.f;
        }

        public static ChatHoverable.EnumHoverAction a(String s) {
            return (ChatHoverable.EnumHoverAction)d.get(s);
        }
    }
}
