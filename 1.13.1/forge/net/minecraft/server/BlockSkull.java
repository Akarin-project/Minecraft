package net.minecraft.server;

public class BlockSkull extends BlockSkullAbstract {
    public static final BlockStateInteger a = BlockProperties.an;
    protected static final VoxelShape b = Block.a(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    protected BlockSkull(BlockSkull.a blockskull$a, Block.Info block$info) {
        super(blockskull$a, block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public VoxelShape g(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return VoxelShapes.a();
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(a, Integer.valueOf(MathHelper.floor((double)(blockactioncontext.h() * 16.0F / 360.0F) + 0.5D) & 15));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, Integer.valueOf(enumblockrotation.a(iblockdata.get(a), 16)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return (IBlockData)iblockdata.set(a, Integer.valueOf(enumblockmirror.a(iblockdata.get(a), 16)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public static enum Type implements BlockSkull.a {
        SKELETON,
        WITHER_SKELETON,
        PLAYER,
        ZOMBIE,
        CREEPER,
        DRAGON;

        private Type() {
        }
    }

    public interface a {
    }
}
