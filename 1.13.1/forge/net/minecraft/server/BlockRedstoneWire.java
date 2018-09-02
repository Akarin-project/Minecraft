package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class BlockRedstoneWire extends Block {
    public static final BlockStateEnum<BlockPropertyRedstoneSide> NORTH = BlockProperties.M;
    public static final BlockStateEnum<BlockPropertyRedstoneSide> EAST = BlockProperties.L;
    public static final BlockStateEnum<BlockPropertyRedstoneSide> SOUTH = BlockProperties.N;
    public static final BlockStateEnum<BlockPropertyRedstoneSide> WEST = BlockProperties.O;
    public static final BlockStateInteger POWER = BlockProperties.al;
    public static final Map<EnumDirection, BlockStateEnum<BlockPropertyRedstoneSide>> q = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, NORTH, EnumDirection.EAST, EAST, EnumDirection.SOUTH, SOUTH, EnumDirection.WEST, WEST));
    protected static final VoxelShape[] r = new VoxelShape[]{Block.a(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.a(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.a(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.a(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.a(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.a(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.a(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.a(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.a(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.a(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.a(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.a(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.a(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};
    private boolean s = true;
    private final Set<BlockPosition> t = Sets.newHashSet();

    public BlockRedstoneWire(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(NORTH, BlockPropertyRedstoneSide.NONE)).set(EAST, BlockPropertyRedstoneSide.NONE)).set(SOUTH, BlockPropertyRedstoneSide.NONE)).set(WEST, BlockPropertyRedstoneSide.NONE)).set(POWER, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return r[w(iblockdata)];
    }

    private static int w(IBlockData iblockdata) {
        int i = 0;
        boolean flag = iblockdata.get(NORTH) != BlockPropertyRedstoneSide.NONE;
        boolean flag1 = iblockdata.get(EAST) != BlockPropertyRedstoneSide.NONE;
        boolean flag2 = iblockdata.get(SOUTH) != BlockPropertyRedstoneSide.NONE;
        boolean flag3 = iblockdata.get(WEST) != BlockPropertyRedstoneSide.NONE;
        if (flag || flag2 && !flag && !flag1 && !flag3) {
            i |= 1 << EnumDirection.NORTH.get2DRotationValue();
        }

        if (flag1 || flag3 && !flag && !flag1 && !flag2) {
            i |= 1 << EnumDirection.EAST.get2DRotationValue();
        }

        if (flag2 || flag && !flag1 && !flag2 && !flag3) {
            i |= 1 << EnumDirection.SOUTH.get2DRotationValue();
        }

        if (flag3 || flag1 && !flag && !flag2 && !flag3) {
            i |= 1 << EnumDirection.WEST.get2DRotationValue();
        }

        return i;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(WEST, this.a(world, blockposition, EnumDirection.WEST))).set(EAST, this.a(world, blockposition, EnumDirection.EAST))).set(NORTH, this.a(world, blockposition, EnumDirection.NORTH))).set(SOUTH, this.a(world, blockposition, EnumDirection.SOUTH));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData var3, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition var6) {
        if (enumdirection == EnumDirection.DOWN) {
            return iblockdata;
        } else {
            return enumdirection == EnumDirection.UP ? (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(WEST, this.a(generatoraccess, blockposition, EnumDirection.WEST))).set(EAST, this.a(generatoraccess, blockposition, EnumDirection.EAST))).set(NORTH, this.a(generatoraccess, blockposition, EnumDirection.NORTH))).set(SOUTH, this.a(generatoraccess, blockposition, EnumDirection.SOUTH)) : (IBlockData)iblockdata.set((IBlockState)q.get(enumdirection), this.a(generatoraccess, blockposition, enumdirection));
        }
    }

    public void b(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                BlockPropertyRedstoneSide blockpropertyredstoneside = (BlockPropertyRedstoneSide)iblockdata.get((IBlockState)q.get(enumdirection));
                if (blockpropertyredstoneside != BlockPropertyRedstoneSide.NONE && generatoraccess.getType(blockposition$b.j(blockposition).d(enumdirection)).getBlock() != this) {
                    blockposition$b.d(EnumDirection.DOWN);
                    IBlockData iblockdata1 = generatoraccess.getType(blockposition$b);
                    if (iblockdata1.getBlock() != Blocks.OBSERVER) {
                        BlockPosition blockposition1 = blockposition$b.shift(enumdirection.opposite());
                        IBlockData iblockdata2 = iblockdata1.updateState(enumdirection.opposite(), generatoraccess.getType(blockposition1), generatoraccess, blockposition$b, blockposition1);
                        a(iblockdata1, iblockdata2, generatoraccess, blockposition$b, i);
                    }

                    blockposition$b.j(blockposition).d(enumdirection).d(EnumDirection.UP);
                    IBlockData iblockdata4 = generatoraccess.getType(blockposition$b);
                    if (iblockdata4.getBlock() != Blocks.OBSERVER) {
                        BlockPosition blockposition2 = blockposition$b.shift(enumdirection.opposite());
                        IBlockData iblockdata3 = iblockdata4.updateState(enumdirection.opposite(), generatoraccess.getType(blockposition2), generatoraccess, blockposition$b, blockposition2);
                        a(iblockdata4, iblockdata3, generatoraccess, blockposition$b, i);
                    }
                }
            }
        }

    }

    private BlockPropertyRedstoneSide a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        IBlockData iblockdata = iblockaccess.getType(blockposition.shift(enumdirection));
        IBlockData iblockdata1 = iblockaccess.getType(blockposition.up());
        if (!iblockdata1.isOccluding()) {
            boolean flag = iblockaccess.getType(blockposition1).q() || iblockaccess.getType(blockposition1).getBlock() == Blocks.GLOWSTONE;
            if (flag && k(iblockaccess.getType(blockposition1.up()))) {
                if (iblockdata.k()) {
                    return BlockPropertyRedstoneSide.UP;
                }

                return BlockPropertyRedstoneSide.SIDE;
            }
        }

        return !a(iblockaccess.getType(blockposition1), enumdirection) && (iblockdata.isOccluding() || !k(iblockaccess.getType(blockposition1.down()))) ? BlockPropertyRedstoneSide.NONE : BlockPropertyRedstoneSide.SIDE;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        return iblockdata.q() || iblockdata.getBlock() == Blocks.GLOWSTONE;
    }

    private IBlockData a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        iblockdata = this.b(world, blockposition, iblockdata);
        ArrayList arraylist = Lists.newArrayList(this.t);
        this.t.clear();

        for(BlockPosition blockposition1 : arraylist) {
            world.applyPhysics(blockposition1, this);
        }

        return iblockdata;
    }

    private IBlockData b(World world, BlockPosition blockposition, IBlockData iblockdata) {
        IBlockData iblockdata1 = iblockdata;
        int i = iblockdata.get(POWER);
        int j = 0;
        j = this.getPower(j, iblockdata);
        this.s = false;
        int k = world.u(blockposition);
        this.s = true;
        if (k > 0 && k > j - 1) {
            j = k;
        }

        int l = 0;

        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);
            boolean flag = blockposition1.getX() != blockposition.getX() || blockposition1.getZ() != blockposition.getZ();
            IBlockData iblockdata2 = world.getType(blockposition1);
            if (flag) {
                l = this.getPower(l, iblockdata2);
            }

            if (iblockdata2.isOccluding() && !world.getType(blockposition.up()).isOccluding()) {
                if (flag && blockposition.getY() >= blockposition.getY()) {
                    l = this.getPower(l, world.getType(blockposition1.up()));
                }
            } else if (!iblockdata2.isOccluding() && flag && blockposition.getY() <= blockposition.getY()) {
                l = this.getPower(l, world.getType(blockposition1.down()));
            }
        }

        if (l > j) {
            j = l - 1;
        } else if (j > 0) {
            --j;
        } else {
            j = 0;
        }

        if (k > j - 1) {
            j = k;
        }

        if (i != j) {
            iblockdata = (IBlockData)iblockdata.set(POWER, Integer.valueOf(j));
            if (world.getType(blockposition) == iblockdata1) {
                world.setTypeAndData(blockposition, iblockdata, 2);
            }

            this.t.add(blockposition);

            for(EnumDirection enumdirection1 : EnumDirection.values()) {
                this.t.add(blockposition.shift(enumdirection1));
            }
        }

        return iblockdata;
    }

    private void a(World world, BlockPosition blockposition) {
        if (world.getType(blockposition).getBlock() == this) {
            world.applyPhysics(blockposition, this);

            for(EnumDirection enumdirection : EnumDirection.values()) {
                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

        }
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata1.getBlock() != iblockdata.getBlock() && !world.isClientSide) {
            this.a(world, blockposition, iblockdata);

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.VERTICAL) {
                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

            for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                this.a(world, blockposition.shift(enumdirection1));
            }

            for(EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                BlockPosition blockposition1 = blockposition.shift(enumdirection2);
                if (world.getType(blockposition1).isOccluding()) {
                    this.a(world, blockposition1.up());
                } else {
                    this.a(world, blockposition1.down());
                }
            }

        }
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
            if (!world.isClientSide) {
                for(EnumDirection enumdirection : EnumDirection.values()) {
                    world.applyPhysics(blockposition.shift(enumdirection), this);
                }

                this.a(world, blockposition, iblockdata);

                for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    this.a(world, blockposition.shift(enumdirection1));
                }

                for(EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    BlockPosition blockposition1 = blockposition.shift(enumdirection2);
                    if (world.getType(blockposition1).isOccluding()) {
                        this.a(world, blockposition1.up());
                    } else {
                        this.a(world, blockposition1.down());
                    }
                }

            }
        }
    }

    public int getPower(int i, IBlockData iblockdata) {
        if (iblockdata.getBlock() != this) {
            return i;
        } else {
            int j = iblockdata.get(POWER);
            return j > i ? j : i;
        }
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            if (iblockdata.canPlace(world, blockposition)) {
                this.a(world, blockposition, iblockdata);
            } else {
                iblockdata.a(world, blockposition, 0);
                world.setAir(blockposition);
            }

        }
    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return !this.s ? 0 : iblockdata.a(iblockaccess, blockposition, enumdirection);
    }

    public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        if (!this.s) {
            return 0;
        } else {
            int i = iblockdata.get(POWER);
            if (i == 0) {
                return 0;
            } else if (enumdirection == EnumDirection.UP) {
                return i;
            } else {
                EnumSet enumset = EnumSet.noneOf(EnumDirection.class);

                for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    if (this.b(iblockaccess, blockposition, enumdirection1)) {
                        enumset.add(enumdirection1);
                    }
                }

                if (enumdirection.k().c() && enumset.isEmpty()) {
                    return i;
                } else if (enumset.contains(enumdirection) && !enumset.contains(enumdirection.f()) && !enumset.contains(enumdirection.e())) {
                    return i;
                } else {
                    return 0;
                }
            }
        }
    }

    private boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        IBlockData iblockdata = iblockaccess.getType(blockposition1);
        boolean flag = iblockdata.isOccluding();
        boolean flag1 = iblockaccess.getType(blockposition.up()).isOccluding();
        if (!flag1 && flag && a(iblockaccess, blockposition1.up())) {
            return true;
        } else if (a(iblockdata, enumdirection)) {
            return true;
        } else if (iblockdata.getBlock() == Blocks.REPEATER && iblockdata.get(BlockDiodeAbstract.c) && iblockdata.get(BlockDiodeAbstract.FACING) == enumdirection) {
            return true;
        } else {
            return !flag && a(iblockaccess, blockposition1.down());
        }
    }

    protected static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return k(iblockaccess.getType(blockposition));
    }

    protected static boolean k(IBlockData iblockdata) {
        return a(iblockdata, (EnumDirection)null);
    }

    protected static boolean a(IBlockData iblockdata, @Nullable EnumDirection enumdirection) {
        Block block = iblockdata.getBlock();
        if (block == Blocks.REDSTONE_WIRE) {
            return true;
        } else if (iblockdata.getBlock() == Blocks.REPEATER) {
            EnumDirection enumdirection1 = (EnumDirection)iblockdata.get(BlockRepeater.FACING);
            return enumdirection1 == enumdirection || enumdirection1.opposite() == enumdirection;
        } else if (Blocks.OBSERVER == iblockdata.getBlock()) {
            return enumdirection == iblockdata.get(BlockObserver.FACING);
        } else {
            return iblockdata.isPowerSource() && enumdirection != null;
        }
    }

    public boolean isPowerSource(IBlockData var1) {
        return this.s;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch(enumblockrotation) {
        case CLOCKWISE_180:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(SOUTH))).set(EAST, iblockdata.get(WEST))).set(SOUTH, iblockdata.get(NORTH))).set(WEST, iblockdata.get(EAST));
        case COUNTERCLOCKWISE_90:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(EAST))).set(EAST, iblockdata.get(SOUTH))).set(SOUTH, iblockdata.get(WEST))).set(WEST, iblockdata.get(NORTH));
        case CLOCKWISE_90:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(WEST))).set(EAST, iblockdata.get(NORTH))).set(SOUTH, iblockdata.get(EAST))).set(WEST, iblockdata.get(SOUTH));
        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch(enumblockmirror) {
        case LEFT_RIGHT:
            return (IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(SOUTH))).set(SOUTH, iblockdata.get(NORTH));
        case FRONT_BACK:
            return (IBlockData)((IBlockData)iblockdata.set(EAST, iblockdata.get(WEST))).set(WEST, iblockdata.get(EAST));
        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(NORTH, EAST, SOUTH, WEST, POWER);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
