package net.minecraft.server;

public abstract class BlockFacingHorizontal extends Block {
    public static final BlockStateDirection FACING = BlockProperties.J;

    protected BlockFacingHorizontal(Block.Info block$info) {
        super(block$info);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }
}
