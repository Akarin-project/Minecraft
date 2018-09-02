package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IRegistry<T> extends Registry<T> {
    Logger e = LogManager.getLogger();
    IRegistry<IRegistry<?>> f = new RegistryMaterials<IRegistry<?>>();
    IRegistry<Block> BLOCK = a("block", new RegistryBlocks(new MinecraftKey("air")));
    IRegistry<FluidType> FLUID = a("fluid", new RegistryBlocks(new MinecraftKey("empty")));
    IRegistry<Paintings> MOTIVE = a("motive", new RegistryBlocks(new MinecraftKey("kebab")));
    IRegistry<PotionRegistry> POTION = a("potion", new RegistryBlocks(new MinecraftKey("empty")));
    IRegistry<DimensionManager> DIMENSION_TYPE = a("dimension_type", new RegistryMaterials());
    IRegistry<MinecraftKey> CUSTOM_STAT = a("custom_stat", new RegistryMaterials());
    IRegistry<BiomeBase> BIOME = a("biome", new RegistryMaterials());
    IRegistry<BiomeLayout<?, ?>> BIOME_SOURCE_TYPE = a("biome_source_type", new RegistryMaterials());
    IRegistry<TileEntityTypes<?>> BLOCK_ENTITY_TYPE = a("block_entity_type", new RegistryMaterials());
    IRegistry<ChunkGeneratorType<?, ?>> CHUNK_GENERATOR_TYPE = a("chunk_generator_type", new RegistryMaterials());
    IRegistry<Enchantment> ENCHANTMENT = a("enchantment", new RegistryMaterials());
    IRegistry<EntityTypes<?>> ENTITY_TYPE = a("entity_type", new RegistryMaterials());
    IRegistry<Item> ITEM = a("item", new RegistryMaterials());
    IRegistry<MobEffectList> MOB_EFFECT = a("mob_effect", new RegistryMaterials());
    IRegistry<Particle<? extends ParticleParam>> PARTICLE_TYPE = a("particle_type", new RegistryMaterials());
    IRegistry<SoundEffect> SOUND_EVENT = a("sound_event", new RegistryMaterials());
    IRegistry<StatisticWrapper<?>> STATS = a("stats", new RegistryMaterials());

    static <T> IRegistry<T> a(String s, IRegistry<T> iregistry) {
        f.a(new MinecraftKey(s), iregistry);
        return iregistry;
    }

    static void e() {
        // $FF: Couldn't be decompiled
    }

    @Nullable
    MinecraftKey getKey(T var1);

    T getOrDefault(@Nullable MinecraftKey var1);

    MinecraftKey b();

    int a(@Nullable T var1);

    @Nullable
    T fromId(int var1);

    Iterator<T> iterator();

    @Nullable
    T get(@Nullable MinecraftKey var1);

    void a(int var1, MinecraftKey var2, T var3);

    void a(MinecraftKey var1, T var2);

    Set<MinecraftKey> keySet();

    boolean d();

    @Nullable
    T a(Random var1);

    default Stream<T> f() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    boolean c(MinecraftKey var1);
}
