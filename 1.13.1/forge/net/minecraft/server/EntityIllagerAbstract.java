package net.minecraft.server;

public abstract class EntityIllagerAbstract extends EntityMonster {
    protected static final DataWatcherObject<Byte> a = DataWatcher.a(EntityIllagerAbstract.class, DataWatcherRegistry.a);

    protected EntityIllagerAbstract(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(a, (byte)0);
    }

    protected void a(int i, boolean flag) {
        int j = this.datawatcher.get(a);
        if (flag) {
            j = j | i;
        } else {
            j = j & ~i;
        }

        this.datawatcher.set(a, (byte)(j & 255));
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ILLAGER;
    }
}
