package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

public class LootTableInfo {
    private final float a;
    private final WorldServer b;
    private final LootTableRegistry c;
    @Nullable
    private final Entity d;
    @Nullable
    private final EntityHuman e;
    @Nullable
    private final DamageSource f;
    @Nullable
    private final BlockPosition g;
    private final Set<LootTable> h = Sets.newLinkedHashSet();

    public LootTableInfo(float fx, WorldServer worldserver, LootTableRegistry loottableregistry, @Nullable Entity entity, @Nullable EntityHuman entityhuman, @Nullable DamageSource damagesource, @Nullable BlockPosition blockposition) {
        this.a = fx;
        this.b = worldserver;
        this.c = loottableregistry;
        this.d = entity;
        this.e = entityhuman;
        this.f = damagesource;
        this.g = blockposition;
    }

    @Nullable
    public Entity a() {
        return this.d;
    }

    @Nullable
    public Entity b() {
        return this.e;
    }

    @Nullable
    public Entity c() {
        return this.f == null ? null : this.f.getEntity();
    }

    @Nullable
    public BlockPosition e() {
        return this.g;
    }

    public boolean a(LootTable loottable) {
        return this.h.add(loottable);
    }

    public void b(LootTable loottable) {
        this.h.remove(loottable);
    }

    public LootTableRegistry f() {
        return this.c;
    }

    public float g() {
        return this.a;
    }

    public WorldServer h() {
        return this.b;
    }

    @Nullable
    public Entity a(LootTableInfo.EntityTarget loottableinfo$entitytarget) {
        switch(loottableinfo$entitytarget) {
        case THIS:
            return this.a();
        case KILLER:
            return this.c();
        case KILLER_PLAYER:
            return this.b();
        default:
            return null;
        }
    }

    public static class Builder {
        private final WorldServer a;
        private float b;
        private Entity c;
        private EntityHuman d;
        private DamageSource e;
        private BlockPosition f;

        public Builder(WorldServer worldserver) {
            this.a = worldserver;
        }

        public LootTableInfo.Builder luck(float fx) {
            this.b = fx;
            return this;
        }

        public LootTableInfo.Builder entity(Entity entity) {
            this.c = entity;
            return this;
        }

        public LootTableInfo.Builder killer(EntityHuman entityhuman) {
            this.d = entityhuman;
            return this;
        }

        public LootTableInfo.Builder damageSource(DamageSource damagesource) {
            this.e = damagesource;
            return this;
        }

        public LootTableInfo.Builder position(BlockPosition blockposition) {
            this.f = blockposition;
            return this;
        }

        public LootTableInfo build() {
            return new LootTableInfo(this.b, this.a, this.a.getMinecraftServer().getLootTableRegistry(), this.c, this.d, this.e, this.f);
        }
    }

    public static enum EntityTarget {
        THIS("this"),
        KILLER("killer"),
        KILLER_PLAYER("killer_player");

        private final String d;

        private EntityTarget(String s1) {
            this.d = s1;
        }

        public static LootTableInfo.EntityTarget a(String s) {
            for(LootTableInfo.EntityTarget loottableinfo$entitytarget : values()) {
                if (loottableinfo$entitytarget.d.equals(s)) {
                    return loottableinfo$entitytarget;
                }
            }

            throw new IllegalArgumentException("Invalid entity target " + s);
        }

        // $FF: synthetic method
        static String a(LootTableInfo.EntityTarget loottableinfo$entitytarget) {
            return loottableinfo$entitytarget.d;
        }
    }
}
