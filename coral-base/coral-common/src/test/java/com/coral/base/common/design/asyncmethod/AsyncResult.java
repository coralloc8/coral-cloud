package com.coral.base.common.design.asyncmethod;

import java.util.concurrent.ExecutionException;

/**
 * @author huss
 * @version 1.0
 * @className AsyncResult
 * @description todo
 * @date 2022/7/29 17:11
 */
public interface AsyncResult<T> {

    /**
     * 是否完成
     *
     * @return
     */
    boolean isCompleted();

    T getValue() throws ExecutionException;

    void await() throws InterruptedException;
}
