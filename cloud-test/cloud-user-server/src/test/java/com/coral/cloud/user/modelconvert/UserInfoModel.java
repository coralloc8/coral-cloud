package com.coral.cloud.user.modelconvert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserInfoModel
 * @description model
 * @date 2022/5/18 18:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoModel {
    private String userNo;
    private String username;
}
