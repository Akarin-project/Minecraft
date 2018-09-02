package net.minecraft.server;

public class WorldType {
    public static final WorldType[] types = new WorldType[16];
    public static final WorldType NORMAL = (new WorldType(0, "default", 1)).k();
    public static final WorldType FLAT = (new WorldType(1, "flat")).a(true);
    public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
    public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).l();
    public static final WorldType CUSTOMIZED = (new WorldType(4, "customized", "normal", 0)).a(true).b(false);
    public static final WorldType g = (new WorldType(5, "buffet")).a(true);
    public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType(6, "debug_all_block_states");
    public static final WorldType NORMAL_1_1 = (new WorldType(8, "default_1_1", 0)).b(false);
    private final int j;
    private final String name;
    private final String l;
    private final int version;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;

    private WorldType(int i, String s) {
        this(i, s, s, 0);
    }

    private WorldType(int i, String s, int jx) {
        this(i, s, s, jx);
    }

    private WorldType(int i, String s, String s1, int jx) {
        this.name = s;
        this.l = s1;
        this.version = jx;
        this.n = true;
        this.j = i;
        types[i] = this;
    }

    public String name() {
        return this.name;
    }

    public String b() {
        return this.l;
    }

    public int getVersion() {
        return this.version;
    }

    public WorldType a(int i) {
        return this == NORMAL && i == 0 ? NORMAL_1_1 : this;
    }

    public WorldType a(boolean flag) {
        this.q = flag;
        return this;
    }

    private WorldType b(boolean flag) {
        this.n = flag;
        return this;
    }

    private WorldType k() {
        this.o = true;
        return this;
    }

    public boolean h() {
        return this.o;
    }

    public static WorldType getType(String s) {
        for(WorldType worldtype : types) {
            if (worldtype != null && worldtype.name.equalsIgnoreCase(s)) {
                return worldtype;
            }
        }

        return null;
    }

    public int i() {
        return this.j;
    }

    private WorldType l() {
        this.p = true;
        return this;
    }
}
