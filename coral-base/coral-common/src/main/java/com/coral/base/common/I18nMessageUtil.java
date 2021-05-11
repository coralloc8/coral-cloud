package com.coral.base.common;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * 生成唯一key
 * 
 * @author huss
 */
public class I18nMessageUtil {

    private static final int COMPRESS_LIMIT_LENGTH = 32;

    /**
     *
     * 获取messagekey 默认会开启路径压缩，超过32个字符，会取package中每个路径的前两位字母。
     * </p>
     * 例如： com.example.test1.test2.test3.test4.person.not_null 为将package com.example.test1.test2.test3.test4 压缩成
     * co.ex.te.te.te.te 最后结果为 co.ex.te.te.te.te.person.not_null
     * 
     * @param obj
     *            可以是普通class、Enum、ConstraintViolation
     *
     * @return
     */
    public static String getMessageKey(Object obj) {
        return getMessageKey(obj, true);
    }

    /**
     *
     * @param obj
     *            可以是普通class、Enum、ConstraintViolation
     * @return
     */
    public static String getMessageKey(Object obj, boolean isCompress) {

        String packageName = obj.getClass().getPackage().getName();
        if (canCompress(packageName.length(), isCompress)) {
            packageName = compressPackage(packageName);
        }
        String classSimpleName = obj.getClass().getSimpleName();
        String className =
            NameStyleUtil.humpToLine(packageName) + StringPool.DOT + NameStyleUtil.humpToLine(classSimpleName);
        if (obj instanceof Enum) {
            return className.toLowerCase() + "." + obj.toString().toLowerCase();
        } else if (obj instanceof ConstraintViolation) {
            return getMessageKey((ConstraintViolation)obj, isCompress);
        }
        return className;

    }

    /**
     * 通过校验获取messkey
     * 
     * @param constraintViolation
     * @return
     */
    private static String getMessageKey(ConstraintViolation constraintViolation, boolean isCompress) {
        if (constraintViolation == null) {
            return "";
        }

        String packageName = constraintViolation.getRootBeanClass().getPackage().getName();
        if (canCompress(packageName.length(), isCompress)) {
            packageName = compressPackage(packageName);
        }
        String classSimpleName = constraintViolation.getRootBeanClass().getSimpleName();
        String className =
            NameStyleUtil.humpToLine(packageName) + StringPool.DOT + NameStyleUtil.humpToLine(classSimpleName);

        ConstraintDescriptor<?> constraintDescriptor = constraintViolation.getConstraintDescriptor();
        Annotation annotation = constraintDescriptor.getAnnotation();

        String annotationStr = NameStyleUtil.humpToLine(annotation.annotationType().getSimpleName());

        String fileName = NameStyleUtil.humpToLine(constraintViolation.getPropertyPath().toString());
        return (className + "." + fileName + "." + annotationStr).toLowerCase();
    }

    private static boolean canCompress(int length, boolean isCompress) {
        return COMPRESS_LIMIT_LENGTH < length && isCompress;
    }

    /**
     * 压缩package路径
     * 
     * @param packageName
     * @return
     */
    private static String compressPackage(String packageName) {
        return Arrays.stream(packageName.split(StringPool.BACK_SLASH + StringPool.DOT))
            .map(s -> s.substring(0, s.length() >= 2 ? 2 : s.length())).collect(Collectors.joining(StringPool.DOT));
    }

}
