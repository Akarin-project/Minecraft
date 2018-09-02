package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenEndCityPieces {
    private static final DefinedStructureInfo a = (new DefinedStructureInfo()).a(true);
    private static final DefinedStructureInfo b = (new DefinedStructureInfo()).a(true).a(Blocks.AIR);
    private static final WorldGenEndCityPieces.PieceGenerator c = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {
        }

        public boolean a(DefinedStructureManager definedstructuremanager, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
            if (i > 8) {
                return false;
            } else {
                EnumBlockRotation enumblockrotation = worldgenendcitypieces$piece.b.c();
                WorldGenEndCityPieces.Piece worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece, blockposition, "base_floor", enumblockrotation, true));
                int j = random.nextInt(3);
                if (j == 0) {
                    WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 4, -1), "base_roof", enumblockrotation, true));
                } else if (j == 1) {
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 0, -1), "second_floor_2", enumblockrotation, false));
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 8, -1), "second_roof", enumblockrotation, false));
                    WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.e, i + 1, worldgenendcitypieces$piece1, (BlockPosition)null, list, random);
                } else if (j == 2) {
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 0, -1), "second_floor_2", enumblockrotation, false));
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 4, -1), "third_floor_2", enumblockrotation, false));
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 8, -1), "third_roof", enumblockrotation, true));
                    WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.e, i + 1, worldgenendcitypieces$piece1, (BlockPosition)null, list, random);
                }

                return true;
            }
        }
    };
    private static final List<Tuple<EnumBlockRotation, BlockPosition>> d = Lists.newArrayList(new Tuple[]{new Tuple(EnumBlockRotation.NONE, new BlockPosition(1, -1, 0)), new Tuple(EnumBlockRotation.CLOCKWISE_90, new BlockPosition(6, -1, 1)), new Tuple(EnumBlockRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 5)), new Tuple(EnumBlockRotation.CLOCKWISE_180, new BlockPosition(5, -1, 6))});
    private static final WorldGenEndCityPieces.PieceGenerator e = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {
        }

        public boolean a(DefinedStructureManager definedstructuremanager, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition var4, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces$piece.b.c();
            WorldGenEndCityPieces.Piece worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(3 + random.nextInt(2), -3, 3 + random.nextInt(2)), "tower_base", enumblockrotation, true));
            worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, 7, 0), "tower_piece", enumblockrotation, true));
            WorldGenEndCityPieces.Piece worldgenendcitypieces$piece2 = random.nextInt(3) == 0 ? worldgenendcitypieces$piece1 : null;
            int j = 1 + random.nextInt(3);

            for(int k = 0; k < j; ++k) {
                worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, 4, 0), "tower_piece", enumblockrotation, true));
                if (k < j - 1 && random.nextBoolean()) {
                    worldgenendcitypieces$piece2 = worldgenendcitypieces$piece1;
                }
            }

            if (worldgenendcitypieces$piece2 != null) {
                for(Tuple tuple : WorldGenEndCityPieces.d) {
                    if (random.nextBoolean()) {
                        WorldGenEndCityPieces.Piece worldgenendcitypieces$piece3 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece2, (BlockPosition)tuple.b(), "bridge_end", enumblockrotation.a((EnumBlockRotation)tuple.a()), true));
                        WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.f, i + 1, worldgenendcitypieces$piece3, (BlockPosition)null, list, random);
                    }
                }

                WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 4, -1), "tower_top", enumblockrotation, true));
            } else {
                if (i != 7) {
                    return WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.h, i + 1, worldgenendcitypieces$piece1, (BlockPosition)null, list, random);
                }

                WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-1, 4, -1), "tower_top", enumblockrotation, true));
            }

            return true;
        }
    };
    private static final WorldGenEndCityPieces.PieceGenerator f = new WorldGenEndCityPieces.PieceGenerator() {
        public boolean a;

        public void a() {
            this.a = false;
        }

        public boolean a(DefinedStructureManager definedstructuremanager, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition var4, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces$piece.b.c();
            int j = random.nextInt(4) + 1;
            WorldGenEndCityPieces.Piece worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(0, 0, -4), "bridge_piece", enumblockrotation, true));
            worldgenendcitypieces$piece1.o = -1;
            byte b0 = 0;

            for(int k = 0; k < j; ++k) {
                if (random.nextBoolean()) {
                    worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, b0, -4), "bridge_piece", enumblockrotation, true));
                    b0 = 0;
                } else {
                    if (random.nextBoolean()) {
                        worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, b0, -4), "bridge_steep_stairs", enumblockrotation, true));
                    } else {
                        worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, b0, -8), "bridge_gentle_stairs", enumblockrotation, true));
                    }

                    b0 = 4;
                }
            }

            if (!this.a && random.nextInt(10 - i) == 0) {
                WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-8 + random.nextInt(8), b0, -70 + random.nextInt(10)), "ship", enumblockrotation, true));
                this.a = true;
            } else if (!WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.c, i + 1, worldgenendcitypieces$piece1, new BlockPosition(-3, b0 + 1, -11), list, random)) {
                return false;
            }

            worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(4, b0, 0), "bridge_end", enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180), true));
            worldgenendcitypieces$piece1.o = -1;
            return true;
        }
    };
    private static final List<Tuple<EnumBlockRotation, BlockPosition>> g = Lists.newArrayList(new Tuple[]{new Tuple(EnumBlockRotation.NONE, new BlockPosition(4, -1, 0)), new Tuple(EnumBlockRotation.CLOCKWISE_90, new BlockPosition(12, -1, 4)), new Tuple(EnumBlockRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 8)), new Tuple(EnumBlockRotation.CLOCKWISE_180, new BlockPosition(8, -1, 12))});
    private static final WorldGenEndCityPieces.PieceGenerator h = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {
        }

        public boolean a(DefinedStructureManager definedstructuremanager, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition var4, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces$piece.b.c();
            WorldGenEndCityPieces.Piece worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(-3, 4, -3), "fat_tower_base", enumblockrotation, true));
            worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, 4, 0), "fat_tower_middle", enumblockrotation, true));

            for(int j = 0; j < 2 && random.nextInt(3) != 0; ++j) {
                worldgenendcitypieces$piece1 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(0, 8, 0), "fat_tower_middle", enumblockrotation, true));

                for(Tuple tuple : WorldGenEndCityPieces.g) {
                    if (random.nextBoolean()) {
                        WorldGenEndCityPieces.Piece worldgenendcitypieces$piece2 = WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, (BlockPosition)tuple.b(), "bridge_end", enumblockrotation.a((EnumBlockRotation)tuple.a()), true));
                        WorldGenEndCityPieces.b(definedstructuremanager, WorldGenEndCityPieces.f, i + 1, worldgenendcitypieces$piece2, (BlockPosition)null, list, random);
                    }
                }
            }

            WorldGenEndCityPieces.b(list, WorldGenEndCityPieces.b(definedstructuremanager, worldgenendcitypieces$piece1, new BlockPosition(-2, 8, -2), "fat_tower_top", enumblockrotation, true));
            return true;
        }
    };

    public static void a() {
        WorldGenFactory.a(WorldGenEndCityPieces.Piece.class, "ECP");
    }

    private static WorldGenEndCityPieces.Piece b(DefinedStructureManager definedstructuremanager, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition blockposition, String s, EnumBlockRotation enumblockrotation, boolean flag) {
        WorldGenEndCityPieces.Piece worldgenendcitypieces$piece1 = new WorldGenEndCityPieces.Piece(definedstructuremanager, s, worldgenendcitypieces$piece.c, enumblockrotation, flag);
        BlockPosition blockposition1 = worldgenendcitypieces$piece.a.a(worldgenendcitypieces$piece.b, blockposition, worldgenendcitypieces$piece1.b, BlockPosition.ZERO);
        worldgenendcitypieces$piece1.a(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
        return worldgenendcitypieces$piece1;
    }

    public static void a(DefinedStructureManager definedstructuremanager, BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<StructurePiece> list, Random random) {
        h.a();
        c.a();
        f.a();
        e.a();
        WorldGenEndCityPieces.Piece worldgenendcitypieces$piece = b(list, new WorldGenEndCityPieces.Piece(definedstructuremanager, "base_floor", blockposition, enumblockrotation, true));
        worldgenendcitypieces$piece = b(list, b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(-1, 0, -1), "second_floor_1", enumblockrotation, false));
        worldgenendcitypieces$piece = b(list, b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(-1, 4, -1), "third_floor_1", enumblockrotation, false));
        worldgenendcitypieces$piece = b(list, b(definedstructuremanager, worldgenendcitypieces$piece, new BlockPosition(-1, 8, -1), "third_roof", enumblockrotation, true));
        b(definedstructuremanager, e, 1, worldgenendcitypieces$piece, (BlockPosition)null, list, random);
    }

    private static WorldGenEndCityPieces.Piece b(List<StructurePiece> list, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece) {
        list.add(worldgenendcitypieces$piece);
        return worldgenendcitypieces$piece;
    }

    private static boolean b(DefinedStructureManager definedstructuremanager, WorldGenEndCityPieces.PieceGenerator worldgenendcitypieces$piecegenerator, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces$piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
        if (i > 8) {
            return false;
        } else {
            ArrayList arraylist = Lists.newArrayList();
            if (worldgenendcitypieces$piecegenerator.a(definedstructuremanager, i, worldgenendcitypieces$piece, blockposition, arraylist, random)) {
                boolean flag = false;
                int j = random.nextInt();

                for(StructurePiece structurepiece : arraylist) {
                    structurepiece.o = j;
                    StructurePiece structurepiece1 = StructurePiece.a(list, structurepiece.d());
                    if (structurepiece1 != null && structurepiece1.o != worldgenendcitypieces$piece.o) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    list.addAll(arraylist);
                    return true;
                }
            }

            return false;
        }
    }

    public static class Piece extends DefinedStructurePiece {
        private String d;
        private EnumBlockRotation e;
        private boolean f;

        public Piece() {
        }

        public Piece(DefinedStructureManager definedstructuremanager, String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation, boolean flag) {
            super(0);
            this.d = s;
            this.c = blockposition;
            this.e = enumblockrotation;
            this.f = flag;
            this.a(definedstructuremanager);
        }

        private void a(DefinedStructureManager definedstructuremanager) {
            DefinedStructure definedstructure = definedstructuremanager.a(new MinecraftKey("end_city/" + this.d));
            DefinedStructureInfo definedstructureinfo = (this.f ? WorldGenEndCityPieces.a : WorldGenEndCityPieces.b).a().a(this.e);
            this.a(definedstructure, this.c, definedstructureinfo);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setString("Template", this.d);
            nbttagcompound.setString("Rot", this.e.name());
            nbttagcompound.setBoolean("OW", this.f);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.d = nbttagcompound.getString("Template");
            this.e = EnumBlockRotation.valueOf(nbttagcompound.getString("Rot"));
            this.f = nbttagcompound.getBoolean("OW");
            this.a(definedstructuremanager);
        }

        protected void a(String s, BlockPosition blockposition, GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox) {
            if (s.startsWith("Chest")) {
                BlockPosition blockposition1 = blockposition.down();
                if (structureboundingbox.b(blockposition1)) {
                    TileEntityLootable.a(generatoraccess, random, blockposition1, LootTables.c);
                }
            } else if (s.startsWith("Sentry")) {
                EntityShulker entityshulker = new EntityShulker(generatoraccess.getMinecraftWorld());
                entityshulker.setPosition((double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D);
                entityshulker.g(blockposition);
                generatoraccess.addEntity(entityshulker);
            } else if (s.startsWith("Elytra")) {
                EntityItemFrame entityitemframe = new EntityItemFrame(generatoraccess.getMinecraftWorld(), blockposition, this.e.a(EnumDirection.SOUTH));
                entityitemframe.setItem(new ItemStack(Items.ELYTRA));
                generatoraccess.addEntity(entityitemframe);
            }

        }
    }

    interface PieceGenerator {
        void a();

        boolean a(DefinedStructureManager var1, int var2, WorldGenEndCityPieces.Piece var3, BlockPosition var4, List<StructurePiece> var5, Random var6);
    }
}
