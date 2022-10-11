package com.coral.base.common.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className BackpackTest
 * @description 背包问题
 * @date 2022/9/23 15:56
 */
public class BackpackTest {

    @Test
    @DisplayName("动态规划求背包问题")
    public void test() {
        /**
         * 有 N 件物品和一个容量是 V的背包。每件物品只能使用一次。
         *
         * 第 i 件物品的体积是 vi，价值是 wi。
         *
         * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
         * 输出最大价值。
         *
         * 输入格式
         *
         * 第一行两个整数，N，V用空格隔开，分别表示物品数量和背包容积。
         *
         * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。
         *
         * 输出格式
         *
         * 输出一个整数，表示最大价值。
         *
         * 数据范围
         *
         * 0<N,V≤1000
         * 0<vi,wi≤1000
         * ————————————————
         */
        int n = 5;
        int v = 10;
        //体积
        int[] vi = new int[]{2, 3, 4, 5, 6};
        //价值
        int[] wi = new int[]{2, 3, 4, 4, 6};

        int[][] m = new int[n + 1][v + 1];


        for (int i = 0; i <= n; i++) {
            m[i][0] = 0;
        }

        for (int i = 0; i <= v; i++) {
            m[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            //背包体积
            for (int j = 1; j <= v; j++) {
                if (vi[i] >= j) {
                    m[i][j] = m[i - 1][j];
                } else {
                    //1、不放入此物时的最大价值
                    //2、腾出放入此物的空间后的剩余最大价值 + 此物的价值
                    //上述谁的价值大就应该取那个最大价值
                    m[i][j] = Math.max(m[i - 1][j], m[i - 1][j - vi[i]] + wi[i]);
                }

                System.out.print(m[i][j] + "  ");
            }
            System.out.println();
        }


    }
}
