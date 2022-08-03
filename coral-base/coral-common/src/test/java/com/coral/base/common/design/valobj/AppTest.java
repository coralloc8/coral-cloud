package com.coral.base.common.design.valobj;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className AppTest
 * @description todo
 * @date 2022/8/2 9:17
 */
public class AppTest {


    @Test
    @DisplayName("Value Object test")
    public void test() {
        HeroStat heroStat = HeroStat.of(1, 1, 1);

        System.out.println(heroStat);

        HeroStat heroStat1 = heroStat.withStrength(2);
        System.out.println(heroStat);
        System.out.println(heroStat1);

        System.out.println(HeroStat.Fields.intelligence);
    }
}
