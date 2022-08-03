package com.coral.base.common.design.typeobj;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className AppTest
 * @description todo
 * @date 2022/8/2 10:32
 */
public class AppTest {

    @Test
    @DisplayName("Type Object test")
    public void test() {
        Monster monster = new Monster(Breed.of("巨魔", 100, 150));
        System.out.println(monster);

        monster = new Monster(Breed.of("巨龙", 100, 50000));
        System.out.println(monster);
    }
}
