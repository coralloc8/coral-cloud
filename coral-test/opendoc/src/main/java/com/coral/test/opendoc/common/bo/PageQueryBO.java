package com.coral.test.opendoc.common.bo;

import com.coral.test.opendoc.common.constants.DefConstant;
import com.coral.test.opendoc.common.enums.GlobalOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 * @version 1.0
 * @className PageQueryBO
 * @description 分页查询参数
 * @date 2021/7/8 10:26
 */
@Data
@ToString(callSuper = true)
@Schema(description = "分页查询")
public class PageQueryBO extends ComQueryBO {

    /**
     * 分页页数
     */
    @Schema(description = "分页页数", required = true, example = DefConstant.PAGE_CURRENT, defaultValue = DefConstant.PAGE_CURRENT)
    protected Integer pageNum;

    /**
     * 每页显示数量
     */
    @Schema(description = "每页显示数量", required = true, example = DefConstant.PAGE_SIZE, defaultValue = DefConstant.PAGE_SIZE)
    protected Integer pageSize;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段", example = DefConstant.ORDER_KEY, defaultValue = DefConstant.ORDER_KEY)
    protected String sidx;

    /**
     * 排序方式
     */
    @Schema(description = "排序方式", example = DefConstant.ORDER_DESC, defaultValue = DefConstant.ORDER_DESC)
    protected GlobalOrder order;


    protected PageQueryBO() {
        this.pageNum = 1;
        this.pageSize = 20;
        this.order = GlobalOrder.DESC;
    }

    /**
     * 设置排序字段
     *
     * @return
     */
    public PageQueryBO sidx() {
        this.setSidx(sidx);
        return this;
    }
}
