package net.minecraft.server;

import java.util.Random;

public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement {
    public static final BlockStateInteger STAGE = BlockProperties.am;
    protected static final VoxelShape b = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private final WorldGenTreeProvider c;

    protected BlockSapling(WorldGenTreeProvider worldgentreeprovider, Block.Info block$info) {
        super(block$info);
        this.c = worldgentreeprovider;
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(STAGE, Integer.valueOf(0)));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return b;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        super.a(iblockdata, world, blockposition, random);
        if (world.getLightLevel(blockposition.up()) >= 9 && random.nextInt(7) == 0) {
            this.grow(world, blockposition, iblockdata, random);
        }

    }

    public void grow(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (iblockdata.get(STAGE) == 0) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.a(STAGE), 4);
        } else {
            this.c.a(generatoraccess, blockposition, iblockdata, random);
        }

    }

    public boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, boolean var4) {
        return true;
    }

    public boolean a(World world, Random var2, BlockPosition var3, IBlockData var4) {
        return (double)world.random.nextFloat() < 0.45D;
    }

    public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        this.grow(world, blockposition, iblockdata, random);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(STAGE);
    }
}
