package com.coral.base.common.design.acyclicvisitor;

/**
 * @author huss
 * @version 1.0
 * @className Hayes
 * @description todo
 * @date 2022/7/29 14:27
 */
public class Hayes extends Modem {
    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof HayesVisitor) {
            ((HayesVisitor) modemVisitor).visit(this);
        } else {
            System.out.println("##### Only HayesVisitor is allowed to visit Hayed modem");
        }
    }
}
