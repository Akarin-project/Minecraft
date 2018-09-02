package net.minecraft.server;

public class BlockCoralFanAbstract extends BlockCoralBase {
    private static final VoxelShape a = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    protected BlockCoralFanAbstract(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }
}
