package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WorldGenFeatureCoralTree extends WorldGenFeatureCoral {
    public WorldGenFeatureCoralTree() {
    }

    protected boolean a(GeneratorAccess generatoraccess, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);
        int i = random.nextInt(3) + 1;

        for(int j = 0; j < i; ++j) {
            if (!this.b(generatoraccess, random, blockposition$mutableblockposition, iblockdata)) {
                return true;
            }

            blockposition$mutableblockposition.c(EnumDirection.UP);
        }

        BlockPosition blockposition1 = blockposition$mutableblockposition.h();
        int k = random.nextInt(3) + 2;
        ArrayList arraylist = Lists.newArrayList(EnumDirection.EnumDirectionLimit.HORIZONTAL);
        Collections.shuffle(arraylist, random);

        for(EnumDirection enumdirection : arraylist.subList(0, k)) {
            blockposition$mutableblockposition.g(blockposition1);
            blockposition$mutableblockposition.c(enumdirection);
            int l = random.nextInt(5) + 2;
            int i1 = 0;

            for(int j1 = 0; j1 < l && this.b(generatoraccess, random, blockposition$mutableblockposition, iblockdata); ++j1) {
                ++i1;
                blockposition$mutableblockposition.c(EnumDirection.UP);
                if (j1 == 0 || i1 >= 2 && random.nextFloat() < 0.25F) {
                    blockposition$mutableblockposition.c(enumdirection);
                    i1 = 0;
                }
            }
        }

        return true;
    }
}
