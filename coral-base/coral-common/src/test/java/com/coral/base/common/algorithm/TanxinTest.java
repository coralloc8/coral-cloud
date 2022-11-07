package com.coral.base.common.algorithm;

/**
 * @author huss
 * @version 1.0
 * @className TanxinTest
 * @description 贪心算法
 * @date 2022/11/2 13:50
 */
public class TanxinTest {

    public static void main(String[] args) {
        /**
         * 假设1元、2元、5元、10元、20元、50元、100元的纸币分别有c0, c1, c2, c3, c4, c5, c6张。现在要用这些钱来支付K元，至少要用多少张纸币？
         */
        int[][] moneyCount = {{1, 1}, {2, 1}, {5, 3}, {10, 4}, {20, 5}, {50, 7}, {100, 8}};

        solve(1995, moneyCount);
    }

    private static void solve(int money, int[][] moneyCount) {
        int count = 0;
        for (int i = moneyCount.length - 1; i >= 0; i--) {
            int current = Math.min(money % moneyCount[i][0] > 0 ? money / moneyCount[i][0] + 1 : money / moneyCount[i][0], moneyCount[i][1]);
            count += current;
            System.out.println(String.format(">>>>>[%s]元的纸币最多需要[%s]张", moneyCount[i][0], current));
            money -= current * moneyCount[i][0];
            System.out.println(String.format("当前还剩余[%s]元", money <= 0 ? 0 : money));
            if (money <= 0) {
                break;
            }
        }

        if (money > 0) {
            count = -1;
        }
        System.out.println(String.format("至少需要[%s]张纸币", count));


    }

}
