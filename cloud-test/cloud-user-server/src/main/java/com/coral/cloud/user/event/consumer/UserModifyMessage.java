package com.coral.cloud.user.event.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserModifyMessage
 * @description 用户修改信息
 * @date 2022/4/15 13:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModifyMessage {
    /**
     * 用户编码
     */
    private String userNo;

    /**
     * 用户名
     */
    private String username;

}
