package net.minecraft.server;

public enum EnumBlockRotation {
    NONE,
    CLOCKWISE_90,
    CLOCKWISE_180,
    COUNTERCLOCKWISE_90;

    private EnumBlockRotation() {
    }

    public EnumBlockRotation a(EnumBlockRotation enumblockrotation1) {
        switch(enumblockrotation1) {
        case CLOCKWISE_180:
            switch(this) {
            case NONE:
                return CLOCKWISE_180;
            case CLOCKWISE_90:
                return COUNTERCLOCKWISE_90;
            case CLOCKWISE_180:
                return NONE;
            case COUNTERCLOCKWISE_90:
                return CLOCKWISE_90;
            }
        case COUNTERCLOCKWISE_90:
            switch(this) {
            case NONE:
                return COUNTERCLOCKWISE_90;
            case CLOCKWISE_90:
                return NONE;
            case CLOCKWISE_180:
                return CLOCKWISE_90;
            case COUNTERCLOCKWISE_90:
                return CLOCKWISE_180;
            }
        case CLOCKWISE_90:
            switch(this) {
            case NONE:
                return CLOCKWISE_90;
            case CLOCKWISE_90:
                return CLOCKWISE_180;
            case CLOCKWISE_180:
                return COUNTERCLOCKWISE_90;
            case COUNTERCLOCKWISE_90:
                return NONE;
            }
        default:
            return this;
        }
    }

    public EnumDirection a(EnumDirection enumdirection) {
        if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
            return enumdirection;
        } else {
            switch(this) {
            case CLOCKWISE_90:
                return enumdirection.e();
            case CLOCKWISE_180:
                return enumdirection.opposite();
            case COUNTERCLOCKWISE_90:
                return enumdirection.f();
            default:
                return enumdirection;
            }
        }
    }

    public int a(int i, int j) {
        switch(this) {
        case CLOCKWISE_90:
            return (i + j / 4) % j;
        case CLOCKWISE_180:
            return (i + j / 2) % j;
        case COUNTERCLOCKWISE_90:
            return (i + j * 3 / 4) % j;
        default:
            return i;
        }
    }
}
