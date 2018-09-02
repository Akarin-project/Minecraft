package net.minecraft.server;

import java.util.Random;

public class BlockReed extends Block {
    public static final BlockStateInteger AGE = BlockProperties.X;
    protected static final VoxelShape b = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    protected BlockReed(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(AGE, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (iblockdata.canPlace(world, blockposition) && world.isEmpty(blockposition.up())) {
            int i;
            for(i = 1; world.getType(blockposition.down(i)).getBlock() == this; ++i) {
                ;
            }

            if (i < 3) {
                int j = iblockdata.get(AGE);
                if (j == 15) {
                    world.setTypeUpdate(blockposition.up(), this.getBlockData());
                    world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(AGE, Integer.valueOf(0)), 4);
                } else {
                    world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(AGE, Integer.valueOf(j + 1)), 4);
                }
            }
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        Block block = iworldreader.getType(blockposition.down()).getBlock();
        if (block == this) {
            return true;
        } else {
            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.SAND || block == Blocks.RED_SAND) {
                BlockPosition blockposition1 = blockposition.down();

                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    IBlockData iblockdata = iworldreader.getType(blockposition1.shift(enumdirection));
                    Fluid fluid = iworldreader.b(blockposition1.shift(enumdirection));
                    if (fluid.a(TagsFluid.WATER) || iblockdata.getBlock() == Blocks.FROSTED_ICE) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(AGE);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
