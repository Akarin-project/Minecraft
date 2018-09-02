package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemKnowledgeBook extends Item {
    private static final Logger a = LogManager.getLogger();

    public ItemKnowledgeBook(Item.Info item$info) {
        super(item$info);
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        NBTTagCompound nbttagcompound = itemstack.getTag();
        if (!entityhuman.abilities.canInstantlyBuild) {
            entityhuman.a(enumhand, ItemStack.a);
        }

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Recipes", 9)) {
            if (!world.isClientSide) {
                NBTTagList nbttaglist = nbttagcompound.getList("Recipes", 8);
                ArrayList arraylist = Lists.newArrayList();

                for(int i = 0; i < nbttaglist.size(); ++i) {
                    String s = nbttaglist.getString(i);
                    IRecipe irecipe = world.getMinecraftServer().getCraftingManager().a(new MinecraftKey(s));
                    if (irecipe == null) {
                        a.error("Invalid recipe: {}", s);
                        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
                    }

                    arraylist.add(irecipe);
                }

                entityhuman.a(arraylist);
                entityhuman.b(StatisticList.ITEM_USED.b(this));
            }

            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            a.error("Tag not valid: {}", nbttagcompound);
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        }
    }
}
