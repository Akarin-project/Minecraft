package net.minecraft.server;

public interface PredicateBlock<T> {
    boolean test(T var1, IBlockAccess var2, BlockPosition var3);
}
