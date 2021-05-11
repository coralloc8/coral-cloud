package com.coral.base.common.cache;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.coral.base.common.exception.CacheSaveException;

/**
 * @author huss
 */
public interface ICacheService<K, V> {

    /**
     * 删除缓存
     * 
     * @param key
     */
    void delete(K key);

    /**
     * 删除缓存
     * 
     * @param keys
     */
    void delete(List<K> keys);

    /**
     * 删除所有缓存
     */
    void deleteAll();

    /**
     * 获取缓存数量
     * 
     * @return
     */
    long size();

    /**
     * 获取缓存
     * 
     * @param key
     * @param clazz
     * @return
     */
    Optional<V> getIfPresent(K key, Class<?> clazz);

    /**
     * 获取缓存，若没有则设置
     *
     * @param key
     * @param supplier
     * @param clazz
     * @throws CacheSaveException
     * @return
     */
    V get(K key, Supplier<V> supplier, Class<?> clazz);

    /**
     * 获取缓存，若没有则设置
     * @param key
     * @param supplier
     * @param timeUnit
     * @param time
     * @param clazz
     * @return
     */
    V get(K key, Supplier<V> supplier, TimeUnit timeUnit, long time, Class<?> clazz);

}
