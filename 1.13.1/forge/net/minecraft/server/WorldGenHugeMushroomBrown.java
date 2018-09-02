package net.minecraft.server;

import java.util.Random;

public class WorldGenHugeMushroomBrown extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenHugeMushroomBrown() {
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

                for(int k = 0; k <= 1 + i; ++k) {
                    int l = k <= 3 ? 0 : 3;

                    for(int i1 = -l; i1 <= l; ++i1) {
                        for(int j1 = -l; j1 <= l; ++j1) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition$mutableblockposition.g(blockposition).d(i1, k, j1));
                            if (!iblockdata.isAir() && !iblockdata.a(TagsBlock.LEAVES)) {
                                return false;
                            }
                        }
                    }
                }

                IBlockData iblockdata1 = (IBlockData)((IBlockData)Blocks.BROWN_MUSHROOM_BLOCK.getBlockData().set(BlockHugeMushroom.p, Boolean.valueOf(true))).set(BlockHugeMushroom.q, Boolean.valueOf(false));
                boolean flag9 = true;

                for(int k1 = -3; k1 <= 3; ++k1) {
                    for(int l1 = -3; l1 <= 3; ++l1) {
                        boolean flag10 = k1 == -3;
                        boolean flag = k1 == 3;
                        boolean flag1 = l1 == -3;
                        boolean flag2 = l1 == 3;
                        boolean flag3 = flag10 || flag;
                        boolean flag4 = flag1 || flag2;
                        if (!flag3 || !flag4) {
                            blockposition$mutableblockposition.g(blockposition).d(k1, i, l1);
                            if (!generatoraccess.getType(blockposition$mutableblockposition).f(generatoraccess, blockposition$mutableblockposition)) {
                                boolean flag5 = flag10 || flag4 && k1 == -2;
                                boolean flag6 = flag || flag4 && k1 == 2;
                                boolean flag7 = flag1 || flag3 && l1 == -2;
                                boolean flag8 = flag2 || flag3 && l1 == 2;
                                this.a(generatoraccess, blockposition$mutableblockposition, (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata1.set(BlockHugeMushroom.o, Boolean.valueOf(flag5))).set(BlockHugeMushroom.b, Boolean.valueOf(flag6))).set(BlockHugeMushroom.a, Boolean.valueOf(flag7))).set(BlockHugeMushroom.c, Boolean.valueOf(flag8)));
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
