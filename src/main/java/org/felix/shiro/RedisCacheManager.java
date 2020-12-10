package org.felix.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.felix.service.RedisService;
import org.springframework.data.redis.cache.RedisCache;

import javax.annotation.Resource;

public class RedisCacheManager implements CacheManager {

    @Resource
    private RedisService redisService;


    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache(s, redisService);
    }

}
