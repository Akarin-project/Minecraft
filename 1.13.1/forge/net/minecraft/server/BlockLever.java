package net.minecraft.server;

public class BlockLever extends BlockAttachable {
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    protected static final VoxelShape b = Block.a(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
    protected static final VoxelShape c = Block.a(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
    protected static final VoxelShape o = Block.a(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
    protected static final VoxelShape p = Block.a(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
    protected static final VoxelShape q = Block.a(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
    protected static final VoxelShape r = Block.a(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
    protected static final VoxelShape s = Block.a(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
    protected static final VoxelShape t = Block.a(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);

    protected BlockLever(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(POWERED, Boolean.valueOf(false))).set(FACE, BlockPropertyAttachPosition.WALL));
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((BlockPropertyAttachPosition)iblockdata.get(FACE)) {
        case FLOOR:
            switch(((EnumDirection)iblockdata.get(FACING)).k()) {
            case X:
                return r;
            case Z:
            default:
                return q;
            }
        case WALL:
            switch((EnumDirection)iblockdata.get(FACING)) {
            case EAST:
                return p;
            case WEST:
                return o;
            case SOUTH:
                return c;
            case NORTH:
            default:
                return b;
            }
        case CEILING:
        default:
            switch(((EnumDirection)iblockdata.get(FACING)).k()) {
            case X:
                return t;
            case Z:
            default:
                return s;
            }
        }
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman var4, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        iblockdata = (IBlockData)iblockdata.a(POWERED);
        boolean flag = iblockdata.get(POWERED);
        if (world.isClientSide) {
            if (flag) {
                a(iblockdata, world, blockposition, 1.0F);
            }

            return true;
        } else {
            world.setTypeAndData(blockposition, iblockdata, 3);
            float f = flag ? 0.6F : 0.5F;
            world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            this.b(iblockdata, world, blockposition);
            return true;
        }
    }

    private static void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, float f) {
        EnumDirection enumdirection = ((EnumDirection)iblockdata.get(FACING)).opposite();
        EnumDirection enumdirection1 = k(iblockdata).opposite();
        double d0 = (double)blockposition.getX() + 0.5D + 0.1D * (double)enumdirection.getAdjacentX() + 0.2D * (double)enumdirection1.getAdjacentX();
        double d1 = (double)blockposition.getY() + 0.5D + 0.1D * (double)enumdirection.getAdjacentY() + 0.2D * (double)enumdirection1.getAdjacentY();
        double d2 = (double)blockposition.getZ() + 0.5D + 0.1D * (double)enumdirection.getAdjacentZ() + 0.2D * (double)enumdirection1.getAdjacentZ();
        generatoraccess.addParticle(new ParticleParamRedstone(1.0F, 0.0F, 0.0F, f), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && iblockdata.getBlock() != iblockdata1.getBlock()) {
            if (iblockdata.get(POWERED)) {
                this.b(iblockdata, world, blockposition);
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

    private void b(IBlockData iblockdata, World world, BlockPosition blockposition) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.shift(k(iblockdata).opposite()), this);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACE, FACING, POWERED);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
