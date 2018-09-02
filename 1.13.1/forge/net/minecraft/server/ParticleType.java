package net.minecraft.server;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class ParticleType extends Particle<ParticleType> implements ParticleParam {
    private static final ParticleParam.a<ParticleType> a = new ParticleParam.a<ParticleType>() {
        public ParticleType a(Particle<ParticleType> particle, StringReader var2) throws CommandSyntaxException {
            return (ParticleType)particle;
        }

        public ParticleType a(Particle<ParticleType> particle, PacketDataSerializer var2) {
            return (ParticleType)particle;
        }

        // $FF: synthetic method
        public ParticleParam b(Particle particle, PacketDataSerializer packetdataserializer) {
            return this.a(particle, packetdataserializer);
        }

        // $FF: synthetic method
        public ParticleParam b(Particle particle, StringReader stringreader) throws CommandSyntaxException {
            return this.a(particle, stringreader);
        }
    };

    protected ParticleType(MinecraftKey minecraftkey, boolean flag) {
        super(minecraftkey, flag, a);
    }

    public Particle<ParticleType> b() {
        return this;
    }

    public void a(PacketDataSerializer var1) {
    }

    public String a() {
        return this.d().toString();
    }
}
