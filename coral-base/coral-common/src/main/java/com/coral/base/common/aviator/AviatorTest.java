package com.coral.base.common.aviator;

/**
 * @author huss
 * @version 1.0
 * @className AviatorTest
 * @description todo
 * @date 2022/1/22 8:54
 */
public class AviatorTest {

    public static void main(String[] args) {


    }

    private static void test1() {
        //条件开始
        String start = "如果";

        //问题
        String question = "Q(2034)";

        //问题操作
        String questionAction = "选中";

        //选项
        String options = "A(1),A(2)";

        //输入值
        String optionaVal = "输入值";

        //条件结束
        String end = "则";

        //结果操作
        String resultAction = "跳转";

        //结果关联的题
        String resultQuestion = "Q(2037)";


        //用户答题 Q(2034)  选中 A(1) 开始计算一下题

        /**
         * 1、首先查询Q(2034) 有没有设置规则。
         * 2、查到设置规则后则开始规则解析。
         */

        // 跳转问题
        /**
         * 逻辑组 G001
         *
         * 问题：2034
         *      选项 A1
         *      选项 A2
         * 问题操作：选中
         *
         * 或
         *
         * 问题：2035
         *      选项 A4
         * 问题操作：未答
         *
         *
         * 结果操作：跳转
         *
         * 结果关联问题：2037
         *
         */

        // 循环问题
        /**
         * 逻辑组 G002
         *
         * 问题：4890
         *
         *
         */


    }

}
