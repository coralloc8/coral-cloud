package com.coral.base.common.convert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className ConvertFruitModel
 * @description 水果model
 * @date 2022/5/19 9:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertFruitModel {

    /**
     * 水果名称
     */
    private String fruitName;

    /**
     * 价格
     */
    private Double money;
}
