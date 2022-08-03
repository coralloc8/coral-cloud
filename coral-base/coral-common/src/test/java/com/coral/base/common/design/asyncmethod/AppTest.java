package com.coral.base.common.design.asyncmethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author huss
 * @version 1.0
 * @className AppTest
 * @description todo
 * @date 2022/8/1 10:06
 */
public class AppTest {

    @Test
    @DisplayName("Async Method test")
    public void test() throws ExecutionException, InterruptedException {

        AsyncExecutor executor = new ThreadAsyncExecutor();

        AsyncResult<Integer> process1 = executor.startProcess(lazyval(10, 500));

        AsyncResult<Integer> process2 = executor.startProcess(lazyval(100, 2000), callback("回调"));

        process1.await();
        process2.await();


        Integer re1 = process1.getValue();

        System.out.println("re1：" + re1);

        Integer re2 = process2.getValue();
        System.out.println("re2：" + re2);
    }

    private static <T> Callable<T> lazyval(T value, long delayMillis) {
        return () -> {
            Thread.sleep(delayMillis);
            return value;
        };
    }

    private static <T> AsyncCallback<T> callback(String name) {
        return (value, ex) -> {
            if (ex.isPresent()) {
                System.out.println(name + " failed: " + ex.map(Exception::getMessage).orElse(""));
            } else {
                System.out.println(name + " <" + value + ">");
            }
        };
    }


}
