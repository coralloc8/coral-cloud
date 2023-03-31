package com.coral.test.liteflow.slot;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className DiagnoseContext
 * @description 诊断上下文
 * @date 2023/3/24 11:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DiagnoseContext {

    /**
     * 患者ID
     */
    private String patientId;

    /**
     * 住院号
     */
    private String inpatientNo;

    /**
     * 门诊号
     */
    private String outPatientNo;

    /**
     * 住院流水号
     */
    private String inpatientSerialNo;

    /**
     * 门诊流水号
     */
    private String outpatientSerialNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 主诉
     */
    private List<String> mainSuits;

    /**
     * 症状
     */
    private List<String> symptoms;

    /**
     * 药品
     */
    private List<String> drugs;

    /**
     * 后续动态扩展的参数
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Map<String, Object> dys = new HashMap<>(16);


    public void put(@NonNull String key, Object obj) {
        dys.put(key, obj);
    }

    public <T> T find(@NonNull String key) {
        Object obj = dys.get(key);
        try {
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
