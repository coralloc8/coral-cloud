package com.coral.cloud.user.entity.userrecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className HospitalUserRecordMzDocument
 * @description 用户门诊记录文档
 * @date 2022/5/9 14:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "#{@documentIndex.hospitalUserRecordMz.write}", createIndex = false)
public class HospitalUserRecordMzWriteDocument {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 医院编码
     */
    private String hospitalCode;
    /**
     * his的主键id
     */
    private String primaryId;
    /**
     * 患者ID
     */
    private String patientId;
    /**
     * 就诊卡号
     */
    private String cardNo;
    /**
     * 门诊号
     */
    private String outPatientNumber;
    /**
     * 患者名称
     */
    private String patientName;
    /**
     * 就诊流水号
     */
    private String outPatientNo;
    /**
     * 就诊时间
     */
    private LocalDateTime outPatientTime;
    /**
     * 初步诊断
     */
    private String primaryDiagnosisStr;
    /**
     * 主诉
     */
    private String mainSuit;
    /**
     * 现病史
     */
    private String nowMedicalHistory;
    /**
     * 既往史
     */
    private String pastHistory;
    /**
     * 过敏史
     */
    private String allergyHistory;
    /**
     * 查体
     */
    private String physicalExamine;
    /**
     * 辅助检查
     */
    private String suppExamine;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
