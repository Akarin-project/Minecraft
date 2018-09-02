package net.minecraft.server;

import javax.annotation.Nullable;

public interface DataPalette<T> {
    int a(T var1);

    @Nullable
    T a(int var1);

    void b(PacketDataSerializer var1);

    int a();

    void a(NBTTagList var1);
}
