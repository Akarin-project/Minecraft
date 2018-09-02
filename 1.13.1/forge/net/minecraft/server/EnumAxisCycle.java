package net.minecraft.server;

public enum EnumAxisCycle {
    NONE {
        public int a(int i, int j, int k, EnumDirection.EnumAxis enumdirection$enumaxis) {
            return enumdirection$enumaxis.a(i, j, k);
        }

        public EnumDirection.EnumAxis a(EnumDirection.EnumAxis enumdirection$enumaxis) {
            return enumdirection$enumaxis;
        }

        public EnumAxisCycle a() {
            return this;
        }
    },
    FORWARD {
        public int a(int i, int j, int k, EnumDirection.EnumAxis enumdirection$enumaxis) {
            return enumdirection$enumaxis.a(k, i, j);
        }

        public EnumDirection.EnumAxis a(EnumDirection.EnumAxis enumdirection$enumaxis) {
            return d[Math.floorMod(enumdirection$enumaxis.ordinal() + 1, 3)];
        }

        public EnumAxisCycle a() {
            return BACKWARD;
        }
    },
    BACKWARD {
        public int a(int i, int j, int k, EnumDirection.EnumAxis enumdirection$enumaxis) {
            return enumdirection$enumaxis.a(j, k, i);
        }

        public EnumDirection.EnumAxis a(EnumDirection.EnumAxis enumdirection$enumaxis) {
            return d[Math.floorMod(enumdirection$enumaxis.ordinal() - 1, 3)];
        }

        public EnumAxisCycle a() {
            return FORWARD;
        }
    };

    public static final EnumDirection.EnumAxis[] d = EnumDirection.EnumAxis.values();
    public static final EnumAxisCycle[] e = values();

    private EnumAxisCycle() {
    }

    public abstract int a(int var1, int var2, int var3, EnumDirection.EnumAxis var4);

    public abstract EnumDirection.EnumAxis a(EnumDirection.EnumAxis var1);

    public abstract EnumAxisCycle a();

    public static EnumAxisCycle a(EnumDirection.EnumAxis enumdirection$enumaxis, EnumDirection.EnumAxis enumdirection$enumaxis1) {
        return e[Math.floorMod(enumdirection$enumaxis1.ordinal() - enumdirection$enumaxis.ordinal(), 3)];
    }
}
