package net.minecraft.server;

import java.util.Random;

public class NoiseGenerator3Handler {
    private static final int[][] e = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}};
    public static final double a = Math.sqrt(3.0D);
    private final int[] f = new int[512];
    public double b;
    public double c;
    public double d;
    private static final double g = 0.5D * (a - 1.0D);
    private static final double h = (3.0D - a) / 6.0D;

    public NoiseGenerator3Handler(Random random) {
        this.b = random.nextDouble() * 256.0D;
        this.c = random.nextDouble() * 256.0D;
        this.d = random.nextDouble() * 256.0D;

        for(int i = 0; i < 256; this.f[i] = i++) {
            ;
        }

        for(int l = 0; l < 256; ++l) {
            int j = random.nextInt(256 - l) + l;
            int k = this.f[l];
            this.f[l] = this.f[j];
            this.f[j] = k;
            this.f[l + 256] = this.f[l];
        }

    }

    private static int a(double d0) {
        return d0 > 0.0D ? (int)d0 : (int)d0 - 1;
    }

    private static double a(int[] aint, double d0, double d1) {
        return (double)aint[0] * d0 + (double)aint[1] * d1;
    }

    public double a(double d0, double d1) {
        double d5 = 0.5D * (a - 1.0D);
        double d6 = (d0 + d1) * d5;
        int i = a(d0 + d6);
        int j = a(d1 + d6);
        double d7 = (3.0D - a) / 6.0D;
        double d8 = (double)(i + j) * d7;
        double d9 = (double)i - d8;
        double d10 = (double)j - d8;
        double d11 = d0 - d9;
        double d12 = d1 - d10;
        byte b0;
        byte b1;
        if (d11 > d12) {
            b0 = 1;
            b1 = 0;
        } else {
            b0 = 0;
            b1 = 1;
        }

        double d13 = d11 - (double)b0 + d7;
        double d14 = d12 - (double)b1 + d7;
        double d15 = d11 - 1.0D + 2.0D * d7;
        double d16 = d12 - 1.0D + 2.0D * d7;
        int k = i & 255;
        int l = j & 255;
        int i1 = this.f[k + this.f[l]] % 12;
        int j1 = this.f[k + b0 + this.f[l + b1]] % 12;
        int k1 = this.f[k + 1 + this.f[l + 1]] % 12;
        double d17 = 0.5D - d11 * d11 - d12 * d12;
        double d2;
        if (d17 < 0.0D) {
            d2 = 0.0D;
        } else {
            d17 = d17 * d17;
            d2 = d17 * d17 * a(e[i1], d11, d12);
        }

        double d18 = 0.5D - d13 * d13 - d14 * d14;
        double d3;
        if (d18 < 0.0D) {
            d3 = 0.0D;
        } else {
            d18 = d18 * d18;
            d3 = d18 * d18 * a(e[j1], d13, d14);
        }

        double d19 = 0.5D - d15 * d15 - d16 * d16;
        double d4;
        if (d19 < 0.0D) {
            d4 = 0.0D;
        } else {
            d19 = d19 * d19;
            d4 = d19 * d19 * a(e[k1], d15, d16);
        }

        return 70.0D * (d2 + d3 + d4);
    }

    public void a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        int k = 0;

        for(int l = 0; l < j; ++l) {
            double d5 = (d1 + (double)l) * d3 + this.c;

            for(int i1 = 0; i1 < i; ++i1) {
                double d6 = (d0 + (double)i1) * d2 + this.b;
                double d10 = (d6 + d5) * g;
                int j1 = a(d6 + d10);
                int k1 = a(d5 + d10);
                double d11 = (double)(j1 + k1) * h;
                double d12 = (double)j1 - d11;
                double d13 = (double)k1 - d11;
                double d14 = d6 - d12;
                double d15 = d5 - d13;
                byte b0;
                byte b1;
                if (d14 > d15) {
                    b0 = 1;
                    b1 = 0;
                } else {
                    b0 = 0;
                    b1 = 1;
                }

                double d16 = d14 - (double)b0 + h;
                double d17 = d15 - (double)b1 + h;
                double d18 = d14 - 1.0D + 2.0D * h;
                double d19 = d15 - 1.0D + 2.0D * h;
                int l1 = j1 & 255;
                int i2 = k1 & 255;
                int j2 = this.f[l1 + this.f[i2]] % 12;
                int k2 = this.f[l1 + b0 + this.f[i2 + b1]] % 12;
                int l2 = this.f[l1 + 1 + this.f[i2 + 1]] % 12;
                double d20 = 0.5D - d14 * d14 - d15 * d15;
                double d7;
                if (d20 < 0.0D) {
                    d7 = 0.0D;
                } else {
                    d20 = d20 * d20;
                    d7 = d20 * d20 * a(e[j2], d14, d15);
                }

                double d21 = 0.5D - d16 * d16 - d17 * d17;
                double d8;
                if (d21 < 0.0D) {
                    d8 = 0.0D;
                } else {
                    d21 = d21 * d21;
                    d8 = d21 * d21 * a(e[k2], d16, d17);
                }

                double d22 = 0.5D - d18 * d18 - d19 * d19;
                double d9;
                if (d22 < 0.0D) {
                    d9 = 0.0D;
                } else {
                    d22 = d22 * d22;
                    d9 = d22 * d22 * a(e[l2], d18, d19);
                }

                int i3 = k++;
                adouble[i3] += 70.0D * (d7 + d8 + d9) * d4;
            }
        }

    }
}
