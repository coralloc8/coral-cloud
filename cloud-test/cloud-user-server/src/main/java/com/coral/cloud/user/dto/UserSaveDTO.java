package com.coral.cloud.user.dto;

import com.coral.base.common.http.request.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserSaveDTO
 * @description 用户保存
 * @date 2022/4/2 13:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户保存")
public class UserSaveDTO implements IRequest {
    @Schema(description = "用户名")
    private String username;
}