package net.minecraft.server;

public class InstantMobEffect extends MobEffectList {
    public InstantMobEffect(boolean flag, int i) {
        super(flag, i);
    }

    public boolean isInstant() {
        return true;
    }

    public boolean a(int i, int var2) {
        return i >= 1;
    }
}
