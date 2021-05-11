package com.coral.base.common;

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

    public static String like(String str) {
        return "%" + str + "%";
    }

    public static String leftLike(String str) {
        return "%" + str;
    }

    public static String rightLike(String str) {
        return str + "%";
    }

}
