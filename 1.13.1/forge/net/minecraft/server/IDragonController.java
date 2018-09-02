package net.minecraft.server;

import javax.annotation.Nullable;

public interface IDragonController {
    boolean a();

    void b();

    void c();

    void a(EntityEnderCrystal var1, BlockPosition var2, DamageSource var3, @Nullable EntityHuman var4);

    void d();

    void e();

    float f();

    float h();

    DragonControllerPhase<? extends IDragonController> getControllerPhase();

    @Nullable
    Vec3D g();

    float a(EntityComplexPart var1, DamageSource var2, float var3);
}
