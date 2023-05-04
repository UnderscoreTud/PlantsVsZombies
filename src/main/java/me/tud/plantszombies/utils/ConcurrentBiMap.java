package me.tud.plantszombies.utils;

import com.google.common.collect.BiMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcurrentBiMap<K, V> implements BiMap<K, V>, Serializable {

    protected final Map<K, V> forward;
    protected final Map<V, K> backward;
    private transient ConcurrentBiMap<V, K> inverse;

    public ConcurrentBiMap() {
        this(new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }

    @Override
    public int size() {
        return forward.size();
    }

    @Override
    public boolean isEmpty() {
        return forward.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return forward.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return forward.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return forward.get(key);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        K oldKey = backward.get(value);
        if (oldKey != null && !key.equals(oldKey))
            throw new IllegalArgumentException(value + " is already bound in backward to " + oldKey);
        V oldVal = forward.put(key, value);
        if (oldVal != null && !Objects.equals(backward.remove(oldVal), key))
            throw new IllegalStateException();
        backward.put(value, key);
        return oldVal;
    }

    @Override
    public V remove(Object key) {
        V oldVal = forward.remove(key);
        if (oldVal == null)
            return null;
        Object oldKey = backward.remove(oldVal);
        if (oldKey == null || !oldKey.equals(key))
            throw new IllegalStateException();
        return oldVal;
    }

    @Nullable
    @Override
    public V forcePut(K key, V value) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public void clear() {
        forward.clear();
        backward.clear();
    }

    @Override
    public @NotNull Set<K> keySet() {
        return forward.keySet();
    }

    @Override
    public Set<V> values() {
        return inverse().keySet();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return forward.entrySet();
    }

    @Override
    public @NotNull BiMap<V, K> inverse() {
        if (inverse == null)
            inverse = new ConcurrentBiMap<>(backward, forward);
        return inverse;
    }

    @Override
    public String toString() {
        return forward.toString();
    }

}
