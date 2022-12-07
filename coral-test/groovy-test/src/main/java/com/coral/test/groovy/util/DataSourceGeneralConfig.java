package com.coral.test.groovy.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceSimpConfig
 * @description 数据源简约配置
 * @date 2022/11/30 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceGeneralConfig {

    /**
     *
     */
    private String dsUuid;

    /**
     * API请求基本地址
     */
    private String baseApiUrl;


    /**
     * 外部资源
     */
    private List<DataSourceValue> externalResources;

    /**
     * 通用参数
     */
    private List<DataSourceValue> generalParameters;

    /**
     * 通用读取实现
     */
    private String generalReadImpl;


    /**
     * 通用前置数据验证
     */
    private String generalValidationData;


    /**
     * 通用响应解析
     */
    private String generalResponseParseImpl;


    /**
     * 通用响应成功标识
     */
    private DataSourceValue generalResponseSuccess;


    /**
     * 通用响应数据节点
     */
    private String generalResponseData;

    /**
     * 通用请求头部
     */
    private List<DataSourceValue> generalRequestHeader;


}
