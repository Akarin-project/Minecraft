package net.minecraft.server;

import java.util.Random;

public class BlockCoralPlant extends BlockCoralBase {
    private final Block c;
    protected static final VoxelShape a = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    protected BlockCoralPlant(Block block, Block.Info block$info) {
        super(block$info);
        this.c = block;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData var4) {
        this.a(iblockdata, world, blockposition);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!b_(iblockdata, world, blockposition)) {
            world.setTypeAndData(blockposition, (IBlockData)this.c.getBlockData().set(b, Boolean.valueOf(false)), 2);
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            this.a(iblockdata, generatoraccess, blockposition);
            if (iblockdata.get(b)) {
                generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            }

            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }
}
