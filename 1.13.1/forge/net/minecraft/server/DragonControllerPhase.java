package net.minecraft.server;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class DragonControllerPhase<T extends IDragonController> {
    private static DragonControllerPhase<?>[] l = new DragonControllerPhase[0];
    public static final DragonControllerPhase<DragonControllerHold> a = a(DragonControllerHold.class, "HoldingPattern");
    public static final DragonControllerPhase<DragonControllerStrafe> b = a(DragonControllerStrafe.class, "StrafePlayer");
    public static final DragonControllerPhase<DragonControllerLandingFly> c = a(DragonControllerLandingFly.class, "LandingApproach");
    public static final DragonControllerPhase<DragonControllerLanding> d = a(DragonControllerLanding.class, "Landing");
    public static final DragonControllerPhase<DragonControllerFly> e = a(DragonControllerFly.class, "Takeoff");
    public static final DragonControllerPhase<DragonControllerLandedFlame> f = a(DragonControllerLandedFlame.class, "SittingFlaming");
    public static final DragonControllerPhase<DragonControllerLandedSearch> g = a(DragonControllerLandedSearch.class, "SittingScanning");
    public static final DragonControllerPhase<DragonControllerLandedAttack> h = a(DragonControllerLandedAttack.class, "SittingAttacking");
    public static final DragonControllerPhase<DragonControllerCharge> i = a(DragonControllerCharge.class, "ChargingPlayer");
    public static final DragonControllerPhase<DragonControllerDying> j = a(DragonControllerDying.class, "Dying");
    public static final DragonControllerPhase<DragonControllerHover> k = a(DragonControllerHover.class, "Hover");
    private final Class<? extends IDragonController> m;
    private final int n;
    private final String o;

    private DragonControllerPhase(int ix, Class<? extends IDragonController> oclass, String s) {
        this.n = ix;
        this.m = oclass;
        this.o = s;
    }

    public IDragonController a(EntityEnderDragon entityenderdragon) {
        try {
            Constructor constructor = this.a();
            return (IDragonController)constructor.newInstance(entityenderdragon);
        } catch (Exception exception) {
            throw new Error(exception);
        }
    }

    protected Constructor<? extends IDragonController> a() throws NoSuchMethodException {
        return this.m.getConstructor(EntityEnderDragon.class);
    }

    public int b() {
        return this.n;
    }

    public String toString() {
        return this.o + " (#" + this.n + ")";
    }

    public static DragonControllerPhase<?> getById(int ix) {
        return ix >= 0 && ix < l.length ? l[ix] : a;
    }

    public static int c() {
        return l.length;
    }

    private static <T extends IDragonController> DragonControllerPhase<T> a(Class<T> oclass, String s) {
        DragonControllerPhase dragoncontrollerphase = new DragonControllerPhase(l.length, oclass, s);
        l = (DragonControllerPhase[])Arrays.copyOf(l, l.length + 1);
        l[dragoncontrollerphase.b()] = dragoncontrollerphase;
        return dragoncontrollerphase;
    }
}
