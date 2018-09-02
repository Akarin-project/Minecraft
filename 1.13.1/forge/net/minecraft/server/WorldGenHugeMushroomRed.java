package net.minecraft.server;

import java.util.Random;

public class WorldGenHugeMushroomRed extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenHugeMushroomRed() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        int i = random.nextInt(3) + 4;
        if (random.nextInt(12) == 0) {
            i *= 2;
        }

        int j = blockposition.getY();
        if (j >= 1 && j + i + 1 < 256) {
            Block block = generatoraccess.getType(blockposition.down()).getBlock();
            if (!Block.d(block) && block != Blocks.GRASS_BLOCK && block != Blocks.MYCELIUM) {
                return false;
            } else {
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for(int k = 0; k <= i; ++k) {
                    byte b0 = 0;
                    if (k < i && k >= i - 3) {
                        b0 = 2;
                    } else if (k == i) {
                        b0 = 1;
                    }

                    for(int l = -b0; l <= b0; ++l) {
                        for(int i1 = -b0; i1 <= b0; ++i1) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition.g(blockposition).d(l, k, i1));
                            if (!iblockdata.isAir() && !iblockdata.a(TagsBlock.LEAVES)) {
                                return false;
                            }
                        }
                    }
                }

                IBlockData iblockdata1 = (IBlockData)Blocks.RED_MUSHROOM_BLOCK.getBlockData().set(BlockHugeMushroom.q, Boolean.valueOf(false));

                for(int k1 = i - 3; k1 <= i; ++k1) {
                    int l1 = k1 < i ? 2 : 1;
                    boolean flag6 = false;

                    for(int j2 = -l1; j2 <= l1; ++j2) {
                        for(int j1 = -l1; j1 <= l1; ++j1) {
                            boolean flag = j2 == -l1;
                            boolean flag1 = j2 == l1;
                            boolean flag2 = j1 == -l1;
                            boolean flag3 = j1 == l1;
                            boolean flag4 = flag || flag1;
                            boolean flag5 = flag2 || flag3;
                            if (k1 >= i || flag4 != flag5) {
                                blockposition$mutableblockposition.g(blockposition).d(j2, k1, j1);
                                if (!generatoraccess.getType(blockposition$mutableblockposition).f(generatoraccess, blockposition$mutableblockposition)) {
                                    this.a(generatoraccess, blockposition$mutableblockposition, (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata1.set(BlockHugeMushroom.p, Boolean.valueOf(k1 >= i - 1))).set(BlockHugeMushroom.o, Boolean.valueOf(j2 < 0))).set(BlockHugeMushroom.b, Boolean.valueOf(j2 > 0))).set(BlockHugeMushroom.a, Boolean.valueOf(j1 < 0))).set(BlockHugeMushroom.c, Boolean.valueOf(j1 > 0)));
                                }
                            }
                        }
                    }
                }

                IBlockData iblockdata2 = (IBlockData)((IBlockData)Blocks.MUSHROOM_STEM.getBlockData().set(BlockHugeMushroom.p, Boolean.valueOf(false))).set(BlockHugeMushroom.q, Boolean.valueOf(false));

                for(int i2 = 0; i2 < i; ++i2) {
                    blockposition$mutableblockposition.g(blockposition).c(EnumDirection.UP, i2);
                    if (!generatoraccess.getType(blockposition$mutableblockposition).f(generatoraccess, blockposition$mutableblockposition)) {
                        this.a(generatoraccess, blockposition$mutableblockposition, iblockdata2);
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
