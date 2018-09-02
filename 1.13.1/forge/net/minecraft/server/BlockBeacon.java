package net.minecraft.server;

public class BlockBeacon extends BlockTileEntity {
    public BlockBeacon(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityBeacon();
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBeacon) {
                entityhuman.openContainer((TileEntityBeacon)tileentity);
                entityhuman.a(StatisticList.INTERACT_WITH_BEACON);
            }

            return true;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityBeacon) {
                ((TileEntityBeacon)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public static void a(World world, BlockPosition blockposition) {
        HttpUtilities.a.submit(() -> {
            Chunk chunk = world.getChunkAtWorldCoords(blockposition);

            for(int i = blockposition.getY() - 1; i >= 0; --i) {
                BlockPosition blockposition2 = new BlockPosition(blockposition.getX(), i, blockposition.getZ());
                if (!chunk.c(blockposition2)) {
                    break;
                }

                IBlockData iblockdata = world.getType(blockposition2);
                if (iblockdata.getBlock() == Blocks.BEACON) {
                    ((WorldServer)world).postToMainThread(() -> {
                        TileEntity tileentity = world.getTileEntity(blockposition2);
                        if (tileentity instanceof TileEntityBeacon) {
                            ((TileEntityBeacon)tileentity).p();
                            world.playBlockAction(blockposition2, Blocks.BEACON, 1, 0);
                        }

                    });
                }
            }

        });
    }
}
