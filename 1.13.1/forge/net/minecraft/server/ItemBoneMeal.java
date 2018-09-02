package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemBoneMeal extends ItemDye {
    public ItemBoneMeal(EnumColor enumcolor, Item.Info item$info) {
        super(enumcolor, item$info);
    }

    public EnumInteractionResult a(ItemActionContext itemactioncontext) {
        World world = itemactioncontext.getWorld();
        BlockPosition blockposition = itemactioncontext.getClickPosition();
        BlockPosition blockposition1 = blockposition.shift(itemactioncontext.getClickedFace());
        if (a(itemactioncontext.getItemStack(), world, blockposition)) {
            if (!world.isClientSide) {
                world.triggerEffect(2005, blockposition, 0);
            }

            return EnumInteractionResult.SUCCESS;
        } else {
            IBlockData iblockdata = world.getType(blockposition);
            boolean flag = iblockdata.c(world, blockposition1, itemactioncontext.getClickedFace()) == EnumBlockFaceShape.SOLID;
            if (flag && a(itemactioncontext.getItemStack(), world, blockposition1, itemactioncontext.getClickedFace())) {
                if (!world.isClientSide) {
                    world.triggerEffect(2005, blockposition1, 0);
                }

                return EnumInteractionResult.SUCCESS;
            } else {
                return EnumInteractionResult.PASS;
            }
        }
    }

    public static boolean a(ItemStack itemstack, World world, BlockPosition blockposition) {
        IBlockData iblockdata = world.getType(blockposition);
        if (iblockdata.getBlock() instanceof IBlockFragilePlantElement) {
            IBlockFragilePlantElement iblockfragileplantelement = (IBlockFragilePlantElement)iblockdata.getBlock();
            if (iblockfragileplantelement.a(world, blockposition, iblockdata, world.isClientSide)) {
                if (!world.isClientSide) {
                    if (iblockfragileplantelement.a(world, world.random, blockposition, iblockdata)) {
                        iblockfragileplantelement.b(world, world.random, blockposition, iblockdata);
                    }

                    itemstack.subtract(1);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean a(ItemStack itemstack, World world, BlockPosition blockposition, @Nullable EnumDirection enumdirection) {
        if (world.getType(blockposition).getBlock() == Blocks.WATER && world.b(blockposition).g() == 8) {
            if (!world.isClientSide) {
                label79:
                for(int i = 0; i < 128; ++i) {
                    BlockPosition blockposition1 = blockposition;
                    BiomeBase biomebase = world.getBiome(blockposition);
                    IBlockData iblockdata = Blocks.SEAGRASS.getBlockData();

                    for(int j = 0; j < i / 16; ++j) {
                        blockposition1 = blockposition1.a(i.nextInt(3) - 1, (i.nextInt(3) - 1) * i.nextInt(3) / 2, i.nextInt(3) - 1);
                        biomebase = world.getBiome(blockposition1);
                        if (world.getType(blockposition1).k()) {
                            continue label79;
                        }
                    }

                    if (biomebase == Biomes.T || biomebase == Biomes.W) {
                        if (i == 0 && enumdirection != null && enumdirection.k().c()) {
                            iblockdata = (IBlockData)((Block)TagsBlock.WALL_CORALS.a(world.random)).getBlockData().set(BlockCoralFanWallAbstract.a, enumdirection);
                        } else if (i.nextInt(4) == 0) {
                            iblockdata = ((Block)TagsBlock.UNDERWATER_BONEMEALS.a(i)).getBlockData();
                        }
                    }

                    if (iblockdata.getBlock().a(TagsBlock.WALL_CORALS)) {
                        for(int k = 0; !iblockdata.canPlace(world, blockposition1) && k < 4; ++k) {
                            iblockdata = (IBlockData)iblockdata.set(BlockCoralFanWallAbstract.a, EnumDirection.EnumDirectionLimit.HORIZONTAL.a(i));
                        }
                    }

                    if (iblockdata.canPlace(world, blockposition1)) {
                        IBlockData iblockdata1 = world.getType(blockposition1);
                        if (iblockdata1.getBlock() == Blocks.WATER && world.b(blockposition1).g() == 8) {
                            world.setTypeAndData(blockposition1, iblockdata, 3);
                        } else if (iblockdata1.getBlock() == Blocks.SEAGRASS && i.nextInt(10) == 0) {
                            ((IBlockFragilePlantElement)Blocks.SEAGRASS).b(world, i, blockposition1, iblockdata1);
                        }
                    }
                }

                itemstack.subtract(1);
            }

            return true;
        } else {
            return false;
        }
    }
}
