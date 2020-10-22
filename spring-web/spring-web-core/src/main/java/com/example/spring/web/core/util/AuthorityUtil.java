package com.example.spring.web.core.util;

import java.util.Iterator;
import java.util.List;

/**
 * @description: 权限菜单
 * @author: huss
 * @time: 2020/7/13 15:22
 */
public class AuthorityUtil {

    /**
     * 获取权限值
     * 
     * @param rights
     * @return
     */
    public static long getRights(int... rights) {
        long rs = 0L;
        int[] var3 = rights;
        int var4 = rights.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            int right = var3[var5];
            rs |= (long)Math.pow(2.0D, (double)right);
        }

        return rs;
    }

    /**
     * 获取权限值
     * 
     * @param rights
     * @return
     */
    public static long getRights(List<Integer> rights) {
        long rs = 0L;

        int right;
        for (Iterator var3 = rights.iterator(); var3.hasNext(); rs |= (long)Math.pow(2.0D, (double)right)) {
            right = (Integer)var3.next();
        }

        return rs;
    }

    /**
     * 检查是否拥有该权限值
     * 
     * @param role
     *            拥有的总的权限值
     * @param access
     *            当前需要验证的权限值
     * @return
     */
    public static boolean checkAccess(long role, long access) {
        if (role != 0L) {
            long value = (long)Math.pow(2.0D, (double)access);
            return (role & value) == value;
        } else {
            return false;
        }
    }
}
