package net.minecraft.server;

import java.util.Random;

public class BlockEnderChest extends BlockTileEntity implements IFluidSource, IFluidContainer {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean b = BlockProperties.y;
    protected static final VoxelShape c = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    protected BlockEnderChest(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(b, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.ENTITYBLOCK_ANIMATED;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.OBSIDIAN;
    }

    public int a(IBlockData var1, Random var2) {
        return 8;
    }

    protected boolean X_() {
        return true;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        Fluid fluid = blockactioncontext.getWorld().b(blockactioncontext.getClickPosition());
        return (IBlockData)((IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().opposite())).set(b, Boolean.valueOf(fluid.c() == FluidTypes.c));
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        InventoryEnderChest inventoryenderchest = entityhuman.getEnderChest();
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (inventoryenderchest != null && tileentity instanceof TileEntityEnderChest) {
            if (world.getType(blockposition.up()).isOccluding()) {
                return true;
            } else if (world.isClientSide) {
                return true;
            } else {
                inventoryenderchest.a((TileEntityEnderChest)tileentity);
                entityhuman.openContainer(inventoryenderchest);
                entityhuman.a(StatisticList.OPEN_ENDERCHEST);
                return true;
            }
        } else {
            return true;
        }
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityEnderChest();
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, b);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(b)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(b) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(b) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(b) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.get(b)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
