package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;

public interface ArgumentSerializer<T extends ArgumentType<?>> {
    void a(T var1, PacketDataSerializer var2);

    T b(PacketDataSerializer var1);

    void a(T var1, JsonObject var2);
}
