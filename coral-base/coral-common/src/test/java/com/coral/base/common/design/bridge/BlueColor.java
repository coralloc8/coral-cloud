package com.coral.base.common.design.bridge;

/**
 * @author huss
 * @version 1.0
 * @className BlueColor
 * @description todo
 * @date 2022/8/1 14:16
 */
public class BlueColor implements Color {
    @Override
    public void drawColor() {
        System.out.println("颜色：蓝色");
    }
}
