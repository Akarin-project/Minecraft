package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockAttachable extends BlockFacingHorizontal {
    public static final BlockStateEnum<BlockPropertyAttachPosition> FACE = BlockProperties.K;

    protected BlockAttachable(Block.Info block$info) {
        super(block$info);
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = k(iblockdata).opposite();
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        IBlockData iblockdata1 = iworldreader.getType(blockposition1);
        Block block = iblockdata1.getBlock();
        if (a(block)) {
            return false;
        } else {
            boolean flag = iblockdata1.c(iworldreader, blockposition1, enumdirection.opposite()) == EnumBlockFaceShape.SOLID;
            if (enumdirection == EnumDirection.UP) {
                return block == Blocks.HOPPER || flag;
            } else {
                return !b(block) && flag;
            }
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        for(EnumDirection enumdirection : blockactioncontext.e()) {
            IBlockData iblockdata;
            if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
                iblockdata = (IBlockData)((IBlockData)this.getBlockData().set(FACE, enumdirection == EnumDirection.UP ? BlockPropertyAttachPosition.CEILING : BlockPropertyAttachPosition.FLOOR)).set(FACING, blockactioncontext.f());
            } else {
                iblockdata = (IBlockData)((IBlockData)this.getBlockData().set(FACE, BlockPropertyAttachPosition.WALL)).set(FACING, enumdirection.opposite());
            }

            if (iblockdata.canPlace(blockactioncontext.getWorld(), blockactioncontext.getClickPosition())) {
                return iblockdata;
            }
        }

        return null;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return k(iblockdata).opposite() == enumdirection && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    protected static EnumDirection k(IBlockData iblockdata) {
        switch((BlockPropertyAttachPosition)iblockdata.get(FACE)) {
        case CEILING:
            return EnumDirection.DOWN;
        case FLOOR:
            return EnumDirection.UP;
        default:
            return (EnumDirection)iblockdata.get(FACING);
        }
    }
}
