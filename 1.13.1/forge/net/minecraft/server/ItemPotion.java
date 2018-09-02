package net.minecraft.server;

public class ItemPotion extends Item {
    public ItemPotion(Item.Info item$info) {
        super(item$info);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
        EntityHuman entityhuman = entityliving instanceof EntityHuman ? (EntityHuman)entityliving : null;
        if (entityhuman == null || !entityhuman.abilities.canInstantlyBuild) {
            itemstack.subtract(1);
        }

        if (entityhuman instanceof EntityPlayer) {
            CriterionTriggers.z.a((EntityPlayer)entityhuman, itemstack);
        }

        if (!world.isClientSide) {
            for(MobEffect mobeffect : PotionUtil.getEffects(itemstack)) {
                if (mobeffect.getMobEffect().isInstant()) {
                    mobeffect.getMobEffect().applyInstantEffect(entityhuman, entityhuman, entityliving, mobeffect.getAmplifier(), 1.0D);
                } else {
                    entityliving.addEffect(new MobEffect(mobeffect));
                }
            }
        }

        if (entityhuman != null) {
            entityhuman.b(StatisticList.ITEM_USED.b(this));
        }

        if (entityhuman == null || !entityhuman.abilities.canInstantlyBuild) {
            if (itemstack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (entityhuman != null) {
                entityhuman.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return itemstack;
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

    public String h(ItemStack itemstack) {
        return PotionUtil.d(itemstack).b(this.getName() + ".effect.");
    }

    public void a(CreativeModeTab creativemodetab, NonNullList<ItemStack> nonnulllist) {
        if (this.a(creativemodetab)) {
            for(PotionRegistry potionregistry : IRegistry.POTION) {
                if (potionregistry != Potions.EMPTY) {
                    nonnulllist.add(PotionUtil.a(new ItemStack(this), potionregistry));
                }
            }
        }

    }
}
