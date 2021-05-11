package com.coral.base.common.convert;

import java.util.List;

/**
 * @author huss
 */
public interface JsonTreeConvert<T,R extends SimpleJsonTree> extends IConvert<List<T>, List<R>> {

    /**
     * 设置树形节点label值
     * 
     * @param t
     * @return
     */
    String setTreeLabel(T t);

    /**
     * 设置树形节点value值
     * 
     * @param t
     * @return
     */
    Object setTreeValue(T t);

}
