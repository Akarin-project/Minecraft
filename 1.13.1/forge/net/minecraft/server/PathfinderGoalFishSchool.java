package net.minecraft.server;

import java.util.List;

public class PathfinderGoalFishSchool extends PathfinderGoal {
    private final EntityFish a;
    private EntityFish b;
    private int c;

    public PathfinderGoalFishSchool(EntityFish entityfish) {
        this.a = entityfish;
    }

    public boolean a() {
        if (!this.a.dB() && !this.a.dz()) {
            List list = this.a.world.a(this.a.getClass(), this.a.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                return false;
            } else {
                for(EntityFish entityfish : list) {
                    if (entityfish.l() && !entityfish.equals(this.a)) {
                        ++entityfish.a;
                        this.b = entityfish;
                        return true;
                    }
                }

                for(EntityFish entityfish1 : list) {
                    if (!entityfish1.equals(this.a) && !entityfish1.dz() && !entityfish1.dB()) {
                        entityfish1.t(true);
                        ++entityfish1.a;
                        this.b = entityfish1;
                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public boolean b() {
        if (this.b.isAlive() && this.b.dB()) {
            double d0 = this.a.h(this.b);
            return d0 <= 121.0D;
        } else {
            return false;
        }
    }

    public void c() {
        this.a.a(true);
        this.c = 0;
    }

    public void d() {
        this.a.a(false);
        --this.b.a;
        this.b = null;
    }

    public void e() {
        if (--this.c <= 0) {
            this.c = 10;
            this.a.getNavigation().a(this.b, 1.0D);
        }
    }
}
