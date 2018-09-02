package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockSnow extends Block {
    public static final BlockStateInteger LAYERS = BlockProperties.ae;
    protected static final VoxelShape[] b = new VoxelShape[]{VoxelShapes.a(), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    protected BlockSnow(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(LAYERS, Integer.valueOf(1)));
    }

    public boolean a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, PathMode pathmode) {
        switch(pathmode) {
        case LAND:
            return iblockdata.get(LAYERS) < 5;
        case WATER:
            return false;
        case AIR:
            return false;
        default:
            return false;
        }
    }

    public boolean a(IBlockData iblockdata) {
        return iblockdata.get(LAYERS) == 8;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return b[iblockdata.get(LAYERS)];
    }

    public VoxelShape f(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return b[iblockdata.get(LAYERS) - 1];
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        Block block = iblockdata.getBlock();
        if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER) {
            EnumBlockFaceShape enumblockfaceshape = iblockdata.c(iworldreader, blockposition.down(), EnumDirection.UP);
            return enumblockfaceshape == EnumBlockFaceShape.SOLID || iblockdata.a(TagsBlock.LEAVES) || block == this && iblockdata.get(LAYERS) == 8;
        } else {
            return false;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity var5, ItemStack itemstack) {
        Integer integer = (Integer)iblockdata.get(LAYERS);
        if (this.X_() && EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0) {
            if (integer == 8) {
                a(world, blockposition, new ItemStack(Blocks.SNOW_BLOCK));
            } else {
                for(int i = 0; i < integer; ++i) {
                    a(world, blockposition, this.t(iblockdata));
                }
            }
        } else {
            a(world, blockposition, new ItemStack(Items.SNOWBALL, integer));
        }

        world.setAir(blockposition);
        entityhuman.b(StatisticList.BLOCK_MINED.b(this));
        entityhuman.applyExhaustion(0.005F);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (world.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 11) {
            iblockdata.a(world, blockposition, 0);
            world.setAir(blockposition);
        }

    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        int i = iblockdata.get(LAYERS);
        if (blockactioncontext.getItemStack().getItem() == this.getItem() && i < 8) {
            if (blockactioncontext.c()) {
                return blockactioncontext.getClickedFace() == EnumDirection.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        if (iblockdata.getBlock() == this) {
            int i = iblockdata.get(LAYERS);
            return (IBlockData)iblockdata.set(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getPlacedState(blockactioncontext);
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(LAYERS);
    }

    protected boolean X_() {
        return true;
    }
}
