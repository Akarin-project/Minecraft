package net.minecraft.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class BlockStateList<O, S extends IBlockDataHolder<S>> {
    private static final Pattern a = Pattern.compile("^[a-z0-9_]+$");
    private final O b;
    private final ImmutableSortedMap<String, IBlockState<?>> c;
    private final ImmutableList<S> d;

    protected <A extends BlockDataAbstract<O, S>> BlockStateList(O object, BlockStateList.b<O, S, A> blockstatelist$b, Map<String, IBlockState<?>> map) {
        this.b = (O)object;
        this.c = ImmutableSortedMap.copyOf(map);
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
        ArrayList arraylist = Lists.newArrayList();
        Stream stream = Stream.of(Collections.emptyList());

        IBlockState iblockstate;
        for(UnmodifiableIterator unmodifiableiterator = this.c.values().iterator(); unmodifiableiterator.hasNext(); stream = stream.flatMap((list) -> {
            return iblockstate.d().stream().map((comparable) -> {
                ArrayList arraylist1 = Lists.newArrayList(list);
                arraylist1.add(comparable);
                return arraylist1;
            });
        })) {
            iblockstate = (IBlockState)unmodifiableiterator.next();
        }

        stream.forEach((list1) -> {
            Map map2 = MapGeneratorUtils.b(this.c.values(), list1);
            BlockDataAbstract blockdataabstract1 = blockstatelist$b.create(object, ImmutableMap.copyOf(map2));
            linkedhashmap.put(map2, blockdataabstract1);
            arraylist.add(blockdataabstract1);
        });

        for(BlockDataAbstract blockdataabstract : arraylist) {
            blockdataabstract.a(linkedhashmap);
        }

        this.d = ImmutableList.copyOf(arraylist);
    }

    public ImmutableList<S> a() {
        return this.d;
    }

    public S getBlockData() {
        return (S)(this.d.get(0));
    }

    public O getBlock() {
        return this.b;
    }

    public Collection<IBlockState<?>> d() {
        return this.c.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("block", this.b).add("properties", this.c.values().stream().map(IBlockState::a).collect(Collectors.toList())).toString();
    }

    @Nullable
    public IBlockState<?> a(String s) {
        return (IBlockState)this.c.get(s);
    }

    public static class a<O, S extends IBlockDataHolder<S>> {
        private final O a;
        private final Map<String, IBlockState<?>> b = Maps.newHashMap();

        public a(O object) {
            this.a = (O)object;
        }

        public BlockStateList.a<O, S> a(IBlockState<?>... aiblockstate) {
            for(IBlockState iblockstate : aiblockstate) {
                this.a(iblockstate);
                this.b.put(iblockstate.a(), iblockstate);
            }

            return this;
        }

        private <T extends Comparable<T>> void a(IBlockState<T> iblockstate) {
            String s = iblockstate.a();
            if (!BlockStateList.a.matcher(s).matches()) {
                throw new IllegalArgumentException(this.a + " has invalidly named property: " + s);
            } else {
                Collection collection = iblockstate.d();
                if (collection.size() <= 1) {
                    throw new IllegalArgumentException(this.a + " attempted use property " + s + " with <= 1 possible values");
                } else {
                    for(Comparable comparable : collection) {
                        String s1 = iblockstate.a(comparable);
                        if (!BlockStateList.a.matcher(s1).matches()) {
                            throw new IllegalArgumentException(this.a + " has property: " + s + " with invalidly named value: " + s1);
                        }
                    }

                    if (this.b.containsKey(s)) {
                        throw new IllegalArgumentException(this.a + " has duplicate property: " + s);
                    }
                }
            }
        }

        public <A extends BlockDataAbstract<O, S>> BlockStateList<O, S> a(BlockStateList.b<O, S, A> blockstatelist$b) {
            return new BlockStateList<O, S>(this.a, blockstatelist$b, this.b);
        }
    }

    public interface b<O, S extends IBlockDataHolder<S>, A extends BlockDataAbstract<O, S>> {
        A create(O var1, ImmutableMap<IBlockState<?>, Comparable<?>> var2);
    }
}
