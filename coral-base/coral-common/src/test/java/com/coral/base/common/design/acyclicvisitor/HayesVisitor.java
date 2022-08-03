package com.coral.base.common.design.acyclicvisitor;

/**
 * @author huss
 * @version 1.0
 * @className HayesVisitor
 * @description todo
 * @date 2022/7/29 14:28
 */
public interface HayesVisitor extends ModemVisitor {
    void visit(Hayes hayes);
}
