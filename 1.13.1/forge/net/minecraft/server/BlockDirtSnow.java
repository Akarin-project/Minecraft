package net.minecraft.server;

public class BlockDirtSnow extends Block {
    public static final BlockStateBoolean a = BlockProperties.v;

    protected BlockDirtSnow(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Boolean.valueOf(false)));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection != EnumDirection.UP) {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            Block block = iblockdata1.getBlock();
            return (IBlockData)iblockdata.set(a, Boolean.valueOf(block == Blocks.SNOW_BLOCK || block == Blocks.SNOW));
        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Block block = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition().up()).getBlock();
        return (IBlockData)this.getBlockData().set(a, Boolean.valueOf(block == Blocks.SNOW_BLOCK || block == Blocks.SNOW));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.DIRT;
    }
}
