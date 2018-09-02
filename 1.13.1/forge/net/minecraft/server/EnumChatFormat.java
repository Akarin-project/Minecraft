package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum EnumChatFormat {
    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
    GOLD("GOLD", '6', 6, 16755200),
    GRAY("GRAY", '7', 7, 11184810),
    DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
    BLUE("BLUE", '9', 9, 5592575),
    GREEN("GREEN", 'a', 10, 5635925),
    AQUA("AQUA", 'b', 11, 5636095),
    RED("RED", 'c', 12, 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
    YELLOW("YELLOW", 'e', 14, 16777045),
    WHITE("WHITE", 'f', 15, 16777215),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, (Integer)null);

    private static final Map<String, EnumChatFormat> w = (Map)Arrays.stream(values()).collect(Collectors.toMap((enumchatformat) -> {
        return d(enumchatformat.y);
    }, (enumchatformat) -> {
        return enumchatformat;
    }));
    private static final Pattern x = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    private final String y;
    public final char character;
    private final boolean A;
    private final String B;
    private final int C;
    @Nullable
    private final Integer D;

    private static String d(String s) {
        return s.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private EnumChatFormat(String s1, char c0, int j, Integer integer) {
        this(s1, c0, false, j, integer);
    }

    private EnumChatFormat(String s1, char c0, boolean flag) {
        this(s1, c0, flag, -1, (Integer)null);
    }

    private EnumChatFormat(String s1, char c0, boolean flag, int j, Integer integer) {
        this.y = s1;
        this.character = c0;
        this.A = flag;
        this.C = j;
        this.D = integer;
        this.B = "\u00a7" + c0;
    }

    public int b() {
        return this.C;
    }

    public boolean isFormat() {
        return this.A;
    }

    public boolean d() {
        return !this.A && this != RESET;
    }

    public String g() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.B;
    }

    @Nullable
    public static String b(@Nullable String s) {
        return s == null ? null : x.matcher(s).replaceAll("");
    }

    @Nullable
    public static EnumChatFormat c(@Nullable String s) {
        return s == null ? null : (EnumChatFormat)w.get(d(s));
    }

    @Nullable
    public static EnumChatFormat a(int i) {
        if (i < 0) {
            return RESET;
        } else {
            for(EnumChatFormat enumchatformat : values()) {
                if (enumchatformat.b() == i) {
                    return enumchatformat;
                }
            }

            return null;
        }
    }

    public static Collection<String> a(boolean flag, boolean flag1) {
        ArrayList arraylist = Lists.newArrayList();

        for(EnumChatFormat enumchatformat : values()) {
            if ((!enumchatformat.d() || flag) && (!enumchatformat.isFormat() || flag1)) {
                arraylist.add(enumchatformat.g());
            }
        }

        return arraylist;
    }
}
