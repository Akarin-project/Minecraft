package net.minecraft.server;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class BlockButtonAbstract extends BlockAttachable {
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    protected static final VoxelShape b = Block.a(6.0D, 14.0D, 5.0D, 10.0D, 16.0D, 11.0D);
    protected static final VoxelShape c = Block.a(5.0D, 14.0D, 6.0D, 11.0D, 16.0D, 10.0D);
    protected static final VoxelShape o = Block.a(6.0D, 0.0D, 5.0D, 10.0D, 2.0D, 11.0D);
    protected static final VoxelShape p = Block.a(5.0D, 0.0D, 6.0D, 11.0D, 2.0D, 10.0D);
    protected static final VoxelShape q = Block.a(5.0D, 6.0D, 14.0D, 11.0D, 10.0D, 16.0D);
    protected static final VoxelShape r = Block.a(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 2.0D);
    protected static final VoxelShape s = Block.a(14.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
    protected static final VoxelShape t = Block.a(0.0D, 6.0D, 5.0D, 2.0D, 10.0D, 11.0D);
    protected static final VoxelShape u = Block.a(6.0D, 15.0D, 5.0D, 10.0D, 16.0D, 11.0D);
    protected static final VoxelShape v = Block.a(5.0D, 15.0D, 6.0D, 11.0D, 16.0D, 10.0D);
    protected static final VoxelShape w = Block.a(6.0D, 0.0D, 5.0D, 10.0D, 1.0D, 11.0D);
    protected static final VoxelShape x = Block.a(5.0D, 0.0D, 6.0D, 11.0D, 1.0D, 10.0D);
    protected static final VoxelShape y = Block.a(5.0D, 6.0D, 15.0D, 11.0D, 10.0D, 16.0D);
    protected static final VoxelShape z = Block.a(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 1.0D);
    protected static final VoxelShape A = Block.a(15.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
    protected static final VoxelShape B = Block.a(0.0D, 6.0D, 5.0D, 1.0D, 10.0D, 11.0D);
    private final boolean E;

    protected BlockButtonAbstract(boolean flag, Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(POWERED, Boolean.valueOf(false))).set(FACE, BlockPropertyAttachPosition.WALL));
        this.E = flag;
    }

    public int a(IWorldReader var1) {
        return this.E ? 30 : 20;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        boolean flag = iblockdata.get(POWERED);
        switch((BlockPropertyAttachPosition)iblockdata.get(FACE)) {
        case FLOOR:
            if (enumdirection.k() == EnumDirection.EnumAxis.X) {
                return flag ? w : o;
            }

            return flag ? x : p;
        case WALL:
            switch(enumdirection) {
            case EAST:
                return flag ? B : t;
            case WEST:
                return flag ? A : s;
            case SOUTH:
                return flag ? z : r;
            case NORTH:
            default:
                return flag ? y : q;
            }
        case CEILING:
        default:
            if (enumdirection.k() == EnumDirection.EnumAxis.X) {
                return flag ? u : b;
            } else {
                return flag ? v : c;
            }
        }
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (iblockdata.get(POWERED)) {
            return true;
        } else {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(POWERED, Boolean.valueOf(true)), 3);
            this.a(entityhuman, world, blockposition, true);
            this.c(iblockdata, world, blockposition);
            world.J().a(blockposition, this, this.a(world));
            return true;
        }
    }

    protected void a(@Nullable EntityHuman entityhuman, GeneratorAccess generatoraccess, BlockPosition blockposition, boolean flag) {
        generatoraccess.a(flag ? entityhuman : null, blockposition, this.a(flag), SoundCategory.BLOCKS, 0.3F, flag ? 0.6F : 0.5F);
    }

    protected abstract SoundEffect a(boolean var1);

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (iblockdata.get(POWERED)) {
                this.c(iblockdata, world, blockposition);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection var4) {
        return iblockdata.get(POWERED) ? 15 : 0;
    }

    public int b(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        return iblockdata.get(POWERED) && k(iblockdata) == enumdirection ? 15 : 0;
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide && iblockdata.get(POWERED)) {
            if (this.E) {
                this.b(iblockdata, world, blockposition);
            } else {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(POWERED, Boolean.valueOf(false)), 3);
                this.c(iblockdata, world, blockposition);
                this.a((EntityHuman)null, world, blockposition, false);
            }

        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity var4) {
        if (!world.isClientSide && this.E && !iblockdata.get(POWERED)) {
            this.b(iblockdata, world, blockposition);
        }
    }

    private void b(IBlockData iblockdata, World world, BlockPosition blockposition) {
        List list = world.a(EntityArrow.class, iblockdata.g(world, blockposition).a().a(blockposition));
        boolean flag = !list.isEmpty();
        boolean flag1 = iblockdata.get(POWERED);
        if (flag != flag1) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(POWERED, Boolean.valueOf(flag)), 3);
            this.c(iblockdata, world, blockposition);
            this.a((EntityHuman)null, world, blockposition, flag);
        }

        if (flag) {
            world.J().a(new BlockPosition(blockposition), this, this.a(world));
        }

    }

    private void c(IBlockData iblockdata, World world, BlockPosition blockposition) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.shift(k(iblockdata).opposite()), this);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, POWERED, FACE);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
