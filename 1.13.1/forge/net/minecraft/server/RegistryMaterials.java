package net.minecraft.server;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryMaterials<V> implements IRegistry<V> {
    protected static final Logger a = LogManager.getLogger();
    protected final RegistryID<V> b = new RegistryID<V>(256);
    protected final BiMap<MinecraftKey, V> c = HashBiMap.create();
    protected Object[] d;
    private int x;

    public RegistryMaterials() {
    }

    public void a(int i, MinecraftKey minecraftkey, V object) {
        this.b.a((V)object, i);
        Validate.notNull(minecraftkey);
        Validate.notNull(object);
        this.d = null;
        if (this.c.containsKey(minecraftkey)) {
            a.debug("Adding duplicate key '{}' to registry", minecraftkey);
        }

        this.c.put(minecraftkey, object);
        if (this.x <= i) {
            this.x = i + 1;
        }

    }

    public void a(MinecraftKey minecraftkey, V object) {
        this.a(this.x, minecraftkey, object);
    }

    @Nullable
    public MinecraftKey getKey(V object) {
        return (MinecraftKey)this.c.inverse().get(object);
    }

    public V getOrDefault(@Nullable MinecraftKey var1) {
        throw new UnsupportedOperationException("No default value");
    }

    public MinecraftKey b() {
        throw new UnsupportedOperationException("No default key");
    }

    public int a(@Nullable V object) {
        return this.b.getId((V)object);
    }

    @Nullable
    public V fromId(int i) {
        return this.b.fromId(i);
    }

    public Iterator<V> iterator() {
        return this.b.iterator();
    }

    @Nullable
    public V get(@Nullable MinecraftKey minecraftkey) {
        return (V)this.c.get(minecraftkey);
    }

    public Set<MinecraftKey> keySet() {
        return Collections.unmodifiableSet(this.c.keySet());
    }

    public boolean d() {
        return this.c.isEmpty();
    }

    @Nullable
    public V a(Random random) {
        if (this.d == null) {
            Set set = this.c.values();
            if (set.isEmpty()) {
                return (V)null;
            }

            this.d = set.toArray(new Object[set.size()]);
        }

        return (V)this.d[random.nextInt(this.d.length)];
    }

    public boolean c(MinecraftKey minecraftkey) {
        return this.c.containsKey(minecraftkey);
    }
}
