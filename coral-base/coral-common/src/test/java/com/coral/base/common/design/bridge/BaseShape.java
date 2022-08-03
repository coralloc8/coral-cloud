package com.coral.base.common.design.bridge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author huss
 * @version 1.0
 * @className AbstractShape
 * @description todo
 * @date 2022/8/1 14:40
 */
@Getter
@ToString
@AllArgsConstructor
public class BaseShape implements Shape {
    protected Color color;

    protected ProductionMode mode;

    @Override
    public void draw() {
        color.drawColor();
        mode.drawMode();
    }
}
