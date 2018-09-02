package net.minecraft.server;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ResourcePackRepository<T extends ResourcePackLoader> {
    private final Set<ResourcePackSource> a = Sets.newHashSet();
    private final Map<String, T> b = Maps.newLinkedHashMap();
    private final List<T> c = Lists.newLinkedList();
    private final ResourcePackLoader.b<T> d;

    public ResourcePackRepository(ResourcePackLoader.b<T> resourcepackloader$b) {
        this.d = resourcepackloader$b;
    }

    public void a() {
        Set set = (Set)this.c.stream().map(ResourcePackLoader::e).collect(Collectors.toCollection(LinkedHashSet::new));
        this.b.clear();
        this.c.clear();

        for(ResourcePackSource resourcepacksource : this.a) {
            resourcepacksource.a(this.b, this.d);
        }

        this.e();
        this.c.addAll((Collection)set.stream().map(this.b::get).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new)));

        for(ResourcePackLoader resourcepackloader : this.b.values()) {
            if (resourcepackloader.f() && !this.c.contains(resourcepackloader)) {
                resourcepackloader.h().a(this.c, resourcepackloader, Functions.identity(), false);
            }
        }

    }

    private void e() {
        ArrayList arraylist = Lists.newArrayList(this.b.entrySet());
        this.b.clear();
        arraylist.stream().sorted(Entry.comparingByKey()).forEachOrdered((entry) -> {
            ResourcePackLoader resourcepackloader = (ResourcePackLoader)this.b.put(entry.getKey(), entry.getValue());
        });
    }

    public void a(Collection<T> collection) {
        this.c.clear();
        this.c.addAll(collection);

        for(ResourcePackLoader resourcepackloader : this.b.values()) {
            if (resourcepackloader.f() && !this.c.contains(resourcepackloader)) {
                resourcepackloader.h().a(this.c, resourcepackloader, Functions.identity(), false);
            }
        }

    }

    public Collection<T> b() {
        return this.b.values();
    }

    public Collection<T> c() {
        ArrayList arraylist = Lists.newArrayList(this.b.values());
        arraylist.removeAll(this.c);
        return arraylist;
    }

    public Collection<T> d() {
        return this.c;
    }

    @Nullable
    public T a(String s) {
        return (T)(this.b.get(s));
    }

    public void a(ResourcePackSource resourcepacksource) {
        this.a.add(resourcepacksource);
    }
}
