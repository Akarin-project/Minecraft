package net.minecraft.server;

public abstract class PersistentBase {
    private final String id;
    private boolean b;

    public PersistentBase(String s) {
        this.id = s;
    }

    public abstract void a(NBTTagCompound var1);

    public abstract NBTTagCompound b(NBTTagCompound var1);

    public void c() {
        this.a(true);
    }

    public void a(boolean flag) {
        this.b = flag;
    }

    public boolean d() {
        return this.b;
    }

    public String getId() {
        return this.id;
    }
}
