package net.minecraft.server;

import java.util.Random;

public class WorldGenVines extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenVines() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);

        for(int i = blockposition.getY(); i < 256; ++i) {
            blockposition$mutableblockposition.g(blockposition);
            blockposition$mutableblockposition.d(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
            blockposition$mutableblockposition.p(i);
            if (generatoraccess.isEmpty(blockposition$mutableblockposition)) {
                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    IBlockData iblockdata = (IBlockData)Blocks.VINE.getBlockData().set(BlockVine.getDirection(enumdirection), Boolean.valueOf(true));
                    if (iblockdata.canPlace(generatoraccess, blockposition$mutableblockposition)) {
                        generatoraccess.setTypeAndData(blockposition$mutableblockposition, iblockdata, 2);
                        break;
                    }
                }
            }
        }

        return true;
    }
}
