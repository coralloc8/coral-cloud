package com.coral.base.common.design.acyclicvisitor;


/**
 * @author huss
 * @version 1.0
 * @className ConfigForDosVisitor
 * @description todo
 * @date 2022/7/29 14:31
 */
public class ConfigForDosVisitor implements AllModemVisitor {

    @Override
    public void visit(Hayes hayes) {
        System.out.println(">>>>>" + hayes + "used with Dos config");
    }

    @Override
    public void visit(Zoom zoom) {
        System.out.println(">>>>>" + zoom + "used with Dos config");
    }
}
