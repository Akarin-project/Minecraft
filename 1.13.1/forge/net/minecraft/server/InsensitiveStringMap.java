package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class InsensitiveStringMap<V> implements Map<String, V> {
    private final Map<String, V> a = Maps.newLinkedHashMap();

    public InsensitiveStringMap() {
    }

    public int size() {
        return this.a.size();
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean containsKey(Object object) {
        return this.a.containsKey(object.toString().toLowerCase(Locale.ROOT));
    }

    public boolean containsValue(Object object) {
        return this.a.containsValue(object);
    }

    public V get(Object object) {
        return (V)this.a.get(object.toString().toLowerCase(Locale.ROOT));
    }

    public V a(String s, V object) {
        return (V)this.a.put(s.toLowerCase(Locale.ROOT), object);
    }

    public V remove(Object object) {
        return (V)this.a.remove(object.toString().toLowerCase(Locale.ROOT));
    }

    public void putAll(Map<? extends String, ? extends V> map) {
        for(Entry entry : map.entrySet()) {
            this.a((String)entry.getKey(), entry.getValue());
        }

    }

    public void clear() {
        this.a.clear();
    }

    public Set<String> keySet() {
        return this.a.keySet();
    }

    public Collection<V> values() {
        return this.a.values();
    }

    public Set<Entry<String, V>> entrySet() {
        return this.a.entrySet();
    }

    // $FF: synthetic method
    public Object put(Object object, Object object1) {
        return this.a((String)object, object1);
    }
}
