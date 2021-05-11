package com.coral.base.common;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author huss
 */

public enum ThreadPool {

    INSTANCE;

    private String poolName;

    private int corePoolSize;

    private int maximumPoolSize;

    private ExecutorService pool;

    public ExecutorService getThreadPool(String poolName, int corePoolSize, int maximumPoolSize) {
        this.poolName = poolName;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        return createThreadPool();
    }

    private ExecutorService createThreadPool() {
        ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder().setNameFormat(poolName.toLowerCase() + "-pool-%d").build();
        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
