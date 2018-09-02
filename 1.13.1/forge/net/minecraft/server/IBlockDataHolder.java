package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;

public interface IBlockDataHolder<C> {
    Collection<IBlockState<?>> a();

    <T extends Comparable<T>> boolean b(IBlockState<T> var1);

    <T extends Comparable<T>> T get(IBlockState<T> var1);

    <T extends Comparable<T>, V extends T> C set(IBlockState<T> var1, V var2);

    <T extends Comparable<T>> C a(IBlockState<T> var1);

    ImmutableMap<IBlockState<?>, Comparable<?>> b();
}
