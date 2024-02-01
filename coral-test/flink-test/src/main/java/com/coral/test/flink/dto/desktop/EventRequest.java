package com.coral.test.flink.dto.desktop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * @author huss
 * @version 1.0
 * @className EventResponseDTO
 * @description 事件响应DTO
 * @date 2022/11/15 13:17
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventRequest {

    private String project;

    private String funcCode;

    private String msgJson;

    private Boolean returnBizData;

    private Boolean requestBiz;

    private String wsid;
}
