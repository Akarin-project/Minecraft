package net.minecraft.server;

import java.util.Random;

public class WorldGenLightStone1 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenLightStone1() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        if (!generatoraccess.isEmpty(blockposition)) {
            return false;
        } else if (generatoraccess.getType(blockposition.up()).getBlock() != Blocks.NETHERRACK) {
            return false;
        } else {
            generatoraccess.setTypeAndData(blockposition, Blocks.GLOWSTONE.getBlockData(), 2);

            for(int i = 0; i < 1500; ++i) {
                BlockPosition blockposition1 = blockposition.a(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                if (generatoraccess.getType(blockposition1).isAir()) {
                    int j = 0;

                    for(EnumDirection enumdirection : EnumDirection.values()) {
                        if (generatoraccess.getType(blockposition1.shift(enumdirection)).getBlock() == Blocks.GLOWSTONE) {
                            ++j;
                        }

                        if (j > 1) {
                            break;
                        }
                    }

                    if (j == 1) {
                        generatoraccess.setTypeAndData(blockposition1, Blocks.GLOWSTONE.getBlockData(), 2);
                    }
                }
            }

            return true;
        }
    }
}
