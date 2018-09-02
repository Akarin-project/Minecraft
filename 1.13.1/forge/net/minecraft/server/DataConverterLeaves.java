package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.annotation.Nullable;

public class DataConverterLeaves extends DataFix {
    private static final int[][] a = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
    private static final Object2IntMap<String> b = (Object2IntMap)DataFixUtils.make(new Object2IntOpenHashMap(), (object2intopenhashmap) -> {
        object2intopenhashmap.put("minecraft:acacia_leaves", 0);
        object2intopenhashmap.put("minecraft:birch_leaves", 1);
        object2intopenhashmap.put("minecraft:dark_oak_leaves", 2);
        object2intopenhashmap.put("minecraft:jungle_leaves", 3);
        object2intopenhashmap.put("minecraft:oak_leaves", 4);
        object2intopenhashmap.put("minecraft:spruce_leaves", 5);
    });
    private static final Set<String> c = ImmutableSet.of("minecraft:acacia_bark", "minecraft:birch_bark", "minecraft:dark_oak_bark", "minecraft:jungle_bark", "minecraft:oak_bark", "minecraft:spruce_bark", new String[]{"minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log"});

    public DataConverterLeaves(Schema schema, boolean flag) {
        super(schema, flag);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.c);
        OpticFinder opticfinder = type.findField("Level");
        OpticFinder opticfinder1 = opticfinder.type().findField("Sections");
        Type type1 = opticfinder1.type();
        if (!(type1 instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        } else {
            Type type2 = ((ListType)type1).getElement();
            OpticFinder opticfinder2 = DSL.typeFinder(type2);
            return this.fixTypeEverywhereTyped("Leaves fix", type, (typed) -> {
                return typed.updateTyped(opticfinder, (typed1) -> {
                    int[] aint = new int[]{0};
                    Typed typed2 = typed1.updateTyped(opticfinder1, (typed3) -> {
                        Int2ObjectOpenHashMap int2objectopenhashmap = new Int2ObjectOpenHashMap((Map)typed3.getAllTyped(opticfinder2).stream().map((typed4) -> {
                            return new DataConverterLeaves.a(typed4, this.getInputSchema());
                        }).collect(Collectors.toMap(DataConverterLeaves.b::c, (dataconverterleaves$a2) -> {
                            return dataconverterleaves$a2;
                        })));
                        if (int2objectopenhashmap.values().stream().allMatch(DataConverterLeaves.b::b)) {
                            return typed3;
                        } else {
                            ArrayList arraylist = Lists.newArrayList();

                            for(int i = 0; i < 7; ++i) {
                                arraylist.add(new IntOpenHashSet());
                            }

                            ObjectIterator objectiterator = int2objectopenhashmap.values().iterator();

                            while(objectiterator.hasNext()) {
                                DataConverterLeaves.a dataconverterleaves$a = (DataConverterLeaves.a)objectiterator.next();
                                if (!dataconverterleaves$a.b()) {
                                    for(int j = 0; j < 4096; ++j) {
                                        int k = dataconverterleaves$a.c(j);
                                        if (dataconverterleaves$a.a(k)) {
                                            ((IntSet)arraylist.get(0)).add(dataconverterleaves$a.c() << 12 | j);
                                        } else if (dataconverterleaves$a.b(k)) {
                                            int l = this.a(j);
                                            int i1 = this.c(j);
                                            aint[0] |= a(l == 0, l == 15, i1 == 0, i1 == 15);
                                        }
                                    }
                                }
                            }

                            for(int j3 = 1; j3 < 7; ++j3) {
                                IntSet intset = (IntSet)arraylist.get(j3 - 1);
                                IntSet intset1 = (IntSet)arraylist.get(j3);
                                IntIterator intiterator = intset.iterator();

                                while(intiterator.hasNext()) {
                                    int k3 = intiterator.nextInt();
                                    int l3 = this.a(k3);
                                    int j1 = this.b(k3);
                                    int k1 = this.c(k3);

                                    for(int[] aint2 : a) {
                                        int l1 = l3 + aint2[0];
                                        int i2 = j1 + aint2[1];
                                        int j2 = k1 + aint2[2];
                                        if (l1 >= 0 && l1 <= 15 && j2 >= 0 && j2 <= 15 && i2 >= 0 && i2 <= 255) {
                                            DataConverterLeaves.a dataconverterleaves$a1 = (DataConverterLeaves.a)int2objectopenhashmap.get(i2 >> 4);
                                            if (dataconverterleaves$a1 != null && !dataconverterleaves$a1.b()) {
                                                int k2 = a(l1, i2 & 15, j2);
                                                int l2 = dataconverterleaves$a1.c(k2);
                                                if (dataconverterleaves$a1.b(l2)) {
                                                    int i3 = dataconverterleaves$a1.d(l2);
                                                    if (i3 > j3) {
                                                        dataconverterleaves$a1.a(k2, l2, j3);
                                                        intset1.add(a(l1, i2, j2));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            return typed3.updateTyped(opticfinder2, (typed4) -> {
                                return ((DataConverterLeaves.a)int2objectopenhashmap.get(((Dynamic)typed4.get(DSL.remainderFinder())).getInt("Y"))).a(typed4);
                            });
                        }
                    });
                    if (aint[0] != 0) {
                        typed2 = typed2.update(DSL.remainderFinder(), (dynamic) -> {
                            Dynamic dynamic1 = (Dynamic)DataFixUtils.orElse(dynamic.get("UpgradeData"), dynamic.emptyMap());
                            return dynamic.set("UpgradeData", dynamic1.set("Sides", dynamic.createByte((byte)(dynamic1.getByte("Sides") | aint[0]))));
                        });
                    }

                    return typed2;
                });
            });
        }
    }

    public static int a(int i, int j, int k) {
        return j << 8 | k << 4 | i;
    }

    private int a(int i) {
        return i & 15;
    }

    private int b(int i) {
        return i >> 8 & 255;
    }

    private int c(int i) {
        return i >> 4 & 15;
    }

    public static int a(boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        int i = 0;
        if (flag2) {
            if (flag1) {
                i |= 2;
            } else if (flag) {
                i |= 128;
            } else {
                i |= 1;
            }
        } else if (flag3) {
            if (flag) {
                i |= 32;
            } else if (flag1) {
                i |= 8;
            } else {
                i |= 16;
            }
        } else if (flag1) {
            i |= 4;
        } else if (flag) {
            i |= 64;
        }

        return i;
    }

    public static final class a extends DataConverterLeaves.b {
        @Nullable
        private IntSet f;
        @Nullable
        private IntSet g;
        @Nullable
        private Int2IntMap h;

        public a(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        protected boolean a() {
            this.f = new IntOpenHashSet();
            this.g = new IntOpenHashSet();
            this.h = new Int2IntOpenHashMap();

            for(int i = 0; i < this.c.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.c.get(i);
                String s = dynamic.getString("Name");
                if (DataConverterLeaves.b.containsKey(s)) {
                    boolean flag = Objects.equals(dynamic.get("Properties").flatMap((dynamic1) -> {
                        return dynamic1.get("decayable");
                    }).flatMap(Dynamic::getStringValue).orElse(""), "false");
                    this.f.add(i);
                    this.h.put(this.a(s, flag, 7), i);
                    this.c.set(i, this.a(dynamic, s, flag, 7));
                }

                if (DataConverterLeaves.c.contains(s)) {
                    this.g.add(i);
                }
            }

            return this.f.isEmpty() && this.g.isEmpty();
        }

        private Dynamic<?> a(Dynamic<?> dynamic, String s, boolean flag, int i) {
            Dynamic dynamic1 = dynamic.emptyMap();
            dynamic1 = dynamic1.set("persistent", dynamic1.createString(flag ? "true" : "false"));
            dynamic1 = dynamic1.set("distance", dynamic1.createString(Integer.toString(i)));
            Dynamic dynamic2 = dynamic.emptyMap();
            dynamic2 = dynamic2.set("Properties", dynamic1);
            dynamic2 = dynamic2.set("Name", dynamic2.createString(s));
            return dynamic2;
        }

        public boolean a(int i) {
            return this.g.contains(i);
        }

        public boolean b(int i) {
            return this.f.contains(i);
        }

        private int d(int i) {
            return this.a(i) ? 0 : Integer.parseInt((String)((Dynamic)this.c.get(i)).get("Properties").flatMap((dynamic) -> {
                return dynamic.get("distance");
            }).flatMap(Dynamic::getStringValue).orElse(""));
        }

        private void a(int i, int j, int k) {
            Dynamic dynamic = (Dynamic)this.c.get(j);
            String s = dynamic.getString("Name");
            boolean flag = Objects.equals(dynamic.get("Properties").flatMap((dynamic1) -> {
                return dynamic1.get("persistent");
            }).flatMap(Dynamic::getStringValue).orElse(""), "true");
            int l = this.a(s, flag, k);
            if (!this.h.containsKey(l)) {
                int i1 = this.c.size();
                this.f.add(i1);
                this.h.put(l, i1);
                this.c.add(this.a(dynamic, s, flag, k));
            }

            int k1 = this.h.get(l);
            if (1 << this.e.c() <= k1) {
                DataBits databits = new DataBits(this.e.c() + 1, 4096);

                for(int j1 = 0; j1 < 4096; ++j1) {
                    databits.a(j1, this.e.a(j1));
                }

                this.e = databits;
            }

            this.e.a(i, k1);
        }
    }

    public abstract static class b {
        final Type<Pair<String, Dynamic<?>>> a = DSL.named(DataConverterTypes.l.typeName(), DSL.remainderType());
        protected final OpticFinder<List<Pair<String, Dynamic<?>>>> b;
        protected final List<Dynamic<?>> c;
        protected final int d;
        @Nullable
        protected DataBits e;

        public b(Typed<?> typed, Schema schema) {
            this.b = DSL.fieldFinder("Palette", DSL.list(this.a));
            if (!Objects.equals(schema.getType(DataConverterTypes.l), this.a)) {
                throw new IllegalStateException("Block state type is not what was expected.");
            } else {
                Optional optional = typed.getOptional(this.b);
                this.c = (List)optional.map((list) -> {
                    return (List)list.stream().map(Pair::getSecond).collect(Collectors.toList());
                }).orElse(ImmutableList.of());
                Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
                this.d = dynamic.getInt("Y");
                this.a(dynamic);
            }
        }

        protected void a(Dynamic<?> dynamic) {
            if (this.a()) {
                this.e = null;
            } else {
                long[] along = ((LongStream)dynamic.get("BlockStates").flatMap(Dynamic::getLongStream).get()).toArray();
                int i = Math.max(4, DataFixUtils.ceillog2(this.c.size()));
                this.e = new DataBits(i, 4096, along);
            }

        }

        public Typed<?> a(Typed<?> typed) {
            return this.b() ? typed : typed.update(DSL.remainderFinder(), (dynamic) -> {
                return dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(this.e.a())));
            }).set(this.b, this.c.stream().map((dynamic) -> {
                return Pair.of(DataConverterTypes.l.typeName(), dynamic);
            }).collect(Collectors.toList()));
        }

        public boolean b() {
            return this.e == null;
        }

        public int c(int i) {
            return this.e.a(i);
        }

        protected int a(String s, boolean flag, int i) {
            return DataConverterLeaves.b.get(s) << 5 | (flag ? 16 : 0) | i;
        }

        int c() {
            return this.d;
        }

        protected abstract boolean a();
    }
}
