package com.coral.base.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className AnnotationUtil
 * @description 获取对应的注解
 * @date 2021/4/9 9:42
 */
public final class AnnotationUtil {

    public static <A extends Annotation, M> Optional<A> findAnnotation(M m, Class<A> annotationType) {
        if (annotationType == null || m == null) {
            return Optional.empty();
        }
        A annotation = null;

        if (m instanceof AnnotatedElement) {
            AnnotatedElement ele = (AnnotatedElement) m;
            annotation = ele.getAnnotation(annotationType);
        }
        if (annotation == null && m instanceof Member) {
            Member me = (Member) m;
            annotation = me.getDeclaringClass().getAnnotation(annotationType);
        }

        if (annotation == null) {
            return Optional.empty();
        }

        return Optional.of(annotation);
    }





//    public static void main(String[] args) {
//
//        List<Field> fieldList = new ArrayList<>();
//        Class clazz = B2.class;
//
//        while (clazz != null) {
//            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
//            clazz = clazz.getSuperclass();
//        }
//
//        System.out.println(fieldList);
//
//
//        Field field = fieldList.stream().filter(e -> e.getName().equals("name")).findFirst().get();
//
//
//        Optional<JsonNullSerializer> opt = findAnnotation(field, JsonNullSerializer.class);
//        System.out.println(opt);
//
//
//    }

}
