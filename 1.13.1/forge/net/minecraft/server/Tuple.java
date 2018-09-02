package net.minecraft.server;

public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple(A object, B object1) {
        this.a = (A)object;
        this.b = (B)object1;
    }

    public A a() {
        return this.a;
    }

    public B b() {
        return this.b;
    }
}
