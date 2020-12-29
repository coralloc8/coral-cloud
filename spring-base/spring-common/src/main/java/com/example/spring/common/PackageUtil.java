package com.example.spring.common;

import java.util.Optional;

/**
 * @description: 包 tuil
 * @author: huss
 * @time: 2020/11/5 10:20
 */
public class PackageUtil {

    /**
     * 查找当前正在运行的main方法所在的class类
     * 
     * @return
     */
    public static Optional<Class<?>> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTraces = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTraces) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Optional.of(Class.forName(stackTraceElement.getClassName()));
                }
            }
        } catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return Optional.empty();
    }
}
