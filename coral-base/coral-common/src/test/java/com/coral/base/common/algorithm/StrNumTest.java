package com.coral.base.common.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author huss
 * @version 1.0
 * @className StrNumTest
 * @description 字符串测试
 * @date 2022/9/22 14:00
 */
public class StrNumTest {

    @Test
    @DisplayName("统计其中哪个字符出现的次数最多")
    public void test() {
        /**
         * 【题目描述】
         * 给定一个由a-z这26个字符组成的字符串，统计其中哪个字符出现的次数最多。
         * 【输入】
         * 输入包含一行，一个字符串，长度不超过1000。
         * 【输出】
         * 输出一行，包括出现次数最多的字符和该字符出现的次数，中间以一个空格分开。如果有多个字符出现的次数相同且最多，那么输出ascii码最小的那一个字符。
         * 【输入样例】
         * abbccc
         * 【输出样例】
         * c 3
         * ————————————————
         */

        Random random = new Random();
        String str = IntStream.rangeClosed(0, 999).mapToObj(e -> {
            int index = random.nextInt(26);
            return "abcdefghijklmnopqrstuvwxyz".charAt(index) + "";

        }).collect(Collectors.joining(""));
        System.out.println(str);
        maxNum(str);
    }


    private void maxNum(String str) {
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 0, size = str.length(); i < size; i++) {
            String curStr = str.charAt(i) + "";
            int num = map.getOrDefault(curStr, 0);
            map.put(curStr, ++num);
        }
        System.out.println(map);
        map.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue()))
                .ifPresent(e -> System.out.println(e.getKey() + " " + e.getValue()));
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%s ms", (end - start)));
    }
}
