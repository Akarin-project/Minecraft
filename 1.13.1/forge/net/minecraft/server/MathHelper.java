package net.minecraft.server;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;

public class MathHelper {
    public static final float a = c(2.0F);
    private static final float[] b = (float[])SystemUtils.a(new float[65536], (afloat) -> {
        for(int i = 0; i < afloat.length; ++i) {
            afloat[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }

    });
    private static final Random c = new Random();
    private static final int[] d = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double e = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] f = new double[257];
    private static final double[] g = new double[257];

    public static float sin(float fx) {
        return b[(int)(fx * 10430.378F) & '\uffff'];
    }

    public static float cos(float fx) {
        return b[(int)(fx * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static float c(float fx) {
        return (float)Math.sqrt((double)fx);
    }

    public static float sqrt(double d0) {
        return (float)Math.sqrt(d0);
    }

    public static int d(float fx) {
        int i = (int)fx;
        return fx < (float)i ? i - 1 : i;
    }

    public static int floor(double d0) {
        int i = (int)d0;
        return d0 < (double)i ? i - 1 : i;
    }

    public static long d(double d0) {
        long i = (long)d0;
        return d0 < (double)i ? i - 1L : i;
    }

    public static float e(float fx) {
        return fx >= 0.0F ? fx : -fx;
    }

    public static int a(int i) {
        return i >= 0 ? i : -i;
    }

    public static int f(float fx) {
        int i = (int)fx;
        return fx > (float)i ? i + 1 : i;
    }

    public static int f(double d0) {
        int i = (int)d0;
        return d0 > (double)i ? i + 1 : i;
    }

    public static int clamp(int i, int j, int k) {
        if (i < j) {
            return j;
        } else {
            return i > k ? k : i;
        }
    }

    public static float a(float fx, float f1, float f2) {
        if (fx < f1) {
            return f1;
        } else {
            return fx > f2 ? f2 : fx;
        }
    }

    public static double a(double d0, double d1, double d2) {
        if (d0 < d1) {
            return d1;
        } else {
            return d0 > d2 ? d2 : d0;
        }
    }

    public static double b(double d0, double d1, double d2) {
        if (d2 < 0.0D) {
            return d0;
        } else {
            return d2 > 1.0D ? d1 : d0 + (d1 - d0) * d2;
        }
    }

    public static double a(double d0, double d1) {
        if (d0 < 0.0D) {
            d0 = -d0;
        }

        if (d1 < 0.0D) {
            d1 = -d1;
        }

        return d0 > d1 ? d0 : d1;
    }

    public static int a(int i, int j) {
        return Math.floorDiv(i, j);
    }

    public static int nextInt(Random random, int i, int j) {
        return i >= j ? i : random.nextInt(j - i + 1) + i;
    }

    public static float a(Random random, float fx, float f1) {
        return fx >= f1 ? fx : random.nextFloat() * (f1 - fx) + fx;
    }

    public static double a(Random random, double d0, double d1) {
        return d0 >= d1 ? d0 : random.nextDouble() * (d1 - d0) + d0;
    }

    public static double a(long[] along) {
        long i = 0L;

        for(long j : along) {
            i += j;
        }

        return (double)i / (double)along.length;
    }

    public static int b(int i, int j) {
        return Math.floorMod(i, j);
    }

    public static float g(float fx) {
        fx = fx % 360.0F;
        if (fx >= 180.0F) {
            fx -= 360.0F;
        }

        if (fx < -180.0F) {
            fx += 360.0F;
        }

        return fx;
    }

    public static double g(double d0) {
        d0 = d0 % 360.0D;
        if (d0 >= 180.0D) {
            d0 -= 360.0D;
        }

        if (d0 < -180.0D) {
            d0 += 360.0D;
        }

        return d0;
    }

    public static float c(float fx, float f1) {
        float f2 = g(fx - f1);
        return f2 < 180.0F ? f2 : f2 - 360.0F;
    }

    public static float d(float fx, float f1) {
        float f2 = g(fx - f1);
        return f2 < 180.0F ? e(f2) : e(f2 - 360.0F);
    }

    public static float b(float fx, float f1, float f2) {
        f2 = e(f2);
        return fx < f1 ? a(fx + f2, fx, f1) : a(fx - f2, f1, fx);
    }

    public static float c(float fx, float f1, float f2) {
        float f3 = c(f1, fx);
        return b(fx, fx + f3, f2);
    }

    public static int c(int i) {
        int j = i - 1;
        j = j | j >> 1;
        j = j | j >> 2;
        j = j | j >> 4;
        j = j | j >> 8;
        j = j | j >> 16;
        return j + 1;
    }

    private static boolean g(int i) {
        return i != 0 && (i & i - 1) == 0;
    }

    public static int d(int i) {
        i = g(i) ? i : c(i);
        return d[(int)((long)i * 125613361L >> 27) & 31];
    }

    public static int e(int i) {
        return d(i) - (g(i) ? 0 : 1);
    }

    public static int c(int i, int j) {
        if (j == 0) {
            return 0;
        } else if (i == 0) {
            return j;
        } else {
            if (i < 0) {
                j *= -1;
            }

            int k = i % j;
            return k == 0 ? i : i + j - k;
        }
    }

    public static long c(int i, int j, int k) {
        long l = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    public static UUID a(Random random) {
        long i = random.nextLong() & -61441L | 16384L;
        long j = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(i, j);
    }

    public static UUID a() {
        return a(c);
    }

    public static double c(double d0, double d1, double d2) {
        return (d0 - d1) / (d2 - d1);
    }

    public static double c(double d0, double d1) {
        double d2 = d1 * d1 + d0 * d0;
        if (Double.isNaN(d2)) {
            return Double.NaN;
        } else {
            boolean flag = d0 < 0.0D;
            if (flag) {
                d0 = -d0;
            }

            boolean flag1 = d1 < 0.0D;
            if (flag1) {
                d1 = -d1;
            }

            boolean flag2 = d0 > d1;
            if (flag2) {
                double d3 = d1;
                d1 = d0;
                d0 = d3;
            }

            double d11 = i(d2);
            d1 = d1 * d11;
            d0 = d0 * d11;
            double d4 = e + d0;
            int i = (int)Double.doubleToRawLongBits(d4);
            double d5 = f[i];
            double d6 = g[i];
            double d7 = d4 - e;
            double d8 = d0 * d6 - d1 * d7;
            double d9 = (6.0D + d8 * d8) * d8 * 0.16666666666666666D;
            double d10 = d5 + d9;
            if (flag2) {
                d10 = (Math.PI / 2D) - d10;
            }

            if (flag1) {
                d10 = Math.PI - d10;
            }

            if (flag) {
                d10 = -d10;
            }

            return d10;
        }
    }

    public static double i(double d0) {
        double d1 = 0.5D * d0;
        long i = Double.doubleToRawLongBits(d0);
        i = 6910469410427058090L - (i >> 1);
        d0 = Double.longBitsToDouble(i);
        d0 = d0 * (1.5D - d1 * d0 * d0);
        return d0;
    }

    public static int f(int i) {
        i = i ^ i >>> 16;
        i = i * -2048144789;
        i = i ^ i >>> 13;
        i = i * -1028477387;
        i = i ^ i >>> 16;
        return i;
    }

    public static int a(int i, int j, IntPredicate intpredicate) {
        int k = j - i;

        while(k > 0) {
            int l = k / 2;
            int i1 = i + l;
            if (intpredicate.test(i1)) {
                k = l;
            } else {
                i = i1 + 1;
                k -= l + 1;
            }
        }

        return i;
    }

    static {
        for(int i = 0; i < 257; ++i) {
            double d0 = (double)i / 256.0D;
            double d1 = Math.asin(d0);
            g[i] = Math.cos(d1);
            f[i] = d1;
        }

    }
}
