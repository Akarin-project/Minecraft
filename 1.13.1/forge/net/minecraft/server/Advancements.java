package net.minecraft.server;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Advancements {
    private static final Logger a = LogManager.getLogger();
    public final Map<MinecraftKey, Advancement> advancements = Maps.newHashMap();
    private final Set<Advancement> c = Sets.newLinkedHashSet();
    private final Set<Advancement> d = Sets.newLinkedHashSet();
    private Advancements.a e;

    public Advancements() {
    }

    public void a(Map<MinecraftKey, Advancement.SerializedAdvancement> map) {
        Function function = Functions.forMap(this.advancements, (Object)null);

        while(!map.isEmpty()) {
            boolean flag = false;
            Iterator iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                Entry entry = (Entry)iterator.next();
                MinecraftKey minecraftkey = (MinecraftKey)entry.getKey();
                Advancement.SerializedAdvancement advancement$serializedadvancement = (Advancement.SerializedAdvancement)entry.getValue();
                if (advancement$serializedadvancement.a(function)) {
                    Advancement advancement = advancement$serializedadvancement.b(minecraftkey);
                    this.advancements.put(minecraftkey, advancement);
                    flag = true;
                    iterator.remove();
                    if (advancement.b() == null) {
                        this.c.add(advancement);
                        if (this.e != null) {
                            this.e.a(advancement);
                        }
                    } else {
                        this.d.add(advancement);
                        if (this.e != null) {
                            this.e.c(advancement);
                        }
                    }
                }
            }

            if (!flag) {
                for(Entry entry1 : map.entrySet()) {
                    a.error("Couldn't load advancement {}: {}", entry1.getKey(), entry1.getValue());
                }
                break;
            }
        }

        a.info("Loaded {} advancements", this.advancements.size());
    }

    public void a() {
        this.advancements.clear();
        this.c.clear();
        this.d.clear();
        if (this.e != null) {
            this.e.a();
        }

    }

    public Iterable<Advancement> b() {
        return this.c;
    }

    public Collection<Advancement> c() {
        return this.advancements.values();
    }

    @Nullable
    public Advancement a(MinecraftKey minecraftkey) {
        return (Advancement)this.advancements.get(minecraftkey);
    }

    public interface a {
        void a(Advancement var1);

        void c(Advancement var1);

        void a();
    }
}
