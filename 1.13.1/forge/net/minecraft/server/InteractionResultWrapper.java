package net.minecraft.server;

public class InteractionResultWrapper<T> {
    private final EnumInteractionResult a;
    private final T b;

    public InteractionResultWrapper(EnumInteractionResult enuminteractionresult, T object) {
        this.a = enuminteractionresult;
        this.b = (T)object;
    }

    public EnumInteractionResult a() {
        return this.a;
    }

    public T b() {
        return this.b;
    }
}
