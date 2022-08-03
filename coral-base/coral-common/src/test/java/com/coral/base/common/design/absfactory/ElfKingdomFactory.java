package com.coral.base.common.design.absfactory;

/**
 * @author huss
 * @version 1.0
 * @className ElfKingdomFactory
 * @description todo
 * @date 2022/7/29 11:02
 */
public class ElfKingdomFactory implements KingdomFactory {
    @Override
    public Castle createCastle() {
        return new ElfCastle();
    }

    @Override
    public King createKing() {
        return new ElfKing();
    }

    @Override
    public Army createArmy() {
        return new ElfArmy();
    }
}
