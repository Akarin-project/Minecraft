package net.minecraft.server;

public enum TickListPriority {
    EXTREMELY_HIGH(-3),
    VERY_HIGH(-2),
    HIGH(-1),
    NORMAL(0),
    LOW(1),
    VERY_LOW(2),
    EXTREMELY_LOW(3);

    private final int h;

    private TickListPriority(int j) {
        this.h = j;
    }

    public static TickListPriority a(int ix) {
        for(TickListPriority ticklistpriority : values()) {
            if (ticklistpriority.h == ix) {
                return ticklistpriority;
            }
        }

        if (ix < EXTREMELY_HIGH.h) {
            return EXTREMELY_HIGH;
        } else {
            return EXTREMELY_LOW;
        }
    }

    public int a() {
        return this.h;
    }
}
