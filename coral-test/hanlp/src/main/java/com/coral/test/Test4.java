package com.coral.test;

import com.coral.base.common.StrFormatter;

import java.util.HashMap;

/**
 * 位运算
 *
 * @author huss
 * @date 2024/2/27 13:54
 * @packageName com.coral.test
 * @className Test4
 */
public class Test4 {

    public static void main(String[] args) {
        // 全是2的次方的与操作值为0
        System.out.println(StrFormatter.format("2&4={}", 2 & 4));
        System.out.println(StrFormatter.format("2&8={}", 2 & 8));
        System.out.println(StrFormatter.format("4&16={}", 2 & 8));

        //
        System.out.println(StrFormatter.format("2&3={}", 2 & 3));


        //
        System.out.println(StrFormatter.format("2&2={}", 2 & 2));
        System.out.println(StrFormatter.format("2&-2={}", 2 & -2));
        System.out.println(StrFormatter.format("3&3={}", 3 & 3));
        System.out.println(StrFormatter.format("3&-3={}", 3 & -3));

      //
        System.out.println(StrFormatter.format("4&2={}", 4 & 2));

        // 添加 1、2、4、8、16、32 几个权限
        System.out.println(StrFormatter.format("1|2|4|8|16|32={}", 1|2|4|8|16|32));
        // 是否拥有32这个权限
        System.out.println(StrFormatter.format("63&32={}", 63 & 32));
        // 移除32这个权限
        System.out.println(StrFormatter.format("63&~32={}", 63 & ~32));
        System.out.println(StrFormatter.format("1|2|4|8|16={}", 1|2|4|8|16));

        // 取低8位
        System.out.println(356>>8&0xff);

    }
}
