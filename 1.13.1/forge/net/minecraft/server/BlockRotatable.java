package net.minecraft.server;

public class BlockRotatable extends Block {
    public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockProperties.A;

    public BlockRotatable(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)this.getBlockData().set(AXIS, EnumDirection.EnumAxis.Y));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch(enumblockrotation) {
        case COUNTERCLOCKWISE_90:
        case CLOCKWISE_90:
            switch((EnumDirection.EnumAxis)iblockdata.get(AXIS)) {
            case X:
                return (IBlockData)iblockdata.set(AXIS, EnumDirection.EnumAxis.Z);
            case Z:
                return (IBlockData)iblockdata.set(AXIS, EnumDirection.EnumAxis.X);
            default:
                return iblockdata;
            }
        default:
            return iblockdata;
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(AXIS);
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(AXIS, blockactioncontext.getClickedFace().k());
    }
}
