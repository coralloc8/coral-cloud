package com.coral.test.flink.tag.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huss
 * 诊断结果
 */

@NoArgsConstructor
@Data
public class DiagResultDTO {

    private String groupCode;

    private List<String> diagCodes;

    public DiagResultDTO(String groupCode, List<String> diagCodes) {
        this.groupCode = groupCode;
        this.diagCodes = diagCodes;
    }


}
