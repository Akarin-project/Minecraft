package net.minecraft.server;

public class BlockMobSpawner extends BlockTileEntity {
    protected BlockMobSpawner(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityMobSpawner();
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float f, int i) {
        super.dropNaturally(iblockdata, world, blockposition, f, i);
        int j = 15 + world.random.nextInt(15) + world.random.nextInt(15);
        this.dropExperience(world, blockposition, j);
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }
}
