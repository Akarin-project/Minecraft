package net.minecraft.server;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockTripwireHook extends Block {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    public static final BlockStateBoolean ATTACHED = BlockProperties.a;
    protected static final VoxelShape o = Block.a(5.0D, 0.0D, 10.0D, 11.0D, 10.0D, 16.0D);
    protected static final VoxelShape p = Block.a(5.0D, 0.0D, 0.0D, 11.0D, 10.0D, 6.0D);
    protected static final VoxelShape q = Block.a(10.0D, 0.0D, 5.0D, 16.0D, 10.0D, 11.0D);
    protected static final VoxelShape r = Block.a(0.0D, 0.0D, 5.0D, 6.0D, 10.0D, 11.0D);

    public BlockTripwireHook(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(POWERED, Boolean.valueOf(false))).set(ATTACHED, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((EnumDirection)iblockdata.get(FACING)) {
        case EAST:
        default:
            return r;
        case WEST:
            return q;
        case SOUTH:
            return p;
        case NORTH:
            return o;
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        IBlockData iblockdata1 = iworldreader.getType(blockposition1);
        boolean flag = b(iblockdata1.getBlock());
        return !flag && enumdirection.k().c() && iblockdata1.c(iworldreader, blockposition1, enumdirection) == EnumBlockFaceShape.SOLID && !iblockdata1.isPowerSource();
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection.opposite() == iblockdata.get(FACING) && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = (IBlockData)((IBlockData)this.getBlockData().set(POWERED, Boolean.valueOf(false))).set(ATTACHED, Boolean.valueOf(false));
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        EnumDirection[] aenumdirection = blockactioncontext.e();

        for(EnumDirection enumdirection : aenumdirection) {
            if (enumdirection.k().c()) {
                EnumDirection enumdirection1 = enumdirection.opposite();
                iblockdata = (IBlockData)iblockdata.set(FACING, enumdirection1);
                if (iblockdata.canPlace(world, blockposition)) {
                    return iblockdata;
                }
            }
        }

        return null;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving var4, ItemStack var5) {
        this.a(world, blockposition, iblockdata, false, false, -1, (IBlockData)null);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean flag1, int i, @Nullable IBlockData iblockdata1) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        boolean flag2 = iblockdata.get(ATTACHED);
        boolean flag3 = iblockdata.get(POWERED);
        boolean flag4 = !flag;
        boolean flag5 = false;
        int j = 0;
        IBlockData[] aiblockdata = new IBlockData[42];

        for(int k = 1; k < 42; ++k) {
            BlockPosition blockposition1 = blockposition.shift(enumdirection, k);
            IBlockData iblockdata2 = world.getType(blockposition1);
            if (iblockdata2.getBlock() == Blocks.TRIPWIRE_HOOK) {
                if (iblockdata2.get(FACING) == enumdirection.opposite()) {
                    j = k;
                }
                break;
            }

            if (iblockdata2.getBlock() != Blocks.TRIPWIRE && k != i) {
                aiblockdata[k] = null;
                flag4 = false;
            } else {
                if (k == i) {
                    iblockdata2 = (IBlockData)MoreObjects.firstNonNull(iblockdata1, iblockdata2);
                }

                boolean flag6 = !iblockdata2.get(BlockTripwire.DISARMED);
                boolean flag7 = iblockdata2.get(BlockTripwire.POWERED);
                flag5 |= flag6 && flag7;
                aiblockdata[k] = iblockdata2;
                if (k == i) {
                    world.J().a(blockposition, this, this.a(world));
                    flag4 &= flag6;
                }
            }
        }

        flag4 = flag4 & j > 1;
        flag5 = flag5 & flag4;
        IBlockData iblockdata3 = (IBlockData)((IBlockData)this.getBlockData().set(ATTACHED, Boolean.valueOf(flag4))).set(POWERED, Boolean.valueOf(flag5));
        if (j > 0) {
            BlockPosition blockposition2 = blockposition.shift(enumdirection, j);
            EnumDirection enumdirection1 = enumdirection.opposite();
            world.setTypeAndData(blockposition2, (IBlockData)iblockdata3.set(FACING, enumdirection1), 3);
            this.a(world, blockposition2, enumdirection1);
            this.a(world, blockposition2, flag4, flag5, flag2, flag3);
        }

        this.a(world, blockposition, flag4, flag5, flag2, flag3);
        if (!flag) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata3.set(FACING, enumdirection), 3);
            if (flag1) {
                this.a(world, blockposition, enumdirection);
            }
        }

        if (flag2 != flag4) {
            for(int l = 1; l < j; ++l) {
                BlockPosition blockposition3 = blockposition.shift(enumdirection, l);
                IBlockData iblockdata4 = aiblockdata[l];
                if (iblockdata4 != null) {
                    world.setTypeAndData(blockposition3, (IBlockData)iblockdata4.set(ATTACHED, Boolean.valueOf(flag4)), 3);
                    if (!world.getType(blockposition3).isAir()) {
                        ;
                    }
                }
            }
        }

    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        this.a(world, blockposition, iblockdata, false, true, -1, (IBlockData)null);
    }

    private void a(World world, BlockPosition blockposition, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        if (flag1 && !flag3) {
            world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4F, 0.6F);
        } else if (!flag1 && flag3) {
            world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4F, 0.5F);
        } else if (flag && !flag2) {
            world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4F, 0.7F);
        } else if (!flag && flag2) {
            world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
        }

    }

    private void a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.shift(enumdirection.opposite()), this);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            boolean flag1 = iblockdata.get(ATTACHED);
            boolean flag2 = iblockdata.get(POWERED);
            if (flag1 || flag2) {
                this.a(world, blockposition, iblockdata, true, false, -1, (IBlockData)null);
            }

            if (flag2) {
                world.applyPhysics(blockposition, this);
                world.applyPhysics(blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite()), this);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection var4) {
        return iblockdata.get(POWERED) ? 15 : 0;
    }

    public int b(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        if (!iblockdata.get(POWERED)) {
            return 0;
        } else {
            return iblockdata.get(FACING) == enumdirection ? 15 : 0;
        }
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
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
        blockstatelist$a.a(FACING, POWERED, ATTACHED);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
