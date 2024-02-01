package com.coral.cloud.monitor.dto.metresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className ApiResponseInfoDTO
 * @description 接口响应信息
 * @date 2023/4/13 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseInfoDTO implements IResponse {

    /**
     * http状态
     */
    private String status;

    /**
     * 异常
     */
    private String exceptionClass;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;
}
