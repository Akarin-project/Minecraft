package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockGrass extends BlockDirtSnowSpreadable implements IBlockFragilePlantElement {
    public BlockGrass(Block.Info block$info) {
        super(block$info);
    }

    public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData var3, boolean var4) {
        return iblockaccess.getType(blockposition.up()).isAir();
    }

    public boolean a(World var1, Random var2, BlockPosition var3, IBlockData var4) {
        return true;
    }

    public void b(World world, Random random, BlockPosition blockposition, IBlockData var4) {
        BlockPosition blockposition1 = blockposition.up();
        IBlockData iblockdata = Blocks.GRASS.getBlockData();

        for(int i = 0; i < 128; ++i) {
            BlockPosition blockposition2 = blockposition1;
            int j = 0;

            while(true) {
                if (j >= i / 16) {
                    IBlockData iblockdata2 = world.getType(blockposition2);
                    if (iblockdata2.getBlock() == iblockdata.getBlock() && random.nextInt(10) == 0) {
                        ((IBlockFragilePlantElement)iblockdata.getBlock()).b(world, random, blockposition2, iblockdata2);
                    }

                    if (!iblockdata2.isAir()) {
                        break;
                    }

                    IBlockData iblockdata1;
                    if (random.nextInt(8) == 0) {
                        List list = world.getBiome(blockposition2).f();
                        if (list.isEmpty()) {
                            break;
                        }

                        iblockdata1 = ((WorldGenFeatureCompositeFlower)list.get(0)).a(random, blockposition2);
                    } else {
                        iblockdata1 = iblockdata;
                    }

                    if (iblockdata1.canPlace(world, blockposition2)) {
                        world.setTypeAndData(blockposition2, iblockdata1, 3);
                    }
                    break;
                }

                blockposition2 = blockposition2.a(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (world.getType(blockposition2.down()).getBlock() != this || world.getType(blockposition2).k()) {
                    break;
                }

                ++j;
            }
        }

    }

    public boolean f(IBlockData var1) {
        return true;
    }

    public TextureType c() {
        return TextureType.CUTOUT_MIPPED;
    }
}
