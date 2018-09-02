package net.minecraft.server;

public enum EnumGamemode {
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    private final int f;
    private final String g;

    private EnumGamemode(int j, String s1) {
        this.f = j;
        this.g = s1;
    }

    public int getId() {
        return this.f;
    }

    public String b() {
        return this.g;
    }

    public IChatBaseComponent c() {
        return new ChatMessage("gameMode." + this.g, new Object[0]);
    }

    public void a(PlayerAbilities playerabilities) {
        if (this == CREATIVE) {
            playerabilities.canFly = true;
            playerabilities.canInstantlyBuild = true;
            playerabilities.isInvulnerable = true;
        } else if (this == SPECTATOR) {
            playerabilities.canFly = true;
            playerabilities.canInstantlyBuild = false;
            playerabilities.isInvulnerable = true;
            playerabilities.isFlying = true;
        } else {
            playerabilities.canFly = false;
            playerabilities.canInstantlyBuild = false;
            playerabilities.isInvulnerable = false;
            playerabilities.isFlying = false;
        }

        playerabilities.mayBuild = !this.d();
    }

    public boolean d() {
        return this == ADVENTURE || this == SPECTATOR;
    }

    public boolean isCreative() {
        return this == CREATIVE;
    }

    public boolean f() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public static EnumGamemode getById(int i) {
        return a(i, SURVIVAL);
    }

    public static EnumGamemode a(int i, EnumGamemode enumgamemode) {
        for(EnumGamemode enumgamemode1 : values()) {
            if (enumgamemode1.f == i) {
                return enumgamemode1;
            }
        }

        return enumgamemode;
    }

    public static EnumGamemode a(String s, EnumGamemode enumgamemode) {
        for(EnumGamemode enumgamemode1 : values()) {
            if (enumgamemode1.g.equals(s)) {
                return enumgamemode1;
            }
        }

        return enumgamemode;
    }
}
