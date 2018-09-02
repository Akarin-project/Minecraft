package net.minecraft.server;

public class BlockJukeBox extends BlockTileEntity {
    public static final BlockStateBoolean HAS_RECORD = BlockProperties.l;

    protected BlockJukeBox(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(HAS_RECORD, Boolean.valueOf(false)));
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman var4, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (iblockdata.get(HAS_RECORD)) {
            this.dropRecord(world, blockposition);
            iblockdata = (IBlockData)iblockdata.set(HAS_RECORD, Boolean.valueOf(false));
            world.setTypeAndData(blockposition, iblockdata, 2);
            return true;
        } else {
            return false;
        }
    }

    public void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, ItemStack itemstack) {
        TileEntity tileentity = generatoraccess.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityJukeBox) {
            ((TileEntityJukeBox)tileentity).setRecord(itemstack.cloneItemStack());
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(HAS_RECORD, Boolean.valueOf(true)), 2);
        }
    }

    public void dropRecord(World world, BlockPosition blockposition) {
        if (!world.isClientSide) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityJukeBox) {
                TileEntityJukeBox tileentityjukebox = (TileEntityJukeBox)tileentity;
                ItemStack itemstack = tileentityjukebox.getRecord();
                if (!itemstack.isEmpty()) {
                    world.triggerEffect(1010, blockposition, 0);
                    world.a(blockposition, (SoundEffect)null);
                    tileentityjukebox.setRecord(ItemStack.a);
                    float f = 0.7F;
                    double d0 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
                    double d1 = (double)(world.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
                    double d2 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
                    ItemStack itemstack1 = itemstack.cloneItemStack();
                    EntityItem entityitem = new EntityItem(world, (double)blockposition.getX() + d0, (double)blockposition.getY() + d1, (double)blockposition.getZ() + d2, itemstack1);
                    entityitem.n();
                    world.addEntity(entityitem);
                }
            }
        }
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            this.dropRecord(world, blockposition);
            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float f, int var5) {
        if (!world.isClientSide) {
            super.dropNaturally(iblockdata, world, blockposition, f, 0);
        }
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityJukeBox();
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityJukeBox) {
            Item item = ((TileEntityJukeBox)tileentity).getRecord().getItem();
            if (item instanceof ItemRecord) {
                return ((ItemRecord)item).d();
            }
        }

        return 0;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(HAS_RECORD);
    }
}
