package com.coral.base.common.bus.event;

import java.util.concurrent.ExecutorService;

import com.coral.base.common.ThreadPool;

/**
 * @author huss
 */
public interface IEventIdentifier {

    /**
     * 获取唯一标识
     * 
     * @return
     */
    String getIdentifierKey();

    /**
     * 是否需要线程池
     * 
     * @return
     */
    boolean needThreadPool();

    /**
     * 获取线程池
     * 
     * @return
     */
    ExecutorService getThreadPool();

    /**
     * 获取默认线程池
     * 
     * @return
     */
    default ExecutorService getDefaultThreadPool() {
        return ThreadPool.INSTANCE.getThreadPool("default", 1, 5);
    }

}
