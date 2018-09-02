package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockDoor extends Block {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean OPEN = BlockProperties.r;
    public static final BlockStateEnum<BlockPropertyDoorHinge> HINGE = BlockProperties.ar;
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> HALF = BlockProperties.P;
    protected static final VoxelShape q = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape r = Block.a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape s = Block.a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape t = Block.a(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    protected BlockDoor(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(OPEN, Boolean.valueOf(false))).set(HINGE, BlockPropertyDoorHinge.LEFT)).set(POWERED, Boolean.valueOf(false))).set(HALF, BlockPropertyDoubleBlockHalf.LOWER));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        boolean flag = !iblockdata.get(OPEN);
        boolean flag1 = iblockdata.get(HINGE) == BlockPropertyDoorHinge.RIGHT;
        switch(enumdirection) {
        case EAST:
        default:
            return flag ? t : (flag1 ? r : q);
        case SOUTH:
            return flag ? q : (flag1 ? t : s);
        case WEST:
            return flag ? s : (flag1 ? q : r);
        case NORTH:
            return flag ? r : (flag1 ? s : t);
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        BlockPropertyDoubleBlockHalf blockpropertydoubleblockhalf = (BlockPropertyDoubleBlockHalf)iblockdata.get(HALF);
        if (enumdirection.k() == EnumDirection.EnumAxis.Y && blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.LOWER == (enumdirection == EnumDirection.UP)) {
            return iblockdata1.getBlock() == this && iblockdata1.get(HALF) != blockpropertydoubleblockhalf ? (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(FACING, iblockdata1.get(FACING))).set(OPEN, iblockdata1.get(OPEN))).set(HINGE, iblockdata1.get(HINGE))).set(POWERED, iblockdata1.get(POWERED)) : Blocks.AIR.getBlockData();
        } else {
            return blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.LOWER && enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData var4, @Nullable TileEntity tileentity, ItemStack itemstack) {
        super.a(world, entityhuman, blockposition, Blocks.AIR.getBlockData(), tileentity, itemstack);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        BlockPropertyDoubleBlockHalf blockpropertydoubleblockhalf = (BlockPropertyDoubleBlockHalf)iblockdata.get(HALF);
        boolean flag = blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.LOWER;
        BlockPosition blockposition1 = flag ? blockposition.up() : blockposition.down();
        IBlockData iblockdata1 = world.getType(blockposition1);
        if (iblockdata1.getBlock() == this && iblockdata1.get(HALF) != blockpropertydoubleblockhalf) {
            world.setTypeAndData(blockposition1, Blocks.AIR.getBlockData(), 35);
            world.a(entityhuman, 2001, blockposition1, Block.getCombinedId(iblockdata1));
            if (!world.isClientSide && !entityhuman.u()) {
                if (flag) {
                    iblockdata.a(world, blockposition, 0);
                } else {
                    iblockdata1.a(world, blockposition1, 0);
                }
            }
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    public boolean a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, PathMode pathmode) {
        switch(pathmode) {
        case LAND:
            return iblockdata.get(OPEN);
        case WATER:
            return false;
        case AIR:
            return iblockdata.get(OPEN);
        default:
            return false;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    private int d() {
        return this.material == Material.ORE ? 1011 : 1012;
    }

    private int e() {
        return this.material == Material.ORE ? 1005 : 1006;
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        if (blockposition.getY() < 255 && blockactioncontext.getWorld().getType(blockposition.up()).a(blockactioncontext)) {
            World world = blockactioncontext.getWorld();
            boolean flag = world.isBlockIndirectlyPowered(blockposition) || world.isBlockIndirectlyPowered(blockposition.up());
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(FACING, blockactioncontext.f())).set(HINGE, this.b(blockactioncontext))).set(POWERED, Boolean.valueOf(flag))).set(OPEN, Boolean.valueOf(flag))).set(HALF, BlockPropertyDoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving var4, ItemStack var5) {
        world.setTypeAndData(blockposition.up(), (IBlockData)iblockdata.set(HALF, BlockPropertyDoubleBlockHalf.UPPER), 3);
    }

    private BlockPropertyDoorHinge b(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        EnumDirection enumdirection = blockactioncontext.f();
        BlockPosition blockposition1 = blockposition.up();
        EnumDirection enumdirection1 = enumdirection.f();
        IBlockData iblockdata = world.getType(blockposition.shift(enumdirection1));
        IBlockData iblockdata1 = world.getType(blockposition1.shift(enumdirection1));
        EnumDirection enumdirection2 = enumdirection.e();
        IBlockData iblockdata2 = world.getType(blockposition.shift(enumdirection2));
        IBlockData iblockdata3 = world.getType(blockposition1.shift(enumdirection2));
        int i = (iblockdata.k() ? -1 : 0) + (iblockdata1.k() ? -1 : 0) + (iblockdata2.k() ? 1 : 0) + (iblockdata3.k() ? 1 : 0);
        boolean flag = iblockdata.getBlock() == this && iblockdata.get(HALF) == BlockPropertyDoubleBlockHalf.LOWER;
        boolean flag1 = iblockdata2.getBlock() == this && iblockdata2.get(HALF) == BlockPropertyDoubleBlockHalf.LOWER;
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i >= 0) {
                int j = enumdirection.getAdjacentX();
                int k = enumdirection.getAdjacentZ();
                float f = blockactioncontext.m();
                float f1 = blockactioncontext.o();
                return (j >= 0 || !(f1 < 0.5F)) && (j <= 0 || !(f1 > 0.5F)) && (k >= 0 || !(f > 0.5F)) && (k <= 0 || !(f < 0.5F)) ? BlockPropertyDoorHinge.LEFT : BlockPropertyDoorHinge.RIGHT;
            } else {
                return BlockPropertyDoorHinge.LEFT;
            }
        } else {
            return BlockPropertyDoorHinge.RIGHT;
        }
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (this.material == Material.ORE) {
            return false;
        } else {
            iblockdata = (IBlockData)iblockdata.a(OPEN);
            world.setTypeAndData(blockposition, iblockdata, 10);
            world.a(entityhuman, iblockdata.get(OPEN) ? this.e() : this.d(), blockposition, 0);
            return true;
        }
    }

    public void setDoor(World world, BlockPosition blockposition, boolean flag) {
        IBlockData iblockdata = world.getType(blockposition);
        if (iblockdata.getBlock() == this && iblockdata.get(OPEN) != flag) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(OPEN, Boolean.valueOf(flag)), 10);
            this.b(world, blockposition, flag);
        }
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition var5) {
        boolean flag = world.isBlockIndirectlyPowered(blockposition) || world.isBlockIndirectlyPowered(blockposition.shift(iblockdata.get(HALF) == BlockPropertyDoubleBlockHalf.LOWER ? EnumDirection.UP : EnumDirection.DOWN));
        if (block != this && flag != iblockdata.get(POWERED)) {
            if (flag != iblockdata.get(OPEN)) {
                this.b(world, blockposition, flag);
            }

            world.setTypeAndData(blockposition, (IBlockData)((IBlockData)iblockdata.set(POWERED, Boolean.valueOf(flag))).set(OPEN, Boolean.valueOf(flag)), 2);
        }

    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata1 = iworldreader.getType(blockposition.down());
        if (iblockdata.get(HALF) == BlockPropertyDoubleBlockHalf.LOWER) {
            return iblockdata1.q();
        } else {
            return iblockdata1.getBlock() == this;
        }
    }

    private void b(World world, BlockPosition blockposition, boolean flag) {
        world.a((EntityHuman)null, flag ? this.e() : this.d(), blockposition, 0);
    }

    public IMaterial getDropType(IBlockData iblockdata, World world, BlockPosition blockposition, int i) {
        return (IMaterial)(iblockdata.get(HALF) == BlockPropertyDoubleBlockHalf.UPPER ? Items.AIR : super.getDropType(iblockdata, world, blockposition, i));
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.DESTROY;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return enumblockmirror == EnumBlockMirror.NONE ? iblockdata : (IBlockData)iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING))).a(HINGE);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(HALF, FACING, OPEN, HINGE, POWERED);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
