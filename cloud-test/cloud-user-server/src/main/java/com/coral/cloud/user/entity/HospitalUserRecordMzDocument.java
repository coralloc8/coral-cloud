package com.coral.cloud.user.entity;

import com.coral.cloud.user.common.constants.IkConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

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
@Setting(settingPath = "elasticsearch/index-hospital_user_record_mz-setting.json")
@Mapping(mappingPath = "elasticsearch/index-hospital_user_record_mz-mapping.json")
@Document(indexName = "index-hospital_user_record_mz")
public class HospitalUserRecordMzDocument {

    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 医院编码
     */
    @Field(type = FieldType.Keyword)
    private String hospitalCode;
    /**
     * his的主键id
     */
    @Field(type = FieldType.Keyword)
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
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime outPatientTime;

    /**
     * 初步诊断
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String primaryDiagnosisStr;
    /**
     * 主诉
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String mainSuit;
    /**
     * 现病史
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String nowMedicalHistory;
    /**
     * 既往史
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String pastHistory;
    /**
     * 过敏史
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String allergyHistory;
    /**
     * 查体
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String physicalExamine;
    /**
     * 辅助检查
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_MAX_WORD)
    private String suppExamine;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updateTime;
}
