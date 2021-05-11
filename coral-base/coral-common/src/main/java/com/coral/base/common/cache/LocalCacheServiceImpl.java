package com.coral.base.common.cache;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.coral.base.common.exception.CacheSaveException;
import com.coral.base.common.exception.OperationNotSupportedException;
import com.coral.base.common.ThreadPool;
import com.google.common.cache.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 本地缓存 cache 1 minute
 * 
 * @author huss
 */

@Slf4j
public class LocalCacheServiceImpl<T> implements ICacheService<String, T> {

    private Cache<String, T> cache;

    public static LocalCacheServiceImpl getInstance() {
        return LocalCacheServiceSingleton.INSTANCE.getLocalCacheService();
    }

    enum LocalCacheServiceSingleton {
        INSTANCE;

        @Getter
        private LocalCacheServiceImpl localCacheService;

        LocalCacheServiceSingleton() {
            this.localCacheService = new LocalCacheServiceImpl();
        }
    }

    private LocalCacheServiceImpl() {
        super();
        init();
    }

    public void init() {
        RemovalListener<String, T> removalListener =
            RemovalListeners.asynchronous((RemovalNotification<String, T> removal) -> {
                log.info("#####" + removal.getKey() + " 缓存被回收了..." + removal.getCause());
            }, ThreadPool.INSTANCE.getThreadPool("com.coral.base.common.cache-pool", 1, 5));

        //@formatter:off
          cache = CacheBuilder.newBuilder()
            .initialCapacity(1)
            .maximumSize(100)
            //.maximumWeight(10 * 1024 * 1024 * 1024)
//            .weigher((String k,String v) -> k.getBytes().length + v.getBytes().length
//             )
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .concurrencyLevel(10)
            .recordStats()
            .removalListener(removalListener)
            .build();
        //@formatter:on

    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }

    @Override
    public void delete(List<String> keys) {
        cache.invalidateAll(keys);
    }

    @Override
    public void deleteAll() {
        cache.invalidateAll();
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public Optional<T> getIfPresent(String key, Class<?> clazz) {
        try {
            T opt = cache.getIfPresent(key);
            if (!Objects.isNull(opt)) {
                return Optional.of(opt);
            }
        } catch (Exception e) {
            log.error("#####com.coral.base.common.cache get error", e);
        }
        return Optional.empty();
    }

    @Override
    public T get(String key, Supplier<T> supplier, Class<?> clazz) {
        try {
            return cache.get(key, () -> supplier.get());
        } catch (ExecutionException e) {
            throw new CacheSaveException("failed to set com.coral.base.common.cache");
        }

    }

    /**
     * 
     * @param key
     * @param supplier
     * @param timeUnit
     * @param time
     * @return
     * @throws OperationNotSupportedException
     */
    @Override
    public T get(String key, Supplier<T> supplier, TimeUnit timeUnit, long time, Class<?> clazz) {
        throw new OperationNotSupportedException();
    }

}
