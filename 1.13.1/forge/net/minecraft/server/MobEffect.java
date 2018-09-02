package net.minecraft.server;

import com.google.common.collect.ComparisonChain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobEffect implements Comparable<MobEffect> {
    private static final Logger a = LogManager.getLogger();
    private final MobEffectList b;
    private int duration;
    private int amplification;
    private boolean splash;
    private boolean ambient;
    private boolean h;
    private boolean i;

    public MobEffect(MobEffectList mobeffectlist) {
        this(mobeffectlist, 0, 0);
    }

    public MobEffect(MobEffectList mobeffectlist, int ix) {
        this(mobeffectlist, ix, 0);
    }

    public MobEffect(MobEffectList mobeffectlist, int ix, int j) {
        this(mobeffectlist, ix, j, false, true);
    }

    public MobEffect(MobEffectList mobeffectlist, int ix, int j, boolean flag, boolean flag1) {
        this(mobeffectlist, ix, j, flag, flag1, flag1);
    }

    public MobEffect(MobEffectList mobeffectlist, int ix, int j, boolean flag, boolean flag1, boolean flag2) {
        this.b = mobeffectlist;
        this.duration = ix;
        this.amplification = j;
        this.ambient = flag;
        this.h = flag1;
        this.i = flag2;
    }

    public MobEffect(MobEffect mobeffect1) {
        this.b = mobeffect1.b;
        this.duration = mobeffect1.duration;
        this.amplification = mobeffect1.amplification;
        this.ambient = mobeffect1.ambient;
        this.h = mobeffect1.h;
        this.i = mobeffect1.i;
    }

    public boolean a(MobEffect mobeffect1) {
        if (this.b != mobeffect1.b) {
            a.warn("This method should only be called for matching effects!");
        }

        boolean flag = false;
        if (mobeffect1.amplification > this.amplification) {
            this.amplification = mobeffect1.amplification;
            this.duration = mobeffect1.duration;
            flag = true;
        } else if (mobeffect1.amplification == this.amplification && this.duration < mobeffect1.duration) {
            this.duration = mobeffect1.duration;
            flag = true;
        }

        if (!mobeffect1.ambient && this.ambient || flag) {
            this.ambient = mobeffect1.ambient;
            flag = true;
        }

        if (mobeffect1.h != this.h) {
            this.h = mobeffect1.h;
            flag = true;
        }

        if (mobeffect1.i != this.i) {
            this.i = mobeffect1.i;
            flag = true;
        }

        return flag;
    }

    public MobEffectList getMobEffect() {
        return this.b;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplification;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public boolean isShowParticles() {
        return this.h;
    }

    public boolean f() {
        return this.i;
    }

    public boolean tick(EntityLiving entityliving) {
        if (this.duration > 0) {
            if (this.b.a(this.duration, this.amplification)) {
                this.b(entityliving);
            }

            this.i();
        }

        return this.duration > 0;
    }

    private int i() {
        return --this.duration;
    }

    public void b(EntityLiving entityliving) {
        if (this.duration > 0) {
            this.b.tick(entityliving, this.amplification);
        }

    }

    public String g() {
        return this.b.c();
    }

    public String toString() {
        String s;
        if (this.amplification > 0) {
            s = this.g() + " x " + (this.amplification + 1) + ", Duration: " + this.duration;
        } else {
            s = this.g() + ", Duration: " + this.duration;
        }

        if (this.splash) {
            s = s + ", Splash: true";
        }

        if (!this.h) {
            s = s + ", Particles: false";
        }

        if (!this.i) {
            s = s + ", Show Icon: false";
        }

        return s;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof MobEffect)) {
            return false;
        } else {
            MobEffect mobeffect1 = (MobEffect)object;
            return this.duration == mobeffect1.duration && this.amplification == mobeffect1.amplification && this.splash == mobeffect1.splash && this.ambient == mobeffect1.ambient && this.b.equals(mobeffect1.b);
        }
    }

    public int hashCode() {
        int ix = this.b.hashCode();
        ix = 31 * ix + this.duration;
        ix = 31 * ix + this.amplification;
        ix = 31 * ix + (this.splash ? 1 : 0);
        ix = 31 * ix + (this.ambient ? 1 : 0);
        return ix;
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("Id", (byte)MobEffectList.getId(this.getMobEffect()));
        nbttagcompound.setByte("Amplifier", (byte)this.getAmplifier());
        nbttagcompound.setInt("Duration", this.getDuration());
        nbttagcompound.setBoolean("Ambient", this.isAmbient());
        nbttagcompound.setBoolean("ShowParticles", this.isShowParticles());
        nbttagcompound.setBoolean("ShowIcon", this.f());
        return nbttagcompound;
    }

    public static MobEffect b(NBTTagCompound nbttagcompound) {
        byte b0 = nbttagcompound.getByte("Id");
        MobEffectList mobeffectlist = MobEffectList.fromId(b0);
        if (mobeffectlist == null) {
            return null;
        } else {
            byte b1 = nbttagcompound.getByte("Amplifier");
            int ix = nbttagcompound.getInt("Duration");
            boolean flag = nbttagcompound.getBoolean("Ambient");
            boolean flag1 = true;
            if (nbttagcompound.hasKeyOfType("ShowParticles", 1)) {
                flag1 = nbttagcompound.getBoolean("ShowParticles");
            }

            boolean flag2 = flag1;
            if (nbttagcompound.hasKeyOfType("ShowIcon", 1)) {
                flag2 = nbttagcompound.getBoolean("ShowIcon");
            }

            return new MobEffect(mobeffectlist, ix, b1 < 0 ? 0 : b1, flag, flag1, flag2);
        }
    }

    public int b(MobEffect mobeffect1) {
        boolean flag = true;
        return (this.getDuration() <= 32147 || mobeffect1.getDuration() <= 32147) && (!this.isAmbient() || !mobeffect1.isAmbient()) ? ComparisonChain.start().compare(this.isAmbient(), mobeffect1.isAmbient()).compare(this.getDuration(), mobeffect1.getDuration()).compare(this.getMobEffect().getColor(), mobeffect1.getMobEffect().getColor()).result() : ComparisonChain.start().compare(this.isAmbient(), mobeffect1.isAmbient()).compare(this.getMobEffect().getColor(), mobeffect1.getMobEffect().getColor()).result();
    }

    // $FF: synthetic method
    public int compareTo(Object object) {
        return this.b((MobEffect)object);
    }
}
