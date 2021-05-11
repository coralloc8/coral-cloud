package com.coral.base.common.enums;

import java.util.concurrent.TimeUnit;

import com.coral.base.common.RandomUtil;

/**
 * 缓存key
 * 
 * @author huss
 */
public interface CacheEnum<T extends Enum<T>> extends IEnum<T, Integer> {

    /**
     * 获取缓存的时间单位，默认以秒为单位
     * 
     * @return
     */
    default TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }

    /**
     * 秒为单位的话会随机一个时间单位，在原有存储时间基础上请加上该随机值
     * 
     * @return
     */
    default int getRandomTime() {
        TimeUnit timeUnit = this.getTimeUnit();

        if (timeUnit != null && TimeUnit.SECONDS.equals(timeUnit)) {
            return (RandomUtil.getRandomInstance().nextInt(300));
        }
        return 0;

    }

}
