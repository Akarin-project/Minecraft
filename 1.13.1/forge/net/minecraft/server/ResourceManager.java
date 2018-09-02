package net.minecraft.server;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceManager implements IReloadableResourceManager {
    private static final Logger a = LogManager.getLogger();
    private final Map<String, ResourceManagerFallback> b = Maps.newHashMap();
    private final List<IResourcePackListener> c = Lists.newArrayList();
    private final Set<String> d = Sets.newLinkedHashSet();
    private final EnumResourcePackType e;

    public ResourceManager(EnumResourcePackType enumresourcepacktype) {
        this.e = enumresourcepacktype;
    }

    public void a(IResourcePack iresourcepack) {
        for(String s : iresourcepack.a(this.e)) {
            this.d.add(s);
            ResourceManagerFallback resourcemanagerfallback = (ResourceManagerFallback)this.b.get(s);
            if (resourcemanagerfallback == null) {
                resourcemanagerfallback = new ResourceManagerFallback(this.e);
                this.b.put(s, resourcemanagerfallback);
            }

            resourcemanagerfallback.a(iresourcepack);
        }

    }

    public IResource a(MinecraftKey minecraftkey) throws IOException {
        IResourceManager iresourcemanager = (IResourceManager)this.b.get(minecraftkey.b());
        if (iresourcemanager != null) {
            return iresourcemanager.a(minecraftkey);
        } else {
            throw new FileNotFoundException(minecraftkey.toString());
        }
    }

    public List<IResource> b(MinecraftKey minecraftkey) throws IOException {
        IResourceManager iresourcemanager = (IResourceManager)this.b.get(minecraftkey.b());
        if (iresourcemanager != null) {
            return iresourcemanager.b(minecraftkey);
        } else {
            throw new FileNotFoundException(minecraftkey.toString());
        }
    }

    public Collection<MinecraftKey> a(String s, Predicate<String> predicate) {
        HashSet hashset = Sets.newHashSet();

        for(ResourceManagerFallback resourcemanagerfallback : this.b.values()) {
            hashset.addAll(resourcemanagerfallback.a(s, predicate));
        }

        ArrayList arraylist = Lists.newArrayList(hashset);
        Collections.sort(arraylist);
        return arraylist;
    }

    private void b() {
        this.b.clear();
        this.d.clear();
    }

    public void a(List<IResourcePack> list) {
        this.b();
        a.info("Reloading ResourceManager: {}", list.stream().map(IResourcePack::a).collect(Collectors.joining(", ")));

        for(IResourcePack iresourcepack : list) {
            this.a(iresourcepack);
        }

        if (a.isDebugEnabled()) {
            this.d();
        } else {
            this.c();
        }

    }

    public void a(IResourcePackListener iresourcepacklistener) {
        this.c.add(iresourcepacklistener);
        if (a.isDebugEnabled()) {
            a.info(this.b(iresourcepacklistener));
        } else {
            iresourcepacklistener.a(this);
        }

    }

    private void c() {
        for(IResourcePackListener iresourcepacklistener : this.c) {
            iresourcepacklistener.a(this);
        }

    }

    private void d() {
        a.info("Reloading all resources! {} listeners to update.", this.c.size());
        ArrayList arraylist = Lists.newArrayList();
        Stopwatch stopwatch = Stopwatch.createStarted();

        for(IResourcePackListener iresourcepacklistener : this.c) {
            arraylist.add(this.b(iresourcepacklistener));
        }

        stopwatch.stop();
        a.info("----");
        a.info("Complete resource reload took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        for(String s : arraylist) {
            a.info(s);
        }

        a.info("----");
    }

    private String b(IResourcePackListener iresourcepacklistener) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        iresourcepacklistener.a(this);
        stopwatch.stop();
        return "Resource reload for " + iresourcepacklistener.getClass().getSimpleName() + " took " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms";
    }
}
