package com.coral.base.common.reflection;


import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author huss
 * @version 1.0
 * @className ColumnUtil
 * @description 列工具类
 * @date 2022/1/12 16:14
 */
public class ColumnUtil {

    private static final String METHOD_IS_PREFIX = "is";
    private static final String METHOD_GET_PREFIX = "get";
    private static final String METHOD_SET_PREFIX = "set";
    private static final String LAMBDA_WRITE_REPLACE = "writeReplace";

    /**
     * 获取属性名称
     *
     * @param lambda
     * @param <T>
     * @return
     */
    public static <T> String getPropertyName(IGetter<T> lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod(LAMBDA_WRITE_REPLACE);
            method.setAccessible(Boolean.TRUE);
            //调用writeReplace()方法，返回一个SerializedLambda对象
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String fieldName = methodToProperty(serializedLambda.getImplMethodName());
            return fieldName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }


    public static String methodToProperty(String name) {
        if (name.startsWith(METHOD_IS_PREFIX)) {
            name = name.substring(2);
        } else {
            if (!name.startsWith(METHOD_GET_PREFIX) && !name.startsWith(METHOD_SET_PREFIX)) {
                throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            name = name.substring(3);
        }

        boolean hump = name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1));
        if (hump) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }


}
