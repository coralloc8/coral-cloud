package com.coral.test.opendoc.modules.admin.bo;

import com.coral.test.opendoc.common.bo.PageQueryBO;
import com.coral.test.opendoc.common.enums.GlobalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author huss
 * @version 1.0
 * @className UserQueryBO
 * @description 用户查询
 * @date 2021/9/14 16:06
 */
@Data
@Schema(name = "UserQuery", description = "用户查询")
public class UserQueryBO extends PageQueryBO {

    @Schema(description = "状态")
    private GlobalStatus status;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "开始日期")
    private LocalDate startDate;

}
