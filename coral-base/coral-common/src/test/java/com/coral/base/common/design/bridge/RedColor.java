package com.coral.base.common.design.bridge;


/**
 * @author huss
 * @version 1.0
 * @className RedColor
 * @description todo
 * @date 2022/8/1 14:15
 */
public class RedColor implements Color {
    @Override
    public void drawColor() {
        System.out.println("颜色：红色");
    }
}
