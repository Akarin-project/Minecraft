package net.minecraft.server;

import java.util.List;

public class ItemGlassBottle extends Item {
    public ItemGlassBottle(Item.Info item$info) {
        super(item$info);
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        List list = world.a(EntityAreaEffectCloud.class, entityhuman.getBoundingBox().g(2.0D), (entityareaeffectcloud1) -> {
            return entityareaeffectcloud1 != null && entityareaeffectcloud1.isAlive() && entityareaeffectcloud1.getSource() instanceof EntityEnderDragon;
        });
        ItemStack itemstack = entityhuman.b(enumhand);
        if (!list.isEmpty()) {
            EntityAreaEffectCloud entityareaeffectcloud = (EntityAreaEffectCloud)list.get(0);
            entityareaeffectcloud.setRadius(entityareaeffectcloud.getRadius() - 0.5F);
            world.a((EntityHuman)null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, this.a(itemstack, entityhuman, new ItemStack(Items.DRAGON_BREATH)));
        } else {
            MovingObjectPosition movingobjectposition = this.a(world, entityhuman, true);
            if (movingobjectposition == null) {
                return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.PASS, itemstack);
            } else {
                if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
                    BlockPosition blockposition = movingobjectposition.a();
                    if (!world.a(entityhuman, blockposition)) {
                        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.PASS, itemstack);
                    }

                    if (world.b(blockposition).a(TagsFluid.WATER)) {
                        world.a(entityhuman, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, this.a(itemstack, entityhuman, PotionUtil.a(new ItemStack(Items.POTION), Potions.b)));
                    }
                }

                return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.PASS, itemstack);
            }
        }
    }

    protected ItemStack a(ItemStack itemstack, EntityHuman entityhuman, ItemStack itemstack1) {
        itemstack.subtract(1);
        entityhuman.b(StatisticList.ITEM_USED.b(this));
        if (itemstack.isEmpty()) {
            return itemstack1;
        } else {
            if (!entityhuman.inventory.pickup(itemstack1)) {
                entityhuman.drop(itemstack1, false);
            }

            return itemstack;
        }
    }
}
