package com.coral.base.common.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className QuestionAnswerFunction
 * @description 问题答案功能
 * @date 2022/5/25 18:06
 */
public class QuestionAnswerFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        System.out.println(arg1);
        String key = FunctionUtils.getStringValue(arg1, env);
        Integer left = (Integer) env.get(key);
        return AviatorLong.valueOf(left.longValue());
    }

    @Override
    public String getName() {
        return "questionAnswer";
    }
}
