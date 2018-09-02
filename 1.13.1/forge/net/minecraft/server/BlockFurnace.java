package net.minecraft.server;

public class BlockFurnace extends BlockTileEntity {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean LIT = BlockRedstoneTorch.LIT;

    protected BlockFurnace(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(LIT, Boolean.valueOf(false)));
    }

    public int m(IBlockData iblockdata) {
        return iblockdata.get(LIT) ? super.m(iblockdata) : 0;
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityFurnace) {
                entityhuman.openContainer((TileEntityFurnace)tileentity);
                entityhuman.a(StatisticList.INTERACT_WITH_FURNACE);
            }

            return true;
        }
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityFurnace();
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().opposite());
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityFurnace) {
                ((TileEntityFurnace)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityFurnace) {
                InventoryUtils.dropInventory(world, blockposition, (TileEntityFurnace)tileentity);
                world.updateAdjacentComparators(blockposition, this);
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

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, LIT);
    }
}
