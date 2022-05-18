package com.coral.base.common;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className StringUtilsTest
 * @description string工具类测试
 * @date 2022/5/18 11:01
 */
public class StringUtilsTest {

    @Test
    @DisplayName("测试是否是基本数据类型")
    public void isCommonDataType() {
        Assertions.assertTrue(StringUtils.isBaseType(int.class));
        Assertions.assertTrue(StringUtils.isBaseType(byte.class));
        Assertions.assertTrue(StringUtils.isBaseType(double.class));
        Assertions.assertTrue(StringUtils.isBaseType(char.class));
        Assertions.assertTrue(StringUtils.isBaseType(long.class));
        Assertions.assertTrue(StringUtils.isBaseType(float.class));
        Assertions.assertTrue(StringUtils.isBaseType(boolean.class));
        Assertions.assertTrue(StringUtils.isBaseType(short.class));
        Assertions.assertFalse(StringUtils.isBaseType(String.class));
        Assertions.assertFalse(StringUtils.isBaseType(Map.class));
        Assertions.assertFalse(StringUtils.isBaseType(Collection.class));
        Assertions.assertFalse(StringUtils.isBaseType(List.class));
        Assertions.assertFalse(StringUtils.isBaseType(StingUtilTestModel.class));
    }

    @Test
    @DisplayName("测试是否是基本数据的包装类型")
    public void isWrapClass() {
        Assertions.assertTrue(StringUtils.isBaseWrapType(Integer.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Short.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Long.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Byte.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Boolean.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Double.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Float.class));
        Assertions.assertTrue(StringUtils.isBaseWrapType(Character.class));

        Assertions.assertTrue(StringUtils.isBaseWrapType(String.class));

        Assertions.assertFalse(StringUtils.isBaseWrapType(Map.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(Collection.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(List.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(StingUtilTestModel.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(int.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(byte.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(double.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(char.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(long.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(float.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(boolean.class));
        Assertions.assertFalse(StringUtils.isBaseWrapType(short.class));



    }


    @Data
    static class StingUtilTestModel {

        private String name;


    }
}
