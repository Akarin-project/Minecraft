package net.minecraft.server;

import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum EnumDirection implements INamable {
    DOWN(0, 1, -1, "down", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Y, new BaseBlockPosition(0, -1, 0)),
    UP(1, 0, -1, "up", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Y, new BaseBlockPosition(0, 1, 0)),
    NORTH(2, 3, 2, "north", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Z, new BaseBlockPosition(0, 0, -1)),
    SOUTH(3, 2, 0, "south", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Z, new BaseBlockPosition(0, 0, 1)),
    WEST(4, 5, 1, "west", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.X, new BaseBlockPosition(-1, 0, 0)),
    EAST(5, 4, 3, "east", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.X, new BaseBlockPosition(1, 0, 0));

    private final int g;
    private final int h;
    private final int i;
    private final String j;
    private final EnumDirection.EnumAxis k;
    private final EnumDirection.EnumAxisDirection l;
    private final BaseBlockPosition m;
    private static final EnumDirection[] n = values();
    private static final Map<String, EnumDirection> o = (Map)Arrays.stream(n).collect(Collectors.toMap(EnumDirection::j, (enumdirection) -> {
        return enumdirection;
    }));
    private static final EnumDirection[] p = (EnumDirection[])Arrays.stream(n).sorted(Comparator.comparingInt((enumdirection) -> {
        return enumdirection.g;
    })).toArray((ix) -> {
        return new EnumDirection[ix];
    });
    private static final EnumDirection[] q = (EnumDirection[])Arrays.stream(n).filter((enumdirection) -> {
        return enumdirection.k().c();
    }).sorted(Comparator.comparingInt((enumdirection) -> {
        return enumdirection.i;
    })).toArray((ix) -> {
        return new EnumDirection[ix];
    });

    private EnumDirection(int jx, int kx, int lx, String s1, EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection, EnumDirection.EnumAxis enumdirection$enumaxis, BaseBlockPosition baseblockposition) {
        this.g = jx;
        this.i = lx;
        this.h = kx;
        this.j = s1;
        this.k = enumdirection$enumaxis;
        this.l = enumdirection$enumaxisdirection;
        this.m = baseblockposition;
    }

    public static EnumDirection[] a(Entity entity) {
        float f = entity.g(1.0F) * ((float)Math.PI / 180F);
        float f1 = -entity.h(1.0F) * ((float)Math.PI / 180F);
        float f2 = MathHelper.sin(f);
        float f3 = MathHelper.cos(f);
        float f4 = MathHelper.sin(f1);
        float f5 = MathHelper.cos(f1);
        boolean flag = f4 > 0.0F;
        boolean flag1 = f2 < 0.0F;
        boolean flag2 = f5 > 0.0F;
        float f6 = flag ? f4 : -f4;
        float f7 = flag1 ? -f2 : f2;
        float f8 = flag2 ? f5 : -f5;
        float f9 = f6 * f3;
        float f10 = f8 * f3;
        EnumDirection enumdirection = flag ? EAST : WEST;
        EnumDirection enumdirection1 = flag1 ? UP : DOWN;
        EnumDirection enumdirection2 = flag2 ? SOUTH : NORTH;
        if (f6 > f8) {
            if (f7 > f9) {
                return a(enumdirection1, enumdirection, enumdirection2);
            } else {
                return f10 > f7 ? a(enumdirection, enumdirection2, enumdirection1) : a(enumdirection, enumdirection1, enumdirection2);
            }
        } else if (f7 > f10) {
            return a(enumdirection1, enumdirection2, enumdirection);
        } else {
            return f9 > f7 ? a(enumdirection2, enumdirection, enumdirection1) : a(enumdirection2, enumdirection1, enumdirection);
        }
    }

    private static EnumDirection[] a(EnumDirection enumdirection, EnumDirection enumdirection1, EnumDirection enumdirection2) {
        return new EnumDirection[]{enumdirection, enumdirection1, enumdirection2, enumdirection2.opposite(), enumdirection1.opposite(), enumdirection.opposite()};
    }

    public int a() {
        return this.g;
    }

    public int get2DRotationValue() {
        return this.i;
    }

    public EnumDirection.EnumAxisDirection c() {
        return this.l;
    }

    public EnumDirection opposite() {
        return fromType1(this.h);
    }

    public EnumDirection e() {
        switch(this) {
        case NORTH:
            return EAST;
        case EAST:
            return SOUTH;
        case SOUTH:
            return WEST;
        case WEST:
            return NORTH;
        default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public EnumDirection f() {
        switch(this) {
        case NORTH:
            return WEST;
        case EAST:
            return NORTH;
        case SOUTH:
            return EAST;
        case WEST:
            return SOUTH;
        default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getAdjacentX() {
        return this.k == EnumDirection.EnumAxis.X ? this.l.a() : 0;
    }

    public int getAdjacentY() {
        return this.k == EnumDirection.EnumAxis.Y ? this.l.a() : 0;
    }

    public int getAdjacentZ() {
        return this.k == EnumDirection.EnumAxis.Z ? this.l.a() : 0;
    }

    public String j() {
        return this.j;
    }

    public EnumDirection.EnumAxis k() {
        return this.k;
    }

    public static EnumDirection fromType1(int ix) {
        return p[MathHelper.a(ix % p.length)];
    }

    public static EnumDirection fromType2(int ix) {
        return q[MathHelper.a(ix % q.length)];
    }

    public static EnumDirection fromAngle(double d0) {
        return fromType2(MathHelper.floor(d0 / 90.0D + 0.5D) & 3);
    }

    public static EnumDirection a(EnumDirection.EnumAxis enumdirection$enumaxis, EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection) {
        switch(enumdirection$enumaxis) {
        case X:
            return enumdirection$enumaxisdirection == EnumDirection.EnumAxisDirection.POSITIVE ? EAST : WEST;
        case Y:
            return enumdirection$enumaxisdirection == EnumDirection.EnumAxisDirection.POSITIVE ? UP : DOWN;
        case Z:
        default:
            return enumdirection$enumaxisdirection == EnumDirection.EnumAxisDirection.POSITIVE ? SOUTH : NORTH;
        }
    }

    public float l() {
        return (float)((this.i & 3) * 90);
    }

    public static EnumDirection a(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public static EnumDirection a(double d0, double d1, double d2) {
        return a((float)d0, (float)d1, (float)d2);
    }

    public static EnumDirection a(float f, float f1, float f2) {
        EnumDirection enumdirection = NORTH;
        float f3 = Float.MIN_VALUE;

        for(EnumDirection enumdirection1 : n) {
            float f4 = f * (float)enumdirection1.m.getX() + f1 * (float)enumdirection1.m.getY() + f2 * (float)enumdirection1.m.getZ();
            if (f4 > f3) {
                f3 = f4;
                enumdirection = enumdirection1;
            }
        }

        return enumdirection;
    }

    public String toString() {
        return this.j;
    }

    public String getName() {
        return this.j;
    }

    public static EnumDirection a(EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection, EnumDirection.EnumAxis enumdirection$enumaxis) {
        for(EnumDirection enumdirection : values()) {
            if (enumdirection.c() == enumdirection$enumaxisdirection && enumdirection.k() == enumdirection$enumaxis) {
                return enumdirection;
            }
        }

        throw new IllegalArgumentException("No such direction: " + enumdirection$enumaxisdirection + " " + enumdirection$enumaxis);
    }

    public static enum EnumAxis implements Predicate<EnumDirection>, INamable {
        X("x") {
            public int a(int i, int var2, int var3) {
                return i;
            }

            public double a(double d0, double var3, double var5) {
                return d0;
            }

            // $FF: synthetic method
            public boolean test(@Nullable Object object) {
                return super.a((EnumDirection)object);
            }
        },
        Y("y") {
            public int a(int var1, int i, int var3) {
                return i;
            }

            public double a(double var1, double d0, double var5) {
                return d0;
            }

            // $FF: synthetic method
            public boolean test(@Nullable Object object) {
                return super.a((EnumDirection)object);
            }
        },
        Z("z") {
            public int a(int var1, int var2, int i) {
                return i;
            }

            public double a(double var1, double var3, double d0) {
                return d0;
            }

            // $FF: synthetic method
            public boolean test(@Nullable Object object) {
                return super.a((EnumDirection)object);
            }
        };

        private static final Map<String, EnumDirection.EnumAxis> d = (Map)Arrays.stream(values()).collect(Collectors.toMap(EnumDirection.EnumAxis::a, (enumdirection$enumaxis) -> {
            return enumdirection$enumaxis;
        }));
        private final String e;

        private EnumAxis(String s1) {
            this.e = s1;
        }

        public String a() {
            return this.e;
        }

        public boolean b() {
            return this == Y;
        }

        public boolean c() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.e;
        }

        public boolean a(@Nullable EnumDirection enumdirection) {
            return enumdirection != null && enumdirection.k() == this;
        }

        public EnumDirection.EnumDirectionLimit d() {
            switch(this) {
            case X:
            case Z:
                return EnumDirection.EnumDirectionLimit.HORIZONTAL;
            case Y:
                return EnumDirection.EnumDirectionLimit.VERTICAL;
            default:
                throw new Error("Someone's been tampering with the universe!");
            }
        }

        public String getName() {
            return this.e;
        }

        public abstract int a(int var1, int var2, int var3);

        public abstract double a(double var1, double var3, double var5);

        // $FF: synthetic method
        public boolean test(@Nullable Object object) {
            return this.a((EnumDirection)object);
        }
    }

    public static enum EnumAxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int c;
        private final String d;

        private EnumAxisDirection(int j, String s1) {
            this.c = j;
            this.d = s1;
        }

        public int a() {
            return this.c;
        }

        public String toString() {
            return this.d;
        }
    }

    public static enum EnumDirectionLimit implements Iterable<EnumDirection>, Predicate<EnumDirection> {
        HORIZONTAL(new EnumDirection[]{EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST}, new EnumDirection.EnumAxis[]{EnumDirection.EnumAxis.X, EnumDirection.EnumAxis.Z}),
        VERTICAL(new EnumDirection[]{EnumDirection.UP, EnumDirection.DOWN}, new EnumDirection.EnumAxis[]{EnumDirection.EnumAxis.Y});

        private final EnumDirection[] c;
        private final EnumDirection.EnumAxis[] d;

        private EnumDirectionLimit(EnumDirection[] aenumdirection, EnumDirection.EnumAxis[] aenumdirection$enumaxis) {
            this.c = aenumdirection;
            this.d = aenumdirection$enumaxis;
        }

        public EnumDirection a(Random random) {
            return this.c[random.nextInt(this.c.length)];
        }

        public boolean a(@Nullable EnumDirection enumdirection) {
            return enumdirection != null && enumdirection.k().d() == this;
        }

        public Iterator<EnumDirection> iterator() {
            return Iterators.forArray(this.c);
        }

        // $FF: synthetic method
        public boolean test(@Nullable Object object) {
            return this.a((EnumDirection)object);
        }
    }
}
