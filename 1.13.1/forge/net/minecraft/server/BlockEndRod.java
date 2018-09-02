package net.minecraft.server;

public class BlockEndRod extends BlockDirectional {
    protected static final VoxelShape b = Block.a(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape c = Block.a(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape o = Block.a(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);

    protected BlockEndRod(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.UP));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return (IBlockData)iblockdata.set(FACING, enumblockmirror.b((EnumDirection)iblockdata.get(FACING)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch(((EnumDirection)iblockdata.get(FACING)).k()) {
        case X:
        default:
            return o;
        case Z:
            return c;
        case Y:
            return b;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        EnumDirection enumdirection = blockactioncontext.getClickedFace();
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition().shift(enumdirection.opposite()));
        return iblockdata.getBlock() == this && iblockdata.get(FACING) == enumdirection ? (IBlockData)this.getBlockData().set(FACING, enumdirection.opposite()) : (IBlockData)this.getBlockData().set(FACING, enumdirection);
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING);
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.NORMAL;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
