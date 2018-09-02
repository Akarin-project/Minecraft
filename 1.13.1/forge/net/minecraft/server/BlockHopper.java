package net.minecraft.server;

public class BlockHopper extends BlockTileEntity {
    public static final BlockStateDirection FACING = BlockProperties.I;
    public static final BlockStateBoolean ENABLED = BlockProperties.e;
    private static final VoxelShape c = Block.a(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape o = Block.a(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape p = VoxelShapes.a(o, c);
    private static final VoxelShape q = VoxelShapes.a(p, IHopper.a, OperatorBoolean.ONLY_FIRST);
    private static final VoxelShape r = VoxelShapes.a(q, Block.a(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
    private static final VoxelShape s = VoxelShapes.a(q, Block.a(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
    private static final VoxelShape t = VoxelShapes.a(q, Block.a(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
    private static final VoxelShape u = VoxelShapes.a(q, Block.a(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
    private static final VoxelShape v = VoxelShapes.a(q, Block.a(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
    private static final VoxelShape w = IHopper.a;
    private static final VoxelShape x = VoxelShapes.a(IHopper.a, Block.a(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
    private static final VoxelShape y = VoxelShapes.a(IHopper.a, Block.a(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
    private static final VoxelShape z = VoxelShapes.a(IHopper.a, Block.a(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
    private static final VoxelShape A = VoxelShapes.a(IHopper.a, Block.a(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));

    public BlockHopper(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.DOWN)).set(ENABLED, Boolean.valueOf(true)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((EnumDirection)iblockdata.get(FACING)) {
        case DOWN:
            return r;
        case NORTH:
            return t;
        case SOUTH:
            return u;
        case WEST:
            return v;
        case EAST:
            return s;
        default:
            return q;
        }
    }

    public VoxelShape h(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((EnumDirection)iblockdata.get(FACING)) {
        case DOWN:
            return w;
        case NORTH:
            return y;
        case SOUTH:
            return z;
        case WEST:
            return A;
        case EAST:
            return x;
        default:
            return IHopper.a;
        }
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        EnumDirection enumdirection = blockactioncontext.getClickedFace().opposite();
        return (IBlockData)((IBlockData)this.getBlockData().set(FACING, enumdirection.k() == EnumDirection.EnumAxis.Y ? EnumDirection.DOWN : enumdirection)).set(ENABLED, Boolean.valueOf(true));
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityHopper();
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityHopper) {
                ((TileEntityHopper)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public boolean r(IBlockData var1) {
        return true;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata1.getBlock() != iblockdata.getBlock()) {
            this.a(world, blockposition, iblockdata);
        }
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityHopper) {
                entityhuman.openContainer((TileEntityHopper)tileentity);
                entityhuman.a(StatisticList.INSPECT_HOPPER);
            }

            return true;
        }
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        this.a(world, blockposition, iblockdata);
    }

    private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        boolean flag = !world.isBlockIndirectlyPowered(blockposition);
        if (flag != iblockdata.get(ENABLED)) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(ENABLED, Boolean.valueOf(flag)), 4);
        }

    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityHopper) {
                InventoryUtils.dropInventory(world, blockposition, (TileEntityHopper)tileentity);
                world.updateAdjacentComparators(blockposition, this);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        return Container.a(world.getTileEntity(blockposition));
    }

    public TextureType c() {
        return TextureType.CUTOUT_MIPPED;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, ENABLED);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? EnumBlockFaceShape.BOWL : EnumBlockFaceShape.UNDEFINED;
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Entity entity) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityHopper) {
            ((TileEntityHopper)tileentity).a(entity);
        }

    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
