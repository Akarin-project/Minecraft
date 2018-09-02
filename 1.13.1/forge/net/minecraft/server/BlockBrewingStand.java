package net.minecraft.server;

public class BlockBrewingStand extends BlockTileEntity {
    public static final BlockStateBoolean[] HAS_BOTTLE = new BlockStateBoolean[]{BlockProperties.i, BlockProperties.j, BlockProperties.k};
    protected static final VoxelShape b = VoxelShapes.a(Block.a(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D), Block.a(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));

    public BlockBrewingStand(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(HAS_BOTTLE[0], Boolean.valueOf(false))).set(HAS_BOTTLE[1], Boolean.valueOf(false))).set(HAS_BOTTLE[2], Boolean.valueOf(false)));
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityBrewingStand();
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBrewingStand) {
                entityhuman.openContainer((TileEntityBrewingStand)tileentity);
                entityhuman.a(StatisticList.INTERACT_WITH_BREWINGSTAND);
            }

            return true;
        }
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBrewingStand) {
                InventoryUtils.dropInventory(world, blockposition, (TileEntityBrewingStand)tileentity);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        return Container.a(world.getTileEntity(blockposition));
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
