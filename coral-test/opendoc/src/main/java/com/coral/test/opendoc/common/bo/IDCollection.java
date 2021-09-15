package com.coral.test.opendoc.common.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className BatchIDBO
 * @description id集合
 * @date 2021/7/12 14:29
 */
@Data
@Schema(description = "ID集合")
public class IDCollection {

    @Schema(description = "id列表")
    private List<String> ids;
}
