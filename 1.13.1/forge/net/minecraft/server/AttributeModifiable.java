package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class AttributeModifiable implements AttributeInstance {
    private final AttributeMapBase a;
    private final IAttribute b;
    private final Map<Integer, Set<AttributeModifier>> c = Maps.newHashMap();
    private final Map<String, Set<AttributeModifier>> d = Maps.newHashMap();
    private final Map<UUID, AttributeModifier> e = Maps.newHashMap();
    private double f;
    private boolean g = true;
    private double h;

    public AttributeModifiable(AttributeMapBase attributemapbase, IAttribute iattribute) {
        this.a = attributemapbase;
        this.b = iattribute;
        this.f = iattribute.getDefault();

        for(int i = 0; i < 3; ++i) {
            this.c.put(i, Sets.newHashSet());
        }

    }

    public IAttribute getAttribute() {
        return this.b;
    }

    public double b() {
        return this.f;
    }

    public void setValue(double d0) {
        if (d0 != this.b()) {
            this.f = d0;
            this.f();
        }
    }

    public Collection<AttributeModifier> a(int i) {
        return (Collection)this.c.get(i);
    }

    public Collection<AttributeModifier> c() {
        HashSet hashset = Sets.newHashSet();

        for(int i = 0; i < 3; ++i) {
            hashset.addAll(this.a(i));
        }

        return hashset;
    }

    @Nullable
    public AttributeModifier a(UUID uuid) {
        return (AttributeModifier)this.e.get(uuid);
    }

    public boolean a(AttributeModifier attributemodifier) {
        return this.e.get(attributemodifier.a()) != null;
    }

    public void b(AttributeModifier attributemodifier) {
        if (this.a(attributemodifier.a()) != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        } else {
            Object object = (Set)this.d.get(attributemodifier.b());
            if (object == null) {
                object = Sets.newHashSet();
                this.d.put(attributemodifier.b(), object);
            }

            ((Set)this.c.get(attributemodifier.c())).add(attributemodifier);
            ((Set)object).add(attributemodifier);
            this.e.put(attributemodifier.a(), attributemodifier);
            this.f();
        }
    }

    protected void f() {
        this.g = true;
        this.a.a(this);
    }

    public void c(AttributeModifier attributemodifier) {
        for(int i = 0; i < 3; ++i) {
            Set set = (Set)this.c.get(i);
            set.remove(attributemodifier);
        }

        Set set1 = (Set)this.d.get(attributemodifier.b());
        if (set1 != null) {
            set1.remove(attributemodifier);
            if (set1.isEmpty()) {
                this.d.remove(attributemodifier.b());
            }
        }

        this.e.remove(attributemodifier.a());
        this.f();
    }

    public void b(UUID uuid) {
        AttributeModifier attributemodifier = this.a(uuid);
        if (attributemodifier != null) {
            this.c(attributemodifier);
        }

    }

    public double getValue() {
        if (this.g) {
            this.h = this.g();
            this.g = false;
        }

        return this.h;
    }

    private double g() {
        double d0 = this.b();

        for(AttributeModifier attributemodifier : this.b(0)) {
            d0 += attributemodifier.d();
        }

        double d1 = d0;

        for(AttributeModifier attributemodifier1 : this.b(1)) {
            d1 += d0 * attributemodifier1.d();
        }

        for(AttributeModifier attributemodifier2 : this.b(2)) {
            d1 *= 1.0D + attributemodifier2.d();
        }

        return this.b.a(d1);
    }

    private Collection<AttributeModifier> b(int i) {
        HashSet hashset = Sets.newHashSet(this.a(i));

        for(IAttribute iattribute = this.b.d(); iattribute != null; iattribute = iattribute.d()) {
            AttributeInstance attributeinstance = this.a.a(iattribute);
            if (attributeinstance != null) {
                hashset.addAll(attributeinstance.a(i));
            }
        }

        return hashset;
    }
}
