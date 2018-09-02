package net.minecraft.server;

import java.util.Random;

public class WorldGenDesertWell extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    private static final BlockStatePredicate a = BlockStatePredicate.a(Blocks.SAND);
    private final IBlockData b = Blocks.SANDSTONE_SLAB.getBlockData();
    private final IBlockData c = Blocks.SANDSTONE.getBlockData();
    private final IBlockData d = Blocks.WATER.getBlockData();

    public WorldGenDesertWell() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        for(blockposition = blockposition.up(); generatoraccess.isEmpty(blockposition) && blockposition.getY() > 2; blockposition = blockposition.down()) {
            ;
        }

        if (!a.a(generatoraccess.getType(blockposition))) {
            return false;
        } else {
            for(int i = -2; i <= 2; ++i) {
                for(int j = -2; j <= 2; ++j) {
                    if (generatoraccess.isEmpty(blockposition.a(i, -1, j)) && generatoraccess.isEmpty(blockposition.a(i, -2, j))) {
                        return false;
                    }
                }
            }

            for(int l = -1; l <= 0; ++l) {
                for(int l1 = -2; l1 <= 2; ++l1) {
                    for(int k = -2; k <= 2; ++k) {
                        generatoraccess.setTypeAndData(blockposition.a(l1, l, k), this.c, 2);
                    }
                }
            }

            generatoraccess.setTypeAndData(blockposition, this.d, 2);

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                generatoraccess.setTypeAndData(blockposition.shift(enumdirection), this.d, 2);
            }

            for(int i1 = -2; i1 <= 2; ++i1) {
                for(int i2 = -2; i2 <= 2; ++i2) {
                    if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                        generatoraccess.setTypeAndData(blockposition.a(i1, 1, i2), this.c, 2);
                    }
                }
            }

            generatoraccess.setTypeAndData(blockposition.a(2, 1, 0), this.b, 2);
            generatoraccess.setTypeAndData(blockposition.a(-2, 1, 0), this.b, 2);
            generatoraccess.setTypeAndData(blockposition.a(0, 1, 2), this.b, 2);
            generatoraccess.setTypeAndData(blockposition.a(0, 1, -2), this.b, 2);

            for(int j1 = -1; j1 <= 1; ++j1) {
                for(int j2 = -1; j2 <= 1; ++j2) {
                    if (j1 == 0 && j2 == 0) {
                        generatoraccess.setTypeAndData(blockposition.a(j1, 4, j2), this.c, 2);
                    } else {
                        generatoraccess.setTypeAndData(blockposition.a(j1, 4, j2), this.b, 2);
                    }
                }
            }

            for(int k1 = 1; k1 <= 3; ++k1) {
                generatoraccess.setTypeAndData(blockposition.a(-1, k1, -1), this.c, 2);
                generatoraccess.setTypeAndData(blockposition.a(-1, k1, 1), this.c, 2);
                generatoraccess.setTypeAndData(blockposition.a(1, k1, -1), this.c, 2);
                generatoraccess.setTypeAndData(blockposition.a(1, k1, 1), this.c, 2);
            }

            return true;
        }
    }
}
