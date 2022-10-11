package com.coral.base.common.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className StrNumTest
 * @description 字符串测试
 * @date 2022/9/22 14:00
 */
public class StrNumTest2 {

    @Test
    @DisplayName("出现次数超过一半的数")
    public void test() {
        /**
         *【题目描述】
         * 给出一个含有n（0 < n ≤ 1000）个整数的数组，请找出其中出现次数超过一半的数。数组中的数大于-50且小于50。
         * 【输入】
         * 第一行包含一个整数n，表示数组大小；
         * 第二行包含n个整数，分别是数组中的每个元素，相邻两个元素之间用单个空格隔开。
         * 【输出】
         * 如果存在这样的数，输出这个数；否则输出no。
         * 【输入样例】
         * 3
         * 1 2 2
         * 【输出样例】
         * 2
         * ————————————————
         */

        Random random = new Random();

        int size = random.nextInt(1001);
        size = size <= 0 ? 1 : size;

        System.out.println("总次数:" + size);

        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            int num = random.nextInt(50);
            nums[i] = num % 2 == 0 ? num : -num;
        }

        maxNum(nums);
    }

    private void maxNum(int[] nums) {
        long start = System.currentTimeMillis();
        int mid = nums.length / 2;
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0, size = nums.length; i < size; i++) {
            Integer curStr = nums[i];
            int num = map.getOrDefault(curStr, 0);
            map.put(curStr, ++num);
        }
        System.out.println(map);

        List<String> results = map.entrySet().stream()
                .filter(e -> e.getValue() > mid)
                .map(e -> e.getKey() + "")
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("no");
        } else {
            System.out.println(String.join(" ", results));
        }

        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%s ms", (end - start)));
    }
}
