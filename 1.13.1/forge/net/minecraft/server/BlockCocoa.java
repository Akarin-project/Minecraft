package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockCocoa extends BlockFacingHorizontal implements IBlockFragilePlantElement {
    public static final BlockStateInteger AGE = BlockProperties.T;
    protected static final VoxelShape[] b = new VoxelShape[]{Block.a(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.a(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.a(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] c = new VoxelShape[]{Block.a(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.a(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.a(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] o = new VoxelShape[]{Block.a(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.a(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.a(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
    protected static final VoxelShape[] p = new VoxelShape[]{Block.a(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.a(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.a(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

    public BlockCocoa(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(AGE, Integer.valueOf(0)));
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (world.random.nextInt(5) == 0) {
            int i = iblockdata.get(AGE);
            if (i < 2) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(AGE, Integer.valueOf(i + 1)), 2);
            }
        }

    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        Block block = iworldreader.getType(blockposition.shift((EnumDirection)iblockdata.get(FACING))).getBlock();
        return block.a(TagsBlock.JUNGLE_LOGS);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        int i = iblockdata.get(AGE);
        switch((EnumDirection)iblockdata.get(FACING)) {
        case SOUTH:
            return p[i];
        case NORTH:
        default:
            return o[i];
        case WEST:
            return c[i];
        case EAST:
            return b[i];
        }
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = this.getBlockData();
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();

        for(EnumDirection enumdirection : blockactioncontext.e()) {
            if (enumdirection.k().c()) {
                iblockdata = (IBlockData)iblockdata.set(FACING, enumdirection);
                if (iblockdata.canPlace(world, blockposition)) {
                    return iblockdata;
                }
            }
        }

        return null;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == iblockdata.get(FACING) && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float var4, int var5) {
        int i = iblockdata.get(AGE);
        byte b0 = 1;
        if (i >= 2) {
            b0 = 3;
        }

        for(int j = 0; j < b0; ++j) {
            a(world, blockposition, new ItemStack(Items.COCOA_BEANS));
        }

    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return new ItemStack(Items.COCOA_BEANS);
    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, boolean var4) {
        return iblockdata.get(AGE) < 2;
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public void b(World world, Random var2, BlockPosition blockposition, IBlockData iblockdata) {
        world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(AGE, Integer.valueOf(iblockdata.get(AGE) + 1)), 2);
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, AGE);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
