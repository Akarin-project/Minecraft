package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class DataConverterMaterialId extends DataFix {
    public static final Int2ObjectMap<String> ID_MAPPING = (Int2ObjectMap)DataFixUtils.make(new Int2ObjectOpenHashMap(), (int2objectopenhashmap) -> {
        int2objectopenhashmap.put(1, "minecraft:stone");
        int2objectopenhashmap.put(2, "minecraft:grass");
        int2objectopenhashmap.put(3, "minecraft:dirt");
        int2objectopenhashmap.put(4, "minecraft:cobblestone");
        int2objectopenhashmap.put(5, "minecraft:planks");
        int2objectopenhashmap.put(6, "minecraft:sapling");
        int2objectopenhashmap.put(7, "minecraft:bedrock");
        int2objectopenhashmap.put(8, "minecraft:flowing_water");
        int2objectopenhashmap.put(9, "minecraft:water");
        int2objectopenhashmap.put(10, "minecraft:flowing_lava");
        int2objectopenhashmap.put(11, "minecraft:lava");
        int2objectopenhashmap.put(12, "minecraft:sand");
        int2objectopenhashmap.put(13, "minecraft:gravel");
        int2objectopenhashmap.put(14, "minecraft:gold_ore");
        int2objectopenhashmap.put(15, "minecraft:iron_ore");
        int2objectopenhashmap.put(16, "minecraft:coal_ore");
        int2objectopenhashmap.put(17, "minecraft:log");
        int2objectopenhashmap.put(18, "minecraft:leaves");
        int2objectopenhashmap.put(19, "minecraft:sponge");
        int2objectopenhashmap.put(20, "minecraft:glass");
        int2objectopenhashmap.put(21, "minecraft:lapis_ore");
        int2objectopenhashmap.put(22, "minecraft:lapis_block");
        int2objectopenhashmap.put(23, "minecraft:dispenser");
        int2objectopenhashmap.put(24, "minecraft:sandstone");
        int2objectopenhashmap.put(25, "minecraft:noteblock");
        int2objectopenhashmap.put(27, "minecraft:golden_rail");
        int2objectopenhashmap.put(28, "minecraft:detector_rail");
        int2objectopenhashmap.put(29, "minecraft:sticky_piston");
        int2objectopenhashmap.put(30, "minecraft:web");
        int2objectopenhashmap.put(31, "minecraft:tallgrass");
        int2objectopenhashmap.put(32, "minecraft:deadbush");
        int2objectopenhashmap.put(33, "minecraft:piston");
        int2objectopenhashmap.put(35, "minecraft:wool");
        int2objectopenhashmap.put(37, "minecraft:yellow_flower");
        int2objectopenhashmap.put(38, "minecraft:red_flower");
        int2objectopenhashmap.put(39, "minecraft:brown_mushroom");
        int2objectopenhashmap.put(40, "minecraft:red_mushroom");
        int2objectopenhashmap.put(41, "minecraft:gold_block");
        int2objectopenhashmap.put(42, "minecraft:iron_block");
        int2objectopenhashmap.put(43, "minecraft:double_stone_slab");
        int2objectopenhashmap.put(44, "minecraft:stone_slab");
        int2objectopenhashmap.put(45, "minecraft:brick_block");
        int2objectopenhashmap.put(46, "minecraft:tnt");
        int2objectopenhashmap.put(47, "minecraft:bookshelf");
        int2objectopenhashmap.put(48, "minecraft:mossy_cobblestone");
        int2objectopenhashmap.put(49, "minecraft:obsidian");
        int2objectopenhashmap.put(50, "minecraft:torch");
        int2objectopenhashmap.put(51, "minecraft:fire");
        int2objectopenhashmap.put(52, "minecraft:mob_spawner");
        int2objectopenhashmap.put(53, "minecraft:oak_stairs");
        int2objectopenhashmap.put(54, "minecraft:chest");
        int2objectopenhashmap.put(56, "minecraft:diamond_ore");
        int2objectopenhashmap.put(57, "minecraft:diamond_block");
        int2objectopenhashmap.put(58, "minecraft:crafting_table");
        int2objectopenhashmap.put(60, "minecraft:farmland");
        int2objectopenhashmap.put(61, "minecraft:furnace");
        int2objectopenhashmap.put(62, "minecraft:lit_furnace");
        int2objectopenhashmap.put(65, "minecraft:ladder");
        int2objectopenhashmap.put(66, "minecraft:rail");
        int2objectopenhashmap.put(67, "minecraft:stone_stairs");
        int2objectopenhashmap.put(69, "minecraft:lever");
        int2objectopenhashmap.put(70, "minecraft:stone_pressure_plate");
        int2objectopenhashmap.put(72, "minecraft:wooden_pressure_plate");
        int2objectopenhashmap.put(73, "minecraft:redstone_ore");
        int2objectopenhashmap.put(76, "minecraft:redstone_torch");
        int2objectopenhashmap.put(77, "minecraft:stone_button");
        int2objectopenhashmap.put(78, "minecraft:snow_layer");
        int2objectopenhashmap.put(79, "minecraft:ice");
        int2objectopenhashmap.put(80, "minecraft:snow");
        int2objectopenhashmap.put(81, "minecraft:cactus");
        int2objectopenhashmap.put(82, "minecraft:clay");
        int2objectopenhashmap.put(84, "minecraft:jukebox");
        int2objectopenhashmap.put(85, "minecraft:fence");
        int2objectopenhashmap.put(86, "minecraft:pumpkin");
        int2objectopenhashmap.put(87, "minecraft:netherrack");
        int2objectopenhashmap.put(88, "minecraft:soul_sand");
        int2objectopenhashmap.put(89, "minecraft:glowstone");
        int2objectopenhashmap.put(90, "minecraft:portal");
        int2objectopenhashmap.put(91, "minecraft:lit_pumpkin");
        int2objectopenhashmap.put(95, "minecraft:stained_glass");
        int2objectopenhashmap.put(96, "minecraft:trapdoor");
        int2objectopenhashmap.put(97, "minecraft:monster_egg");
        int2objectopenhashmap.put(98, "minecraft:stonebrick");
        int2objectopenhashmap.put(99, "minecraft:brown_mushroom_block");
        int2objectopenhashmap.put(100, "minecraft:red_mushroom_block");
        int2objectopenhashmap.put(101, "minecraft:iron_bars");
        int2objectopenhashmap.put(102, "minecraft:glass_pane");
        int2objectopenhashmap.put(103, "minecraft:melon_block");
        int2objectopenhashmap.put(106, "minecraft:vine");
        int2objectopenhashmap.put(107, "minecraft:fence_gate");
        int2objectopenhashmap.put(108, "minecraft:brick_stairs");
        int2objectopenhashmap.put(109, "minecraft:stone_brick_stairs");
        int2objectopenhashmap.put(110, "minecraft:mycelium");
        int2objectopenhashmap.put(111, "minecraft:waterlily");
        int2objectopenhashmap.put(112, "minecraft:nether_brick");
        int2objectopenhashmap.put(113, "minecraft:nether_brick_fence");
        int2objectopenhashmap.put(114, "minecraft:nether_brick_stairs");
        int2objectopenhashmap.put(116, "minecraft:enchanting_table");
        int2objectopenhashmap.put(119, "minecraft:end_portal");
        int2objectopenhashmap.put(120, "minecraft:end_portal_frame");
        int2objectopenhashmap.put(121, "minecraft:end_stone");
        int2objectopenhashmap.put(122, "minecraft:dragon_egg");
        int2objectopenhashmap.put(123, "minecraft:redstone_lamp");
        int2objectopenhashmap.put(125, "minecraft:double_wooden_slab");
        int2objectopenhashmap.put(126, "minecraft:wooden_slab");
        int2objectopenhashmap.put(127, "minecraft:cocoa");
        int2objectopenhashmap.put(128, "minecraft:sandstone_stairs");
        int2objectopenhashmap.put(129, "minecraft:emerald_ore");
        int2objectopenhashmap.put(130, "minecraft:ender_chest");
        int2objectopenhashmap.put(131, "minecraft:tripwire_hook");
        int2objectopenhashmap.put(133, "minecraft:emerald_block");
        int2objectopenhashmap.put(134, "minecraft:spruce_stairs");
        int2objectopenhashmap.put(135, "minecraft:birch_stairs");
        int2objectopenhashmap.put(136, "minecraft:jungle_stairs");
        int2objectopenhashmap.put(137, "minecraft:command_block");
        int2objectopenhashmap.put(138, "minecraft:beacon");
        int2objectopenhashmap.put(139, "minecraft:cobblestone_wall");
        int2objectopenhashmap.put(141, "minecraft:carrots");
        int2objectopenhashmap.put(142, "minecraft:potatoes");
        int2objectopenhashmap.put(143, "minecraft:wooden_button");
        int2objectopenhashmap.put(145, "minecraft:anvil");
        int2objectopenhashmap.put(146, "minecraft:trapped_chest");
        int2objectopenhashmap.put(147, "minecraft:light_weighted_pressure_plate");
        int2objectopenhashmap.put(148, "minecraft:heavy_weighted_pressure_plate");
        int2objectopenhashmap.put(151, "minecraft:daylight_detector");
        int2objectopenhashmap.put(152, "minecraft:redstone_block");
        int2objectopenhashmap.put(153, "minecraft:quartz_ore");
        int2objectopenhashmap.put(154, "minecraft:hopper");
        int2objectopenhashmap.put(155, "minecraft:quartz_block");
        int2objectopenhashmap.put(156, "minecraft:quartz_stairs");
        int2objectopenhashmap.put(157, "minecraft:activator_rail");
        int2objectopenhashmap.put(158, "minecraft:dropper");
        int2objectopenhashmap.put(159, "minecraft:stained_hardened_clay");
        int2objectopenhashmap.put(160, "minecraft:stained_glass_pane");
        int2objectopenhashmap.put(161, "minecraft:leaves2");
        int2objectopenhashmap.put(162, "minecraft:log2");
        int2objectopenhashmap.put(163, "minecraft:acacia_stairs");
        int2objectopenhashmap.put(164, "minecraft:dark_oak_stairs");
        int2objectopenhashmap.put(170, "minecraft:hay_block");
        int2objectopenhashmap.put(171, "minecraft:carpet");
        int2objectopenhashmap.put(172, "minecraft:hardened_clay");
        int2objectopenhashmap.put(173, "minecraft:coal_block");
        int2objectopenhashmap.put(174, "minecraft:packed_ice");
        int2objectopenhashmap.put(175, "minecraft:double_plant");
        int2objectopenhashmap.put(256, "minecraft:iron_shovel");
        int2objectopenhashmap.put(257, "minecraft:iron_pickaxe");
        int2objectopenhashmap.put(258, "minecraft:iron_axe");
        int2objectopenhashmap.put(259, "minecraft:flint_and_steel");
        int2objectopenhashmap.put(260, "minecraft:apple");
        int2objectopenhashmap.put(261, "minecraft:bow");
        int2objectopenhashmap.put(262, "minecraft:arrow");
        int2objectopenhashmap.put(263, "minecraft:coal");
        int2objectopenhashmap.put(264, "minecraft:diamond");
        int2objectopenhashmap.put(265, "minecraft:iron_ingot");
        int2objectopenhashmap.put(266, "minecraft:gold_ingot");
        int2objectopenhashmap.put(267, "minecraft:iron_sword");
        int2objectopenhashmap.put(268, "minecraft:wooden_sword");
        int2objectopenhashmap.put(269, "minecraft:wooden_shovel");
        int2objectopenhashmap.put(270, "minecraft:wooden_pickaxe");
        int2objectopenhashmap.put(271, "minecraft:wooden_axe");
        int2objectopenhashmap.put(272, "minecraft:stone_sword");
        int2objectopenhashmap.put(273, "minecraft:stone_shovel");
        int2objectopenhashmap.put(274, "minecraft:stone_pickaxe");
        int2objectopenhashmap.put(275, "minecraft:stone_axe");
        int2objectopenhashmap.put(276, "minecraft:diamond_sword");
        int2objectopenhashmap.put(277, "minecraft:diamond_shovel");
        int2objectopenhashmap.put(278, "minecraft:diamond_pickaxe");
        int2objectopenhashmap.put(279, "minecraft:diamond_axe");
        int2objectopenhashmap.put(280, "minecraft:stick");
        int2objectopenhashmap.put(281, "minecraft:bowl");
        int2objectopenhashmap.put(282, "minecraft:mushroom_stew");
        int2objectopenhashmap.put(283, "minecraft:golden_sword");
        int2objectopenhashmap.put(284, "minecraft:golden_shovel");
        int2objectopenhashmap.put(285, "minecraft:golden_pickaxe");
        int2objectopenhashmap.put(286, "minecraft:golden_axe");
        int2objectopenhashmap.put(287, "minecraft:string");
        int2objectopenhashmap.put(288, "minecraft:feather");
        int2objectopenhashmap.put(289, "minecraft:gunpowder");
        int2objectopenhashmap.put(290, "minecraft:wooden_hoe");
        int2objectopenhashmap.put(291, "minecraft:stone_hoe");
        int2objectopenhashmap.put(292, "minecraft:iron_hoe");
        int2objectopenhashmap.put(293, "minecraft:diamond_hoe");
        int2objectopenhashmap.put(294, "minecraft:golden_hoe");
        int2objectopenhashmap.put(295, "minecraft:wheat_seeds");
        int2objectopenhashmap.put(296, "minecraft:wheat");
        int2objectopenhashmap.put(297, "minecraft:bread");
        int2objectopenhashmap.put(298, "minecraft:leather_helmet");
        int2objectopenhashmap.put(299, "minecraft:leather_chestplate");
        int2objectopenhashmap.put(300, "minecraft:leather_leggings");
        int2objectopenhashmap.put(301, "minecraft:leather_boots");
        int2objectopenhashmap.put(302, "minecraft:chainmail_helmet");
        int2objectopenhashmap.put(303, "minecraft:chainmail_chestplate");
        int2objectopenhashmap.put(304, "minecraft:chainmail_leggings");
        int2objectopenhashmap.put(305, "minecraft:chainmail_boots");
        int2objectopenhashmap.put(306, "minecraft:iron_helmet");
        int2objectopenhashmap.put(307, "minecraft:iron_chestplate");
        int2objectopenhashmap.put(308, "minecraft:iron_leggings");
        int2objectopenhashmap.put(309, "minecraft:iron_boots");
        int2objectopenhashmap.put(310, "minecraft:diamond_helmet");
        int2objectopenhashmap.put(311, "minecraft:diamond_chestplate");
        int2objectopenhashmap.put(312, "minecraft:diamond_leggings");
        int2objectopenhashmap.put(313, "minecraft:diamond_boots");
        int2objectopenhashmap.put(314, "minecraft:golden_helmet");
        int2objectopenhashmap.put(315, "minecraft:golden_chestplate");
        int2objectopenhashmap.put(316, "minecraft:golden_leggings");
        int2objectopenhashmap.put(317, "minecraft:golden_boots");
        int2objectopenhashmap.put(318, "minecraft:flint");
        int2objectopenhashmap.put(319, "minecraft:porkchop");
        int2objectopenhashmap.put(320, "minecraft:cooked_porkchop");
        int2objectopenhashmap.put(321, "minecraft:painting");
        int2objectopenhashmap.put(322, "minecraft:golden_apple");
        int2objectopenhashmap.put(323, "minecraft:sign");
        int2objectopenhashmap.put(324, "minecraft:wooden_door");
        int2objectopenhashmap.put(325, "minecraft:bucket");
        int2objectopenhashmap.put(326, "minecraft:water_bucket");
        int2objectopenhashmap.put(327, "minecraft:lava_bucket");
        int2objectopenhashmap.put(328, "minecraft:minecart");
        int2objectopenhashmap.put(329, "minecraft:saddle");
        int2objectopenhashmap.put(330, "minecraft:iron_door");
        int2objectopenhashmap.put(331, "minecraft:redstone");
        int2objectopenhashmap.put(332, "minecraft:snowball");
        int2objectopenhashmap.put(333, "minecraft:boat");
        int2objectopenhashmap.put(334, "minecraft:leather");
        int2objectopenhashmap.put(335, "minecraft:milk_bucket");
        int2objectopenhashmap.put(336, "minecraft:brick");
        int2objectopenhashmap.put(337, "minecraft:clay_ball");
        int2objectopenhashmap.put(338, "minecraft:reeds");
        int2objectopenhashmap.put(339, "minecraft:paper");
        int2objectopenhashmap.put(340, "minecraft:book");
        int2objectopenhashmap.put(341, "minecraft:slime_ball");
        int2objectopenhashmap.put(342, "minecraft:chest_minecart");
        int2objectopenhashmap.put(343, "minecraft:furnace_minecart");
        int2objectopenhashmap.put(344, "minecraft:egg");
        int2objectopenhashmap.put(345, "minecraft:compass");
        int2objectopenhashmap.put(346, "minecraft:fishing_rod");
        int2objectopenhashmap.put(347, "minecraft:clock");
        int2objectopenhashmap.put(348, "minecraft:glowstone_dust");
        int2objectopenhashmap.put(349, "minecraft:fish");
        int2objectopenhashmap.put(350, "minecraft:cooked_fished");
        int2objectopenhashmap.put(351, "minecraft:dye");
        int2objectopenhashmap.put(352, "minecraft:bone");
        int2objectopenhashmap.put(353, "minecraft:sugar");
        int2objectopenhashmap.put(354, "minecraft:cake");
        int2objectopenhashmap.put(355, "minecraft:bed");
        int2objectopenhashmap.put(356, "minecraft:repeater");
        int2objectopenhashmap.put(357, "minecraft:cookie");
        int2objectopenhashmap.put(358, "minecraft:filled_map");
        int2objectopenhashmap.put(359, "minecraft:shears");
        int2objectopenhashmap.put(360, "minecraft:melon");
        int2objectopenhashmap.put(361, "minecraft:pumpkin_seeds");
        int2objectopenhashmap.put(362, "minecraft:melon_seeds");
        int2objectopenhashmap.put(363, "minecraft:beef");
        int2objectopenhashmap.put(364, "minecraft:cooked_beef");
        int2objectopenhashmap.put(365, "minecraft:chicken");
        int2objectopenhashmap.put(366, "minecraft:cooked_chicken");
        int2objectopenhashmap.put(367, "minecraft:rotten_flesh");
        int2objectopenhashmap.put(368, "minecraft:ender_pearl");
        int2objectopenhashmap.put(369, "minecraft:blaze_rod");
        int2objectopenhashmap.put(370, "minecraft:ghast_tear");
        int2objectopenhashmap.put(371, "minecraft:gold_nugget");
        int2objectopenhashmap.put(372, "minecraft:nether_wart");
        int2objectopenhashmap.put(373, "minecraft:potion");
        int2objectopenhashmap.put(374, "minecraft:glass_bottle");
        int2objectopenhashmap.put(375, "minecraft:spider_eye");
        int2objectopenhashmap.put(376, "minecraft:fermented_spider_eye");
        int2objectopenhashmap.put(377, "minecraft:blaze_powder");
        int2objectopenhashmap.put(378, "minecraft:magma_cream");
        int2objectopenhashmap.put(379, "minecraft:brewing_stand");
        int2objectopenhashmap.put(380, "minecraft:cauldron");
        int2objectopenhashmap.put(381, "minecraft:ender_eye");
        int2objectopenhashmap.put(382, "minecraft:speckled_melon");
        int2objectopenhashmap.put(383, "minecraft:spawn_egg");
        int2objectopenhashmap.put(384, "minecraft:experience_bottle");
        int2objectopenhashmap.put(385, "minecraft:fire_charge");
        int2objectopenhashmap.put(386, "minecraft:writable_book");
        int2objectopenhashmap.put(387, "minecraft:written_book");
        int2objectopenhashmap.put(388, "minecraft:emerald");
        int2objectopenhashmap.put(389, "minecraft:item_frame");
        int2objectopenhashmap.put(390, "minecraft:flower_pot");
        int2objectopenhashmap.put(391, "minecraft:carrot");
        int2objectopenhashmap.put(392, "minecraft:potato");
        int2objectopenhashmap.put(393, "minecraft:baked_potato");
        int2objectopenhashmap.put(394, "minecraft:poisonous_potato");
        int2objectopenhashmap.put(395, "minecraft:map");
        int2objectopenhashmap.put(396, "minecraft:golden_carrot");
        int2objectopenhashmap.put(397, "minecraft:skull");
        int2objectopenhashmap.put(398, "minecraft:carrot_on_a_stick");
        int2objectopenhashmap.put(399, "minecraft:nether_star");
        int2objectopenhashmap.put(400, "minecraft:pumpkin_pie");
        int2objectopenhashmap.put(401, "minecraft:fireworks");
        int2objectopenhashmap.put(402, "minecraft:firework_charge");
        int2objectopenhashmap.put(403, "minecraft:enchanted_book");
        int2objectopenhashmap.put(404, "minecraft:comparator");
        int2objectopenhashmap.put(405, "minecraft:netherbrick");
        int2objectopenhashmap.put(406, "minecraft:quartz");
        int2objectopenhashmap.put(407, "minecraft:tnt_minecart");
        int2objectopenhashmap.put(408, "minecraft:hopper_minecart");
        int2objectopenhashmap.put(417, "minecraft:iron_horse_armor");
        int2objectopenhashmap.put(418, "minecraft:golden_horse_armor");
        int2objectopenhashmap.put(419, "minecraft:diamond_horse_armor");
        int2objectopenhashmap.put(420, "minecraft:lead");
        int2objectopenhashmap.put(421, "minecraft:name_tag");
        int2objectopenhashmap.put(422, "minecraft:command_block_minecart");
        int2objectopenhashmap.put(2256, "minecraft:record_13");
        int2objectopenhashmap.put(2257, "minecraft:record_cat");
        int2objectopenhashmap.put(2258, "minecraft:record_blocks");
        int2objectopenhashmap.put(2259, "minecraft:record_chirp");
        int2objectopenhashmap.put(2260, "minecraft:record_far");
        int2objectopenhashmap.put(2261, "minecraft:record_mall");
        int2objectopenhashmap.put(2262, "minecraft:record_mellohi");
        int2objectopenhashmap.put(2263, "minecraft:record_stal");
        int2objectopenhashmap.put(2264, "minecraft:record_strad");
        int2objectopenhashmap.put(2265, "minecraft:record_ward");
        int2objectopenhashmap.put(2266, "minecraft:record_11");
        int2objectopenhashmap.put(2267, "minecraft:record_wait");
        int2objectopenhashmap.defaultReturnValue("minecraft:air");
    });

    public DataConverterMaterialId(Schema schema, boolean flag) {
        super(schema, flag);
    }

    public static String a(int i) {
        return (String)ID_MAPPING.get(i);
    }

    public TypeRewriteRule makeRule() {
        Type type = DSL.or(DSL.intType(), DSL.named(DataConverterTypes.q.typeName(), DSL.namespacedString()));
        Type type1 = DSL.named(DataConverterTypes.q.typeName(), DSL.namespacedString());
        OpticFinder opticfinder = DSL.fieldFinder("id", type);
        return this.fixTypeEverywhereTyped("ItemIdFix", this.getInputSchema().getType(DataConverterTypes.ITEM_STACK), this.getOutputSchema().getType(DataConverterTypes.ITEM_STACK), (typed) -> {
            return typed.update(opticfinder, type1, (either) -> {
                return (Pair)either.map((integer) -> {
                    return Pair.of(DataConverterTypes.q.typeName(), a(integer));
                }, (pair) -> {
                    return pair;
                });
            });
        });
    }
}
