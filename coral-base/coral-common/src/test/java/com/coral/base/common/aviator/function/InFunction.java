package com.coral.base.common.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Collection;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className InFunction
 * @description in
 * @date 2022/5/27 14:04
 */
public class InFunction extends AbstractFunction {

    /**
     * @param env
     * @param arg1 需要判断的数据
     * @param arg2 数据集合 collection
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Collection collection = (Collection) FunctionUtils.getJavaObject(arg2, env);
        Object obj = FunctionUtils.getJavaObject(arg1, env);
        boolean isMatch = collection.stream().anyMatch(e -> e.toString().equals(obj.toString()));
        return AviatorBoolean.valueOf(isMatch);
    }

    @Override
    public String getName() {
        return "inSeq";
    }
}
