package net.minecraft.server;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator<WorldGenFeatureHellFlowingLavaConfiguration> {
    private static final IBlockData a = Blocks.NETHERRACK.getBlockData();

    public WorldGenHellLava() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition blockposition, WorldGenFeatureHellFlowingLavaConfiguration worldgenfeaturehellflowinglavaconfiguration) {
        if (generatoraccess.getType(blockposition.up()) != a) {
            return false;
        } else if (!generatoraccess.getType(blockposition).isAir() && generatoraccess.getType(blockposition) != a) {
            return false;
        } else {
            int i = 0;
            if (generatoraccess.getType(blockposition.west()) == a) {
                ++i;
            }

            if (generatoraccess.getType(blockposition.east()) == a) {
                ++i;
            }

            if (generatoraccess.getType(blockposition.north()) == a) {
                ++i;
            }

            if (generatoraccess.getType(blockposition.south()) == a) {
                ++i;
            }

            if (generatoraccess.getType(blockposition.down()) == a) {
                ++i;
            }

            int j = 0;
            if (generatoraccess.isEmpty(blockposition.west())) {
                ++j;
            }

            if (generatoraccess.isEmpty(blockposition.east())) {
                ++j;
            }

            if (generatoraccess.isEmpty(blockposition.north())) {
                ++j;
            }

            if (generatoraccess.isEmpty(blockposition.south())) {
                ++j;
            }

            if (generatoraccess.isEmpty(blockposition.down())) {
                ++j;
            }

            if (!worldgenfeaturehellflowinglavaconfiguration.a && i == 4 && j == 1 || i == 5) {
                generatoraccess.setTypeAndData(blockposition, Blocks.LAVA.getBlockData(), 2);
                generatoraccess.I().a(blockposition, FluidTypes.e, 0);
            }

            return true;
        }
    }
}
