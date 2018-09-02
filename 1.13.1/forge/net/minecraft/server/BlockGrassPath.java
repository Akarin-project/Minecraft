package net.minecraft.server;

import java.util.Random;

public class BlockGrassPath extends Block {
    protected static final VoxelShape a = BlockSoil.b;

    protected BlockGrassPath(Block.Info block$info) {
        super(block$info);
    }

    public int j(IBlockData var1, IBlockAccess iblockaccess, BlockPosition var3) {
        return iblockaccess.K();
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return !this.getBlockData().canPlace(blockactioncontext.getWorld(), blockactioncontext.getClickPosition()) ? Block.a(this.getBlockData(), Blocks.DIRT.getBlockData(), blockactioncontext.getWorld(), blockactioncontext.getClickPosition()) : super.getPlacedState(blockactioncontext);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == EnumDirection.UP && !iblockdata.canPlace(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 1);
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        BlockSoil.b(iblockdata, world, blockposition);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.up());
        return !iblockdata.getMaterial().isBuildable() || iblockdata.getBlock() instanceof BlockFenceGate;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.DIRT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
