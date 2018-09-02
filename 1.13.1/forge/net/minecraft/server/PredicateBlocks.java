package net.minecraft.server;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public final class PredicateBlocks {
    public static <T> PredicateBlock<T> a(PredicateBlock<T> predicateblock) {
        return new PredicateBlocks.b<T>(predicateblock);
    }

    public static <T> PredicateBlock<T> b(PredicateBlock<? super T>... apredicateblock) {
        return new PredicateBlocks.c<T>(a(apredicateblock));
    }

    private static <T> List<T> a(T... aobject) {
        return c(Arrays.asList(aobject));
    }

    private static <T> List<T> c(Iterable<T> iterable) {
        ArrayList arraylist = Lists.newArrayList();

        for(Object object : iterable) {
            arraylist.add(Preconditions.checkNotNull(object));
        }

        return arraylist;
    }

    static class b<T> implements PredicateBlock<T> {
        private final PredicateBlock<T> a;

        b(PredicateBlock<T> predicateblock) {
            this.a = (PredicateBlock)Preconditions.checkNotNull(predicateblock);
        }

        public boolean test(@Nullable T object, IBlockAccess iblockaccess, BlockPosition blockposition) {
            return !this.a.test((T)object, iblockaccess, blockposition);
        }
    }

    static class c<T> implements PredicateBlock<T> {
        private final List<? extends PredicateBlock<? super T>> a;

        private c(List<? extends PredicateBlock<? super T>> list) {
            this.a = list;
        }

        public boolean test(@Nullable T object, IBlockAccess iblockaccess, BlockPosition blockposition) {
            for(int i = 0; i < this.a.size(); ++i) {
                if (((PredicateBlock)this.a.get(i)).test(object, iblockaccess, blockposition)) {
                    return true;
                }
            }

            return false;
        }
    }
}
