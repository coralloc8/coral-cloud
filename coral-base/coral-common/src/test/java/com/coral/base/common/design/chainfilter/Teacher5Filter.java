package com.coral.base.common.design.chainfilter;

/**
 * @author huss
 * @version 1.0
 * @className TeacherFilter
 * @description todo
 * @date 2022/8/1 16:59
 */
public class Teacher5Filter implements IChainFilter {
    @Override
    public boolean filter(Request request) {
        if (request.getDays() > 60) {
            System.out.println("请假天数过长,还是回家种田吧!");
        }
        return true;
    }
}
