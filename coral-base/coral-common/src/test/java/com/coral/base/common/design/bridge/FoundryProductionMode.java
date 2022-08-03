package com.coral.base.common.design.bridge;

/**
 * @author huss
 * @version 1.0
 * @className FoundryProductionMode
 * @description todo
 * @date 2022/8/1 14:19
 */
public class FoundryProductionMode implements ProductionMode {
    @Override
    public void drawMode() {
        System.out.println("生产方式：代工");
    }
}
