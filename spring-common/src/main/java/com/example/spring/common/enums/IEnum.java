package com.example.spring.common.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 */
public interface IEnum<T extends Enum<T>, R extends Serializable> {

    String CODE_KEY = "code";

    String NAME_KEY = "name";

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
     * 获取所有参数
     * 
     * @return
     */
    default Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>(4);
        map.put(CODE_KEY, this.getCode());
        map.put(NAME_KEY, this.getName());
        return map;
    }

}
