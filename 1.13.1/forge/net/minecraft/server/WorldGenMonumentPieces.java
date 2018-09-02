package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldGenMonumentPieces {
    public static void a() {
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece1.class, "OMB");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece2.class, "OMCR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece3.class, "OMDXR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece4.class, "OMDXYR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece5.class, "OMDYR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece6.class, "OMDYZR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece7.class, "OMDZR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceEntry.class, "OMEntry");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiecePenthouse.class, "OMPenthouse");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceSimple.class, "OMSimple");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceSimpleT.class, "OMSimpleT");
    }

    interface IWorldGenMonumentPieceSelector {
        boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker var1);

        WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection var1, WorldGenMonumentPieces.WorldGenMonumentStateTracker var2, Random var3);
    }

    public abstract static class WorldGenMonumentPiece extends StructurePiece {
        protected static final IBlockData a = Blocks.PRISMARINE.getBlockData();
        protected static final IBlockData b = Blocks.PRISMARINE_BRICKS.getBlockData();
        protected static final IBlockData c = Blocks.DARK_PRISMARINE.getBlockData();
        protected static final IBlockData d = b;
        protected static final IBlockData e = Blocks.SEA_LANTERN.getBlockData();
        protected static final IBlockData f = Blocks.WATER.getBlockData();
        protected static final Set<Block> g = ImmutableSet.builder().add(Blocks.ICE).add(Blocks.PACKED_ICE).add(Blocks.BLUE_ICE).add(f.getBlock()).build();
        protected static final int h = b(2, 0, 0);
        protected static final int i = b(2, 2, 0);
        protected static final int j = b(0, 1, 0);
        protected static final int k = b(4, 1, 0);
        protected WorldGenMonumentPieces.WorldGenMonumentStateTracker l;

        protected static final int b(int ix, int jx, int kx) {
            return jx * 25 + kx * 5 + ix;
        }

        public WorldGenMonumentPiece() {
            super(0);
        }

        public WorldGenMonumentPiece(int ix) {
            super(ix);
        }

        public WorldGenMonumentPiece(EnumDirection enumdirection, StructureBoundingBox structureboundingbox) {
            super(1);
            this.a(enumdirection);
            this.n = structureboundingbox;
        }

        protected WorldGenMonumentPiece(int ix, EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, int jx, int kx, int lx) {
            super(ix);
            this.a(enumdirection);
            this.l = worldgenmonumentpieces$worldgenmonumentstatetracker;
            int i1 = worldgenmonumentpieces$worldgenmonumentstatetracker.a;
            int j1 = i1 % 5;
            int k1 = i1 / 5 % 5;
            int l1 = i1 / 25;
            if (enumdirection != EnumDirection.NORTH && enumdirection != EnumDirection.SOUTH) {
                this.n = new StructureBoundingBox(0, 0, 0, lx * 8 - 1, kx * 4 - 1, jx * 8 - 1);
            } else {
                this.n = new StructureBoundingBox(0, 0, 0, jx * 8 - 1, kx * 4 - 1, lx * 8 - 1);
            }

            switch(enumdirection) {
            case NORTH:
                this.n.a(j1 * 8, l1 * 4, -(k1 + lx) * 8 + 1);
                break;
            case SOUTH:
                this.n.a(j1 * 8, l1 * 4, k1 * 8);
                break;
            case WEST:
                this.n.a(-(k1 + lx) * 8 + 1, l1 * 4, j1 * 8);
                break;
            default:
                this.n.a(k1 * 8, l1 * 4, j1 * 8);
            }

        }

        protected void a(NBTTagCompound var1) {
        }

        protected void a(NBTTagCompound var1, DefinedStructureManager var2) {
        }

        protected void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int ix, int jx, int kx, int lx, int i1, int j1) {
            for(int k1 = jx; k1 <= i1; ++k1) {
                for(int l1 = ix; l1 <= lx; ++l1) {
                    for(int i2 = kx; i2 <= j1; ++i2) {
                        IBlockData iblockdata = this.a((IBlockAccess)generatoraccess, l1, k1, i2, structureboundingbox);
                        if (!g.contains(iblockdata.getBlock())) {
                            if (this.d(k1) >= generatoraccess.getSeaLevel() && iblockdata != f) {
                                this.a(generatoraccess, Blocks.AIR.getBlockData(), l1, k1, i2, structureboundingbox);
                            } else {
                                this.a(generatoraccess, f, l1, k1, i2, structureboundingbox);
                            }
                        }
                    }
                }
            }

        }

        protected void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int ix, int jx, boolean flag) {
            if (flag) {
                this.a(generatoraccess, structureboundingbox, ix + 0, 0, jx + 0, ix + 2, 0, jx + 8 - 1, a, a, false);
                this.a(generatoraccess, structureboundingbox, ix + 5, 0, jx + 0, ix + 8 - 1, 0, jx + 8 - 1, a, a, false);
                this.a(generatoraccess, structureboundingbox, ix + 3, 0, jx + 0, ix + 4, 0, jx + 2, a, a, false);
                this.a(generatoraccess, structureboundingbox, ix + 3, 0, jx + 5, ix + 4, 0, jx + 8 - 1, a, a, false);
                this.a(generatoraccess, structureboundingbox, ix + 3, 0, jx + 2, ix + 4, 0, jx + 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, ix + 3, 0, jx + 5, ix + 4, 0, jx + 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, ix + 2, 0, jx + 3, ix + 2, 0, jx + 4, b, b, false);
                this.a(generatoraccess, structureboundingbox, ix + 5, 0, jx + 3, ix + 5, 0, jx + 4, b, b, false);
            } else {
                this.a(generatoraccess, structureboundingbox, ix + 0, 0, jx + 0, ix + 8 - 1, 0, jx + 8 - 1, a, a, false);
            }

        }

        protected void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int ix, int jx, int kx, int lx, int i1, int j1, IBlockData iblockdata) {
            for(int k1 = jx; k1 <= i1; ++k1) {
                for(int l1 = ix; l1 <= lx; ++l1) {
                    for(int i2 = kx; i2 <= j1; ++i2) {
                        if (this.a((IBlockAccess)generatoraccess, l1, k1, i2, structureboundingbox) == f) {
                            this.a(generatoraccess, iblockdata, l1, k1, i2, structureboundingbox);
                        }
                    }
                }
            }

        }

        protected boolean a(StructureBoundingBox structureboundingbox, int ix, int jx, int kx, int lx) {
            int i1 = this.a(ix, jx);
            int j1 = this.b(ix, jx);
            int k1 = this.a(kx, lx);
            int l1 = this.b(kx, lx);
            return structureboundingbox.a(Math.min(i1, k1), Math.min(j1, l1), Math.max(i1, k1), Math.max(j1, l1));
        }

        protected boolean a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int ix, int jx, int kx) {
            int lx = this.a(ix, kx);
            int i1 = this.d(jx);
            int j1 = this.b(ix, kx);
            if (structureboundingbox.b(new BlockPosition(lx, i1, j1))) {
                EntityGuardianElder entityguardianelder = new EntityGuardianElder(generatoraccess.getMinecraftWorld());
                entityguardianelder.heal(entityguardianelder.getMaxHealth());
                entityguardianelder.setPositionRotation((double)lx + 0.5D, (double)i1, (double)j1 + 0.5D, 0.0F, 0.0F);
                entityguardianelder.prepare(generatoraccess.getDamageScaler(new BlockPosition(entityguardianelder)), (GroupDataEntity)null, (NBTTagCompound)null);
                generatoraccess.addEntity(entityguardianelder);
                return true;
            } else {
                return false;
            }
        }
    }

    public static class WorldGenMonumentPiece1 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        private WorldGenMonumentPieces.WorldGenMonumentStateTracker p;
        private WorldGenMonumentPieces.WorldGenMonumentStateTracker q;
        private final List<WorldGenMonumentPieces.WorldGenMonumentPiece> r = Lists.newArrayList();

        public WorldGenMonumentPiece1() {
        }

        public WorldGenMonumentPiece1(Random random, int i, int j, EnumDirection enumdirection) {
            super(0);
            this.a(enumdirection);
            EnumDirection enumdirection1 = this.f();
            if (enumdirection1.k() == EnumDirection.EnumAxis.Z) {
                this.n = new StructureBoundingBox(i, 39, j, i + 58 - 1, 61, j + 58 - 1);
            } else {
                this.n = new StructureBoundingBox(i, 39, j, i + 58 - 1, 61, j + 58 - 1);
            }

            List list = this.a(random);
            this.p.d = true;
            this.r.add(new WorldGenMonumentPieces.WorldGenMonumentPieceEntry(enumdirection1, this.p));
            this.r.add(new WorldGenMonumentPieces.WorldGenMonumentPiece2(enumdirection1, this.q, random));
            ArrayList arraylist = Lists.newArrayList();
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector6());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector4());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector3());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector7());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector5());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector1());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector2());

            label47:
            for(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker : list) {
                if (!worldgenmonumentpieces$worldgenmonumentstatetracker.d && !worldgenmonumentpieces$worldgenmonumentstatetracker.b()) {
                    Iterator iterator = arraylist.iterator();

                    WorldGenMonumentPieces.IWorldGenMonumentPieceSelector worldgenmonumentpieces$iworldgenmonumentpieceselector;
                    while(true) {
                        if (!iterator.hasNext()) {
                            continue label47;
                        }

                        worldgenmonumentpieces$iworldgenmonumentpieceselector = (WorldGenMonumentPieces.IWorldGenMonumentPieceSelector)iterator.next();
                        if (worldgenmonumentpieces$iworldgenmonumentpieceselector.a(worldgenmonumentpieces$worldgenmonumentstatetracker)) {
                            break;
                        }
                    }

                    this.r.add(worldgenmonumentpieces$iworldgenmonumentpieceselector.a(enumdirection1, worldgenmonumentpieces$worldgenmonumentstatetracker, random));
                }
            }

            int l = this.n.b;
            int i1 = this.a(9, 22);
            int j1 = this.b(9, 22);

            for(WorldGenMonumentPieces.WorldGenMonumentPiece worldgenmonumentpieces$worldgenmonumentpiece : this.r) {
                worldgenmonumentpieces$worldgenmonumentpiece.d().a(i1, l, j1);
            }

            StructureBoundingBox structureboundingbox1 = StructureBoundingBox.a(this.a(1, 1), this.d(1), this.b(1, 1), this.a(23, 21), this.d(8), this.b(23, 21));
            StructureBoundingBox structureboundingbox2 = StructureBoundingBox.a(this.a(34, 1), this.d(1), this.b(34, 1), this.a(56, 21), this.d(8), this.b(56, 21));
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(this.a(22, 22), this.d(13), this.b(22, 22), this.a(35, 35), this.d(17), this.b(35, 35));
            int k = random.nextInt();
            this.r.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(enumdirection1, structureboundingbox1, k++));
            this.r.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(enumdirection1, structureboundingbox2, k++));
            this.r.add(new WorldGenMonumentPieces.WorldGenMonumentPiecePenthouse(enumdirection1, structureboundingbox));
        }

        private List<WorldGenMonumentPieces.WorldGenMonumentStateTracker> a(Random random) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker[] aworldgenmonumentpieces$worldgenmonumentstatetracker = new WorldGenMonumentPieces.WorldGenMonumentStateTracker[75];

            for(int i = 0; i < 5; ++i) {
                for(int j = 0; j < 4; ++j) {
                    boolean flag = false;
                    int k = b(i, 0, j);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[k] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(k);
                }
            }

            for(int l1 = 0; l1 < 5; ++l1) {
                for(int k2 = 0; k2 < 4; ++k2) {
                    boolean flag1 = true;
                    int k3 = b(l1, 1, k2);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[k3] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(k3);
                }
            }

            for(int i2 = 1; i2 < 4; ++i2) {
                for(int l2 = 0; l2 < 2; ++l2) {
                    boolean flag2 = true;
                    int l3 = b(i2, 2, l2);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[l3] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(l3);
                }
            }

            this.p = aworldgenmonumentpieces$worldgenmonumentstatetracker[h];

            for(int j2 = 0; j2 < 5; ++j2) {
                for(int i3 = 0; i3 < 5; ++i3) {
                    for(int j3 = 0; j3 < 3; ++j3) {
                        int i4 = b(j2, j3, i3);
                        if (aworldgenmonumentpieces$worldgenmonumentstatetracker[i4] != null) {
                            for(EnumDirection enumdirection : EnumDirection.values()) {
                                int l = j2 + enumdirection.getAdjacentX();
                                int i1 = j3 + enumdirection.getAdjacentY();
                                int j1 = i3 + enumdirection.getAdjacentZ();
                                if (l >= 0 && l < 5 && j1 >= 0 && j1 < 5 && i1 >= 0 && i1 < 3) {
                                    int k1 = b(l, i1, j1);
                                    if (aworldgenmonumentpieces$worldgenmonumentstatetracker[k1] != null) {
                                        if (j1 == i3) {
                                            aworldgenmonumentpieces$worldgenmonumentstatetracker[i4].a(enumdirection, aworldgenmonumentpieces$worldgenmonumentstatetracker[k1]);
                                        } else {
                                            aworldgenmonumentpieces$worldgenmonumentstatetracker[i4].a(enumdirection.opposite(), aworldgenmonumentpieces$worldgenmonumentstatetracker[k1]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1003);
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1001);
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1002);
            aworldgenmonumentpieces$worldgenmonumentstatetracker[i].a(EnumDirection.UP, worldgenmonumentpieces$worldgenmonumentstatetracker);
            aworldgenmonumentpieces$worldgenmonumentstatetracker[j].a(EnumDirection.SOUTH, worldgenmonumentpieces$worldgenmonumentstatetracker1);
            aworldgenmonumentpieces$worldgenmonumentstatetracker[k].a(EnumDirection.SOUTH, worldgenmonumentpieces$worldgenmonumentstatetracker2);
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker1.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker2.d = true;
            this.p.e = true;
            this.q = aworldgenmonumentpieces$worldgenmonumentstatetracker[b(random.nextInt(4), 0, 2)];
            this.q.d = true;
            this.q.b[EnumDirection.EAST.a()].d = true;
            this.q.b[EnumDirection.NORTH.a()].d = true;
            this.q.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].d = true;
            this.q.b[EnumDirection.UP.a()].d = true;
            this.q.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
            this.q.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            this.q.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            ArrayList arraylist = Lists.newArrayList();

            for(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker4 : aworldgenmonumentpieces$worldgenmonumentstatetracker) {
                if (worldgenmonumentpieces$worldgenmonumentstatetracker4 != null) {
                    worldgenmonumentpieces$worldgenmonumentstatetracker4.a();
                    arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker4);
                }
            }

            worldgenmonumentpieces$worldgenmonumentstatetracker.a();
            Collections.shuffle(arraylist, random);
            int j4 = 1;

            for(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 : arraylist) {
                int k4 = 0;
                int l4 = 0;

                while(k4 < 2 && l4 < 5) {
                    ++l4;
                    int i5 = random.nextInt(6);
                    if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5]) {
                        int j5 = EnumDirection.fromType1(i5).opposite().a();
                        worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5] = false;
                        worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].c[j5] = false;
                        if (worldgenmonumentpieces$worldgenmonumentstatetracker3.a(j4++) && worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].a(j4++)) {
                            ++k4;
                        } else {
                            worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5] = true;
                            worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].c[j5] = true;
                        }
                    }
                }
            }

            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker);
            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker1);
            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker2);
            return arraylist;
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair chunkcoordintpair) {
            int i = Math.max(generatoraccess.getSeaLevel(), 64) - this.n.b;
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 58, i, 58);
            this.a(false, 0, generatoraccess, random, structureboundingbox);
            this.a(true, 33, generatoraccess, random, structureboundingbox);
            this.a(generatoraccess, random, structureboundingbox);
            this.b(generatoraccess, random, structureboundingbox);
            this.c(generatoraccess, random, structureboundingbox);
            this.d(generatoraccess, random, structureboundingbox);
            this.e(generatoraccess, random, structureboundingbox);
            this.f(generatoraccess, random, structureboundingbox);

            for(int j = 0; j < 7; ++j) {
                int k = 0;

                while(k < 7) {
                    if (k == 0 && j == 3) {
                        k = 6;
                    }

                    int l = j * 9;
                    int i1 = k * 9;

                    for(int j1 = 0; j1 < 4; ++j1) {
                        for(int k1 = 0; k1 < 4; ++k1) {
                            this.a(generatoraccess, b, l + j1, 0, i1 + k1, structureboundingbox);
                            this.b(generatoraccess, b, l + j1, -1, i1 + k1, structureboundingbox);
                        }
                    }

                    if (j != 0 && j != 6) {
                        k += 6;
                    } else {
                        ++k;
                    }
                }
            }

            for(int l1 = 0; l1 < 5; ++l1) {
                this.a(generatoraccess, structureboundingbox, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1);
                this.a(generatoraccess, structureboundingbox, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1);
                this.a(generatoraccess, structureboundingbox, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1);
                this.a(generatoraccess, structureboundingbox, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1);
            }

            for(WorldGenMonumentPieces.WorldGenMonumentPiece worldgenmonumentpieces$worldgenmonumentpiece : this.r) {
                if (worldgenmonumentpieces$worldgenmonumentpiece.d().a(structureboundingbox)) {
                    worldgenmonumentpieces$worldgenmonumentpiece.a(generatoraccess, random, structureboundingbox, chunkcoordintpair);
                }
            }

            return true;
        }

        private void a(boolean flag, int i, GeneratorAccess generatoraccess, Random var4, StructureBoundingBox structureboundingbox) {
            boolean flag1 = true;
            if (this.a(structureboundingbox, i, 0, i + 23, 20)) {
                this.a(generatoraccess, structureboundingbox, i + 0, 0, 0, i + 24, 0, 20, a, a, false);
                this.a(generatoraccess, structureboundingbox, i + 0, 1, 0, i + 24, 10, 20);

                for(int j = 0; j < 4; ++j) {
                    this.a(generatoraccess, structureboundingbox, i + j, j + 1, j, i + j, j + 1, 20, b, b, false);
                    this.a(generatoraccess, structureboundingbox, i + j + 7, j + 5, j + 7, i + j + 7, j + 5, 20, b, b, false);
                    this.a(generatoraccess, structureboundingbox, i + 17 - j, j + 5, j + 7, i + 17 - j, j + 5, 20, b, b, false);
                    this.a(generatoraccess, structureboundingbox, i + 24 - j, j + 1, j, i + 24 - j, j + 1, 20, b, b, false);
                    this.a(generatoraccess, structureboundingbox, i + j + 1, j + 1, j, i + 23 - j, j + 1, j, b, b, false);
                    this.a(generatoraccess, structureboundingbox, i + j + 8, j + 5, j + 7, i + 16 - j, j + 5, j + 7, b, b, false);
                }

                this.a(generatoraccess, structureboundingbox, i + 4, 4, 4, i + 6, 4, 20, a, a, false);
                this.a(generatoraccess, structureboundingbox, i + 7, 4, 4, i + 17, 4, 6, a, a, false);
                this.a(generatoraccess, structureboundingbox, i + 18, 4, 4, i + 20, 4, 20, a, a, false);
                this.a(generatoraccess, structureboundingbox, i + 11, 8, 11, i + 13, 8, 20, a, a, false);
                this.a(generatoraccess, d, i + 12, 9, 12, structureboundingbox);
                this.a(generatoraccess, d, i + 12, 9, 15, structureboundingbox);
                this.a(generatoraccess, d, i + 12, 9, 18, structureboundingbox);
                int j1 = i + (flag ? 19 : 5);
                int k = i + (flag ? 5 : 19);

                for(int l = 20; l >= 5; l -= 3) {
                    this.a(generatoraccess, d, j1, 5, l, structureboundingbox);
                }

                for(int k1 = 19; k1 >= 7; k1 -= 3) {
                    this.a(generatoraccess, d, k, 5, k1, structureboundingbox);
                }

                for(int l1 = 0; l1 < 4; ++l1) {
                    int i1 = flag ? i + 24 - (17 - l1 * 3) : i + 17 - l1 * 3;
                    this.a(generatoraccess, d, i1, 5, 5, structureboundingbox);
                }

                this.a(generatoraccess, d, k, 5, 5, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, i + 11, 1, 12, i + 13, 7, 12, a, a, false);
                this.a(generatoraccess, structureboundingbox, i + 12, 1, 11, i + 12, 7, 13, a, a, false);
            }

        }

        private void a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 22, 5, 35, 17)) {
                this.a(generatoraccess, structureboundingbox, 25, 0, 0, 32, 8, 20);

                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, b, b, false);
                    this.a(generatoraccess, b, 25, 5, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, b, 26, 6, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, e, 26, 5, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, structureboundingbox, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, b, b, false);
                    this.a(generatoraccess, b, 32, 5, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, b, 31, 6, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, e, 31, 5, 5 + i * 4, structureboundingbox);
                    this.a(generatoraccess, structureboundingbox, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, a, a, false);
                }
            }

        }

        private void b(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 15, 20, 42, 21)) {
                this.a(generatoraccess, structureboundingbox, 15, 0, 21, 42, 0, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 26, 1, 21, 31, 3, 21);
                this.a(generatoraccess, structureboundingbox, 21, 12, 21, 36, 12, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 17, 11, 21, 40, 11, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 16, 10, 21, 41, 10, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 15, 7, 21, 42, 9, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 16, 6, 21, 41, 6, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 17, 5, 21, 40, 5, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 21, 4, 21, 36, 4, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 22, 3, 21, 26, 3, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 31, 3, 21, 35, 3, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 23, 2, 21, 25, 2, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 32, 2, 21, 34, 2, 21, a, a, false);
                this.a(generatoraccess, structureboundingbox, 28, 4, 20, 29, 4, 21, b, b, false);
                this.a(generatoraccess, b, 27, 3, 21, structureboundingbox);
                this.a(generatoraccess, b, 30, 3, 21, structureboundingbox);
                this.a(generatoraccess, b, 26, 2, 21, structureboundingbox);
                this.a(generatoraccess, b, 31, 2, 21, structureboundingbox);
                this.a(generatoraccess, b, 25, 1, 21, structureboundingbox);
                this.a(generatoraccess, b, 32, 1, 21, structureboundingbox);

                for(int i = 0; i < 7; ++i) {
                    this.a(generatoraccess, c, 28 - i, 6 + i, 21, structureboundingbox);
                    this.a(generatoraccess, c, 29 + i, 6 + i, 21, structureboundingbox);
                }

                for(int j = 0; j < 4; ++j) {
                    this.a(generatoraccess, c, 28 - j, 9 + j, 21, structureboundingbox);
                    this.a(generatoraccess, c, 29 + j, 9 + j, 21, structureboundingbox);
                }

                this.a(generatoraccess, c, 28, 12, 21, structureboundingbox);
                this.a(generatoraccess, c, 29, 12, 21, structureboundingbox);

                for(int k = 0; k < 3; ++k) {
                    this.a(generatoraccess, c, 22 - k * 2, 8, 21, structureboundingbox);
                    this.a(generatoraccess, c, 22 - k * 2, 9, 21, structureboundingbox);
                    this.a(generatoraccess, c, 35 + k * 2, 8, 21, structureboundingbox);
                    this.a(generatoraccess, c, 35 + k * 2, 9, 21, structureboundingbox);
                }

                this.a(generatoraccess, structureboundingbox, 15, 13, 21, 42, 15, 21);
                this.a(generatoraccess, structureboundingbox, 15, 1, 21, 15, 6, 21);
                this.a(generatoraccess, structureboundingbox, 16, 1, 21, 16, 5, 21);
                this.a(generatoraccess, structureboundingbox, 17, 1, 21, 20, 4, 21);
                this.a(generatoraccess, structureboundingbox, 21, 1, 21, 21, 3, 21);
                this.a(generatoraccess, structureboundingbox, 22, 1, 21, 22, 2, 21);
                this.a(generatoraccess, structureboundingbox, 23, 1, 21, 24, 1, 21);
                this.a(generatoraccess, structureboundingbox, 42, 1, 21, 42, 6, 21);
                this.a(generatoraccess, structureboundingbox, 41, 1, 21, 41, 5, 21);
                this.a(generatoraccess, structureboundingbox, 37, 1, 21, 40, 4, 21);
                this.a(generatoraccess, structureboundingbox, 36, 1, 21, 36, 3, 21);
                this.a(generatoraccess, structureboundingbox, 33, 1, 21, 34, 1, 21);
                this.a(generatoraccess, structureboundingbox, 35, 1, 21, 35, 2, 21);
            }

        }

        private void c(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 21, 21, 36, 36)) {
                this.a(generatoraccess, structureboundingbox, 21, 0, 22, 36, 0, 36, a, a, false);
                this.a(generatoraccess, structureboundingbox, 21, 1, 22, 36, 23, 36);

                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, b, b, false);
                }

                this.a(generatoraccess, structureboundingbox, 25, 16, 25, 32, 16, 32, a, a, false);
                this.a(generatoraccess, structureboundingbox, 25, 17, 25, 25, 19, 25, b, b, false);
                this.a(generatoraccess, structureboundingbox, 32, 17, 25, 32, 19, 25, b, b, false);
                this.a(generatoraccess, structureboundingbox, 25, 17, 32, 25, 19, 32, b, b, false);
                this.a(generatoraccess, structureboundingbox, 32, 17, 32, 32, 19, 32, b, b, false);
                this.a(generatoraccess, b, 26, 20, 26, structureboundingbox);
                this.a(generatoraccess, b, 27, 21, 27, structureboundingbox);
                this.a(generatoraccess, e, 27, 20, 27, structureboundingbox);
                this.a(generatoraccess, b, 26, 20, 31, structureboundingbox);
                this.a(generatoraccess, b, 27, 21, 30, structureboundingbox);
                this.a(generatoraccess, e, 27, 20, 30, structureboundingbox);
                this.a(generatoraccess, b, 31, 20, 31, structureboundingbox);
                this.a(generatoraccess, b, 30, 21, 30, structureboundingbox);
                this.a(generatoraccess, e, 30, 20, 30, structureboundingbox);
                this.a(generatoraccess, b, 31, 20, 26, structureboundingbox);
                this.a(generatoraccess, b, 30, 21, 27, structureboundingbox);
                this.a(generatoraccess, e, 30, 20, 27, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 28, 21, 27, 29, 21, 27, a, a, false);
                this.a(generatoraccess, structureboundingbox, 27, 21, 28, 27, 21, 29, a, a, false);
                this.a(generatoraccess, structureboundingbox, 28, 21, 30, 29, 21, 30, a, a, false);
                this.a(generatoraccess, structureboundingbox, 30, 21, 28, 30, 21, 29, a, a, false);
            }

        }

        private void d(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 0, 21, 6, 58)) {
                this.a(generatoraccess, structureboundingbox, 0, 0, 21, 6, 0, 57, a, a, false);
                this.a(generatoraccess, structureboundingbox, 0, 1, 21, 6, 7, 57);
                this.a(generatoraccess, structureboundingbox, 4, 4, 21, 6, 4, 53, a, a, false);

                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, i, i + 1, 21, i, i + 1, 57 - i, b, b, false);
                }

                for(int j = 23; j < 53; j += 3) {
                    this.a(generatoraccess, d, 5, 5, j, structureboundingbox);
                }

                this.a(generatoraccess, d, 5, 5, 52, structureboundingbox);

                for(int k = 0; k < 4; ++k) {
                    this.a(generatoraccess, structureboundingbox, k, k + 1, 21, k, k + 1, 57 - k, b, b, false);
                }

                this.a(generatoraccess, structureboundingbox, 4, 1, 52, 6, 3, 52, a, a, false);
                this.a(generatoraccess, structureboundingbox, 5, 1, 51, 5, 3, 53, a, a, false);
            }

            if (this.a(structureboundingbox, 51, 21, 58, 58)) {
                this.a(generatoraccess, structureboundingbox, 51, 0, 21, 57, 0, 57, a, a, false);
                this.a(generatoraccess, structureboundingbox, 51, 1, 21, 57, 7, 57);
                this.a(generatoraccess, structureboundingbox, 51, 4, 21, 53, 4, 53, a, a, false);

                for(int l = 0; l < 4; ++l) {
                    this.a(generatoraccess, structureboundingbox, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, b, b, false);
                }

                for(int i1 = 23; i1 < 53; i1 += 3) {
                    this.a(generatoraccess, d, 52, 5, i1, structureboundingbox);
                }

                this.a(generatoraccess, d, 52, 5, 52, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 51, 1, 52, 53, 3, 52, a, a, false);
                this.a(generatoraccess, structureboundingbox, 52, 1, 51, 52, 3, 53, a, a, false);
            }

            if (this.a(structureboundingbox, 0, 51, 57, 57)) {
                this.a(generatoraccess, structureboundingbox, 7, 0, 51, 50, 0, 57, a, a, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 51, 50, 10, 57);

                for(int j1 = 0; j1 < 4; ++j1) {
                    this.a(generatoraccess, structureboundingbox, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, b, b, false);
                }
            }

        }

        private void e(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 7, 21, 13, 50)) {
                this.a(generatoraccess, structureboundingbox, 7, 0, 21, 13, 0, 50, a, a, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 21, 13, 10, 50);
                this.a(generatoraccess, structureboundingbox, 11, 8, 21, 13, 8, 53, a, a, false);

                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, i + 7, i + 5, 21, i + 7, i + 5, 54, b, b, false);
                }

                for(int j = 21; j <= 45; j += 3) {
                    this.a(generatoraccess, d, 12, 9, j, structureboundingbox);
                }
            }

            if (this.a(structureboundingbox, 44, 21, 50, 54)) {
                this.a(generatoraccess, structureboundingbox, 44, 0, 21, 50, 0, 50, a, a, false);
                this.a(generatoraccess, structureboundingbox, 44, 1, 21, 50, 10, 50);
                this.a(generatoraccess, structureboundingbox, 44, 8, 21, 46, 8, 53, a, a, false);

                for(int k = 0; k < 4; ++k) {
                    this.a(generatoraccess, structureboundingbox, 50 - k, k + 5, 21, 50 - k, k + 5, 54, b, b, false);
                }

                for(int l = 21; l <= 45; l += 3) {
                    this.a(generatoraccess, d, 45, 9, l, structureboundingbox);
                }
            }

            if (this.a(structureboundingbox, 8, 44, 49, 54)) {
                this.a(generatoraccess, structureboundingbox, 14, 0, 44, 43, 0, 50, a, a, false);
                this.a(generatoraccess, structureboundingbox, 14, 1, 44, 43, 10, 50);

                for(int i1 = 12; i1 <= 45; i1 += 3) {
                    this.a(generatoraccess, d, i1, 9, 45, structureboundingbox);
                    this.a(generatoraccess, d, i1, 9, 52, structureboundingbox);
                    if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45) {
                        this.a(generatoraccess, d, i1, 9, 47, structureboundingbox);
                        this.a(generatoraccess, d, i1, 9, 50, structureboundingbox);
                        this.a(generatoraccess, d, i1, 10, 45, structureboundingbox);
                        this.a(generatoraccess, d, i1, 10, 46, structureboundingbox);
                        this.a(generatoraccess, d, i1, 10, 51, structureboundingbox);
                        this.a(generatoraccess, d, i1, 10, 52, structureboundingbox);
                        this.a(generatoraccess, d, i1, 11, 47, structureboundingbox);
                        this.a(generatoraccess, d, i1, 11, 50, structureboundingbox);
                        this.a(generatoraccess, d, i1, 12, 48, structureboundingbox);
                        this.a(generatoraccess, d, i1, 12, 49, structureboundingbox);
                    }
                }

                for(int j1 = 0; j1 < 3; ++j1) {
                    this.a(generatoraccess, structureboundingbox, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, a, a, false);
                }

                this.a(generatoraccess, structureboundingbox, 11, 8, 54, 46, 8, 54, b, b, false);
                this.a(generatoraccess, structureboundingbox, 14, 8, 44, 43, 8, 53, a, a, false);
            }

        }

        private void f(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox) {
            if (this.a(structureboundingbox, 14, 21, 20, 43)) {
                this.a(generatoraccess, structureboundingbox, 14, 0, 21, 20, 0, 43, a, a, false);
                this.a(generatoraccess, structureboundingbox, 14, 1, 22, 20, 14, 43);
                this.a(generatoraccess, structureboundingbox, 18, 12, 22, 20, 12, 39, a, a, false);
                this.a(generatoraccess, structureboundingbox, 18, 12, 21, 20, 12, 21, b, b, false);

                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, b, b, false);
                }

                for(int j = 23; j <= 39; j += 3) {
                    this.a(generatoraccess, d, 19, 13, j, structureboundingbox);
                }
            }

            if (this.a(structureboundingbox, 37, 21, 43, 43)) {
                this.a(generatoraccess, structureboundingbox, 37, 0, 21, 43, 0, 43, a, a, false);
                this.a(generatoraccess, structureboundingbox, 37, 1, 22, 43, 14, 43);
                this.a(generatoraccess, structureboundingbox, 37, 12, 22, 39, 12, 39, a, a, false);
                this.a(generatoraccess, structureboundingbox, 37, 12, 21, 39, 12, 21, b, b, false);

                for(int k = 0; k < 4; ++k) {
                    this.a(generatoraccess, structureboundingbox, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, b, b, false);
                }

                for(int l = 23; l <= 39; l += 3) {
                    this.a(generatoraccess, d, 38, 13, l, structureboundingbox);
                }
            }

            if (this.a(structureboundingbox, 15, 37, 42, 43)) {
                this.a(generatoraccess, structureboundingbox, 21, 0, 37, 36, 0, 43, a, a, false);
                this.a(generatoraccess, structureboundingbox, 21, 1, 37, 36, 14, 43);
                this.a(generatoraccess, structureboundingbox, 21, 12, 37, 36, 12, 39, a, a, false);

                for(int i1 = 0; i1 < 4; ++i1) {
                    this.a(generatoraccess, structureboundingbox, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, b, b, false);
                }

                for(int j1 = 21; j1 <= 36; j1 += 3) {
                    this.a(generatoraccess, d, j1, 13, 38, structureboundingbox);
                }
            }

        }
    }

    public static class WorldGenMonumentPiece2 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece2() {
        }

        public WorldGenMonumentPiece2(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 2, 2, 2);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            this.a(generatoraccess, structureboundingbox, 1, 8, 0, 14, 8, 14, a);
            boolean flag = true;
            IBlockData iblockdata = b;
            this.a(generatoraccess, structureboundingbox, 0, 7, 0, 0, 7, 15, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 15, 7, 0, 15, 7, 15, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 7, 0, 15, 7, 0, iblockdata, iblockdata, false);
            this.a(generatoraccess, structureboundingbox, 1, 7, 15, 14, 7, 15, iblockdata, iblockdata, false);

            for(int j = 1; j <= 6; ++j) {
                iblockdata = b;
                if (j == 2 || j == 6) {
                    iblockdata = a;
                }

                for(int i = 0; i <= 15; i += 15) {
                    this.a(generatoraccess, structureboundingbox, i, j, 0, i, j, 1, iblockdata, iblockdata, false);
                    this.a(generatoraccess, structureboundingbox, i, j, 6, i, j, 9, iblockdata, iblockdata, false);
                    this.a(generatoraccess, structureboundingbox, i, j, 14, i, j, 15, iblockdata, iblockdata, false);
                }

                this.a(generatoraccess, structureboundingbox, 1, j, 0, 1, j, 0, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 6, j, 0, 9, j, 0, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 14, j, 0, 14, j, 0, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 1, j, 15, 14, j, 15, iblockdata, iblockdata, false);
            }

            this.a(generatoraccess, structureboundingbox, 6, 3, 6, 9, 6, 9, c, c, false);
            this.a(generatoraccess, structureboundingbox, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getBlockData(), Blocks.GOLD_BLOCK.getBlockData(), false);

            for(int k = 3; k <= 6; k += 3) {
                for(int l = 6; l <= 9; l += 3) {
                    this.a(generatoraccess, e, l, k, 6, structureboundingbox);
                    this.a(generatoraccess, e, l, k, 9, structureboundingbox);
                }
            }

            this.a(generatoraccess, structureboundingbox, 5, 1, 6, 5, 2, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 9, 5, 2, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 1, 6, 10, 2, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 1, 9, 10, 2, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 1, 5, 6, 2, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 1, 5, 9, 2, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 1, 10, 6, 2, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 1, 10, 9, 2, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 2, 5, 5, 6, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 2, 10, 5, 6, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 2, 5, 10, 6, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 2, 10, 10, 6, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 7, 1, 5, 7, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 7, 1, 10, 7, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 7, 9, 5, 7, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 7, 9, 10, 7, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 7, 5, 6, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 7, 10, 6, 7, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 7, 5, 14, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 7, 10, 14, 7, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 2, 2, 1, 3, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 2, 3, 1, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 13, 1, 2, 13, 1, 3, b, b, false);
            this.a(generatoraccess, structureboundingbox, 12, 1, 2, 12, 1, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 12, 2, 1, 13, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 13, 3, 1, 13, b, b, false);
            this.a(generatoraccess, structureboundingbox, 13, 1, 12, 13, 1, 13, b, b, false);
            this.a(generatoraccess, structureboundingbox, 12, 1, 13, 12, 1, 13, b, b, false);
            return true;
        }
    }

    public static class WorldGenMonumentPiece3 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece3() {
        }

        public WorldGenMonumentPiece3(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 2, 1, 1);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.l.b[EnumDirection.EAST.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.l;
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 8, 0, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(generatoraccess, structureboundingbox, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 1, 7, 4, 6, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 8, 4, 1, 14, 4, 6, a);
            }

            this.a(generatoraccess, structureboundingbox, 0, 3, 0, 0, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 15, 3, 0, 15, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 0, 15, 3, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 7, 14, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 2, 7, a, a, false);
            this.a(generatoraccess, structureboundingbox, 15, 2, 0, 15, 2, 7, a, a, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 15, 2, 0, a, a, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 7, 14, 2, 7, a, a, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 15, 1, 0, 15, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 15, 1, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 7, 14, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 0, 10, 1, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 2, 0, 9, 2, 3, a, a, false);
            this.a(generatoraccess, structureboundingbox, 5, 3, 0, 10, 3, 4, b, b, false);
            this.a(generatoraccess, e, 6, 2, 3, structureboundingbox);
            this.a(generatoraccess, e, 9, 2, 3, structureboundingbox);
            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 1, 0, 12, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 1, 7, 12, 2, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 15, 1, 3, 15, 2, 4);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece4 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece4() {
        }

        public WorldGenMonumentPiece4(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 2, 2, 1);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.l.b[EnumDirection.EAST.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.l;
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2 = worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()];
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 8, 0, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(generatoraccess, structureboundingbox, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 8, 1, 7, 8, 6, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 8, 8, 1, 14, 8, 6, a);
            }

            for(int i = 1; i <= 7; ++i) {
                IBlockData iblockdata = b;
                if (i == 2 || i == 6) {
                    iblockdata = a;
                }

                this.a(generatoraccess, structureboundingbox, 0, i, 0, 0, i, 7, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 15, i, 0, 15, i, 7, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 1, i, 0, 15, i, 0, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 1, i, 7, 14, i, 7, iblockdata, iblockdata, false);
            }

            this.a(generatoraccess, structureboundingbox, 2, 1, 3, 2, 7, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 2, 4, 7, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 5, 4, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 13, 1, 3, 13, 7, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 11, 1, 2, 12, 7, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 11, 1, 5, 12, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 3, 5, 3, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 1, 3, 10, 3, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 7, 2, 10, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 5, 2, 5, 7, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 5, 2, 10, 7, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 5, 5, 5, 7, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 10, 5, 5, 10, 7, 5, b, b, false);
            this.a(generatoraccess, b, 6, 6, 2, structureboundingbox);
            this.a(generatoraccess, b, 9, 6, 2, structureboundingbox);
            this.a(generatoraccess, b, 6, 6, 5, structureboundingbox);
            this.a(generatoraccess, b, 9, 6, 5, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 5, 4, 3, 6, 4, 4, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 4, 3, 10, 4, 4, b, b, false);
            this.a(generatoraccess, e, 5, 4, 2, structureboundingbox);
            this.a(generatoraccess, e, 5, 4, 5, structureboundingbox);
            this.a(generatoraccess, e, 10, 4, 2, structureboundingbox);
            this.a(generatoraccess, e, 10, 4, 5, structureboundingbox);
            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 1, 0, 12, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 1, 7, 12, 2, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 15, 1, 3, 15, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 5, 0, 4, 6, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 5, 7, 4, 6, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 5, 3, 0, 6, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 5, 0, 12, 6, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 11, 5, 7, 12, 6, 7);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 15, 5, 3, 15, 6, 4);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece5 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece5() {
        }

        public WorldGenMonumentPiece5(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 2, 1);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 0, 0, this.l.c[EnumDirection.DOWN.a()]);
            }

            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.l.b[EnumDirection.UP.a()];
            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 8, 1, 6, 8, 6, a);
            }

            this.a(generatoraccess, structureboundingbox, 0, 4, 0, 0, 4, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 4, 0, 7, 4, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 0, 6, 4, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 7, 6, 4, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 4, 1, 2, 4, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 2, 1, 4, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 4, 1, 5, 4, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 4, 2, 6, 4, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 4, 5, 2, 4, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 4, 5, 1, 4, 5, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 4, 5, 5, 4, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 4, 5, 6, 4, 5, b, b, false);
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.l;

            for(int i = 1; i <= 5; i += 4) {
                byte b0 = 0;
                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 2, i, b0, 2, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 5, i, b0, 5, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 3, i + 2, b0, 4, i + 2, b0, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 0, i, b0, 7, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 0, i + 1, b0, 7, i + 1, b0, a, a, false);
                }

                b0 = 7;
                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 2, i, b0, 2, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 5, i, b0, 5, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 3, i + 2, b0, 4, i + 2, b0, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 0, i, b0, 7, i + 2, b0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 0, i + 1, b0, 7, i + 1, b0, a, a, false);
                }

                byte b1 = 0;
                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()]) {
                    this.a(generatoraccess, structureboundingbox, b1, i, 2, b1, i + 2, 2, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i, 5, b1, i + 2, 5, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i + 2, 3, b1, i + 2, 4, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, b1, i, 0, b1, i + 2, 7, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i + 1, 0, b1, i + 1, 7, a, a, false);
                }

                b1 = 7;
                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()]) {
                    this.a(generatoraccess, structureboundingbox, b1, i, 2, b1, i + 2, 2, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i, 5, b1, i + 2, 5, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i + 2, 3, b1, i + 2, 4, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, b1, i, 0, b1, i + 2, 7, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b1, i + 1, 0, b1, i + 1, 7, a, a, false);
                }

                worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker;
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece6 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece6() {
        }

        public WorldGenMonumentPiece6(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 2, 2);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.l.b[EnumDirection.NORTH.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.l;
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 = worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()];
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 0, 8, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(generatoraccess, structureboundingbox, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 8, 1, 6, 8, 7, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 8, 8, 6, 8, 14, a);
            }

            for(int i = 1; i <= 7; ++i) {
                IBlockData iblockdata = b;
                if (i == 2 || i == 6) {
                    iblockdata = a;
                }

                this.a(generatoraccess, structureboundingbox, 0, i, 0, 0, i, 15, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 7, i, 0, 7, i, 15, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 1, i, 0, 6, i, 0, iblockdata, iblockdata, false);
                this.a(generatoraccess, structureboundingbox, 1, i, 15, 6, i, 15, iblockdata, iblockdata, false);
            }

            for(int j = 1; j <= 7; ++j) {
                IBlockData iblockdata1 = c;
                if (j == 2 || j == 6) {
                    iblockdata1 = e;
                }

                this.a(generatoraccess, structureboundingbox, 3, j, 7, 4, j, 8, iblockdata1, iblockdata1, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 1, 3, 7, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 15, 4, 2, 15);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 11, 0, 2, 12);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 1, 11, 7, 2, 12);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 5, 0, 4, 6, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 5, 3, 7, 6, 4);
                this.a(generatoraccess, structureboundingbox, 5, 4, 2, 6, 4, 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 2, 6, 3, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 5, 6, 3, 5, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 5, 3, 0, 6, 4);
                this.a(generatoraccess, structureboundingbox, 1, 4, 2, 2, 4, 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 2, 1, 3, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 5, 1, 3, 5, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 5, 15, 4, 6, 15);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 5, 11, 0, 6, 12);
                this.a(generatoraccess, structureboundingbox, 1, 4, 10, 2, 4, 13, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 10, 1, 3, 10, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 13, 1, 3, 13, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 5, 11, 7, 6, 12);
                this.a(generatoraccess, structureboundingbox, 5, 4, 10, 6, 4, 13, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 10, 6, 3, 10, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 13, 6, 3, 13, b, b, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece7 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiece7() {
        }

        public WorldGenMonumentPiece7(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 1, 2);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.l.b[EnumDirection.NORTH.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.l;
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 0, 8, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(generatoraccess, structureboundingbox, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 1, 6, 4, 7, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 8, 6, 4, 14, a);
            }

            this.a(generatoraccess, structureboundingbox, 0, 3, 0, 0, 3, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 3, 0, 7, 3, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 0, 7, 3, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 15, 6, 3, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 2, 15, a, a, false);
            this.a(generatoraccess, structureboundingbox, 7, 2, 0, 7, 2, 15, a, a, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 7, 2, 0, a, a, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 15, 6, 2, 15, a, a, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 1, 0, 7, 1, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 7, 1, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 15, 6, 1, 15, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 1, 1, 1, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 1, 1, 6, 1, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 1, 1, 3, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 3, 1, 6, 3, 2, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 13, 1, 1, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 1, 13, 6, 1, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 13, 1, 3, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 3, 13, 6, 3, 14, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 6, 2, 3, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 6, 5, 3, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 1, 9, 2, 3, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 9, 5, 3, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 2, 6, 4, 2, 6, b, b, false);
            this.a(generatoraccess, structureboundingbox, 3, 2, 9, 4, 2, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 2, 2, 7, 2, 2, 8, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 2, 7, 5, 2, 8, b, b, false);
            this.a(generatoraccess, e, 2, 2, 5, structureboundingbox);
            this.a(generatoraccess, e, 5, 2, 5, structureboundingbox);
            this.a(generatoraccess, e, 2, 2, 10, structureboundingbox);
            this.a(generatoraccess, e, 5, 2, 10, structureboundingbox);
            this.a(generatoraccess, b, 2, 3, 5, structureboundingbox);
            this.a(generatoraccess, b, 5, 3, 5, structureboundingbox);
            this.a(generatoraccess, b, 2, 3, 10, structureboundingbox);
            this.a(generatoraccess, b, 5, 3, 10, structureboundingbox);
            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 1, 3, 7, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 15, 4, 2, 15);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 11, 0, 2, 12);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 7, 1, 11, 7, 2, 12);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece8 extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        private int p;

        public WorldGenMonumentPiece8() {
        }

        public WorldGenMonumentPiece8(EnumDirection enumdirection, StructureBoundingBox structureboundingbox, int i) {
            super(enumdirection, structureboundingbox);
            this.p = i & 1;
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.p == 0) {
                for(int i = 0; i < 4; ++i) {
                    this.a(generatoraccess, structureboundingbox, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, b, b, false);
                }

                this.a(generatoraccess, structureboundingbox, 7, 0, 6, 15, 0, 16, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 0, 6, 6, 3, 20, b, b, false);
                this.a(generatoraccess, structureboundingbox, 16, 0, 6, 16, 3, 20, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 7, 7, 1, 20, b, b, false);
                this.a(generatoraccess, structureboundingbox, 15, 1, 7, 15, 1, 20, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 6, 9, 3, 6, b, b, false);
                this.a(generatoraccess, structureboundingbox, 13, 1, 6, 15, 3, 6, b, b, false);
                this.a(generatoraccess, structureboundingbox, 8, 1, 7, 9, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 13, 1, 7, 14, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 9, 0, 5, 13, 0, 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, 10, 0, 7, 12, 0, 7, c, c, false);
                this.a(generatoraccess, structureboundingbox, 8, 0, 10, 8, 0, 12, c, c, false);
                this.a(generatoraccess, structureboundingbox, 14, 0, 10, 14, 0, 12, c, c, false);

                for(int k = 18; k >= 7; k -= 3) {
                    this.a(generatoraccess, e, 6, 3, k, structureboundingbox);
                    this.a(generatoraccess, e, 16, 3, k, structureboundingbox);
                }

                this.a(generatoraccess, e, 10, 0, 10, structureboundingbox);
                this.a(generatoraccess, e, 12, 0, 10, structureboundingbox);
                this.a(generatoraccess, e, 10, 0, 12, structureboundingbox);
                this.a(generatoraccess, e, 12, 0, 12, structureboundingbox);
                this.a(generatoraccess, e, 8, 3, 6, structureboundingbox);
                this.a(generatoraccess, e, 14, 3, 6, structureboundingbox);
                this.a(generatoraccess, b, 4, 2, 4, structureboundingbox);
                this.a(generatoraccess, e, 4, 1, 4, structureboundingbox);
                this.a(generatoraccess, b, 4, 0, 4, structureboundingbox);
                this.a(generatoraccess, b, 18, 2, 4, structureboundingbox);
                this.a(generatoraccess, e, 18, 1, 4, structureboundingbox);
                this.a(generatoraccess, b, 18, 0, 4, structureboundingbox);
                this.a(generatoraccess, b, 4, 2, 18, structureboundingbox);
                this.a(generatoraccess, e, 4, 1, 18, structureboundingbox);
                this.a(generatoraccess, b, 4, 0, 18, structureboundingbox);
                this.a(generatoraccess, b, 18, 2, 18, structureboundingbox);
                this.a(generatoraccess, e, 18, 1, 18, structureboundingbox);
                this.a(generatoraccess, b, 18, 0, 18, structureboundingbox);
                this.a(generatoraccess, b, 9, 7, 20, structureboundingbox);
                this.a(generatoraccess, b, 13, 7, 20, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 6, 0, 21, 7, 4, 21, b, b, false);
                this.a(generatoraccess, structureboundingbox, 15, 0, 21, 16, 4, 21, b, b, false);
                this.a(generatoraccess, structureboundingbox, 11, 2, 16);
            } else if (this.p == 1) {
                this.a(generatoraccess, structureboundingbox, 9, 3, 18, 13, 3, 20, b, b, false);
                this.a(generatoraccess, structureboundingbox, 9, 0, 18, 9, 2, 18, b, b, false);
                this.a(generatoraccess, structureboundingbox, 13, 0, 18, 13, 2, 18, b, b, false);
                byte b0 = 9;
                boolean flag = true;
                boolean flag1 = true;

                for(int j = 0; j < 2; ++j) {
                    this.a(generatoraccess, b, b0, 6, 20, structureboundingbox);
                    this.a(generatoraccess, e, b0, 5, 20, structureboundingbox);
                    this.a(generatoraccess, b, b0, 4, 20, structureboundingbox);
                    b0 = 13;
                }

                this.a(generatoraccess, structureboundingbox, 7, 3, 7, 15, 3, 14, b, b, false);
                b0 = 10;

                for(int l = 0; l < 2; ++l) {
                    this.a(generatoraccess, structureboundingbox, b0, 0, 10, b0, 6, 10, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b0, 0, 12, b0, 6, 12, b, b, false);
                    this.a(generatoraccess, e, b0, 0, 10, structureboundingbox);
                    this.a(generatoraccess, e, b0, 0, 12, structureboundingbox);
                    this.a(generatoraccess, e, b0, 4, 10, structureboundingbox);
                    this.a(generatoraccess, e, b0, 4, 12, structureboundingbox);
                    b0 = 12;
                }

                b0 = 8;

                for(int i1 = 0; i1 < 2; ++i1) {
                    this.a(generatoraccess, structureboundingbox, b0, 0, 7, b0, 2, 7, b, b, false);
                    this.a(generatoraccess, structureboundingbox, b0, 0, 14, b0, 2, 14, b, b, false);
                    b0 = 14;
                }

                this.a(generatoraccess, structureboundingbox, 8, 3, 8, 8, 3, 13, c, c, false);
                this.a(generatoraccess, structureboundingbox, 14, 3, 8, 14, 3, 13, c, c, false);
                this.a(generatoraccess, structureboundingbox, 11, 5, 13);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPieceEntry extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPieceEntry() {
        }

        public WorldGenMonumentPieceEntry(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 1, 1);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            this.a(generatoraccess, structureboundingbox, 0, 3, 0, 2, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 3, 0, 7, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 0, 1, 2, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, 2, 0, 7, 2, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 1, 0, 7, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 7, 7, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 2, 3, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 5, 1, 0, 6, 3, 0, b, b, false);
            if (this.l.c[EnumDirection.NORTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7);
            }

            if (this.l.c[EnumDirection.WEST.a()]) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 1, 2, 4);
            }

            if (this.l.c[EnumDirection.EAST.a()]) {
                this.a(generatoraccess, structureboundingbox, 6, 1, 3, 7, 2, 4);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiecePenthouse extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPiecePenthouse() {
        }

        public WorldGenMonumentPiecePenthouse(EnumDirection enumdirection, StructureBoundingBox structureboundingbox) {
            super(enumdirection, structureboundingbox);
        }

        public boolean a(GeneratorAccess generatoraccess, Random var2, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            this.a(generatoraccess, structureboundingbox, 2, -1, 2, 11, -1, 11, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, -1, 0, 1, -1, 11, a, a, false);
            this.a(generatoraccess, structureboundingbox, 12, -1, 0, 13, -1, 11, a, a, false);
            this.a(generatoraccess, structureboundingbox, 2, -1, 0, 11, -1, 1, a, a, false);
            this.a(generatoraccess, structureboundingbox, 2, -1, 12, 11, -1, 13, a, a, false);
            this.a(generatoraccess, structureboundingbox, 0, 0, 0, 0, 0, 13, b, b, false);
            this.a(generatoraccess, structureboundingbox, 13, 0, 0, 13, 0, 13, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 0, 12, 0, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 0, 13, 12, 0, 13, b, b, false);

            for(int i = 2; i <= 11; i += 3) {
                this.a(generatoraccess, e, 0, 0, i, structureboundingbox);
                this.a(generatoraccess, e, 13, 0, i, structureboundingbox);
                this.a(generatoraccess, e, i, 0, 0, structureboundingbox);
            }

            this.a(generatoraccess, structureboundingbox, 2, 0, 3, 4, 0, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 9, 0, 3, 11, 0, 9, b, b, false);
            this.a(generatoraccess, structureboundingbox, 4, 0, 9, 9, 0, 11, b, b, false);
            this.a(generatoraccess, b, 5, 0, 8, structureboundingbox);
            this.a(generatoraccess, b, 8, 0, 8, structureboundingbox);
            this.a(generatoraccess, b, 10, 0, 10, structureboundingbox);
            this.a(generatoraccess, b, 3, 0, 10, structureboundingbox);
            this.a(generatoraccess, structureboundingbox, 3, 0, 3, 3, 0, 7, c, c, false);
            this.a(generatoraccess, structureboundingbox, 10, 0, 3, 10, 0, 7, c, c, false);
            this.a(generatoraccess, structureboundingbox, 6, 0, 10, 7, 0, 10, c, c, false);
            byte b0 = 3;

            for(int j = 0; j < 2; ++j) {
                for(int k = 2; k <= 8; k += 3) {
                    this.a(generatoraccess, structureboundingbox, b0, 0, k, b0, 2, k, b, b, false);
                }

                b0 = 10;
            }

            this.a(generatoraccess, structureboundingbox, 5, 0, 10, 5, 2, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 8, 0, 10, 8, 2, 10, b, b, false);
            this.a(generatoraccess, structureboundingbox, 6, -1, 7, 7, -1, 8, c, c, false);
            this.a(generatoraccess, structureboundingbox, 6, -1, 3, 7, -1, 4);
            this.a(generatoraccess, structureboundingbox, 6, 1, 6);
            return true;
        }
    }

    static class WorldGenMonumentPieceSelector1 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector1() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            return !worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.WEST.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.SOUTH.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()];
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPieceSimpleT(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    static class WorldGenMonumentPieceSelector2 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector2() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker var1) {
            return true;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPieceSimple(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    static class WorldGenMonumentPieceSelector3 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector3() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            return worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker;
            if (!worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()] || worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].d) {
                worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.SOUTH.a()];
            }

            worldgenmonumentpieces$worldgenmonumentstatetracker1.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.NORTH.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece7(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker1, random);
        }
    }

    static class WorldGenMonumentPieceSelector4 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector4() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].d && worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d) {
                WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()];
                return worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()].d;
            } else {
                return false;
            }
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece6(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    static class WorldGenMonumentPieceSelector5 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector5() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            return worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece5(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    static class WorldGenMonumentPieceSelector6 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector6() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()].d && worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d) {
                WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()];
                return worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()].d;
            } else {
                return false;
            }
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()].d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece4(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    static class WorldGenMonumentPieceSelector7 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
        private WorldGenMonumentPieceSelector7() {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker) {
            return worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.EAST.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece3(enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, random);
        }
    }

    public static class WorldGenMonumentPieceSimple extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        private int p;

        public WorldGenMonumentPieceSimple() {
        }

        public WorldGenMonumentPieceSimple(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random random) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 1, 1);
            this.p = random.nextInt(3);
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 0, 0, this.l.c[EnumDirection.DOWN.a()]);
            }

            if (this.l.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 1, 6, 4, 6, a);
            }

            boolean flag = this.p != 0 && random.nextBoolean() && !this.l.c[EnumDirection.DOWN.a()] && !this.l.c[EnumDirection.UP.a()] && this.l.c() > 1;
            if (this.p == 0) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 0, 2, 1, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 3, 0, 2, 3, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 2, 2, a, a, false);
                this.a(generatoraccess, structureboundingbox, 1, 2, 0, 2, 2, 0, a, a, false);
                this.a(generatoraccess, e, 1, 2, 1, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 5, 1, 0, 7, 1, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 5, 3, 0, 7, 3, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 2, 0, 7, 2, 2, a, a, false);
                this.a(generatoraccess, structureboundingbox, 5, 2, 0, 6, 2, 0, a, a, false);
                this.a(generatoraccess, e, 6, 2, 1, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 0, 1, 5, 2, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 3, 5, 2, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 2, 5, 0, 2, 7, a, a, false);
                this.a(generatoraccess, structureboundingbox, 1, 2, 7, 2, 2, 7, a, a, false);
                this.a(generatoraccess, e, 1, 2, 6, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 5, 1, 5, 7, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 5, 3, 5, 7, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 2, 5, 7, 2, 7, a, a, false);
                this.a(generatoraccess, structureboundingbox, 5, 2, 7, 6, 2, 7, a, a, false);
                this.a(generatoraccess, e, 6, 2, 6, structureboundingbox);
                if (this.l.c[EnumDirection.SOUTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 3, 3, 0, 4, 3, 0, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 3, 3, 0, 4, 3, 1, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 3, 2, 0, 4, 2, 0, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 1, 1, b, b, false);
                }

                if (this.l.c[EnumDirection.NORTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 3, 3, 7, 4, 3, 7, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 3, 3, 6, 4, 3, 7, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 3, 2, 7, 4, 2, 7, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 3, 1, 6, 4, 1, 7, b, b, false);
                }

                if (this.l.c[EnumDirection.WEST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 0, 3, 3, 0, 3, 4, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 0, 3, 3, 1, 3, 4, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 0, 2, 3, 0, 2, 4, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 0, 1, 3, 1, 1, 4, b, b, false);
                }

                if (this.l.c[EnumDirection.EAST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 7, 3, 3, 7, 3, 4, b, b, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, 6, 3, 3, 7, 3, 4, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 7, 2, 3, 7, 2, 4, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 6, 1, 3, 7, 1, 4, b, b, false);
                }
            } else if (this.p == 1) {
                this.a(generatoraccess, structureboundingbox, 2, 1, 2, 2, 3, 2, b, b, false);
                this.a(generatoraccess, structureboundingbox, 2, 1, 5, 2, 3, 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, 5, 1, 5, 5, 3, 5, b, b, false);
                this.a(generatoraccess, structureboundingbox, 5, 1, 2, 5, 3, 2, b, b, false);
                this.a(generatoraccess, e, 2, 2, 2, structureboundingbox);
                this.a(generatoraccess, e, 2, 2, 5, structureboundingbox);
                this.a(generatoraccess, e, 5, 2, 5, structureboundingbox);
                this.a(generatoraccess, e, 5, 2, 2, structureboundingbox);
                this.a(generatoraccess, structureboundingbox, 0, 1, 0, 1, 3, 0, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 3, 1, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 1, 7, 1, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 1, 6, 0, 3, 6, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 7, 7, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 6, 7, 3, 6, b, b, false);
                this.a(generatoraccess, structureboundingbox, 6, 1, 0, 7, 3, 0, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 1, 7, 3, 1, b, b, false);
                this.a(generatoraccess, a, 1, 2, 0, structureboundingbox);
                this.a(generatoraccess, a, 0, 2, 1, structureboundingbox);
                this.a(generatoraccess, a, 1, 2, 7, structureboundingbox);
                this.a(generatoraccess, a, 0, 2, 6, structureboundingbox);
                this.a(generatoraccess, a, 6, 2, 7, structureboundingbox);
                this.a(generatoraccess, a, 7, 2, 6, structureboundingbox);
                this.a(generatoraccess, a, 6, 2, 0, structureboundingbox);
                this.a(generatoraccess, a, 7, 2, 1, structureboundingbox);
                if (!this.l.c[EnumDirection.SOUTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 1, 3, 0, 6, 3, 0, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 1, 2, 0, 6, 2, 0, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 1, 1, 0, 6, 1, 0, b, b, false);
                }

                if (!this.l.c[EnumDirection.NORTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 1, 3, 7, 6, 3, 7, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 1, 2, 7, 6, 2, 7, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 1, 1, 7, 6, 1, 7, b, b, false);
                }

                if (!this.l.c[EnumDirection.WEST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 0, 3, 1, 0, 3, 6, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 0, 2, 1, 0, 2, 6, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 0, 1, 1, 0, 1, 6, b, b, false);
                }

                if (!this.l.c[EnumDirection.EAST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 7, 3, 1, 7, 3, 6, b, b, false);
                    this.a(generatoraccess, structureboundingbox, 7, 2, 1, 7, 2, 6, a, a, false);
                    this.a(generatoraccess, structureboundingbox, 7, 1, 1, 7, 1, 6, b, b, false);
                }
            } else if (this.p == 2) {
                this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 0, 7, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 0, 6, 1, 0, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 1, 7, 6, 1, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 2, 7, c, c, false);
                this.a(generatoraccess, structureboundingbox, 7, 2, 0, 7, 2, 7, c, c, false);
                this.a(generatoraccess, structureboundingbox, 1, 2, 0, 6, 2, 0, c, c, false);
                this.a(generatoraccess, structureboundingbox, 1, 2, 7, 6, 2, 7, c, c, false);
                this.a(generatoraccess, structureboundingbox, 0, 3, 0, 0, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 7, 3, 0, 7, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 3, 0, 6, 3, 0, b, b, false);
                this.a(generatoraccess, structureboundingbox, 1, 3, 7, 6, 3, 7, b, b, false);
                this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4, c, c, false);
                this.a(generatoraccess, structureboundingbox, 7, 1, 3, 7, 2, 4, c, c, false);
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0, c, c, false);
                this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7, c, c, false);
                if (this.l.c[EnumDirection.SOUTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
                }

                if (this.l.c[EnumDirection.NORTH.a()]) {
                    this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7);
                }

                if (this.l.c[EnumDirection.WEST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4);
                }

                if (this.l.c[EnumDirection.EAST.a()]) {
                    this.a(generatoraccess, structureboundingbox, 7, 1, 3, 7, 2, 4);
                }
            }

            if (flag) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 3, 4, 1, 4, b, b, false);
                this.a(generatoraccess, structureboundingbox, 3, 2, 3, 4, 2, 4, a, a, false);
                this.a(generatoraccess, structureboundingbox, 3, 3, 3, 4, 3, 4, b, b, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPieceSimpleT extends WorldGenMonumentPieces.WorldGenMonumentPiece {
        public WorldGenMonumentPieceSimpleT() {
        }

        public WorldGenMonumentPieceSimpleT(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker, Random var3) {
            super(1, enumdirection, worldgenmonumentpieces$worldgenmonumentstatetracker, 1, 1, 1);
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair var4) {
            if (this.l.a / 25 > 0) {
                this.a(generatoraccess, structureboundingbox, 0, 0, this.l.c[EnumDirection.DOWN.a()]);
            }

            if (this.l.b[EnumDirection.UP.a()] == null) {
                this.a(generatoraccess, structureboundingbox, 1, 4, 1, 6, 4, 6, a);
            }

            for(int i = 1; i <= 6; ++i) {
                for(int j = 1; j <= 6; ++j) {
                    if (random.nextInt(3) != 0) {
                        int k = 2 + (random.nextInt(4) == 0 ? 0 : 1);
                        IBlockData iblockdata = Blocks.WET_SPONGE.getBlockData();
                        this.a(generatoraccess, structureboundingbox, i, k, j, i, 3, j, iblockdata, iblockdata, false);
                    }
                }
            }

            this.a(generatoraccess, structureboundingbox, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 1, 0, 7, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 0, 6, 1, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 1, 7, 6, 1, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 2, 0, 0, 2, 7, c, c, false);
            this.a(generatoraccess, structureboundingbox, 7, 2, 0, 7, 2, 7, c, c, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 0, 6, 2, 0, c, c, false);
            this.a(generatoraccess, structureboundingbox, 1, 2, 7, 6, 2, 7, c, c, false);
            this.a(generatoraccess, structureboundingbox, 0, 3, 0, 0, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 7, 3, 0, 7, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 0, 6, 3, 0, b, b, false);
            this.a(generatoraccess, structureboundingbox, 1, 3, 7, 6, 3, 7, b, b, false);
            this.a(generatoraccess, structureboundingbox, 0, 1, 3, 0, 2, 4, c, c, false);
            this.a(generatoraccess, structureboundingbox, 7, 1, 3, 7, 2, 4, c, c, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0, c, c, false);
            this.a(generatoraccess, structureboundingbox, 3, 1, 7, 4, 2, 7, c, c, false);
            if (this.l.c[EnumDirection.SOUTH.a()]) {
                this.a(generatoraccess, structureboundingbox, 3, 1, 0, 4, 2, 0);
            }

            return true;
        }
    }

    static class WorldGenMonumentStateTracker {
        private final int a;
        private final WorldGenMonumentPieces.WorldGenMonumentStateTracker[] b = new WorldGenMonumentPieces.WorldGenMonumentStateTracker[6];
        private final boolean[] c = new boolean[6];
        private boolean d;
        private boolean e;
        private int f;

        public WorldGenMonumentStateTracker(int i) {
            this.a = i;
        }

        public void a(EnumDirection enumdirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1) {
            this.b[enumdirection.a()] = worldgenmonumentpieces$worldgenmonumentstatetracker1;
            worldgenmonumentpieces$worldgenmonumentstatetracker1.b[enumdirection.opposite().a()] = this;
        }

        public void a() {
            for(int i = 0; i < 6; ++i) {
                this.c[i] = this.b[i] != null;
            }

        }

        public boolean a(int i) {
            if (this.e) {
                return true;
            } else {
                this.f = i;

                for(int j = 0; j < 6; ++j) {
                    if (this.b[j] != null && this.c[j] && this.b[j].f != i && this.b[j].a(i)) {
                        return true;
                    }
                }

                return false;
            }
        }

        public boolean b() {
            return this.a >= 75;
        }

        public int c() {
            int i = 0;

            for(int j = 0; j < 6; ++j) {
                if (this.c[j]) {
                    ++i;
                }
            }

            return i;
        }
    }
}
