package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenWoodlandMansionPieces {
    public static void a() {
        WorldGenFactory.a(WorldGenWoodlandMansionPieces.i.class, "WMP");
    }

    public static void a(DefinedStructureManager definedstructuremanager, BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<WorldGenWoodlandMansionPieces.i> list, Random random) {
        WorldGenWoodlandMansionPieces.c worldgenwoodlandmansionpieces$c = new WorldGenWoodlandMansionPieces.c(random);
        WorldGenWoodlandMansionPieces.d worldgenwoodlandmansionpieces$d = new WorldGenWoodlandMansionPieces.d(definedstructuremanager, random);
        worldgenwoodlandmansionpieces$d.a(blockposition, enumblockrotation, list, worldgenwoodlandmansionpieces$c);
    }

    static class a extends WorldGenWoodlandMansionPieces.b {
        private a() {
        }

        public String a(Random random) {
            return "1x1_a" + (random.nextInt(5) + 1);
        }

        public String b(Random random) {
            return "1x1_as" + (random.nextInt(4) + 1);
        }

        public String a(Random random, boolean var2) {
            return "1x2_a" + (random.nextInt(9) + 1);
        }

        public String b(Random random, boolean var2) {
            return "1x2_b" + (random.nextInt(5) + 1);
        }

        public String c(Random random) {
            return "1x2_s" + (random.nextInt(2) + 1);
        }

        public String d(Random random) {
            return "2x2_a" + (random.nextInt(4) + 1);
        }

        public String e(Random var1) {
            return "2x2_s1";
        }
    }

    abstract static class b {
        private b() {
        }

        public abstract String a(Random var1);

        public abstract String b(Random var1);

        public abstract String a(Random var1, boolean var2);

        public abstract String b(Random var1, boolean var2);

        public abstract String c(Random var1);

        public abstract String d(Random var1);

        public abstract String e(Random var1);
    }

    static class c {
        private final Random a;
        private final WorldGenWoodlandMansionPieces.g b;
        private final WorldGenWoodlandMansionPieces.g c;
        private final WorldGenWoodlandMansionPieces.g[] d;
        private final int e;
        private final int f;

        public c(Random random) {
            this.a = random;
            boolean flag = true;
            this.e = 7;
            this.f = 4;
            this.b = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.b.a(this.e, this.f, this.e + 1, this.f + 1, 3);
            this.b.a(this.e - 1, this.f, this.e - 1, this.f + 1, 2);
            this.b.a(this.e + 2, this.f - 2, this.e + 3, this.f + 3, 5);
            this.b.a(this.e + 1, this.f - 2, this.e + 1, this.f - 1, 1);
            this.b.a(this.e + 1, this.f + 2, this.e + 1, this.f + 3, 1);
            this.b.a(this.e - 1, this.f - 1, 1);
            this.b.a(this.e - 1, this.f + 2, 1);
            this.b.a(0, 0, 11, 1, 5);
            this.b.a(0, 9, 11, 11, 5);
            this.a(this.b, this.e, this.f - 2, EnumDirection.WEST, 6);
            this.a(this.b, this.e, this.f + 3, EnumDirection.WEST, 6);
            this.a(this.b, this.e - 2, this.f - 1, EnumDirection.WEST, 3);
            this.a(this.b, this.e - 2, this.f + 2, EnumDirection.WEST, 3);

            while(this.a(this.b)) {
                ;
            }

            this.d = new WorldGenWoodlandMansionPieces.g[3];
            this.d[0] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.d[1] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.d[2] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.a(this.b, this.d[0]);
            this.a(this.b, this.d[1]);
            this.d[0].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
            this.d[1].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
            this.c = new WorldGenWoodlandMansionPieces.g(this.b.b, this.b.c, 5);
            this.b();
            this.a(this.c, this.d[2]);
        }

        public static boolean a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, int i, int j) {
            int k = worldgenwoodlandmansionpieces$g.a(i, j);
            return k == 1 || k == 2 || k == 3 || k == 4;
        }

        public boolean a(WorldGenWoodlandMansionPieces.g var1, int i, int j, int k, int l) {
            return (this.d[k].a(i, j) & '\uffff') == l;
        }

        @Nullable
        public EnumDirection b(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, int i, int j, int k, int l) {
            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                if (this.a(worldgenwoodlandmansionpieces$g, i + enumdirection.getAdjacentX(), j + enumdirection.getAdjacentZ(), k, l)) {
                    return enumdirection;
                }
            }

            return null;
        }

        private void a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, int i, int j, EnumDirection enumdirection, int k) {
            if (k > 0) {
                worldgenwoodlandmansionpieces$g.a(i, j, 1);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection.getAdjacentX(), j + enumdirection.getAdjacentZ(), 0, 1);

                for(int l = 0; l < 8; ++l) {
                    EnumDirection enumdirection1 = EnumDirection.fromType2(this.a.nextInt(4));
                    if (enumdirection1 != enumdirection.opposite() && (enumdirection1 != EnumDirection.EAST || !this.a.nextBoolean())) {
                        int i1 = i + enumdirection.getAdjacentX();
                        int j1 = j + enumdirection.getAdjacentZ();
                        if (worldgenwoodlandmansionpieces$g.a(i1 + enumdirection1.getAdjacentX(), j1 + enumdirection1.getAdjacentZ()) == 0 && worldgenwoodlandmansionpieces$g.a(i1 + enumdirection1.getAdjacentX() * 2, j1 + enumdirection1.getAdjacentZ() * 2) == 0) {
                            this.a(worldgenwoodlandmansionpieces$g, i + enumdirection.getAdjacentX() + enumdirection1.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection1.getAdjacentZ(), enumdirection1, k - 1);
                            break;
                        }
                    }
                }

                EnumDirection enumdirection2 = enumdirection.e();
                EnumDirection enumdirection3 = enumdirection.f();
                worldgenwoodlandmansionpieces$g.a(i + enumdirection2.getAdjacentX(), j + enumdirection2.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection3.getAdjacentX(), j + enumdirection3.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection.getAdjacentX() + enumdirection2.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection2.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection.getAdjacentX() + enumdirection3.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection3.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection.getAdjacentX() * 2, j + enumdirection.getAdjacentZ() * 2, 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection2.getAdjacentX() * 2, j + enumdirection2.getAdjacentZ() * 2, 0, 2);
                worldgenwoodlandmansionpieces$g.a(i + enumdirection3.getAdjacentX() * 2, j + enumdirection3.getAdjacentZ() * 2, 0, 2);
            }
        }

        private boolean a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g) {
            boolean flag = false;

            for(int i = 0; i < worldgenwoodlandmansionpieces$g.c; ++i) {
                for(int j = 0; j < worldgenwoodlandmansionpieces$g.b; ++j) {
                    if (worldgenwoodlandmansionpieces$g.a(j, i) == 0) {
                        int k = 0;
                        k = k + (a(worldgenwoodlandmansionpieces$g, j + 1, i) ? 1 : 0);
                        k = k + (a(worldgenwoodlandmansionpieces$g, j - 1, i) ? 1 : 0);
                        k = k + (a(worldgenwoodlandmansionpieces$g, j, i + 1) ? 1 : 0);
                        k = k + (a(worldgenwoodlandmansionpieces$g, j, i - 1) ? 1 : 0);
                        if (k >= 3) {
                            worldgenwoodlandmansionpieces$g.a(j, i, 2);
                            flag = true;
                        } else if (k == 2) {
                            int l = 0;
                            l = l + (a(worldgenwoodlandmansionpieces$g, j + 1, i + 1) ? 1 : 0);
                            l = l + (a(worldgenwoodlandmansionpieces$g, j - 1, i + 1) ? 1 : 0);
                            l = l + (a(worldgenwoodlandmansionpieces$g, j + 1, i - 1) ? 1 : 0);
                            l = l + (a(worldgenwoodlandmansionpieces$g, j - 1, i - 1) ? 1 : 0);
                            if (l <= 1) {
                                worldgenwoodlandmansionpieces$g.a(j, i, 2);
                                flag = true;
                            }
                        }
                    }
                }
            }

            return flag;
        }

        private void b() {
            ArrayList arraylist = Lists.newArrayList();
            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g = this.d[1];

            for(int i = 0; i < this.c.c; ++i) {
                for(int j = 0; j < this.c.b; ++j) {
                    int k = worldgenwoodlandmansionpieces$g.a(j, i);
                    int l = k & 983040;
                    if (l == 131072 && (k & 2097152) == 2097152) {
                        arraylist.add(new Tuple(j, i));
                    }
                }
            }

            if (arraylist.isEmpty()) {
                this.c.a(0, 0, this.c.b, this.c.c, 5);
            } else {
                Tuple tuple = (Tuple)arraylist.get(this.a.nextInt(arraylist.size()));
                int l1 = worldgenwoodlandmansionpieces$g.a(tuple.a(), tuple.b());
                worldgenwoodlandmansionpieces$g.a(tuple.a(), tuple.b(), l1 | 4194304);
                EnumDirection enumdirection1 = this.b(this.b, tuple.a(), tuple.b(), 1, l1 & '\uffff');
                int i2 = tuple.a() + enumdirection1.getAdjacentX();
                int i1 = tuple.b() + enumdirection1.getAdjacentZ();

                for(int j1 = 0; j1 < this.c.c; ++j1) {
                    for(int k1 = 0; k1 < this.c.b; ++k1) {
                        if (!a(this.b, k1, j1)) {
                            this.c.a(k1, j1, 5);
                        } else if (k1 == tuple.a() && j1 == tuple.b()) {
                            this.c.a(k1, j1, 3);
                        } else if (k1 == i2 && j1 == i1) {
                            this.c.a(k1, j1, 3);
                            this.d[2].a(k1, j1, 8388608);
                        }
                    }
                }

                ArrayList arraylist1 = Lists.newArrayList();

                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                    if (this.c.a(i2 + enumdirection.getAdjacentX(), i1 + enumdirection.getAdjacentZ()) == 0) {
                        arraylist1.add(enumdirection);
                    }
                }

                if (arraylist1.isEmpty()) {
                    this.c.a(0, 0, this.c.b, this.c.c, 5);
                    worldgenwoodlandmansionpieces$g.a(tuple.a(), tuple.b(), l1);
                } else {
                    EnumDirection enumdirection2 = (EnumDirection)arraylist1.get(this.a.nextInt(arraylist1.size()));
                    this.a(this.c, i2 + enumdirection2.getAdjacentX(), i1 + enumdirection2.getAdjacentZ(), enumdirection2, 4);

                    while(this.a(this.c)) {
                        ;
                    }

                }
            }
        }

        private void a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g1) {
            ArrayList arraylist = Lists.newArrayList();

            for(int i = 0; i < worldgenwoodlandmansionpieces$g.c; ++i) {
                for(int j = 0; j < worldgenwoodlandmansionpieces$g.b; ++j) {
                    if (worldgenwoodlandmansionpieces$g.a(j, i) == 2) {
                        arraylist.add(new Tuple(j, i));
                    }
                }
            }

            Collections.shuffle(arraylist, this.a);
            int k3 = 10;

            for(Tuple tuple : arraylist) {
                int k = tuple.a();
                int l = tuple.b();
                if (worldgenwoodlandmansionpieces$g1.a(k, l) == 0) {
                    int i1 = k;
                    int j1 = k;
                    int k1 = l;
                    int l1 = l;
                    int i2 = 65536;
                    if (worldgenwoodlandmansionpieces$g1.a(k + 1, l) == 0 && worldgenwoodlandmansionpieces$g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces$g1.a(k + 1, l + 1) == 0 && worldgenwoodlandmansionpieces$g.a(k + 1, l) == 2 && worldgenwoodlandmansionpieces$g.a(k, l + 1) == 2 && worldgenwoodlandmansionpieces$g.a(k + 1, l + 1) == 2) {
                        j1 = k + 1;
                        l1 = l + 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces$g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces$g1.a(k - 1, l + 1) == 0 && worldgenwoodlandmansionpieces$g.a(k - 1, l) == 2 && worldgenwoodlandmansionpieces$g.a(k, l + 1) == 2 && worldgenwoodlandmansionpieces$g.a(k - 1, l + 1) == 2) {
                        i1 = k - 1;
                        l1 = l + 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces$g1.a(k, l - 1) == 0 && worldgenwoodlandmansionpieces$g1.a(k - 1, l - 1) == 0 && worldgenwoodlandmansionpieces$g.a(k - 1, l) == 2 && worldgenwoodlandmansionpieces$g.a(k, l - 1) == 2 && worldgenwoodlandmansionpieces$g.a(k - 1, l - 1) == 2) {
                        i1 = k - 1;
                        k1 = l - 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k + 1, l) == 0 && worldgenwoodlandmansionpieces$g.a(k + 1, l) == 2) {
                        j1 = k + 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces$g.a(k, l + 1) == 2) {
                        l1 = l + 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces$g.a(k - 1, l) == 2) {
                        i1 = k - 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces$g1.a(k, l - 1) == 0 && worldgenwoodlandmansionpieces$g.a(k, l - 1) == 2) {
                        k1 = l - 1;
                        i2 = 131072;
                    }

                    int j2 = this.a.nextBoolean() ? i1 : j1;
                    int k2 = this.a.nextBoolean() ? k1 : l1;
                    int l2 = 2097152;
                    if (!worldgenwoodlandmansionpieces$g.b(j2, k2, 1)) {
                        j2 = j2 == i1 ? j1 : i1;
                        k2 = k2 == k1 ? l1 : k1;
                        if (!worldgenwoodlandmansionpieces$g.b(j2, k2, 1)) {
                            k2 = k2 == k1 ? l1 : k1;
                            if (!worldgenwoodlandmansionpieces$g.b(j2, k2, 1)) {
                                j2 = j2 == i1 ? j1 : i1;
                                k2 = k2 == k1 ? l1 : k1;
                                if (!worldgenwoodlandmansionpieces$g.b(j2, k2, 1)) {
                                    l2 = 0;
                                    j2 = i1;
                                    k2 = k1;
                                }
                            }
                        }
                    }

                    for(int i3 = k1; i3 <= l1; ++i3) {
                        for(int j3 = i1; j3 <= j1; ++j3) {
                            if (j3 == j2 && i3 == k2) {
                                worldgenwoodlandmansionpieces$g1.a(j3, i3, 1048576 | l2 | i2 | k3);
                            } else {
                                worldgenwoodlandmansionpieces$g1.a(j3, i3, i2 | k3);
                            }
                        }
                    }

                    ++k3;
                }
            }

        }
    }

    static class d {
        private final DefinedStructureManager a;
        private final Random b;
        private int c;
        private int d;

        public d(DefinedStructureManager definedstructuremanager, Random random) {
            this.a = definedstructuremanager;
            this.b = random;
        }

        public void a(BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.c worldgenwoodlandmansionpieces$c) {
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e = new WorldGenWoodlandMansionPieces.e();
            worldgenwoodlandmansionpieces$e.b = blockposition;
            worldgenwoodlandmansionpieces$e.a = enumblockrotation;
            worldgenwoodlandmansionpieces$e.c = "wall_flat";
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e1 = new WorldGenWoodlandMansionPieces.e();
            this.a(list, worldgenwoodlandmansionpieces$e);
            worldgenwoodlandmansionpieces$e1.b = worldgenwoodlandmansionpieces$e.b.up(8);
            worldgenwoodlandmansionpieces$e1.a = worldgenwoodlandmansionpieces$e.a;
            worldgenwoodlandmansionpieces$e1.c = "wall_window";
            if (!list.isEmpty()) {
                ;
            }

            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g = worldgenwoodlandmansionpieces$c.b;
            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g1 = worldgenwoodlandmansionpieces$c.c;
            this.c = worldgenwoodlandmansionpieces$c.e + 1;
            this.d = worldgenwoodlandmansionpieces$c.f + 1;
            int i = worldgenwoodlandmansionpieces$c.e + 1;
            int j = worldgenwoodlandmansionpieces$c.f;
            this.a(list, worldgenwoodlandmansionpieces$e, worldgenwoodlandmansionpieces$g, EnumDirection.SOUTH, this.c, this.d, i, j);
            this.a(list, worldgenwoodlandmansionpieces$e1, worldgenwoodlandmansionpieces$g, EnumDirection.SOUTH, this.c, this.d, i, j);
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e2 = new WorldGenWoodlandMansionPieces.e();
            worldgenwoodlandmansionpieces$e2.b = worldgenwoodlandmansionpieces$e.b.up(19);
            worldgenwoodlandmansionpieces$e2.a = worldgenwoodlandmansionpieces$e.a;
            worldgenwoodlandmansionpieces$e2.c = "wall_window";
            boolean flag = false;

            for(int k = 0; k < worldgenwoodlandmansionpieces$g1.c && !flag; ++k) {
                for(int l = worldgenwoodlandmansionpieces$g1.b - 1; l >= 0 && !flag; --l) {
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g1, l, k)) {
                        worldgenwoodlandmansionpieces$e2.b = worldgenwoodlandmansionpieces$e2.b.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (k - this.d) * 8);
                        worldgenwoodlandmansionpieces$e2.b = worldgenwoodlandmansionpieces$e2.b.shift(enumblockrotation.a(EnumDirection.EAST), (l - this.c) * 8);
                        this.b(list, worldgenwoodlandmansionpieces$e2);
                        this.a(list, worldgenwoodlandmansionpieces$e2, worldgenwoodlandmansionpieces$g1, EnumDirection.SOUTH, l, k, l, k);
                        flag = true;
                    }
                }
            }

            this.a(list, blockposition.up(16), enumblockrotation, worldgenwoodlandmansionpieces$g, worldgenwoodlandmansionpieces$g1);
            this.a(list, blockposition.up(27), enumblockrotation, worldgenwoodlandmansionpieces$g1, (WorldGenWoodlandMansionPieces.g)null);
            if (!list.isEmpty()) {
                ;
            }

            WorldGenWoodlandMansionPieces.b[] aworldgenwoodlandmansionpieces$b = new WorldGenWoodlandMansionPieces.b[]{new WorldGenWoodlandMansionPieces.a(), new WorldGenWoodlandMansionPieces.f(), new WorldGenWoodlandMansionPieces.h()};

            for(int l2 = 0; l2 < 3; ++l2) {
                BlockPosition blockposition1 = blockposition.up(8 * l2 + (l2 == 2 ? 3 : 0));
                WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g2 = worldgenwoodlandmansionpieces$c.d[l2];
                WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g3 = l2 == 2 ? worldgenwoodlandmansionpieces$g1 : worldgenwoodlandmansionpieces$g;
                String s = l2 == 0 ? "carpet_south_1" : "carpet_south_2";
                String s1 = l2 == 0 ? "carpet_west_1" : "carpet_west_2";

                for(int i1 = 0; i1 < worldgenwoodlandmansionpieces$g3.c; ++i1) {
                    for(int j1 = 0; j1 < worldgenwoodlandmansionpieces$g3.b; ++j1) {
                        if (worldgenwoodlandmansionpieces$g3.a(j1, i1) == 1) {
                            BlockPosition blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i1 - this.d) * 8);
                            blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.EAST), (j1 - this.c) * 8);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "corridor_floor", blockposition2, enumblockrotation));
                            if (worldgenwoodlandmansionpieces$g3.a(j1, i1 - 1) == 1 || (worldgenwoodlandmansionpieces$g2.a(j1, i1 - 1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "carpet_north", blockposition2.shift(enumblockrotation.a(EnumDirection.EAST), 1).up(), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces$g3.a(j1 + 1, i1) == 1 || (worldgenwoodlandmansionpieces$g2.a(j1 + 1, i1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "carpet_east", blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 1).shift(enumblockrotation.a(EnumDirection.EAST), 5).up(), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces$g3.a(j1, i1 + 1) == 1 || (worldgenwoodlandmansionpieces$g2.a(j1, i1 + 1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, s, blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 5).shift(enumblockrotation.a(EnumDirection.WEST), 1), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces$g3.a(j1 - 1, i1) == 1 || (worldgenwoodlandmansionpieces$g2.a(j1 - 1, i1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, s1, blockposition2.shift(enumblockrotation.a(EnumDirection.WEST), 1).shift(enumblockrotation.a(EnumDirection.NORTH), 1), enumblockrotation));
                            }
                        }
                    }
                }

                String s2 = l2 == 0 ? "indoors_wall_1" : "indoors_wall_2";
                String s3 = l2 == 0 ? "indoors_door_1" : "indoors_door_2";
                ArrayList arraylist = Lists.newArrayList();

                for(int k1 = 0; k1 < worldgenwoodlandmansionpieces$g3.c; ++k1) {
                    for(int l1 = 0; l1 < worldgenwoodlandmansionpieces$g3.b; ++l1) {
                        boolean flag1 = l2 == 2 && worldgenwoodlandmansionpieces$g3.a(l1, k1) == 3;
                        if (worldgenwoodlandmansionpieces$g3.a(l1, k1) == 2 || flag1) {
                            int i2 = worldgenwoodlandmansionpieces$g2.a(l1, k1);
                            int j2 = i2 & 983040;
                            int k2 = i2 & '\uffff';
                            flag1 = flag1 && (i2 & 8388608) == 8388608;
                            arraylist.clear();
                            if ((i2 & 2097152) == 2097152) {
                                for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                                    if (worldgenwoodlandmansionpieces$g3.a(l1 + enumdirection.getAdjacentX(), k1 + enumdirection.getAdjacentZ()) == 1) {
                                        arraylist.add(enumdirection);
                                    }
                                }
                            }

                            EnumDirection enumdirection1 = null;
                            if (!arraylist.isEmpty()) {
                                enumdirection1 = (EnumDirection)arraylist.get(this.b.nextInt(arraylist.size()));
                            } else if ((i2 & 1048576) == 1048576) {
                                enumdirection1 = EnumDirection.UP;
                            }

                            BlockPosition blockposition4 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (k1 - this.d) * 8);
                            blockposition4 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), -1 + (l1 - this.c) * 8);
                            if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g3, l1 - 1, k1) && !worldgenwoodlandmansionpieces$c.a(worldgenwoodlandmansionpieces$g3, l1 - 1, k1, l2, k2)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, enumdirection1 == EnumDirection.WEST ? s3 : s2, blockposition4, enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces$g3.a(l1 + 1, k1) == 1 && !flag1) {
                                BlockPosition blockposition3 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 8);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, enumdirection1 == EnumDirection.EAST ? s3 : s2, blockposition3, enumblockrotation));
                            }

                            if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g3, l1, k1 + 1) && !worldgenwoodlandmansionpieces$c.a(worldgenwoodlandmansionpieces$g3, l1, k1 + 1, l2, k2)) {
                                BlockPosition blockposition5 = blockposition4.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                blockposition5 = blockposition5.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, enumdirection1 == EnumDirection.SOUTH ? s3 : s2, blockposition5, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (worldgenwoodlandmansionpieces$g3.a(l1, k1 - 1) == 1 && !flag1) {
                                BlockPosition blockposition6 = blockposition4.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                blockposition6 = blockposition6.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, enumdirection1 == EnumDirection.NORTH ? s3 : s2, blockposition6, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (j2 == 65536) {
                                this.a(list, blockposition4, enumblockrotation, enumdirection1, aworldgenwoodlandmansionpieces$b[l2]);
                            } else if (j2 == 131072 && enumdirection1 != null) {
                                EnumDirection enumdirection3 = worldgenwoodlandmansionpieces$c.b(worldgenwoodlandmansionpieces$g3, l1, k1, l2, k2);
                                boolean flag2 = (i2 & 4194304) == 4194304;
                                this.a(list, blockposition4, enumblockrotation, enumdirection3, enumdirection1, aworldgenwoodlandmansionpieces$b[l2], flag2);
                            } else if (j2 == 262144 && enumdirection1 != null && enumdirection1 != EnumDirection.UP) {
                                EnumDirection enumdirection2 = enumdirection1.e();
                                if (!worldgenwoodlandmansionpieces$c.a(worldgenwoodlandmansionpieces$g3, l1 + enumdirection2.getAdjacentX(), k1 + enumdirection2.getAdjacentZ(), l2, k2)) {
                                    enumdirection2 = enumdirection2.opposite();
                                }

                                this.a(list, blockposition4, enumblockrotation, enumdirection2, enumdirection1, aworldgenwoodlandmansionpieces$b[l2]);
                            } else if (j2 == 262144 && enumdirection1 == EnumDirection.UP) {
                                this.a(list, blockposition4, enumblockrotation, aworldgenwoodlandmansionpieces$b[l2]);
                            }
                        }
                    }
                }
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, EnumDirection enumdirection, int i, int j, int k, int l) {
            int i1 = i;
            int j1 = j;
            EnumDirection enumdirection1 = enumdirection;

            while(true) {
                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 + enumdirection.getAdjacentX(), j1 + enumdirection.getAdjacentZ())) {
                    this.c(list, worldgenwoodlandmansionpieces$e);
                    enumdirection = enumdirection.e();
                    if (i1 != k || j1 != l || enumdirection1 != enumdirection) {
                        this.b(list, worldgenwoodlandmansionpieces$e);
                    }
                } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 + enumdirection.getAdjacentX(), j1 + enumdirection.getAdjacentZ()) && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 + enumdirection.getAdjacentX() + enumdirection.f().getAdjacentX(), j1 + enumdirection.getAdjacentZ() + enumdirection.f().getAdjacentZ())) {
                    this.d(list, worldgenwoodlandmansionpieces$e);
                    i1 += enumdirection.getAdjacentX();
                    j1 += enumdirection.getAdjacentZ();
                    enumdirection = enumdirection.f();
                } else {
                    i1 += enumdirection.getAdjacentX();
                    j1 += enumdirection.getAdjacentZ();
                    if (i1 != k || j1 != l || enumdirection1 != enumdirection) {
                        this.b(list, worldgenwoodlandmansionpieces$e);
                    }
                }

                if (i1 == k && j1 == l && enumdirection1 == enumdirection) {
                    break;
                }
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g, @Nullable WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces$g1) {
            for(int i = 0; i < worldgenwoodlandmansionpieces$g.c; ++i) {
                for(int j = 0; j < worldgenwoodlandmansionpieces$g.b; ++j) {
                    BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i - this.d) * 8);
                    blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), (j - this.c) * 8);
                    boolean flag = worldgenwoodlandmansionpieces$g1 != null && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g1, j, i);
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j, i) && !flag) {
                        list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof", blockposition1.up(3), enumblockrotation));
                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j + 1, i)) {
                            BlockPosition blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_front", blockposition2, enumblockrotation));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j - 1, i)) {
                            BlockPosition blockposition6 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 0);
                            blockposition6 = blockposition6.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_front", blockposition6, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j, i - 1)) {
                            BlockPosition blockposition7 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_front", blockposition7, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j, i + 1)) {
                            BlockPosition blockposition8 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            blockposition8 = blockposition8.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_front", blockposition8, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                        }
                    }
                }
            }

            if (worldgenwoodlandmansionpieces$g1 != null) {
                for(int k = 0; k < worldgenwoodlandmansionpieces$g.c; ++k) {
                    for(int i1 = 0; i1 < worldgenwoodlandmansionpieces$g.b; ++i1) {
                        BlockPosition blockposition4 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (k - this.d) * 8);
                        blockposition4 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), (i1 - this.c) * 8);
                        boolean flag1 = WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g1, i1, k);
                        if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k) && flag1) {
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 + 1, k)) {
                                BlockPosition blockposition9 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall", blockposition9, enumblockrotation));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 - 1, k)) {
                                BlockPosition blockposition10 = blockposition4.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                                blockposition10 = blockposition10.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall", blockposition10, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k - 1)) {
                                BlockPosition blockposition11 = blockposition4.shift(enumblockrotation.a(EnumDirection.WEST), 0);
                                blockposition11 = blockposition11.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall", blockposition11, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k + 1)) {
                                BlockPosition blockposition12 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                                blockposition12 = blockposition12.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall", blockposition12, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 + 1, k)) {
                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k - 1)) {
                                    BlockPosition blockposition13 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                    blockposition13 = blockposition13.shift(enumblockrotation.a(EnumDirection.NORTH), 2);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall_corner", blockposition13, enumblockrotation));
                                }

                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k + 1)) {
                                    BlockPosition blockposition14 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 8);
                                    blockposition14 = blockposition14.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall_corner", blockposition14, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                                }
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1 - 1, k)) {
                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k - 1)) {
                                    BlockPosition blockposition15 = blockposition4.shift(enumblockrotation.a(EnumDirection.WEST), 2);
                                    blockposition15 = blockposition15.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall_corner", blockposition15, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                                }

                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, i1, k + 1)) {
                                    BlockPosition blockposition16 = blockposition4.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                                    blockposition16 = blockposition16.shift(enumblockrotation.a(EnumDirection.SOUTH), 8);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.a, "small_wall_corner", blockposition16, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                                }
                            }
                        }
                    }
                }
            }

            for(int l = 0; l < worldgenwoodlandmansionpieces$g.c; ++l) {
                for(int j1 = 0; j1 < worldgenwoodlandmansionpieces$g.b; ++j1) {
                    BlockPosition blockposition5 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (l - this.d) * 8);
                    blockposition5 = blockposition5.shift(enumblockrotation.a(EnumDirection.EAST), (j1 - this.c) * 8);
                    boolean flag2 = worldgenwoodlandmansionpieces$g1 != null && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g1, j1, l);
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1, l) && !flag2) {
                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 + 1, l)) {
                            BlockPosition blockposition17 = blockposition5.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1, l + 1)) {
                                BlockPosition blockposition3 = blockposition17.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_corner", blockposition3, enumblockrotation));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 + 1, l + 1)) {
                                BlockPosition blockposition19 = blockposition17.shift(enumblockrotation.a(EnumDirection.SOUTH), 5);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_inner_corner", blockposition19, enumblockrotation));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1, l - 1)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_corner", blockposition17, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 + 1, l - 1)) {
                                BlockPosition blockposition20 = blockposition5.shift(enumblockrotation.a(EnumDirection.EAST), 9);
                                blockposition20 = blockposition20.shift(enumblockrotation.a(EnumDirection.NORTH), 2);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_inner_corner", blockposition20, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 - 1, l)) {
                            BlockPosition blockposition18 = blockposition5.shift(enumblockrotation.a(EnumDirection.EAST), 0);
                            blockposition18 = blockposition18.shift(enumblockrotation.a(EnumDirection.SOUTH), 0);
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1, l + 1)) {
                                BlockPosition blockposition21 = blockposition18.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_corner", blockposition21, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 - 1, l + 1)) {
                                BlockPosition blockposition22 = blockposition18.shift(enumblockrotation.a(EnumDirection.SOUTH), 8);
                                blockposition22 = blockposition22.shift(enumblockrotation.a(EnumDirection.WEST), 3);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_inner_corner", blockposition22, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1, l - 1)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_corner", blockposition18, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces$g, j1 - 1, l - 1)) {
                                BlockPosition blockposition23 = blockposition18.shift(enumblockrotation.a(EnumDirection.SOUTH), 1);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.a, "roof_inner_corner", blockposition23, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            }
                        }
                    }
                }
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e) {
            EnumDirection enumdirection = worldgenwoodlandmansionpieces$e.a.a(EnumDirection.WEST);
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "entrance", worldgenwoodlandmansionpieces$e.b.shift(enumdirection, 9), worldgenwoodlandmansionpieces$e.a));
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.SOUTH), 16);
        }

        private void b(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e) {
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$e.c, worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.EAST), 7), worldgenwoodlandmansionpieces$e.a));
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.SOUTH), 8);
        }

        private void c(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e) {
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.SOUTH), -1);
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, "wall_corner", worldgenwoodlandmansionpieces$e.b, worldgenwoodlandmansionpieces$e.a));
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.SOUTH), -7);
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.WEST), -6);
            worldgenwoodlandmansionpieces$e.a = worldgenwoodlandmansionpieces$e.a.a(EnumBlockRotation.CLOCKWISE_90);
        }

        private void d(List<WorldGenWoodlandMansionPieces.i> var1, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces$e) {
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.SOUTH), 6);
            worldgenwoodlandmansionpieces$e.b = worldgenwoodlandmansionpieces$e.b.shift(worldgenwoodlandmansionpieces$e.a.a(EnumDirection.EAST), 8);
            worldgenwoodlandmansionpieces$e.a = worldgenwoodlandmansionpieces$e.a.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces$b) {
            EnumBlockRotation enumblockrotation1 = EnumBlockRotation.NONE;
            String s = worldgenwoodlandmansionpieces$b.a(this.b);
            if (enumdirection != EnumDirection.EAST) {
                if (enumdirection == EnumDirection.NORTH) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
                } else if (enumdirection == EnumDirection.WEST) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.CLOCKWISE_180);
                } else if (enumdirection == EnumDirection.SOUTH) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.CLOCKWISE_90);
                } else {
                    s = worldgenwoodlandmansionpieces$b.b(this.b);
                }
            }

            BlockPosition blockposition1 = DefinedStructure.a(new BlockPosition(1, 0, 0), EnumBlockMirror.NONE, enumblockrotation1, 7, 7);
            enumblockrotation1 = enumblockrotation1.a(enumblockrotation);
            blockposition1 = blockposition1.a(enumblockrotation);
            BlockPosition blockposition2 = blockposition.a(blockposition1.getX(), 0, blockposition1.getZ());
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, s, blockposition2, enumblockrotation1));
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, EnumDirection enumdirection1, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces$b, boolean flag) {
            if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.SOUTH) {
                BlockPosition blockposition14 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition14, enumblockrotation));
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.NORTH) {
                BlockPosition blockposition13 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition13 = blockposition13.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition13, enumblockrotation, EnumBlockMirror.LEFT_RIGHT));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.NORTH) {
                BlockPosition blockposition12 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition12 = blockposition12.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition12, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.SOUTH) {
                BlockPosition blockposition11 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition11, enumblockrotation, EnumBlockMirror.FRONT_BACK));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.EAST) {
                BlockPosition blockposition10 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition10, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90), EnumBlockMirror.LEFT_RIGHT));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.WEST) {
                BlockPosition blockposition9 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition9, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.WEST) {
                BlockPosition blockposition8 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition8 = blockposition8.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition8, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90), EnumBlockMirror.FRONT_BACK));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.EAST) {
                BlockPosition blockposition7 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition7 = blockposition7.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.a(this.b, flag), blockposition7, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.NORTH) {
                BlockPosition blockposition6 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition6 = blockposition6.shift(enumblockrotation.a(EnumDirection.NORTH), 8);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.b(this.b, flag), blockposition6, enumblockrotation));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.SOUTH) {
                BlockPosition blockposition5 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition5 = blockposition5.shift(enumblockrotation.a(EnumDirection.SOUTH), 14);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.b(this.b, flag), blockposition5, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.EAST) {
                BlockPosition blockposition4 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 15);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.b(this.b, flag), blockposition4, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.WEST) {
                BlockPosition blockposition3 = blockposition.shift(enumblockrotation.a(EnumDirection.WEST), 7);
                blockposition3 = blockposition3.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.b(this.b, flag), blockposition3, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.UP && enumdirection == EnumDirection.EAST) {
                BlockPosition blockposition2 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 15);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.c(this.b), blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.UP && enumdirection == EnumDirection.SOUTH) {
                BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.NORTH), 0);
                list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.c(this.b), blockposition1, enumblockrotation));
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, EnumDirection enumdirection1, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces$b) {
            byte b0 = 0;
            byte b1 = 0;
            EnumBlockRotation enumblockrotation1 = enumblockrotation;
            EnumBlockMirror enumblockmirror = EnumBlockMirror.NONE;
            if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.SOUTH) {
                b0 = -7;
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.NORTH) {
                b0 = -7;
                b1 = 6;
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.EAST) {
                b0 = 1;
                b1 = 14;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.WEST) {
                b0 = 7;
                b1 = 14;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.WEST) {
                b0 = 7;
                b1 = -8;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90);
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.EAST) {
                b0 = 1;
                b1 = -8;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90);
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.NORTH) {
                b0 = 15;
                b1 = 6;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180);
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.SOUTH) {
                b0 = 15;
                enumblockmirror = EnumBlockMirror.FRONT_BACK;
            }

            BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), b0);
            blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), b1);
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.d(this.b), blockposition1, enumblockrotation1, enumblockmirror));
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces$b) {
            BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
            list.add(new WorldGenWoodlandMansionPieces.i(this.a, worldgenwoodlandmansionpieces$b.e(this.b), blockposition1, enumblockrotation, EnumBlockMirror.NONE));
        }
    }

    static class e {
        public EnumBlockRotation a;
        public BlockPosition b;
        public String c;

        private e() {
        }
    }

    static class f extends WorldGenWoodlandMansionPieces.b {
        private f() {
        }

        public String a(Random random) {
            return "1x1_b" + (random.nextInt(4) + 1);
        }

        public String b(Random random) {
            return "1x1_as" + (random.nextInt(4) + 1);
        }

        public String a(Random random, boolean flag) {
            return flag ? "1x2_c_stairs" : "1x2_c" + (random.nextInt(4) + 1);
        }

        public String b(Random random, boolean flag) {
            return flag ? "1x2_d_stairs" : "1x2_d" + (random.nextInt(5) + 1);
        }

        public String c(Random random) {
            return "1x2_se" + (random.nextInt(1) + 1);
        }

        public String d(Random random) {
            return "2x2_b" + (random.nextInt(5) + 1);
        }

        public String e(Random var1) {
            return "2x2_s1";
        }
    }

    static class g {
        private final int[][] a;
        private final int b;
        private final int c;
        private final int d;

        public g(int i, int j, int k) {
            this.b = i;
            this.c = j;
            this.d = k;
            this.a = new int[i][j];
        }

        public void a(int i, int j, int k) {
            if (i >= 0 && i < this.b && j >= 0 && j < this.c) {
                this.a[i][j] = k;
            }

        }

        public void a(int i, int j, int k, int l, int i1) {
            for(int j1 = j; j1 <= l; ++j1) {
                for(int k1 = i; k1 <= k; ++k1) {
                    this.a(k1, j1, i1);
                }
            }

        }

        public int a(int i, int j) {
            return i >= 0 && i < this.b && j >= 0 && j < this.c ? this.a[i][j] : this.d;
        }

        public void a(int i, int j, int k, int l) {
            if (this.a(i, j) == k) {
                this.a(i, j, l);
            }

        }

        public boolean b(int i, int j, int k) {
            return this.a(i - 1, j) == k || this.a(i + 1, j) == k || this.a(i, j + 1) == k || this.a(i, j - 1) == k;
        }
    }

    static class h extends WorldGenWoodlandMansionPieces.f {
        private h() {
        }
    }

    public static class i extends DefinedStructurePiece {
        private String d;
        private EnumBlockRotation e;
        private EnumBlockMirror f;

        public i() {
        }

        public i(DefinedStructureManager definedstructuremanager, String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation) {
            this(definedstructuremanager, s, blockposition, enumblockrotation, EnumBlockMirror.NONE);
        }

        public i(DefinedStructureManager definedstructuremanager, String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumBlockMirror enumblockmirror) {
            super(0);
            this.d = s;
            this.c = blockposition;
            this.e = enumblockrotation;
            this.f = enumblockmirror;
            this.a(definedstructuremanager);
        }

        private void a(DefinedStructureManager definedstructuremanager) {
            DefinedStructure definedstructure = definedstructuremanager.a(new MinecraftKey("woodland_mansion/" + this.d));
            DefinedStructureInfo definedstructureinfo = (new DefinedStructureInfo()).a(true).a(this.e).a(this.f);
            this.a(definedstructure, this.c, definedstructureinfo);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setString("Template", this.d);
            nbttagcompound.setString("Rot", this.b.c().name());
            nbttagcompound.setString("Mi", this.b.b().name());
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.d = nbttagcompound.getString("Template");
            this.e = EnumBlockRotation.valueOf(nbttagcompound.getString("Rot"));
            this.f = EnumBlockMirror.valueOf(nbttagcompound.getString("Mi"));
            this.a(definedstructuremanager);
        }

        protected void a(String s, BlockPosition blockposition, GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox) {
            if (s.startsWith("Chest")) {
                EnumBlockRotation enumblockrotation = this.b.c();
                IBlockData iblockdata = Blocks.CHEST.getBlockData();
                if ("ChestWest".equals(s)) {
                    iblockdata = (IBlockData)iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.WEST));
                } else if ("ChestEast".equals(s)) {
                    iblockdata = (IBlockData)iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.EAST));
                } else if ("ChestSouth".equals(s)) {
                    iblockdata = (IBlockData)iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.SOUTH));
                } else if ("ChestNorth".equals(s)) {
                    iblockdata = (IBlockData)iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.NORTH));
                }

                this.a(generatoraccess, structureboundingbox, random, blockposition, LootTables.o, iblockdata);
            } else if ("Mage".equals(s)) {
                EntityEvoker entityevoker = new EntityEvoker(generatoraccess.getMinecraftWorld());
                entityevoker.di();
                entityevoker.setPositionRotation(blockposition, 0.0F, 0.0F);
                generatoraccess.addEntity(entityevoker);
                generatoraccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
            } else if ("Warrior".equals(s)) {
                EntityVindicator entityvindicator = new EntityVindicator(generatoraccess.getMinecraftWorld());
                entityvindicator.di();
                entityvindicator.setPositionRotation(blockposition, 0.0F, 0.0F);
                entityvindicator.prepare(generatoraccess.getDamageScaler(new BlockPosition(entityvindicator)), (GroupDataEntity)null, (NBTTagCompound)null);
                generatoraccess.addEntity(entityvindicator);
                generatoraccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
            }

        }
    }
}
