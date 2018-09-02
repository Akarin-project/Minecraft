package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenVillagePieces {
    public static void a() {
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageLibrary.class, "ViBH");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageFarm2.class, "ViDF");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageFarm.class, "ViF");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageLight.class, "ViL");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageButcher.class, "ViPH");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHouse.class, "ViSH");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHut.class, "ViSmH");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageTemple.class, "ViST");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageBlacksmith.class, "ViS");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageStartPiece.class, "ViStart");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageRoad.class, "ViSR");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHouse2.class, "ViTRH");
        WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageWell.class, "ViW");
    }

    public static List<WorldGenVillagePieces.WorldGenVillagePieceWeight> a(Random random, int i) {
        ArrayList arraylist = Lists.newArrayList();
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHouse.class, 4, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageTemple.class, 20, MathHelper.nextInt(random, 0 + i, 1 + i)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageLibrary.class, 20, MathHelper.nextInt(random, 0 + i, 2 + i)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHut.class, 3, MathHelper.nextInt(random, 2 + i, 5 + i * 3)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageButcher.class, 15, MathHelper.nextInt(random, 0 + i, 2 + i)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageFarm2.class, 3, MathHelper.nextInt(random, 1 + i, 4 + i)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageFarm.class, 3, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(random, 0, 1 + i)));
        arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHouse2.class, 8, MathHelper.nextInt(random, 0 + i, 3 + i * 2)));
        Iterator iterator = arraylist.iterator();

        while(iterator.hasNext()) {
            if (((WorldGenVillagePieces.WorldGenVillagePieceWeight)iterator.next()).d == 0) {
                iterator.remove();
            }
        }

        return arraylist;
    }

    private static int a(List<WorldGenVillagePieces.WorldGenVillagePieceWeight> list) {
        boolean flag = false;
        int i = 0;

        for(WorldGenVillagePieces.WorldGenVillagePieceWeight worldgenvillagepieces$worldgenvillagepieceweight : list) {
            if (worldgenvillagepieces$worldgenvillagepieceweight.d > 0 && worldgenvillagepieces$worldgenvillagepieceweight.c < worldgenvillagepieces$worldgenvillagepieceweight.d) {
                flag = true;
            }

            i += worldgenvillagepieces$worldgenvillagepieceweight.b;
        }

        return flag ? i : -1;
    }

    private static WorldGenVillagePieces.WorldGenVillagePiece a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, WorldGenVillagePieces.WorldGenVillagePieceWeight worldgenvillagepieces$worldgenvillagepieceweight, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
        Class oclass = worldgenvillagepieces$worldgenvillagepieceweight.a;
        Object object = null;
        if (oclass == WorldGenVillagePieces.WorldGenVillageHouse.class) {
            object = WorldGenVillagePieces.WorldGenVillageHouse.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageTemple.class) {
            object = WorldGenVillagePieces.WorldGenVillageTemple.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageLibrary.class) {
            object = WorldGenVillagePieces.WorldGenVillageLibrary.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageHut.class) {
            object = WorldGenVillagePieces.WorldGenVillageHut.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageButcher.class) {
            object = WorldGenVillagePieces.WorldGenVillageButcher.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageFarm2.class) {
            object = WorldGenVillagePieces.WorldGenVillageFarm2.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageFarm.class) {
            object = WorldGenVillagePieces.WorldGenVillageFarm.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageBlacksmith.class) {
            object = WorldGenVillagePieces.WorldGenVillageBlacksmith.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        } else if (oclass == WorldGenVillagePieces.WorldGenVillageHouse2.class) {
            object = WorldGenVillagePieces.WorldGenVillageHouse2.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
        }

        return (WorldGenVillagePieces.WorldGenVillagePiece)object;
    }

    private static WorldGenVillagePieces.WorldGenVillagePiece c(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
        int i1 = a(worldgenvillagepieces$worldgenvillagestartpiece.c);
        if (i1 <= 0) {
            return null;
        } else {
            int j1 = 0;

            while(j1 < 5) {
                ++j1;
                int k1 = random.nextInt(i1);

                for(WorldGenVillagePieces.WorldGenVillagePieceWeight worldgenvillagepieces$worldgenvillagepieceweight : worldgenvillagepieces$worldgenvillagestartpiece.c) {
                    k1 -= worldgenvillagepieces$worldgenvillagepieceweight.b;
                    if (k1 < 0) {
                        if (!worldgenvillagepieces$worldgenvillagepieceweight.a(l) || worldgenvillagepieces$worldgenvillagepieceweight == worldgenvillagepieces$worldgenvillagestartpiece.b && worldgenvillagepieces$worldgenvillagestartpiece.c.size() > 1) {
                            break;
                        }

                        WorldGenVillagePieces.WorldGenVillagePiece worldgenvillagepieces$worldgenvillagepiece = a(worldgenvillagepieces$worldgenvillagestartpiece, worldgenvillagepieces$worldgenvillagepieceweight, list, random, i, j, k, enumdirection, l);
                        if (worldgenvillagepieces$worldgenvillagepiece != null) {
                            ++worldgenvillagepieces$worldgenvillagepieceweight.c;
                            worldgenvillagepieces$worldgenvillagestartpiece.b = worldgenvillagepieces$worldgenvillagepieceweight;
                            if (!worldgenvillagepieces$worldgenvillagepieceweight.a()) {
                                worldgenvillagepieces$worldgenvillagestartpiece.c.remove(worldgenvillagepieces$worldgenvillagepieceweight);
                            }

                            return worldgenvillagepieces$worldgenvillagepiece;
                        }
                    }
                }
            }

            StructureBoundingBox structureboundingbox = WorldGenVillagePieces.WorldGenVillageLight.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection);
            if (structureboundingbox != null) {
                return new WorldGenVillagePieces.WorldGenVillageLight(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
            } else {
                return null;
            }
        }
    }

    private static StructurePiece d(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
        if (l > 50) {
            return null;
        } else if (Math.abs(i - worldgenvillagepieces$worldgenvillagestartpiece.d().a) <= 112 && Math.abs(k - worldgenvillagepieces$worldgenvillagestartpiece.d().c) <= 112) {
            WorldGenVillagePieces.WorldGenVillagePiece worldgenvillagepieces$worldgenvillagepiece = c(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l + 1);
            if (worldgenvillagepieces$worldgenvillagepiece != null) {
                list.add(worldgenvillagepieces$worldgenvillagepiece);
                worldgenvillagepieces$worldgenvillagestartpiece.d.add(worldgenvillagepieces$worldgenvillagepiece);
                return worldgenvillagepieces$worldgenvillagepiece;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static StructurePiece e(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
        if (l > 3 + worldgenvillagepieces$worldgenvillagestartpiece.a) {
            return null;
        } else if (Math.abs(i - worldgenvillagepieces$worldgenvillagestartpiece.d().a) <= 112 && Math.abs(k - worldgenvillagepieces$worldgenvillagestartpiece.d().c) <= 112) {
            StructureBoundingBox structureboundingbox = WorldGenVillagePieces.WorldGenVillageRoad.a(worldgenvillagepieces$worldgenvillagestartpiece, list, random, i, j, k, enumdirection);
            if (structureboundingbox != null && structureboundingbox.b > 10) {
                WorldGenVillagePieces.WorldGenVillageRoad worldgenvillagepieces$worldgenvillageroad = new WorldGenVillagePieces.WorldGenVillageRoad(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
                list.add(worldgenvillagepieces$worldgenvillageroad);
                worldgenvillagepieces$worldgenvillagestartpiece.e.add(worldgenvillagepieces$worldgenvillageroad);
                return worldgenvillagepieces$worldgenvillageroad;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static enum Material {
        OAK(0),
        SANDSTONE(1),
        ACACIA(2),
        SPRUCE(3);

        private final int e;

        private Material(int j) {
            this.e = j;
        }

        public int a() {
            return this.e;
        }

        public static WorldGenVillagePieces.Material a(int i) {
            WorldGenVillagePieces.Material[] aworldgenvillagepieces$material = values();
            return i >= 0 && i < aworldgenvillagepieces$material.length ? aworldgenvillagepieces$material[i] : OAK;
        }
    }

    public static class WorldGenVillageBlacksmith extends WorldGenVillagePieces.WorldGenVillagePiece {
        private boolean a;

        public WorldGenVillageBlacksmith() {
        }

        public WorldGenVillageBlacksmith(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static WorldGenVillagePieces.WorldGenVillageBlacksmith a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 10, 6, 7, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageBlacksmith(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setBoolean("Chest", this.a);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.a = nbttagcompound.getBoolean("Chest");
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 6 - 1, 0);
            }

            IBlockData iblockdata = Blocks.COBBLESTONE.getBlockData();
            IBlockData iblockdata1 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.WEST));
            IBlockData iblockdata3 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata4 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata5 = this.a(Blocks.OAK_LOG.getBlockData());
            IBlockData iblockdata6 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 9, 4, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 9, 0, 6, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 0, 9, 4, 6, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 5, 1, 8, 5, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 2, 3, 0, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 4, 0, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 0, 3, 4, 0, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 6, 0, 4, 6, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, iblockdata3, 3, 3, 1, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 3, 1, 2, 3, 3, 2, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 4, 1, 3, 5, 3, 3, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 3, 5, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 6, 5, 3, 6, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 0, 5, 3, 0, iblockdata6, iblockdata6, false);
            this.a(generatoraccess, structureboundingbox, 9, 1, 0, 9, 3, 0, iblockdata6, iblockdata6, false);
            this.a(generatoraccess, structureboundingbox, 6, 1, 4, 9, 4, 6, iblockdata, iblockdata, false);
            this.a(generatoraccess, Blocks.LAVA.getBlockData(), 7, 1, 5, structureboundingbox);
            this.a(generatoraccess, Blocks.LAVA.getBlockData(), 8, 1, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.IRON_BARS.getBlockData().set(BlockIronBars.NORTH, Boolean.valueOf(true))).set(BlockIronBars.SOUTH, Boolean.valueOf(true)), 9, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)Blocks.IRON_BARS.getBlockData().set(BlockIronBars.NORTH, Boolean.valueOf(true)), 9, 2, 4, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 7, 2, 4, 8, 2, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, iblockdata, 6, 1, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)Blocks.FURNACE.getBlockData().set(BlockFurnace.FACING, EnumDirection.SOUTH), 6, 2, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)Blocks.FURNACE.getBlockData().set(BlockFurnace.FACING, EnumDirection.SOUTH), 6, 3, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)Blocks.STONE_SLAB.getBlockData().set(BlockStepAbstract.a, BlockPropertySlabType.DOUBLE), 8, 1, 1, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 2, 6, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 4, 2, 6, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 2, 1, 4, structureboundingbox);
            this.a(generatoraccess, Blocks.OAK_PRESSURE_PLATE.getBlockData(), 2, 2, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 1, 1, 5, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 2, 1, 5, structureboundingbox);
            this.a(generatoraccess, iblockdata2, 1, 1, 4, structureboundingbox);
            if (!this.a && structureboundingbox.b(new BlockPosition(this.a(5, 5), this.d(1), this.b(5, 5)))) {
                this.a = true;
                this.a(generatoraccess, structureboundingbox, random, 5, 1, 5, LootTables.e);
            }

            for(int i = 6; i <= 8; ++i) {
                if (this.a(generatoraccess, i, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, i, -1, -1, structureboundingbox).isAir()) {
                    this.a(generatoraccess, iblockdata4, i, 0, -1, structureboundingbox);
                    if (this.a(generatoraccess, i, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                        this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), i, -1, -1, structureboundingbox);
                    }
                }
            }

            for(int k = 0; k < 7; ++k) {
                for(int j = 0; j < 10; ++j) {
                    this.a(generatoraccess, j, 6, k, structureboundingbox);
                    this.b(generatoraccess, iblockdata, j, -1, k, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 7, 1, 1, 1);
            return true;
        }

        protected int c(int var1, int var2) {
            return 3;
        }
    }

    public static class WorldGenVillageButcher extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageButcher() {
        }

        public WorldGenVillageButcher(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static WorldGenVillagePieces.WorldGenVillageButcher a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 7, 11, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageButcher(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 7 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.SOUTH));
            IBlockData iblockdata3 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.WEST));
            IBlockData iblockdata4 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata5 = this.a(Blocks.OAK_LOG.getBlockData());
            IBlockData iblockdata6 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
            this.a(generatoraccess, iblockdata, 6, 0, 6, structureboundingbox);
            IBlockData iblockdata7 = (IBlockData)((IBlockData)iblockdata6.set(BlockFence.NORTH, Boolean.valueOf(true))).set(BlockFence.SOUTH, Boolean.valueOf(true));
            IBlockData iblockdata8 = (IBlockData)((IBlockData)iblockdata6.set(BlockFence.WEST, Boolean.valueOf(true))).set(BlockFence.EAST, Boolean.valueOf(true));
            this.a(generatoraccess, structureboundingbox, 2, 1, 6, 2, 1, 9, iblockdata7, iblockdata7, false);
            this.a(generatoraccess, (IBlockData)((IBlockData)iblockdata6.set(BlockFence.SOUTH, Boolean.valueOf(true))).set(BlockFence.EAST, Boolean.valueOf(true)), 2, 1, 10, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 8, 1, 6, 8, 1, 9, iblockdata7, iblockdata7, false);
            this.a(generatoraccess, (IBlockData)((IBlockData)iblockdata6.set(BlockFence.SOUTH, Boolean.valueOf(true))).set(BlockFence.WEST, Boolean.valueOf(true)), 8, 1, 10, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 3, 1, 10, 7, 1, 10, iblockdata8, iblockdata8, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 7, 0, 4, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 0, 3, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 8, 0, 0, 8, 3, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 7, 1, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 5, 7, 1, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 7, 3, 0, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 5, 7, 3, 5, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 1, 8, 4, 1, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 4, 8, 4, 4, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 0, 5, 2, 8, 5, 3, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, iblockdata4, 0, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata4, 0, 4, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata4, 8, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata4, 8, 4, 3, structureboundingbox);
            IBlockData iblockdata9 = iblockdata1;
            IBlockData iblockdata10 = iblockdata2;

            for(int i = -1; i <= 2; ++i) {
                for(int j = 0; j <= 8; ++j) {
                    this.a(generatoraccess, iblockdata9, j, 4 + i, i, structureboundingbox);
                    this.a(generatoraccess, iblockdata10, j, 4 + i, 5 - i, structureboundingbox);
                }
            }

            this.a(generatoraccess, iblockdata5, 0, 2, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 0, 2, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 2, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 2, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 3, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 2, 1, 3, structureboundingbox);
            this.a(generatoraccess, Blocks.OAK_PRESSURE_PLATE.getBlockData(), 2, 2, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata4, 1, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata9, 2, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 1, 1, 3, structureboundingbox);
            IBlockData iblockdata11 = (IBlockData)Blocks.STONE_SLAB.getBlockData().set(BlockStepAbstract.a, BlockPropertySlabType.DOUBLE);
            this.a(generatoraccess, structureboundingbox, 5, 0, 1, 7, 0, 3, iblockdata11, iblockdata11, false);
            this.a(generatoraccess, iblockdata11, 6, 1, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata11, 6, 1, 2, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
            this.a(generatoraccess, EnumDirection.NORTH, 2, 3, 1, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 2, 1, 0, EnumDirection.NORTH);
            if (this.a(generatoraccess, 2, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 2, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata9, 2, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 2, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 2, -1, -1, structureboundingbox);
                }
            }

            this.a(generatoraccess, Blocks.AIR.getBlockData(), 6, 1, 5, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 6, 2, 5, structureboundingbox);
            this.a(generatoraccess, EnumDirection.SOUTH, 6, 3, 4, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 6, 1, 5, EnumDirection.SOUTH);

            for(int l = 0; l < 5; ++l) {
                for(int k = 0; k < 9; ++k) {
                    this.a(generatoraccess, k, 7, l, structureboundingbox);
                    this.b(generatoraccess, iblockdata, k, -1, l, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 4, 1, 2, 2);
            return true;
        }

        protected int c(int i, int j) {
            return i == 0 ? 4 : super.c(i, j);
        }
    }

    public static class WorldGenVillageFarm extends WorldGenVillagePieces.WorldGenVillagePiece {
        private IBlockData a;
        private IBlockData b;

        public WorldGenVillageFarm() {
        }

        public WorldGenVillageFarm(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
            this.a = b(random);
            this.b = b(random);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.set("CA", GameProfileSerializer.a(this.a));
            nbttagcompound.set("CB", GameProfileSerializer.a(this.b));
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.a = GameProfileSerializer.d(nbttagcompound.getCompound("CA"));
            this.b = GameProfileSerializer.d(nbttagcompound.getCompound("CB"));
        }

        private static IBlockData b(Random random) {
            switch(random.nextInt(10)) {
            case 0:
            case 1:
                return Blocks.CARROTS.getBlockData();
            case 2:
            case 3:
                return Blocks.POTATOES.getBlockData();
            case 4:
                return Blocks.BEETROOTS.getBlockData();
            default:
                return Blocks.WHEAT.getBlockData();
            }
        }

        public static WorldGenVillagePieces.WorldGenVillageFarm a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 7, 4, 9, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageFarm(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 4 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.OAK_LOG.getBlockData());
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 6, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 0, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 6, 0, 0, 6, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 5, 0, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 8, 5, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);

            for(int i = 1; i <= 7; ++i) {
                BlockCrops blockcrops = (BlockCrops)this.a.getBlock();
                int j = blockcrops.e();
                int k = j / 3;
                this.a(generatoraccess, (IBlockData)this.a.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k, j))), 1, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.a.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k, j))), 2, 1, i, structureboundingbox);
                blockcrops = (BlockCrops)this.b.getBlock();
                int l = blockcrops.e();
                int i1 = l / 3;
                this.a(generatoraccess, (IBlockData)this.b.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i1, l))), 4, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.b.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i1, l))), 5, 1, i, structureboundingbox);
            }

            for(int j1 = 0; j1 < 9; ++j1) {
                for(int k1 = 0; k1 < 7; ++k1) {
                    this.a(generatoraccess, k1, 4, j1, structureboundingbox);
                    this.b(generatoraccess, Blocks.DIRT.getBlockData(), k1, -1, j1, structureboundingbox);
                }
            }

            return true;
        }
    }

    public static class WorldGenVillageFarm2 extends WorldGenVillagePieces.WorldGenVillagePiece {
        private IBlockData a;
        private IBlockData b;
        private IBlockData c;
        private IBlockData d;

        public WorldGenVillageFarm2() {
        }

        public WorldGenVillageFarm2(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
            this.a = WorldGenVillagePieces.WorldGenVillageFarm.b(random);
            this.b = WorldGenVillagePieces.WorldGenVillageFarm.b(random);
            this.c = WorldGenVillagePieces.WorldGenVillageFarm.b(random);
            this.d = WorldGenVillagePieces.WorldGenVillageFarm.b(random);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.set("CA", GameProfileSerializer.a(this.a));
            nbttagcompound.set("CB", GameProfileSerializer.a(this.b));
            nbttagcompound.set("CC", GameProfileSerializer.a(this.c));
            nbttagcompound.set("CD", GameProfileSerializer.a(this.d));
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.a = GameProfileSerializer.d(nbttagcompound.getCompound("CA"));
            this.b = GameProfileSerializer.d(nbttagcompound.getCompound("CB"));
            this.c = GameProfileSerializer.d(nbttagcompound.getCompound("CC"));
            this.d = GameProfileSerializer.d(nbttagcompound.getCompound("CD"));
            if (!(this.a.getBlock() instanceof BlockCrops)) {
                this.a = Blocks.WHEAT.getBlockData();
            }

            if (!(this.b.getBlock() instanceof BlockCrops)) {
                this.b = Blocks.CARROTS.getBlockData();
            }

            if (!(this.c.getBlock() instanceof BlockCrops)) {
                this.c = Blocks.POTATOES.getBlockData();
            }

            if (!(this.d.getBlock() instanceof BlockCrops)) {
                this.d = Blocks.BEETROOTS.getBlockData();
            }

        }

        public static WorldGenVillagePieces.WorldGenVillageFarm2 a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 13, 4, 9, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageFarm2(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 4 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.OAK_LOG.getBlockData());
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 12, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 0, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 6, 0, 0, 6, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 12, 0, 0, 12, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 11, 0, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 8, 11, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 9, 0, 1, 9, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);

            for(int i = 1; i <= 7; ++i) {
                BlockCrops blockcrops = (BlockCrops)this.a.getBlock();
                int j = blockcrops.e();
                int k = j / 3;
                this.a(generatoraccess, (IBlockData)this.a.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k, j))), 1, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.a.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k, j))), 2, 1, i, structureboundingbox);
                blockcrops = (BlockCrops)this.b.getBlock();
                int l = blockcrops.e();
                int i1 = l / 3;
                this.a(generatoraccess, (IBlockData)this.b.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i1, l))), 4, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.b.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i1, l))), 5, 1, i, structureboundingbox);
                blockcrops = (BlockCrops)this.c.getBlock();
                int j1 = blockcrops.e();
                int k1 = j1 / 3;
                this.a(generatoraccess, (IBlockData)this.c.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k1, j1))), 7, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.c.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, k1, j1))), 8, 1, i, structureboundingbox);
                blockcrops = (BlockCrops)this.d.getBlock();
                int l1 = blockcrops.e();
                int i2 = l1 / 3;
                this.a(generatoraccess, (IBlockData)this.d.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i2, l1))), 10, 1, i, structureboundingbox);
                this.a(generatoraccess, (IBlockData)this.d.set(blockcrops.d(), Integer.valueOf(MathHelper.nextInt(random, i2, l1))), 11, 1, i, structureboundingbox);
            }

            for(int j2 = 0; j2 < 9; ++j2) {
                for(int k2 = 0; k2 < 13; ++k2) {
                    this.a(generatoraccess, k2, 4, j2, structureboundingbox);
                    this.b(generatoraccess, Blocks.DIRT.getBlockData(), k2, -1, j2, structureboundingbox);
                }
            }

            return true;
        }
    }

    public static class WorldGenVillageHouse extends WorldGenVillagePieces.WorldGenVillagePiece {
        private boolean a;

        public WorldGenVillageHouse() {
        }

        public WorldGenVillageHouse(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
            this.a = random.nextBoolean();
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setBoolean("Terrace", this.a);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.a = nbttagcompound.getBoolean("Terrace");
        }

        public static WorldGenVillagePieces.WorldGenVillageHouse a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 5, 6, 5, enumdirection);
            return StructurePiece.a(list, structureboundingbox) != null ? null : new WorldGenVillagePieces.WorldGenVillageHouse(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 6 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata3 = this.a(Blocks.OAK_LOG.getBlockData());
            IBlockData iblockdata4 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 4, 0, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 0, 4, 4, 4, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 1, 3, 4, 3, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, iblockdata, 0, 1, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 0, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 0, 3, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 1, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 3, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 0, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 0, 2, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 0, 3, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 2, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 3, 4, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 3, 3, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, structureboundingbox, 4, 1, 1, 4, 3, 3, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 4, 3, 3, 4, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 2, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 2, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 1, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 3, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 2, 3, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 3, 3, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 3, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 3, 1, 0, structureboundingbox);
            if (this.a(generatoraccess, 2, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 2, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata2, 2, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 2, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 2, -1, -1, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 3, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            if (this.a) {
                boolean flag = false;
                boolean flag1 = true;

                for(int i = 0; i <= 4; ++i) {
                    for(int j = 0; j <= 4; ++j) {
                        boolean flag2 = i == 0 || i == 4;
                        boolean flag3 = j == 0 || j == 4;
                        if (flag2 || flag3) {
                            boolean flag4 = i == 0 || i == 4;
                            boolean flag5 = j == 0 || j == 4;
                            IBlockData iblockdata5 = (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata4.set(BlockFence.SOUTH, Boolean.valueOf(flag4 && j != 0))).set(BlockFence.NORTH, Boolean.valueOf(flag4 && j != 4))).set(BlockFence.WEST, Boolean.valueOf(flag5 && i != 0))).set(BlockFence.EAST, Boolean.valueOf(flag5 && i != 4));
                            this.a(generatoraccess, iblockdata5, i, 5, j, structureboundingbox);
                        }
                    }
                }
            }

            if (this.a) {
                IBlockData iblockdata6 = (IBlockData)Blocks.LADDER.getBlockData().set(BlockLadder.FACING, EnumDirection.SOUTH);
                this.a(generatoraccess, iblockdata6, 3, 1, 3, structureboundingbox);
                this.a(generatoraccess, iblockdata6, 3, 2, 3, structureboundingbox);
                this.a(generatoraccess, iblockdata6, 3, 3, 3, structureboundingbox);
                this.a(generatoraccess, iblockdata6, 3, 4, 3, structureboundingbox);
            }

            this.a(generatoraccess, EnumDirection.NORTH, 2, 3, 1, structureboundingbox);

            for(int k = 0; k < 5; ++k) {
                for(int l = 0; l < 5; ++l) {
                    this.a(generatoraccess, l, 6, k, structureboundingbox);
                    this.b(generatoraccess, iblockdata, l, -1, k, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 1, 1, 2, 1);
            return true;
        }
    }

    public static class WorldGenVillageHouse2 extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageHouse2() {
        }

        public WorldGenVillageHouse2(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static WorldGenVillagePieces.WorldGenVillageHouse2 a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 7, 12, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageHouse2(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 7 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.SOUTH));
            IBlockData iblockdata3 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.EAST));
            IBlockData iblockdata4 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.WEST));
            IBlockData iblockdata5 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata6 = this.a(Blocks.OAK_LOG.getBlockData());
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 2, 0, 5, 8, 0, 10, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 7, 0, 4, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 0, 3, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 8, 0, 0, 8, 3, 10, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 7, 2, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 5, 2, 1, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 2, 0, 6, 2, 3, 10, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 3, 0, 10, 7, 3, 10, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 7, 3, 0, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 5, 2, 3, 5, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 1, 8, 4, 1, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 4, 3, 4, 4, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 0, 5, 2, 8, 5, 3, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, iblockdata5, 0, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 0, 4, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 4, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 4, 4, structureboundingbox);
            IBlockData iblockdata7 = iblockdata1;
            IBlockData iblockdata8 = iblockdata2;
            IBlockData iblockdata9 = iblockdata4;
            IBlockData iblockdata10 = iblockdata3;

            for(int i = -1; i <= 2; ++i) {
                for(int j = 0; j <= 8; ++j) {
                    this.a(generatoraccess, iblockdata7, j, 4 + i, i, structureboundingbox);
                    if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6)) {
                        this.a(generatoraccess, iblockdata8, j, 4 + i, 5 - i, structureboundingbox);
                    }
                }
            }

            this.a(generatoraccess, structureboundingbox, 3, 4, 5, 3, 4, 10, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 7, 4, 2, 7, 4, 10, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 4, 5, 4, 4, 5, 10, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 6, 5, 4, 6, 5, 10, iblockdata5, iblockdata5, false);
            this.a(generatoraccess, structureboundingbox, 5, 6, 3, 5, 6, 10, iblockdata5, iblockdata5, false);

            for(int k = 4; k >= 1; --k) {
                this.a(generatoraccess, iblockdata5, k, 2 + k, 7 - k, structureboundingbox);

                for(int k1 = 8 - k; k1 <= 10; ++k1) {
                    this.a(generatoraccess, iblockdata10, k, 2 + k, k1, structureboundingbox);
                }
            }

            this.a(generatoraccess, iblockdata5, 6, 6, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 7, 5, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata4, 6, 6, 4, structureboundingbox);

            for(int l = 6; l <= 8; ++l) {
                for(int l1 = 5; l1 <= 10; ++l1) {
                    this.a(generatoraccess, iblockdata9, l, 12 - l, l1, structureboundingbox);
                }
            }

            this.a(generatoraccess, iblockdata6, 0, 2, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 0, 2, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 4, 2, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 6, 2, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 8, 2, 1, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 8, 2, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 8, 2, 5, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 8, 2, 6, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 7, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 8, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 8, 2, 9, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 2, 2, 6, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 2, 2, 7, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 2, 2, 8, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 2, 2, 9, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 4, 4, 10, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 4, 10, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 6, 4, 10, structureboundingbox);
            this.a(generatoraccess, iblockdata5, 5, 5, 10, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
            this.a(generatoraccess, EnumDirection.NORTH, 2, 3, 1, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 2, 1, 0, EnumDirection.NORTH);
            this.a(generatoraccess, structureboundingbox, 1, 0, -1, 3, 2, -1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            if (this.a(generatoraccess, 2, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 2, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata7, 2, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 2, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 2, -1, -1, structureboundingbox);
                }
            }

            for(int i1 = 0; i1 < 5; ++i1) {
                for(int i2 = 0; i2 < 9; ++i2) {
                    this.a(generatoraccess, i2, 7, i1, structureboundingbox);
                    this.b(generatoraccess, iblockdata, i2, -1, i1, structureboundingbox);
                }
            }

            for(int j1 = 5; j1 < 11; ++j1) {
                for(int j2 = 2; j2 < 9; ++j2) {
                    this.a(generatoraccess, j2, 7, j1, structureboundingbox);
                    this.b(generatoraccess, iblockdata, j2, -1, j1, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 4, 1, 2, 2);
            return true;
        }
    }

    public static class WorldGenVillageHut extends WorldGenVillagePieces.WorldGenVillagePiece {
        private boolean a;
        private int b;

        public WorldGenVillageHut() {
        }

        public WorldGenVillageHut(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
            this.a = random.nextBoolean();
            this.b = random.nextInt(3);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setInt("T", this.b);
            nbttagcompound.setBoolean("C", this.a);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.b = nbttagcompound.getInt("T");
            this.a = nbttagcompound.getBoolean("C");
        }

        public static WorldGenVillagePieces.WorldGenVillageHut a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 4, 6, 5, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageHut(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 6 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata3 = this.a(Blocks.OAK_LOG.getBlockData());
            IBlockData iblockdata4 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 3, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 3, 0, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
            if (this.a) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 1, 2, 4, 3, iblockdata3, iblockdata3, false);
            } else {
                this.a(generatoraccess, structureboundingbox, 1, 5, 1, 2, 5, 3, iblockdata3, iblockdata3, false);
            }

            this.a(generatoraccess, iblockdata3, 1, 4, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 2, 4, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 1, 4, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 2, 4, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 0, 4, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 0, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 0, 4, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 3, 4, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 3, 4, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 3, 4, 3, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 3, 0, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 0, 3, 3, 0, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 4, 0, 3, 4, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 4, 3, 3, 4, iblockdata3, iblockdata3, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 3, 3, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 1, 3, 3, 3, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 2, 3, 0, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 4, 2, 3, 4, iblockdata1, iblockdata1, false);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 3, 2, 2, structureboundingbox);
            if (this.b > 0) {
                this.a(generatoraccess, (IBlockData)((IBlockData)iblockdata4.set(BlockFence.NORTH, Boolean.valueOf(true))).set(this.b == 1 ? BlockFence.WEST : BlockFence.EAST, Boolean.valueOf(true)), this.b, 1, 3, structureboundingbox);
                this.a(generatoraccess, Blocks.OAK_PRESSURE_PLATE.getBlockData(), this.b, 2, 3, structureboundingbox);
            }

            this.a(generatoraccess, Blocks.AIR.getBlockData(), 1, 1, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 1, 2, 0, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 1, 1, 0, EnumDirection.NORTH);
            if (this.a(generatoraccess, 1, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 1, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata2, 1, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 1, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 1, -1, -1, structureboundingbox);
                }
            }

            for(int i = 0; i < 5; ++i) {
                for(int j = 0; j < 4; ++j) {
                    this.a(generatoraccess, j, 6, i, structureboundingbox);
                    this.b(generatoraccess, iblockdata, j, -1, i, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 1, 1, 2, 1);
            return true;
        }
    }

    public static class WorldGenVillageLibrary extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageLibrary() {
        }

        public WorldGenVillageLibrary(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static WorldGenVillagePieces.WorldGenVillageLibrary a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 9, 6, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageLibrary(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 9 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.SOUTH));
            IBlockData iblockdata3 = this.a((IBlockData)Blocks.OAK_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.EAST));
            IBlockData iblockdata4 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata5 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata6 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 7, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 8, 0, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 5, 0, 8, 5, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 6, 1, 8, 6, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 7, 2, 8, 7, 3, iblockdata, iblockdata, false);

            for(int i = -1; i <= 2; ++i) {
                for(int j = 0; j <= 8; ++j) {
                    this.a(generatoraccess, iblockdata1, j, 6 + i, i, structureboundingbox);
                    this.a(generatoraccess, iblockdata2, j, 6 + i, 5 - i, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 5, 8, 1, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 8, 1, 0, 8, 1, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 0, 7, 1, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 4, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 5, 0, 4, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 8, 2, 5, 8, 4, 5, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 8, 2, 0, 8, 4, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 1, 0, 4, 4, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 5, 7, 4, 5, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 8, 2, 1, 8, 4, 4, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 7, 4, 0, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 4, 2, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 2, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 6, 2, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 4, 3, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 3, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 6, 3, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 3, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 3, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 2, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 3, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 8, 3, 3, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 3, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 5, 2, 5, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 6, 2, 5, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 1, 4, 1, 7, 4, 1, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 4, 7, 4, 4, iblockdata4, iblockdata4, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
            this.a(generatoraccess, iblockdata4, 7, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 7, 1, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 6, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 5, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 4, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 3, 1, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 6, 1, 3, structureboundingbox);
            this.a(generatoraccess, Blocks.OAK_PRESSURE_PLATE.getBlockData(), 6, 2, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata6, 4, 1, 3, structureboundingbox);
            this.a(generatoraccess, Blocks.OAK_PRESSURE_PLATE.getBlockData(), 4, 2, 3, structureboundingbox);
            this.a(generatoraccess, Blocks.CRAFTING_TABLE.getBlockData(), 7, 1, 1, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 1, 1, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 1, 2, 0, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 1, 1, 0, EnumDirection.NORTH);
            if (this.a(generatoraccess, 1, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 1, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata5, 1, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 1, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 1, -1, -1, structureboundingbox);
                }
            }

            for(int l = 0; l < 6; ++l) {
                for(int k = 0; k < 9; ++k) {
                    this.a(generatoraccess, k, 9, l, structureboundingbox);
                    this.b(generatoraccess, iblockdata, k, -1, l, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 2, 1, 2, 1);
            return true;
        }

        protected int c(int var1, int var2) {
            return 1;
        }
    }

    public static class WorldGenVillageLight extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageLight() {
        }

        public WorldGenVillageLight(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece var0, List<StructurePiece> list, Random var2, int i, int j, int k, EnumDirection enumdirection) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 3, 4, 2, enumdirection);
            return StructurePiece.a(list, structureboundingbox) != null ? null : structureboundingbox;
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 4 - 1, 0);
            }

            IBlockData iblockdata = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 2, 3, 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, iblockdata, 1, 0, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 1, 1, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 1, 2, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.BLACK_WOOL.getBlockData(), 1, 3, 0, structureboundingbox);
            this.a(generatoraccess, EnumDirection.EAST, 2, 3, 0, structureboundingbox);
            this.a(generatoraccess, EnumDirection.NORTH, 1, 3, 1, structureboundingbox);
            this.a(generatoraccess, EnumDirection.WEST, 0, 3, 0, structureboundingbox);
            this.a(generatoraccess, EnumDirection.SOUTH, 1, 3, -1, structureboundingbox);
            return true;
        }
    }

    abstract static class WorldGenVillagePiece extends StructurePiece {
        protected int f = -1;
        private int a;
        protected WorldGenVillagePieces.Material g;
        protected boolean h;

        public WorldGenVillagePiece() {
        }

        protected WorldGenVillagePiece(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i) {
            super(i);
            if (worldgenvillagepieces$worldgenvillagestartpiece != null) {
                this.g = worldgenvillagepieces$worldgenvillagestartpiece.g;
                this.h = worldgenvillagepieces$worldgenvillagestartpiece.h;
            }

        }

        protected void a(NBTTagCompound nbttagcompound) {
            nbttagcompound.setInt("HPos", this.f);
            nbttagcompound.setInt("VCount", this.a);
            nbttagcompound.setByte("Type", (byte)this.g.a());
            nbttagcompound.setBoolean("Zombie", this.h);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager var2) {
            this.f = nbttagcompound.getInt("HPos");
            this.a = nbttagcompound.getInt("VCount");
            this.g = WorldGenVillagePieces.Material.a(nbttagcompound.getByte("Type"));
            if (nbttagcompound.getBoolean("Desert")) {
                this.g = WorldGenVillagePieces.Material.SANDSTONE;
            }

            this.h = nbttagcompound.getBoolean("Zombie");
        }

        @Nullable
        protected StructurePiece a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j) {
            EnumDirection enumdirection = this.f();
            if (enumdirection != null) {
                switch(enumdirection) {
                case NORTH:
                default:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a - 1, this.n.b + i, this.n.c + j, EnumDirection.WEST, this.e());
                case SOUTH:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a - 1, this.n.b + i, this.n.c + j, EnumDirection.WEST, this.e());
                case WEST:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a + j, this.n.b + i, this.n.c - 1, EnumDirection.NORTH, this.e());
                case EAST:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a + j, this.n.b + i, this.n.c - 1, EnumDirection.NORTH, this.e());
                }
            } else {
                return null;
            }
        }

        @Nullable
        protected StructurePiece b(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j) {
            EnumDirection enumdirection = this.f();
            if (enumdirection != null) {
                switch(enumdirection) {
                case NORTH:
                default:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.d + 1, this.n.b + i, this.n.c + j, EnumDirection.EAST, this.e());
                case SOUTH:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.d + 1, this.n.b + i, this.n.c + j, EnumDirection.EAST, this.e());
                case WEST:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a + j, this.n.b + i, this.n.f + 1, EnumDirection.SOUTH, this.e());
                case EAST:
                    return WorldGenVillagePieces.d(worldgenvillagepieces$worldgenvillagestartpiece, list, random, this.n.a + j, this.n.b + i, this.n.f + 1, EnumDirection.SOUTH, this.e());
                }
            } else {
                return null;
            }
        }

        protected int a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox) {
            int i = 0;
            int j = 0;
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(int k = this.n.c; k <= this.n.f; ++k) {
                for(int l = this.n.a; l <= this.n.d; ++l) {
                    blockposition$mutableblockposition.c(l, 64, k);
                    if (structureboundingbox.b(blockposition$mutableblockposition)) {
                        i += generatoraccess.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, blockposition$mutableblockposition).getY();
                        ++j;
                    }
                }
            }

            if (j == 0) {
                return -1;
            } else {
                return i / j;
            }
        }

        protected static boolean a(StructureBoundingBox structureboundingbox) {
            return structureboundingbox != null && structureboundingbox.b > 10;
        }

        protected void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
            if (this.a < l) {
                for(int i1 = this.a; i1 < l; ++i1) {
                    int j1 = this.a(i + i1, k);
                    int k1 = this.d(j);
                    int l1 = this.b(i + i1, k);
                    if (!structureboundingbox.b(new BlockPosition(j1, k1, l1))) {
                        break;
                    }

                    ++this.a;
                    if (this.h) {
                        EntityZombieVillager entityzombievillager = new EntityZombieVillager(generatoraccess.getMinecraftWorld());
                        entityzombievillager.setPositionRotation((double)j1 + 0.5D, (double)k1, (double)l1 + 0.5D, 0.0F, 0.0F);
                        entityzombievillager.prepare(generatoraccess.getDamageScaler(new BlockPosition(entityzombievillager)), (GroupDataEntity)null, (NBTTagCompound)null);
                        entityzombievillager.setProfession(this.c(i1, 0));
                        entityzombievillager.di();
                        generatoraccess.addEntity(entityzombievillager);
                    } else {
                        EntityVillager entityvillager = new EntityVillager(generatoraccess.getMinecraftWorld());
                        entityvillager.setPositionRotation((double)j1 + 0.5D, (double)k1, (double)l1 + 0.5D, 0.0F, 0.0F);
                        entityvillager.setProfession(this.c(i1, generatoraccess.m().nextInt(6)));
                        entityvillager.a(generatoraccess.getDamageScaler(new BlockPosition(entityvillager)), (GroupDataEntity)null, (NBTTagCompound)null, false);
                        generatoraccess.addEntity(entityvillager);
                    }
                }

            }
        }

        protected int c(int var1, int i) {
            return i;
        }

        protected IBlockData a(IBlockData iblockdata) {
            Block block = iblockdata.getBlock();
            if (this.g == WorldGenVillagePieces.Material.SANDSTONE) {
                if (block.a(TagsBlock.LOGS) || block == Blocks.COBBLESTONE) {
                    return Blocks.SANDSTONE.getBlockData();
                }

                if (block.a(TagsBlock.PLANKS)) {
                    return Blocks.CUT_SANDSTONE.getBlockData();
                }

                if (block == Blocks.OAK_STAIRS) {
                    return (IBlockData)Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, iblockdata.get(BlockStairs.FACING));
                }

                if (block == Blocks.COBBLESTONE_STAIRS) {
                    return (IBlockData)Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, iblockdata.get(BlockStairs.FACING));
                }

                if (block == Blocks.GRAVEL) {
                    return Blocks.SANDSTONE.getBlockData();
                }

                if (block == Blocks.OAK_PRESSURE_PLATE) {
                    return Blocks.BIRCH_PRESSURE_PLATE.getBlockData();
                }
            } else if (this.g == WorldGenVillagePieces.Material.SPRUCE) {
                if (block.a(TagsBlock.LOGS)) {
                    return (IBlockData)Blocks.SPRUCE_LOG.getBlockData().set(BlockLogAbstract.AXIS, iblockdata.get(BlockLogAbstract.AXIS));
                }

                if (block.a(TagsBlock.PLANKS)) {
                    return Blocks.SPRUCE_PLANKS.getBlockData();
                }

                if (block == Blocks.OAK_STAIRS) {
                    return (IBlockData)Blocks.SPRUCE_STAIRS.getBlockData().set(BlockStairs.FACING, iblockdata.get(BlockStairs.FACING));
                }

                if (block == Blocks.OAK_FENCE) {
                    return Blocks.SPRUCE_FENCE.getBlockData();
                }

                if (block == Blocks.OAK_PRESSURE_PLATE) {
                    return Blocks.SPRUCE_PRESSURE_PLATE.getBlockData();
                }
            } else if (this.g == WorldGenVillagePieces.Material.ACACIA) {
                if (block.a(TagsBlock.LOGS)) {
                    return (IBlockData)Blocks.ACACIA_LOG.getBlockData().set(BlockLogAbstract.AXIS, iblockdata.get(BlockLogAbstract.AXIS));
                }

                if (block.a(TagsBlock.PLANKS)) {
                    return Blocks.ACACIA_PLANKS.getBlockData();
                }

                if (block == Blocks.OAK_STAIRS) {
                    return (IBlockData)Blocks.ACACIA_STAIRS.getBlockData().set(BlockStairs.FACING, iblockdata.get(BlockStairs.FACING));
                }

                if (block == Blocks.COBBLESTONE) {
                    return (IBlockData)Blocks.ACACIA_LOG.getBlockData().set(BlockLogAbstract.AXIS, EnumDirection.EnumAxis.Y);
                }

                if (block == Blocks.OAK_FENCE) {
                    return Blocks.ACACIA_FENCE.getBlockData();
                }

                if (block == Blocks.OAK_PRESSURE_PLATE) {
                    return Blocks.ACACIA_PRESSURE_PLATE.getBlockData();
                }
            }

            return iblockdata;
        }

        protected BlockDoor b() {
            if (this.g == WorldGenVillagePieces.Material.ACACIA) {
                return (BlockDoor)Blocks.ACACIA_DOOR;
            } else {
                return this.g == WorldGenVillagePieces.Material.SPRUCE ? (BlockDoor)Blocks.SPRUCE_DOOR : (BlockDoor)Blocks.OAK_DOOR;
            }
        }

        protected void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, Random random, int i, int j, int k, EnumDirection var7) {
            if (!this.h) {
                this.a(generatoraccess, structureboundingbox, random, i, j, k, EnumDirection.NORTH, this.b());
            }

        }

        protected void a(GeneratorAccess generatoraccess, EnumDirection enumdirection, int i, int j, int k, StructureBoundingBox structureboundingbox) {
            if (!this.h) {
                this.a(generatoraccess, (IBlockData)Blocks.WALL_TORCH.getBlockData().set(BlockTorchWall.a, enumdirection), i, j, k, structureboundingbox);
            }

        }

        protected void b(GeneratorAccess generatoraccess, IBlockData iblockdata, int i, int j, int k, StructureBoundingBox structureboundingbox) {
            IBlockData iblockdata1 = this.a(iblockdata);
            super.b(generatoraccess, iblockdata1, i, j, k, structureboundingbox);
        }

        protected void a(WorldGenVillagePieces.Material worldgenvillagepieces$material) {
            this.g = worldgenvillagepieces$material;
        }
    }

    public static class WorldGenVillagePieceWeight {
        public Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> a;
        public final int b;
        public int c;
        public int d;

        public WorldGenVillagePieceWeight(Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> oclass, int i, int j) {
            this.a = oclass;
            this.b = i;
            this.d = j;
        }

        public boolean a(int var1) {
            return this.d == 0 || this.c < this.d;
        }

        public boolean a() {
            return this.d == 0 || this.c < this.d;
        }
    }

    public static class WorldGenVillageRoad extends WorldGenVillagePieces.WorldGenVillageRoadPiece {
        private int a;

        public WorldGenVillageRoad() {
        }

        public WorldGenVillageRoad(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
            this.a = Math.max(structureboundingbox.c(), structureboundingbox.e());
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setInt("Length", this.a);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.a = nbttagcompound.getInt("Length");
        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            boolean flag = false;

            for(int i = random.nextInt(5); i < this.a - 8; i += 2 + random.nextInt(5)) {
                StructurePiece structurepiece1 = this.a((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, 0, i);
                if (structurepiece1 != null) {
                    i += Math.max(structurepiece1.n.c(), structurepiece1.n.e());
                    flag = true;
                }
            }

            for(int j = random.nextInt(5); j < this.a - 8; j += 2 + random.nextInt(5)) {
                StructurePiece structurepiece2 = this.b((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, 0, j);
                if (structurepiece2 != null) {
                    j += Math.max(structurepiece2.n.c(), structurepiece2.n.e());
                    flag = true;
                }
            }

            EnumDirection enumdirection = this.f();
            if (flag && random.nextInt(3) > 0 && enumdirection != null) {
                switch(enumdirection) {
                case NORTH:
                default:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a - 1, this.n.b, this.n.c, EnumDirection.WEST, this.e());
                    break;
                case SOUTH:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a - 1, this.n.b, this.n.f - 2, EnumDirection.WEST, this.e());
                    break;
                case WEST:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a, this.n.b, this.n.c - 1, EnumDirection.NORTH, this.e());
                    break;
                case EAST:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.d - 2, this.n.b, this.n.c - 1, EnumDirection.NORTH, this.e());
                }
            }

            if (flag && random.nextInt(3) > 0 && enumdirection != null) {
                switch(enumdirection) {
                case NORTH:
                default:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.d + 1, this.n.b, this.n.c, EnumDirection.EAST, this.e());
                    break;
                case SOUTH:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.d + 1, this.n.b, this.n.f - 2, EnumDirection.EAST, this.e());
                    break;
                case WEST:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a, this.n.b, this.n.f + 1, EnumDirection.SOUTH, this.e());
                    break;
                case EAST:
                    WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.d - 2, this.n.b, this.n.f + 1, EnumDirection.SOUTH, this.e());
                }
            }

        }

        public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece var0, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection) {
            for(int l = 7 * MathHelper.nextInt(random, 3, 5); l >= 7; l -= 7) {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 3, 3, l, enumdirection);
                if (StructurePiece.a(list, structureboundingbox) == null) {
                    return structureboundingbox;
                }
            }

            return null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            IBlockData iblockdata = this.a(Blocks.GRASS_PATH.getBlockData());
            IBlockData iblockdata1 = this.a(Blocks.OAK_PLANKS.getBlockData());
            IBlockData iblockdata2 = this.a(Blocks.GRAVEL.getBlockData());
            IBlockData iblockdata3 = this.a(Blocks.COBBLESTONE.getBlockData());
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
            this.n.b = 1000;
            this.n.e = 0;

            for(int i = this.n.a; i <= this.n.d; ++i) {
                for(int j = this.n.c; j <= this.n.f; ++j) {
                    blockposition$mutableblockposition.c(i, 64, j);
                    if (structureboundingbox.b(blockposition$mutableblockposition)) {
                        int k = generatoraccess.a(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, blockposition$mutableblockposition.getX(), blockposition$mutableblockposition.getZ());
                        blockposition$mutableblockposition.c(blockposition$mutableblockposition.getX(), k, blockposition$mutableblockposition.getZ()).c(EnumDirection.DOWN);
                        if (blockposition$mutableblockposition.getY() < generatoraccess.getSeaLevel()) {
                            blockposition$mutableblockposition.p(generatoraccess.getSeaLevel() - 1);
                        }

                        while(blockposition$mutableblockposition.getY() >= generatoraccess.getSeaLevel() - 1) {
                            IBlockData iblockdata4 = generatoraccess.getType(blockposition$mutableblockposition);
                            Block block = iblockdata4.getBlock();
                            if (block == Blocks.GRASS_BLOCK && generatoraccess.isEmpty(blockposition$mutableblockposition.up())) {
                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, iblockdata, 2);
                                break;
                            }

                            if (iblockdata4.getMaterial().isLiquid()) {
                                generatoraccess.setTypeAndData(new BlockPosition(blockposition$mutableblockposition), iblockdata1, 2);
                                break;
                            }

                            if (block == Blocks.SAND || block == Blocks.RED_SAND || block == Blocks.SANDSTONE || block == Blocks.CHISELED_SANDSTONE || block == Blocks.CUT_SANDSTONE || block == Blocks.RED_SANDSTONE || block == Blocks.CHISELED_SANDSTONE || block == Blocks.CUT_SANDSTONE) {
                                generatoraccess.setTypeAndData(blockposition$mutableblockposition, iblockdata2, 2);
                                generatoraccess.setTypeAndData(blockposition$mutableblockposition.down(), iblockdata3, 2);
                                break;
                            }

                            blockposition$mutableblockposition.c(EnumDirection.DOWN);
                        }

                        this.n.b = Math.min(this.n.b, blockposition$mutableblockposition.getY());
                        this.n.e = Math.max(this.n.e, blockposition$mutableblockposition.getY());
                    }
                }
            }

            return true;
        }
    }

    public abstract static class WorldGenVillageRoadPiece extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageRoadPiece() {
        }

        protected WorldGenVillageRoadPiece(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
        }
    }

    public static class WorldGenVillageStartPiece extends WorldGenVillagePieces.WorldGenVillageWell {
        public int a;
        public WorldGenVillagePieces.WorldGenVillagePieceWeight b;
        public List<WorldGenVillagePieces.WorldGenVillagePieceWeight> c;
        public List<StructurePiece> d = Lists.newArrayList();
        public List<StructurePiece> e = Lists.newArrayList();

        public WorldGenVillageStartPiece() {
        }

        public WorldGenVillageStartPiece(int var1, Random random, int i, int j, List<WorldGenVillagePieces.WorldGenVillagePieceWeight> list, WorldGenFeatureVillageConfiguration worldgenfeaturevillageconfiguration) {
            super((WorldGenVillagePieces.WorldGenVillageStartPiece)null, 0, random, i, j);
            this.c = list;
            this.a = worldgenfeaturevillageconfiguration.a;
            this.g = worldgenfeaturevillageconfiguration.b;
            this.a(this.g);
            this.h = random.nextInt(50) == 0;
        }
    }

    public static class WorldGenVillageTemple extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageTemple() {
        }

        public WorldGenVillageTemple(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random var3, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        public static WorldGenVillagePieces.WorldGenVillageTemple a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 5, 12, 9, enumdirection);
            return a(structureboundingbox) && StructurePiece.a(list, structureboundingbox) == null ? new WorldGenVillagePieces.WorldGenVillageTemple(worldgenvillagepieces$worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 12 - 1, 0);
            }

            IBlockData iblockdata = Blocks.COBBLESTONE.getBlockData();
            IBlockData iblockdata1 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH));
            IBlockData iblockdata2 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.WEST));
            IBlockData iblockdata3 = this.a((IBlockData)Blocks.COBBLESTONE_STAIRS.getBlockData().set(BlockStairs.FACING, EnumDirection.EAST));
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 3, 3, 7, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 5, 1, 3, 9, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 3, 0, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 3, 10, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 10, 3, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 4, 1, 1, 4, 10, 3, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 4, 0, 4, 7, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 4, 0, 4, 4, 4, 7, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 8, 3, 4, 8, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 5, 4, 3, 10, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 5, 5, 3, 5, 7, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 9, 0, 4, 9, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 0, 4, 0, 4, 4, 4, iblockdata, iblockdata, false);
            this.a(generatoraccess, iblockdata, 0, 11, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata, 4, 11, 2, structureboundingbox);
            this.a(generatoraccess, iblockdata, 2, 11, 0, structureboundingbox);
            this.a(generatoraccess, iblockdata, 2, 11, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata, 1, 1, 6, structureboundingbox);
            this.a(generatoraccess, iblockdata, 1, 1, 7, structureboundingbox);
            this.a(generatoraccess, iblockdata, 2, 1, 7, structureboundingbox);
            this.a(generatoraccess, iblockdata, 3, 1, 6, structureboundingbox);
            this.a(generatoraccess, iblockdata, 3, 1, 7, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 1, 5, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 2, 1, 6, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 3, 1, 5, structureboundingbox);
            this.a(generatoraccess, iblockdata2, 1, 2, 7, structureboundingbox);
            this.a(generatoraccess, iblockdata3, 3, 2, 7, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 3, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 2, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 3, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 6, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 7, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 6, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 7, 2, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 6, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 7, 0, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 6, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 7, 4, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 0, 3, 6, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.SOUTH, Boolean.valueOf(true))).set(BlockGlassPane.NORTH, Boolean.valueOf(true)), 4, 3, 6, structureboundingbox);
            this.a(generatoraccess, (IBlockData)((IBlockData)Blocks.GLASS_PANE.getBlockData().set(BlockGlassPane.EAST, Boolean.valueOf(true))).set(BlockGlassPane.WEST, Boolean.valueOf(true)), 2, 3, 8, structureboundingbox);
            this.a(generatoraccess, EnumDirection.SOUTH, 2, 4, 7, structureboundingbox);
            this.a(generatoraccess, EnumDirection.EAST, 1, 4, 6, structureboundingbox);
            this.a(generatoraccess, EnumDirection.WEST, 3, 4, 6, structureboundingbox);
            this.a(generatoraccess, EnumDirection.NORTH, 2, 4, 5, structureboundingbox);
            IBlockData iblockdata4 = (IBlockData)Blocks.LADDER.getBlockData().set(BlockLadder.FACING, EnumDirection.WEST);

            for(int i = 1; i <= 9; ++i) {
                this.a(generatoraccess, iblockdata4, 3, i, 3, structureboundingbox);
            }

            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, random, 2, 1, 0, EnumDirection.NORTH);
            if (this.a(generatoraccess, 2, 0, -1, structureboundingbox).isAir() && !this.a(generatoraccess, 2, -1, -1, structureboundingbox).isAir()) {
                this.a(generatoraccess, iblockdata1, 2, 0, -1, structureboundingbox);
                if (this.a(generatoraccess, 2, -1, -1, structureboundingbox).getBlock() == Blocks.GRASS_PATH) {
                    this.a(generatoraccess, Blocks.GRASS_BLOCK.getBlockData(), 2, -1, -1, structureboundingbox);
                }
            }

            for(int k = 0; k < 9; ++k) {
                for(int j = 0; j < 5; ++j) {
                    this.a(generatoraccess, j, 12, k, structureboundingbox);
                    this.b(generatoraccess, iblockdata, j, -1, k, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 2, 1, 2, 1);
            return true;
        }

        protected int c(int var1, int var2) {
            return 2;
        }
    }

    public static class WorldGenVillageWell extends WorldGenVillagePieces.WorldGenVillagePiece {
        public WorldGenVillageWell() {
        }

        public WorldGenVillageWell(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece, int i, Random random, int j, int k) {
            super(worldgenvillagepieces$worldgenvillagestartpiece, i);
            this.a(EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random));
            if (this.f().k() == EnumDirection.EnumAxis.Z) {
                this.n = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
            } else {
                this.n = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
            }

        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a - 1, this.n.e - 4, this.n.c + 1, EnumDirection.WEST, this.e());
            WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.d + 1, this.n.e - 4, this.n.c + 1, EnumDirection.EAST, this.e());
            WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a + 1, this.n.e - 4, this.n.c - 1, EnumDirection.NORTH, this.e());
            WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.n.a + 1, this.n.e - 4, this.n.f + 1, EnumDirection.SOUTH, this.e());
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.f < 0) {
                this.f = this.a(generatoraccess, structureboundingbox);
                if (this.f < 0) {
                    return true;
                }

                this.n.a(0, this.f - this.n.e + 3, 0);
            }

            IBlockData iblockdata = this.a(Blocks.COBBLESTONE.getBlockData());
            IBlockData iblockdata1 = this.a(Blocks.OAK_FENCE.getBlockData());
            this.a(generatoraccess, structureboundingbox, 1, 0, 1, 4, 12, 4, iblockdata, Blocks.WATER.getBlockData(), false);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 12, 2, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 3, 12, 2, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 2, 12, 3, structureboundingbox);
            this.a(generatoraccess, Blocks.AIR.getBlockData(), 3, 12, 3, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 13, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 14, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 4, 13, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 4, 14, 1, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 13, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 1, 14, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 4, 13, 4, structureboundingbox);
            this.a(generatoraccess, iblockdata1, 4, 14, 4, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 1, 15, 1, 4, 15, 4, iblockdata, iblockdata, false);

            for(int i = 0; i <= 5; ++i) {
                for(int j = 0; j <= 5; ++j) {
                    if (j == 0 || j == 5 || i == 0 || i == 5) {
                        this.a(generatoraccess, iblockdata, j, 11, i, structureboundingbox);
                        this.a(generatoraccess, j, 12, i, structureboundingbox);
                    }
                }
            }

            return true;
        }
    }
}
