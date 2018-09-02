package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataConverterSchemaV99 extends Schema {
    private static final Logger b = LogManager.getLogger();
    private static final Map<String, String> c = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        hashmap.put("minecraft:furnace", "Furnace");
        hashmap.put("minecraft:lit_furnace", "Furnace");
        hashmap.put("minecraft:chest", "Chest");
        hashmap.put("minecraft:trapped_chest", "Chest");
        hashmap.put("minecraft:ender_chest", "EnderChest");
        hashmap.put("minecraft:jukebox", "RecordPlayer");
        hashmap.put("minecraft:dispenser", "Trap");
        hashmap.put("minecraft:dropper", "Dropper");
        hashmap.put("minecraft:sign", "Sign");
        hashmap.put("minecraft:mob_spawner", "MobSpawner");
        hashmap.put("minecraft:noteblock", "Music");
        hashmap.put("minecraft:brewing_stand", "Cauldron");
        hashmap.put("minecraft:enhanting_table", "EnchantTable");
        hashmap.put("minecraft:command_block", "CommandBlock");
        hashmap.put("minecraft:beacon", "Beacon");
        hashmap.put("minecraft:skull", "Skull");
        hashmap.put("minecraft:daylight_detector", "DLDetector");
        hashmap.put("minecraft:hopper", "Hopper");
        hashmap.put("minecraft:banner", "Banner");
        hashmap.put("minecraft:flower_pot", "FlowerPot");
        hashmap.put("minecraft:repeating_command_block", "CommandBlock");
        hashmap.put("minecraft:chain_command_block", "CommandBlock");
        hashmap.put("minecraft:standing_sign", "Sign");
        hashmap.put("minecraft:wall_sign", "Sign");
        hashmap.put("minecraft:piston_head", "Piston");
        hashmap.put("minecraft:daylight_detector_inverted", "DLDetector");
        hashmap.put("minecraft:unpowered_comparator", "Comparator");
        hashmap.put("minecraft:powered_comparator", "Comparator");
        hashmap.put("minecraft:wall_banner", "Banner");
        hashmap.put("minecraft:standing_banner", "Banner");
        hashmap.put("minecraft:structure_block", "Structure");
        hashmap.put("minecraft:end_portal", "Airportal");
        hashmap.put("minecraft:end_gateway", "EndGateway");
        hashmap.put("minecraft:shield", "Banner");
    });
    protected static final HookFunction a = new HookFunction() {
        public <T> T apply(DynamicOps<T> dynamicops, T object) {
            return (T)DataConverterSchemaV99.a(new Dynamic(dynamicops, object), DataConverterSchemaV99.c, "ArmorStand");
        }
    };

    public DataConverterSchemaV99(int i, Schema schema) {
        super(i, schema);
    }

    protected static TypeTemplate a(Schema schema) {
        return DSL.optionalFields("Equipment", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
    }

    protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String s) {
        schema.register(map, s, () -> {
            return a(schema);
        });
    }

    protected static void b(Schema schema, Map<String, Supplier<TypeTemplate>> map, String s) {
        schema.register(map, s, () -> {
            return DSL.optionalFields("inTile", DataConverterTypes.p.in(schema));
        });
    }

    protected static void c(Schema schema, Map<String, Supplier<TypeTemplate>> map, String s) {
        schema.register(map, s, () -> {
            return DSL.optionalFields("DisplayTile", DataConverterTypes.p.in(schema));
        });
    }

    protected static void d(Schema schema, Map<String, Supplier<TypeTemplate>> map, String s) {
        schema.register(map, s, () -> {
            return DSL.optionalFields("Items", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
        });
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap hashmap = Maps.newHashMap();
        schema.register(hashmap, "Item", (var1) -> {
            return DSL.optionalFields("Item", DataConverterTypes.ITEM_STACK.in(schema));
        });
        schema.registerSimple(hashmap, "XPOrb");
        b(schema, hashmap, "ThrownEgg");
        schema.registerSimple(hashmap, "LeashKnot");
        schema.registerSimple(hashmap, "Painting");
        schema.register(hashmap, "Arrow", (var1) -> {
            return DSL.optionalFields("inTile", DataConverterTypes.p.in(schema));
        });
        schema.register(hashmap, "TippedArrow", (var1) -> {
            return DSL.optionalFields("inTile", DataConverterTypes.p.in(schema));
        });
        schema.register(hashmap, "SpectralArrow", (var1) -> {
            return DSL.optionalFields("inTile", DataConverterTypes.p.in(schema));
        });
        b(schema, hashmap, "Snowball");
        b(schema, hashmap, "Fireball");
        b(schema, hashmap, "SmallFireball");
        b(schema, hashmap, "ThrownEnderpearl");
        schema.registerSimple(hashmap, "EyeOfEnderSignal");
        schema.register(hashmap, "ThrownPotion", (var1) -> {
            return DSL.optionalFields("inTile", DataConverterTypes.p.in(schema), "Potion", DataConverterTypes.ITEM_STACK.in(schema));
        });
        b(schema, hashmap, "ThrownExpBottle");
        schema.register(hashmap, "ItemFrame", (var1) -> {
            return DSL.optionalFields("Item", DataConverterTypes.ITEM_STACK.in(schema));
        });
        b(schema, hashmap, "WitherSkull");
        schema.registerSimple(hashmap, "PrimedTnt");
        schema.register(hashmap, "FallingSand", (var1) -> {
            return DSL.optionalFields("Block", DataConverterTypes.p.in(schema), "TileEntityData", DataConverterTypes.j.in(schema));
        });
        schema.register(hashmap, "FireworksRocketEntity", (var1) -> {
            return DSL.optionalFields("FireworksItem", DataConverterTypes.ITEM_STACK.in(schema));
        });
        schema.registerSimple(hashmap, "Boat");
        schema.register(hashmap, "Minecart", () -> {
            return DSL.optionalFields("DisplayTile", DataConverterTypes.p.in(schema), "Items", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
        });
        c(schema, hashmap, "MinecartRideable");
        schema.register(hashmap, "MinecartChest", (var1) -> {
            return DSL.optionalFields("DisplayTile", DataConverterTypes.p.in(schema), "Items", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
        });
        c(schema, hashmap, "MinecartFurnace");
        c(schema, hashmap, "MinecartTNT");
        schema.register(hashmap, "MinecartSpawner", () -> {
            return DSL.optionalFields("DisplayTile", DataConverterTypes.p.in(schema), DataConverterTypes.r.in(schema));
        });
        schema.register(hashmap, "MinecartHopper", (var1) -> {
            return DSL.optionalFields("DisplayTile", DataConverterTypes.p.in(schema), "Items", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
        });
        c(schema, hashmap, "MinecartCommandBlock");
        a(schema, hashmap, "ArmorStand");
        a(schema, hashmap, "Creeper");
        a(schema, hashmap, "Skeleton");
        a(schema, hashmap, "Spider");
        a(schema, hashmap, "Giant");
        a(schema, hashmap, "Zombie");
        a(schema, hashmap, "Slime");
        a(schema, hashmap, "Ghast");
        a(schema, hashmap, "PigZombie");
        schema.register(hashmap, "Enderman", (var1) -> {
            return DSL.optionalFields("carried", DataConverterTypes.p.in(schema), a(schema));
        });
        a(schema, hashmap, "CaveSpider");
        a(schema, hashmap, "Silverfish");
        a(schema, hashmap, "Blaze");
        a(schema, hashmap, "LavaSlime");
        a(schema, hashmap, "EnderDragon");
        a(schema, hashmap, "WitherBoss");
        a(schema, hashmap, "Bat");
        a(schema, hashmap, "Witch");
        a(schema, hashmap, "Endermite");
        a(schema, hashmap, "Guardian");
        a(schema, hashmap, "Pig");
        a(schema, hashmap, "Sheep");
        a(schema, hashmap, "Cow");
        a(schema, hashmap, "Chicken");
        a(schema, hashmap, "Squid");
        a(schema, hashmap, "Wolf");
        a(schema, hashmap, "MushroomCow");
        a(schema, hashmap, "SnowMan");
        a(schema, hashmap, "Ozelot");
        a(schema, hashmap, "VillagerGolem");
        schema.register(hashmap, "EntityHorse", (var1) -> {
            return DSL.optionalFields("Items", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)), "ArmorItem", DataConverterTypes.ITEM_STACK.in(schema), "SaddleItem", DataConverterTypes.ITEM_STACK.in(schema), a(schema));
        });
        a(schema, hashmap, "Rabbit");
        schema.register(hashmap, "Villager", (var1) -> {
            return DSL.optionalFields("Inventory", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", DataConverterTypes.ITEM_STACK.in(schema), "buyB", DataConverterTypes.ITEM_STACK.in(schema), "sell", DataConverterTypes.ITEM_STACK.in(schema)))), a(schema));
        });
        schema.registerSimple(hashmap, "EnderCrystal");
        schema.registerSimple(hashmap, "AreaEffectCloud");
        schema.registerSimple(hashmap, "ShulkerBullet");
        a(schema, hashmap, "Shulker");
        return hashmap;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap hashmap = Maps.newHashMap();
        d(schema, hashmap, "Furnace");
        d(schema, hashmap, "Chest");
        schema.registerSimple(hashmap, "EnderChest");
        schema.register(hashmap, "RecordPlayer", (var1) -> {
            return DSL.optionalFields("RecordItem", DataConverterTypes.ITEM_STACK.in(schema));
        });
        d(schema, hashmap, "Trap");
        d(schema, hashmap, "Dropper");
        schema.registerSimple(hashmap, "Sign");
        schema.register(hashmap, "MobSpawner", (var1) -> {
            return DataConverterTypes.r.in(schema);
        });
        schema.registerSimple(hashmap, "Music");
        schema.registerSimple(hashmap, "Piston");
        d(schema, hashmap, "Cauldron");
        schema.registerSimple(hashmap, "EnchantTable");
        schema.registerSimple(hashmap, "Airportal");
        schema.registerSimple(hashmap, "Control");
        schema.registerSimple(hashmap, "Beacon");
        schema.registerSimple(hashmap, "Skull");
        schema.registerSimple(hashmap, "DLDetector");
        d(schema, hashmap, "Hopper");
        schema.registerSimple(hashmap, "Comparator");
        schema.register(hashmap, "FlowerPot", (var1) -> {
            return DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), DataConverterTypes.q.in(schema)));
        });
        schema.registerSimple(hashmap, "Banner");
        schema.registerSimple(hashmap, "Structure");
        schema.registerSimple(hashmap, "EndGateway");
        return hashmap;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map1) {
        schema.registerType(false, DataConverterTypes.a, DSL::remainder);
        schema.registerType(false, DataConverterTypes.b, () -> {
            return DSL.optionalFields("Inventory", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)), "EnderItems", DSL.list(DataConverterTypes.ITEM_STACK.in(schema)));
        });
        schema.registerType(false, DataConverterTypes.c, () -> {
            return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(DataConverterTypes.n.in(schema)), "TileEntities", DSL.list(DataConverterTypes.j.in(schema)), "TileTicks", DSL.list(DSL.fields("i", DataConverterTypes.p.in(schema)))));
        });
        schema.registerType(true, DataConverterTypes.j, () -> {
            return DSL.taggedChoiceLazy("id", DSL.string(), map1);
        });
        schema.registerType(true, DataConverterTypes.n, () -> {
            return DSL.optionalFields("Riding", DataConverterTypes.n.in(schema), DataConverterTypes.ENTITY.in(schema));
        });
        schema.registerType(false, DataConverterTypes.m, () -> {
            return DSL.constType(DSL.namespacedString());
        });
        schema.registerType(true, DataConverterTypes.ENTITY, () -> {
            return DSL.taggedChoiceLazy("id", DSL.string(), map);
        });
        schema.registerType(true, DataConverterTypes.ITEM_STACK, () -> {
            return DSL.hook(DSL.optionalFields("id", DSL.or(DSL.constType(DSL.intType()), DataConverterTypes.q.in(schema)), "tag", DSL.optionalFields("EntityTag", DataConverterTypes.n.in(schema), "BlockEntityTag", DataConverterTypes.j.in(schema), "CanDestroy", DSL.list(DataConverterTypes.p.in(schema)), "CanPlaceOn", DSL.list(DataConverterTypes.p.in(schema)))), a, HookFunction.IDENTITY);
        });
        schema.registerType(false, DataConverterTypes.e, DSL::remainder);
        schema.registerType(false, DataConverterTypes.p, () -> {
            return DSL.or(DSL.constType(DSL.intType()), DSL.constType(DSL.namespacedString()));
        });
        schema.registerType(false, DataConverterTypes.q, () -> {
            return DSL.constType(DSL.namespacedString());
        });
        schema.registerType(false, DataConverterTypes.g, DSL::remainder);
        schema.registerType(false, DataConverterTypes.h, () -> {
            return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(DataConverterTypes.s.in(schema)), "Objectives", DSL.list(DataConverterTypes.t.in(schema)), "Teams", DSL.list(DataConverterTypes.u.in(schema))));
        });
        schema.registerType(false, DataConverterTypes.s, DSL::remainder);
        schema.registerType(false, DataConverterTypes.t, DSL::remainder);
        schema.registerType(false, DataConverterTypes.u, DSL::remainder);
        schema.registerType(true, DataConverterTypes.r, DSL::remainder);
    }

    protected static <T> T a(Dynamic<T> dynamic, Map<String, String> map, String s) {
        return (T)dynamic.update("tag", (dynamic2) -> {
            return dynamic2.update("BlockEntityTag", (dynamic4) -> {
                String s2 = dynamic.getString("id");
                String s3 = (String)map.get(DataConverterSchemaNamed.a(s2));
                if (s3 == null) {
                    b.warn("Unable to resolve BlockEntity for ItemStack: {}", s2);
                    return dynamic4;
                } else {
                    return dynamic4.set("id", dynamic.createString(s3));
                }
            }).update("EntityTag", (dynamic4) -> {
                String s3 = dynamic.getString("id");
                return Objects.equals(DataConverterSchemaNamed.a(s3), "minecraft:armor_stand") ? dynamic4.set("id", dynamic.createString(s)) : dynamic4;
            });
        }).getValue();
    }
}
