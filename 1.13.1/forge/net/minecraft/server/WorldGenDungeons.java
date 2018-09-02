package net.minecraft.server;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    private static final Logger a = LogManager.getLogger();
    private static final EntityTypes<?>[] b = new EntityTypes[]{EntityTypes.SKELETON, EntityTypes.ZOMBIE, EntityTypes.ZOMBIE, EntityTypes.SPIDER};
    private static final IBlockData c = Blocks.CAVE_AIR.getBlockData();

    public WorldGenDungeons() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        boolean flag = true;
        int i = random.nextInt(2) + 2;
        int j = -i - 1;
        int k = i + 1;
        boolean flag1 = true;
        boolean flag2 = true;
        int l = random.nextInt(2) + 2;
        int i1 = -l - 1;
        int j1 = l + 1;
        int k1 = 0;

        for(int l1 = j; l1 <= k; ++l1) {
            for(int i2 = -1; i2 <= 4; ++i2) {
                for(int j2 = i1; j2 <= j1; ++j2) {
                    BlockPosition blockposition1 = blockposition.a(l1, i2, j2);
                    Material material = generatoraccess.getType(blockposition1).getMaterial();
                    boolean flag3 = material.isBuildable();
                    if (i2 == -1 && !flag3) {
                        return false;
                    }

                    if (i2 == 4 && !flag3) {
                        return false;
                    }

                    if ((l1 == j || l1 == k || j2 == i1 || j2 == j1) && i2 == 0 && generatoraccess.isEmpty(blockposition1) && generatoraccess.isEmpty(blockposition1.up())) {
                        ++k1;
                    }
                }
            }
        }

        if (k1 >= 1 && k1 <= 5) {
            for(int l2 = j; l2 <= k; ++l2) {
                for(int j3 = 3; j3 >= -1; --j3) {
                    for(int l3 = i1; l3 <= j1; ++l3) {
                        BlockPosition blockposition2 = blockposition.a(l2, j3, l3);
                        if (l2 != j && j3 != -1 && l3 != i1 && l2 != k && j3 != 4 && l3 != j1) {
                            if (generatoraccess.getType(blockposition2).getBlock() != Blocks.CHEST) {
                                generatoraccess.setTypeAndData(blockposition2, c, 2);
                            }
                        } else if (blockposition2.getY() >= 0 && !generatoraccess.getType(blockposition2.down()).getMaterial().isBuildable()) {
                            generatoraccess.setTypeAndData(blockposition2, c, 2);
                        } else if (generatoraccess.getType(blockposition2).getMaterial().isBuildable() && generatoraccess.getType(blockposition2).getBlock() != Blocks.CHEST) {
                            if (j3 == -1 && random.nextInt(4) != 0) {
                                generatoraccess.setTypeAndData(blockposition2, Blocks.MOSSY_COBBLESTONE.getBlockData(), 2);
                            } else {
                                generatoraccess.setTypeAndData(blockposition2, Blocks.COBBLESTONE.getBlockData(), 2);
                            }
                        }
                    }
                }
            }

            for(int i3 = 0; i3 < 2; ++i3) {
                for(int k3 = 0; k3 < 3; ++k3) {
                    int i4 = blockposition.getX() + random.nextInt(i * 2 + 1) - i;
                    int j4 = blockposition.getY();
                    int k4 = blockposition.getZ() + random.nextInt(l * 2 + 1) - l;
                    BlockPosition blockposition3 = new BlockPosition(i4, j4, k4);
                    if (generatoraccess.isEmpty(blockposition3)) {
                        int k2 = 0;

                        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                            if (generatoraccess.getType(blockposition3.shift(enumdirection)).getMaterial().isBuildable()) {
                                ++k2;
                            }
                        }

                        if (k2 == 1) {
                            generatoraccess.setTypeAndData(blockposition3, StructurePiece.a(generatoraccess, blockposition3, Blocks.CHEST.getBlockData()), 2);
                            TileEntityLootable.a(generatoraccess, random, blockposition3, LootTables.d);
                            break;
                        }
                    }
                }
            }

            generatoraccess.setTypeAndData(blockposition, Blocks.SPAWNER.getBlockData(), 2);
            TileEntity tileentity = generatoraccess.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner)tileentity).getSpawner().setMobName(this.a(random));
            } else {
                a.error("Failed to fetch mob spawner entity at ({}, {}, {})", blockposition.getX(), blockposition.getY(), blockposition.getZ());
            }

            return true;
        } else {
            return false;
        }
    }

    private EntityTypes<?> a(Random random) {
        return b[random.nextInt(b.length)];
    }
}
