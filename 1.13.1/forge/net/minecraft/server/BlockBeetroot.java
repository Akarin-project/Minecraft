package net.minecraft.server;

import java.util.Random;

public class BlockBeetroot extends BlockCrops {
    public static final BlockStateInteger a = BlockProperties.U;
    private static final VoxelShape[] c = new VoxelShape[]{Block.a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)};

    public BlockBeetroot(Block.Info block$info) {
        super(block$info);
    }

    public BlockStateInteger d() {
        return a;
    }

    public int e() {
        return 3;
    }

    protected IMaterial f() {
        return Items.BEETROOT_SEEDS;
    }

    protected IMaterial g() {
        return Items.BEETROOT;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (random.nextInt(3) != 0) {
            super.a(iblockdata, world, blockposition, random);
        }

    }

    protected int a(World world) {
        return super.a(world) / 3;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return c[iblockdata.get(this.d())];
    }
}
