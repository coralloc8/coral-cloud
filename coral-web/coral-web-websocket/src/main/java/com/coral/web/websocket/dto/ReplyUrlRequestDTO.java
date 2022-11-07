package com.coral.web.websocket.dto;

import com.coral.web.websocket.common.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className ReplyUrlRequestDTO
 * @description 响应url入参
 * @date 2022/10/28 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyUrlRequestDTO {

    /**
     * 业务类型
     */
    private BusinessType businessType;

    /**
     * 功能方法
     */
    private String function;

    /**
     * 入参
     */
    private Object params;
}
