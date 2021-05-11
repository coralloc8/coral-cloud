package com.coral.base.common.bus.event;

import java.util.concurrent.ExecutorService;

import com.coral.base.common.ThreadPool;

/**
 * @author huss
 */

public enum DefaultEventIdentifier implements IEventIdentifier {

    /**
     * 默认event标识
     */
    DEFAULT("default") {
        @Override
        public boolean needThreadPool() {
            return false;
        }

        @Override
        public ExecutorService getThreadPool() {
            return ThreadPool.INSTANCE.getThreadPool(this.getIdentifierKey(), 5, 10);
        }
    },

    ;

    @Override
    public abstract boolean needThreadPool();

    @Override
    public abstract ExecutorService getThreadPool();

    private String key;

    @Override
    public String getIdentifierKey() {
        return this.key;
    }

    DefaultEventIdentifier(String key) {
        this.key = key;
    }
}
