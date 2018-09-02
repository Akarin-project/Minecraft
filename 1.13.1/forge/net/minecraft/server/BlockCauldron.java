package net.minecraft.server;

public class BlockCauldron extends Block {
    public static final BlockStateInteger LEVEL = BlockProperties.af;
    protected static final VoxelShape b = Block.a(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape c = VoxelShapes.a(VoxelShapes.b(), b, OperatorBoolean.ONLY_FIRST);

    public BlockCauldron(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(LEVEL, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    public boolean f(IBlockData var1) {
        return false;
    }

    public VoxelShape h(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
        int i = iblockdata.get(LEVEL);
        float f = (float)blockposition.getY() + (6.0F + (float)(3 * i)) / 16.0F;
        if (!world.isClientSide && entity.isBurning() && i > 0 && entity.getBoundingBox().b <= (double)f) {
            entity.extinguish();
            this.a(world, blockposition, iblockdata, i - 1);
        }

    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, EnumDirection var6, float var7, float var8, float var9) {
        ItemStack itemstack = entityhuman.b(enumhand);
        if (itemstack.isEmpty()) {
            return true;
        } else {
            int i = iblockdata.get(LEVEL);
            Item item = itemstack.getItem();
            if (item == Items.WATER_BUCKET) {
                if (i < 3 && !world.isClientSide) {
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        entityhuman.a(enumhand, new ItemStack(Items.BUCKET));
                    }

                    entityhuman.a(StatisticList.FILL_CAULDRON);
                    this.a(world, blockposition, iblockdata, 3);
                    world.a((EntityHuman)null, blockposition, SoundEffects.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return true;
            } else if (item == Items.BUCKET) {
                if (i == 3 && !world.isClientSide) {
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        itemstack.subtract(1);
                        if (itemstack.isEmpty()) {
                            entityhuman.a(enumhand, new ItemStack(Items.WATER_BUCKET));
                        } else if (!entityhuman.inventory.pickup(new ItemStack(Items.WATER_BUCKET))) {
                            entityhuman.drop(new ItemStack(Items.WATER_BUCKET), false);
                        }
                    }

                    entityhuman.a(StatisticList.USE_CAULDRON);
                    this.a(world, blockposition, iblockdata, 0);
                    world.a((EntityHuman)null, blockposition, SoundEffects.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return true;
            } else if (item == Items.GLASS_BOTTLE) {
                if (i > 0 && !world.isClientSide) {
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        ItemStack itemstack4 = PotionUtil.a(new ItemStack(Items.POTION), Potions.b);
                        entityhuman.a(StatisticList.USE_CAULDRON);
                        itemstack.subtract(1);
                        if (itemstack.isEmpty()) {
                            entityhuman.a(enumhand, itemstack4);
                        } else if (!entityhuman.inventory.pickup(itemstack4)) {
                            entityhuman.drop(itemstack4, false);
                        } else if (entityhuman instanceof EntityPlayer) {
                            ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
                        }
                    }

                    world.a((EntityHuman)null, blockposition, SoundEffects.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.a(world, blockposition, iblockdata, i - 1);
                }

                return true;
            } else if (item == Items.POTION && PotionUtil.d(itemstack) == Potions.b) {
                if (i < 3 && !world.isClientSide) {
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
                        entityhuman.a(StatisticList.USE_CAULDRON);
                        entityhuman.a(enumhand, itemstack3);
                        if (entityhuman instanceof EntityPlayer) {
                            ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
                        }
                    }

                    world.a((EntityHuman)null, blockposition, SoundEffects.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.a(world, blockposition, iblockdata, i + 1);
                }

                return true;
            } else {
                if (i > 0 && item instanceof ItemArmorColorable) {
                    ItemArmorColorable itemarmorcolorable = (ItemArmorColorable)item;
                    if (itemarmorcolorable.e(itemstack) && !world.isClientSide) {
                        itemarmorcolorable.g(itemstack);
                        this.a(world, blockposition, iblockdata, i - 1);
                        entityhuman.a(StatisticList.CLEAN_ARMOR);
                        return true;
                    }
                }

                if (i > 0 && item instanceof ItemBanner) {
                    if (TileEntityBanner.a(itemstack) > 0 && !world.isClientSide) {
                        ItemStack itemstack2 = itemstack.cloneItemStack();
                        itemstack2.setCount(1);
                        TileEntityBanner.b(itemstack2);
                        entityhuman.a(StatisticList.CLEAN_BANNER);
                        if (!entityhuman.abilities.canInstantlyBuild) {
                            itemstack.subtract(1);
                            this.a(world, blockposition, iblockdata, i - 1);
                        }

                        if (itemstack.isEmpty()) {
                            entityhuman.a(enumhand, itemstack2);
                        } else if (!entityhuman.inventory.pickup(itemstack2)) {
                            entityhuman.drop(itemstack2, false);
                        } else if (entityhuman instanceof EntityPlayer) {
                            ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
                        }
                    }

                    return true;
                } else if (i > 0 && item instanceof ItemBlock) {
                    Block block = ((ItemBlock)item).getBlock();
                    if (block instanceof BlockShulkerBox && !world.e()) {
                        ItemStack itemstack1 = new ItemStack(Blocks.SHULKER_BOX, 1);
                        if (itemstack.hasTag()) {
                            itemstack1.setTag(itemstack.getTag().clone());
                        }

                        entityhuman.a(enumhand, itemstack1);
                        this.a(world, blockposition, iblockdata, i - 1);
                        entityhuman.a(StatisticList.CLEAN_SHULKER_BOX);
                    }

                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
        world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(LEVEL, Integer.valueOf(MathHelper.clamp(i, 0, 3))), 2);
        world.updateAdjacentComparators(blockposition, this);
    }

    public void c(World world, BlockPosition blockposition) {
        if (world.random.nextInt(20) == 1) {
            float f = world.getBiome(blockposition).c(blockposition);
            if (!(f < 0.15F)) {
                IBlockData iblockdata = world.getType(blockposition);
                if (iblockdata.get(LEVEL) < 3) {
                    world.setTypeAndData(blockposition, (IBlockData)iblockdata.a(LEVEL), 2);
                }

            }
        }
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData iblockdata, World var2, BlockPosition var3) {
        return iblockdata.get(LEVEL);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(LEVEL);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.UP) {
            return EnumBlockFaceShape.BOWL;
        } else {
            return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.UNDEFINED : EnumBlockFaceShape.SOLID;
        }
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
