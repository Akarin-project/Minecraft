package net.minecraft.server;

public class BlockMinecartTrack extends BlockMinecartTrackAbstract {
    public static final BlockStateEnum<BlockPropertyTrackPosition> SHAPE = BlockProperties.R;

    protected BlockMinecartTrack(Block.Info block$info) {
        super(false, block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(SHAPE, BlockPropertyTrackPosition.NORTH_SOUTH));
    }

    protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        if (block.getBlockData().isPowerSource() && (new MinecartTrackLogic(world, blockposition, iblockdata)).b() == 3) {
            this.a(world, blockposition, iblockdata, false);
        }

    }

    public IBlockState<BlockPropertyTrackPosition> e() {
        return SHAPE;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch(enumblockrotation) {
        case CLOCKWISE_180:
            switch((BlockPropertyTrackPosition)iblockdata.get(SHAPE)) {
            case ASCENDING_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_WEST);
            case ASCENDING_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_EAST);
            case ASCENDING_NORTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_SOUTH);
            case ASCENDING_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_NORTH);
            case SOUTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_WEST);
            case SOUTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_EAST);
            case NORTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_EAST);
            case NORTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_WEST);
            }
        case COUNTERCLOCKWISE_90:
            switch((BlockPropertyTrackPosition)iblockdata.get(SHAPE)) {
            case ASCENDING_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_NORTH);
            case ASCENDING_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_SOUTH);
            case ASCENDING_NORTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_WEST);
            case ASCENDING_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_EAST);
            case SOUTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_EAST);
            case SOUTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_EAST);
            case NORTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_WEST);
            case NORTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_WEST);
            case NORTH_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.EAST_WEST);
            case EAST_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_SOUTH);
            }
        case CLOCKWISE_90:
            switch((BlockPropertyTrackPosition)iblockdata.get(SHAPE)) {
            case ASCENDING_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_SOUTH);
            case ASCENDING_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_NORTH);
            case ASCENDING_NORTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_EAST);
            case ASCENDING_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_WEST);
            case SOUTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_WEST);
            case SOUTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_WEST);
            case NORTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_EAST);
            case NORTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_EAST);
            case NORTH_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.EAST_WEST);
            case EAST_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_SOUTH);
            }
        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        BlockPropertyTrackPosition blockpropertytrackposition = (BlockPropertyTrackPosition)iblockdata.get(SHAPE);
        switch(enumblockmirror) {
        case LEFT_RIGHT:
            switch(blockpropertytrackposition) {
            case ASCENDING_NORTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_SOUTH);
            case ASCENDING_SOUTH:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_NORTH);
            case SOUTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_EAST);
            case SOUTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_WEST);
            case NORTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_WEST);
            case NORTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_EAST);
            default:
                return super.a(iblockdata, enumblockmirror);
            }
        case FRONT_BACK:
            switch(blockpropertytrackposition) {
            case ASCENDING_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_WEST);
            case ASCENDING_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.ASCENDING_EAST);
            case ASCENDING_NORTH:
            case ASCENDING_SOUTH:
            default:
                break;
            case SOUTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_WEST);
            case SOUTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.SOUTH_EAST);
            case NORTH_WEST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_EAST);
            case NORTH_EAST:
                return (IBlockData)iblockdata.set(SHAPE, BlockPropertyTrackPosition.NORTH_WEST);
            }
        }

        return super.a(iblockdata, enumblockmirror);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(SHAPE);
    }
}
