package com.coral.base.common.design.bridge;

/**
 * @author huss
 * @version 1.0
 * @className CircleShape
 * @description todo
 * @date 2022/8/1 14:14
 */
public class CircleShape extends BaseShape implements Shape {

    public CircleShape(Color color, ProductionMode mode) {
        super(color, mode);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("形状：圆形");
    }
}
