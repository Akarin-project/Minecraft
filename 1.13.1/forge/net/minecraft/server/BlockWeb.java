package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockWeb extends Block {
    public BlockWeb(Block.Info block$info) {
        super(block$info);
    }

    public void a(IBlockData var1, World var2, BlockPosition var3, Entity entity) {
        entity.bh();
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.STRING;
    }

    protected boolean X_() {
        return true;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(this));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
