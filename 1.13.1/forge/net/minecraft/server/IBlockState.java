package net.minecraft.server;

import java.util.Collection;
import java.util.Optional;

public interface IBlockState<T extends Comparable<T>> {
    String a();

    Collection<T> d();

    Class<T> b();

    Optional<T> b(String var1);

    String a(T var1);
}
