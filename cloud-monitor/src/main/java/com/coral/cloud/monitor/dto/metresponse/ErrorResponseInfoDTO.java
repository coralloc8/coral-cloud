package com.coral.cloud.monitor.dto.metresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className ErrorResponseInfoDTO
 * @description 错误响应
 * @date 2023/4/13 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseInfoDTO implements IResponse {

    /**
     * 异常
     */
    private String exceptionClass;

}
