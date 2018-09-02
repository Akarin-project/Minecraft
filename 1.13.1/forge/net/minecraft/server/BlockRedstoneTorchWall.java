package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockRedstoneTorchWall extends BlockRedstoneTorch {
    public static final BlockStateDirection b = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean c = BlockRedstoneTorch.LIT;

    protected BlockRedstoneTorchWall(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(b, EnumDirection.NORTH)).set(c, Boolean.valueOf(true)));
    }

    public String m() {
        return this.getItem().getName();
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return Blocks.WALL_TORCH.a(iblockdata, iblockaccess, blockposition);
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        return Blocks.WALL_TORCH.canPlace(iblockdata, iworldreader, blockposition);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return Blocks.WALL_TORCH.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = Blocks.WALL_TORCH.getPlacedState(blockactioncontext);
        return iblockdata == null ? null : (IBlockData)this.getBlockData().set(b, iblockdata.get(b));
    }

    protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = ((EnumDirection)iblockdata.get(b)).opposite();
        return world.isBlockFacePowered(blockposition.shift(enumdirection), enumdirection);
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        return iblockdata.get(c) && iblockdata.get(b) != enumdirection ? 15 : 0;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return Blocks.WALL_TORCH.a(iblockdata, enumblockrotation);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return Blocks.WALL_TORCH.a(iblockdata, enumblockmirror);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(b, c);
    }
}
