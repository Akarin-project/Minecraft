package net.minecraft.server;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator<WorldGenFeatureLakeConfiguration> {
    private static final IBlockData a = Blocks.CAVE_AIR.getBlockData();

    public WorldGenLakes() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureLakeConfiguration worldgenfeaturelakeconfiguration) {
        while(blockposition.getY() > 5 && generatoraccess.isEmpty(blockposition)) {
            blockposition = blockposition.down();
        }

        if (blockposition.getY() <= 4) {
            return false;
        } else {
            blockposition = blockposition.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for(int l = 1; l < 15; ++l) {
                    for(int i1 = 1; i1 < 15; ++i1) {
                        for(int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D) {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            for(int k1 = 0; k1 < 16; ++k1) {
                for(int l2 = 0; l2 < 16; ++l2) {
                    for(int k = 0; k < 8; ++k) {
                        boolean flag1 = !aboolean[(k1 * 16 + l2) * 8 + k] && (k1 < 15 && aboolean[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && aboolean[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && aboolean[(k1 * 16 + l2 + 1) * 8 + k] || l2 > 0 && aboolean[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && aboolean[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && aboolean[(k1 * 16 + l2) * 8 + (k - 1)]);
                        if (flag1) {
                            Material material = generatoraccess.getType(blockposition.a(k1, k, l2)).getMaterial();
                            if (k >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (k < 4 && !material.isBuildable() && generatoraccess.getType(blockposition.a(k1, k, l2)).getBlock() != worldgenfeaturelakeconfiguration.a) {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int l1 = 0; l1 < 16; ++l1) {
                for(int i3 = 0; i3 < 16; ++i3) {
                    for(int i4 = 0; i4 < 8; ++i4) {
                        if (aboolean[(l1 * 16 + i3) * 8 + i4]) {
                            generatoraccess.setTypeAndData(blockposition.a(l1, i4, i3), i4 >= 4 ? a : worldgenfeaturelakeconfiguration.a.getBlockData(), 2);
                        }
                    }
                }
            }

            for(int i2 = 0; i2 < 16; ++i2) {
                for(int j3 = 0; j3 < 16; ++j3) {
                    for(int j4 = 4; j4 < 8; ++j4) {
                        if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                            BlockPosition blockposition1 = blockposition.a(i2, j4 - 1, j3);
                            if (Block.d(generatoraccess.getType(blockposition1).getBlock()) && generatoraccess.getBrightness(EnumSkyBlock.SKY, blockposition.a(i2, j4, j3)) > 0) {
                                BiomeBase biomebase = generatoraccess.getBiome(blockposition1);
                                if (biomebase.r().a().getBlock() == Blocks.MYCELIUM) {
                                    generatoraccess.setTypeAndData(blockposition1, Blocks.MYCELIUM.getBlockData(), 2);
                                } else {
                                    generatoraccess.setTypeAndData(blockposition1, Blocks.GRASS_BLOCK.getBlockData(), 2);
                                }
                            }
                        }
                    }
                }
            }

            if (worldgenfeaturelakeconfiguration.a.getBlockData().getMaterial() == Material.LAVA) {
                for(int j2 = 0; j2 < 16; ++j2) {
                    for(int k3 = 0; k3 < 16; ++k3) {
                        for(int k4 = 0; k4 < 8; ++k4) {
                            boolean flag2 = !aboolean[(j2 * 16 + k3) * 8 + k4] && (j2 < 15 && aboolean[((j2 + 1) * 16 + k3) * 8 + k4] || j2 > 0 && aboolean[((j2 - 1) * 16 + k3) * 8 + k4] || k3 < 15 && aboolean[(j2 * 16 + k3 + 1) * 8 + k4] || k3 > 0 && aboolean[(j2 * 16 + (k3 - 1)) * 8 + k4] || k4 < 7 && aboolean[(j2 * 16 + k3) * 8 + k4 + 1] || k4 > 0 && aboolean[(j2 * 16 + k3) * 8 + (k4 - 1)]);
                            if (flag2 && (k4 < 4 || random.nextInt(2) != 0) && generatoraccess.getType(blockposition.a(j2, k4, k3)).getMaterial().isBuildable()) {
                                generatoraccess.setTypeAndData(blockposition.a(j2, k4, k3), Blocks.STONE.getBlockData(), 2);
                            }
                        }
                    }
                }
            }

            if (worldgenfeaturelakeconfiguration.a.getBlockData().getMaterial() == Material.WATER) {
                for(int k2 = 0; k2 < 16; ++k2) {
                    for(int l3 = 0; l3 < 16; ++l3) {
                        boolean flag = true;
                        BlockPosition blockposition2 = blockposition.a(k2, 4, l3);
                        if (generatoraccess.getBiome(blockposition2).a(generatoraccess, blockposition2, false)) {
                            generatoraccess.setTypeAndData(blockposition2, Blocks.ICE.getBlockData(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}
