package net.minecraft.server;

import java.util.Random;

public abstract class BlockDiodeAbstract extends BlockFacingHorizontal {
    protected static final VoxelShape b = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    public static final BlockStateBoolean c = BlockProperties.t;

    protected BlockDiodeAbstract(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.down()).q();
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!this.a((IWorldReader)world, blockposition, iblockdata)) {
            boolean flag = iblockdata.get(c);
            boolean flag1 = this.a(world, blockposition, iblockdata);
            if (flag && !flag1) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(c, Boolean.valueOf(false)), 2);
            } else if (!flag) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(c, Boolean.valueOf(true)), 2);
                if (!flag1) {
                    world.J().a(blockposition, this, this.k(iblockdata), TickListPriority.HIGH);
                }
            }

        }
    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return iblockdata.a(iblockaccess, blockposition, enumdirection);
    }

    public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        if (!iblockdata.get(c)) {
            return 0;
        } else {
            return iblockdata.get(FACING) == enumdirection ? this.b(iblockaccess, blockposition, iblockdata) : 0;
        }
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (iblockdata.canPlace(world, blockposition)) {
            this.c(world, blockposition, iblockdata);
        } else {
            iblockdata.a(world, blockposition, 0);
            world.setAir(blockposition);

            for(EnumDirection enumdirection : EnumDirection.values()) {
                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

        }
    }

    protected void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.a((IWorldReader)world, blockposition, iblockdata)) {
            boolean flag = iblockdata.get(c);
            boolean flag1 = this.a(world, blockposition, iblockdata);
            if (flag != flag1 && !world.J().b(blockposition, this)) {
                TickListPriority ticklistpriority = TickListPriority.HIGH;
                if (this.c((IBlockAccess)world, blockposition, iblockdata)) {
                    ticklistpriority = TickListPriority.EXTREMELY_HIGH;
                } else if (flag) {
                    ticklistpriority = TickListPriority.VERY_HIGH;
                }

                world.J().a(blockposition, this, this.k(iblockdata), ticklistpriority);
            }

        }
    }

    public boolean a(IWorldReader var1, BlockPosition var2, IBlockData var3) {
        return false;
    }

    protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return this.b(world, blockposition, iblockdata) > 0;
    }

    protected int b(World world, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        int i = world.getBlockFacePower(blockposition1, enumdirection);
        if (i >= 15) {
            return i;
        } else {
            IBlockData iblockdata1 = world.getType(blockposition1);
            return Math.max(i, iblockdata1.getBlock() == Blocks.REDSTONE_WIRE ? iblockdata1.get(BlockRedstoneWire.POWER) : 0);
        }
    }

    protected int b(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        EnumDirection enumdirection1 = enumdirection.e();
        EnumDirection enumdirection2 = enumdirection.f();
        return Math.max(this.a(iworldreader, blockposition.shift(enumdirection1), enumdirection1), this.a(iworldreader, blockposition.shift(enumdirection2), enumdirection2));
    }

    protected int a(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata = iworldreader.getType(blockposition);
        Block block = iblockdata.getBlock();
        if (this.w(iblockdata)) {
            if (block == Blocks.REDSTONE_BLOCK) {
                return 15;
            } else {
                return block == Blocks.REDSTONE_WIRE ? iblockdata.get(BlockRedstoneWire.POWER) : iworldreader.a(blockposition, enumdirection);
            }
        } else {
            return 0;
        }
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().opposite());
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving var4, ItemStack var5) {
        if (this.a(world, blockposition, iblockdata)) {
            world.J().a(blockposition, this, 1);
        }

    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData var4) {
        this.d(world, blockposition, iblockdata);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
            this.a(world, blockposition);
            this.d(world, blockposition, iblockdata);
        }
    }

    protected void a(World var1, BlockPosition var2) {
    }

    protected void d(World world, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        world.a(blockposition1, this, blockposition);
        world.a(blockposition1, this, enumdirection);
    }

    protected boolean w(IBlockData iblockdata) {
        return iblockdata.isPowerSource();
    }

    protected int b(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return 15;
    }

    public static boolean isDiode(IBlockData iblockdata) {
        return iblockdata.getBlock() instanceof BlockDiodeAbstract;
    }

    public boolean c(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
        EnumDirection enumdirection = ((EnumDirection)iblockdata.get(FACING)).opposite();
        IBlockData iblockdata1 = iblockaccess.getType(blockposition.shift(enumdirection));
        return isDiode(iblockdata1) && iblockdata1.get(FACING) != enumdirection;
    }

    protected abstract int k(IBlockData var1);

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public boolean f(IBlockData var1) {
        return true;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }
}
