package com.coral.base.common.design.balking;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author huss
 * @version 1.0
 * @className AppTest
 * @description todo
 * @date 2022/8/1 13:37
 */
@Slf4j
public class AppTest {

    @Test
    @DisplayName("Balking test")
    public void test() {
        WashingMachine washingMachine = new WashingMachine();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(washingMachine::wash);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            System.out.println("ERROR: Waiting on executor service shutdown!");
            Thread.currentThread().interrupt();
        }
    }
}
