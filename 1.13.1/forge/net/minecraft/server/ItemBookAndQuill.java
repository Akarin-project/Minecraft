package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemBookAndQuill extends Item {
    public ItemBookAndQuill(Item.Info item$info) {
        super(item$info);
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        entityhuman.a(itemstack, enumhand);
        entityhuman.b(StatisticList.ITEM_USED.b(this));
        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
    }

    public static boolean b(@Nullable NBTTagCompound nbttagcompound) {
        if (nbttagcompound == null) {
            return false;
        } else if (!nbttagcompound.hasKeyOfType("pages", 9)) {
            return false;
        } else {
            NBTTagList nbttaglist = nbttagcompound.getList("pages", 8);

            for(int i = 0; i < nbttaglist.size(); ++i) {
                String s = nbttaglist.getString(i);
                if (s.length() > 32767) {
                    return false;
                }
            }

            return true;
        }
    }
}
