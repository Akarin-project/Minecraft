package net.minecraft.server;

public class BlockEnchantmentTable extends BlockTileEntity {
    protected static final VoxelShape a = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    protected BlockEnchantmentTable(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityEnchantTable();
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityEnchantTable) {
                entityhuman.openTileEntity((TileEntityEnchantTable)tileentity);
            }

            return true;
        }
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityEnchantTable) {
                ((TileEntityEnchantTable)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
