package com.coral.test.opendoc.schema;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className DateTimeRangeSchema
 * @description 时间范围查询schema
 * @date 2021/12/14 15:44
 */
@Schema(description = "时间范围查询")
public interface DateTimeRangeSchema {

    /**
     * 开始时间
     *
     * @return String
     */
    @Schema(description = "开始时间")
    String getStartTime();

    /**
     * 结束时间
     *
     * @return
     */
    @Schema(description = "结束时间")
    LocalDateTime getEndTime();
}
