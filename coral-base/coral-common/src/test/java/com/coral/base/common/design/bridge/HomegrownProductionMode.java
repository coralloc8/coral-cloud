package com.coral.base.common.design.bridge;

/**
 * @author huss
 * @version 1.0
 * @className HomegrownProductionMode
 * @description todo
 * @date 2022/8/1 14:18
 */
public class HomegrownProductionMode implements ProductionMode {
    @Override
    public void drawMode() {
        System.out.println("生产方式：自产");
    }
}
