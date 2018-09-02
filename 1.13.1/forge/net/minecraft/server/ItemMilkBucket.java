package net.minecraft.server;

public class ItemMilkBucket extends Item {
    public ItemMilkBucket(Item.Info item$info) {
        super(item$info);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
        if (entityliving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityliving;
            CriterionTriggers.z.a(entityplayer, itemstack);
            entityplayer.b(StatisticList.ITEM_USED.b(this));
        }

        if (entityliving instanceof EntityHuman && !((EntityHuman)entityliving).abilities.canInstantlyBuild) {
            itemstack.subtract(1);
        }

        if (!world.isClientSide) {
            entityliving.removeAllEffects();
        }

        return itemstack.isEmpty() ? new ItemStack(Items.BUCKET) : itemstack;
    }

    public int c(ItemStack var1) {
        return 32;
    }

    public EnumAnimation d(ItemStack var1) {
        return EnumAnimation.DRINK;
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        entityhuman.c(enumhand);
        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, entityhuman.b(enumhand));
    }
}
