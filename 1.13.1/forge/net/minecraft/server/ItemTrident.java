package net.minecraft.server;

import com.google.common.collect.Multimap;

public class ItemTrident extends Item {
    public ItemTrident(Item.Info item$info) {
        super(item$info);
        this.a(new MinecraftKey("throwing"), (itemstack, var1, entityliving) -> {
            return entityliving != null && entityliving.isHandRaised() && entityliving.cW() == itemstack ? 1.0F : 0.0F;
        });
    }

    public boolean a(IBlockData var1, World var2, BlockPosition var3, EntityHuman entityhuman) {
        return !entityhuman.u();
    }

    public EnumAnimation d(ItemStack var1) {
        return EnumAnimation.SPEAR;
    }

    public int c(ItemStack var1) {
        return 72000;
    }

    public void a(ItemStack itemstack, World world, EntityLiving entityliving, int i) {
        if (entityliving instanceof EntityHuman) {
            EntityHuman entityhuman = (EntityHuman)entityliving;
            int j = this.c(itemstack) - i;
            if (j >= 10) {
                int k = EnchantmentManager.g(itemstack);
                if (k <= 0 || entityhuman.ao()) {
                    if (!world.isClientSide) {
                        itemstack.damage(1, entityhuman);
                        if (k == 0) {
                            EntityThrownTrident entitythrowntrident = new EntityThrownTrident(world, entityhuman, itemstack);
                            entitythrowntrident.a(entityhuman, entityhuman.pitch, entityhuman.yaw, 0.0F, 2.5F + (float)k * 0.5F, 1.0F);
                            if (entityhuman.abilities.canInstantlyBuild) {
                                entitythrowntrident.fromPlayer = EntityArrow.PickupStatus.CREATIVE_ONLY;
                            }

                            world.addEntity(entitythrowntrident);
                            if (!entityhuman.abilities.canInstantlyBuild) {
                                entityhuman.inventory.f(itemstack);
                            }
                        }
                    }

                    entityhuman.b(StatisticList.ITEM_USED.b(this));
                    SoundEffect soundeffect = SoundEffects.ITEM_TRIDENT_THROW;
                    if (k > 0) {
                        float f = entityhuman.yaw;
                        float f1 = entityhuman.pitch;
                        float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                        float f3 = -MathHelper.sin(f1 * ((float)Math.PI / 180F));
                        float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                        float f5 = MathHelper.c(f2 * f2 + f3 * f3 + f4 * f4);
                        float f6 = 3.0F * ((1.0F + (float)k) / 4.0F);
                        f2 = f2 * (f6 / f5);
                        f3 = f3 * (f6 / f5);
                        f4 = f4 * (f6 / f5);
                        entityhuman.f((double)f2, (double)f3, (double)f4);
                        if (k >= 3) {
                            soundeffect = SoundEffects.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (k == 2) {
                            soundeffect = SoundEffects.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundeffect = SoundEffects.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        entityhuman.o(20);
                        if (entityhuman.onGround) {
                            float f7 = 1.1999999F;
                            entityhuman.move(EnumMoveType.SELF, 0.0D, (double)1.1999999F, 0.0D);
                        }
                    }

                    world.a((EntityHuman)null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, soundeffect, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }

    public InteractionResultWrapper<ItemStack> a(World var1, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        if (itemstack.getDamage() >= itemstack.h()) {
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        } else if (EnchantmentManager.g(itemstack) > 0 && !entityhuman.ao()) {
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        } else {
            entityhuman.c(enumhand);
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
        }
    }

    public boolean a(ItemStack itemstack, EntityLiving var2, EntityLiving entityliving) {
        itemstack.damage(1, entityliving);
        return true;
    }

    public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
        if ((double)iblockdata.e(world, blockposition) != 0.0D) {
            itemstack.damage(2, entityliving);
        }

        return true;
    }

    public Multimap<String, AttributeModifier> a(EnumItemSlot enumitemslot) {
        Multimap multimap = super.a(enumitemslot);
        if (enumitemslot == EnumItemSlot.MAINHAND) {
            multimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(g, "Tool modifier", 8.0D, 0));
            multimap.put(GenericAttributes.g.getName(), new AttributeModifier(h, "Tool modifier", (double)-2.9F, 0));
        }

        return multimap;
    }

    public int c() {
        return 1;
    }
}
