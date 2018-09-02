package net.minecraft.server;

import java.util.function.Function;

public class TickListWorldGen<T> implements TickList<T> {
    private final Function<BlockPosition, TickList<T>> a;

    public TickListWorldGen(Function<BlockPosition, TickList<T>> function) {
        this.a = function;
    }

    public boolean a(BlockPosition blockposition, T object) {
        return ((TickList)this.a.apply(blockposition)).a(blockposition, object);
    }

    public void a(BlockPosition blockposition, T object, int i, TickListPriority ticklistpriority) {
        ((TickList)this.a.apply(blockposition)).a(blockposition, object, i, ticklistpriority);
    }

    public boolean b(BlockPosition var1, T var2) {
        return false;
    }
}
