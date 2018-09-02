package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;

public class ArgumentSerializerVoid<T extends ArgumentType<?>> implements ArgumentSerializer<T> {
    private final Supplier<T> a;

    public ArgumentSerializerVoid(Supplier<T> supplier) {
        this.a = supplier;
    }

    public void a(T var1, PacketDataSerializer var2) {
    }

    public T b(PacketDataSerializer var1) {
        return (T)(this.a.get());
    }

    public void a(T var1, JsonObject var2) {
    }
}
