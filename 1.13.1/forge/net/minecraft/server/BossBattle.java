package net.minecraft.server;

import java.util.UUID;

public abstract class BossBattle {
    private final UUID h;
    public IChatBaseComponent title;
    protected float b;
    public BossBattle.BarColor color;
    public BossBattle.BarStyle style;
    protected boolean e;
    protected boolean f;
    protected boolean g;

    public BossBattle(UUID uuid, IChatBaseComponent ichatbasecomponent, BossBattle.BarColor bossbattle$barcolor, BossBattle.BarStyle bossbattle$barstyle) {
        this.h = uuid;
        this.title = ichatbasecomponent;
        this.color = bossbattle$barcolor;
        this.style = bossbattle$barstyle;
        this.b = 1.0F;
    }

    public UUID i() {
        return this.h;
    }

    public IChatBaseComponent j() {
        return this.title;
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
        this.title = ichatbasecomponent;
    }

    public float getProgress() {
        return this.b;
    }

    public void a(float fx) {
        this.b = fx;
    }

    public BossBattle.BarColor l() {
        return this.color;
    }

    public void a(BossBattle.BarColor bossbattle$barcolor) {
        this.color = bossbattle$barcolor;
    }

    public BossBattle.BarStyle m() {
        return this.style;
    }

    public void a(BossBattle.BarStyle bossbattle$barstyle) {
        this.style = bossbattle$barstyle;
    }

    public boolean n() {
        return this.e;
    }

    public BossBattle a(boolean flag) {
        this.e = flag;
        return this;
    }

    public boolean o() {
        return this.f;
    }

    public BossBattle b(boolean flag) {
        this.f = flag;
        return this;
    }

    public BossBattle c(boolean flag) {
        this.g = flag;
        return this;
    }

    public boolean p() {
        return this.g;
    }

    public static enum BarColor {
        PINK("pink", EnumChatFormat.RED),
        BLUE("blue", EnumChatFormat.BLUE),
        RED("red", EnumChatFormat.DARK_RED),
        GREEN("green", EnumChatFormat.GREEN),
        YELLOW("yellow", EnumChatFormat.YELLOW),
        PURPLE("purple", EnumChatFormat.DARK_BLUE),
        WHITE("white", EnumChatFormat.WHITE);

        private final String h;
        private final EnumChatFormat i;

        private BarColor(String s1, EnumChatFormat enumchatformat) {
            this.h = s1;
            this.i = enumchatformat;
        }

        public EnumChatFormat a() {
            return this.i;
        }

        public String b() {
            return this.h;
        }

        public static BossBattle.BarColor a(String s) {
            for(BossBattle.BarColor bossbattle$barcolor : values()) {
                if (bossbattle$barcolor.h.equals(s)) {
                    return bossbattle$barcolor;
                }
            }

            return WHITE;
        }
    }

    public static enum BarStyle {
        PROGRESS("progress"),
        NOTCHED_6("notched_6"),
        NOTCHED_10("notched_10"),
        NOTCHED_12("notched_12"),
        NOTCHED_20("notched_20");

        private final String f;

        private BarStyle(String s1) {
            this.f = s1;
        }

        public String a() {
            return this.f;
        }

        public static BossBattle.BarStyle a(String s) {
            for(BossBattle.BarStyle bossbattle$barstyle : values()) {
                if (bossbattle$barstyle.f.equals(s)) {
                    return bossbattle$barstyle;
                }
            }

            return PROGRESS;
        }
    }
}
