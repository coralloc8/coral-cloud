package com.coral.base.common.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 */
public interface IEnum<T extends Enum<T>, R extends Serializable> {

    String CODE_KEY = "code";

    String NAME_KEY = "name";

    String DESCRIPTION_KEY = "description";

    /**
     * 获取code
     * 
     * @return
     */
    R getCode();

    /**
     * 获取name
     * 
     * @return
     */
    String getName();

    /**
     * 描述
     * 
     * @return
     */
    default String getDescription() {
        return "";
    }

    /**
     * 获取所有参数
     * 
     * @return
     */
    default Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>(4);
        map.put(CODE_KEY, this.getCode());
        map.put(NAME_KEY, this.getName());
        map.put(DESCRIPTION_KEY, this.getDescription());
        return map;
    }

}
