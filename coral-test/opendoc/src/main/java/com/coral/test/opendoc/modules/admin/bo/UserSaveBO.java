package com.coral.test.opendoc.modules.admin.bo;

import com.coral.test.opendoc.common.enums.GlobalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserSaveBO
 * @description 用户保存
 * @date 2022/3/29 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveBO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "状态")
    private GlobalStatus status;
}
