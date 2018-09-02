package net.minecraft.server;

import javax.annotation.Nullable;

public abstract class EntityGolem extends EntityCreature implements IAnimal {
    protected EntityGolem(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
    }

    public void c(float var1, float var2) {
    }

    @Nullable
    protected SoundEffect D() {
        return null;
    }

    @Nullable
    protected SoundEffect d(DamageSource var1) {
        return null;
    }

    @Nullable
    protected SoundEffect cs() {
        return null;
    }

    public int z() {
        return 120;
    }

    protected boolean isTypeNotPersistent() {
        return false;
    }
}
