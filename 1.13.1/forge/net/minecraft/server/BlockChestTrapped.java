package net.minecraft.server;

public class BlockChestTrapped extends BlockChest {
    public BlockChestTrapped(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityChestTrapped();
    }

    protected Statistic<MinecraftKey> d() {
        return StatisticList.CUSTOM.b(StatisticList.TRIGGER_TRAPPED_CHEST);
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection var4) {
        return MathHelper.clamp(TileEntityChest.a(iblockaccess, blockposition), 0, 15);
    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? iblockdata.a(iblockaccess, blockposition, enumdirection) : 0;
    }
}
