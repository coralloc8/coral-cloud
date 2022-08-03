package com.coral.base.common;

import java.util.Objects;

/**
 * @author huss
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String getFilePth(String path) {
        return path.replaceAll("\\\\", "/").replaceAll("//", "/");
    }

    public static String getCacheKey(String key1, String... keys) {
        StringBuilder sb = new StringBuilder(key1);
        for (String key : keys) {
            sb.append(":").append(key);
        }
        return sb.toString();
    }


    public static <T> T convert(Object obj, Class<T> clazz) {
        return (T) obj;
    }

    /**
     * 字符串前后拼接%
     *
     * @param str
     * @return
     */
    public static String like(String str) {
        return "%" + str + "%";
    }

    /**
     * 字符串左拼接%
     *
     * @param str
     * @return
     */
    public static String leftLike(String str) {
        return "%" + str;
    }

    /**
     * 字符串右拼接%
     *
     * @param str
     * @return
     */
    public static String rightLike(String str) {
        return str + "%";
    }

    /**
     * 是否是基本数据类型 即 int,double,long等类似格式
     *
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class clazz) {
        return Objects.nonNull(clazz) && clazz.isPrimitive();
    }

    /**
     * 是否是基本数据类型的包装类型
     *
     * @param clz
     * @return
     */
    public static boolean isBaseWrapType(Class clz) {
        try {
            if (Objects.isNull(clz) || isBaseType(clz)) {
                return false;
            }
            if (String.class.isAssignableFrom(clz)) {
                return true;
            }
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是数字
     *
     * @param letter
     * @return
     */
    public static boolean isNumber(String letter) {
        if (isBlank(letter)) {
            return false;
        }

        if (letter.startsWith(StringPool.DASH) || letter.startsWith(StringPool.PLUS)) {
            letter = letter.substring(1);
        }

        if (letter.indexOf(StringPool.DOT) < 0) {
            return isNumeric(letter);
        } else {
            String[] letters = letter.split(StringPool.BACK_SLASH + StringPool.DOT);
            return isNumeric(letters[0]) && isNumeric(letters[1]);
        }
    }

}
