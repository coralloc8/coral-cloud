package com.coral.base.common.design.absfactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className MainTest
 * @description todo
 * @date 2022/7/29 11:03
 */
public class MainTest {

    @Test
    @DisplayName("测试 absfactory")
    public void tes() {
        KingdomFactory kingdomFactory = new ElfKingdomFactory();

        Castle castle = kingdomFactory.createCastle();
        System.out.println(castle.getDescription());

        Army army = kingdomFactory.createArmy();
        System.out.println(army.getDescription());

        King king = kingdomFactory.createKing();
        System.out.println(king.getDescription());
    }
}
