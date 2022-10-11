package com.coral.base.common.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className PutAppleTest
 * @description 放苹果算法
 * @date 2022/9/22 17:12
 */
public class PutAppleTest {

    @Test
    @DisplayName("放苹果")
    public void test() {
        /**
         * 【题目描述】
         * 把M个同样的苹果放在N个同样的盘子里，允许有的盘子空着不放，问共有多少种不同的分法？（用K表示）5，1，1和1，5，1 是同一种分法。
         * 【输入】
         * 第一行是测试数据的数目t（0 ≤ t ≤ 20）。以下每行均包含二个整数M和N，以空格分开。1≤M，N≤10。
         * 【输出】
         * 对输入的每组数据M和N，用一行输出相应的K。
         * 【输入样例】
         * 1
         * 7 3
         * 【输出样例】
         * 8
         * ————————————————
         */

        /**
         *
         * 008
         * 170      260     440     350
         * 116      125     134
         * 224      233
         *
         */

        int size = put(8, 3);
        System.out.println("结果：" + size);
    }

    @Test
    @DisplayName("放苹果2")
    public void test2() {
        int m = 8;
        int n = 3;
        int[][] f = new int[n + 1][m + 1];


        //初始化节点信息
        //无论几个苹果，0个篮子或者1个篮子的话只有1种方案
        for (int i = 0; i <= m; i++) {
            f[0][i] = f[1][i] = 1;

        }
        //无论几个篮子，0个苹果或者1个苹果的话只有1种方案
        for (int j = 0; j <= n; j++) {
            f[j][0] = f[j][1] = 1;
        }

        //先遍历行
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= m; j++) {
                //苹果小于篮子的情况
                if (j < i) {
                    f[i][j] = f[j][j];
                } else {
                    f[i][j] = f[i - 1][j] + f[i][j - i];
                }
                System.out.print(f[i][j] + "  ");
            }
            System.out.println();
        }

        System.out.println(f[3][7]);
    }


    private int put(int m, int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1 || m == 0) {
            return 1;
        }

        if (n > m) {
            return put(m, m);
        }
        return put(m, n - 1) + put(m - n, n);
    }
}
