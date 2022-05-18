package com.coral.base.common.reflection;

import com.coral.base.common.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className LogUtil
 * @description 日志工具类
 * @date 2022/1/12 14:44
 */
@Slf4j
public class ModifyUtil {

    /**
     * 修改参数
     *
     * @param currVal
     * @param t
     * @param methodGetter
     * @param logBuilder
     * @param <T>
     * @return
     */
    public static <T> T update(Object currVal, T t, IGetter<T> methodGetter, StringBuilder logBuilder) {
        try {
            String className = t.getClass().getSimpleName();
            String fieldName = LambdaFieldUtil.getFieldName(methodGetter);
            Object oldVal = methodGetter.apply(t);

            boolean empty = Objects.isNull(currVal) ||
                    String.class.isAssignableFrom(currVal.getClass()) && StringUtils.isBlank(currVal.toString());
            if (!empty) {
                String log = StrFormatter.format("[{}.{}] updated,old:{},new:{};", className, fieldName, oldVal, currVal);
                logBuilder.append(log);
                BeanUtils.setProperty(t, fieldName, currVal);
            }
        } catch (Exception e) {
            log.error("#####Error:", e);
        }
        return t;
    }

}
