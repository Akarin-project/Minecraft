package net.minecraft.server;

import java.util.Random;

public class BlockPistonExtension extends BlockDirectional {
    public static final BlockStateEnum<BlockPropertyPistonType> TYPE = BlockProperties.at;
    public static final BlockStateBoolean SHORT = BlockProperties.u;
    protected static final VoxelShape o = Block.a(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape p = Block.a(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    protected static final VoxelShape q = Block.a(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape r = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    protected static final VoxelShape s = Block.a(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape t = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape u = Block.a(6.0D, -4.0D, 6.0D, 10.0D, 12.0D, 10.0D);
    protected static final VoxelShape v = Block.a(6.0D, 4.0D, 6.0D, 10.0D, 20.0D, 10.0D);
    protected static final VoxelShape w = Block.a(6.0D, 6.0D, -4.0D, 10.0D, 10.0D, 12.0D);
    protected static final VoxelShape x = Block.a(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 20.0D);
    protected static final VoxelShape y = Block.a(-4.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    protected static final VoxelShape z = Block.a(4.0D, 6.0D, 6.0D, 20.0D, 10.0D, 10.0D);
    protected static final VoxelShape A = Block.a(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D);
    protected static final VoxelShape B = Block.a(6.0D, 4.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape C = Block.a(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 12.0D);
    protected static final VoxelShape D = Block.a(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape E = Block.a(0.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    protected static final VoxelShape F = Block.a(4.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);

    public BlockPistonExtension(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(TYPE, BlockPropertyPistonType.DEFAULT)).set(SHORT, Boolean.valueOf(false)));
    }

    private VoxelShape k(IBlockData iblockdata) {
        switch((EnumDirection)iblockdata.get(FACING)) {
        case DOWN:
        default:
            return t;
        case UP:
            return s;
        case NORTH:
            return r;
        case SOUTH:
            return q;
        case WEST:
            return p;
        case EAST:
            return o;
        }
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return VoxelShapes.a(this.k(iblockdata), this.w(iblockdata));
    }

    private VoxelShape w(IBlockData iblockdata) {
        boolean flag = iblockdata.get(SHORT);
        switch((EnumDirection)iblockdata.get(FACING)) {
        case DOWN:
        default:
            return flag ? B : v;
        case UP:
            return flag ? A : u;
        case NORTH:
            return flag ? D : x;
        case SOUTH:
            return flag ? C : w;
        case WEST:
            return flag ? F : z;
        case EAST:
            return flag ? E : y;
        }
    }

    public boolean r(IBlockData iblockdata) {
        return iblockdata.get(FACING) == EnumDirection.UP;
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        if (!world.isClientSide && entityhuman.abilities.canInstantlyBuild) {
            BlockPosition blockposition1 = blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite());
            Block block = world.getType(blockposition1).getBlock();
            if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
                world.setAir(blockposition1);
            }
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
            EnumDirection enumdirection = ((EnumDirection)iblockdata.get(FACING)).opposite();
            blockposition = blockposition.shift(enumdirection);
            IBlockData iblockdata2 = world.getType(blockposition);
            if ((iblockdata2.getBlock() == Blocks.PISTON || iblockdata2.getBlock() == Blocks.STICKY_PISTON) && iblockdata2.get(BlockPiston.EXTENDED)) {
                iblockdata2.a(world, blockposition, 0);
                world.setAir(blockposition);
            }

        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection.opposite() == iblockdata.get(FACING) && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        Block block = iworldreader.getType(blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite())).getBlock();
        return block == Blocks.PISTON || block == Blocks.STICKY_PISTON || block == Blocks.MOVING_PISTON;
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1) {
        if (iblockdata.canPlace(world, blockposition)) {
            BlockPosition blockposition2 = blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite());
            world.getType(blockposition2).doPhysics(world, blockposition2, block, blockposition1);
        }

    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata) {
        return new ItemStack(iblockdata.get(TYPE) == BlockPropertyPistonType.STICKY ? Blocks.STICKY_PISTON : Blocks.PISTON);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, TYPE, SHORT);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData iblockdata, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == iblockdata.get(FACING) ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
