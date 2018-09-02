package net.minecraft.server;

public class BlockKelpPlant extends Block implements IFluidContainer {
    final BlockKelp a;

    protected BlockKelpPlant(BlockKelp blockkelp, Block.Info block$info) {
        super(block$info);
        this.a = blockkelp;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public Fluid h(IBlockData var1) {
        return FluidTypes.c.a(false);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            if (enumdirection == EnumDirection.UP) {
                Block block = iblockdata1.getBlock();
                if (block != this && block != this.a) {
                    return this.a.a(generatoraccess);
                }
            }

            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();
        IBlockData iblockdata = iworldreader.getType(blockposition1);
        Block block = iblockdata.getBlock();
        return block != Blocks.MAGMA_BLOCK && (block == this || Block.a(iblockdata.h(iworldreader, blockposition1), EnumDirection.UP));
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.KELP;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return new ItemStack(Blocks.KELP);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData var3, FluidType var4) {
        return false;
    }

    public boolean place(GeneratorAccess var1, BlockPosition var2, IBlockData var3, Fluid var4) {
        return false;
    }
}
