package org.felix.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.felix.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class RedisCache<K, V> implements Cache {

    private Logger log = LoggerFactory.getLogger(RedisCache.class);

    public static final String PREFIX = "shiro-cache:";

    private String cacheKey;

    private long expire = 30;

    private RedisService redisService;

    public RedisCache(String name, RedisService redisService) {

    }

    @Override
    public Object get(Object o) throws CacheException {
        return null;
    }

    @Override
    public Object put(Object o, Object o2) throws CacheException {
        return null;
    }

    @Override
    public Object remove(Object o) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
