package com.coral.base.common;

import java.util.Random;

public final class RandomUtil {

    private static final Random RANDOM = new Random();

    public static Random getRandomInstance() {
        return RANDOM;
    }

    /**
     * @return 随机数字 随机优惠码规则：共n位，数字+英文字
     *         <p/>
     *         **
     */
    public static String getCharAndNumr(int length) {
        StringBuilder val = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = RANDOM.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = RANDOM.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char)(choice + RANDOM.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val.append(String.valueOf(RANDOM.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * @return 随机数字
     *         <p/>
     *         **
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int)((random * num));
    }

    /**
     * 
     * @description 包含数字
     * @author huss
     * @email 452327322@qq.com
     * @date 2020年3月6日下午3:10:28
     * @param maxLength
     *            最小13位
     * @return String
     */
    public static String getRandomNumStr(int maxLength) {
        long currentTimeMillis = System.currentTimeMillis();
        String keyPrefix = String.valueOf(currentTimeMillis);
        int length = maxLength - keyPrefix.length();
        int suffix = RandomUtil.buildRandom(length);
        StringBuilder key = new StringBuilder(keyPrefix).append(suffix);
        return key.toString();
    }

}
