package net.minecraft.server;

public interface DataWatcherSerializer<T> {
    void a(PacketDataSerializer var1, T var2);

    T a(PacketDataSerializer var1);

    DataWatcherObject<T> a(int var1);

    T a(T var1);
}
