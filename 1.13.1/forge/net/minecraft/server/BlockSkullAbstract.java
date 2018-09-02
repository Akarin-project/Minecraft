package net.minecraft.server;

public abstract class BlockSkullAbstract extends BlockTileEntity {
    private final BlockSkull.a a;

    public BlockSkullAbstract(BlockSkull.a blockskull$a, Block.Info block$info) {
        super(block$info);
        this.a = blockskull$a;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntitySkull();
    }

    public void dropNaturally(IBlockData var1, World var2, BlockPosition var3, float var4, int var5) {
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        if (!world.isClientSide && entityhuman.abilities.canInstantlyBuild) {
            TileEntitySkull.a(world, blockposition);
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock() && !world.isClientSide) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntitySkull) {
                TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
                if (tileentityskull.shouldDrop()) {
                    ItemStack itemstack = this.a(world, blockposition, iblockdata);
                    Block block = tileentityskull.getBlock().getBlock();
                    if ((block == Blocks.PLAYER_HEAD || block == Blocks.PLAYER_WALL_HEAD) && tileentityskull.getGameProfile() != null) {
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
                        itemstack.getOrCreateTag().set("SkullOwner", nbttagcompound);
                    }

                    a(world, blockposition, itemstack);
                }
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }
}
