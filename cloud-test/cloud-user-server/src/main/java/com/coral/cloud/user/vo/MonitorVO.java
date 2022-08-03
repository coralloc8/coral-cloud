package com.coral.cloud.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className MonitorVO
 * @description todo
 * @date 2022/8/3 10:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitorVO {

    private Double gaugeNumber;

    private Double counterNumber;

    private Double summaryNumber;

    private Double summaryCount;

    private Double summaryTotalAmount;

    private Double summaryMean;

    private Double summaryMax;
}
