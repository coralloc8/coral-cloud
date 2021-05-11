package com.coral.base.common.json;

import java.lang.annotation.*;

/**
 * @author huss
 * @version 1.0
 * @className IgnoreNull
 * @description 会针对null类型的值序列化时忽略掉该字段
 * @date 2021/4/9 9:30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonIgnoreNull {
    boolean value() default true;
}
