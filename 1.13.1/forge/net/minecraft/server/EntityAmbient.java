package net.minecraft.server;

public abstract class EntityAmbient extends EntityInsentient implements IAnimal {
    protected EntityAmbient(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
    }

    public boolean a(EntityHuman var1) {
        return false;
    }
}
