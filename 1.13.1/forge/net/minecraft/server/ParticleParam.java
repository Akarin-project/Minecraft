package net.minecraft.server;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public interface ParticleParam {
    Particle<?> b();

    void a(PacketDataSerializer var1);

    String a();

    public interface a<T extends ParticleParam> {
        T b(Particle<T> var1, StringReader var2) throws CommandSyntaxException;

        T b(Particle<T> var1, PacketDataSerializer var2);
    }
}
