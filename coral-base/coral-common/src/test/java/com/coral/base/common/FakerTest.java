package com.coral.base.common;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * @author huss
 * @version 1.0
 * @className FakerTest
 * @description 数据造假测试
 * @date 2022/7/26 11:27
 */
public class FakerTest {

    @Test
    @DisplayName("数据造假测试")
    public void test() {
        Faker faker = new Faker(Locale.CHINA);
        System.out.println(faker.name().fullName());
    }
}
