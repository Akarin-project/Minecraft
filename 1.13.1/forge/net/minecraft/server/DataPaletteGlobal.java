package net.minecraft.server;

public class DataPaletteGlobal<T> implements DataPalette<T> {
    private final RegistryBlockID<T> a;
    private final T b;

    public DataPaletteGlobal(RegistryBlockID<T> registryblockid, T object) {
        this.a = registryblockid;
        this.b = (T)object;
    }

    public int a(T object) {
        int i = this.a.getId((T)object);
        return i == -1 ? 0 : i;
    }

    public T a(int i) {
        Object object = this.a.fromId(i);
        return (T)(object == null ? this.b : object);
    }

    public void b(PacketDataSerializer var1) {
    }

    public int a() {
        return PacketDataSerializer.a(0);
    }

    public void a(NBTTagList var1) {
    }
}
