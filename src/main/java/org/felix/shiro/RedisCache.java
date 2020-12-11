package org.felix.shiro;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.felix.service.RedisService;
import org.felix.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private Logger log = LoggerFactory.getLogger(RedisCache.class);

    public static final String PREFIX = "shiro-cache:";

    private String cacheKey;

    private long expire = 30;

    private RedisService redisService;

    public RedisCache(String name, RedisService redisService) {
        this.redisService = redisService;
        this.cacheKey = PREFIX + name;
    }

    @Override
    public V get(K k) throws CacheException {

        log.debug("shiro get key from RedisCache[{}]", k);
        if (k == null) {
            return null;
        }

        String redisCacheKey = getRedisCacheKey(k);
        Object rawValue = redisService.get(redisCacheKey);
        redisService.delete(redisCacheKey);

        return (V) rawValue;
    }

    private String getRedisCacheKey(K k) {

        if (k == null) {
            return null;
        }

        return this.cacheKey + JwtTokenUtil.getUserId(k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {

        log.debug("put key [{}]", k);
        if (k == null) {
            log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return v;
        }
        try {

            String redisCacheKey = getRedisCacheKey(k);
            redisService.set(redisCacheKey, v, expire, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new CacheException(e);
        }

        return v;
    }

    @Override
    public V remove(K k) throws CacheException {

        log.debug("remove key[{}]", k);
        if (k == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(k);
            Object rawValue = redisService.get(redisCacheKey);
            redisService.delete(redisCacheKey);
            return (V) rawValue;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void clear() throws CacheException {

        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = redisService.keys(this.cacheKey + "*");
        } catch (Exception e) {
            throw new CacheException(e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        keys.forEach(key -> {
            redisService.delete(key);
        });

    }

    @Override
    public int size() {

        int size = 0;
        try {
            size = redisService.keys(this.cacheKey + "*").size();
        } catch (Exception e) {
            log.error("get keys size error", e);
        }

        return size;
    }

    @Override
    public Set<K> keys() {

        Set<String> keys = null;

        try {
            keys = redisService.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
            return Collections.EMPTY_SET;
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.EMPTY_SET;
        }
        Set<K> convertedKeys = new HashSet<>();
        try {

            keys.forEach(key -> {
                convertedKeys.add((K) key);
            });
        } catch (Exception e) {
            log.error("deserialize keys error:", e);
        }

        return convertedKeys;
    }

    @Override
    public Collection<V> values() {

        Set<String> keys = null;
        try {
            keys = redisService.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get values error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        List<V> result = new ArrayList<>();
        keys.forEach(key -> {
            V value = null;
            try {
                value = (V) redisService.get(key);
            } catch (Exception e) {
                log.error("deserialize values=error", e);
            }
            if (value != null) {
                result.add(value);
            }
        });

        return Collections.unmodifiableList(result);
    }

}
