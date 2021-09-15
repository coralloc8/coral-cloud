package com.coral.test.opendoc.common.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huss
 * @version 1.0
 * @className PageQueryBO
 * @description 分页查询参数
 * @date 2021/7/8 10:26
 */
@Data
@Schema(description = "关键字搜索")
public class ComQueryBO {

    /**
     * 查询关键字
     */
    @Schema(description = "查询关键字")
    protected String keyword;
}
