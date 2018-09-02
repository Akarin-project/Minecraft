package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockCoralBase extends Block implements IFluidSource, IFluidContainer {
    public static final BlockStateBoolean b = BlockProperties.y;
    private static final VoxelShape a = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    protected BlockCoralBase(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(b, Boolean.valueOf(true)));
    }

    protected void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (!b_(iblockdata, generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 60 + generatoraccess.m().nextInt(40));
        }

    }

    protected static boolean b_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        if (iblockdata.get(b)) {
            return true;
        } else {
            for(EnumDirection enumdirection : EnumDirection.values()) {
                if (iblockaccess.b(blockposition.shift(enumdirection)).a(TagsFluid.WATER)) {
                    return true;
                }
            }

            return false;
        }
    }

    protected boolean X_() {
        return true;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return (IBlockData)this.getBlockData().set(b, Boolean.valueOf(fluid.a(TagsFluid.WATER) && fluid.g() == 8));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
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

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(b)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return enumdirection == EnumDirection.DOWN && !this.canPlace(iblockdata, generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.down()).q();
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(b);
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(b) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(b)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(b) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(b) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }
}
