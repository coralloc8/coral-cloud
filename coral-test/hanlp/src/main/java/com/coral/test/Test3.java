package com.coral.test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Stack;

public class Test3 {

    /**
     * 根据字节数来截取字符串，碰到汉字只能取整，不足的舍弃
      * @param args
     */
    public static void main(String[] args) {
        int size = 5;
        int curSize = 0;
        String str = "我ABC汉DEF";
        StringBuilder sb = new StringBuilder();
        String[] stirs = str.split("");
        for (String st : stirs) {
            int length = st.getBytes(StandardCharsets.UTF_8).length;
            if (curSize + length > size) {
                break;
            }
            sb.append(st);
            curSize += length;
        }
        System.out.println(sb.toString());
        Stack<String> stack = new Stack<>();
    }
}
