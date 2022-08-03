package com.coral.base.common.design.chainfilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className Request
 * @description todo
 * @date 2022/8/1 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    /**
     * 姓名
     */
    private String name;

    /**
     * 天数
     */
    private Integer days;

}
