package com.coral.base.common.design.bridge;

/**
 * @author huss
 * @version 1.0
 * @className SquareShape
 * @description todo
 * @date 2022/8/1 14:15
 */
public class SquareShape extends BaseShape implements Shape {

    public SquareShape(Color color, ProductionMode mode) {
        super(color, mode);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("形状：立方体");
    }
}
