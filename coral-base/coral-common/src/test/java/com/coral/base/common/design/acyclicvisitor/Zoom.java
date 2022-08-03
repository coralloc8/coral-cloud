package com.coral.base.common.design.acyclicvisitor;

/**
 * @author huss
 * @version 1.0
 * @className Zoom
 * @description todo
 * @date 2022/7/29 14:25
 */
public class Zoom extends Modem {
    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof ZoomVisitor) {
            ((ZoomVisitor) modemVisitor).visit(this);
        } else {
            System.out.println("##### Only ZoomVisitor is allowed to visit Zoom modem");
        }
    }
}
