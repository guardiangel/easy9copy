package org.felix.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.felix.service.RedisService;

import javax.annotation.Resource;

/**
 * @author Felix
 */
public class RedisCacheManager implements CacheManager {

    @Resource
    private RedisService redisService;


    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache(s, redisService);
    }
}
