package net.minecraft.server;

public enum EnumBlockMirror {
    NONE,
    LEFT_RIGHT,
    FRONT_BACK;

    private EnumBlockMirror() {
    }

    public int a(int i, int j) {
        int k = j / 2;
        int l = i > k ? i - j : i;
        switch(this) {
        case FRONT_BACK:
            return (j - l) % j;
        case LEFT_RIGHT:
            return (k - l + j) % j;
        default:
            return i;
        }
    }

    public EnumBlockRotation a(EnumDirection enumdirection) {
        EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
        return (this != LEFT_RIGHT || enumdirection$enumaxis != EnumDirection.EnumAxis.Z) && (this != FRONT_BACK || enumdirection$enumaxis != EnumDirection.EnumAxis.X) ? EnumBlockRotation.NONE : EnumBlockRotation.CLOCKWISE_180;
    }

    public EnumDirection b(EnumDirection enumdirection) {
        if (this == FRONT_BACK && enumdirection.k() == EnumDirection.EnumAxis.X) {
            return enumdirection.opposite();
        } else {
            return this == LEFT_RIGHT && enumdirection.k() == EnumDirection.EnumAxis.Z ? enumdirection.opposite() : enumdirection;
        }
    }
}
