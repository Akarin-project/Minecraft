package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportTagsItem extends DebugReportTags<Item> {
    private static final Logger e = LogManager.getLogger();

    public DebugReportTagsItem(DebugReportGenerator debugreportgenerator) {
        super(debugreportgenerator, IRegistry.ITEM);
    }

    protected void b() {
        this.a(TagsBlock.WOOL, TagsItem.WOOL);
        this.a(TagsBlock.PLANKS, TagsItem.PLANKS);
        this.a(TagsBlock.STONE_BRICKS, TagsItem.STONE_BRICKS);
        this.a(TagsBlock.WOODEN_BUTTONS, TagsItem.WOODEN_BUTTONS);
        this.a(TagsBlock.BUTTONS, TagsItem.BUTTONS);
        this.a(TagsBlock.CARPETS, TagsItem.CARPETS);
        this.a(TagsBlock.WOODEN_DOORS, TagsItem.WOODEN_DOORS);
        this.a(TagsBlock.WOODEN_STAIRS, TagsItem.WOODEN_STAIRS);
        this.a(TagsBlock.WOODEN_SLABS, TagsItem.WOODEN_SLABS);
        this.a(TagsBlock.WOODEN_PRESSURE_PLATES, TagsItem.WOODEN_PRESSURE_PLATES);
        this.a(TagsBlock.DOORS, TagsItem.DOORS);
        this.a(TagsBlock.SAPLINGS, TagsItem.SAPLINGS);
        this.a(TagsBlock.OAK_LOGS, TagsItem.OAK_LOGS);
        this.a(TagsBlock.DARK_OAK_LOGS, TagsItem.DARK_OAK_LOGS);
        this.a(TagsBlock.BIRCH_LOGS, TagsItem.BIRCH_LOGS);
        this.a(TagsBlock.ACACIA_LOGS, TagsItem.ACACIA_LOGS);
        this.a(TagsBlock.SPRUCE_LOGS, TagsItem.SPRUCE_LOGS);
        this.a(TagsBlock.JUNGLE_LOGS, TagsItem.JUNGLE_LOGS);
        this.a(TagsBlock.LOGS, TagsItem.LOGS);
        this.a(TagsBlock.SAND, TagsItem.SAND);
        this.a(TagsBlock.SLABS, TagsItem.SLABS);
        this.a(TagsBlock.STAIRS, TagsItem.STAIRS);
        this.a(TagsBlock.ANVIL, TagsItem.ANVIL);
        this.a(TagsBlock.RAILS, TagsItem.RAILS);
        this.a(TagsBlock.LEAVES, TagsItem.LEAVES);
        this.a(TagsBlock.WOODEN_TRAPDOORS, TagsItem.WOODEN_TRAPDOORS);
        this.a(TagsBlock.TRAPDOORS, TagsItem.TRAPDOORS);
        this.a(TagsItem.BANNERS).a(Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER, Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER, Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER, Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER);
        this.a(TagsItem.BOATS).a(Items.OAK_BOAT, Items.SPRUCE_BOAT, Items.BIRCH_BOAT, Items.JUNGLE_BOAT, Items.ACACIA_BOAT, Items.DARK_OAK_BOAT);
        this.a(TagsItem.FISHES).a(Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH);
    }

    protected void a(Tag<Block> tag, Tag<Item> tag1) {
        Tag.a tag$a = this.a(tag1);

        for(Tag.b tag$b : tag.b()) {
            Tag.b tag$b1 = this.a(tag$b);
            tag$a.a(tag$b1);
        }

    }

    private Tag.b<Item> a(Tag.b<Block> tag$b) {
        if (tag$b instanceof Tag.c) {
            return new Tag.c<Item>(((Tag.c)tag$b).a());
        } else if (tag$b instanceof Tag.d) {
            ArrayList arraylist = Lists.newArrayList();

            for(Block block : ((Tag.d)tag$b).a()) {
                Item item = block.getItem();
                if (item == Items.AIR) {
                    e.warn("Itemless block copied to item tag: {}", IRegistry.BLOCK.getKey(block));
                } else {
                    arraylist.add(item);
                }
            }

            return new Tag.d<Item>(arraylist);
        } else {
            throw new UnsupportedOperationException("Unknown tag entry " + tag$b);
        }
    }

    protected java.nio.file.Path a(MinecraftKey minecraftkey) {
        return this.b.b().resolve("data/" + minecraftkey.b() + "/tags/items/" + minecraftkey.getKey() + ".json");
    }

    public String a() {
        return "Item Tags";
    }

    protected void a(Tags<Item> tags) {
        TagsItem.a(tags);
    }
}
