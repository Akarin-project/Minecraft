package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkConverterPalette extends DataFix {
    private static final Logger a = LogManager.getLogger();
    private static final BitSet b = new BitSet(256);
    private static final BitSet c = new BitSet(256);
    private static final Dynamic<?> d = DataConverterFlattenData.b("{Name:'minecraft:pumpkin'}");
    private static final Dynamic<?> e = DataConverterFlattenData.b("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
    private static final Dynamic<?> f = DataConverterFlattenData.b("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
    private static final Dynamic<?> g = DataConverterFlattenData.b("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
    private static final Dynamic<?> h = DataConverterFlattenData.b("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
    private static final Dynamic<?> i = DataConverterFlattenData.b("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
    private static final Dynamic<?> j = DataConverterFlattenData.b("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
    private static final Dynamic<?> k = DataConverterFlattenData.b("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
    private static final Dynamic<?> l = DataConverterFlattenData.b("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
    private static final Dynamic<?> m = DataConverterFlattenData.b("{Name:'minecraft:peony',Properties:{half:'upper'}}");
    private static final Map<String, Dynamic<?>> n = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        hashmap.put("minecraft:air0", DataConverterFlattenData.b("{Name:'minecraft:flower_pot'}"));
        hashmap.put("minecraft:red_flower0", DataConverterFlattenData.b("{Name:'minecraft:potted_poppy'}"));
        hashmap.put("minecraft:red_flower1", DataConverterFlattenData.b("{Name:'minecraft:potted_blue_orchid'}"));
        hashmap.put("minecraft:red_flower2", DataConverterFlattenData.b("{Name:'minecraft:potted_allium'}"));
        hashmap.put("minecraft:red_flower3", DataConverterFlattenData.b("{Name:'minecraft:potted_azure_bluet'}"));
        hashmap.put("minecraft:red_flower4", DataConverterFlattenData.b("{Name:'minecraft:potted_red_tulip'}"));
        hashmap.put("minecraft:red_flower5", DataConverterFlattenData.b("{Name:'minecraft:potted_orange_tulip'}"));
        hashmap.put("minecraft:red_flower6", DataConverterFlattenData.b("{Name:'minecraft:potted_white_tulip'}"));
        hashmap.put("minecraft:red_flower7", DataConverterFlattenData.b("{Name:'minecraft:potted_pink_tulip'}"));
        hashmap.put("minecraft:red_flower8", DataConverterFlattenData.b("{Name:'minecraft:potted_oxeye_daisy'}"));
        hashmap.put("minecraft:yellow_flower0", DataConverterFlattenData.b("{Name:'minecraft:potted_dandelion'}"));
        hashmap.put("minecraft:sapling0", DataConverterFlattenData.b("{Name:'minecraft:potted_oak_sapling'}"));
        hashmap.put("minecraft:sapling1", DataConverterFlattenData.b("{Name:'minecraft:potted_spruce_sapling'}"));
        hashmap.put("minecraft:sapling2", DataConverterFlattenData.b("{Name:'minecraft:potted_birch_sapling'}"));
        hashmap.put("minecraft:sapling3", DataConverterFlattenData.b("{Name:'minecraft:potted_jungle_sapling'}"));
        hashmap.put("minecraft:sapling4", DataConverterFlattenData.b("{Name:'minecraft:potted_acacia_sapling'}"));
        hashmap.put("minecraft:sapling5", DataConverterFlattenData.b("{Name:'minecraft:potted_dark_oak_sapling'}"));
        hashmap.put("minecraft:red_mushroom0", DataConverterFlattenData.b("{Name:'minecraft:potted_red_mushroom'}"));
        hashmap.put("minecraft:brown_mushroom0", DataConverterFlattenData.b("{Name:'minecraft:potted_brown_mushroom'}"));
        hashmap.put("minecraft:deadbush0", DataConverterFlattenData.b("{Name:'minecraft:potted_dead_bush'}"));
        hashmap.put("minecraft:tallgrass2", DataConverterFlattenData.b("{Name:'minecraft:potted_fern'}"));
        hashmap.put("minecraft:cactus0", DataConverterFlattenData.b(2240));
    });
    private static final Map<String, Dynamic<?>> o = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        a(hashmap, 0, "skeleton", "skull");
        a(hashmap, 1, "wither_skeleton", "skull");
        a(hashmap, 2, "zombie", "head");
        a(hashmap, 3, "player", "head");
        a(hashmap, 4, "creeper", "head");
        a(hashmap, 5, "dragon", "head");
    });
    private static final Map<String, Dynamic<?>> p = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        a(hashmap, "oak_door", 1024);
        a(hashmap, "iron_door", 1136);
        a(hashmap, "spruce_door", 3088);
        a(hashmap, "birch_door", 3104);
        a(hashmap, "jungle_door", 3120);
        a(hashmap, "acacia_door", 3136);
        a(hashmap, "dark_oak_door", 3152);
    });
    private static final Map<String, Dynamic<?>> q = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        for(int ix = 0; ix < 26; ++ix) {
            hashmap.put("true" + ix, DataConverterFlattenData.b("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + ix + "'}}"));
            hashmap.put("false" + ix, DataConverterFlattenData.b("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + ix + "'}}"));
        }

    });
    private static final Int2ObjectMap<String> r = (Int2ObjectMap)DataFixUtils.make(new Int2ObjectOpenHashMap(), (int2objectopenhashmap) -> {
        int2objectopenhashmap.put(0, "white");
        int2objectopenhashmap.put(1, "orange");
        int2objectopenhashmap.put(2, "magenta");
        int2objectopenhashmap.put(3, "light_blue");
        int2objectopenhashmap.put(4, "yellow");
        int2objectopenhashmap.put(5, "lime");
        int2objectopenhashmap.put(6, "pink");
        int2objectopenhashmap.put(7, "gray");
        int2objectopenhashmap.put(8, "light_gray");
        int2objectopenhashmap.put(9, "cyan");
        int2objectopenhashmap.put(10, "purple");
        int2objectopenhashmap.put(11, "blue");
        int2objectopenhashmap.put(12, "brown");
        int2objectopenhashmap.put(13, "green");
        int2objectopenhashmap.put(14, "red");
        int2objectopenhashmap.put(15, "black");
    });
    private static final Map<String, Dynamic<?>> s = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        ObjectIterator objectiterator = r.int2ObjectEntrySet().iterator();

        while(objectiterator.hasNext()) {
            Entry entry = (Entry)objectiterator.next();
            if (!Objects.equals(entry.getValue(), "red")) {
                a(hashmap, entry.getIntKey(), (String)entry.getValue());
            }
        }

    });
    private static final Map<String, Dynamic<?>> t = (Map)DataFixUtils.make(Maps.newHashMap(), (hashmap) -> {
        ObjectIterator objectiterator = r.int2ObjectEntrySet().iterator();

        while(objectiterator.hasNext()) {
            Entry entry = (Entry)objectiterator.next();
            if (!Objects.equals(entry.getValue(), "white")) {
                b(hashmap, 15 - entry.getIntKey(), (String)entry.getValue());
            }
        }

    });
    private static final Dynamic<?> u = DataConverterFlattenData.b(0);

    public ChunkConverterPalette(Schema schema, boolean flag) {
        super(schema, flag);
    }

    private static void a(Map<String, Dynamic<?>> map, int ix, String sx, String s1) {
        map.put(ix + "north", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_" + s1 + "',Properties:{facing:'north'}}"));
        map.put(ix + "east", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_" + s1 + "',Properties:{facing:'east'}}"));
        map.put(ix + "south", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_" + s1 + "',Properties:{facing:'south'}}"));
        map.put(ix + "west", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_" + s1 + "',Properties:{facing:'west'}}"));

        for(int jx = 0; jx < 16; ++jx) {
            map.put(ix + "" + jx, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_" + s1 + "',Properties:{rotation:'" + jx + "'}}"));
        }

    }

    private static void a(Map<String, Dynamic<?>> map, String sx, int ix) {
        map.put("minecraft:" + sx + "eastlowerleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "eastlowerleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "eastlowerlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "eastlowerlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "eastlowerrightfalsefalse", DataConverterFlattenData.b(ix));
        map.put("minecraft:" + sx + "eastlowerrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "eastlowerrighttruefalse", DataConverterFlattenData.b(ix + 4));
        map.put("minecraft:" + sx + "eastlowerrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "eastupperleftfalsefalse", DataConverterFlattenData.b(ix + 8));
        map.put("minecraft:" + sx + "eastupperleftfalsetrue", DataConverterFlattenData.b(ix + 10));
        map.put("minecraft:" + sx + "eastupperlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "eastupperlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "eastupperrightfalsefalse", DataConverterFlattenData.b(ix + 9));
        map.put("minecraft:" + sx + "eastupperrightfalsetrue", DataConverterFlattenData.b(ix + 11));
        map.put("minecraft:" + sx + "eastupperrighttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "eastupperrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "northlowerleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "northlowerleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "northlowerlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "northlowerlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "northlowerrightfalsefalse", DataConverterFlattenData.b(ix + 3));
        map.put("minecraft:" + sx + "northlowerrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "northlowerrighttruefalse", DataConverterFlattenData.b(ix + 7));
        map.put("minecraft:" + sx + "northlowerrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "northupperleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "northupperleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "northupperlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "northupperlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "northupperrightfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "northupperrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "northupperrighttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "northupperrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "southlowerleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "southlowerleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "southlowerlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "southlowerlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "southlowerrightfalsefalse", DataConverterFlattenData.b(ix + 1));
        map.put("minecraft:" + sx + "southlowerrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "southlowerrighttruefalse", DataConverterFlattenData.b(ix + 5));
        map.put("minecraft:" + sx + "southlowerrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "southupperleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "southupperleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "southupperlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "southupperlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "southupperrightfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "southupperrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "southupperrighttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "southupperrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "westlowerleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "westlowerleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "westlowerlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "westlowerlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "westlowerrightfalsefalse", DataConverterFlattenData.b(ix + 2));
        map.put("minecraft:" + sx + "westlowerrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "westlowerrighttruefalse", DataConverterFlattenData.b(ix + 6));
        map.put("minecraft:" + sx + "westlowerrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "westupperleftfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "westupperleftfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "westupperlefttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "westupperlefttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + sx + "westupperrightfalsefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + sx + "westupperrightfalsetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + sx + "westupperrighttruefalse", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + sx + "westupperrighttruetrue", DataConverterFlattenData.b("{Name:'minecraft:" + sx + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
    }

    private static void a(Map<String, Dynamic<?>> map, int ix, String sx) {
        map.put("southfalsefoot" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
        map.put("westfalsefoot" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
        map.put("northfalsefoot" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
        map.put("eastfalsefoot" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
        map.put("southfalsehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
        map.put("westfalsehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
        map.put("northfalsehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
        map.put("eastfalsehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
        map.put("southtruehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
        map.put("westtruehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
        map.put("northtruehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
        map.put("easttruehead" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
    }

    private static void b(Map<String, Dynamic<?>> map, int ix, String sx) {
        for(int jx = 0; jx < 16; ++jx) {
            map.put("" + jx + "_" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_banner',Properties:{rotation:'" + jx + "'}}"));
        }

        map.put("north_" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_banner',Properties:{facing:'north'}}"));
        map.put("south_" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_banner',Properties:{facing:'south'}}"));
        map.put("west_" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_banner',Properties:{facing:'west'}}"));
        map.put("east_" + ix, DataConverterFlattenData.b("{Name:'minecraft:" + sx + "_wall_banner',Properties:{facing:'east'}}"));
    }

    public static String a(Dynamic<?> dynamic) {
        return dynamic.getString("Name");
    }

    public static String a(Dynamic<?> dynamic, String sx) {
        return (String)dynamic.get("Properties").map((dynamic1) -> {
            return dynamic1.getString(sx);
        }).orElse("");
    }

    public static int a(RegistryID<Dynamic<?>> registryid, Dynamic<?> dynamic) {
        int ix = registryid.getId(dynamic);
        if (ix == -1) {
            ix = registryid.c(dynamic);
        }

        return ix;
    }

    private Dynamic<?> b(Dynamic<?> dynamic) {
        Optional optional = dynamic.get("Level");
        return optional.isPresent() && ((Dynamic)optional.get()).get("Sections").flatMap(Dynamic::getStream).isPresent() ? dynamic.set("Level", (new ChunkConverterPalette.d((Dynamic)optional.get())).a()) : dynamic;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.c);
        Type type1 = this.getOutputSchema().getType(DataConverterTypes.c);
        return this.writeFixAndRead("ChunkPalettedStorageFix", type, type1, this::b);
    }

    public static int a(boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        int ix = 0;
        if (flag2) {
            if (flag1) {
                ix |= 2;
            } else if (flag) {
                ix |= 128;
            } else {
                ix |= 1;
            }
        } else if (flag3) {
            if (flag) {
                ix |= 32;
            } else if (flag1) {
                ix |= 8;
            } else {
                ix |= 16;
            }
        } else if (flag1) {
            ix |= 4;
        } else if (flag) {
            ix |= 64;
        }

        return ix;
    }

    static {
        c.set(2);
        c.set(3);
        c.set(110);
        c.set(140);
        c.set(144);
        c.set(25);
        c.set(86);
        c.set(26);
        c.set(176);
        c.set(177);
        c.set(175);
        c.set(64);
        c.set(71);
        c.set(193);
        c.set(194);
        c.set(195);
        c.set(196);
        c.set(197);
        b.set(54);
        b.set(146);
        b.set(25);
        b.set(26);
        b.set(51);
        b.set(53);
        b.set(67);
        b.set(108);
        b.set(109);
        b.set(114);
        b.set(128);
        b.set(134);
        b.set(135);
        b.set(136);
        b.set(156);
        b.set(163);
        b.set(164);
        b.set(180);
        b.set(203);
        b.set(55);
        b.set(85);
        b.set(113);
        b.set(188);
        b.set(189);
        b.set(190);
        b.set(191);
        b.set(192);
        b.set(93);
        b.set(94);
        b.set(101);
        b.set(102);
        b.set(160);
        b.set(106);
        b.set(107);
        b.set(183);
        b.set(184);
        b.set(185);
        b.set(186);
        b.set(187);
        b.set(132);
        b.set(139);
        b.set(199);
    }

    public static enum Direction {
        DOWN(ChunkConverterPalette.Direction.AxisDirection.NEGATIVE, ChunkConverterPalette.Direction.Axis.Y),
        UP(ChunkConverterPalette.Direction.AxisDirection.POSITIVE, ChunkConverterPalette.Direction.Axis.Y),
        NORTH(ChunkConverterPalette.Direction.AxisDirection.NEGATIVE, ChunkConverterPalette.Direction.Axis.Z),
        SOUTH(ChunkConverterPalette.Direction.AxisDirection.POSITIVE, ChunkConverterPalette.Direction.Axis.Z),
        WEST(ChunkConverterPalette.Direction.AxisDirection.NEGATIVE, ChunkConverterPalette.Direction.Axis.X),
        EAST(ChunkConverterPalette.Direction.AxisDirection.POSITIVE, ChunkConverterPalette.Direction.Axis.X);

        private final ChunkConverterPalette.Direction.Axis g;
        private final ChunkConverterPalette.Direction.AxisDirection h;

        private Direction(ChunkConverterPalette.Direction.AxisDirection chunkconverterpalette$direction$axisdirection, ChunkConverterPalette.Direction.Axis chunkconverterpalette$direction$axis) {
            this.g = chunkconverterpalette$direction$axis;
            this.h = chunkconverterpalette$direction$axisdirection;
        }

        public ChunkConverterPalette.Direction.AxisDirection a() {
            return this.h;
        }

        public ChunkConverterPalette.Direction.Axis b() {
            return this.g;
        }

        public static enum Axis {
            X,
            Y,
            Z;

            private Axis() {
            }
        }

        public static enum AxisDirection {
            POSITIVE(1),
            NEGATIVE(-1);

            private final int c;

            private AxisDirection(int j) {
                this.c = j;
            }

            public int a() {
                return this.c;
            }
        }
    }

    static class a {
        private final byte[] a;

        public a() {
            this.a = new byte[2048];
        }

        public a(byte[] abyte) {
            this.a = abyte;
            if (abyte.length != 2048) {
                throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + abyte.length);
            }
        }

        public int a(int i, int j, int k) {
            int l = this.b(j << 8 | k << 4 | i);
            return this.a(j << 8 | k << 4 | i) ? this.a[l] & 15 : this.a[l] >> 4 & 15;
        }

        private boolean a(int i) {
            return (i & 1) == 0;
        }

        private int b(int i) {
            return i >> 1;
        }
    }

    static class c {
        private final RegistryID<Dynamic<?>> b = new RegistryID<Dynamic<?>>(32);
        private Dynamic<?> c;
        private final Dynamic<?> d;
        private final boolean e;
        private final Int2ObjectMap<IntList> f = new Int2ObjectLinkedOpenHashMap();
        private final IntList g = new IntArrayList();
        public int a;
        private final Set<Dynamic<?>> h = Sets.newIdentityHashSet();
        private final int[] i = new int[4096];

        public c(Dynamic<?> dynamic) {
            this.c = dynamic.emptyList();
            this.d = dynamic;
            this.a = dynamic.getInt("Y");
            this.e = dynamic.get("Blocks").isPresent();
        }

        public Dynamic<?> a(int ix) {
            if (ix >= 0 && ix <= 4095) {
                Dynamic dynamic = this.b.fromId(this.i[ix]);
                return dynamic == null ? ChunkConverterPalette.u : dynamic;
            } else {
                return ChunkConverterPalette.u;
            }
        }

        public void a(int ix, Dynamic<?> dynamic) {
            if (this.h.add(dynamic)) {
                this.c = this.c.merge("%%FILTER_ME%%".equals(ChunkConverterPalette.a(dynamic)) ? ChunkConverterPalette.u : dynamic);
            }

            this.i[ix] = ChunkConverterPalette.a(this.b, dynamic);
        }

        public int b(int ix) {
            if (!this.e) {
                return ix;
            } else {
                ByteBuffer bytebuffer = (ByteBuffer)this.d.get("Blocks").flatMap(Dynamic::getByteBuffer).get();
                ChunkConverterPalette.a chunkconverterpalette$a = (ChunkConverterPalette.a)this.d.get("Data").flatMap(Dynamic::getByteBuffer).map((bytebuffer1) -> {
                    return new ChunkConverterPalette.a(DataFixUtils.toArray(bytebuffer1));
                }).orElseGet(ChunkConverterPalette.a::new);
                ChunkConverterPalette.a chunkconverterpalette$a1 = (ChunkConverterPalette.a)this.d.get("Add").flatMap(Dynamic::getByteBuffer).map((bytebuffer1) -> {
                    return new ChunkConverterPalette.a(DataFixUtils.toArray(bytebuffer1));
                }).orElseGet(ChunkConverterPalette.a::new);
                this.h.add(ChunkConverterPalette.u);
                ChunkConverterPalette.a(this.b, ChunkConverterPalette.u);
                this.c = this.c.merge(ChunkConverterPalette.u);

                for(int j = 0; j < 4096; ++j) {
                    int k = j & 15;
                    int l = j >> 8 & 15;
                    int i1 = j >> 4 & 15;
                    int j1 = chunkconverterpalette$a1.a(k, l, i1) << 12 | (bytebuffer.get(j) & 255) << 4 | chunkconverterpalette$a.a(k, l, i1);
                    if (ChunkConverterPalette.c.get(j1 >> 4)) {
                        this.a(j1 >> 4, j);
                    }

                    if (ChunkConverterPalette.b.get(j1 >> 4)) {
                        int k1 = ChunkConverterPalette.a(k == 0, k == 15, i1 == 0, i1 == 15);
                        if (k1 == 0) {
                            this.g.add(j);
                        } else {
                            ix |= k1;
                        }
                    }

                    this.a(j, DataConverterFlattenData.b(j1));
                }

                return ix;
            }
        }

        private void a(int ix, int j) {
            Object object = (IntList)this.f.get(ix);
            if (object == null) {
                object = new IntArrayList();
                this.f.put(ix, object);
            }

            ((IntList)object).add(j);
        }

        public Dynamic<?> a() {
            Dynamic dynamic = this.d;
            if (!this.e) {
                return dynamic;
            } else {
                dynamic = dynamic.set("Palette", this.c);
                int ix = Math.max(4, DataFixUtils.ceillog2(this.h.size()));
                DataBits databits = new DataBits(ix, 4096);

                for(int j = 0; j < this.i.length; ++j) {
                    databits.a(j, this.i[j]);
                }

                dynamic = dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(databits.a())));
                dynamic = dynamic.remove("Blocks");
                dynamic = dynamic.remove("Data");
                dynamic = dynamic.remove("Add");
                return dynamic;
            }
        }
    }

    static final class d {
        private int a;
        private final ChunkConverterPalette.c[] b = new ChunkConverterPalette.c[16];
        private final Dynamic<?> c;
        private final int d;
        private final int e;
        private final Int2ObjectMap<Dynamic<?>> f = new Int2ObjectLinkedOpenHashMap(16);

        public d(Dynamic<?> dynamic) {
            this.c = dynamic;
            this.d = dynamic.getInt("xPos") << 4;
            this.e = dynamic.getInt("zPos") << 4;
            dynamic.get("TileEntities").flatMap(Dynamic::getStream).ifPresent((stream) -> {
                stream.forEach((dynamic17) -> {
                    int l3 = dynamic17.getInt("x") - this.d & 15;
                    int i4 = dynamic17.getInt("y");
                    int j4 = dynamic17.getInt("z") - this.e & 15;
                    int k4 = i4 << 8 | j4 << 4 | l3;
                    if (this.f.put(k4, dynamic17) != null) {
                        ChunkConverterPalette.a.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.d, this.e, l3, i4, j4);
                    }

                });
            });
            boolean flag = dynamic.getBoolean("convertedFromAlphaFormat");
            dynamic.get("Sections").flatMap(Dynamic::getStream).ifPresent((stream) -> {
                stream.forEach((dynamic17) -> {
                    ChunkConverterPalette.c chunkconverterpalette$c1 = new ChunkConverterPalette.c(dynamic17);
                    this.a = chunkconverterpalette$c1.b(this.a);
                    this.b[chunkconverterpalette$c1.a] = chunkconverterpalette$c1;
                });
            });

            for(ChunkConverterPalette.c chunkconverterpalette$c : this.b) {
                if (chunkconverterpalette$c != null) {
                    ObjectIterator objectiterator = chunkconverterpalette$c.f.entrySet().iterator();

                    label258:
                    while(objectiterator.hasNext()) {
                        java.util.Map.Entry entry = (java.util.Map.Entry)objectiterator.next();
                        int i = chunkconverterpalette$c.a << 12;
                        switch(entry.getKey()) {
                        case 2:
                            IntListIterator intlistiterator10 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator10.hasNext()) {
                                    continue label258;
                                }

                                int i3 = intlistiterator10.next();
                                i3 = i3 | i;
                                Dynamic dynamic12 = this.a(i3);
                                if ("minecraft:grass_block".equals(ChunkConverterPalette.a(dynamic12))) {
                                    String s12 = ChunkConverterPalette.a(this.a(a(i3, ChunkConverterPalette.Direction.UP)));
                                    if ("minecraft:snow".equals(s12) || "minecraft:snow_layer".equals(s12)) {
                                        this.a(i3, ChunkConverterPalette.f);
                                    }
                                }
                            }
                        case 3:
                            IntListIterator intlistiterator9 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator9.hasNext()) {
                                    continue label258;
                                }

                                int l2 = intlistiterator9.next();
                                l2 = l2 | i;
                                Dynamic dynamic11 = this.a(l2);
                                if ("minecraft:podzol".equals(ChunkConverterPalette.a(dynamic11))) {
                                    String s11 = ChunkConverterPalette.a(this.a(a(l2, ChunkConverterPalette.Direction.UP)));
                                    if ("minecraft:snow".equals(s11) || "minecraft:snow_layer".equals(s11)) {
                                        this.a(l2, ChunkConverterPalette.e);
                                    }
                                }
                            }
                        case 25:
                            IntListIterator intlistiterator8 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator8.hasNext()) {
                                    continue label258;
                                }

                                int k2 = intlistiterator8.next();
                                k2 = k2 | i;
                                Dynamic dynamic10 = this.c(k2);
                                if (dynamic10 != null) {
                                    String s10 = Boolean.toString(dynamic10.getBoolean("powered")) + (byte)Math.min(Math.max(dynamic10.getByte("note"), 0), 24);
                                    this.a(k2, (Dynamic)ChunkConverterPalette.q.getOrDefault(s10, ChunkConverterPalette.q.get("false0")));
                                }
                            }
                        case 26:
                            IntListIterator intlistiterator7 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator7.hasNext()) {
                                    continue label258;
                                }

                                int j2 = intlistiterator7.next();
                                j2 = j2 | i;
                                Dynamic dynamic9 = this.b(j2);
                                Dynamic dynamic15 = this.a(j2);
                                if (dynamic9 != null) {
                                    int k3 = dynamic9.getInt("color");
                                    if (k3 != 14 && k3 >= 0 && k3 < 16) {
                                        String s16 = ChunkConverterPalette.a(dynamic15, "facing") + ChunkConverterPalette.a(dynamic15, "occupied") + ChunkConverterPalette.a(dynamic15, "part") + k3;
                                        if (ChunkConverterPalette.s.containsKey(s16)) {
                                            this.a(j2, (Dynamic)ChunkConverterPalette.s.get(s16));
                                        }
                                    }
                                }
                            }
                        case 64:
                        case 71:
                        case 193:
                        case 194:
                        case 195:
                        case 196:
                        case 197:
                            IntListIterator intlistiterator6 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator6.hasNext()) {
                                    continue label258;
                                }

                                int i2 = intlistiterator6.next();
                                i2 = i2 | i;
                                Dynamic dynamic8 = this.a(i2);
                                if (ChunkConverterPalette.a(dynamic8).endsWith("_door")) {
                                    Dynamic dynamic14 = this.a(i2);
                                    if ("lower".equals(ChunkConverterPalette.a(dynamic14, "half"))) {
                                        int j3 = a(i2, ChunkConverterPalette.Direction.UP);
                                        Dynamic dynamic16 = this.a(j3);
                                        String s1 = ChunkConverterPalette.a(dynamic14);
                                        if (s1.equals(ChunkConverterPalette.a(dynamic16))) {
                                            String s2 = ChunkConverterPalette.a(dynamic14, "facing");
                                            String s3 = ChunkConverterPalette.a(dynamic14, "open");
                                            String s4 = flag ? "left" : ChunkConverterPalette.a(dynamic16, "hinge");
                                            String s5 = flag ? "false" : ChunkConverterPalette.a(dynamic16, "powered");
                                            this.a(i2, (Dynamic)ChunkConverterPalette.p.get(s1 + s2 + "lower" + s4 + s3 + s5));
                                            this.a(j3, (Dynamic)ChunkConverterPalette.p.get(s1 + s2 + "upper" + s4 + s3 + s5));
                                        }
                                    }
                                }
                            }
                        case 86:
                            IntListIterator intlistiterator5 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator5.hasNext()) {
                                    continue label258;
                                }

                                int l1 = intlistiterator5.next();
                                l1 = l1 | i;
                                Dynamic dynamic7 = this.a(l1);
                                if ("minecraft:carved_pumpkin".equals(ChunkConverterPalette.a(dynamic7))) {
                                    String s9 = ChunkConverterPalette.a(this.a(a(l1, ChunkConverterPalette.Direction.DOWN)));
                                    if ("minecraft:grass_block".equals(s9) || "minecraft:dirt".equals(s9)) {
                                        this.a(l1, ChunkConverterPalette.d);
                                    }
                                }
                            }
                        case 110:
                            IntListIterator intlistiterator4 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator4.hasNext()) {
                                    continue label258;
                                }

                                int k1 = intlistiterator4.next();
                                k1 = k1 | i;
                                Dynamic dynamic6 = this.a(k1);
                                if ("minecraft:mycelium".equals(ChunkConverterPalette.a(dynamic6))) {
                                    String s8 = ChunkConverterPalette.a(this.a(a(k1, ChunkConverterPalette.Direction.UP)));
                                    if ("minecraft:snow".equals(s8) || "minecraft:snow_layer".equals(s8)) {
                                        this.a(k1, ChunkConverterPalette.g);
                                    }
                                }
                            }
                        case 140:
                            IntListIterator intlistiterator3 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator3.hasNext()) {
                                    continue label258;
                                }

                                int j1 = intlistiterator3.next();
                                j1 = j1 | i;
                                Dynamic dynamic5 = this.c(j1);
                                if (dynamic5 != null) {
                                    String s7 = dynamic5.getString("Item") + dynamic5.getInt("Data");
                                    this.a(j1, (Dynamic)ChunkConverterPalette.n.getOrDefault(s7, ChunkConverterPalette.n.get("minecraft:air0")));
                                }
                            }
                        case 144:
                            IntListIterator intlistiterator2 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator2.hasNext()) {
                                    continue label258;
                                }

                                int i1 = intlistiterator2.next();
                                i1 = i1 | i;
                                Dynamic dynamic4 = this.b(i1);
                                if (dynamic4 != null) {
                                    String s6 = String.valueOf(dynamic4.getByte("SkullType"));
                                    String s14 = ChunkConverterPalette.a(this.a(i1), "facing");
                                    String s15;
                                    if (!"up".equals(s14) && !"down".equals(s14)) {
                                        s15 = s6 + s14;
                                    } else {
                                        s15 = s6 + String.valueOf(dynamic4.getInt("Rot"));
                                    }

                                    dynamic4.remove("SkullType");
                                    dynamic4.remove("facing");
                                    dynamic4.remove("Rot");
                                    this.a(i1, (Dynamic)ChunkConverterPalette.o.getOrDefault(s15, ChunkConverterPalette.o.get("0north")));
                                }
                            }
                        case 175:
                            IntListIterator intlistiterator1 = ((IntList)entry.getValue()).iterator();

                            while(true) {
                                if (!intlistiterator1.hasNext()) {
                                    continue label258;
                                }

                                int l = intlistiterator1.next();
                                l = l | i;
                                Dynamic dynamic3 = this.a(l);
                                if ("upper".equals(ChunkConverterPalette.a(dynamic3, "half"))) {
                                    Dynamic dynamic13 = this.a(a(l, ChunkConverterPalette.Direction.DOWN));
                                    String s13 = ChunkConverterPalette.a(dynamic13);
                                    if ("minecraft:sunflower".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.h);
                                    } else if ("minecraft:lilac".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.i);
                                    } else if ("minecraft:tall_grass".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.j);
                                    } else if ("minecraft:large_fern".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.k);
                                    } else if ("minecraft:rose_bush".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.l);
                                    } else if ("minecraft:peony".equals(s13)) {
                                        this.a(l, ChunkConverterPalette.m);
                                    }
                                }
                            }
                        case 176:
                        case 177:
                            IntListIterator intlistiterator = ((IntList)entry.getValue()).iterator();

                            while(intlistiterator.hasNext()) {
                                int j = intlistiterator.next();
                                j = j | i;
                                Dynamic dynamic1 = this.b(j);
                                Dynamic dynamic2 = this.a(j);
                                if (dynamic1 != null) {
                                    int k = dynamic1.getInt("Base");
                                    if (k != 15 && k >= 0 && k < 16) {
                                        String s = ChunkConverterPalette.a(dynamic2, entry.getKey() == 176 ? "rotation" : "facing") + "_" + k;
                                        if (ChunkConverterPalette.t.containsKey(s)) {
                                            this.a(j, (Dynamic)ChunkConverterPalette.t.get(s));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        @Nullable
        private Dynamic<?> b(int i) {
            return (Dynamic)this.f.get(i);
        }

        @Nullable
        private Dynamic<?> c(int i) {
            return (Dynamic)this.f.remove(i);
        }

        public static int a(int i, ChunkConverterPalette.Direction chunkconverterpalette$direction) {
            switch(chunkconverterpalette$direction.b()) {
            case X:
                int j = (i & 15) + chunkconverterpalette$direction.a().a();
                return j >= 0 && j <= 15 ? i & -16 | j : -1;
            case Y:
                int k = (i >> 8) + chunkconverterpalette$direction.a().a();
                return k >= 0 && k <= 255 ? i & 255 | k << 8 : -1;
            case Z:
                int l = (i >> 4 & 15) + chunkconverterpalette$direction.a().a();
                return l >= 0 && l <= 15 ? i & -241 | l << 4 : -1;
            default:
                return -1;
            }
        }

        private void a(int i, Dynamic<?> dynamic) {
            if (i >= 0 && i <= 65535) {
                ChunkConverterPalette.c chunkconverterpalette$c = this.d(i);
                if (chunkconverterpalette$c != null) {
                    chunkconverterpalette$c.a(i & 4095, dynamic);
                }
            }
        }

        @Nullable
        private ChunkConverterPalette.c d(int i) {
            int j = i >> 12;
            return j < this.b.length ? this.b[j] : null;
        }

        public Dynamic<?> a(int i) {
            if (i >= 0 && i <= 65535) {
                ChunkConverterPalette.c chunkconverterpalette$c = this.d(i);
                return chunkconverterpalette$c == null ? ChunkConverterPalette.u : chunkconverterpalette$c.a(i & 4095);
            } else {
                return ChunkConverterPalette.u;
            }
        }

        public Dynamic<?> a() {
            Dynamic dynamic = this.c;
            if (this.f.isEmpty()) {
                dynamic = dynamic.remove("TileEntities");
            } else {
                dynamic = dynamic.set("TileEntities", dynamic.createList(this.f.values().stream()));
            }

            Dynamic dynamic1 = dynamic.emptyMap();
            Dynamic dynamic2 = dynamic.emptyList();

            for(ChunkConverterPalette.c chunkconverterpalette$c : this.b) {
                if (chunkconverterpalette$c != null) {
                    dynamic2 = dynamic2.merge(chunkconverterpalette$c.a());
                    dynamic1 = dynamic1.set(String.valueOf(chunkconverterpalette$c.a), dynamic1.createIntList(Arrays.stream(chunkconverterpalette$c.g.toIntArray())));
                }
            }

            Dynamic dynamic3 = dynamic.emptyMap();
            dynamic3 = dynamic3.set("Sides", dynamic3.createByte((byte)this.a));
            dynamic3 = dynamic3.set("Indices", dynamic1);
            return dynamic.set("UpgradeData", dynamic3).set("Sections", dynamic2);
        }
    }
}
