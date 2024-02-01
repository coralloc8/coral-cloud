package com.coral.test.flink.dto.desktop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className Project
 * @description 项目信息
 * @date 2022/11/15 15:03
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FunctionConfig implements Serializable {

    private Long id;

    /**
     * 项目标识
     */
    private String project;

    /**
     * 方法编码
     */
    private String funcCode;

    /**
     * 方法名称
     */
    private String funcName;

    /**
     * api路径
     */
    private String apiUrl;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 数据格式
     */
    private String dataType;

    /**
     * 扩展属性
     */
    private String extra;

    /**
     * 模拟入参
     */
    private String mockRequest;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private Integer status;
}
