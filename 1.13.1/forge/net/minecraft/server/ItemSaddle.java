package net.minecraft.server;

public class ItemSaddle extends Item {
    public ItemSaddle(Item.Info item$info) {
        super(item$info);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand var4) {
        if (entityliving instanceof EntityPig) {
            EntityPig entitypig = (EntityPig)entityliving;
            if (!entitypig.hasSaddle() && !entitypig.isBaby()) {
                entitypig.setSaddle(true);
                entitypig.world.a(entityhuman, entitypig.locX, entitypig.locY, entitypig.locZ, SoundEffects.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                itemstack.subtract(1);
            }

            return true;
        } else {
            return false;
        }
    }
}
