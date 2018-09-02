package net.minecraft.server;

public class BlockWaterLily extends BlockPlant {
    protected static final VoxelShape a = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    protected BlockWaterLily(Block.Info block$info) {
        super(block$info);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
        super.a(iblockdata, world, blockposition, entity);
        if (entity instanceof EntityBoat) {
            world.setAir(new BlockPosition(blockposition), true);
        }

    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        Fluid fluid = iblockaccess.b(blockposition);
        return fluid.c() == FluidTypes.c || iblockdata.getMaterial() == Material.ICE;
    }
}
