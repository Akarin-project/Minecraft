package net.minecraft.server;

public class BlockDaylightDetector extends BlockTileEntity {
    public static final BlockStateInteger POWER = BlockProperties.al;
    public static final BlockStateBoolean b = BlockProperties.m;
    protected static final VoxelShape c = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);

    public BlockDaylightDetector(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(POWER, Integer.valueOf(0))).set(b, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection var4) {
        return iblockdata.get(POWER);
    }

    public static void b(IBlockData iblockdata, World world, BlockPosition blockposition) {
        if (world.worldProvider.g()) {
            int i = world.getBrightness(EnumSkyBlock.SKY, blockposition) - world.c();
            float f = world.c(1.0F);
            boolean flag = iblockdata.get(b);
            if (flag) {
                i = 15 - i;
            } else if (i > 0) {
                float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }

            i = MathHelper.clamp(i, 0, 15);
            if (iblockdata.get(POWER) != i) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(POWER, Integer.valueOf(i)), 3);
            }

        }
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        if (entityhuman.dy()) {
            if (world.isClientSide) {
                return true;
            } else {
                IBlockData iblockdata1 = (IBlockData)iblockdata.a(b);
                world.setTypeAndData(blockposition, iblockdata1, 4);
                b(iblockdata1, world, blockposition);
                return true;
            }
        } else {
            return super.interact(iblockdata, world, blockposition, entityhuman, enumhand, enumdirection, f, f1, f2);
        }
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityLightDetector();
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(POWER, b);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }
}
