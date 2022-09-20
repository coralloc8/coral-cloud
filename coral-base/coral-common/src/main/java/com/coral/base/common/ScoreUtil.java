package com.coral.base.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huss
 * @version 1.0
 * @className ScoreUtil
 * @description 分值计算
 * @date 2022/9/19 16:44
 */
@Slf4j
public class ScoreUtil {
    /**
     * 运算符
     */
    private static final List<String> OPERATOR = Arrays.asList("+", "-", "*", "/", "%", "^", "√", "(", ")");

    /**
     * 次方
     */
    private static final String CF = "^";
    /**
     * 根号
     */
    private static final String GH = "√";

    /**
     * 左括号
     */
    private static final String LEFT_BRACKET = StringPool.LEFT_BRACKET;

    /**
     * 右括号
     */
    private static final String RIGHT_BRACKET = StringPool.RIGHT_BRACKET;


    /**
     * 解析计算公式
     *
     * @param str
     * @return
     */
    public static String parse(String str) {
        String param = format(str);
        System.out.println("param:" + param);
        param = extract(param);
        param = pow(param);
        return param;
    }

    /**
     * 开根
     *
     * @param str
     * @return
     */
    private static String extract(String str) {
        if (!str.contains(GH)) {
            return str;
        }
        List<String> params = Arrays.asList(str.split("\\" + GH));
        return doParse(params, true);
    }

    /**
     * 次方
     *
     * @param str
     * @return
     */
    private static String pow(String str) {
        if (!str.contains(CF)) {
            return str;
        }
        List<String> params = Arrays.asList(str.split("\\" + CF));
        return doParse(params, false);
    }

    private static String doParse(List<String> params, boolean extract) {
        List<String> lines = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0, size = params.size(); i < size - 1; i++) {
            String left = map.get(i);
            String right = map.get(i + 1);

            if (StringUtils.isBlank(left)) {
                left = params.get(i);
            }
            if (StringUtils.isBlank(right)) {
                right = params.get(i + 1);
            }

            log.debug("left:{}", left);
            log.debug("right:{}", right);

            int leftIndex = leftIndex(left);
            int rightIndex = rightIndex(right);

            String leftNew = left.substring(leftIndex);
            String rightNew = right.substring(0, rightIndex);

            log.debug("leftNew:{}", leftNew);
            log.debug("rightNew:{}", rightNew);

            map.put(i, left.substring(0, leftIndex));
            map.put(i + 1, right.substring(rightIndex));

            log.debug("current map:{}", map);

            if (extract) {
                rightNew = "1.0/" + rightNew;
            }

            String curPow = StrFormatter.format("math.pow({},{})", leftNew, rightNew);

            lines.add(curPow);

            log.debug("==========================");
        }

        List<String> allLines = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        map.entrySet().forEach(e -> {
            allLines.add(e.getValue());
            int index = atomicInteger.getAndIncrement();
            if (index < lines.size()) {
                String line = lines.get(index);
                if (StringUtils.isNotBlank(line)) {
                    allLines.add(line);
                }
            }
        });

        return String.join("", allLines);
    }


    /**
     * 格式化字符串
     *
     * @param str
     * @return
     */
    private static String format(String str) {
        return StringUtils.isBlank(str) ? "" :
                str.replaceAll("[\\{\\[【（]", LEFT_BRACKET)
                        .replaceAll("[\\}\\]】）]", RIGHT_BRACKET)
                        .replaceAll("e", String.valueOf(Math.E))
                        .replaceAll("π", String.valueOf(Math.PI))
                        .replaceAll(" ", "");
    }


    /**
     * 计算右边最接近的字符串
     *
     * @param str
     * @return
     */
    private static int rightIndex(String str) {
        int index = 0;
        if (!str.startsWith(LEFT_BRACKET)) {
            boolean matched = false;
            for (int i = 0, size = str.length(); i < size; i++) {
                char c = str.charAt(i);
                if (OPERATOR.contains(String.valueOf(c))) {
                    index = i - 1;
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                index = str.length() - 1;
            }
        } else {
            index = calLeftToRightIndex(str, index);
        }
        return index + 1;
    }

    /**
     * 计算左边最接近的字符串的下标
     *
     * @param str
     * @return
     */
    private static int leftIndex(String str) {
        int maxIndex = str.length();
        int index = maxIndex - 1;
        if (!str.endsWith(RIGHT_BRACKET)) {
            boolean matched = false;
            for (int i = maxIndex - 1; i >= 0; i--) {
                char c = str.charAt(i);
                if (OPERATOR.contains(String.valueOf(c))) {
                    index = i + 1;
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                index = 0;
            }

        } else {
            index = calRightToLeftIndex(str, index);
        }
        return index;
    }

    private static int calLeftToRightIndex(String str, int def) {
        int index = def;
        Queue<Integer> stack = new LinkedBlockingQueue<>();

        for (int i = 0, size = str.length(); i < size; i++) {
            String curStr = String.valueOf(str.charAt(i));
            if (curStr.equals(LEFT_BRACKET)) {
                stack.offer(1);
            }
            if (curStr.equals(RIGHT_BRACKET)) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static int calRightToLeftIndex(String str, int def) {
        int index = def;
        Queue<Integer> stack = new LinkedBlockingQueue<>();

        for (int i = str.length() - 1; i >= 0; i--) {
            String curStr = String.valueOf(str.charAt(i));
            if (curStr.equals(RIGHT_BRACKET)) {
                stack.offer(1);
            }
            if (curStr.equals(LEFT_BRACKET)) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                index = i;
                break;
            }
        }
        return index;
    }

}
