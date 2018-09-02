package net.minecraft.server;

public class BlockFloorSign extends BlockSign {
    public static final BlockStateInteger ROTATION = BlockProperties.an;

    public BlockFloorSign(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(ROTATION, Integer.valueOf(0))).set(a, Boolean.valueOf(false)));
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.down()).getMaterial().isBuildable();
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return (IBlockData)((IBlockData)this.getBlockData().set(ROTATION, Integer.valueOf(MathHelper.floor((double)((180.0F + blockactioncontext.h()) * 16.0F / 360.0F) + 0.5D) & 15))).set(a, Boolean.valueOf(fluid.c() == FluidTypes.c));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !this.canPlace(iblockdata, generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(ROTATION, Integer.valueOf(enumblockrotation.a(iblockdata.get(ROTATION), 16)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return (IBlockData)iblockdata.set(ROTATION, Integer.valueOf(enumblockmirror.a(iblockdata.get(ROTATION), 16)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(ROTATION, a);
    }
}
