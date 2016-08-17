package com.yuelvic.rdroid.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by yuelvic on 8/17/16.
 */
public class RestResult extends HashMap<String, Object> {

    public RestResult(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public RestResult(int initialCapacity) {
        super(initialCapacity);
    }

    public RestResult() {
        super();
    }

    public RestResult(Map<? extends String, ?> m) {
        super(m);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        super.putAll(m);
    }

    @Override
    public Object remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<Object> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return super.entrySet();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        super.forEach(action);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        super.replaceAll(function);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return super.replace(key, oldValue, newValue);
    }
}
