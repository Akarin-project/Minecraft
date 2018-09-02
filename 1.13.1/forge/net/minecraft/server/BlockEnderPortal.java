package net.minecraft.server;

import java.util.Random;

public class BlockEnderPortal extends BlockTileEntity {
    protected static final VoxelShape a = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    protected BlockEnderPortal(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityEnderPortal();
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
        if (!world.isClientSide && !entity.isPassenger() && !entity.isVehicle() && entity.bm() && VoxelShapes.c(VoxelShapes.a(entity.getBoundingBox().d((double)(-blockposition.getX()), (double)(-blockposition.getY()), (double)(-blockposition.getZ()))), iblockdata.g(world, blockposition), OperatorBoolean.AND)) {
            entity.a(DimensionManager.THE_END);
        }

    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
