package net.minecraft.server;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
    private final int[] d = new int[512];
    public double a;
    public double b;
    public double c;
    private static final double[] e = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
    private static final double[] f = new double[]{1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
    private static final double[] g = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
    private static final double[] h = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
    private static final double[] i = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};

    public NoiseGeneratorPerlin(Random random) {
        this.a = random.nextDouble() * 256.0D;
        this.b = random.nextDouble() * 256.0D;
        this.c = random.nextDouble() * 256.0D;

        for(int ix = 0; ix < 256; this.d[ix] = ix++) {
            ;
        }

        for(int l = 0; l < 256; ++l) {
            int j = random.nextInt(256 - l) + l;
            int k = this.d[l];
            this.d[l] = this.d[j];
            this.d[j] = k;
            this.d[l + 256] = this.d[l];
        }

    }

    public double a(double d0, double d1, double d2) {
        double d3 = d0 + this.a;
        double d4 = d1 + this.b;
        double d5 = d2 + this.c;
        int ix = (int)d3;
        int j = (int)d4;
        int k = (int)d5;
        if (d3 < (double)ix) {
            --ix;
        }

        if (d4 < (double)j) {
            --j;
        }

        if (d5 < (double)k) {
            --k;
        }

        int l = ix & 255;
        int i1 = j & 255;
        int j1 = k & 255;
        d3 = d3 - (double)ix;
        d4 = d4 - (double)j;
        d5 = d5 - (double)k;
        double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0D - 15.0D) + 10.0D);
        double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0D - 15.0D) + 10.0D);
        double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);
        int k1 = this.d[l] + i1;
        int l1 = this.d[k1] + j1;
        int i2 = this.d[k1 + 1] + j1;
        int j2 = this.d[l + 1] + i1;
        int k2 = this.d[j2] + j1;
        int l2 = this.d[j2 + 1] + j1;
        return this.b(d8, this.b(d7, this.b(d6, this.a(this.d[l1], d3, d4, d5), this.a(this.d[k2], d3 - 1.0D, d4, d5)), this.b(d6, this.a(this.d[i2], d3, d4 - 1.0D, d5), this.a(this.d[l2], d3 - 1.0D, d4 - 1.0D, d5))), this.b(d7, this.b(d6, this.a(this.d[l1 + 1], d3, d4, d5 - 1.0D), this.a(this.d[k2 + 1], d3 - 1.0D, d4, d5 - 1.0D)), this.b(d6, this.a(this.d[i2 + 1], d3, d4 - 1.0D, d5 - 1.0D), this.a(this.d[l2 + 1], d3 - 1.0D, d4 - 1.0D, d5 - 1.0D))));
    }

    public final double b(double d0, double d1, double d2) {
        return d1 + d0 * (d2 - d1);
    }

    public final double a(int ix, double d0, double d1) {
        int j = ix & 15;
        return h[j] * d0 + i[j] * d1;
    }

    public final double a(int ix, double d0, double d1, double d2) {
        int j = ix & 15;
        return e[j] * d0 + f[j] * d1 + g[j] * d2;
    }

    public double a(double d0, double d1) {
        return this.a(d0, d1, 0.0D);
    }

    public double c(double d0, double d1, double d2) {
        return this.a(d0, d1, d2);
    }

    public void a(double[] adouble, double d0, double d1, double d2, int ix, int j, int k, double d3, double d4, double d5, double d6) {
        if (j == 1) {
            int l5 = 0;
            int i6 = 0;
            int i1 = 0;
            int j6 = 0;
            double d21 = 0.0D;
            double d22 = 0.0D;
            int k6 = 0;
            double d23 = 1.0D / d6;

            for(int i3 = 0; i3 < ix; ++i3) {
                double d24 = d0 + (double)i3 * d3 + this.a;
                int l6 = (int)d24;
                if (d24 < (double)l6) {
                    --l6;
                }

                int j3 = l6 & 255;
                d24 = d24 - (double)l6;
                double d25 = d24 * d24 * d24 * (d24 * (d24 * 6.0D - 15.0D) + 10.0D);

                for(int i7 = 0; i7 < k; ++i7) {
                    double d26 = d2 + (double)i7 * d5 + this.c;
                    int j7 = (int)d26;
                    if (d26 < (double)j7) {
                        --j7;
                    }

                    int k7 = j7 & 255;
                    d26 = d26 - (double)j7;
                    double d27 = d26 * d26 * d26 * (d26 * (d26 * 6.0D - 15.0D) + 10.0D);
                    l5 = this.d[j3] + 0;
                    i6 = this.d[l5] + k7;
                    i1 = this.d[j3 + 1] + 0;
                    j6 = this.d[i1] + k7;
                    d21 = this.b(d25, this.a(this.d[i6], d24, d26), this.a(this.d[j6], d24 - 1.0D, 0.0D, d26));
                    d22 = this.b(d25, this.a(this.d[i6 + 1], d24, 0.0D, d26 - 1.0D), this.a(this.d[j6 + 1], d24 - 1.0D, 0.0D, d26 - 1.0D));
                    double d28 = this.b(d27, d21, d22);
                    int l7 = k6++;
                    adouble[l7] += d28 * d23;
                }
            }

        } else {
            int l = 0;
            double d7 = 1.0D / d6;
            int j1 = -1;
            int k1 = 0;
            int l1 = 0;
            int i2 = 0;
            int j2 = 0;
            int k2 = 0;
            int l2 = 0;
            double d8 = 0.0D;
            double d9 = 0.0D;
            double d10 = 0.0D;
            double d11 = 0.0D;

            for(int k3 = 0; k3 < ix; ++k3) {
                double d12 = d0 + (double)k3 * d3 + this.a;
                int l3 = (int)d12;
                if (d12 < (double)l3) {
                    --l3;
                }

                int i4 = l3 & 255;
                d12 = d12 - (double)l3;
                double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0D - 15.0D) + 10.0D);

                for(int j4 = 0; j4 < k; ++j4) {
                    double d14 = d2 + (double)j4 * d5 + this.c;
                    int k4 = (int)d14;
                    if (d14 < (double)k4) {
                        --k4;
                    }

                    int l4 = k4 & 255;
                    d14 = d14 - (double)k4;
                    double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0D - 15.0D) + 10.0D);

                    for(int i5 = 0; i5 < j; ++i5) {
                        double d16 = d1 + (double)i5 * d4 + this.b;
                        int j5 = (int)d16;
                        if (d16 < (double)j5) {
                            --j5;
                        }

                        int k5 = j5 & 255;
                        d16 = d16 - (double)j5;
                        double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0D - 15.0D) + 10.0D);
                        if (i5 == 0 || k5 != j1) {
                            j1 = k5;
                            k1 = this.d[i4] + k5;
                            l1 = this.d[k1] + l4;
                            i2 = this.d[k1 + 1] + l4;
                            j2 = this.d[i4 + 1] + k5;
                            k2 = this.d[j2] + l4;
                            l2 = this.d[j2 + 1] + l4;
                            d8 = this.b(d13, this.a(this.d[l1], d12, d16, d14), this.a(this.d[k2], d12 - 1.0D, d16, d14));
                            d9 = this.b(d13, this.a(this.d[i2], d12, d16 - 1.0D, d14), this.a(this.d[l2], d12 - 1.0D, d16 - 1.0D, d14));
                            d10 = this.b(d13, this.a(this.d[l1 + 1], d12, d16, d14 - 1.0D), this.a(this.d[k2 + 1], d12 - 1.0D, d16, d14 - 1.0D));
                            d11 = this.b(d13, this.a(this.d[i2 + 1], d12, d16 - 1.0D, d14 - 1.0D), this.a(this.d[l2 + 1], d12 - 1.0D, d16 - 1.0D, d14 - 1.0D));
                        }

                        double d18 = this.b(d17, d8, d9);
                        double d19 = this.b(d17, d10, d11);
                        double d20 = this.b(d15, d18, d19);
                        int i8 = l++;
                        adouble[i8] += d20 * d7;
                    }
                }
            }

        }
    }
}
