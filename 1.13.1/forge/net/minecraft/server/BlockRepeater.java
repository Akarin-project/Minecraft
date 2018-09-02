package net.minecraft.server;

public class BlockRepeater extends BlockDiodeAbstract {
    public static final BlockStateBoolean LOCKED = BlockProperties.p;
    public static final BlockStateInteger DELAY = BlockProperties.aa;

    protected BlockRepeater(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(DELAY, Integer.valueOf(1))).set(LOCKED, Boolean.valueOf(false))).set(c, Boolean.valueOf(false)));
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (!entityhuman.abilities.mayBuild) {
            return false;
        } else {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.a(DELAY), 3);
            return true;
        }
    }

    protected int k(IBlockData iblockdata) {
        return iblockdata.get(DELAY) * 2;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = super.getPlacedState(blockactioncontext);
        return (IBlockData)iblockdata.set(LOCKED, Boolean.valueOf(this.a(blockactioncontext.getWorld(), blockactioncontext.getClickPosition(), iblockdata)));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return !generatoraccess.e() && enumdirection.k() != ((EnumDirection)iblockdata.get(FACING)).k() ? (IBlockData)iblockdata.set(LOCKED, Boolean.valueOf(this.a(generatoraccess, blockposition, iblockdata))) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata) {
        return this.b(iworldreader, blockposition, iblockdata) > 0;
    }

    protected boolean w(IBlockData iblockdata) {
        return isDiode(iblockdata);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, DELAY, LOCKED, c);
    }
}
