package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureBlueIce extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenFeatureBlueIce() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        if (blockposition.getY() > generatoraccess.getSeaLevel() - 1) {
            return false;
        } else if (generatoraccess.getType(blockposition).getBlock() != Blocks.WATER && generatoraccess.getType(blockposition.down()).getBlock() != Blocks.WATER) {
            return false;
        } else {
            boolean flag = false;

            for(EnumDirection enumdirection : EnumDirection.values()) {
                if (enumdirection != EnumDirection.DOWN && generatoraccess.getType(blockposition.shift(enumdirection)).getBlock() == Blocks.PACKED_ICE) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return false;
            } else {
                generatoraccess.setTypeAndData(blockposition, Blocks.BLUE_ICE.getBlockData(), 2);

                for(int i = 0; i < 200; ++i) {
                    int j = random.nextInt(5) - random.nextInt(6);
                    int k = 3;
                    if (j < 2) {
                        k += j / 2;
                    }

                    if (k >= 1) {
                        BlockPosition blockposition1 = blockposition.a(random.nextInt(k) - random.nextInt(k), j, random.nextInt(k) - random.nextInt(k));
                        IBlockData iblockdata = generatoraccess.getType(blockposition1);
                        Block block = iblockdata.getBlock();
                        if (iblockdata.getMaterial() == Material.AIR || block == Blocks.WATER || block == Blocks.PACKED_ICE || block == Blocks.ICE) {
                            for(EnumDirection enumdirection1 : EnumDirection.values()) {
                                Block block1 = generatoraccess.getType(blockposition1.shift(enumdirection1)).getBlock();
                                if (block1 == Blocks.BLUE_ICE) {
                                    generatoraccess.setTypeAndData(blockposition1, Blocks.BLUE_ICE.getBlockData(), 2);
                                    break;
                                }
                            }
                        }
                    }
                }

                return true;
            }
        }
    }
}
