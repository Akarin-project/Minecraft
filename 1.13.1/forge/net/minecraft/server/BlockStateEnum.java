package net.minecraft.server;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BlockStateEnum<T extends Enum<T> & INamable> extends BlockState<T> {
    private final ImmutableSet<T> a;
    private final Map<String, T> b = Maps.newHashMap();

    protected BlockStateEnum(String s, Class<T> oclass, Collection<T> collection) {
        super(s, oclass);
        this.a = ImmutableSet.copyOf(collection);

        for(Enum oenum : collection) {
            String s1 = ((INamable)oenum).getName();
            if (this.b.containsKey(s1)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + s1 + "'");
            }

            this.b.put(s1, oenum);
        }

    }

    public Collection<T> d() {
        return this.a;
    }

    public Optional<T> b(String s) {
        return Optional.ofNullable(this.b.get(s));
    }

    public String a(T oenum) {
        return ((INamable)oenum).getName();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof BlockStateEnum && super.equals(object)) {
            BlockStateEnum blockstateenum1 = (BlockStateEnum)object;
            return this.a.equals(blockstateenum1.a) && this.b.equals(blockstateenum1.b);
        } else {
            return false;
        }
    }

    public int c() {
        int i = super.c();
        i = 31 * i + this.a.hashCode();
        i = 31 * i + this.b.hashCode();
        return i;
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> of(String s, Class<T> oclass) {
        return a(s, oclass, Predicates.alwaysTrue());
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String s, Class<T> oclass, Predicate<T> predicate) {
        return a(s, oclass, (Collection)Arrays.stream(oclass.getEnumConstants()).filter(predicate).collect(Collectors.toList()));
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> of(String s, Class<T> oclass, T... aenum) {
        return a(s, oclass, Lists.newArrayList(aenum));
    }

    public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String s, Class<T> oclass, Collection<T> collection) {
        return new BlockStateEnum<T>(s, oclass, collection);
    }
}
