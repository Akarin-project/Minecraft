package net.minecraft.server;

public class BlockGlazedTerracotta extends BlockFacingHorizontal {
    public BlockGlazedTerracotta(Block.Info block$info) {
        super(block$info);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING);
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().opposite());
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.PUSH_ONLY;
    }
}
