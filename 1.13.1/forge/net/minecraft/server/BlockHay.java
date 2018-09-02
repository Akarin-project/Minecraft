package net.minecraft.server;

public class BlockHay extends BlockRotatable {
    public BlockHay(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(AXIS, EnumDirection.EnumAxis.Y));
    }

    public void fallOn(World var1, BlockPosition var2, Entity entity, float f) {
        entity.c(f, 0.2F);
    }
}
