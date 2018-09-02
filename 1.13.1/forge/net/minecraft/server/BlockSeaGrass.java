package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockSeaGrass extends BlockPlant implements IBlockFragilePlantElement, IFluidContainer {
    protected static final VoxelShape a = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    protected BlockSeaGrass(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return Block.a(iblockdata.h(iblockaccess, blockposition), EnumDirection.UP) && iblockdata.getBlock() != Blocks.MAGMA_BLOCK;
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return fluid.a(TagsFluid.WATER) && fluid.g() == 8 ? super.getPlacedState(blockactioncontext) : null;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        IBlockData iblockdata2 = super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        if (!iblockdata2.isAir()) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return iblockdata2;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(this));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, boolean var4) {
        return true;
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public Fluid h(IBlockData var1) {
        return FluidTypes.c.a(false);
    }

    public void b(World world, Random var2, BlockPosition blockposition, IBlockData var4) {
        IBlockData iblockdata = Blocks.TALL_SEAGRASS.getBlockData();
        IBlockData iblockdata1 = (IBlockData)iblockdata.set(BlockTallSeaGrass.c, BlockPropertyDoubleBlockHalf.UPPER);
        BlockPosition blockposition1 = blockposition.up();
        if (world.getType(blockposition1).getBlock() == Blocks.WATER) {
            world.setTypeAndData(blockposition, iblockdata, 2);
            world.setTypeAndData(blockposition1, iblockdata1, 2);
        }

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
