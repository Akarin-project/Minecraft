package net.minecraft.server;

import java.util.Random;

public class BlockCactus extends Block {
    public static final BlockStateInteger AGE = BlockProperties.X;
    protected static final VoxelShape b = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape c = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    protected BlockCactus(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(AGE, Integer.valueOf(0)));
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!iblockdata.canPlace(world, blockposition)) {
            world.setAir(blockposition, true);
        } else {
            BlockPosition blockposition1 = blockposition.up();
            if (world.isEmpty(blockposition1)) {
                int i;
                for(i = 1; world.getType(blockposition.down(i)).getBlock() == this; ++i) {
                    ;
                }

                if (i < 3) {
                    int j = iblockdata.get(AGE);
                    if (j == 15) {
                        world.setTypeUpdate(blockposition1, this.getBlockData());
                        IBlockData iblockdata1 = (IBlockData)iblockdata.set(AGE, Integer.valueOf(0));
                        world.setTypeAndData(blockposition, iblockdata1, 4);
                        iblockdata1.doPhysics(world, blockposition1, this, blockposition);
                    } else {
                        world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(AGE, Integer.valueOf(j + 1)), 4);
                    }

                }
            }
        }
    }

    public VoxelShape f(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    public boolean f(IBlockData var1) {
        return true;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 1);
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            IBlockData iblockdata = iworldreader.getType(blockposition.shift(enumdirection));
            Material material = iblockdata.getMaterial();
            if (material.isBuildable() || iworldreader.b(blockposition.shift(enumdirection)).a(TagsFluid.LAVA)) {
                return false;
            }
        }

        Block block = iworldreader.getType(blockposition.down()).getBlock();
        return (block == Blocks.CACTUS || block == Blocks.SAND || block == Blocks.RED_SAND) && !iworldreader.getType(blockposition.up()).getMaterial().isLiquid();
    }

    public void a(IBlockData var1, World var2, BlockPosition var3, Entity entity) {
        entity.damageEntity(DamageSource.CACTUS, 1.0F);
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

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
