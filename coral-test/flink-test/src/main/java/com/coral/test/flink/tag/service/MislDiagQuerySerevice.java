package com.coral.test.flink.tag.service;

import com.coral.test.flink.tag.dto.MislDiagDTO;
import com.coral.test.flink.tag.dto.PatientInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MislDiagQuerySerevice {
    /**
     * 查询诊断列表
     *
     * @param patientInfo
     * @return
     */
    public List<MislDiagDTO> queryDiags(PatientInfoDTO patientInfo) {
        // 模拟查询数据库
        return MislDiagDTO.init().stream()
                .filter(e -> e.getIptSn().equalsIgnoreCase(patientInfo.getIptSn()))
                .collect(Collectors.toList());
    }
}
