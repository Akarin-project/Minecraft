package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockTallSeaGrass extends BlockTallPlantShearable implements IFluidContainer {
    public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> c = BlockTallPlantShearable.b;
    protected static final VoxelShape o = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public BlockTallSeaGrass(Block block, Block.Info block$info) {
        super(block, block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return o;
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return Block.a(iblockdata.h(iblockaccess, blockposition), EnumDirection.UP) && iblockdata.getBlock() != Blocks.MAGMA_BLOCK;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return new ItemStack(Blocks.SEAGRASS);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = super.getPlacedState(blockactioncontext);
        if (iblockdata != null) {
            Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition().up());
            if (fluid.a(TagsFluid.WATER) && fluid.g() == 8) {
                return iblockdata;
            }
        }

        return null;
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        if (iblockdata.get(c) == BlockPropertyDoubleBlockHalf.UPPER) {
            IBlockData iblockdata1 = iworldreader.getType(blockposition.down());
            return iblockdata1.getBlock() == this && iblockdata1.get(c) == BlockPropertyDoubleBlockHalf.LOWER;
        } else {
            Fluid fluid = iworldreader.b(blockposition);
            return super.canPlace(iblockdata, iworldreader, blockposition) && fluid.a(TagsFluid.WATER) && fluid.g() == 8;
        }
    }

    public Fluid h(IBlockData var1) {
        return FluidTypes.c.a(false);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData var3, FluidType var4) {
        return false;
    }

    public boolean place(GeneratorAccess var1, BlockPosition var2, IBlockData var3, Fluid var4) {
        return false;
    }

    public int j(IBlockData var1, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return Blocks.WATER.getBlockData().b(iblockaccess, blockposition);
    }
}
