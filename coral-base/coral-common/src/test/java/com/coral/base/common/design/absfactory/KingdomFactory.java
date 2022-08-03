package com.coral.base.common.design.absfactory;

/**
 * @author huss
 * @version 1.0
 * @className KingdomFactory
 * @description 王国工厂
 * @date 2022/7/29 10:59
 */
public interface KingdomFactory {

    /**
     * 创建城堡
     *
     * @return
     */
    Castle createCastle();

    /**
     * 创建国王
     *
     * @return
     */
    King createKing();

    /**
     * 创建部队
     *
     * @return
     */
    Army createArmy();
}
