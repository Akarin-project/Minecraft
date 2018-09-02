package net.minecraft.server;

public class BlockStructureVoid extends Block {
    private static final VoxelShape a = Block.a(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    protected BlockStructureVoid(Block.Info block$info) {
        super(block$info);
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.INVISIBLE;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void dropNaturally(IBlockData var1, World var2, BlockPosition var3, float var4, int var5) {
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.DESTROY;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
