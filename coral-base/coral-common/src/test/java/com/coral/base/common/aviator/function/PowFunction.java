package com.coral.base.common.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className PowFunction
 * @description 次方根
 * @date 2022/9/19 14:35
 */
public class PowFunction extends AbstractFunction {

    /**
     * @param env
     * @param arg1 参数1
     * @param arg2 参数2
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Number left = FunctionUtils.getNumberValue(arg1, env);
        Number right = FunctionUtils.getNumberValue(arg2, env);
        return AviatorDouble.valueOf(Math.pow(left.doubleValue(), right.doubleValue()));
    }

    @Override
    public String getName() {
        return "pow";
    }


}
