package com.coral.base.common.design.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className APpTest
 * @description todo
 * @date 2022/8/1 14:46
 */
public class AppTest {

    @Test
    @DisplayName("Bridge test")
    public void test() {
        Shape shape = new CircleShape(new RedColor(), new FoundryProductionMode());
        shape.draw();
        System.out.println("==================");
        shape = new SquareShape(new BlueColor(), new HomegrownProductionMode());
        shape.draw();
        System.out.println("==================");
        shape = new SquareShape(new RedColor(), new HomegrownProductionMode());
        shape.draw();
    }
}
