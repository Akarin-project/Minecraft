package net.minecraft.server;

public class BlockStainedGlassPane extends BlockGlassPane {
    private final EnumColor color;

    public BlockStainedGlassPane(EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.color = enumcolor;
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(NORTH, Boolean.valueOf(false))).set(EAST, Boolean.valueOf(false))).set(SOUTH, Boolean.valueOf(false))).set(WEST, Boolean.valueOf(false))).set(p, Boolean.valueOf(false)));
    }

    public EnumColor d() {
        return this.color;
    }

    public TextureType c() {
        return TextureType.TRANSLUCENT;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata1.getBlock() != iblockdata.getBlock()) {
            if (!world.isClientSide) {
                BlockBeacon.a(world, blockposition);
            }

        }
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean var5) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (!world.isClientSide) {
                BlockBeacon.a(world, blockposition);
            }

        }
    }
}
