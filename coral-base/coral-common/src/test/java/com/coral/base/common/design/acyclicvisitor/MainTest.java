package com.coral.base.common.design.acyclicvisitor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className MainTest
 * @description todo
 * @date 2022/7/29 14:59
 */
public class MainTest {

    @Test
    @DisplayName("acyclic visitor测试")
    public void test() {
        ModemVisitor dosVisitor = new ConfigForDosVisitor();
        ModemVisitor unixVisitor = new ConfigForUnixVisitor();

        Hayes hayes = new Hayes();
        Zoom zoom = new Zoom();

        hayes.accept(dosVisitor);
        zoom.accept(dosVisitor);

        System.out.println("==================================");

        hayes.accept(unixVisitor);
        zoom.accept(unixVisitor);
    }
}
