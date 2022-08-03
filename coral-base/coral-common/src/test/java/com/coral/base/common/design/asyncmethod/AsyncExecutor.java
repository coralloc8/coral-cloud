package com.coral.base.common.design.asyncmethod;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author huss
 * @version 1.0
 * @className AsyncExecutor
 * @description todo
 * @date 2022/7/29 17:13
 */
public interface AsyncExecutor {

    <T> AsyncResult<T> startProcess(Callable<T> task);

    <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);

    <T> T endProcess(AsyncResult<T> asyncResult) throws InterruptedException, ExecutionException;
}
