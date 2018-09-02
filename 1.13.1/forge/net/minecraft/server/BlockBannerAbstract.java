package net.minecraft.server;

import javax.annotation.Nullable;

public abstract class BlockBannerAbstract extends BlockTileEntity {
    private final EnumColor a;

    protected BlockBannerAbstract(EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.a = enumcolor;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean a() {
        return true;
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityBanner(this.a);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.WHITE_BANNER;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
        return tileentity instanceof TileEntityBanner ? ((TileEntityBanner)tileentity).a(iblockdata) : super.a(iblockaccess, blockposition, iblockdata);
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float var4, int var5) {
        a(world, blockposition, this.a(world, blockposition, iblockdata));
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (tileentity instanceof TileEntityBanner) {
            a(world, blockposition, ((TileEntityBanner)tileentity).a(iblockdata));
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, (TileEntity)null, itemstack);
        }

    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, @Nullable EntityLiving var4, ItemStack itemstack) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityBanner) {
            ((TileEntityBanner)tileentity).a(itemstack, this.a);
        }

    }

    public EnumColor b() {
        return this.a;
    }
}
