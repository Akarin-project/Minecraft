package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockConduit extends BlockTileEntity implements IFluidSource, IFluidContainer {
    public static final BlockStateBoolean a = BlockProperties.y;
    protected static final VoxelShape b = Block.a(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlockConduit(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Boolean.valueOf(true)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityConduit();
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.ENTITYBLOCK_ANIMATED;
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(a) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(a)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, @Nullable EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBeacon) {
                ((TileEntityBeacon)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return (IBlockData)this.getBlockData().set(a, Boolean.valueOf(fluid.a(TagsFluid.WATER) && fluid.g() == 8));
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(a)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(a) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(a) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }
}
