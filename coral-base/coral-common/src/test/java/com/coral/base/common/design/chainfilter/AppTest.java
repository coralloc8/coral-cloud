package com.coral.base.common.design.chainfilter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className AppTest
 * @description todo
 * @date 2022/8/1 17:20
 */
public class AppTest {

    @Test
    @DisplayName("Chain Filter test")
    public void test() {
        ChainFilter chainFilter = new ChainFilter()
                .addFilter(new TeacherFilter())
                .addFilter(new Teacher2Filter())
                .addFilter(new Teacher3Filter())
                .addFilter(new Teacher4Filter())
                .addFilter(new Teacher5Filter());

        chainFilter.filter(new Request("小三", 6));
        System.out.println("##################################");

        chainFilter.filter(new Request("小四", 3));
        System.out.println("##################################");

        chainFilter.filter(new Request("小五", 12));
        System.out.println("##################################");

        chainFilter.filter(new Request("小六", 122));
        System.out.println("##################################");

        chainFilter.filter(new Request("小七", 30));

    }
}
