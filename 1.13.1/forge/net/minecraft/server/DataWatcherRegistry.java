package net.minecraft.server;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public class DataWatcherRegistry {
    private static final RegistryID<DataWatcherSerializer<?>> q = new RegistryID<DataWatcherSerializer<?>>(16);
    public static final DataWatcherSerializer<Byte> a = new DataWatcherSerializer<Byte>() {
        public void a(PacketDataSerializer packetdataserializer, Byte obyte) {
            packetdataserializer.writeByte(obyte);
        }

        public Byte b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.readByte();
        }

        public DataWatcherObject<Byte> a(int i) {
            return new DataWatcherObject<Byte>(i, this);
        }

        public Byte a(Byte obyte) {
            return obyte;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Integer> b = new DataWatcherSerializer<Integer>() {
        public void a(PacketDataSerializer packetdataserializer, Integer integer) {
            packetdataserializer.d(integer);
        }

        public Integer b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.g();
        }

        public DataWatcherObject<Integer> a(int i) {
            return new DataWatcherObject<Integer>(i, this);
        }

        public Integer a(Integer integer) {
            return integer;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Float> c = new DataWatcherSerializer<Float>() {
        public void a(PacketDataSerializer packetdataserializer, Float f) {
            packetdataserializer.writeFloat(f);
        }

        public Float b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.readFloat();
        }

        public DataWatcherObject<Float> a(int i) {
            return new DataWatcherObject<Float>(i, this);
        }

        public Float a(Float f) {
            return f;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<String> d = new DataWatcherSerializer<String>() {
        public void a(PacketDataSerializer packetdataserializer, String s) {
            packetdataserializer.a(s);
        }

        public String b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.e(32767);
        }

        public DataWatcherObject<String> a(int i) {
            return new DataWatcherObject<String>(i, this);
        }

        public String a(String s) {
            return s;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<IChatBaseComponent> e = new DataWatcherSerializer<IChatBaseComponent>() {
        public void a(PacketDataSerializer packetdataserializer, IChatBaseComponent ichatbasecomponent) {
            packetdataserializer.a(ichatbasecomponent);
        }

        public IChatBaseComponent b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.f();
        }

        public DataWatcherObject<IChatBaseComponent> a(int i) {
            return new DataWatcherObject<IChatBaseComponent>(i, this);
        }

        public IChatBaseComponent a(IChatBaseComponent ichatbasecomponent) {
            return ichatbasecomponent.h();
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<IChatBaseComponent>> f = new DataWatcherSerializer<Optional<IChatBaseComponent>>() {
        public void a(PacketDataSerializer packetdataserializer, Optional<IChatBaseComponent> optional) {
            if (optional.isPresent()) {
                packetdataserializer.writeBoolean(true);
                packetdataserializer.a((IChatBaseComponent)optional.get());
            } else {
                packetdataserializer.writeBoolean(false);
            }

        }

        public Optional<IChatBaseComponent> b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.readBoolean() ? Optional.of(packetdataserializer.f()) : Optional.empty();
        }

        public DataWatcherObject<Optional<IChatBaseComponent>> a(int i) {
            return new DataWatcherObject<Optional<IChatBaseComponent>>(i, this);
        }

        public Optional<IChatBaseComponent> a(Optional<IChatBaseComponent> optional) {
            return optional.isPresent() ? Optional.of(((IChatBaseComponent)optional.get()).h()) : Optional.empty();
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<ItemStack> g = new DataWatcherSerializer<ItemStack>() {
        public void a(PacketDataSerializer packetdataserializer, ItemStack itemstack) {
            packetdataserializer.a(itemstack);
        }

        public ItemStack b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.k();
        }

        public DataWatcherObject<ItemStack> a(int i) {
            return new DataWatcherObject<ItemStack>(i, this);
        }

        public ItemStack a(ItemStack itemstack) {
            return itemstack.cloneItemStack();
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<IBlockData>> h = new DataWatcherSerializer<Optional<IBlockData>>() {
        public void a(PacketDataSerializer packetdataserializer, Optional<IBlockData> optional) {
            if (optional.isPresent()) {
                packetdataserializer.d(Block.getCombinedId((IBlockData)optional.get()));
            } else {
                packetdataserializer.d(0);
            }

        }

        public Optional<IBlockData> b(PacketDataSerializer packetdataserializer) {
            int i = packetdataserializer.g();
            return i == 0 ? Optional.empty() : Optional.of(Block.getByCombinedId(i));
        }

        public DataWatcherObject<Optional<IBlockData>> a(int i) {
            return new DataWatcherObject<Optional<IBlockData>>(i, this);
        }

        public Optional<IBlockData> a(Optional<IBlockData> optional) {
            return optional;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Boolean> i = new DataWatcherSerializer<Boolean>() {
        public void a(PacketDataSerializer packetdataserializer, Boolean obool) {
            packetdataserializer.writeBoolean(obool);
        }

        public Boolean b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.readBoolean();
        }

        public DataWatcherObject<Boolean> a(int i) {
            return new DataWatcherObject<Boolean>(i, this);
        }

        public Boolean a(Boolean obool) {
            return obool;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<ParticleParam> j = new DataWatcherSerializer<ParticleParam>() {
        public void a(PacketDataSerializer packetdataserializer, ParticleParam particleparam) {
            packetdataserializer.d(IRegistry.PARTICLE_TYPE.a(particleparam.b()));
            particleparam.a(packetdataserializer);
        }

        public ParticleParam b(PacketDataSerializer packetdataserializer) {
            return this.a(packetdataserializer, IRegistry.PARTICLE_TYPE.fromId(packetdataserializer.g()));
        }

        private <T extends ParticleParam> T a(PacketDataSerializer packetdataserializer, Particle<T> particle) {
            return (T)particle.f().b(particle, packetdataserializer);
        }

        public DataWatcherObject<ParticleParam> a(int i) {
            return new DataWatcherObject<ParticleParam>(i, this);
        }

        public ParticleParam a(ParticleParam particleparam) {
            return particleparam;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Vector3f> k = new DataWatcherSerializer<Vector3f>() {
        public void a(PacketDataSerializer packetdataserializer, Vector3f vector3f) {
            packetdataserializer.writeFloat(vector3f.getX());
            packetdataserializer.writeFloat(vector3f.getY());
            packetdataserializer.writeFloat(vector3f.getZ());
        }

        public Vector3f b(PacketDataSerializer packetdataserializer) {
            return new Vector3f(packetdataserializer.readFloat(), packetdataserializer.readFloat(), packetdataserializer.readFloat());
        }

        public DataWatcherObject<Vector3f> a(int i) {
            return new DataWatcherObject<Vector3f>(i, this);
        }

        public Vector3f a(Vector3f vector3f) {
            return vector3f;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<BlockPosition> l = new DataWatcherSerializer<BlockPosition>() {
        public void a(PacketDataSerializer packetdataserializer, BlockPosition blockposition) {
            packetdataserializer.a(blockposition);
        }

        public BlockPosition b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.e();
        }

        public DataWatcherObject<BlockPosition> a(int i) {
            return new DataWatcherObject<BlockPosition>(i, this);
        }

        public BlockPosition a(BlockPosition blockposition) {
            return blockposition;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<BlockPosition>> m = new DataWatcherSerializer<Optional<BlockPosition>>() {
        public void a(PacketDataSerializer packetdataserializer, Optional<BlockPosition> optional) {
            packetdataserializer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetdataserializer.a((BlockPosition)optional.get());
            }

        }

        public Optional<BlockPosition> b(PacketDataSerializer packetdataserializer) {
            return !packetdataserializer.readBoolean() ? Optional.empty() : Optional.of(packetdataserializer.e());
        }

        public DataWatcherObject<Optional<BlockPosition>> a(int i) {
            return new DataWatcherObject<Optional<BlockPosition>>(i, this);
        }

        public Optional<BlockPosition> a(Optional<BlockPosition> optional) {
            return optional;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<EnumDirection> n = new DataWatcherSerializer<EnumDirection>() {
        public void a(PacketDataSerializer packetdataserializer, EnumDirection enumdirection) {
            packetdataserializer.a((Enum)enumdirection);
        }

        public EnumDirection b(PacketDataSerializer packetdataserializer) {
            return (EnumDirection)packetdataserializer.a(EnumDirection.class);
        }

        public DataWatcherObject<EnumDirection> a(int i) {
            return new DataWatcherObject<EnumDirection>(i, this);
        }

        public EnumDirection a(EnumDirection enumdirection) {
            return enumdirection;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<UUID>> o = new DataWatcherSerializer<Optional<UUID>>() {
        public void a(PacketDataSerializer packetdataserializer, Optional<UUID> optional) {
            packetdataserializer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetdataserializer.a((UUID)optional.get());
            }

        }

        public Optional<UUID> b(PacketDataSerializer packetdataserializer) {
            return !packetdataserializer.readBoolean() ? Optional.empty() : Optional.of(packetdataserializer.i());
        }

        public DataWatcherObject<Optional<UUID>> a(int i) {
            return new DataWatcherObject<Optional<UUID>>(i, this);
        }

        public Optional<UUID> a(Optional<UUID> optional) {
            return optional;
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<NBTTagCompound> p = new DataWatcherSerializer<NBTTagCompound>() {
        public void a(PacketDataSerializer packetdataserializer, NBTTagCompound nbttagcompound) {
            packetdataserializer.a(nbttagcompound);
        }

        public NBTTagCompound b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.j();
        }

        public DataWatcherObject<NBTTagCompound> a(int i) {
            return new DataWatcherObject<NBTTagCompound>(i, this);
        }

        public NBTTagCompound a(NBTTagCompound nbttagcompound) {
            return nbttagcompound.clone();
        }

        // $FF: synthetic method
        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };

    public static void a(DataWatcherSerializer<?> datawatcherserializer) {
        q.c(datawatcherserializer);
    }

    @Nullable
    public static DataWatcherSerializer<?> a(int ix) {
        return q.fromId(ix);
    }

    public static int b(DataWatcherSerializer<?> datawatcherserializer) {
        return q.getId(datawatcherserializer);
    }

    static {
        a(a);
        a(b);
        a(c);
        a(d);
        a(e);
        a(f);
        a(g);
        a(i);
        a(k);
        a(l);
        a(m);
        a(n);
        a(o);
        a(h);
        a(p);
        a(j);
    }
}
