package net.minecraft.server;

import java.util.Random;

public abstract class BlockPressurePlateAbstract extends Block {
    protected static final VoxelShape a = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 0.5D, 15.0D);
    protected static final VoxelShape b = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

    protected BlockPressurePlateAbstract(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return this.getPower(iblockdata) > 0 ? a : b;
    }

    public int a(IWorldReader var1) {
        return 20;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean a() {
        return true;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        return iblockdata.q() || iblockdata.getBlock() instanceof BlockFence;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide) {
            int i = this.getPower(iblockdata);
            if (i > 0) {
                this.a(world, blockposition, iblockdata, i);
            }

        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity var4) {
        if (!world.isClientSide) {
            int i = this.getPower(iblockdata);
            if (i == 0) {
                this.a(world, blockposition, iblockdata, i);
            }

        }
    }

    protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
        int j = this.b(world, blockposition);
        boolean flag = i > 0;
        boolean flag1 = j > 0;
        if (i != j) {
            iblockdata = this.a(iblockdata, j);
            world.setTypeAndData(blockposition, iblockdata, 2);
            this.a(world, blockposition);
            world.a(blockposition, blockposition);
        }

        if (!flag1 && flag) {
            this.b((GeneratorAccess)world, blockposition);
        } else if (flag1 && !flag) {
            this.a((GeneratorAccess)world, blockposition);
        }

        if (flag1) {
            world.J().a(new BlockPosition(blockposition), this, this.a(world));
        }

    }

    protected abstract void a(GeneratorAccess var1, BlockPosition var2);

    protected abstract void b(GeneratorAccess var1, BlockPosition var2);

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (this.getPower(iblockdata) > 0) {
                this.a(world, blockposition);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    protected void a(World world, BlockPosition blockposition) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.down(), this);
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection var4) {
        return this.getPower(iblockdata);
    }

    public int b(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? this.getPower(iblockdata) : 0;
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.DESTROY;
    }

    protected abstract int b(World var1, BlockPosition var2);

    protected abstract int getPower(IBlockData var1);

    protected abstract IBlockData a(IBlockData var1, int var2);

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
