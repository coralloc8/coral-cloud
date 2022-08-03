package com.coral.base.common.design.chainfilter;

import com.coral.base.common.StrFormatter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className ChainFilter
 * @description todo
 * @date 2022/8/1 16:47
 */
public class ChainFilter implements IChainFilter {

    @Getter
    private List<IChainFilter> filters;

    public ChainFilter() {
        this.filters = new ArrayList<>();
    }

    /**
     * 添加过滤器
     *
     * @param filter
     * @return
     */
    public ChainFilter addFilter(IChainFilter filter) {
        if (Objects.nonNull(filter)) {
            filters.add(filter);
        }
        return this;
    }

    @Override
    public boolean filter(Request request) {
        System.out.println(StrFormatter.format("请假学生：{} 请假天数：{}", request.getName(), request.getDays()));
        for (IChainFilter filter : getFilters()) {
            boolean result = filter.filter(request);
            if (!result) {
                break;
            }
        }
        System.out.println(">>>>>所有filter执行完毕.");
        return true;
    }
}
