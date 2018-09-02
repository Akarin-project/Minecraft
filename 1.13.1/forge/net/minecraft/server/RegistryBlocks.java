package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RegistryBlocks<V> extends RegistryMaterials<V> {
    private final MinecraftKey x;
    private V y;

    public RegistryBlocks(MinecraftKey minecraftkey) {
        this.x = minecraftkey;
    }

    public void a(int i, MinecraftKey minecraftkey, V object) {
        if (this.x.equals(minecraftkey)) {
            this.y = (V)object;
        }

        super.a(i, minecraftkey, object);
    }

    public int a(@Nullable V object) {
        int i = super.a(object);
        return i == -1 ? super.a(this.y) : i;
    }

    public MinecraftKey getKey(V object) {
        MinecraftKey minecraftkey = super.getKey(object);
        return minecraftkey == null ? this.x : minecraftkey;
    }

    public V getOrDefault(@Nullable MinecraftKey minecraftkey) {
        Object object = this.get(minecraftkey);
        return (V)(object == null ? this.y : object);
    }

    @Nonnull
    public V fromId(int i) {
        Object object = super.fromId(i);
        return (V)(object == null ? this.y : object);
    }

    @Nonnull
    public V a(Random random) {
        Object object = super.a(random);
        return (V)(object == null ? this.y : object);
    }

    public MinecraftKey b() {
        return this.x;
    }
}
