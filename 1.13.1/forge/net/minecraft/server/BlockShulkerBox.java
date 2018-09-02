package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockShulkerBox extends BlockTileEntity {
    public static final BlockStateEnum<EnumDirection> a = BlockDirectional.FACING;
    @Nullable
    public final EnumColor color;

    public BlockShulkerBox(@Nullable EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.color = enumcolor;
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.UP));
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityShulkerBox(this.color);
    }

    public boolean q(IBlockData var1) {
        return true;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else if (entityhuman.isSpectator()) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityShulkerBox) {
                EnumDirection enumdirection = (EnumDirection)iblockdata.get(a);
                boolean flag;
                if (((TileEntityShulkerBox)tileentity).r() == TileEntityShulkerBox.AnimationPhase.CLOSED) {
                    AxisAlignedBB axisalignedbb = VoxelShapes.b().a().b((double)(0.5F * (float)enumdirection.getAdjacentX()), (double)(0.5F * (float)enumdirection.getAdjacentY()), (double)(0.5F * (float)enumdirection.getAdjacentZ())).a((double)enumdirection.getAdjacentX(), (double)enumdirection.getAdjacentY(), (double)enumdirection.getAdjacentZ());
                    flag = world.getCubes((Entity)null, axisalignedbb.a(blockposition.shift(enumdirection)));
                } else {
                    flag = true;
                }

                if (flag) {
                    entityhuman.a(StatisticList.OPEN_SHULKER_BOX);
                    entityhuman.openContainer((IInventory)tileentity);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(a, blockactioncontext.getClickedFace());
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        if (world.getTileEntity(blockposition) instanceof TileEntityShulkerBox) {
            TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)world.getTileEntity(blockposition);
            tileentityshulkerbox.a(entityhuman.abilities.canInstantlyBuild);
            tileentityshulkerbox.d(entityhuman);
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    public void dropNaturally(IBlockData var1, World var2, BlockPosition var3, float var4, int var5) {
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityShulkerBox) {
                ((TileEntityShulkerBox)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityShulkerBox) {
                TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)tileentity;
                if (!tileentityshulkerbox.s() && tileentityshulkerbox.G()) {
                    ItemStack itemstack = new ItemStack(this);
                    itemstack.getOrCreateTag().set("BlockEntityTag", ((TileEntityShulkerBox)tileentity).g(new NBTTagCompound()));
                    if (tileentityshulkerbox.hasCustomName()) {
                        itemstack.a(tileentityshulkerbox.getCustomName());
                        tileentityshulkerbox.setCustomName((IChatBaseComponent)null);
                    }

                    a(world, blockposition, itemstack);
                }

                world.updateAdjacentComparators(blockposition, iblockdata.getBlock());
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.DESTROY;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
        return tileentity instanceof TileEntityShulkerBox ? VoxelShapes.a(((TileEntityShulkerBox)tileentity).a(iblockdata)) : VoxelShapes.b();
    }

    public boolean f(IBlockData var1) {
        return false;
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        return Container.b((IInventory)world.getTileEntity(blockposition));
    }

    public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
        ItemStack itemstack = super.a(iblockaccess, blockposition, iblockdata);
        TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)iblockaccess.getTileEntity(blockposition);
        NBTTagCompound nbttagcompound = tileentityshulkerbox.g(new NBTTagCompound());
        if (!nbttagcompound.isEmpty()) {
            itemstack.a("BlockEntityTag", nbttagcompound);
        }

        return itemstack;
    }

    public static Block a(EnumColor enumcolor) {
        if (enumcolor == null) {
            return Blocks.SHULKER_BOX;
        } else {
            switch(enumcolor) {
            case WHITE:
                return Blocks.WHITE_SHULKER_BOX;
            case ORANGE:
                return Blocks.ORANGE_SHULKER_BOX;
            case MAGENTA:
                return Blocks.MAGENTA_SHULKER_BOX;
            case LIGHT_BLUE:
                return Blocks.LIGHT_BLUE_SHULKER_BOX;
            case YELLOW:
                return Blocks.YELLOW_SHULKER_BOX;
            case LIME:
                return Blocks.LIME_SHULKER_BOX;
            case PINK:
                return Blocks.PINK_SHULKER_BOX;
            case GRAY:
                return Blocks.GRAY_SHULKER_BOX;
            case LIGHT_GRAY:
                return Blocks.LIGHT_GRAY_SHULKER_BOX;
            case CYAN:
                return Blocks.CYAN_SHULKER_BOX;
            case PURPLE:
            default:
                return Blocks.PURPLE_SHULKER_BOX;
            case BLUE:
                return Blocks.BLUE_SHULKER_BOX;
            case BROWN:
                return Blocks.BROWN_SHULKER_BOX;
            case GREEN:
                return Blocks.GREEN_SHULKER_BOX;
            case RED:
                return Blocks.RED_SHULKER_BOX;
            case BLACK:
                return Blocks.BLACK_SHULKER_BOX;
            }
        }
    }

    public static ItemStack b(EnumColor enumcolor) {
        return new ItemStack(a(enumcolor));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, enumblockrotation.a((EnumDirection)iblockdata.get(a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(a)));
    }

    public EnumBlockFaceShape a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition, EnumDirection enumdirection) {
        EnumDirection enumdirection1 = (EnumDirection)iblockdata.get(a);
        TileEntityShulkerBox.AnimationPhase tileentityshulkerbox$animationphase = ((TileEntityShulkerBox)iblockaccess.getTileEntity(blockposition)).r();
        return tileentityshulkerbox$animationphase != TileEntityShulkerBox.AnimationPhase.CLOSED && (tileentityshulkerbox$animationphase != TileEntityShulkerBox.AnimationPhase.OPENED || enumdirection1 != enumdirection.opposite() && enumdirection1 != enumdirection) ? EnumBlockFaceShape.UNDEFINED : EnumBlockFaceShape.SOLID;
    }
}
