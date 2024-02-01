package com.coral.test.flink.tag.dto;

import com.coral.base.common.IOUtil;
import com.coral.base.common.json.JsonUtil;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 编码知识库-临床表现相反的诊断编码知识库
 */
@Data
public class KnowledgeCodeDiseaseGroupContraryDTO {
    private static final Class THIS = KnowledgeCodeDiseaseGroupContraryDTO.class;

    /**
     * 字典来源
     */
    private String dataSource;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典版本
     */
    private String dictVersion;

    /**
     * ICD10附加编码（医保2.0）
     */
    private String diseaseAttachCode;
    /**
     * ICD10主要编码
     */
    private String diseaseCode;

    /**
     * ICD10名称
     */
    private String diseaseName;

    /**
     * 同组诊断编码
     */
    private String groupCode;

    /**
     * 组间关系（同组或，组间且）
     */
    private Integer groupRelation;

    /**
     * 主键
     */
    private Long id;

    /**
     * 参考标准
     */
    private String reference;


    public static List<KnowledgeCodeDiseaseGroupContraryDTO> init() {
        InputStream inputStream = THIS.getClassLoader()
                .getResourceAsStream("disease_group_contrary.json");
        try {
            String json = IOUtil.readStream(inputStream);
            List<KnowledgeCodeDiseaseGroupContraryDTO> gcs = JsonUtil.parseArray(json, KnowledgeCodeDiseaseGroupContraryDTO.class);
            return gcs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
