package com.coral.test.opendoc.modules.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className PageInfoVO
 * @description 分页信息
 * @date 2021/9/14 18:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页信息")
public class PageInfoVO<T> {

    @Schema(description = "分页页数")
    private Integer pageNum;

    @Schema(description = "每页显示数量")
    private Integer pageSize;

    @Schema(description = "总页数")
    private Integer size;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "是否有下一页")
    private Boolean hasNextPage;

    @Schema(description = "数据")
    private List<T> records;

}
