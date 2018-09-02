package net.minecraft.server;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
    private final double a;
    private final int b;
    private final Supplier<String> c;
    private final UUID d;
    private boolean e;

    public AttributeModifier(String s, double d0, int i) {
        this(MathHelper.a((Random)ThreadLocalRandom.current()), () -> {
            return s;
        }, d0, i);
    }

    public AttributeModifier(UUID uuid, String s, double d0, int i) {
        this(uuid, () -> {
            return s;
        }, d0, i);
    }

    public AttributeModifier(UUID uuid, Supplier<String> supplier, double d0, int i) {
        this.e = true;
        this.d = uuid;
        this.c = supplier;
        this.a = d0;
        this.b = i;
        Validate.inclusiveBetween(0L, 2L, (long)i, "Invalid operation");
    }

    public UUID a() {
        return this.d;
    }

    public String b() {
        return (String)this.c.get();
    }

    public int c() {
        return this.b;
    }

    public double d() {
        return this.a;
    }

    public boolean e() {
        return this.e;
    }

    public AttributeModifier a(boolean flag) {
        this.e = flag;
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            AttributeModifier attributemodifier1 = (AttributeModifier)object;
            if (this.d != null) {
                if (!this.d.equals(attributemodifier1.d)) {
                    return false;
                }
            } else if (attributemodifier1.d != null) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.d != null ? this.d.hashCode() : 0;
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name='" + (String)this.c.get() + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
    }
}
