package com.coral.base.common.design.balking;

import com.coral.base.common.StrFormatter;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author huss
 * @version 1.0
 * @className WashingMachine
 * @description todo
 * @date 2022/8/1 11:21
 */
public class WashingMachine {

    private DelayProvider delayProvider;
    @Getter
    private WashingMachineState washingMachineState;

    public WashingMachine() {
        this((interval, timeUnit, task) -> {
            try {
                Thread.sleep(timeUnit.toMillis(interval));
            } catch (Exception e) {
                System.out.println(">>>>>错误:" + e);
                Thread.currentThread().interrupt();
            }
            task.run();
        });
    }

    public WashingMachine(DelayProvider delayProvider) {
        this.delayProvider = delayProvider;
        this.washingMachineState = WashingMachineState.ENABLED;
    }

    public void wash() {
        synchronized (this) {
            WashingMachineState machineState = getWashingMachineState();
            System.out.println(StrFormatter.format("{}: Actual machine state: {}", Thread.currentThread().getName(), machineState));
            if (machineState == WashingMachineState.WASHING) {
                System.out.println(StrFormatter.format("Cannot wash if the machine has been already washing!"));
                return;
            }
            this.washingMachineState = WashingMachineState.WASHING;
        }
        System.out.println(StrFormatter.format("{}: Doing the washing", Thread.currentThread().getName()));

        this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
    }

    public synchronized void endOfWashing() {
        washingMachineState = WashingMachineState.ENABLED;
        System.out.println(StrFormatter.format("{}: Washing completed.", Thread.currentThread().getId()));
    }

}
