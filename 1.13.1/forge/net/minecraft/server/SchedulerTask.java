package net.minecraft.server;

import java.util.function.BiConsumer;
import javax.annotation.Nullable;

public interface SchedulerTask<K, T extends SchedulerTask<K, T>> {
    @Nullable
    T a();

    void a(K var1, BiConsumer<K, T> var2);
}
