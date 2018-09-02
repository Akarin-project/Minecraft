package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockPistonMoving extends BlockTileEntity {
    public static final BlockStateDirection a = BlockPistonExtension.FACING;
    public static final BlockStateEnum<BlockPropertyPistonType> b = BlockPistonExtension.TYPE;

    public BlockPistonMoving(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH)).set(b, BlockPropertyPistonType.DEFAULT));
    }

    @Nullable
    public TileEntity a(IBlockAccess var1) {
        return null;
    }

    public static TileEntity a(IBlockData iblockdata, EnumDirection enumdirection, boolean flag, boolean flag1) {
        return new TileEntityPiston(iblockdata, enumdirection, flag, flag1);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityPiston) {
                ((TileEntityPiston)tileentity).j();
            } else {
                super.remove(iblockdata, world, blockposition, iblockdata1, flag);
            }

        }
    }

    public void postBreak(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        BlockPosition blockposition1 = blockposition.shift(((EnumDirection)iblockdata.get(a)).opposite());
        IBlockData iblockdata1 = generatoraccess.getType(blockposition1);
        if (iblockdata1.getBlock() instanceof BlockPiston && iblockdata1.get(BlockPiston.EXTENDED)) {
            generatoraccess.setAir(blockposition1);
        }

    }

    public boolean f(IBlockData var1) {
        return false;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman var4, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (!world.isClientSide && world.getTileEntity(blockposition) == null) {
            world.setAir(blockposition);
            return true;
        } else {
            return false;
        }
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public void dropNaturally(IBlockData var1, World world, BlockPosition blockposition, float var4, int var5) {
        if (!world.isClientSide) {
            TileEntityPiston tileentitypiston = this.a(world, blockposition);
            if (tileentitypiston != null) {
                tileentitypiston.i().a(world, blockposition, 0);
            }
        }
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return VoxelShapes.a();
    }

    public VoxelShape f(IBlockData var1, IBlockAccess iblockaccess, BlockPosition blockposition) {
        TileEntityPiston tileentitypiston = this.a(iblockaccess, blockposition);
        return tileentitypiston != null ? tileentitypiston.a(iblockaccess, blockposition) : VoxelShapes.a();
    }

    @Nullable
    private TileEntityPiston a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
        return tileentity instanceof TileEntityPiston ? (TileEntityPiston)tileentity : null;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, enumblockrotation.a((EnumDirection)iblockdata.get(a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
