package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockKelp extends Block implements IFluidContainer {
    public static final BlockStateInteger a = BlockProperties.Y;
    protected static final VoxelShape b = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

    protected BlockKelp(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Integer.valueOf(0)));
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return fluid.a(TagsFluid.WATER) && fluid.g() == 8 ? this.a(blockactioncontext.getWorld()) : null;
    }

    public IBlockData a(GeneratorAccess generatoraccess) {
        return (IBlockData)this.getBlockData().set(a, Integer.valueOf(generatoraccess.m().nextInt(25)));
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

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (!iblockdata.canPlace(world, blockposition)) {
            world.setAir(blockposition, true);
        } else {
            BlockPosition blockposition1 = blockposition.up();
            IBlockData iblockdata1 = world.getType(blockposition1);
            if (iblockdata1.getBlock() == Blocks.WATER && iblockdata.get(a) < 25 && random.nextDouble() < 0.14D) {
                world.setTypeUpdate(blockposition1, (IBlockData)iblockdata.a(a));
            }

        }
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();
        IBlockData iblockdata = iworldreader.getType(blockposition1);
        Block block = iblockdata.getBlock();
        if (block == Blocks.MAGMA_BLOCK) {
            return false;
        } else {
            return block == this || block == Blocks.KELP_PLANT || Block.a(iblockdata.h(iworldreader, blockposition1), EnumDirection.UP);
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            if (enumdirection == EnumDirection.DOWN) {
                return Blocks.AIR.getBlockData();
            }

            generatoraccess.J().a(blockposition, this, 1);
        }

        if (enumdirection == EnumDirection.UP && iblockdata1.getBlock() == this) {
            return Blocks.KELP_PLANT.getBlockData();
        } else {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData var3, FluidType var4) {
        return false;
    }

    public boolean place(GeneratorAccess var1, BlockPosition var2, IBlockData var3, Fluid var4) {
        return false;
    }
}
