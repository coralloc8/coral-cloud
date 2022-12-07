package com.coral.test.groovy.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceSimpPerConfig
 * @description DataSourceSimpPerConfig
 * @date 2022/12/1 17:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceValue {

    private String key;

    private String value;
}