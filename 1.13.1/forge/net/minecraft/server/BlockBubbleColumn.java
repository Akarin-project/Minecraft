package net.minecraft.server;

import java.util.Random;

public class BlockBubbleColumn extends Block implements IFluidSource {
    public static final BlockStateBoolean a = BlockProperties.d;

    public BlockBubbleColumn(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Boolean.valueOf(true)));
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
        IBlockData iblockdata1 = world.getType(blockposition.up());
        if (iblockdata1.isAir()) {
            entity.j(iblockdata.get(a));
            if (!world.isClientSide) {
                WorldServer worldserver = (WorldServer)world;

                for(int i = 0; i < 2; ++i) {
                    worldserver.a(Particles.R, (double)((float)blockposition.getX() + world.random.nextFloat()), (double)(blockposition.getY() + 1), (double)((float)blockposition.getZ() + world.random.nextFloat()), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    worldserver.a(Particles.e, (double)((float)blockposition.getX() + world.random.nextFloat()), (double)(blockposition.getY() + 1), (double)((float)blockposition.getZ() + world.random.nextFloat()), 1, 0.0D, 0.01D, 0.0D, 0.2D);
                }
            }
        } else {
            entity.k(iblockdata.get(a));
        }

    }

    public void onPlace(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4) {
        a(world, blockposition.up(), a((IBlockAccess)world, blockposition.down()));
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random var4) {
        a(world, blockposition.up(), a((IBlockAccess)world, blockposition));
    }

    public Fluid h(IBlockData var1) {
        return FluidTypes.c.a(false);
    }

    public static void a(GeneratorAccess generatoraccess, BlockPosition blockposition, boolean flag) {
        if (a(generatoraccess, blockposition)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)Blocks.BUBBLE_COLUMN.getBlockData().set(a, Boolean.valueOf(flag)), 2);
        }

    }

    public static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        Fluid fluid = generatoraccess.b(blockposition);
        return generatoraccess.getType(blockposition).getBlock() == Blocks.WATER && fluid.g() >= 8 && fluid.d();
    }

    private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        Block block = iblockdata.getBlock();
        if (block == Blocks.BUBBLE_COLUMN) {
            return iblockdata.get(a);
        } else {
            return block != Blocks.SOUL_SAND;
        }
    }

    public int a(IWorldReader var1) {
        return 5;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.WATER.getBlockData();
        } else {
            if (enumdirection == EnumDirection.DOWN) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)Blocks.BUBBLE_COLUMN.getBlockData().set(a, Boolean.valueOf(a((IBlockAccess)generatoraccess, blockposition1))), 2);
            } else if (enumdirection == EnumDirection.UP && iblockdata1.getBlock() != Blocks.BUBBLE_COLUMN && a(generatoraccess, blockposition1)) {
                generatoraccess.J().a(blockposition, this, this.a(generatoraccess));
            }

            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        Block block = iworldreader.getType(blockposition.down()).getBlock();
        return block == Blocks.BUBBLE_COLUMN || block == Blocks.MAGMA_BLOCK || block == Blocks.SOUL_SAND;
    }

    public boolean j() {
        return false;
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public TextureType c() {
        return TextureType.TRANSLUCENT;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.INVISIBLE;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData var3) {
        generatoraccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 11);
        return FluidTypes.c;
    }
}
