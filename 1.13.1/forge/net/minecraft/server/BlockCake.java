package net.minecraft.server;

public class BlockCake extends Block {
    public static final BlockStateInteger BITES = BlockProperties.Z;
    protected static final VoxelShape[] b = new VoxelShape[]{Block.a(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.a(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};

    protected BlockCake(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(BITES, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return b[iblockdata.get(BITES)];
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, EnumDirection var6, float var7, float var8, float var9) {
        if (!world.isClientSide) {
            return this.a(world, blockposition, iblockdata, entityhuman);
        } else {
            ItemStack itemstack = entityhuman.b(enumhand);
            return this.a(world, blockposition, iblockdata, entityhuman) || itemstack.isEmpty();
        }
    }

    private boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        if (!entityhuman.q(false)) {
            return false;
        } else {
            entityhuman.a(StatisticList.EAT_CAKE_SLICE);
            entityhuman.getFoodData().eat(2, 0.1F);
            int i = iblockdata.get(BITES);
            if (i < 6) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                generatoraccess.setAir(blockposition);
            }

            return true;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.down()).getMaterial().isBuildable();
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(BITES);
    }

    public int a(IBlockData iblockdata, World var2, BlockPosition var3) {
        return (7 - iblockdata.get(BITES)) * 2;
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
