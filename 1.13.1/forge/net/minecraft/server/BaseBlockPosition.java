package net.minecraft.server;

import com.google.common.base.MoreObjects;
import javax.annotation.concurrent.Immutable;

@Immutable
public class BaseBlockPosition implements Comparable<BaseBlockPosition> {
    public static final BaseBlockPosition ZERO = new BaseBlockPosition(0, 0, 0);
    private final int a;
    private final int b;
    private final int c;

    public BaseBlockPosition(int i, int j, int k) {
        this.a = i;
        this.b = j;
        this.c = k;
    }

    public BaseBlockPosition(double d0, double d1, double d2) {
        this(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof BaseBlockPosition)) {
            return false;
        } else {
            BaseBlockPosition baseblockposition1 = (BaseBlockPosition)object;
            if (this.getX() != baseblockposition1.getX()) {
                return false;
            } else if (this.getY() != baseblockposition1.getY()) {
                return false;
            } else {
                return this.getZ() == baseblockposition1.getZ();
            }
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int l(BaseBlockPosition baseblockposition1) {
        if (this.getY() == baseblockposition1.getY()) {
            return this.getZ() == baseblockposition1.getZ() ? this.getX() - baseblockposition1.getX() : this.getZ() - baseblockposition1.getZ();
        } else {
            return this.getY() - baseblockposition1.getY();
        }
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public BaseBlockPosition d(BaseBlockPosition baseblockposition1) {
        return new BaseBlockPosition(this.getY() * baseblockposition1.getZ() - this.getZ() * baseblockposition1.getY(), this.getZ() * baseblockposition1.getX() - this.getX() * baseblockposition1.getZ(), this.getX() * baseblockposition1.getY() - this.getY() * baseblockposition1.getX());
    }

    public double h(int i, int j, int k) {
        double d0 = (double)(this.getX() - i);
        double d1 = (double)(this.getY() - j);
        double d2 = (double)(this.getZ() - k);
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double m(BaseBlockPosition baseblockposition1) {
        return this.h(baseblockposition1.getX(), baseblockposition1.getY(), baseblockposition1.getZ());
    }

    public double distanceSquared(double d0, double d1, double d2) {
        double d3 = (double)this.getX() - d0;
        double d4 = (double)this.getY() - d1;
        double d5 = (double)this.getZ() - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double g(double d0, double d1, double d2) {
        double d3 = (double)this.getX() + 0.5D - d0;
        double d4 = (double)this.getY() + 0.5D - d1;
        double d5 = (double)this.getZ() + 0.5D - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double n(BaseBlockPosition baseblockposition1) {
        return this.distanceSquared((double)baseblockposition1.getX(), (double)baseblockposition1.getY(), (double)baseblockposition1.getZ());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    // $FF: synthetic method
    public int compareTo(Object object) {
        return this.l((BaseBlockPosition)object);
    }
}
