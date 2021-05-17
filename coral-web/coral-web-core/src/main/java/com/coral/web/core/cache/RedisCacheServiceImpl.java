package com.coral.web.core.cache;

import com.coral.base.common.cache.ICacheService;
import com.coral.base.common.exception.Exceptions;
import com.coral.base.common.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author huss
 */
public class RedisCacheServiceImpl<V> implements ICacheService<String, V> {

    private static final String ALL_PATTERN = "*";

    @Qualifier("myRedisTemplate")
    @Autowired
    private RedisTemplate<String, V> myRedisTemplate;

    @Override
    public void delete(String key) {
        myRedisTemplate.delete(key);
    }

    @Override
    public void delete(List<String> keys) {
        myRedisTemplate.delete(keys);
    }

    @Override
    public void deleteAll() {
        myRedisTemplate.multi();
        Collection<String> keys = this.keys(ALL_PATTERN);
        myRedisTemplate.delete(keys);
        myRedisTemplate.exec();
    }

    @Override
    public long size() {
        Collection<String> keys = this.keys(ALL_PATTERN);
        return keys.size();
    }

    @Override
    public Optional<V> getIfPresent(String key, Class<?> clazz) {
        if (myRedisTemplate.hasKey(key)) {

            Object obj = myRedisTemplate.opsForValue().get(key);

            if (obj == null) {
                return Optional.empty();
            }
            V v;
            if (obj instanceof List) {
                v = (V) ((List<Map>) obj).stream().map(e -> JsonUtil.toPojo(e, clazz)).collect(Collectors.toList());
            } else if (obj instanceof Map) {
                v = (V) JsonUtil.toPojo(((Map) obj), clazz);
            } else {
                v = (V) obj;
            }

            return Optional.of(v);
        }
        return Optional.empty();
    }

    @Override
    public V get(String key, Supplier<V> supplier, Class<?> clazz) {

        SessionCallback<V> callback = new SessionCallback<V>() {
            @Override
            public V execute(RedisOperations operations) throws DataAccessException {
                Optional<V> resOpt = getIfPresent(key, clazz);
                if (!resOpt.isPresent()) {
                    V res = supplier.get();
                    myRedisTemplate.opsForValue().set(key, res);
                    return res;
                }
                return resOpt.get();
            }
        };
        return myRedisTemplate.execute(callback);
    }

    @Override
    public V get(String key, Supplier<V> supplier, TimeUnit timeUnit, long time, Class<?> clazz) {
        SessionCallback<V> callback = new SessionCallback<V>() {
            @Override
            public V execute(RedisOperations operations) throws DataAccessException {
                Optional<V> resOpt = getIfPresent(key, clazz);
                if (resOpt.isPresent()) {
                    return resOpt.get();
                }
                V res = supplier.get();
                myRedisTemplate.opsForValue().set(key, res, time, timeUnit);

                return res;
            }
        };
        return myRedisTemplate.execute(callback);

    }

    private List<String> keys(String patternKey) {
        ScanOptions options = ScanOptions.scanOptions().count(10000).match(patternKey).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) myRedisTemplate.getKeySerializer();
        Cursor cursor = myRedisTemplate.executeWithStickyConnection(
                redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(cursor.next().toString());
        }
        try {
            cursor.close();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
        return result;

    }
}
