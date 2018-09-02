package net.minecraft.server;

import java.util.Random;

public class BlockObserver extends BlockDirectional {
    public static final BlockStateBoolean b = BlockProperties.t;

    public BlockObserver(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.SOUTH)).set(b, Boolean.valueOf(false)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, b);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (iblockdata.get(b)) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(false)), 2);
        } else {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(true)), 2);
            world.J().a(blockposition, this, 2);
        }

        this.a(world, blockposition, iblockdata);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(FACING) == enumdirection && !iblockdata.get(b)) {
            this.a(generatoraccess, blockposition);
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    private void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (!generatoraccess.e() && !generatoraccess.J().a(blockposition, this)) {
            generatoraccess.J().a(blockposition, this, 2);
        }

    }

    protected void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        world.a(blockposition1, this, blockposition);
        world.a(blockposition1, this, enumdirection);
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return iblockdata.a(iblockaccess, blockposition, enumdirection);
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        return iblockdata.get(b) && iblockdata.get(FACING) == enumdirection ? 15 : 0;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (!world.e() && iblockdata.get(b) && !world.J().a(blockposition, this)) {
                IBlockData iblockdata2 = (IBlockData)iblockdata.set(b, Boolean.valueOf(false));
                world.setTypeAndData(blockposition, iblockdata2, 18);
                this.a(world, blockposition, iblockdata2);
            }

        }
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean var5) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (!world.isClientSide && iblockdata.get(b) && world.J().a(blockposition, this)) {
                this.a(world, blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(false)));
            }

        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.d().opposite().opposite());
    }
}
