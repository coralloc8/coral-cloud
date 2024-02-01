package com.coral.test.flink.tag.dto;


import lombok.Data;

import java.util.List;

@Data
public class PatientInfoDTO {
    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 医保结算清单流水号
     */
    private String stlmSn;

    /**
     * 患者id
     */
    private String patientId;

    /**
     * 住院流水号
     */
    private String iptSn;

    /**
     * 门诊流水号
     */
    private String optSn;

    /**
     * 住院号
     */
    private String iptNo;

    /**
     * 门诊号
     */
    private String optNo;

    /**
     * 医疗机构名称
     */
    private String orgName;

    /**
     * 病案号
     */
    private String miRecordNum;

    /**
     * 病案流水号
     */
    private String sn;

    /**
     * 手术记录
     */
    private List<MislOpnDTO> opns;

}
