package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class LootEntityProperties {
    private static final Map<MinecraftKey, LootEntityProperty.a<?>> a = Maps.newHashMap();
    private static final Map<Class<? extends LootEntityProperty>, LootEntityProperty.a<?>> b = Maps.newHashMap();

    public static <T extends LootEntityProperty> void a(LootEntityProperty.a<? extends T> lootentityproperty$a) {
        MinecraftKey minecraftkey = lootentityproperty$a.a();
        Class oclass = lootentityproperty$a.b();
        if (a.containsKey(minecraftkey)) {
            throw new IllegalArgumentException("Can't re-register entity property name " + minecraftkey);
        } else if (b.containsKey(oclass)) {
            throw new IllegalArgumentException("Can't re-register entity property class " + oclass.getName());
        } else {
            a.put(minecraftkey, lootentityproperty$a);
            b.put(oclass, lootentityproperty$a);
        }
    }

    public static LootEntityProperty.a<?> a(MinecraftKey minecraftkey) {
        LootEntityProperty.a lootentityproperty$a = (LootEntityProperty.a)a.get(minecraftkey);
        if (lootentityproperty$a == null) {
            throw new IllegalArgumentException("Unknown loot entity property '" + minecraftkey + "'");
        } else {
            return lootentityproperty$a;
        }
    }

    public static <T extends LootEntityProperty> LootEntityProperty.a<T> a(T lootentityproperty) {
        LootEntityProperty.a lootentityproperty$a = (LootEntityProperty.a)b.get(lootentityproperty.getClass());
        if (lootentityproperty$a == null) {
            throw new IllegalArgumentException("Unknown loot entity property " + lootentityproperty);
        } else {
            return lootentityproperty$a;
        }
    }

    static {
        a(new LootEntityPropertyOnFire.a());
    }
}
