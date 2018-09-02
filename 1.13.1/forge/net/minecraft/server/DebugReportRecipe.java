package net.minecraft.server;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportRecipe implements DebugReportProvider {
    private static final Logger b = LogManager.getLogger();
    private static final Gson c = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator d;

    public DebugReportRecipe(DebugReportGenerator debugreportgenerator) {
        this.d = debugreportgenerator;
    }

    public void a(HashCache hashcache) throws IOException {
        java.nio.file.Path path = this.d.b();
        HashSet hashset = Sets.newHashSet();
        this.a((debugreportrecipedata) -> {
            if (!hashset.add(debugreportrecipedata.b())) {
                throw new IllegalStateException("Duplicate recipe " + debugreportrecipedata.b());
            } else {
                this.a(hashcache, debugreportrecipedata.a(), path.resolve("data/" + debugreportrecipedata.b().b() + "/recipes/" + debugreportrecipedata.b().getKey() + ".json"));
                JsonObject jsonobject = debugreportrecipedata.c();
                if (jsonobject != null) {
                    this.b(hashcache, jsonobject, path.resolve("data/" + debugreportrecipedata.b().b() + "/advancements/" + debugreportrecipedata.d().getKey() + ".json"));
                }

            }
        });
        this.b(hashcache, Advancement.SerializedAdvancement.a().a("impossible", new CriterionTriggerImpossible.a()).b(), path.resolve("data/minecraft/advancements/recipes/root.json"));
    }

    private void a(HashCache hashcache, JsonObject jsonobject, java.nio.file.Path path) {
        try {
            String s = c.toJson(jsonobject);
            String s1 = a.hashUnencodedChars(s).toString();
            if (!Objects.equals(hashcache.a(path), s1) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(path);
                Throwable throwable = null;

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (bufferedwriter != null) {
                        if (throwable != null) {
                            try {
                                bufferedwriter.close();
                            } catch (Throwable throwable1) {
                                throwable.addSuppressed(throwable1);
                            }
                        } else {
                            bufferedwriter.close();
                        }
                    }

                }
            }

            hashcache.a(path, s1);
        } catch (IOException ioexception) {
            b.error("Couldn't save recipe {}", path, ioexception);
        }

    }

    private void b(HashCache hashcache, JsonObject jsonobject, java.nio.file.Path path) {
        try {
            String s = c.toJson(jsonobject);
            String s1 = a.hashUnencodedChars(s).toString();
            if (!Objects.equals(hashcache.a(path), s1) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(path);
                Throwable throwable = null;

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (bufferedwriter != null) {
                        if (throwable != null) {
                            try {
                                bufferedwriter.close();
                            } catch (Throwable throwable1) {
                                throwable.addSuppressed(throwable1);
                            }
                        } else {
                            bufferedwriter.close();
                        }
                    }

                }
            }

            hashcache.a(path, s1);
        } catch (IOException ioexception) {
            b.error("Couldn't save recipe advancement {}", path, ioexception);
        }

    }

    private void a(Consumer<DebugReportRecipeData> consumer) {
        DebugReportRecipeShaped.a(Blocks.ACACIA_WOOD, 3).a('#', Blocks.ACACIA_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.ACACIA_LOG)).a(consumer);
        DebugReportRecipeShaped.a(Items.ACACIA_BOAT).a('#', Blocks.ACACIA_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.ACACIA_BUTTON).b(Blocks.ACACIA_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_DOOR, 3).a('#', Blocks.ACACIA_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_FENCE, 3).a('#', Items.STICK).a('W', Blocks.ACACIA_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.ACACIA_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.ACACIA_PLANKS, 4).a(TagsItem.ACACIA_LOGS).a("planks").a("has_logs", this.a(TagsItem.ACACIA_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_PRESSURE_PLATE).a('#', Blocks.ACACIA_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_SLAB, 6).a('#', Blocks.ACACIA_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_STAIRS, 4).a('#', Blocks.ACACIA_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_TRAPDOOR, 2).a('#', Blocks.ACACIA_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACTIVATOR_RAIL, 6).a('#', Blocks.REDSTONE_TORCH).a('S', Items.STICK).a('X', Items.IRON_INGOT).a("XSX").a("X#X").a("XSX").a("has_rail", this.a((IMaterial)Blocks.RAIL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.ANDESITE, 2).b(Blocks.DIORITE).b(Blocks.COBBLESTONE).a("has_stone", this.a((IMaterial)Blocks.DIORITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ANVIL).a('I', Blocks.IRON_BLOCK).a('i', Items.IRON_INGOT).a("III").a(" i ").a("iii").a("has_iron_block", this.a((IMaterial)Blocks.IRON_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Items.ARMOR_STAND).a('/', Items.STICK).a('_', Blocks.STONE_SLAB).a("///").a(" / ").a("/_/").a("has_stone_slab", this.a((IMaterial)Blocks.STONE_SLAB)).a(consumer);
        DebugReportRecipeShaped.a(Items.ARROW, 4).a('#', Items.STICK).a('X', Items.FLINT).a('Y', Items.FEATHER).a("X").a("#").a("Y").a("has_feather", this.a(Items.FEATHER)).a("has_flint", this.a(Items.FLINT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BEACON).a('S', Items.NETHER_STAR).a('G', Blocks.GLASS).a('O', Blocks.OBSIDIAN).a("GGG").a("GSG").a("OOO").a("has_nether_star", this.a(Items.NETHER_STAR)).a(consumer);
        DebugReportRecipeShaped.a(Items.BEETROOT_SOUP).a('B', Items.BOWL).a('O', Items.BEETROOT).a("OOO").a("OOO").a(" B ").a("has_beetroot", this.a(Items.BEETROOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_WOOD, 3).a('#', Blocks.BIRCH_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.BIRCH_LOG)).a(consumer);
        DebugReportRecipeShaped.a(Items.BIRCH_BOAT).a('#', Blocks.BIRCH_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BIRCH_BUTTON).b(Blocks.BIRCH_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_DOOR, 3).a('#', Blocks.BIRCH_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_FENCE, 3).a('#', Items.STICK).a('W', Blocks.BIRCH_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.BIRCH_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BIRCH_PLANKS, 4).a(TagsItem.BIRCH_LOGS).a("planks").a("has_log", this.a(TagsItem.BIRCH_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_PRESSURE_PLATE).a('#', Blocks.BIRCH_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_SLAB, 6).a('#', Blocks.BIRCH_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_STAIRS, 4).a('#', Blocks.BIRCH_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_TRAPDOOR, 2).a('#', Blocks.BIRCH_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Items.BLACK_BANNER).a('#', Blocks.BLACK_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_black_wool", this.a((IMaterial)Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.BLACK_BED).a('#', Blocks.BLACK_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_black_wool", this.a((IMaterial)Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BLACK_BED).b(Items.WHITE_BED).b(Items.INK_SAC).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "black_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BLACK_CARPET, 3).a('#', Blocks.BLACK_WOOL).a("##").b("carpet").a("has_black_wool", this.a((IMaterial)Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BLACK_CONCRETE_POWDER, 8).b(Items.INK_SAC).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.INK_SAC).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_STAINED_GLASS_PANE, 16).a('#', Blocks.BLACK_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.INK_SAC).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BLACK_WOOL).b(Items.INK_SAC).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BLAZE_POWDER, 2).b(Items.BLAZE_ROD).a("has_blaze_rod", this.a(Items.BLAZE_ROD)).a(consumer);
        DebugReportRecipeShaped.a(Items.BLUE_BANNER).a('#', Blocks.BLUE_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_blue_wool", this.a((IMaterial)Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.BLUE_BED).a('#', Blocks.BLUE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_blue_wool", this.a((IMaterial)Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BLUE_BED).b(Items.WHITE_BED).b(Items.LAPIS_LAZULI).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "blue_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BLUE_CARPET, 3).a('#', Blocks.BLUE_WOOL).a("##").b("carpet").a("has_blue_wool", this.a((IMaterial)Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BLUE_CONCRETE_POWDER, 8).b(Items.LAPIS_LAZULI).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_ICE).a('#', Blocks.PACKED_ICE).a("###").a("###").a("###").a("has_at_least_9_packed_ice", this.a(CriterionConditionValue.d.b(9), Blocks.PACKED_ICE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.LAPIS_LAZULI).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_STAINED_GLASS_PANE, 16).a('#', Blocks.BLUE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.LAPIS_LAZULI).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BLUE_WOOL).b(Items.LAPIS_LAZULI).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.OAK_BOAT).a('#', Blocks.OAK_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        Item item = Items.BONE_MEAL;
        DebugReportRecipeShaped.a(Blocks.BONE_BLOCK).a('X', Items.BONE_MEAL).a("XXX").a("XXX").a("XXX").a("has_at_least_9_bonemeal", this.a(CriterionConditionValue.d.b(9), item)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BONE_MEAL, 3).b(Items.BONE).a("bonemeal").a("has_bone", this.a(Items.BONE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BONE_MEAL, 9).b(Blocks.BONE_BLOCK).a("bonemeal").a("has_at_least_9_bonemeal", this.a(CriterionConditionValue.d.b(9), Items.BONE_MEAL)).a("has_bone_block", this.a((IMaterial)Blocks.BONE_BLOCK)).a(consumer, "bone_meal_from_bone_block");
        DebugReportRecipeShapeless.a(Items.BOOK).b(Items.PAPER, 3).b(Items.LEATHER).a("has_paper", this.a(Items.PAPER)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BOOKSHELF).a('#', TagsItem.PLANKS).a('X', Items.BOOK).a("###").a("XXX").a("###").a("has_book", this.a(Items.BOOK)).a(consumer);
        DebugReportRecipeShaped.a(Items.BOW).a('#', Items.STICK).a('X', Items.STRING).a(" #X").a("# X").a(" #X").a("has_string", this.a(Items.STRING)).a(consumer);
        DebugReportRecipeShaped.a(Items.BOWL, 4).a('#', TagsItem.PLANKS).a("# #").a(" # ").a("has_brown_mushroom", this.a((IMaterial)Blocks.BROWN_MUSHROOM)).a("has_red_mushroom", this.a((IMaterial)Blocks.RED_MUSHROOM)).a("has_mushroom_stew", this.a(Items.MUSHROOM_STEW)).a(consumer);
        DebugReportRecipeShaped.a(Items.BREAD).a('#', Items.WHEAT).a("###").a("has_wheat", this.a(Items.WHEAT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BREWING_STAND).a('B', Items.BLAZE_ROD).a('#', Blocks.COBBLESTONE).a(" B ").a("###").a("has_blaze_rod", this.a(Items.BLAZE_ROD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BRICKS).a('#', Items.BRICK).a("##").a("##").a("has_brick", this.a(Items.BRICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BRICK_SLAB, 6).a('#', Blocks.BRICKS).a("###").a("has_brick_block", this.a((IMaterial)Blocks.BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BRICK_STAIRS, 4).a('#', Blocks.BRICKS).a("#  ").a("## ").a("###").a("has_brick_block", this.a((IMaterial)Blocks.BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Items.BROWN_BANNER).a('#', Blocks.BROWN_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_brown_wool", this.a((IMaterial)Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.BROWN_BED).a('#', Blocks.BROWN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_brown_wool", this.a((IMaterial)Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.BROWN_BED).b(Items.WHITE_BED).b(Items.COCOA_BEANS).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "brown_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BROWN_CARPET, 3).a('#', Blocks.BROWN_WOOL).a("##").b("carpet").a("has_brown_wool", this.a((IMaterial)Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BROWN_CONCRETE_POWDER, 8).b(Items.COCOA_BEANS).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.COCOA_BEANS).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_STAINED_GLASS_PANE, 16).a('#', Blocks.BROWN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.COCOA_BEANS).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.BROWN_WOOL).b(Items.COCOA_BEANS).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.BUCKET).a('#', Items.IRON_INGOT).a("# #").a(" # ").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CAKE).a('A', Items.MILK_BUCKET).a('B', Items.SUGAR).a('C', Items.WHEAT).a('E', Items.EGG).a("AAA").a("BEB").a("CCC").a("has_egg", this.a(Items.EGG)).a(consumer);
        DebugReportRecipeShaped.a(Items.CARROT_ON_A_STICK).a('#', Items.FISHING_ROD).a('X', Items.CARROT).a("# ").a(" X").a("has_carrot", this.a(Items.CARROT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CAULDRON).a('#', Items.IRON_INGOT).a("# #").a("# #").a("###").a("has_water_bucket", this.a(Items.WATER_BUCKET)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CHEST).a('#', TagsItem.PLANKS).a("###").a("# #").a("###").a("has_lots_of_items", new CriterionTriggerInventoryChanged.b(CriterionConditionValue.d.b(10), CriterionConditionValue.d.e, CriterionConditionValue.d.e, new CriterionConditionItem[0])).a(consumer);
        DebugReportRecipeShaped.a(Items.CHEST_MINECART).a('A', Blocks.CHEST).a('B', Items.MINECART).a("A").a("B").a("has_minecart", this.a(Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CHISELED_QUARTZ_BLOCK).a('#', Blocks.QUARTZ_SLAB).a("#").a("#").a("has_chiseled_quartz_block", this.a((IMaterial)Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", this.a((IMaterial)Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", this.a((IMaterial)Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CHISELED_STONE_BRICKS).a('#', Blocks.STONE_BRICK_SLAB).a("#").a("#").a("has_stone_bricks", this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CLAY).a('#', Items.CLAY_BALL).a("##").a("##").a("has_clay_ball", this.a(Items.CLAY_BALL)).a(consumer);
        DebugReportRecipeShaped.a(Items.CLOCK).a('#', Items.GOLD_INGOT).a('X', Items.REDSTONE).a(" # ").a("#X#").a(" # ").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.COAL, 9).b(Blocks.COAL_BLOCK).a("has_at_least_9_coal", this.a(CriterionConditionValue.d.b(9), Items.COAL)).a("has_coal_block", this.a((IMaterial)Blocks.COAL_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COAL_BLOCK).a('#', Items.COAL).a("###").a("###").a("###").a("has_at_least_9_coal", this.a(CriterionConditionValue.d.b(9), Items.COAL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COARSE_DIRT, 4).a('D', Blocks.DIRT).a('G', Blocks.GRAVEL).a("DG").a("GD").a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_SLAB, 6).a('#', Blocks.COBBLESTONE).a("###").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_WALL, 6).a('#', Blocks.COBBLESTONE).a("###").a("###").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COMPARATOR).a('#', Blocks.REDSTONE_TORCH).a('X', Items.QUARTZ).a('I', Blocks.STONE).a(" # ").a("#X#").a("III").a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Items.COMPASS).a('#', Items.IRON_INGOT).a('X', Items.REDSTONE).a(" # ").a("#X#").a(" # ").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.COOKIE, 8).a('#', Items.WHEAT).a('X', Items.COCOA_BEANS).a("#X#").a("has_cocoa", this.a(Items.COCOA_BEANS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CRAFTING_TABLE).a('#', TagsItem.PLANKS).a("##").a("##").a("has_planks", this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CHISELED_RED_SANDSTONE).a('#', Blocks.RED_SANDSTONE_SLAB).a("#").a("#").a("has_red_sandstone", this.a((IMaterial)Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", this.a((IMaterial)Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", this.a((IMaterial)Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CHISELED_SANDSTONE).a('#', Blocks.SANDSTONE_SLAB).a("#").a("#").a("has_stone_slab", this.a((IMaterial)Blocks.SANDSTONE_SLAB)).a(consumer);
        DebugReportRecipeShaped.a(Items.CYAN_BANNER).a('#', Blocks.CYAN_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_cyan_wool", this.a((IMaterial)Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.CYAN_BED).a('#', Blocks.CYAN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_cyan_wool", this.a((IMaterial)Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.CYAN_BED).b(Items.WHITE_BED).b(Items.CYAN_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "cyan_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.CYAN_CARPET, 3).a('#', Blocks.CYAN_WOOL).a("##").b("carpet").a("has_cyan_wool", this.a((IMaterial)Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.CYAN_CONCRETE_POWDER, 8).b(Items.CYAN_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.CYAN_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.CACTUS_GREEN).a("has_green_dye", this.a(Items.CACTUS_GREEN)).a("has_lapis", this.a(Items.LAPIS_LAZULI)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.CYAN_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_STAINED_GLASS_PANE, 16).a('#', Blocks.CYAN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.CYAN_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.CYAN_WOOL).b(Items.CYAN_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_WOOD, 3).a('#', Blocks.DARK_OAK_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.DARK_OAK_LOG)).a(consumer);
        DebugReportRecipeShaped.a(Items.DARK_OAK_BOAT).a('#', Blocks.DARK_OAK_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.DARK_OAK_BUTTON).b(Blocks.DARK_OAK_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_DOOR, 3).a('#', Blocks.DARK_OAK_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_FENCE, 3).a('#', Items.STICK).a('W', Blocks.DARK_OAK_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.DARK_OAK_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.DARK_OAK_PLANKS, 4).a(TagsItem.DARK_OAK_LOGS).a("planks").a("has_logs", this.a(TagsItem.DARK_OAK_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_PRESSURE_PLATE).a('#', Blocks.DARK_OAK_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_SLAB, 6).a('#', Blocks.DARK_OAK_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_STAIRS, 4).a('#', Blocks.DARK_OAK_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_TRAPDOOR, 2).a('#', Blocks.DARK_OAK_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_PRISMARINE).a('S', Items.PRISMARINE_SHARD).a('I', Items.INK_SAC).a("SSS").a("SIS").a("SSS").a("has_prismarine_shard", this.a(Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_STAIRS, 4).a('#', Blocks.PRISMARINE).a("#  ").a("## ").a("###").a("has_prismarine", this.a((IMaterial)Blocks.PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_BRICK_STAIRS, 4).a('#', Blocks.PRISMARINE_BRICKS).a("#  ").a("## ").a("###").a("has_prismarine_bricks", this.a((IMaterial)Blocks.PRISMARINE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_PRISMARINE_STAIRS, 4).a('#', Blocks.DARK_PRISMARINE).a("#  ").a("## ").a("###").a("has_dark_prismarine", this.a((IMaterial)Blocks.DARK_PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DAYLIGHT_DETECTOR).a('Q', Items.QUARTZ).a('G', Blocks.GLASS).a('W', RecipeItemStack.a(TagsItem.WOODEN_SLABS)).a("GGG").a("QQQ").a("WWW").a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DETECTOR_RAIL, 6).a('R', Items.REDSTONE).a('#', Blocks.STONE_PRESSURE_PLATE).a('X', Items.IRON_INGOT).a("X X").a("X#X").a("XRX").a("has_rail", this.a((IMaterial)Blocks.RAIL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.DIAMOND, 9).b(Blocks.DIAMOND_BLOCK).a("has_at_least_9_diamond", this.a(CriterionConditionValue.d.b(9), Items.DIAMOND)).a("has_diamond_block", this.a((IMaterial)Blocks.DIAMOND_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_AXE).a('#', Items.STICK).a('X', Items.DIAMOND).a("XX").a("X#").a(" #").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DIAMOND_BLOCK).a('#', Items.DIAMOND).a("###").a("###").a("###").a("has_at_least_9_diamond", this.a(CriterionConditionValue.d.b(9), Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_BOOTS).a('X', Items.DIAMOND).a("X X").a("X X").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_CHESTPLATE).a('X', Items.DIAMOND).a("X X").a("XXX").a("XXX").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_HELMET).a('X', Items.DIAMOND).a("XXX").a("X X").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_HOE).a('#', Items.STICK).a('X', Items.DIAMOND).a("XX").a(" #").a(" #").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_LEGGINGS).a('X', Items.DIAMOND).a("XXX").a("X X").a("X X").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_PICKAXE).a('#', Items.STICK).a('X', Items.DIAMOND).a("XXX").a(" # ").a(" # ").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_SHOVEL).a('#', Items.STICK).a('X', Items.DIAMOND).a("X").a("#").a("#").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Items.DIAMOND_SWORD).a('#', Items.STICK).a('X', Items.DIAMOND).a("X").a("X").a("#").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DIORITE, 2).a('Q', Items.QUARTZ).a('C', Blocks.COBBLESTONE).a("CQ").a("QC").a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DISPENSER).a('R', Items.REDSTONE).a('#', Blocks.COBBLESTONE).a('X', Items.BOW).a("###").a("#X#").a("#R#").a("has_bow", this.a(Items.BOW)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DROPPER).a('R', Items.REDSTONE).a('#', Blocks.COBBLESTONE).a("###").a("# #").a("#R#").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.EMERALD, 9).b(Blocks.EMERALD_BLOCK).a("has_at_least_9_emerald", this.a(CriterionConditionValue.d.b(9), Items.EMERALD)).a("has_emerald_block", this.a((IMaterial)Blocks.EMERALD_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.EMERALD_BLOCK).a('#', Items.EMERALD).a("###").a("###").a("###").a("has_at_least_9_emerald", this.a(CriterionConditionValue.d.b(9), Items.EMERALD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ENCHANTING_TABLE).a('B', Items.BOOK).a('#', Blocks.OBSIDIAN).a('D', Items.DIAMOND).a(" B ").a("D#D").a("###").a("has_obsidian", this.a((IMaterial)Blocks.OBSIDIAN)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ENDER_CHEST).a('#', Blocks.OBSIDIAN).a('E', Items.ENDER_EYE).a("###").a("#E#").a("###").a("has_ender_eye", this.a(Items.ENDER_EYE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.ENDER_EYE).b(Items.ENDER_PEARL).b(Items.BLAZE_POWDER).a("has_blaze_powder", this.a(Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.END_STONE_BRICKS, 4).a('#', Blocks.END_STONE).a("##").a("##").a("has_end_stone", this.a((IMaterial)Blocks.END_STONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.END_CRYSTAL).a('T', Items.GHAST_TEAR).a('E', Items.ENDER_EYE).a('G', Blocks.GLASS).a("GGG").a("GEG").a("GTG").a("has_ender_eye", this.a(Items.ENDER_EYE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.END_ROD, 4).a('#', Items.POPPED_CHORUS_FRUIT).a('/', Items.BLAZE_ROD).a("/").a("#").a("has_chorus_fruit_popped", this.a(Items.POPPED_CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_FENCE, 3).a('#', Items.STICK).a('W', Blocks.OAK_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.OAK_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Items.FERMENTED_SPIDER_EYE).b(Items.SPIDER_EYE).b(Blocks.BROWN_MUSHROOM).b(Items.SUGAR).a("has_spider_eye", this.a(Items.SPIDER_EYE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.FIRE_CHARGE, 3).b(Items.GUNPOWDER).b(Items.BLAZE_POWDER).a(RecipeItemStack.a(Items.COAL, Items.CHARCOAL)).a("has_blaze_powder", this.a(Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a(Items.FISHING_ROD).a('#', Items.STICK).a('X', Items.STRING).a("  #").a(" #X").a("# X").a("has_string", this.a(Items.STRING)).a(consumer);
        DebugReportRecipeShapeless.a(Items.FLINT_AND_STEEL).b(Items.IRON_INGOT).b(Items.FLINT).a("has_flint", this.a(Items.FLINT)).a("has_obsidian", this.a((IMaterial)Blocks.OBSIDIAN)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.FLOWER_POT).a('#', Items.BRICK).a("# #").a(" # ").a("has_brick", this.a(Items.BRICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.FURNACE).a('#', Blocks.COBBLESTONE).a("###").a("# #").a("###").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.FURNACE_MINECART).a('A', Blocks.FURNACE).a('B', Items.MINECART).a("A").a("B").a("has_minecart", this.a(Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Items.GLASS_BOTTLE, 3).a('#', Blocks.GLASS).a("# #").a(" # ").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GLASS_PANE, 16).a('#', Blocks.GLASS).a("###").a("###").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GLOWSTONE).a('#', Items.GLOWSTONE_DUST).a("##").a("##").a("has_glowstone_dust", this.a(Items.GLOWSTONE_DUST)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_APPLE).a('#', Items.GOLD_INGOT).a('X', Items.APPLE).a("###").a("#X#").a("###").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_AXE).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("XX").a("X#").a(" #").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_BOOTS).a('X', Items.GOLD_INGOT).a("X X").a("X X").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_CARROT).a('#', Items.GOLD_NUGGET).a('X', Items.CARROT).a("###").a("#X#").a("###").a("has_gold_nugget", this.a(Items.GOLD_NUGGET)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_CHESTPLATE).a('X', Items.GOLD_INGOT).a("X X").a("XXX").a("XXX").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_HELMET).a('X', Items.GOLD_INGOT).a("XXX").a("X X").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_HOE).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("XX").a(" #").a(" #").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_LEGGINGS).a('X', Items.GOLD_INGOT).a("XXX").a("X X").a("X X").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_PICKAXE).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("XXX").a(" # ").a(" # ").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POWERED_RAIL, 6).a('R', Items.REDSTONE).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("X X").a("X#X").a("XRX").a("has_rail", this.a((IMaterial)Blocks.RAIL)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_SHOVEL).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("X").a("#").a("#").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.GOLDEN_SWORD).a('#', Items.STICK).a('X', Items.GOLD_INGOT).a("X").a("X").a("#").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GOLD_BLOCK).a('#', Items.GOLD_INGOT).a("###").a("###").a("###").a("has_at_least_9_gold_ingot", this.a(CriterionConditionValue.d.b(9), Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a(Items.GOLD_INGOT, 9).b(Blocks.GOLD_BLOCK).a("gold_ingot").a("has_at_least_9_gold_ingot", this.a(CriterionConditionValue.d.b(9), Items.GOLD_INGOT)).a("has_gold_block", this.a((IMaterial)Blocks.GOLD_BLOCK)).a(consumer, "gold_ingot_from_gold_block");
        DebugReportRecipeShaped.a(Items.GOLD_INGOT).a('#', Items.GOLD_NUGGET).a("###").a("###").a("###").b("gold_ingot").a("has_at_least_9_gold_nugget", this.a(CriterionConditionValue.d.b(9), Items.GOLD_NUGGET)).a(consumer, "gold_ingot_from_nuggets");
        DebugReportRecipeShapeless.a(Items.GOLD_NUGGET, 9).b(Items.GOLD_INGOT).a("has_at_least_9_gold_nugget", this.a(CriterionConditionValue.d.b(9), Items.GOLD_NUGGET)).a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.GRANITE).b(Blocks.DIORITE).b(Items.QUARTZ).a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Items.GRAY_BANNER).a('#', Blocks.GRAY_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_gray_wool", this.a((IMaterial)Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.GRAY_BED).a('#', Blocks.GRAY_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_gray_wool", this.a((IMaterial)Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.GRAY_BED).b(Items.WHITE_BED).b(Items.GRAY_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "gray_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.GRAY_CARPET, 3).a('#', Blocks.GRAY_WOOL).a("##").b("carpet").a("has_gray_wool", this.a((IMaterial)Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.GRAY_CONCRETE_POWDER, 8).b(Items.GRAY_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.GRAY_DYE, 2).b(Items.INK_SAC).b(Items.BONE_MEAL).a("has_bonemeal", this.a(Items.BONE_MEAL)).a("has_black_dye", this.a(Items.INK_SAC)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.GRAY_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_STAINED_GLASS_PANE, 16).a('#', Blocks.GRAY_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.GRAY_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.GRAY_WOOL).b(Items.GRAY_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.GREEN_BANNER).a('#', Blocks.GREEN_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_green_wool", this.a((IMaterial)Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.GREEN_BED).a('#', Blocks.GREEN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_green_wool", this.a((IMaterial)Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.GREEN_BED).b(Items.WHITE_BED).b(Items.CACTUS_GREEN).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "green_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.GREEN_CARPET, 3).a('#', Blocks.GREEN_WOOL).a("##").b("carpet").a("has_green_wool", this.a((IMaterial)Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.GREEN_CONCRETE_POWDER, 8).b(Items.CACTUS_GREEN).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.CACTUS_GREEN).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_STAINED_GLASS_PANE, 16).a('#', Blocks.GREEN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.CACTUS_GREEN).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.GREEN_WOOL).b(Items.CACTUS_GREEN).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.HAY_BLOCK).a('#', Items.WHEAT).a("###").a("###").a("###").a("has_at_least_9_wheat", this.a(CriterionConditionValue.d.b(9), Items.WHEAT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).a('#', Items.IRON_INGOT).a("##").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.HOPPER).a('C', Blocks.CHEST).a('I', Items.IRON_INGOT).a("I I").a("ICI").a(" I ").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.HOPPER_MINECART).a('A', Blocks.HOPPER).a('B', Items.MINECART).a("A").a("B").a("has_minecart", this.a(Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_AXE).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("XX").a("X#").a(" #").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_BARS, 16).a('#', Items.IRON_INGOT).a("###").a("###").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_BLOCK).a('#', Items.IRON_INGOT).a("###").a("###").a("###").a("has_at_least_9_iron_ingot", this.a(CriterionConditionValue.d.b(9), Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_BOOTS).a('X', Items.IRON_INGOT).a("X X").a("X X").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_CHESTPLATE).a('X', Items.IRON_INGOT).a("X X").a("XXX").a("XXX").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_DOOR, 3).a('#', Items.IRON_INGOT).a("##").a("##").a("##").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_HELMET).a('X', Items.IRON_INGOT).a("XXX").a("X X").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_HOE).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("XX").a(" #").a(" #").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a(Items.IRON_INGOT, 9).b(Blocks.IRON_BLOCK).a("iron_ingot").a("has_at_least_9_iron_ingot", this.a(CriterionConditionValue.d.b(9), Items.IRON_INGOT)).a("has_iron_block", this.a((IMaterial)Blocks.IRON_BLOCK)).a(consumer, "iron_ingot_from_iron_block");
        DebugReportRecipeShaped.a(Items.IRON_INGOT).a('#', Items.IRON_NUGGET).a("###").a("###").a("###").b("iron_ingot").a("has_at_least_9_iron_nugget", this.a(CriterionConditionValue.d.b(9), Items.IRON_NUGGET)).a(consumer, "iron_ingot_from_nuggets");
        DebugReportRecipeShaped.a(Items.IRON_LEGGINGS).a('X', Items.IRON_INGOT).a("XXX").a("X X").a("X X").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a(Items.IRON_NUGGET, 9).b(Items.IRON_INGOT).a("has_at_least_9_iron_nugget", this.a(CriterionConditionValue.d.b(9), Items.IRON_NUGGET)).a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_PICKAXE).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("XXX").a(" # ").a(" # ").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_SHOVEL).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("X").a("#").a("#").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.IRON_SWORD).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("X").a("X").a("#").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_TRAPDOOR).a('#', Items.IRON_INGOT).a("##").a("##").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.ITEM_FRAME).a('#', Items.STICK).a('X', Items.LEATHER).a("###").a("#X#").a("###").a("has_leather", this.a(Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUKEBOX).a('#', TagsItem.PLANKS).a('X', Items.DIAMOND).a("###").a("#X#").a("###").a("has_diamond", this.a(Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_WOOD, 3).a('#', Blocks.JUNGLE_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.JUNGLE_LOG)).a(consumer);
        DebugReportRecipeShaped.a(Items.JUNGLE_BOAT).a('#', Blocks.JUNGLE_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.JUNGLE_BUTTON).b(Blocks.JUNGLE_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_DOOR, 3).a('#', Blocks.JUNGLE_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_FENCE, 3).a('#', Items.STICK).a('W', Blocks.JUNGLE_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.JUNGLE_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.JUNGLE_PLANKS, 4).a(TagsItem.JUNGLE_LOGS).a("planks").a("has_log", this.a(TagsItem.JUNGLE_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_PRESSURE_PLATE).a('#', Blocks.JUNGLE_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_SLAB, 6).a('#', Blocks.JUNGLE_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_STAIRS, 4).a('#', Blocks.JUNGLE_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_TRAPDOOR, 2).a('#', Blocks.JUNGLE_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LADDER, 3).a('#', Items.STICK).a("# #").a("###").a("# #").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LAPIS_BLOCK).a('#', Items.LAPIS_LAZULI).a("###").a("###").a("###").a("has_at_least_9_lapis", this.a(CriterionConditionValue.d.b(9), Items.LAPIS_LAZULI)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LAPIS_LAZULI, 9).b(Blocks.LAPIS_BLOCK).a("has_at_least_9_lapis", this.a(CriterionConditionValue.d.b(9), Items.LAPIS_LAZULI)).a("has_lapis_block", this.a((IMaterial)Blocks.LAPIS_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEAD, 2).a('~', Items.STRING).a('O', Items.SLIME_BALL).a("~~ ").a("~O ").a("  ~").a("has_slime_ball", this.a(Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEATHER).a('#', Items.RABBIT_HIDE).a("##").a("##").a("has_rabbit_hide", this.a(Items.RABBIT_HIDE)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEATHER_BOOTS).a('X', Items.LEATHER).a("X X").a("X X").a("has_leather", this.a(Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEATHER_CHESTPLATE).a('X', Items.LEATHER).a("X X").a("XXX").a("XXX").a("has_leather", this.a(Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEATHER_HELMET).a('X', Items.LEATHER).a("XXX").a("X X").a("has_leather", this.a(Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEATHER_LEGGINGS).a('X', Items.LEATHER).a("XXX").a("X X").a("X X").a("has_leather", this.a(Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LEVER).a('#', Blocks.COBBLESTONE).a('X', Items.STICK).a("X").a("#").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIGHT_BLUE_BANNER).a('#', Blocks.LIGHT_BLUE_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_light_blue_wool", this.a((IMaterial)Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIGHT_BLUE_BED).a('#', Blocks.LIGHT_BLUE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_light_blue_wool", this.a((IMaterial)Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIGHT_BLUE_BED).b(Items.WHITE_BED).b(Items.LIGHT_BLUE_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "light_blue_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_CARPET, 3).a('#', Blocks.LIGHT_BLUE_WOOL).a("##").b("carpet").a("has_light_blue_wool", this.a((IMaterial)Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIGHT_BLUE_CONCRETE_POWDER, 8).b(Items.LIGHT_BLUE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIGHT_BLUE_DYE).b(Blocks.BLUE_ORCHID).a("light_blue_dye").a("has_red_flower", this.a((IMaterial)Blocks.BLUE_ORCHID)).a(consumer, "light_blue_dye_from_blue_orchid");
        DebugReportRecipeShapeless.a(Items.LIGHT_BLUE_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.BONE_MEAL).a("light_blue_dye").a("has_lapis", this.a(Items.LAPIS_LAZULI)).a("has_bonemeal", this.a(Items.BONE_MEAL)).a(consumer, "light_blue_dye_from_lapis_bonemeal");
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.LIGHT_BLUE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, 16).a('#', Blocks.LIGHT_BLUE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.LIGHT_BLUE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIGHT_BLUE_WOOL).b(Items.LIGHT_BLUE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIGHT_GRAY_BANNER).a('#', Blocks.LIGHT_GRAY_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_light_gray_wool", this.a((IMaterial)Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIGHT_GRAY_BED).a('#', Blocks.LIGHT_GRAY_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_light_gray_wool", this.a((IMaterial)Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_BED).b(Items.WHITE_BED).b(Items.LIGHT_GRAY_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "light_gray_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_CARPET, 3).a('#', Blocks.LIGHT_GRAY_WOOL).a("##").b("carpet").a("has_light_gray_wool", this.a((IMaterial)Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIGHT_GRAY_CONCRETE_POWDER, 8).b(Items.LIGHT_GRAY_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_DYE).b(Blocks.AZURE_BLUET).a("light_gray_dye").a("has_red_flower", this.a((IMaterial)Blocks.AZURE_BLUET)).a(consumer, "light_gray_dye_from_azure_bluet");
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_DYE, 2).b(Items.GRAY_DYE).b(Items.BONE_MEAL).a("light_gray_dye").a("has_gray_dye", this.a(Items.GRAY_DYE)).a("has_bonemeal", this.a(Items.BONE_MEAL)).a(consumer, "light_gray_dye_from_gray_bonemeal");
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_DYE, 3).b(Items.INK_SAC).b(Items.BONE_MEAL, 2).a("light_gray_dye").a("has_bonemeal", this.a(Items.BONE_MEAL)).a("has_black_dye", this.a(Items.INK_SAC)).a(consumer, "light_gray_dye_from_ink_bonemeal");
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_DYE).b(Blocks.OXEYE_DAISY).a("light_gray_dye").a("has_red_flower", this.a((IMaterial)Blocks.OXEYE_DAISY)).a(consumer, "light_gray_dye_from_oxeye_daisy");
        DebugReportRecipeShapeless.a(Items.LIGHT_GRAY_DYE).b(Blocks.WHITE_TULIP).a("light_gray_dye").a("has_red_flower", this.a((IMaterial)Blocks.WHITE_TULIP)).a(consumer, "light_gray_dye_from_white_tulip");
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.LIGHT_GRAY_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, 16).a('#', Blocks.LIGHT_GRAY_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.LIGHT_GRAY_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIGHT_GRAY_WOOL).b(Items.LIGHT_GRAY_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).a('#', Items.GOLD_INGOT).a("##").a("has_gold_ingot", this.a(Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIME_BANNER).a('#', Blocks.LIME_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_lime_wool", this.a((IMaterial)Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.LIME_BED).a('#', Blocks.LIME_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_lime_wool", this.a((IMaterial)Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIME_BED).b(Items.WHITE_BED).b(Items.LIME_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "lime_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIME_CARPET, 3).a('#', Blocks.LIME_WOOL).a("##").b("carpet").a("has_lime_wool", this.a((IMaterial)Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIME_CONCRETE_POWDER, 8).b(Items.LIME_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.LIME_DYE, 2).b(Items.CACTUS_GREEN).b(Items.BONE_MEAL).a("has_green_dye", this.a(Items.CACTUS_GREEN)).a("has_bonemeal", this.a(Items.BONE_MEAL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.LIME_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_STAINED_GLASS_PANE, 16).a('#', Blocks.LIME_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.LIME_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.LIME_WOOL).b(Items.LIME_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JACK_O_LANTERN).a('A', Blocks.CARVED_PUMPKIN).a('B', Blocks.TORCH).a("A").a("B").a("has_carved_pumpkin", this.a((IMaterial)Blocks.CARVED_PUMPKIN)).a(consumer);
        DebugReportRecipeShaped.a(Items.MAGENTA_BANNER).a('#', Blocks.MAGENTA_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_magenta_wool", this.a((IMaterial)Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.MAGENTA_BED).a('#', Blocks.MAGENTA_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_magenta_wool", this.a((IMaterial)Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.MAGENTA_BED).b(Items.WHITE_BED).b(Items.MAGENTA_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "magenta_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.MAGENTA_CARPET, 3).a('#', Blocks.MAGENTA_WOOL).a("##").b("carpet").a("has_magenta_wool", this.a((IMaterial)Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.MAGENTA_CONCRETE_POWDER, 8).b(Items.MAGENTA_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.MAGENTA_DYE).b(Blocks.ALLIUM).a("magenta_dye").a("has_red_flower", this.a((IMaterial)Blocks.ALLIUM)).a(consumer, "magenta_dye_from_allium");
        DebugReportRecipeShapeless.a(Items.MAGENTA_DYE, 4).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED, 2).b(Items.BONE_MEAL).a("magenta_dye").a("has_lapis", this.a(Items.LAPIS_LAZULI)).a("has_rose_red", this.a(Items.ROSE_RED)).a("has_bonemeal", this.a(Items.BONE_MEAL)).a(consumer, "magenta_dye_from_lapis_ink_bonemeal");
        DebugReportRecipeShapeless.a(Items.MAGENTA_DYE, 3).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED).b(Items.PINK_DYE).a("magenta_dye").a("has_pink_dye", this.a(Items.PINK_DYE)).a("has_lapis", this.a(Items.LAPIS_LAZULI)).a("has_red_dye", this.a(Items.ROSE_RED)).a(consumer, "magenta_dye_from_lapis_red_pink");
        DebugReportRecipeShapeless.a(Items.MAGENTA_DYE, 2).b(Blocks.LILAC).a("magenta_dye").a("has_double_plant", this.a((IMaterial)Blocks.LILAC)).a(consumer, "magenta_dye_from_lilac");
        DebugReportRecipeShapeless.a(Items.MAGENTA_DYE, 2).b(Items.PURPLE_DYE).b(Items.PINK_DYE).a("magenta_dye").a("has_pink_dye", this.a(Items.PINK_DYE)).a("has_purple_dye", this.a(Items.PURPLE_DYE)).a(consumer, "magenta_dye_from_purple_and_pink");
        DebugReportRecipeShaped.a(Blocks.MAGENTA_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.MAGENTA_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MAGENTA_STAINED_GLASS_PANE, 16).a('#', Blocks.MAGENTA_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MAGENTA_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.MAGENTA_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.MAGENTA_WOOL).b(Items.MAGENTA_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MAGMA_BLOCK).a('#', Items.MAGMA_CREAM).a("##").a("##").a("has_magma_cream", this.a(Items.MAGMA_CREAM)).a(consumer);
        DebugReportRecipeShapeless.a(Items.MAGMA_CREAM).b(Items.BLAZE_POWDER).b(Items.SLIME_BALL).a("has_blaze_powder", this.a(Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a(Items.MAP).a('#', Items.PAPER).a('X', Items.COMPASS).a("###").a("#X#").a("###").a("has_compass", this.a(Items.COMPASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MELON).a('M', Items.MELON_SLICE).a("MMM").a("MMM").a("MMM").a("has_melon", this.a(Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.MELON_SEEDS).b(Items.MELON_SLICE).a("has_melon", this.a(Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShaped.a(Items.MINECART).a('#', Items.IRON_INGOT).a("# #").a("###").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.MOSSY_COBBLESTONE).b(Blocks.COBBLESTONE).b(Blocks.VINE).a("has_vine", this.a((IMaterial)Blocks.VINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MOSSY_COBBLESTONE_WALL, 6).a('#', Blocks.MOSSY_COBBLESTONE).a("###").a("###").a("has_mossy_cobblestone", this.a((IMaterial)Blocks.MOSSY_COBBLESTONE)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.MOSSY_STONE_BRICKS).b(Blocks.STONE_BRICKS).b(Blocks.VINE).a("has_mossy_cobblestone", this.a((IMaterial)Blocks.MOSSY_COBBLESTONE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.MUSHROOM_STEW).b(Blocks.BROWN_MUSHROOM).b(Blocks.RED_MUSHROOM).b(Items.BOWL).a("has_mushroom_stew", this.a(Items.MUSHROOM_STEW)).a("has_bowl", this.a(Items.BOWL)).a("has_brown_mushroom", this.a((IMaterial)Blocks.BROWN_MUSHROOM)).a("has_red_mushroom", this.a((IMaterial)Blocks.RED_MUSHROOM)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICKS).a('N', Items.NETHER_BRICK).a("NN").a("NN").a("has_netherbrick", this.a(Items.NETHER_BRICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_FENCE, 6).a('#', Blocks.NETHER_BRICKS).a("###").a("###").a("has_nether_brick", this.a((IMaterial)Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_SLAB, 6).a('#', Blocks.NETHER_BRICKS).a("###").a("has_nether_brick", this.a((IMaterial)Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_STAIRS, 4).a('#', Blocks.NETHER_BRICKS).a("#  ").a("## ").a("###").a("has_nether_brick", this.a((IMaterial)Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_WART_BLOCK).a('#', Items.NETHER_WART).a("###").a("###").a("###").a("has_nether_wart", this.a(Items.NETHER_WART)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NOTE_BLOCK).a('#', TagsItem.PLANKS).a('X', Items.REDSTONE).a("###").a("#X#").a("###").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_WOOD, 3).a('#', Blocks.OAK_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.OAK_LOG)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.OAK_BUTTON).b(Blocks.OAK_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.OAK_PLANKS, 4).a(TagsItem.OAK_LOGS).a("planks").a("has_log", this.a(TagsItem.OAK_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_PRESSURE_PLATE).a('#', Blocks.OAK_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_SLAB, 6).a('#', Blocks.OAK_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_STAIRS, 4).a('#', Blocks.OAK_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_TRAPDOOR, 2).a('#', Blocks.OAK_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OBSERVER).a('Q', Items.QUARTZ).a('R', Items.REDSTONE).a('#', Blocks.COBBLESTONE).a("###").a("RRQ").a("###").a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Items.ORANGE_BANNER).a('#', Blocks.ORANGE_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_orange_wool", this.a((IMaterial)Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.ORANGE_BED).a('#', Blocks.ORANGE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_orange_wool", this.a((IMaterial)Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.ORANGE_BED).b(Items.WHITE_BED).b(Items.ORANGE_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "orange_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.ORANGE_CARPET, 3).a('#', Blocks.ORANGE_WOOL).a("##").b("carpet").a("has_orange_wool", this.a((IMaterial)Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.ORANGE_CONCRETE_POWDER, 8).b(Items.ORANGE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.ORANGE_DYE).b(Blocks.ORANGE_TULIP).a("orange_dye").a("has_red_flower", this.a((IMaterial)Blocks.ORANGE_TULIP)).a(consumer, "orange_dye_from_orange_tulip");
        DebugReportRecipeShapeless.a(Items.ORANGE_DYE, 2).b(Items.ROSE_RED).b(Items.DANDELION_YELLOW).a("orange_dye").a("has_red_dye", this.a(Items.ROSE_RED)).a("has_yellow_dye", this.a(Items.DANDELION_YELLOW)).a(consumer, "orange_dye_from_red_yellow");
        DebugReportRecipeShaped.a(Blocks.ORANGE_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.ORANGE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ORANGE_STAINED_GLASS_PANE, 16).a('#', Blocks.ORANGE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ORANGE_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.ORANGE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.ORANGE_WOOL).b(Items.ORANGE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.PAINTING).a('#', Items.STICK).a('X', RecipeItemStack.a(TagsItem.WOOL)).a("###").a("#X#").a("###").a("has_wool", this.a(TagsItem.WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.PAPER, 3).a('#', Blocks.SUGAR_CANE).a("###").a("has_reeds", this.a((IMaterial)Blocks.SUGAR_CANE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_PILLAR, 2).a('#', Blocks.QUARTZ_BLOCK).a("#").a("#").a("has_chiseled_quartz_block", this.a((IMaterial)Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", this.a((IMaterial)Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", this.a((IMaterial)Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.PACKED_ICE).b(Blocks.ICE, 9).a("has_at_least_9_ice", this.a(CriterionConditionValue.d.b(9), Blocks.ICE)).a(consumer);
        DebugReportRecipeShaped.a(Items.PINK_BANNER).a('#', Blocks.PINK_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_pink_wool", this.a((IMaterial)Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.PINK_BED).a('#', Blocks.PINK_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_pink_wool", this.a((IMaterial)Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PINK_BED).b(Items.WHITE_BED).b(Items.PINK_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "pink_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.PINK_CARPET, 3).a('#', Blocks.PINK_WOOL).a("##").b("carpet").a("has_pink_wool", this.a((IMaterial)Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.PINK_CONCRETE_POWDER, 8).b(Items.PINK_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PINK_DYE, 2).b(Blocks.PEONY).a("pink_dye").a("has_double_plant", this.a((IMaterial)Blocks.PEONY)).a(consumer, "pink_dye_from_peony");
        DebugReportRecipeShapeless.a(Items.PINK_DYE).b(Blocks.PINK_TULIP).a("pink_dye").a("has_red_flower", this.a((IMaterial)Blocks.PINK_TULIP)).a(consumer, "pink_dye_from_pink_tulip");
        DebugReportRecipeShapeless.a(Items.PINK_DYE, 2).b(Items.ROSE_RED).b(Items.BONE_MEAL).a("pink_dye").a("has_bonemeal", this.a(Items.BONE_MEAL)).a("has_red_dye", this.a(Items.ROSE_RED)).a(consumer, "pink_dye_from_red_bonemeal");
        DebugReportRecipeShaped.a(Blocks.PINK_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.PINK_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PINK_STAINED_GLASS_PANE, 16).a('#', Blocks.PINK_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PINK_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.PINK_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.PINK_WOOL).b(Items.PINK_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PISTON).a('R', Items.REDSTONE).a('#', Blocks.COBBLESTONE).a('T', TagsItem.PLANKS).a('X', Items.IRON_INGOT).a("TTT").a("#X#").a("#R#").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_GRANITE, 4).a('S', Blocks.GRANITE).a("SS").a("SS").a("has_stone", this.a((IMaterial)Blocks.GRANITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_DIORITE, 4).a('S', Blocks.DIORITE).a("SS").a("SS").a("has_stone", this.a((IMaterial)Blocks.DIORITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_ANDESITE, 4).a('S', Blocks.ANDESITE).a("SS").a("SS").a("has_stone", this.a((IMaterial)Blocks.ANDESITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE).a('S', Items.PRISMARINE_SHARD).a("SS").a("SS").a("has_prismarine_shard", this.a(Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_BRICKS).a('S', Items.PRISMARINE_SHARD).a("SSS").a("SSS").a("SSS").a("has_prismarine_shard", this.a(Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_SLAB, 6).a('#', Blocks.PRISMARINE).a("###").a("has_prismarine", this.a((IMaterial)Blocks.PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_BRICK_SLAB, 6).a('#', Blocks.PRISMARINE_BRICKS).a("###").a("has_prismarine_bricks", this.a((IMaterial)Blocks.PRISMARINE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_PRISMARINE_SLAB, 6).a('#', Blocks.DARK_PRISMARINE).a("###").a("has_dark_prismarine", this.a((IMaterial)Blocks.DARK_PRISMARINE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PUMPKIN_PIE).b(Blocks.PUMPKIN).b(Items.SUGAR).b(Items.EGG).a("has_carved_pumpkin", this.a((IMaterial)Blocks.CARVED_PUMPKIN)).a("has_pumpkin", this.a((IMaterial)Blocks.PUMPKIN)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PUMPKIN_SEEDS, 4).b(Blocks.PUMPKIN).a("has_pumpkin", this.a((IMaterial)Blocks.PUMPKIN)).a(consumer);
        DebugReportRecipeShaped.a(Items.PURPLE_BANNER).a('#', Blocks.PURPLE_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_purple_wool", this.a((IMaterial)Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.PURPLE_BED).a('#', Blocks.PURPLE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_purple_wool", this.a((IMaterial)Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PURPLE_BED).b(Items.WHITE_BED).b(Items.PURPLE_DYE).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "purple_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.PURPLE_CARPET, 3).a('#', Blocks.PURPLE_WOOL).a("##").b("carpet").a("has_purple_wool", this.a((IMaterial)Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.PURPLE_CONCRETE_POWDER, 8).b(Items.PURPLE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.PURPLE_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED).a("has_lapis", this.a(Items.LAPIS_LAZULI)).a("has_red_dye", this.a(Items.ROSE_RED)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SHULKER_BOX).a('#', Blocks.CHEST).a('-', Items.SHULKER_SHELL).a("-").a("#").a("-").a("has_shulker_shell", this.a(Items.SHULKER_SHELL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.PURPLE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_STAINED_GLASS_PANE, 16).a('#', Blocks.PURPLE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.PURPLE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.PURPLE_WOOL).b(Items.PURPLE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_BLOCK, 4).a('F', Items.POPPED_CHORUS_FRUIT).a("FF").a("FF").a("has_chorus_fruit_popped", this.a(Items.POPPED_CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_PILLAR).a('#', Blocks.PURPUR_SLAB).a("#").a("#").a("has_purpur_block", this.a((IMaterial)Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_SLAB, 6).a('#', RecipeItemStack.a(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).a("###").a("has_purpur_block", this.a((IMaterial)Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).a("#  ").a("## ").a("###").a("has_purpur_block", this.a((IMaterial)Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_BLOCK).a('#', Items.QUARTZ).a("##").a("##").a("has_quartz", this.a(Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_SLAB, 6).a('#', RecipeItemStack.a(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).a("###").a("has_chiseled_quartz_block", this.a((IMaterial)Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", this.a((IMaterial)Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", this.a((IMaterial)Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).a("#  ").a("## ").a("###").a("has_chiseled_quartz_block", this.a((IMaterial)Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", this.a((IMaterial)Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", this.a((IMaterial)Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a(Items.RABBIT_STEW).a('P', Items.BAKED_POTATO).a('R', Items.COOKED_RABBIT).a('B', Items.BOWL).a('C', Items.CARROT).a('M', Blocks.BROWN_MUSHROOM).a(" R ").a("CPM").a(" B ").b("rabbit_stew").a("has_cooked_rabbit", this.a(Items.COOKED_RABBIT)).a(consumer, "rabbit_stew_from_brown_mushroom");
        DebugReportRecipeShaped.a(Items.RABBIT_STEW).a('P', Items.BAKED_POTATO).a('R', Items.COOKED_RABBIT).a('B', Items.BOWL).a('C', Items.CARROT).a('D', Blocks.RED_MUSHROOM).a(" R ").a("CPD").a(" B ").b("rabbit_stew").a("has_cooked_rabbit", this.a(Items.COOKED_RABBIT)).a(consumer, "rabbit_stew_from_red_mushroom");
        DebugReportRecipeShaped.a(Blocks.RAIL, 16).a('#', Items.STICK).a('X', Items.IRON_INGOT).a("X X").a("X#X").a("X X").a("has_minecart", this.a(Items.MINECART)).a(consumer);
        DebugReportRecipeShapeless.a(Items.REDSTONE, 9).b(Blocks.REDSTONE_BLOCK).a("has_redstone_block", this.a((IMaterial)Blocks.REDSTONE_BLOCK)).a("has_at_least_9_redstone", this.a(CriterionConditionValue.d.b(9), Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.REDSTONE_BLOCK).a('#', Items.REDSTONE).a("###").a("###").a("###").a("has_at_least_9_redstone", this.a(CriterionConditionValue.d.b(9), Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.REDSTONE_LAMP).a('R', Items.REDSTONE).a('G', Blocks.GLOWSTONE).a(" R ").a("RGR").a(" R ").a("has_glowstone", this.a((IMaterial)Blocks.GLOWSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.REDSTONE_TORCH).a('#', Items.STICK).a('X', Items.REDSTONE).a("X").a("#").a("has_redstone", this.a(Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.RED_BANNER).a('#', Blocks.RED_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_red_wool", this.a((IMaterial)Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.RED_BED).a('#', Blocks.RED_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_red_wool", this.a((IMaterial)Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.RED_BED).b(Items.WHITE_BED).b(Items.ROSE_RED).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "red_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.RED_CARPET, 3).a('#', Blocks.RED_WOOL).a("##").b("carpet").a("has_red_wool", this.a((IMaterial)Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.RED_CONCRETE_POWDER, 8).b(Items.ROSE_RED).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.ROSE_RED).b(Items.BEETROOT).a("red_dye").a("has_beetroot", this.a(Items.BEETROOT)).a(consumer, "red_dye_from_beetroot");
        DebugReportRecipeShapeless.a(Items.ROSE_RED).b(Blocks.POPPY).a("red_dye").a("has_red_flower", this.a((IMaterial)Blocks.POPPY)).a(consumer, "red_dye_from_poppy");
        DebugReportRecipeShapeless.a(Items.ROSE_RED, 2).b(Blocks.ROSE_BUSH).a("red_dye").a("has_double_plant", this.a((IMaterial)Blocks.ROSE_BUSH)).a(consumer, "red_dye_from_rose_bush");
        DebugReportRecipeShapeless.a(Items.ROSE_RED).b(Blocks.RED_TULIP).a("red_dye").a("has_red_flower", this.a((IMaterial)Blocks.RED_TULIP)).a(consumer, "red_dye_from_tulip");
        DebugReportRecipeShaped.a(Blocks.RED_NETHER_BRICKS).a('W', Items.NETHER_WART).a('N', Items.NETHER_BRICK).a("NW").a("WN").a("has_nether_wart", this.a(Items.NETHER_WART)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_SANDSTONE).a('#', Blocks.RED_SAND).a("##").a("##").a("has_sand", this.a((IMaterial)Blocks.RED_SAND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_SANDSTONE_SLAB, 6).a('#', RecipeItemStack.a(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).a("###").a("has_red_sandstone", this.a((IMaterial)Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", this.a((IMaterial)Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", this.a((IMaterial)Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_SANDSTONE_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).a("#  ").a("## ").a("###").a("has_red_sandstone", this.a((IMaterial)Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", this.a((IMaterial)Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", this.a((IMaterial)Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.ROSE_RED).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_STAINED_GLASS_PANE, 16).a('#', Blocks.RED_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.ROSE_RED).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.RED_WOOL).b(Items.ROSE_RED).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.REPEATER).a('#', Blocks.REDSTONE_TORCH).a('X', Items.REDSTONE).a('I', Blocks.STONE).a("#X#").a("III").a("has_redstone_torch", this.a((IMaterial)Blocks.REDSTONE_TORCH)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SANDSTONE).a('#', Blocks.SAND).a("##").a("##").a("has_sand", this.a((IMaterial)Blocks.SAND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SANDSTONE_SLAB, 6).a('#', RecipeItemStack.a(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).a("###").a("has_sandstone", this.a((IMaterial)Blocks.SANDSTONE)).a("has_chiseled_sandstone", this.a((IMaterial)Blocks.CHISELED_SANDSTONE)).a("has_cut_sandstone", this.a((IMaterial)Blocks.CUT_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SANDSTONE_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).a("#  ").a("## ").a("###").a("has_sandstone", this.a((IMaterial)Blocks.SANDSTONE)).a("has_chiseled_sandstone", this.a((IMaterial)Blocks.CHISELED_SANDSTONE)).a("has_cut_sandstone", this.a((IMaterial)Blocks.CUT_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SEA_LANTERN).a('S', Items.PRISMARINE_SHARD).a('C', Items.PRISMARINE_CRYSTALS).a("SCS").a("CCC").a("SCS").a("has_prismarine_crystals", this.a(Items.PRISMARINE_CRYSTALS)).a(consumer);
        DebugReportRecipeShaped.a(Items.SHEARS).a('#', Items.IRON_INGOT).a(" #").a("# ").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.SHIELD).a('W', TagsItem.PLANKS).a('o', Items.IRON_INGOT).a("WoW").a("WWW").a(" W ").a("has_iron_ingot", this.a(Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.SIGN, 3).a('#', TagsItem.PLANKS).a('X', Items.STICK).a("###").a("###").a(" X ").a("has_planks", this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SLIME_BLOCK).a('#', Items.SLIME_BALL).a("###").a("###").a("###").a("has_at_least_9_slime_ball", this.a(CriterionConditionValue.d.b(9), Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.SLIME_BALL, 9).b(Blocks.SLIME_BLOCK).a("has_at_least_9_slime_ball", this.a(CriterionConditionValue.d.b(9), Items.SLIME_BALL)).a("has_slime", this.a((IMaterial)Blocks.SLIME_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CUT_RED_SANDSTONE, 4).a('#', Blocks.RED_SANDSTONE).a("##").a("##").a("has_red_sandstone", this.a((IMaterial)Blocks.RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CUT_SANDSTONE, 4).a('#', Blocks.SANDSTONE).a("##").a("##").a("has_sandstone", this.a((IMaterial)Blocks.SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SNOW_BLOCK).a('#', Items.SNOWBALL).a("##").a("##").a("has_snowball", this.a(Items.SNOWBALL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SNOW, 6).a('#', Blocks.SNOW_BLOCK).a("###").a("has_snowball", this.a(Items.SNOWBALL)).a(consumer);
        DebugReportRecipeShaped.a(Items.GLISTERING_MELON_SLICE).a('#', Items.GOLD_NUGGET).a('X', Items.MELON_SLICE).a("###").a("#X#").a("###").a("has_melon", this.a(Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShaped.a(Items.SPECTRAL_ARROW, 2).a('#', Items.GLOWSTONE_DUST).a('X', Items.ARROW).a(" # ").a("#X#").a(" # ").a("has_glowstone_dust", this.a(Items.GLOWSTONE_DUST)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_WOOD, 3).a('#', Blocks.SPRUCE_LOG).a("##").a("##").b("bark").a("has_log", this.a((IMaterial)Blocks.SPRUCE_LOG)).a(consumer);
        DebugReportRecipeShaped.a(Items.SPRUCE_BOAT).a('#', Blocks.SPRUCE_PLANKS).a("# #").a("###").b("boat").a("in_water", this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.SPRUCE_BUTTON).b(Blocks.SPRUCE_PLANKS).a("wooden_button").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_DOOR, 3).a('#', Blocks.SPRUCE_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_FENCE, 3).a('#', Items.STICK).a('W', Blocks.SPRUCE_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_FENCE_GATE).a('#', Items.STICK).a('W', Blocks.SPRUCE_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.SPRUCE_PLANKS, 4).a(TagsItem.SPRUCE_LOGS).a("planks").a("has_log", this.a(TagsItem.SPRUCE_LOGS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_PRESSURE_PLATE).a('#', Blocks.SPRUCE_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_SLAB, 6).a('#', Blocks.SPRUCE_PLANKS).a("###").b("wooden_slab").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_STAIRS, 4).a('#', Blocks.SPRUCE_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_TRAPDOOR, 2).a('#', Blocks.SPRUCE_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", this.a((IMaterial)Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Items.STICK, 4).a('#', TagsItem.PLANKS).a("#").a("#").a("has_planks", this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STICKY_PISTON).a('P', Blocks.PISTON).a('S', Items.SLIME_BALL).a("S").a("P").a("has_slime_ball", this.a(Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICKS, 4).a('#', Blocks.STONE).a("##").a("##").a("has_stone", this.a((IMaterial)Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.STONE_AXE).a('#', Items.STICK).a('X', Blocks.COBBLESTONE).a("XX").a("X#").a(" #").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICK_SLAB, 6).a('#', RecipeItemStack.a(TagsItem.STONE_BRICKS)).a("###").a("has_stone_bricks", this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICK_STAIRS, 4).a('#', RecipeItemStack.a(TagsItem.STONE_BRICKS)).a("#  ").a("## ").a("###").a("has_stone_bricks", this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.STONE_BUTTON).b(Blocks.STONE).a("has_stone", this.a((IMaterial)Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.STONE_HOE).a('#', Items.STICK).a('X', Blocks.COBBLESTONE).a("XX").a(" #").a(" #").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.STONE_PICKAXE).a('#', Items.STICK).a('X', Blocks.COBBLESTONE).a("XXX").a(" # ").a(" # ").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_PRESSURE_PLATE).a('#', Blocks.STONE).a("##").a("has_stone", this.a((IMaterial)Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.STONE_SHOVEL).a('#', Items.STICK).a('X', Blocks.COBBLESTONE).a("X").a("#").a("#").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_SLAB, 6).a('#', Blocks.STONE).a("###").a("has_stone", this.a((IMaterial)Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_STAIRS, 4).a('#', Blocks.COBBLESTONE).a("#  ").a("## ").a("###").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.STONE_SWORD).a('#', Items.STICK).a('X', Blocks.COBBLESTONE).a("X").a("X").a("#").a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_WOOL).a('#', Items.STRING).a("##").a("##").a("has_string", this.a(Items.STRING)).a(consumer, "white_wool_from_string");
        DebugReportRecipeShapeless.a(Items.SUGAR).b(Blocks.SUGAR_CANE).a("has_reeds", this.a((IMaterial)Blocks.SUGAR_CANE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.TNT).a('#', RecipeItemStack.a(Blocks.SAND, Blocks.RED_SAND)).a('X', Items.GUNPOWDER).a("X#X").a("#X#").a("X#X").a("has_gunpowder", this.a(Items.GUNPOWDER)).a(consumer);
        DebugReportRecipeShaped.a(Items.TNT_MINECART).a('A', Blocks.TNT).a('B', Items.MINECART).a("A").a("B").a("has_minecart", this.a(Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.TORCH, 4).a('#', Items.STICK).a('X', RecipeItemStack.a(Items.COAL, Items.CHARCOAL)).a("X").a("#").a("has_stone_pickaxe", this.a(Items.STONE_PICKAXE)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.TRAPPED_CHEST).b(Blocks.CHEST).b(Blocks.TRIPWIRE_HOOK).a("has_tripwire_hook", this.a((IMaterial)Blocks.TRIPWIRE_HOOK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.TRIPWIRE_HOOK, 2).a('#', TagsItem.PLANKS).a('S', Items.STICK).a('I', Items.IRON_INGOT).a("I").a("S").a("#").a("has_string", this.a(Items.STRING)).a(consumer);
        DebugReportRecipeShaped.a(Items.TURTLE_HELMET).a('X', Items.SCUTE).a("XXX").a("X X").a("has_scute", this.a(Items.SCUTE)).a(consumer);
        DebugReportRecipeShapeless.a(Items.WHEAT, 9).b(Blocks.HAY_BLOCK).a("has_at_least_9_wheat", this.a(CriterionConditionValue.d.b(9), Items.WHEAT)).a("has_hay_block", this.a((IMaterial)Blocks.HAY_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Items.WHITE_BANNER).a('#', Blocks.WHITE_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.WHITE_BED).a('#', Blocks.WHITE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_CARPET, 3).a('#', Blocks.WHITE_WOOL).a("##").b("carpet").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.WHITE_CONCRETE_POWDER, 8).b(Items.BONE_MEAL).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.BONE_MEAL).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_STAINED_GLASS_PANE, 16).a('#', Blocks.WHITE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.BONE_MEAL).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShaped.a(Items.WOODEN_AXE).a('#', Items.STICK).a('X', TagsItem.PLANKS).a("XX").a("X#").a(" #").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_DOOR, 3).a('#', Blocks.OAK_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", this.a((IMaterial)Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Items.WOODEN_HOE).a('#', Items.STICK).a('X', TagsItem.PLANKS).a("XX").a(" #").a(" #").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Items.WOODEN_PICKAXE).a('#', Items.STICK).a('X', TagsItem.PLANKS).a("XXX").a(" # ").a(" # ").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Items.WOODEN_SHOVEL).a('#', Items.STICK).a('X', TagsItem.PLANKS).a("X").a("#").a("#").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Items.WOODEN_SWORD).a('#', Items.STICK).a('X', TagsItem.PLANKS).a("X").a("X").a("#").a("has_stick", this.a(Items.STICK)).a(consumer);
        DebugReportRecipeShapeless.a(Items.WRITABLE_BOOK).b(Items.BOOK).b(Items.INK_SAC).b(Items.FEATHER).a("has_book", this.a(Items.BOOK)).a(consumer);
        DebugReportRecipeShaped.a(Items.YELLOW_BANNER).a('#', Blocks.YELLOW_WOOL).a('|', Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_yellow_wool", this.a((IMaterial)Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.YELLOW_BED).a('#', Blocks.YELLOW_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_yellow_wool", this.a((IMaterial)Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.YELLOW_BED).b(Items.WHITE_BED).b(Items.DANDELION_YELLOW).a("dyed_bed").a("has_bed", this.a(Items.WHITE_BED)).a(consumer, "yellow_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.YELLOW_CARPET, 3).a('#', Blocks.YELLOW_WOOL).a("##").b("carpet").a("has_yellow_wool", this.a((IMaterial)Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.YELLOW_CONCRETE_POWDER, 8).b(Items.DANDELION_YELLOW).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", this.a((IMaterial)Blocks.SAND)).a("has_gravel", this.a((IMaterial)Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.DANDELION_YELLOW).b(Blocks.DANDELION).a("yellow_dye").a("has_yellow_flower", this.a((IMaterial)Blocks.DANDELION)).a(consumer, "yellow_dye_from_dandelion");
        DebugReportRecipeShapeless.a(Items.DANDELION_YELLOW, 2).b(Blocks.SUNFLOWER).a("yellow_dye").a("has_double_plant", this.a((IMaterial)Blocks.SUNFLOWER)).a(consumer, "yellow_dye_from_sunflower");
        DebugReportRecipeShaped.a(Blocks.YELLOW_STAINED_GLASS, 8).a('#', Blocks.GLASS).a('X', Items.DANDELION_YELLOW).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.YELLOW_STAINED_GLASS_PANE, 16).a('#', Blocks.YELLOW_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", this.a((IMaterial)Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.YELLOW_TERRACOTTA, 8).a('#', Blocks.TERRACOTTA).a('X', Items.DANDELION_YELLOW).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", this.a((IMaterial)Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.YELLOW_WOOL).b(Items.DANDELION_YELLOW).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", this.a((IMaterial)Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a(Items.DRIED_KELP, 9).b(Blocks.DRIED_KELP_BLOCK).a("has_at_least_9_dried_kelp", this.a(CriterionConditionValue.d.b(9), Items.DRIED_KELP)).a("has_dried_kelp_block", this.a((IMaterial)Blocks.DRIED_KELP_BLOCK)).a(consumer);
        DebugReportRecipeShapeless.a(Blocks.DRIED_KELP_BLOCK).b(Items.DRIED_KELP, 9).a("has_at_least_9_dried_kelp", this.a(CriterionConditionValue.d.b(9), Items.DRIED_KELP)).a("has_dried_kelp_block", this.a((IMaterial)Blocks.DRIED_KELP_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CONDUIT).a('#', Items.NAUTILUS_SHELL).a('X', Items.HEART_OF_THE_SEA).a("###").a("#X#").a("###").a("has_nautilus_core", this.a(Items.HEART_OF_THE_SEA)).a("has_nautilus_shell", this.a(Items.NAUTILUS_SHELL)).a(consumer);
        DebugReportRecipeSpecial.a(RecipeSerializers.c).a(consumer, "armor_dye");
        DebugReportRecipeSpecial.a(RecipeSerializers.m).a(consumer, "banner_add_pattern");
        DebugReportRecipeSpecial.a(RecipeSerializers.l).a(consumer, "banner_duplicate");
        DebugReportRecipeSpecial.a(RecipeSerializers.d).a(consumer, "book_cloning");
        DebugReportRecipeSpecial.a(RecipeSerializers.g).a(consumer, "firework_rocket");
        DebugReportRecipeSpecial.a(RecipeSerializers.h).a(consumer, "firework_star");
        DebugReportRecipeSpecial.a(RecipeSerializers.i).a(consumer, "firework_star_fade");
        DebugReportRecipeSpecial.a(RecipeSerializers.e).a(consumer, "map_cloning");
        DebugReportRecipeSpecial.a(RecipeSerializers.f).a(consumer, "map_extending");
        DebugReportRecipeSpecial.a(RecipeSerializers.j).a(consumer, "repair_item");
        DebugReportRecipeSpecial.a(RecipeSerializers.n).a(consumer, "shield_decoration");
        DebugReportRecipeSpecial.a(RecipeSerializers.o).a(consumer, "shulker_box_coloring");
        DebugReportRecipeSpecial.a(RecipeSerializers.k).a(consumer, "tipped_arrow");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.POTATO), Items.BAKED_POTATO, 0.35F, 200).a("has_potato", this.a(Items.POTATO)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CLAY_BALL), Items.BRICK, 0.3F, 200).a("has_clay_ball", this.a(Items.CLAY_BALL)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(TagsItem.LOGS), Items.CHARCOAL, 0.15F, 200).a("has_log", this.a(TagsItem.LOGS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT, 0.1F, 200).a("has_chorus_fruit", this.a(Items.CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.COAL_ORE.getItem()), Items.COAL, 0.1F, 200).a("has_coal_ore", this.a((IMaterial)Blocks.COAL_ORE)).a(consumer, "coal_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.BEEF), Items.COOKED_BEEF, 0.35F, 200).a("has_beef", this.a(Items.BEEF)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, 200).a("has_chicken", this.a(Items.CHICKEN)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.COD), Items.COOKED_COD, 0.35F, 200).a("has_cod", this.a(Items.COD)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.KELP), Items.DRIED_KELP, 0.1F, 200).a("has_kelp", this.a((IMaterial)Blocks.KELP)).a(consumer, "dried_kelp_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.SALMON), Items.COOKED_SALMON, 0.35F, 200).a("has_salmon", this.a(Items.SALMON)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, 200).a("has_mutton", this.a(Items.MUTTON)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, 200).a("has_porkchop", this.a(Items.PORKCHOP)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, 200).a("has_rabbit", this.a(Items.RABBIT)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.DIAMOND_ORE.getItem()), Items.DIAMOND, 1.0F, 200).a("has_diamond_ore", this.a((IMaterial)Blocks.DIAMOND_ORE)).a(consumer, "diamond_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LAPIS_ORE.getItem()), Items.LAPIS_LAZULI, 0.2F, 200).a("has_lapis_ore", this.a((IMaterial)Blocks.LAPIS_ORE)).a(consumer, "lapis_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.EMERALD_ORE.getItem()), Items.EMERALD, 1.0F, 200).a("has_emerald_ore", this.a((IMaterial)Blocks.EMERALD_ORE)).a(consumer, "emerald_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(TagsItem.SAND), Blocks.GLASS.getItem(), 0.1F, 200).a("has_sand", this.a(TagsItem.SAND)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GOLD_ORE.getItem()), Items.GOLD_INGOT, 1.0F, 200).a("has_gold_ore", this.a((IMaterial)Blocks.GOLD_ORE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.SEA_PICKLE.getItem()), Items.LIME_DYE, 0.1F, 200).a("has_sea_pickle", this.a((IMaterial)Blocks.SEA_PICKLE)).a(consumer, "lime_dye_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CACTUS.getItem()), Items.CACTUS_GREEN, 1.0F, 200).a("has_cactus", this.a((IMaterial)Blocks.CACTUS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1F, 200).a("has_golden_pickaxe", this.a(Items.GOLDEN_PICKAXE)).a("has_golden_shovel", this.a(Items.GOLDEN_SHOVEL)).a("has_golden_axe", this.a(Items.GOLDEN_AXE)).a("has_golden_hoe", this.a(Items.GOLDEN_HOE)).a("has_golden_sword", this.a(Items.GOLDEN_SWORD)).a("has_golden_helmet", this.a(Items.GOLDEN_HELMET)).a("has_golden_chestplate", this.a(Items.GOLDEN_CHESTPLATE)).a("has_golden_leggings", this.a(Items.GOLDEN_LEGGINGS)).a("has_golden_boots", this.a(Items.GOLDEN_BOOTS)).a("has_golden_horse_armor", this.a(Items.GOLDEN_HORSE_ARMOR)).a(consumer, "gold_nugget_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1F, 200).a("has_iron_pickaxe", this.a(Items.IRON_PICKAXE)).a("has_iron_shovel", this.a(Items.IRON_SHOVEL)).a("has_iron_axe", this.a(Items.IRON_AXE)).a("has_iron_hoe", this.a(Items.IRON_HOE)).a("has_iron_sword", this.a(Items.IRON_SWORD)).a("has_iron_helmet", this.a(Items.IRON_HELMET)).a("has_iron_chestplate", this.a(Items.IRON_CHESTPLATE)).a("has_iron_leggings", this.a(Items.IRON_LEGGINGS)).a("has_iron_boots", this.a(Items.IRON_BOOTS)).a("has_iron_horse_armor", this.a(Items.IRON_HORSE_ARMOR)).a("has_chainmail_helmet", this.a(Items.CHAINMAIL_HELMET)).a("has_chainmail_chestplate", this.a(Items.CHAINMAIL_CHESTPLATE)).a("has_chainmail_leggings", this.a(Items.CHAINMAIL_LEGGINGS)).a("has_chainmail_boots", this.a(Items.CHAINMAIL_BOOTS)).a(consumer, "iron_nugget_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.IRON_ORE.getItem()), Items.IRON_INGOT, 0.7F, 200).a("has_iron_ore", this.a(Blocks.IRON_ORE.getItem())).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CLAY), Blocks.TERRACOTTA.getItem(), 0.35F, 200).a("has_clay_block", this.a((IMaterial)Blocks.CLAY)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1F, 200).a("has_netherrack", this.a((IMaterial)Blocks.NETHERRACK)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 200).a("has_nether_quartz_ore", this.a((IMaterial)Blocks.NETHER_QUARTZ_ORE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7F, 200).a("has_redstone_ore", this.a((IMaterial)Blocks.REDSTONE_ORE)).a(consumer, "redstone_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.WET_SPONGE), Blocks.SPONGE.getItem(), 0.15F, 200).a("has_wet_sponge", this.a((IMaterial)Blocks.WET_SPONGE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.COBBLESTONE), Blocks.STONE.getItem(), 0.1F, 200).a("has_cobblestone", this.a((IMaterial)Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.getItem(), 0.1F, 200).a("has_stone_bricks", this.a((IMaterial)Blocks.STONE_BRICKS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_black_terracotta", this.a((IMaterial)Blocks.BLACK_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_blue_terracotta", this.a((IMaterial)Blocks.BLUE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_brown_terracotta", this.a((IMaterial)Blocks.BROWN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_cyan_terracotta", this.a((IMaterial)Blocks.CYAN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_gray_terracotta", this.a((IMaterial)Blocks.GRAY_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_green_terracotta", this.a((IMaterial)Blocks.GREEN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_light_blue_terracotta", this.a((IMaterial)Blocks.LIGHT_BLUE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_light_gray_terracotta", this.a((IMaterial)Blocks.LIGHT_GRAY_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_lime_terracotta", this.a((IMaterial)Blocks.LIME_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_magenta_terracotta", this.a((IMaterial)Blocks.MAGENTA_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_orange_terracotta", this.a((IMaterial)Blocks.ORANGE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_pink_terracotta", this.a((IMaterial)Blocks.PINK_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_purple_terracotta", this.a((IMaterial)Blocks.PURPLE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_red_terracotta", this.a((IMaterial)Blocks.RED_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_white_terracotta", this.a((IMaterial)Blocks.WHITE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_yellow_terracotta", this.a((IMaterial)Blocks.YELLOW_TERRACOTTA)).a(consumer);
    }

    private CriterionTriggerEnterBlock.b a(Block block) {
        return new CriterionTriggerEnterBlock.b(block, (Map)null);
    }

    private CriterionTriggerInventoryChanged.b a(CriterionConditionValue.d criterionconditionvalue$d, IMaterial imaterial) {
        return this.a(CriterionConditionItem.a.a().a(imaterial).a(criterionconditionvalue$d).b());
    }

    private CriterionTriggerInventoryChanged.b a(IMaterial imaterial) {
        return this.a(CriterionConditionItem.a.a().a(imaterial).b());
    }

    private CriterionTriggerInventoryChanged.b a(Tag<Item> tag) {
        return this.a(CriterionConditionItem.a.a().a(tag).b());
    }

    private CriterionTriggerInventoryChanged.b a(CriterionConditionItem... acriterionconditionitem) {
        return new CriterionTriggerInventoryChanged.b(CriterionConditionValue.d.e, CriterionConditionValue.d.e, CriterionConditionValue.d.e, acriterionconditionitem);
    }

    public String a() {
        return "Recipes";
    }
}
