package com.coral.base.common.convert;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJsonTree {

    private String label;
    private Object value;
    // @Default
    // private boolean joined = false;
    // @Default
    // private boolean expand = true;
    /**
     * 额外属性
     */
    private Map<String, Object> extraAttributes;

    private List<SimpleJsonTree> children;

}
