package com.shibam.swapicacheengine.cache;



import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SwapiCache {
    private final Map<String, Object> cacheMap;

    public SwapiCache() {
        this.cacheMap = new ConcurrentHashMap<>();
    }

    public void put(String key, Object value) {
        cacheMap.put(key, value);
    }

    public Object get(String key) {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }

    public void evict(String key) {
        cacheMap.remove(key);
    }

    public void clear() {
        cacheMap.clear();
    }
}