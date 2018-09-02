package net.minecraft.server;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatClickable {
    private final ChatClickable.EnumClickAction a;
    private final String b;

    public ChatClickable(ChatClickable.EnumClickAction chatclickable$enumclickaction, String s) {
        this.a = chatclickable$enumclickaction;
        this.b = s;
    }

    public ChatClickable.EnumClickAction a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            ChatClickable chatclickable1 = (ChatClickable)object;
            if (this.a != chatclickable1.a) {
                return false;
            } else {
                if (this.b != null) {
                    if (!this.b.equals(chatclickable1.b)) {
                        return false;
                    }
                } else if (chatclickable1.b != null) {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
    }

    public int hashCode() {
        int i = this.a.hashCode();
        i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
        return i;
    }

    public static enum EnumClickAction {
        OPEN_URL("open_url", true),
        OPEN_FILE("open_file", false),
        RUN_COMMAND("run_command", true),
        SUGGEST_COMMAND("suggest_command", true),
        CHANGE_PAGE("change_page", true);

        private static final Map<String, ChatClickable.EnumClickAction> f = (Map)Arrays.stream(values()).collect(Collectors.toMap(ChatClickable.EnumClickAction::b, (chatclickable$enumclickaction) -> {
            return chatclickable$enumclickaction;
        }));
        private final boolean g;
        private final String h;

        private EnumClickAction(String s1, boolean flag) {
            this.h = s1;
            this.g = flag;
        }

        public boolean a() {
            return this.g;
        }

        public String b() {
            return this.h;
        }

        public static ChatClickable.EnumClickAction a(String s) {
            return (ChatClickable.EnumClickAction)f.get(s);
        }
    }
}
