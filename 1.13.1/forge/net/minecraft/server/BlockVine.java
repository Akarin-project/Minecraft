package net.minecraft.server;

import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockVine extends Block {
    public static final BlockStateBoolean UP = BlockSprawling.p;
    public static final BlockStateBoolean NORTH = BlockSprawling.a;
    public static final BlockStateBoolean EAST = BlockSprawling.b;
    public static final BlockStateBoolean SOUTH = BlockSprawling.c;
    public static final BlockStateBoolean WEST = BlockSprawling.o;
    public static final Map<EnumDirection, BlockStateBoolean> q = (Map)BlockSprawling.r.entrySet().stream().filter((entry) -> {
        return entry.getKey() != EnumDirection.DOWN;
    }).collect(SystemUtils.a());
    protected static final VoxelShape r = Block.a(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape s = Block.a(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    protected static final VoxelShape t = Block.a(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape u = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape v = Block.a(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

    public BlockVine(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(UP, Boolean.valueOf(false))).set(NORTH, Boolean.valueOf(false))).set(EAST, Boolean.valueOf(false))).set(SOUTH, Boolean.valueOf(false))).set(WEST, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        VoxelShape voxelshape = VoxelShapes.a();
        if (iblockdata.get(UP)) {
            voxelshape = VoxelShapes.a(voxelshape, r);
        }

        if (iblockdata.get(NORTH)) {
            voxelshape = VoxelShapes.a(voxelshape, u);
        }

        if (iblockdata.get(EAST)) {
            voxelshape = VoxelShapes.a(voxelshape, t);
        }

        if (iblockdata.get(SOUTH)) {
            voxelshape = VoxelShapes.a(voxelshape, v);
        }

        if (iblockdata.get(WEST)) {
            voxelshape = VoxelShapes.a(voxelshape, s);
        }

        return voxelshape;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        return this.k(this.m(iblockdata, iworldreader, blockposition));
    }

    private boolean k(IBlockData iblockdata) {
        return this.w(iblockdata) > 0;
    }

    private int w(IBlockData iblockdata) {
        int i = 0;

        for(BlockStateBoolean blockstateboolean : q.values()) {
            if (iblockdata.get(blockstateboolean)) {
                ++i;
            }
        }

        return i;
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.DOWN) {
            return false;
        } else {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);
            if (this.b(iblockaccess, blockposition1, enumdirection)) {
                return true;
            } else if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
                return false;
            } else {
                BlockStateBoolean blockstateboolean = (BlockStateBoolean)q.get(enumdirection);
                IBlockData iblockdata = iblockaccess.getType(blockposition.up());
                return iblockdata.getBlock() == this && iblockdata.get(blockstateboolean);
            }
        }
    }

    private boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        return iblockdata.c(iblockaccess, blockposition, enumdirection.opposite()) == EnumBlockFaceShape.SOLID && !f(iblockdata.getBlock());
    }

    protected static boolean f(Block block) {
        return block instanceof BlockShulkerBox || block instanceof BlockStainedGlass || block == Blocks.BEACON || block == Blocks.CAULDRON || block == Blocks.GLASS || block == Blocks.PISTON || block == Blocks.STICKY_PISTON || block == Blocks.PISTON_HEAD || block.a(TagsBlock.WOODEN_TRAPDOORS);
    }

    private IBlockData m(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.up();
        if (iblockdata.get(UP)) {
            iblockdata = (IBlockData)iblockdata.set(UP, Boolean.valueOf(this.b(iblockaccess, blockposition1, EnumDirection.DOWN)));
        }

        IBlockData iblockdata1 = null;

        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            BlockStateBoolean blockstateboolean = getDirection(enumdirection);
            if (iblockdata.get(blockstateboolean)) {
                boolean flag = this.a(iblockaccess, blockposition, enumdirection);
                if (!flag) {
                    if (iblockdata1 == null) {
                        iblockdata1 = iblockaccess.getType(blockposition1);
                    }

                    flag = iblockdata1.getBlock() == this && iblockdata1.get(blockstateboolean);
                }

                iblockdata = (IBlockData)iblockdata.set(blockstateboolean, Boolean.valueOf(flag));
            }
        }

        return iblockdata;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == EnumDirection.DOWN) {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            IBlockData iblockdata2 = this.m(iblockdata, generatoraccess, blockposition);
            return !this.k(iblockdata2) ? Blocks.AIR.getBlockData() : iblockdata2;
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (!world.isClientSide) {
            IBlockData iblockdata1 = this.m(iblockdata, world, blockposition);
            if (iblockdata1 != iblockdata) {
                if (this.k(iblockdata1)) {
                    world.setTypeAndData(blockposition, iblockdata1, 2);
                } else {
                    iblockdata.a(world, blockposition, 0);
                    world.setAir(blockposition);
                }

            } else if (world.random.nextInt(4) == 0) {
                EnumDirection enumdirection = EnumDirection.a(random);
                BlockPosition blockposition1 = blockposition.up();
                if (enumdirection.k().c() && !iblockdata.get(getDirection(enumdirection))) {
                    if (this.a(world, blockposition)) {
                        BlockPosition blockposition5 = blockposition.shift(enumdirection);
                        IBlockData iblockdata6 = world.getType(blockposition5);
                        if (iblockdata6.isAir()) {
                            EnumDirection enumdirection3 = enumdirection.e();
                            EnumDirection enumdirection4 = enumdirection.f();
                            boolean flag = iblockdata.get(getDirection(enumdirection3));
                            boolean flag1 = iblockdata.get(getDirection(enumdirection4));
                            BlockPosition blockposition3 = blockposition5.shift(enumdirection3);
                            BlockPosition blockposition4 = blockposition5.shift(enumdirection4);
                            if (flag && this.b(world, blockposition3, enumdirection3)) {
                                world.setTypeAndData(blockposition5, (IBlockData)this.getBlockData().set(getDirection(enumdirection3), Boolean.valueOf(true)), 2);
                            } else if (flag1 && this.b(world, blockposition4, enumdirection4)) {
                                world.setTypeAndData(blockposition5, (IBlockData)this.getBlockData().set(getDirection(enumdirection4), Boolean.valueOf(true)), 2);
                            } else {
                                EnumDirection enumdirection1 = enumdirection.opposite();
                                if (flag && world.isEmpty(blockposition3) && this.b(world, blockposition.shift(enumdirection3), enumdirection1)) {
                                    world.setTypeAndData(blockposition3, (IBlockData)this.getBlockData().set(getDirection(enumdirection1), Boolean.valueOf(true)), 2);
                                } else if (flag1 && world.isEmpty(blockposition4) && this.b(world, blockposition.shift(enumdirection4), enumdirection1)) {
                                    world.setTypeAndData(blockposition4, (IBlockData)this.getBlockData().set(getDirection(enumdirection1), Boolean.valueOf(true)), 2);
                                } else if ((double)world.random.nextFloat() < 0.05D && this.b(world, blockposition5.up(), EnumDirection.UP)) {
                                    world.setTypeAndData(blockposition5, (IBlockData)this.getBlockData().set(UP, Boolean.valueOf(true)), 2);
                                }
                            }
                        } else if (this.b(world, blockposition5, enumdirection)) {
                            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(getDirection(enumdirection), Boolean.valueOf(true)), 2);
                        }

                    }
                } else {
                    if (enumdirection == EnumDirection.UP && blockposition.getY() < 255) {
                        if (this.a(world, blockposition, enumdirection)) {
                            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(UP, Boolean.valueOf(true)), 2);
                            return;
                        }

                        if (world.isEmpty(blockposition1)) {
                            if (!this.a(world, blockposition)) {
                                return;
                            }

                            IBlockData iblockdata5 = iblockdata;

                            for(EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                                if (random.nextBoolean() || !this.b(world, blockposition1.shift(enumdirection2), EnumDirection.UP)) {
                                    iblockdata5 = (IBlockData)iblockdata5.set(getDirection(enumdirection2), Boolean.valueOf(false));
                                }
                            }

                            if (this.x(iblockdata5)) {
                                world.setTypeAndData(blockposition1, iblockdata5, 2);
                            }

                            return;
                        }
                    }

                    if (blockposition.getY() > 0) {
                        BlockPosition blockposition2 = blockposition.down();
                        IBlockData iblockdata2 = world.getType(blockposition2);
                        if (iblockdata2.isAir() || iblockdata2.getBlock() == this) {
                            IBlockData iblockdata3 = iblockdata2.isAir() ? this.getBlockData() : iblockdata2;
                            IBlockData iblockdata4 = this.a(iblockdata, iblockdata3, random);
                            if (iblockdata3 != iblockdata4 && this.x(iblockdata4)) {
                                world.setTypeAndData(blockposition2, iblockdata4, 2);
                            }
                        }
                    }

                }
            }
        }
    }

    private IBlockData a(IBlockData iblockdata, IBlockData iblockdata1, Random random) {
        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            if (random.nextBoolean()) {
                BlockStateBoolean blockstateboolean = getDirection(enumdirection);
                if (iblockdata.get(blockstateboolean)) {
                    iblockdata1 = (IBlockData)iblockdata1.set(blockstateboolean, Boolean.valueOf(true));
                }
            }
        }

        return iblockdata1;
    }

    private boolean x(IBlockData iblockdata) {
        return iblockdata.get(NORTH) || iblockdata.get(EAST) || iblockdata.get(SOUTH) || iblockdata.get(WEST);
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        boolean flag = true;
        Iterable iterable = BlockPosition.MutableBlockPosition.b(blockposition.getX() - 4, blockposition.getY() - 1, blockposition.getZ() - 4, blockposition.getX() + 4, blockposition.getY() + 1, blockposition.getZ() + 4);
        int i = 5;

        for(BlockPosition blockposition1 : iterable) {
            if (iblockaccess.getType(blockposition1).getBlock() == this) {
                --i;
                if (i <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        IBlockData iblockdata1 = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        if (iblockdata1.getBlock() == this) {
            return this.w(iblockdata1) < q.size();
        } else {
            return super.a(iblockdata, blockactioncontext);
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        boolean flag = iblockdata.getBlock() == this;
        IBlockData iblockdata1 = flag ? iblockdata : this.getBlockData();

        for(EnumDirection enumdirection : blockactioncontext.e()) {
            if (enumdirection != EnumDirection.DOWN) {
                BlockStateBoolean blockstateboolean = getDirection(enumdirection);
                boolean flag1 = flag && iblockdata.get(blockstateboolean);
                if (!flag1 && this.a(blockactioncontext.getWorld(), blockactioncontext.getClickPosition(), enumdirection)) {
                    return (IBlockData)iblockdata1.set(blockstateboolean, Boolean.valueOf(true));
                }
            }
        }

        return flag ? iblockdata1 : null;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(Blocks.VINE));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(UP, NORTH, EAST, SOUTH, WEST);
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

    public static BlockStateBoolean getDirection(EnumDirection enumdirection) {
        return (BlockStateBoolean)q.get(enumdirection);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
