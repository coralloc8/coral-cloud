package com.coral.base.common.design.acyclicvisitor;

/**
 * @author huss
 * @version 1.0
 * @className ConfigForUnixVisitor
 * @description todo
 * @date 2022/7/29 14:52
 */
public class ConfigForUnixVisitor implements ZoomVisitor {

    @Override
    public void visit(Zoom zoom) {
        System.out.println(">>>>>" + zoom + "used with Unix config");
    }
}
