package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttributeMapServer extends AttributeMapBase {
    private final Set<AttributeInstance> e = Sets.newHashSet();
    protected final Map<String, AttributeInstance> d = new InsensitiveStringMap();

    public AttributeMapServer() {
    }

    public AttributeModifiable e(IAttribute iattribute) {
        return (AttributeModifiable)super.a(iattribute);
    }

    public AttributeModifiable b(String s) {
        AttributeInstance attributeinstance = super.a(s);
        if (attributeinstance == null) {
            attributeinstance = (AttributeInstance)this.d.get(s);
        }

        return (AttributeModifiable)attributeinstance;
    }

    public AttributeInstance b(IAttribute iattribute) {
        AttributeInstance attributeinstance = super.b(iattribute);
        if (iattribute instanceof AttributeRanged && ((AttributeRanged)iattribute).g() != null) {
            this.d.put(((AttributeRanged)iattribute).g(), attributeinstance);
        }

        return attributeinstance;
    }

    protected AttributeInstance c(IAttribute iattribute) {
        return new AttributeModifiable(this, iattribute);
    }

    public void a(AttributeInstance attributeinstance) {
        if (attributeinstance.getAttribute().c()) {
            this.e.add(attributeinstance);
        }

        for(IAttribute iattribute : this.c.get(attributeinstance.getAttribute())) {
            AttributeModifiable attributemodifiable = this.e(iattribute);
            if (attributemodifiable != null) {
                attributemodifiable.f();
            }
        }

    }

    public Set<AttributeInstance> getAttributes() {
        return this.e;
    }

    public Collection<AttributeInstance> c() {
        HashSet hashset = Sets.newHashSet();

        for(AttributeInstance attributeinstance : this.a()) {
            if (attributeinstance.getAttribute().c()) {
                hashset.add(attributeinstance);
            }
        }

        return hashset;
    }

    // $FF: synthetic method
    public AttributeInstance a(String s) {
        return this.b(s);
    }

    // $FF: synthetic method
    public AttributeInstance a(IAttribute iattribute) {
        return this.e(iattribute);
    }
}
