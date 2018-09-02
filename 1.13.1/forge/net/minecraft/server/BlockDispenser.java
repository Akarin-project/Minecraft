package net.minecraft.server;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;

public class BlockDispenser extends BlockTileEntity {
    public static final BlockStateDirection FACING = BlockDirectional.FACING;
    public static final BlockStateBoolean TRIGGERED = BlockProperties.w;
    public static final Map<Item, IDispenseBehavior> REGISTRY = (Map)SystemUtils.a(new Object2ObjectOpenHashMap(), (object2objectopenhashmap) -> {
        object2objectopenhashmap.defaultReturnValue(new DispenseBehaviorItem());
    });

    public static void a(IMaterial imaterial, IDispenseBehavior idispensebehavior) {
        REGISTRY.put(imaterial.getItem(), idispensebehavior);
    }

    protected BlockDispenser(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(TRIGGERED, Boolean.valueOf(false)));
    }

    public int a(IWorldReader var1) {
        return 4;
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityDispenser) {
                entityhuman.openContainer((TileEntityDispenser)tileentity);
                if (tileentity instanceof TileEntityDropper) {
                    entityhuman.a(StatisticList.INSPECT_DROPPER);
                } else {
                    entityhuman.a(StatisticList.INSPECT_DISPENSER);
                }
            }

            return true;
        }
    }

    public void dispense(World world, BlockPosition blockposition) {
        SourceBlock sourceblock = new SourceBlock(world, blockposition);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
        int i = tileentitydispenser.p();
        if (i < 0) {
            world.triggerEffect(1001, blockposition, 0);
        } else {
            ItemStack itemstack = tileentitydispenser.getItem(i);
            IDispenseBehavior idispensebehavior = this.a(itemstack);
            if (idispensebehavior != IDispenseBehavior.NONE) {
                tileentitydispenser.setItem(i, idispensebehavior.dispense(sourceblock, itemstack));
            }

        }
    }

    protected IDispenseBehavior a(ItemStack itemstack) {
        return (IDispenseBehavior)REGISTRY.get(itemstack.getItem());
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        boolean flag = world.isBlockIndirectlyPowered(blockposition) || world.isBlockIndirectlyPowered(blockposition.up());
        boolean flag1 = iblockdata.get(TRIGGERED);
        if (flag && !flag1) {
            world.J().a(blockposition, this, this.a((IWorldReader)world));
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(TRIGGERED, Boolean.valueOf(true)), 4);
        } else if (!flag && flag1) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(TRIGGERED, Boolean.valueOf(false)), 4);
        }

    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide) {
            this.dispense(world, blockposition);
        }

    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityDispenser();
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.d().opposite());
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityDispenser) {
                ((TileEntityDispenser)tileentity).setCustomName(itemstack.getName());
            }
        }

    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityDispenser) {
                InventoryUtils.dropInventory(world, blockposition, (TileEntityDispenser)tileentity);
                world.updateAdjacentComparators(blockposition, this);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public static IPosition a(ISourceBlock isourceblock) {
        EnumDirection enumdirection = (EnumDirection)isourceblock.e().get(FACING);
        double d0 = isourceblock.getX() + 0.7D * (double)enumdirection.getAdjacentX();
        double d1 = isourceblock.getY() + 0.7D * (double)enumdirection.getAdjacentY();
        double d2 = isourceblock.getZ() + 0.7D * (double)enumdirection.getAdjacentZ();
        return new Position(d0, d1, d2);
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        return Container.a(world.getTileEntity(blockposition));
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, TRIGGERED);
    }
}
