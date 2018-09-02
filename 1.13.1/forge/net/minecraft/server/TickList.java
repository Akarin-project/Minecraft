package net.minecraft.server;

public interface TickList<T> {
    boolean a(BlockPosition var1, T var2);

    default void a(BlockPosition blockposition, T object, int i) {
        this.a(blockposition, object, i, TickListPriority.NORMAL);
    }

    void a(BlockPosition var1, T var2, int var3, TickListPriority var4);

    boolean b(BlockPosition var1, T var2);
}
