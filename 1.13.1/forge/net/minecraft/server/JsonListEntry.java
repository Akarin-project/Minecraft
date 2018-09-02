package net.minecraft.server;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class JsonListEntry<T> {
    @Nullable
    private final T a;

    public JsonListEntry(T object) {
        this.a = (T)object;
    }

    protected JsonListEntry(@Nullable T object, JsonObject var2) {
        this.a = (T)object;
    }

    @Nullable
    public T getKey() {
        return this.a;
    }

    boolean hasExpired() {
        return false;
    }

    protected void a(JsonObject var1) {
    }
}
