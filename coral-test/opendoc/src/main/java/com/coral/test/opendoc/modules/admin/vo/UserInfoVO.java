package com.coral.test.opendoc.modules.admin.vo;

import com.coral.test.opendoc.common.enums.GlobalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className UserInfo
 * @description 用户信息
 * @date 2021/9/14 15:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息", name = "UserInfo")
public class UserInfoVO {

    @Schema(description = "编号")
    private String no;

    @Schema(description = "姓名")
    private String username;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态")
    private GlobalStatus status;

}
