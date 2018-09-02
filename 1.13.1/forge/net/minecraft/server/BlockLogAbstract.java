package net.minecraft.server;

public class BlockLogAbstract extends BlockRotatable {
    private final MaterialMapColor b;

    public BlockLogAbstract(MaterialMapColor materialmapcolor, Block.Info block$info) {
        super(block$info);
        this.b = materialmapcolor;
    }

    public MaterialMapColor c(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata.get(AXIS) == EnumDirection.EnumAxis.Y ? this.b : this.l;
    }
}
