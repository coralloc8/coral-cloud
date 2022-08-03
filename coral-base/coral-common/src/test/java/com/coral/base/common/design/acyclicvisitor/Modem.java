package com.coral.base.common.design.acyclicvisitor;

/**
 * @author huss
 * @version 1.0
 * @className Modem
 * @description todo
 * @date 2022/7/29 14:22
 */
public abstract class Modem {

    public abstract void accept(ModemVisitor modemVisitor);
}
