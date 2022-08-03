package com.coral.base.common.design.balking;

import java.util.concurrent.TimeUnit;

/**
 * @author huss
 * @version 1.0
 * @className DelayProvider
 * @description todo
 * @date 2022/8/1 11:23
 */
public interface DelayProvider {

    void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
