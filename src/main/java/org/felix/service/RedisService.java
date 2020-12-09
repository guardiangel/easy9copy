package org.felix.service;

import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@Service("redisService")
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * delete a key
     *
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return key == null ? false : redisTemplate.delete(key);
    }

    /**
     * batch delete
     *
     * @param keys
     * @return
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * if contain a key
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return key == null ? false : redisTemplate.hasKey(key);
    }


    /**
     * set expire time
     *
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        return key == null || timeUnit == null ? false : redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * find matched keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return pattern == null ? null : redisTemplate.keys(pattern);
    }

    /**
     * persist a key
     *
     * @param key
     * @return
     */
    public Boolean persist(String key) {
        return key == null ? false : redisTemplate.persist(key);
    }

    /**
     * return the expire time
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public Long getExpire(String key, TimeUnit timeUnit) {

        if (key == null || timeUnit == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key or timeUnit 不能为空");
        }

        return redisTemplate.getExpire(key, timeUnit);
    }


    /**
     * set a key-value
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        if (key == null || value == null) {
            return;
        }

        redisTemplate.opsForValue().set(key, value);
    }


    /**
     * set a key-value and expire time
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {

        if (key == null || value == null || timeUnit == null) {
            return;
        }

        if (time < 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        }
    }

    /**
     * set a key-value and expire time
     * if key exists,return false
     * if key not exists, set the key and return ture
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public Boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        if (key == null || value == null || timeUnit == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key, value and timeUnit can't be null");
        }

        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * get value from key
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        if (key == null) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * get the old value, and set the new value
     *
     * @param key
     * @param value
     * @return
     */
    public Object getSet(String key, Object value) {
        return key == null ? null : redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * batch get
     *
     * @param keys
     * @return
     */
    public List<Object> mget(Collection<String> keys) {
        return keys == null ? Collections.emptyList()
                : redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * increment by cas.
     * if value can't be transfer to int,throw exception.
     *
     * @param key
     * @param increment
     * @return
     */
    public Long incrby(String key, long increment) {

        if (key == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key can't be null");
        }

        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * decrease by cas.
     * if value can't be transfer to int,throw exception.
     *
     * @param key
     * @param decrement
     * @return
     */
    public Long decrby(String key, long decrement) {

        if (key == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key can't be null");
        }

        return redisTemplate.opsForValue().decrement(key, decrement);
    }

    /**
     * if key exists,append value to the existed value
     * if key not exists, set key-value
     *
     * @param key
     * @param value
     * @return value's length
     */
    public Integer append(String key, String value) {

        if (key == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key can't be null");
        }

        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * get the specified value via key and field params
     *
     * @param key
     * @param field
     * @return
     */
    public Object hget(String key, Object field) {

        return key == null || field == null ? null
                : redisTemplate.opsForHash().get(key, field);
    }

    /**
     * if key exists,use value instead of field
     * if key not exists,set key-value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, Object field, Object value) {
        if (null == key || null == field) {
            return;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * if the field contains specified key
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, Object field) {

        return null == key || null == field ? false
                : redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * delete mutiple fields from specified key, if field not exists, ignore.
     * if key not exists, return 0
     *
     * @param key
     * @param fields
     * @return the total num of deleted fields
     */
    public Long hdel(String key, Object... fields) {

        return key == null || fields == null || fields.length == 0 ? 0L
                : redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * get all fields and values from key
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {

        return key == null ? null : redisTemplate.opsForHash().entries(key);
    }

    /**
     * if key exists, use new value instead of old value
     * if key not exists,set key-value
     *
     * @param key
     * @param hash
     */
    public void hmset(String key, Map<String, Object> hash) {
        if (key == null || hash == null) {
            return;
        }

        redisTemplate.opsForHash().putAll(key, hash);
    }

    /**
     * batch get
     *
     * @param key
     * @param fields
     * @return
     */
    public List<Object> hmget(String key, Collection<Object> fields) {
        if (key == null || fields == null) {
            return null;
        }

        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * increase the field by specified key
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public Long hIncrBy(String key, Object field, long increment) {
        if (null == key || null == field) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key or field 不能为空");
        }
        return redisTemplate.opsForHash().increment(key, field, increment);

    }

    /**
     * insert strs into a list
     *
     * @param key
     * @param strs
     * @return
     */
    public Long lpush(String key, Object... strs) {

        if (key == null) {
            return 0L;
        }

        return redisTemplate.opsForList().leftPushAll(key, strs);
    }

    /**
     * insert strs into a list
     *
     * @param key
     * @param strs
     * @return
     */
    public Long rpush(String key, Object... strs) {

        if (key == null) {
            return 0L;
        }

        return redisTemplate.opsForList().rightPushAll(key, strs);
    }

    /**
     * pop a element from a list
     *
     * @param key
     * @return
     */
    public Object lpop(String key) {

        if (key == null) {
            return null;
        }

        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * pop a element from a list
     *
     * @param key
     * @return
     */
    public Object rpop(String key) {

        if (key == null) {
            return null;
        }

        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lrange(String key, long start, long end) {

        if (key == null) {
            return null;
        }

        return redisTemplate.opsForList().range(key, start, end);
    }


    /**
     * remain the specified range
     * @param key
     * @param start
     * @param end
     */
    public void ltrim(String key, long start, long end) {
        if (null == key) {
            return;
        }
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * return the element from specified position
     * @param key
     * @param index
     * @return
     */
    public Object lindex(String key, long index) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * the total number of specified key
     * @param key
     * @return
     */
    public Long llen(String key) {
        if (null == key) {
            return 0L;
        }
        return redisTemplate.opsForList().size(key);
    }

    /**
     * insert a member into a set
     *
     * @param key
     * @param members
     * @return
     */
    public Long sadd(String key, Object... members) {

        if (null == key) {
            return 0L;
        }

        return redisTemplate.opsForSet().add(key, members);
    }


    /**
     * the number of elements in a set
     * @param key
     * @return
     */
    public Long scard(String key) {
        
        if (null == key) {
            return 0L;
        }
        
        return redisTemplate.opsForSet().size(key);
    }

    /**
     *
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, Object member) {

        if (null == key) {
            return false;
        }

        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     *
     * @param key
     * @return
     */
    public Object srandmember(String key) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForSet().randomMember(key);

    }

    public Object spop(String key) {

        if (null == key) {
            return null;
        }

        return redisTemplate.opsForSet().pop(key);
    }

    public Set<Object> smembers(String key) {

        if (null == key) {
            return null;
        }

        return redisTemplate.opsForSet().members(key);
    }

    public Long srem(String key, Object... members) {

        if (null == key) {
            return 0L;
        }

        return redisTemplate.opsForSet().remove(key, members);
    }

    public Boolean smove(String srckey, String dstkey, Object member) {

        if (null == srckey || null == dstkey) {
            return false;
        }

        return redisTemplate.opsForSet().move(srckey, member, dstkey);
    }

    public Set<Object> sUnion(String key, String otherKeys) {

        if (null == key || otherKeys == null) {
            return null;
        }

        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Boolean zadd(String key, double score, Object member) {
        if (null == key) {
            return false;
        }
        return redisTemplate.opsForZSet().add(key, member, score);

    }

    public Long zrem(String key, Object... members) {
        if (null == key || null == members) {
            return 0L;
        }
        return redisTemplate.opsForZSet().remove(key, members);

    }

    public Long zcard(String key) {
        if (null == key) {
            return 0L;
        }
        return redisTemplate.opsForZSet().size(key);
    }

    public Double zincrby(String key, double score, Object member) {
        if (null == key) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR.getCode(), "key 不能为空");
        }
        return redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    public Long zcount(String key, double min, double max) {
        if (null == key) {
            return 0L;
        }
        return redisTemplate.opsForZSet().count(key, min, max);

    }

    public Long zrank(String key, Object member) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().rank(key, member);

    }

    public Double zscore(String key, Object member) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().score(key, member);
    }

    public Set<Object> zrange(String key, long min, long max) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().range(key, min, max);

    }

    public Set<Object> zReverseRange(String key, long start, long end) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().reverseRange(key, start, end);

    }

    public Set<Object> zrangebyscore(String key, double min, double max) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);

    }

    public Set<Object> zrevrangeByScore(String key, double min, double max) {
        if (null == key) {
            return null;
        }
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

}
