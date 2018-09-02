package net.minecraft.server;

import java.util.Random;

public class BlockSoil extends Block {
    public static final BlockStateInteger MOISTURE = BlockProperties.ai;
    protected static final VoxelShape b = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    protected BlockSoil(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(MOISTURE, Integer.valueOf(0)));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == EnumDirection.UP && !iblockdata.canPlace(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 1);
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.up());
        return !iblockdata.getMaterial().isBuildable() || iblockdata.getBlock() instanceof BlockFenceGate;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return !this.getBlockData().canPlace(blockactioncontext.getWorld(), blockactioncontext.getClickPosition()) ? Blocks.DIRT.getBlockData() : super.getPlacedState(blockactioncontext);
    }

    public int j(IBlockData var1, IBlockAccess iblockaccess, BlockPosition var3) {
        return iblockaccess.K();
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!iblockdata.canPlace(world, blockposition)) {
            b(iblockdata, world, blockposition);
        } else {
            int i = iblockdata.get(MOISTURE);
            if (!a((IWorldReader)world, blockposition) && !world.isRainingAt(blockposition.up())) {
                if (i > 0) {
                    world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(MOISTURE, Integer.valueOf(i - 1)), 2);
                } else if (!a((IBlockAccess)world, blockposition)) {
                    b(iblockdata, world, blockposition);
                }
            } else if (i < 7) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(MOISTURE, Integer.valueOf(7)), 2);
            }

        }
    }

    public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
        if (!world.isClientSide && world.random.nextFloat() < f - 0.5F && entity instanceof EntityLiving && (entity instanceof EntityHuman || world.getGameRules().getBoolean("mobGriefing")) && entity.width * entity.width * entity.length > 0.512F) {
            b(world.getType(blockposition), world, blockposition);
        }

        super.fallOn(world, blockposition, entity, f);
    }

    public static void b(IBlockData iblockdata, World world, BlockPosition blockposition) {
        world.setTypeUpdate(blockposition, a(iblockdata, Blocks.DIRT.getBlockData(), world, blockposition));
    }

    private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        Block block = iblockaccess.getType(blockposition.up()).getBlock();
        return block instanceof BlockCrops || block instanceof BlockStem || block instanceof BlockStemAttached;
    }

    private static boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition : BlockPosition.b(blockposition.a(-4, 0, -4), blockposition.a(4, 1, 4))) {
            if (iworldreader.b(blockposition$mutableblockposition).a(TagsFluid.WATER)) {
                return true;
            }
        }

        return false;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Blocks.DIRT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(MOISTURE);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
