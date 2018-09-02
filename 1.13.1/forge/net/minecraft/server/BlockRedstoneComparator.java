package net.minecraft.server;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockRedstoneComparator extends BlockDiodeAbstract implements ITileEntity {
    public static final BlockStateEnum<BlockPropertyComparatorMode> MODE = BlockProperties.aq;

    public BlockRedstoneComparator(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(c, Boolean.valueOf(false))).set(MODE, BlockPropertyComparatorMode.COMPARE));
    }

    protected int k(IBlockData var1) {
        return 2;
    }

    protected int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData var3) {
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
        return tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).c() : 0;
    }

    private int e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return iblockdata.get(MODE) == BlockPropertyComparatorMode.SUBTRACT ? Math.max(this.b(world, blockposition, iblockdata) - this.b((IWorldReader)world, blockposition, iblockdata), 0) : this.b(world, blockposition, iblockdata);
    }

    protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        int i = this.b(world, blockposition, iblockdata);
        if (i >= 15) {
            return true;
        } else if (i == 0) {
            return false;
        } else {
            return i >= this.b((IWorldReader)world, blockposition, iblockdata);
        }
    }

    protected void a(World world, BlockPosition blockposition) {
        world.n(blockposition);
    }

    protected int b(World world, BlockPosition blockposition, IBlockData iblockdata) {
        int i = super.b(world, blockposition, iblockdata);
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        IBlockData iblockdata1 = world.getType(blockposition1);
        if (iblockdata1.isComplexRedstone()) {
            i = iblockdata1.a(world, blockposition1);
        } else if (i < 15 && iblockdata1.isOccluding()) {
            blockposition1 = blockposition1.shift(enumdirection);
            iblockdata1 = world.getType(blockposition1);
            if (iblockdata1.isComplexRedstone()) {
                i = iblockdata1.a(world, blockposition1);
            } else if (iblockdata1.isAir()) {
                EntityItemFrame entityitemframe = this.a(world, enumdirection, blockposition1);
                if (entityitemframe != null) {
                    i = entityitemframe.q();
                }
            }
        }

        return i;
    }

    @Nullable
    private EntityItemFrame a(World world, EnumDirection enumdirection, BlockPosition blockposition) {
        List list = world.a(EntityItemFrame.class, new AxisAlignedBB((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), (double)(blockposition.getX() + 1), (double)(blockposition.getY() + 1), (double)(blockposition.getZ() + 1)), (entityitemframe) -> {
            return entityitemframe != null && entityitemframe.getDirection() == enumdirection;
        });
        return list.size() == 1 ? (EntityItemFrame)list.get(0) : null;
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (!entityhuman.abilities.mayBuild) {
            return false;
        } else {
            iblockdata = (IBlockData)iblockdata.a(MODE);
            float f = iblockdata.get(MODE) == BlockPropertyComparatorMode.SUBTRACT ? 0.55F : 0.5F;
            world.a(entityhuman, blockposition, SoundEffects.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            world.setTypeAndData(blockposition, iblockdata, 2);
            this.f(world, blockposition, iblockdata);
            return true;
        }
    }

    protected void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.J().b(blockposition, this)) {
            int i = this.e(world, blockposition, iblockdata);
            TileEntity tileentity = world.getTileEntity(blockposition);
            int j = tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).c() : 0;
            if (i != j || iblockdata.get(c) != this.a(world, blockposition, iblockdata)) {
                TickListPriority ticklistpriority = this.c(world, blockposition, iblockdata) ? TickListPriority.HIGH : TickListPriority.NORMAL;
                world.J().a(blockposition, this, 2, ticklistpriority);
            }

        }
    }

    private void f(World world, BlockPosition blockposition, IBlockData iblockdata) {
        int i = this.e(world, blockposition, iblockdata);
        TileEntity tileentity = world.getTileEntity(blockposition);
        int j = 0;
        if (tileentity instanceof TileEntityComparator) {
            TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
            j = tileentitycomparator.c();
            tileentitycomparator.a(i);
        }

        if (j != i || iblockdata.get(MODE) == BlockPropertyComparatorMode.COMPARE) {
            boolean flag1 = this.a(world, blockposition, iblockdata);
            boolean flag = iblockdata.get(c);
            if (flag && !flag1) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(c, Boolean.valueOf(false)), 2);
            } else if (!flag && flag1) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(c, Boolean.valueOf(true)), 2);
            }

            this.d(world, blockposition, iblockdata);
        }

    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        this.f(world, blockposition, iblockdata);
    }

    public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
        super.a(iblockdata, world, blockposition, i, j);
        TileEntity tileentity = world.getTileEntity(blockposition);
        return tileentity != null && tileentity.c(i, j);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityComparator();
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, MODE, c);
    }
}
